# Logic 2010: architecture overview

This overview comes from the survey phase. Class names are the ones in
`mappings/core.mapping`. The placeholder name appears in parentheses the first
time a class is mentioned. Classes that do not have a real name yet are listed
by placeholder only.

## 1. Big picture

Logic 2010 is a single-process Swing application, `edu.ucla.phil.logic.*`, with
about 390 top-level classes. The code is written in an old Java 1.1/1.2 style:
it uses `Vector`, `Hashtable` and a lot of static state. It has three layers:

1. **Program core.** `LogicProgram` handles startup, configuration, links and
   options. It holds almost everything in static fields. `MainMenu` (`C_z_C`) is
   the launcher window. Around them sit the shared data plumbing (scrambled
   files, tagged records, messages) and the module and problem-set framework.
2. **Six modules.** These are `LPDerivation`, `LPInvalidation`, `LPParsing`,
   `LPRecognition`, `LPSymbolizer` and `LPTruthAnalysis`. Each one is a
   `LogicModule` (`C_U`) panel shown inside its own `ModuleFrame` (`C_0E`, a
   `JFrame`).
3. **Logic engine.** `Expression` (`C_RF`) is the formula tree. It is built by
   two JavaCC parsers, one for each notation ("syntax 1" and "syntax 2"). Rules,
   theorems and scheme matching (`SchemeInstantiation`) sit on top of it and
   drive derivation checking.

A single `ServerConnection` (`C_KC`) handles all network traffic. It talks HTTP
to `https://logiclx.humnet.ucla.edu/Logic/Desktop/*` for user verification,
submissions, backups and updates.

## 2. Startup

`LogicProgram.main` runs `LogicProgram$C__A` on the EDT. That runnable does the
following:

1. It tries to install the SwingHelper `CheckThreadViolationRepaintManager`. The
   "Could not load thread violation checker" message comes from this step and
   is harmless. It then sets the cross-platform look and feel.
2. `loadIconsAndFonts` (m960) loads `images/Logic2010_*.png` and the M+ fonts
   from the jar.
3. `initialize` (m961) does most of the work:
   - It reads the system properties `config.dir` (default `user.dir`),
     `link.dir`, `prog.dir`, `root.dir`, `copy.dir`, `fail.dir` and `from.ide`.
     `run.sh` sets the first four.
   - It sets `configDir/override.txt` (`OverrideSettings`, `C_t_A`),
     `configDir/prefs.txt` and `configDir/work/prefs.txt` (`PreferencesFile`,
     `C_w_B`), and `work/user.txt`.
   - `readCoreInfo` (m1071) reads `coreinfo.txt`. If that file is missing it
     reads `spirit.txt`, which is scrambled. Finding `spirit.txt` switches the
     whole program into "scrambled" mode: `LogicProgram.scrambleKey` (f537) is
     set to the default key.
   - `readLinks` (m1072) reads `links.txt`, or `ghost.txt` in scrambled mode. It
     is a key:value table (`LogicProgram.links`, f535). The `syntax:` entry calls
     `setSyntax` (m1061), which chooses the symbol tables, letter sets and
     `ruleDir` (`syntax1/` or `syntax2/`).
   - It loads the global message catalogue (`Message.loadMessages`, `C_H`).
   - It reads the options file (`options:` link, `wraith.txt`) and the local
     options (`local/wraith.txt`) with `readOptions` (m1056).
   - It starts `SingleInstanceGuard` (`C_m_`), a small "TCPSolo" server socket,
     so that only one copy runs. This step is skipped on macOS.
   - `ServerConnection.readDatabaseLinks` (m802) resolves the institution, term,
     course and text version, and all server URLs.
4. `initializeUser` (m962) loads `work/user.txt` into `UserInfo` (`C_OE`). If
   the file is missing it creates `NewUserInfo` (`C_HB`) and runs the dialogs in
   `UserSetup` (`C_u_C`) and `AccountManager` (`C_j_C`). It can then verify the
   user and course with the server and check for updates
   (`ServerConnection.m862/m864`). The updater downloads `loader.jar` and hands
   off to `edu.ucla.phil.logic.LPUpdateLoader`, which lives in the separate
   `loader.jar`.
