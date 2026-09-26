# Server communication, users, courses and updates

This part of Logic 2010 talks to the UCLA "Logic database" server. It also keeps
track of who the user is and which course they are in, and handles backups,
submissions and self-updates. Names come from `mappings/core.mapping` and
`mappings/server.mapping`. The placeholder name is given in parentheses the first
time a class is mentioned.

Everything described here comes from reading the code. The server was never
contacted.

## 1. Classes at a glance

| class | role |
|---|---|
| `ServerConnection` (`C_KC`) | Static facade holding every endpoint URL and every request, plus backup, restore, update and work-folder helpers. |
| `ServerSession` (`C_0C`) | A server nonce plus the program credentials. It builds signed form or multipart bodies. |
| `ResponseHandler` (`C_v_`) | Error sink (`setError`/`getError`). Every request object implements it, and `ErrorRef` is the concrete store. |
| `Submission` (`C_0A`), `BackupRequest` (`C_XA`), `ProblemUpload` (`C_LD`), `ProblemDbRecord` (`C_FC`), `UpdateInfo` (`C_j_B`) | Per-operation request/result records. Each one carries its session and error. |
| `BackupEntry` (`C_w_D`), `QueryResult` (`C_s_C`), `LoginResult` (`C_a_F`), `PasswordEntry` (`C__F`) | Small value objects. |
| `HtmlTableParser` (`C_r_`), `HtmlTag` (`C_FA`) | Turn the server's HTML reply into an `Object[][]` grid. |
| `NetworkTask` (`C_r_A`), `NetworkWorker` (`C_o_`), `PostWorker` (`C_HE`), `DownloadWorker` (`C_s_E`), `BusyIndicator` (`C_x_A`) | Watchdog, abort and progress-dialog support. |
| `ServerUrl` (`C_p_`), `HttpDownloader` (`C_i_B`), `JarClassLoader` (`C_RA`) | URL wrapper, file/zip download and reading `loader.jar`'s version. |
| `Md5OutputStream`, `HexEncoder`, `Base64Codec`, `Base64InputStream` (`C_LE`), `Base64OutputStream` (`C_WC`), `Utf8Codec` (`C_UB`), `ScramblingWriter` (`C_k_F`) | Hand-written codecs (the code predates `java.util.Base64`). |
| `UserInfo` (`C_OE`), `NewUserInfo` (`C_HB`) | `work/user.txt`, plus the server-side ids (`userUid`, `courseUid`, `relations`). |
| `CourseInfo` (`C_MF`), `CourseChooserPanel` (`C_n_E`) | The server's course list and the Institution/Term/Course chooser. |
| `Institution` (`C_SF`), `StandardInstitution` (`C_e_C`), `TermCode` (`C_P`), plus `UclaInstitution`, `UcsbInstitution`, `UcsdInstitution`, `YaleInstitution`, `SunysbInstitution`, `BounInstitution`, `TmuInstitution` | Term codes and student-ID normalisation. |
| `AccountManager` (`C_j_C`), `UserSetup` (`C_u_C`) | The dialogs: password, course choice, welcome, restore/register, quit-time backup. |
| `PasswordInputField` (`C_q_`), `PasswordFieldValidator` (`C_UE`), `ButtonChoiceHandler` (`C_b_E`), `UpdatePromptHandler` (`C_SE`), `UpdateInstructionsHandler` (`C_p_F`) | Dialog widgets and handlers. |
| `HttpDigestAuth` (`C_N`), `AuthHeaderParams` (`C_OD`), `SocketLineClient` (`C_GE`), `MailSocketClient` (`C_UD`), `SocketLineReader` (`C_w_`), `ProtocolReply` (`C_YB`) | Leftover library code that nothing on the live path uses, apart from `SocketLineClient.getLocalIpString`. |

## 2. Configuration: where the URLs come from

`ServerConnection.readDatabaseLinks` runs during startup. It reads the links
table (`ghost.txt`) and resolves these directories: `editDir`, `localDir`,
`textDir`, `adminDir` and `nonetDir`.

- **Instructor ("admin") install.** If `textDir` holds its own links file, this
  is an admin install (`adminInstall = true`). In that case:
  - `institution`, `term`, `course`, `ident` and `textVersion` are read from the
    text links.
  - `adminInstitution`, `adminTerm`, `adminCourse` and `adminVersion` are read
    from our own links.
- **Otherwise.** Everything comes from our own links. The `admin*` values are
  only set when `adminDir` points at an admin install.