5. `loadRulesAndTheorems` (m963) parses `theorems` (`fiend.txt`) into
   `TheoremTable` (`C_z_`) and `rules` (`banshee.txt`) into `RuleTable`
   (`C_z_B`).
6. Finally `LogicProgram.mainMenu = MainMenu.m2207(...)` opens the launcher
   window.

`ProgramLauncher` (`C_UC`) is an alternative entry point. It reads
`maps.txt`/`paths.txt` and re-executes `java ... edu.ucla.phil.logic.LogicProgram`
with the right `-D` flags. This is how the old Windows and floppy installs
started the program; see `mapsa.txt` and friends.

## 3. File layout and the scrambled data files

In the macOS bundle layout (`/data/logic2010/Contents/Resources`, which is
`config.dir` and `link.dir`), the files are:

| file | link key | content |
|---|---|---|
| `spirit.txt` | (core info) | `version:`, `arch:` |
| `ghost.txt` | (links) | the links table: institution/term/course/ident/syntax/version, directory aliases (`progDir`, `linkDir`, `ruleDir`, `adminDir`, `nonetDir`, `localDir`), data file names, PDF docs, web pages and **all server URLs** (`logic_nonce`, `logic_verify`, `logic_submission`, `logic_backup`, ...) |
| `wraith.txt` | `options` | option records (`logic`, `derivation`, ...). This includes the server credential `logic`$`logic:<base64>`u |
| `spectre.txt` | `messages` | global message catalogue |
| `zombie.txt`, `tomb.txt`, `shade.txt`, `demon.txt`, `crypt.txt`, `troll.txt` | `derMessages`, `invMessages`, `parMessages`, `symMessages`, `truMessages`, `recMessages` | per-module message catalogues |
| `imp.txt` | `tips` | tips |
| `maps*.txt` | | launcher path maps (plain text) |
| `syntax1/`, `syntax2/` | `ruleDir` | per-notation problem and rule files: `ghoul` = derivation problems (`derwork.txt:`), `werewolf` = invalidity, `vampire` = parsing, `devil` = symbolization, `warlock` = truth tables, `goblin` = recognition, `mummy` = symbolization answer key (`symAnswers`), `banshee` = rules, `fiend` = theorems. `syntax2/text/` holds the textbook PDFs. |
| `local/` | `localDir` | instructor-local overrides: problem files with the same names plus a local `wraith.txt` options file |
| `work/` | | the student's data, in plain text: `user.txt`, `prefs.txt`, `derwork.txt` ... `truwork.txt`, `keywork.txt`, `*data.txt` logs, `diagnostics.txt` (HTTP trace when enabled, from `DiagnosticsLog`, `C_k_C`), `errors.txt` |
| `docs/*.pdf` | `menuHelp`, `derHelp`, ... | help PDFs, opened in an external viewer |

**Decoding.** The scrambled files are line-oriented text in a running-key
cipher over a 96-character alphabet (TAB, space and ASCII 0x21-0x7E). Other
characters pass through unchanged. The code is in `Scrambler` (`C_z_D`):
`unscramble(String, key)` (m2220) and `scramble` (m2218). `ScrambledReader`
(`C_XB`) is a `BufferedReader` whose `readLine()` calls `unscramble`, and
`PlainRecordReader` (`C_b_D`) is the same class with a null key. The key is
`DEFAULT_KEY` = `"the Logic Program is protected by international copyright law"`.

The decoder works like this. Let `A` be the alphabet, `N = 96` and `k = 0` at
the start of each line. For each position `l`:

- If `key[l % len]` is in `A`, add its index to `k`.
- If the ciphertext char `c` is in `A`, output `A[N-1 - (k - idx(c) + N-1) % N]`
  and then set `k = idx(c)`.

So each output character depends on the key and on the previous ciphertext
character. A 15-line Python port is in the survey scratchpad; it decodes every
`*.txt` above correctly.