The institution names `Demo` and `Test` switch on `demoMode`. In demo mode,
submission and upload are refused with message `not039`.

Endpoint URLs are link keys. In the shipped `ghost.txt` they all live under
`https://logiclx.humnet.ucla.edu/Logic/Desktop/`:

| link key | path | field |
|---|---|---|
| `logic_nonce` | `Nonce` | `nonceUrl` |
| `logic_verify` | `Verify` | `verifyUrl` |
| `logic_user` | `User` | `userUrl` |
| `logic_password` | `Password` | `passwordUrl` |
| `logic_site` | `Site` | `siteUrl` (required, but no request uses it) |
| `logic_submission` | `Submission` | `submissionUrl` |
| `logic_backup`, `logic_backup_info`, `logic_restore`, `logic_delete_backup` | `Backup`, `Backup_Info`, `Restore`, `Delete_Backup` | `backup*Url`, `restoreUrl` |
| `logic_load_remote` | `Load_Remote` | `loadRemoteUrl` |
| `logic_course_remote` | `Course_Remote` | `courseRemoteUrl` |
| `logic_get_user_reln`, `logic_add_user_reln` | `Get_User_Reln`, `Add_User_Reln` | `get/addUserRelationUrl` |
| `logic_user_info`, `logic_upload`, `logic_insert_problem`, `logic_update_problem`, `logic_delete_problem` | *not present in `ghost.txt`* | so those features are inert |
| `logic_sql_generator` | `http://logic2k.../SQL.cfm` | never used |

`readDatabaseLinks` fails, and startup stops with "Could not read database link
file", if any of these is missing: nonce, verify, user, password, site,
submission, backup, backup_info, restore, delete_backup, course_remote,
load_remote, get/add_user_reln.

### Program credentials

`serverCredentials` is `LogicProgram.getCredentials("logic")`. It comes from the
options file (`wraith.txt`) entry ``$logic:<b64>`u``, where the password is
`Scrambler.unscramble(base64decode(<b64>))`. These credentials identify the
*program installation*, not the student. The same options line format supplies
other named credentials: `digest` (the work-file signature key), `exam`,
`restore`, `instructor` and `developer`. It also supplies the backup options:
`` `b `` is the backup key (for example `work`), `` `r `` is the restore key and
`` `c `` is the maximum number of backups to keep.

## 3. Transport

All calls are HTTP POSTs made by `ServerConnection.doPost`:

- **Request body.** The body is a `Vector` of parts:
  - A `String` is written followed by CRLF.
  - A `byte[]` is written raw.
  - A `File` is written base64-encoded, followed by CRLF.
- **Headers.** The body is first written into an `Md5OutputStream` to compute
  `Content-length` and `Content-MD5` (the base64 MD5 of the body).
  `Content-type` is either `application/x-www-form-urlencoded` or
  `multipart/form-data; boundary=<b>`.
- **Timeouts and status.** Connect and read timeouts are 10 s. Any status
  outside 2xx is treated as "no response" (`null`).
- **Reading the reply.** The reply is read line by line and reading **stops at
  the first line containing `</html>`**. If the caller passed a save file, the
  rest of the stream is base64-decoded into it (`decodeBase64ToFile`; no current
  caller uses this).
- **Watchdog.** A `NetworkTask` watchdog is always attached; `post` creates a
  10 s one if the caller passed none.
  - `NetworkTask.execute(worker)` starts the worker thread and waits
    `initialWait`.
  - If the worker is still running, it shows a `ProgressDialog` with an
    **Abort** button.
  - When the dialog closes, it closes the registered input/output streams to
    break any blocked I/O. It then waits another 15 s and finally calls
    `Thread.stop()`.
- **Logging.** If `DiagnosticsLog.out` is open (`work/diagnostics.txt`), every
  request is logged with a UTC timestamp: the URL, the full body including
  `auth` hashes, and the full response.

Multipart bodies are built by `ServerSession.encodeMultipart(boundary)`. The
boundary string is simply the operation name (`submit`, `backup`, `loginUser`,
and so on). Each part is:

```
--<boundary>
Content-Disposition: form-data; name="<field>"

<value>
```

and the body ends with `--<boundary>--`.

## 4. Authentication and signing

The protocol is a home-grown variant of HTTP Digest, carried in form fields.

1. **Get a nonce.** `openSession` → `requestNonce` POSTs the form
   `user=<serverCredentials.user>` to `logic_nonce`. The reply must have
   `error status` = 0 and a `nonce` row. The result is a
   `ServerSession(credentials, nonce)`. On failure the user is asked
   "Retry the operation?" (`askRetry`, message `not037`), and the call loops.
2. **Sign each request.** The caller sets its parameters with
   `session.setParams(table, "f1.f2.f3")`. The dotted string is the list of
   fields that are covered by the digest. Encoding then:
   - increments `nonceCount`;
   - sets `nc` = 8 lowercase hex digits of `nonceCount`;
   - creates a fresh `cnonce = md5hex(currentTimeMillis + ":" + nc(counter))`;
   - appends `user`, `nonce`, `nc`, `dgst=<f1.f2.f3>`, `cnonce` and

     ```
     auth = md5hex(user ":" nonce ":" nc ":" password ":" md5hex(v(f1) v(f2) v(f3)...))
     ```

     Here `v(f)` is the parameter's value with whitespace trimmed, and the
     values are concatenated with no separators. A `byte[]` value is hashed raw
     and a `File` is hashed as its base64 text. `md5hex` is lowercase.
3. **Verify the reply.** `verifyResponseSignature` requires three reply rows:
   - `dgst`, a dotted list of row keys;
   - `cnonce`, which must equal the client's cnonce, or the call fails with
     "client nonce mismatch";
   - `auth`, which must equal

     ```
     md5hex(cnonce ":" password ":" md5hex(concat of columns 1..n of each dgst row))
     ```

     Otherwise the call fails with "signature mismatch".
4. **Error status.** Every reply carries `error status`
   (`getErrorStatus`). The values are:
   - `0` means OK.
   - `-1` means a stale nonce. `repostWithNewNonce` then refreshes the nonce
     (`refreshNonce`, which is another `Nonce` POST), re-signs and re-posts the
     same parameters, and `checkAuthStatus` interprets the new status. A
     second `-1` is "repeated nonce failure".
   - `-2` is "internal authentication failure".
   - Any other non-zero value is reported as `status = N`.
5. **Close the session.** `closeSession` releases the nonce. It POSTs the form
   `user, nonce, auth=md5hex(user:nonce:password)` to `logic_nonce` again. Every
   high-level operation opens a session, does its work and closes it.

Errors are stored in the request's `ResponseHandler`:
`reportError(text, h)` does `h.setError("not008", {commErrorMsg: text})`. The
retry loops show that message together with "Retry the operation?". Nearly
every operation `xxx` is therefore written as
`do { r = xxxOnce(...) } while (failed && askRetry(h))`.

### Response format

The server answers with HTML. `parseResponseTable` hands the text to
`HtmlTableParser.parseTable`:

1. It finds the first `<table>…</table>`.
2. For each `<tr>`/`<td>` it creates a cell. `colspan`/`rowspan` are honoured
   with a linked-list packing (`C_i_F.m1862`), so cells land in the correct grid
   column.
3. The cell text is the content with every inner tag removed and the entities
   `&quot; &amp; &lt; &gt; &nbsp; &#N;` decoded (`HtmlTag.decodeEntities`).
4. `toGrid` returns `Object[rows][cols]`.

A value is looked up by matching column 0 case-insensitively (`findRow`) and
reading column 1 (`getResponseValue`/`getResponseInteger`). So a reply looks
like:

```html
<table><tr><td>error status</td><td>0</td></tr>
       <tr><td>nonce</td><td>…</td></tr> …</table>
```

## 5. Endpoints and their fields

The "boundary" column is the multipart boundary, which is also the operation
name. Fields in *italics* are sent but are not part of `dgst`.