The server credential in the options file is decoded as
`unscramble(base64decode(x))`; see `LogicProgram.readOptions`.

`Scrambler` also holds the MD5 helpers (`md5Hex`/`md5Base64`, which use
`Md5OutputStream` `C_KD`, `HexEncoder` `C_m_C` and `Base64Codec` `C_o_B`). These
are used for server authentication, problem digests and work-file signatures.

**Record format.** Problem, work, option and message files all use
`TaggedRecord` (`C_XD`) lines. A line is a sequence of `value` + "`" +
tag-character pairs, for example:

```
Deriv 1.001`$~Q .: (P->Q)->~P`-`=Deriv 1.001`C
```

The first field is the name. Tags such as `$` (statement), `-`, `=`, `C`, `?`,
`n`, `e`, `i` and `x` have meanings that depend on the file. Literal backquotes
are doubled. Lines that start with `#` are comments, and `#-` lines are headings
shown in problem lists. Messages use `n` (id), `e`/`i` (error or info text), `x`
(explanation) and `b` (buttons). `Message.parseMessages` (m658) builds the
catalogue from them.

## 4. Modules

`ModuleConstants` (`C_XC`) defines the module indexes: der=0, inv=1, par=2,
rec=3, sym=4, tru=5. It also defines `moduleAbbrs` ("Deriv", "Inval", ...),
`moduleNames`, `moduleWorks` (`derwork.txt`, ...) and `moduleClasses`.

The framework uses **reflection by name**. For this reason the LP* classes keep
their original member names.

- `LogicModule.staticCheck` checks that each module class declares the static
  fields `instances`, `exercises`, `digestVersKey`, `monoProbs` and
  `messageClass`, and the static methods `startup(Rectangle, BusyIndicator,
  String)`, `checkQuit(Hashtable, Hashtable)`, `readExercises(boolean, boolean,
  boolean)` and `readWork()`.
- `LogicModule.getExercises/getWork/getStaticField/setStaticField` call these
  members reflectively.
- Each module's `messageClass` is a `Message` subclass with its own `messages`
  table and `linkName`: `C_n_` (derMessages), `C_LA` (inv), `C_ND` (par),
  `C_BF` (rec), `C_h_E` (sym) and `C_FE` (tru).

Each module is built from the same set of parts:

- the `LPxxx extends LogicModule` panel, which holds the static problem list and
  one instance per open problem;
- a `ProblemSet` (`C_e_D`) subclass: `C_MD` der, `C_PC` inv, `C_ZC` par, `C_j_A`
  rec, `C_h_C` sym, `C_O` tru;
- a `ProblemEntry` (`C_f_F`) subclass: `C_EE`, `C_u_D`, `C_l_D`, `C_ED`, `C__C`,
  `C_f_C`. An entry has a name and a state N/I/C/I/U, which the submission
  reports as the `evaluation`;
- a static "dialogs" helper for problem choosing, deleting and saving: `C_KB`
  der, `C_CE` inv, `C_KA` par, `C_EC` rec, `C_WB` sym (the Answer Manager),
  `C_w_A` tru;
- a toolbar or button panel with Check, Close, Delete, Help and so on: `C_PE`,
  `C_IB`, `C_RB`, `C_h_`, `C_b_B`, `C_u_E`;
- page printers, which are subclasses of `C_CA` in the `C_l_B` printing
  framework.

Problem availability and restrictions come from options such as
`derivation`$noCheck:{"1.7",...}`?`. These are parsed into `ProblemSelector`
(`C_BE`) sets and tested with `LogicProgram.m1060`.

## 5. Formula representation

`Expression` (`C_RF`) is an abstract n-ary tree node. It has `kind` (f738),
`symbol` (f739), `children` (f740, a `Vector`) and `childCount` (f741).
`ExpressionKinds` (`C_a_D`) defines the kind codes:

| kind | class | meaning |
|---|---|---|
| 0 | `AtomicFormula` (`C_q_A`) | sentence or predicate letter applied to terms |
| 1 | `QuantifiedFormula` (`C_o_A`) | `@x` (all) / `!x` (some): children = variable, body |
| 2 | `ConnectiveFormula` (`C_q_F`) | `~ & \| -> <->` |
| 3 | `SimpleTerm` (`C_i_`) | variable or name; can point to its binding quantifier |
| 4 | `OperationTerm` (`C_n_C`) | operation symbol applied to terms |
| 5 | `DescriptionTerm` (`C_WE`) | definite description |
| 6 | `IdentityFormula` (`C_x_D`) | `=`; `<>` is parsed as `~(a=b)` |
| 7 | `MembershipFormula` (`C_t_D`) | `[m]` (∊) |

`Term` (`C_X`) and `Formula` (`C_y_A`) are the two abstract intermediate
classes. `ExpressionPath` (`C_e_`) addresses a subexpression by a list of child
indexes. Internally formulas are ASCII ("Maggie-talk": `@ ! % ~ & | -> <-> <>
[m] .: \`). They are rendered with the symbol tables in `LogicConstants`
(`C_n_A`), which include `kaplan1..7` (Unicode and private font code points) and
`html1..3`.

**Parsing.** `FormulaParser` (`C_FB`) is a static front end that picks one of
two JavaCC-generated parsers. `syntax1` is syntax 1 (`Syntax1Parser` ...) and
`syntax2` is syntax 2 (`Syntax2Parser` ...). Each package has the usual JavaCC
classes: Parser, TokenManager, SimpleCharStream, ParseException,
TokenMgrError, Constants and Token. The two copies are almost identical. They
differ in their letter conventions: syntax 1 uses operation letters `ABCDE` and
variables `a-z`, while syntax 2 uses operation letters `a-h` and variables
`i-z`.

**Schemes and rules.**

- `SchemeInstantiation` (`C_j_D`) maps a `SchematicLetter` (`C_i_A`: term,
  operation or predicate letter keys `C_PB`/`C_W`/`C_w_C`) to a
  `LetterReplacement` (`C_GF`).
- `Expression.instantiate` (m1238/m1240) and the `m1266..m1269` matchers use it
  to apply and recognize rule schemata.
- A `Rule` (`C_VB`) may be compound, for example `DN = DNE.DNI`.
  `SchematicRule` (`C_LF`) holds premise and conclusion schemata. `Theorem`
  (`C_QE`) is numbered.
- `ArgumentParser` (`C_VC`) splits `P. Q .: R` into premises and conclusion.

## 6. Derivations

The derivation module shows nested `DerivationBox` (`C__`) components, one per
(sub)derivation. Each box has a "show" line and a body of `DerivationLine`
(`C_G`) rows; both classes implement `DerivationNode` (`C_0B`). Every line has
an annotation, a `Justification` (`C_HD`), which is serialized as `n:...`.
The types are:

- 1 = rule application `C_HF`
- 2 = `C_l_`
- 3 = assumption `C_p_E`
- 4 = `C_GA`, with a condition or inner rule

`DerivationLineChecker` (`C_a_`, 2.2k lines) checks each line against the rules,
theorems and options, and reports `dererrNNN` messages. `C_KB` holds the rule
chooser and the other derivation dialogs.

## 7. Problems, work and submission

- **Problem sources.** Each module's `readExercises` reads base problems from
  `ruleDir/<file>` (scrambled). It then merges `local/<file>` and "database"
  (remote course) problems through `insertDBProbs`/`updateDBProbs`. The
  problems go into the module's `ProblemSet`.
- **Work.** `readWork`/`LogicModule.writeProblems` save the student's attempts
  to `work/<mod>work.txt` as plain `TaggedRecord` lines. The file ends with a
  `# <digest>` line from `UserInfo.m1148`, keyed by `digestVersKey`. This is an
  MD5 signature over the lines and the user, which detects hand editing.
  `work/<mod>data.txt` logs submissions when `logSubmit` is on.
- **Authentication.** `ServerConnection.openSession` (m829) POSTs
  `user=logic` to `logic_nonce` and gets back a nonce. The server returns an
  HTML `<TABLE>` of key/value rows, which `parseResponseTable` (m817) parses
  with `C_FA`/`C_r_`. After that, every request is a `ServerSession` (`C_0C`)
  form or multipart POST. It carries the parameters and
  `user, nonce, nc, cnonce, dgst=<field list>` together with
  `auth = md5hex(user:nonce:nc:password:md5hex(fields...))`.
- **Submission.** In `LPxxx.submit` → `ServerConnection.prepareSubmission`
  (m835) / `submit` (m836) / `submitOnce` (m837), a `Submission` (`C_0A`) is
  posted to `logic_submission`. It carries `logic_user_uid`,
  `logic_course_uid`, `evaluation` (N/I/C/U state), `tproblem_md5` (MD5 of the
  problem statement), `twork` (the whole work record), `problem_name`, `module`,
  `help_count`, `duration` and `ip`. The server replies with
  `dtTimestamp`/`logic_submission_uid`.
- **Other server calls.** Backups, restores and backup info (`work.zip`, the
  `C_XA` requests) have their own endpoints. So do user and site verification
  (`logic_verify`, `logic_user`, `logic_site`), password changes, course
  relations and remote problem loading. `AccountManager` also uploads
  instructor problems (`C_LD`). HTTP Digest/Basic auth (`HttpDigestAuth`,
  `C_N`) exists for protected downloads. Long calls run as a `NetworkTask`
  (`C_r_A`), which is abortable and shows a progress dialog, while a
  `BusyIndicator` (`C_x_A`) is active.

## 8. UI structure

- **Top level.**
  - `MainMenu` (`C_z_C`) is itself a `LogicModule`. It has one button per module
    plus Help, Assignments/Website and User/Backup/Copy/Quit.
  - Each module opens in a `ModuleFrame`. `ProblemEditorFrame` (`C_d_A`) is
    used for user problems. `ProblemListView` (`C_YA`, a `JList`) lists
    problems.
- **Panels.**
  - `CellPanel` (`C_TA`) is the base `JPanel`. Through `CellRenderable`
    (`C_AF`) it can act as a list cell and recolor its children.
  - `SizedPanel` (`C_LB`) is a `JPanel` with a fixed preferred size.
  - There are many custom `LayoutManager`s (`C_QF`, `C_m_A`, `C_DC`, ...).
- **Formula entry.**
  - `StyledTextPane` (`C_e_E`) → `EditableTextPane` (`C_p_A`, with undo) →
    `FormulaTextPane` (`C_s_D`, which maps Mac Option-key characters to
    symbols) → `FormulaEntryField` (`C_IF`, Ctrl+Shift+letter shortcuts).
  - `KeypadDialog` (`C_T`) popups provide clickable symbol keypads: `C_a_E` in
    general and `C_BA` for derivations.
- **Messages.**
  - `MessageDialog.showMessage` (`C_UA`, m1328/m1329) shows a `Message` with
    `<param>` substitution.
  - The dialog buttons are described by a `DialogHandler` (`C_D`) spec,
    `"label:action;default"`. Subclasses of `C_D` implement the actions.
  - `ProgressDialog` (`C_SD`) is the splash and progress box.
- **Colors.** The UCLA "bruin" colors come from `LogicConstants`. A monochrome
  "overhead" palette is also available.
- **Printing.** `C_l_B` is a print-queue thread, `C_CA` is the page base class
  and `C_JE` is the page component.

## 9. Surprises and notes

- The two parser packages are near-duplicate JavaCC outputs. Once one is named,
  the other can be named mechanically.
- The "encryption" is only a keyed Vigenère-like scramble with a hard-coded key.
  The file names (`ghost`, `spirit`, `zombie`, ...) are deliberately
  meaningless, and `ghost.txt` maps logical names to them.
- Much of the state is global and static: `LogicProgram.*`,
  `ServerConnection.*` and each module's static problem list. The modules find
  each other by reflection.
- Some leftover classes have no callers, for example `C_EF` and `C_YD`. They are
  probably dead code.