| endpoint | boundary | parameters (dgst order) | reply rows used |
|---|---|---|---|
| Verify | `userExists` | `uid` (normalised student id), `institution` | `return_value` (1 = registered), `logic_user_uid` |
| User | `loginUser` | new user: `uid.password.institution.purpose`; known user: `first_name.middle_name.last_name.uid.password.email.institution.purpose` | status 0 means the password was accepted; `logic_user_uid` |
| Password | `changePassword` | `logic_user_uid.old_password.new_password` | status |
| Submission | `submit` | `logic_user_uid.logic_course_uid.evaluation.tproblem_md5.twork.problem_name.module.help_count.duration`, plus `.wave_token` and `.ip` when present | `dtTimestamp`, `logic_submission_uid` (both optional) |
| Backup | `backup` | `userid.sectionid.data.key` (+`.ip`). `sectionid` is always `0`, and `data` is the base64 work zip | status |
| Backup_Info | `backup_info` | `userid` | `backup count`; `backup_N` rows: col1 key, col2 date, col4 backup id |
| Restore | `restore` | `backup` (id) | `data` (base64 zip) |
| Delete_Backup | `delete_backup` | `backup` (id) | status |
| Load_Remote | `load_remote` | `logic_course_uid.arch` (exam check: `logic_course_uid`, *arch*) | `code version`, `text version`, `loader version`, `download URL`, `download text`, `update URL`, `update text`, `loader URL`, `admin version`, `admin url`, `admin text`, optional `wave token` |
| Course_Remote | `getRemoteCourses` | `arch` | `course count`; `course_N`: col1 institution, col2 term, col3 course, col4 course uid, col5 comment, col6 number (unused), col7 staff `"inst1;inst2:asst1;asst2"` |
| Get_User_Reln | `getUserCourseRelations` | `logic_user_uid.logic_course_uid` | `reln count`, `reln_N` (e.g. `student`, `instructor`, `developer`) |
| Add_User_Reln | `addUserCourseRelation` | `logic_user_uid.logic_course_uid.relation` | status. **The reply signature is not checked here.** |
| (upload) | `upload` | `logic_user_uid.logic_course_uid.problem_name.text.webtext.syntax.type.aux.num_answers[.answer_1…]` | `dtTimestamp`, `logic_inst_problem_uid` |
| (user info) | `getUserInfo` | `institution.studentID` | result discarded (stub) |
| (problem db) | `insert_problem` / `update_problem` / `delete_problem` | `problem_name.tProblem.tProblem_md5.tWeb_form_problem.common_name.comment.version.syntax`. The values are **SQL-quoted client-side** (`sqlQuote`: `'…'` with doubled quotes, `NULL` for null) | status |

Field details:

- `password`, `old_password` and `new_password` are never plaintext. They are
  `md5Base64(typed password)` (`PasswordEntry.hashPassword`).
- `purpose` is `LogicConstants.userPurposes[i]`, one of `create`, `verify` or
  `update`.
- `evaluation` is the problem state letter. `tproblem_md5` is the
  `md5Base64` of the trimmed problem statement, and `twork` is the entire
  `TaggedRecord` line of the student's work. `duration` and `help_count` come
  from the record.
- `ip` is the local IPv4 address in dotted form
  (`SocketLineClient.getLocalIpString`).
- `arch` is `LogicProgram.arch` (`macos`, `windows`, ...).

Surprises in the endpoint code:

- The nonce-retry branch of `saveProblemRecordOnce`/`deleteProblemRecordOnce`
  re-posts to `insertProblemUrl` with boundary `backup`, which is a
  copy-and-paste bug.
- `ServerUrl` has a bug in its `user:pass@` splitting (`substring(i)` instead of
  `substring(0, i)`). This doesn't matter because the user-info part is thrown
  away.

## 6. Identity: users, passwords, courses

**`user.txt`.** `UserInfo` is a `Hashtable`. It keeps key order in `keyOrder`
and a `dirty` flag. The file format is version 2: the first line is `2`, then
one `key:value` line per field (`firstName`, `midName`, `lastName`,
`studentID`, `email`, `institution`, `term`, `className`, `ident`, and
`backupKey` when set). Versions 0 and 1 are older positional formats and can
still be read. `NewUserInfo` is a transient variant that only asks for a student
ID and is never saved.

**Work-file signature.** `UserInfo.computeDigest` builds an MD5 over:

1. `"<full name> (<sid>@<institution>)\n"`;
2. each line followed by `\n`;
3. the password of the `digest` credentials.

The digest is base64-encoded. It is the `# <digest>` trailer that guards
`work/*work.txt` against hand editing.

**Institutions.**

- `Institution.forName` returns one of the built-in `StandardInstitution`s
  (UCLA, UCSB, UCSD, Yale, SUNYSB, BOUN). An unknown name creates a new one on
  the fly. `TmuInstitution` is defined but is not in the built-in table.
- Terms are coded as `<year><season>`, for example `2026F`. The seasons are
  `W S SP SS SSA SSB SSC F`.
  - `parseTerm` gives a `TermCode(year, seasonIndex)`.
  - `formatTermName` gives "Fall 2026".
- `normalizeStudentId` removes tabs, spaces and dashes.

**Courses.**

- `CourseInfo.getAllCourses` fetches `Course_Remote` lazily. The list is sorted
  by institution, then term (`TermCode.compareTerm`), then course, using the
  comparator `C_d_B`, whose original error text calls it "LPLogicCourseOrder".
- `findCourses(inst[, term[, course]])` filters the list.
  `findCoursesFor(user)` falls back from course to term to institution, and for
  a new user to all courses.
- `CourseChooserPanel` shows three cascading choice boxes and the
  instructors/assistants (or the comment) of the selected course.
- `AccountManager.chooseCourse` writes the choice back into the user
  (`institution`/`term`/`className`, `courseUid`).

**Login (`LoginResult.login` → `AccountManager.promptForPassword`).**

1. `verifyUser` (Verify) asks whether the student ID is registered.
   - A new user who is not registered gets error `not028`.
   - A known user who is not registered gets the **Create password** dialog,
     then `loginUser` with purpose `create`.
2. For a registered user, a password dialog appears with OK / Cancel /
   **Change**.
   - Change runs `changePassword`: `loginUser` with purpose `update` and the
     old password, then the Password endpoint.
3. `loginUser` with purpose `verify`. If the status is non-zero, the user may
   re-enter the password once (`retryLogin`). After that the result is
   "Authorization Failed".
   - If `developer` credentials exist, a developer can override the check,
     which then reduces to `verifyUser`.
4. On success `user.userUid` is set. The resulting `LoginResult` carries the
   password hash, which is kept as `BackupRequest.passwordHash` and
   `Submission.passwordHash`. It is only checked for non-null and is never sent.

**Relations.**

- `getUserRelations` is cached in `UserInfo.relations`.
  `hasRelation("student")` also accepts `instructor`, and `instructor` also
  accepts `developer`.
- `UserSetup.checkAccess(credName, relation)` gates privileged operations:
  - Online, it asks a privileged person to identify themselves
    (`identifyPrivilegedUser`: student-ID dialog, password, login) and checks
    the relation on the server.
  - Offline, it prompts for the local password of the named credentials, which
    must be at least 6 characters.
  - Successes are cached for the session.

## 7. Submissions

`LPxxx.submit` does the following:

1. `ServerConnection.prepareSubmission` opens a session, logs in and confirms
   the course (`not048`). It returns a `Submission(courseUid, userUid,
   session, pwHash)`.
2. For each selected problem, the module fills the fields and calls `submit`,
   which retries `submitOnce`.
3. The names of successful and failed problems go into `succeededNames`/`failedNames`, and
   `AccountManager.showSubmissionResults` lists them.
4. If `logSubmit` is on, `getLogRecord()` (`uid:ip:timestamp`) is appended to
   `work/<mod>data.txt`.
5. `finishSubmission` closes the session.

At exam start, `LogicProgram.m964` submits a dummy submission named
`exam start` with module `nul` and evaluation `X`.

## 8. Backups, restore and the work folder

- **Zipping the work.** `zipWork` zips `WORK_FILES` (`user.txt`, `prefs.txt` and
  the seven `*work.txt` files) and `DATA_FILES` (`*data.txt`). They are stored
  under a `work/` directory entry at deflate level 9. `zipWorkToBase64`
  produces the `data` field.
- **Backing up.** `backupWork(key)` runs these steps:
  1. `prepareBackup` (login).
  2. Backup.
  3. `fetchBackupInfo`.
  4. `pruneBackups(max)`. While more than `max` backups exist for this key, it
     selects the oldest (by date string) and calls `deleteBackup`.
- **Restoring.** `restoreWork(restoreKey, backupKey, newUser)` runs these steps:
  1. It logs in and fetches the backup list.
  2. It chooses a key. It uses `backupKey` if that key has backups. Otherwise
     it falls back to `restoreKey`; in exam mode that needs the `restore`
     password.
  3. The user picks a backup in `chooseBackup` (newest preselected).
  4. It downloads the backup and unzips it into `configDir`.
     - Only `work/<name>` entries whose name is in `WORK_FILES` are written. A
       legacy `logic/` prefix is rewritten to `work/`.
     - When restoring from the fallback `restoreKey`, only `user.txt` and
       `prefs.txt` are restored.
- **Initial backup.** In homework mode (`backupKey == "work"`) at startup,
  `ensureInitialBackup` makes sure the user has the `student` relation. If no
  backup exists yet for the key, it uploads an initial one.
- **Copying.** `copyWork` copies the work folder by zipping it to base64 and
  unzipping it at the destination. `deleteWork` deletes the files and reports
  any that remain.

## 9. The update mechanism

`checkForUpdates(user)` runs at startup. It POSTs `Load_Remote` with the user's
course uid and `arch`, then decides as follows. `isUpToDate(remote, local,
lenient)` is a string comparison: when `lenient` is set and neither version ends
in `x`, remote ≤ local passes; otherwise the two must be equal.

1. **Core update.** This applies once per run and only when not running from an
   IDE. If the `code version` is not up to date with
   `LogicProgram.codeVersion`, then:
   - If `download text` is an http(s) URL, the method is `METHOD_MANUAL`. The
     dialog `not084`/`not085` offers Backup / "Update Instructions" (which
     opens that URL in a browser) / Quit.
   - Otherwise the method is `METHOD_LOADER`. The dialog `not033` offers
     Update / Quit. If work exists and has not been backed up, it uses `not046`
     instead and forces a backup first (the dialog repeats while the backup
     fails, result 5).
2. **Local (text) update.** If the user's course differs from the configured
   one, or the `text version` is behind `textVersion`, the method is
   `METHOD_DIRECT` with `update URL`. The dialog is `not034` or `not047`.
3. **Instructor version.** This applies when `admin url` is present, and either
   this is an admin install or the user is an instructor who answers "Now" to
   `not060`. If the admin course or `admin version` is stale, the method is
   `METHOD_DIRECT` with `admin url`. The dialog is `not036` or `not049`.

`performUpdate` then does one of the following:

- **`METHOD_DIRECT`.** `HttpDownloader.download(url, localUpdate ? configDir :
  rootDir)`. Because the target is a directory, the stream is unzipped in
  place. The result is 2, and the program restarts itself with the same user.
  Note that `downloadDirect` only accepts the `http` protocol.
- **`METHOD_LOADER`.**
  1. `fetchLoaderJar` reads `LPUpdateLoader.version` from `progDir/loader.jar`
     through a `JarClassLoader`. If the jar is missing or older than
     `loader version`, it downloads `loader URL` over it.
  2. `writeLoadInfo` writes `progDir/loadinfo.txt` with these keys: `source`
     (the download URL), `configDir`, `rootDir`, `progDir`, `linkDir`, `arch`,
     `type` (`core` or `local`), `soloPort` (the `SingleInstanceGuard` port,
     unless the override `SoloCheck` is `no`), `workDeleted:`,
     `needBackup:true|false`, `institution`, `term` and `course`.
  3. `launchLoader` runs `java -Dload.info=<loadinfo> -jar loader.jar` in
     `configDir`. It uses `javaw` on Windows and `$java.home/bin/java` on
     macOS.
  4. `ruleDir/text/*` is moved to `progDir/trash`, and the trash is emptied.
  5. The result is 3, and the program exits.

  `loader.jar` (`LPUpdateLoader`) then takes over:
  1. It waits for the program to release its TCPSolo port (password `cogito`).
  2. It rewrites `source` to end in `.zip`.
  3. It downloads and unzips that into `configDir` (for `local`) or `rootDir`
     (for `core`).
  4. It relaunches `java -Dconfig.dir=… -Dlink.dir=… -Dprog.dir=… -Droot.dir=…
     -jar progDir/logic.jar`, or `open <rootDir>` on macOS.

The optional `wave token` from Load_Remote is remembered in
`ServerConnection.waveToken` and added to every later submission.

When the program runs with `exam` credentials, `chooseCourse` first calls
`isExamVersionCurrent`. This compares Load_Remote's `text version` (or
`admin version` on admin installs) with the local one.

## 10. Notable details

- The server authenticates the **installation** through the shared `logic`
  credentials hidden in `wraith.txt`. The student is authenticated only by the
  hashed password field inside a signed request. Anyone with the options file
  can sign requests.
- Request signing covers only the fields listed in `dgst`. The transport sends
  `Content-MD5`, but nothing verifies it on the client side.
- `HttpDigestAuth` is a complete RFC 2617 client (Basic, Digest, MD5-sess,
  auth-int, `rspauth` checking). The SMTP/POP3 socket client `MailSocketClient`
  and the `httpGet`, `fetchUserInfo`, `saveWorkZip` and `restoreFromWorkZip`
  helpers are unused leftovers.
- Every UI string above (`notNNN`) lives in the scrambled message catalogue
  `spectre.txt`. `tools/unscramble.py` decodes it.
