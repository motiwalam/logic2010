# Program core, messages and the module / problem-set framework

This document covers the "app" group: `LogicProgram` and its helpers, the
launcher and settings files, the data-file plumbing (`Scrambler`,
`TaggedRecord`, `DelimitedTokenizer`), messages and dialogs, and the framework
that the six LP* modules are built on (`LogicModule`, `ModuleFrame`,
`ProblemSet`, `ProblemEntry`, `ProblemSelector`, `ProblemListView`). Names are
the ones in `mappings/core.mapping` and `mappings/app.mapping`.

## 1. LogicProgram: a static program core

`LogicProgram` has no instances that matter; nearly all of its state is in
static fields, and every other class reaches it directly.

**Startup.** `main` posts a `ProgramStartup` (`LogicProgram$_A`) runnable to the
EDT. That runnable installs the optional thread-violation repaint manager,
sets the cross-platform look and feel, calls `loadIconsAndFonts`, and then
loops:

1. `initialize()` returns 0 (ok), 1 (fatal) or 2 (retry). It resolves the
   directories (`configDir`, `workDir`, `linkDir`, `progDir`, `rootDir`,
   `copyDir`, `trashDir`, `failDir`, `windowsDir`), checks that `configDir` is
   writable (`isWritableDirectory` writes, reads back and deletes
   `tempest.txt`), and reads `override.txt` and the two `prefs.txt` files.
   It then reads core info and links (see the architecture overview), loads
   the global message catalogue and the options, applies font-size and colour
   preferences (`applyPrefsFontSize`, `applyOverrideFontSize`,
   `applyColorPrefs`), and starts the `SingleInstanceGuard` on `soloPort`.
2. `initializeUser()` returns 0 (ok), 1/3 (quit) or loops. It is a long state
   machine over "does `work/user.txt` exist", "remote mode", "exam
   credentials" and "demo mode". It calls into `UserSetup`, `AccountManager`
   and `ServerConnection`. `checkUserCourse` compares the stored course with
   the server's, and `readCourseFromLoadInfo` pre-fills a new user from
   `loadinfo.txt` (the file written by an update before a restart).
3. `loadRulesAndTheorems()`, then for exam users `submitExamStart`, which posts
   a dummy `Submission` named "exam start" with evaluation `X`.
4. `MainMenu.open(null)` creates the launcher, and `showHeadlines(true)` opens
   the "headlines" document in the external word processor if its version
   (`headVers` link) is newer than the one recorded in the user file.

`reinitializing`/`reinitUser` let `ServerConnection` restart the whole
sequence after the links change (for example after the user picks a different
course installation), without re-reading the directory properties.

**Options.** `readOptions` reads the `logic` record of the options file. Tag
meanings:

| tag | effect |
|---|---|
| `+` | flags: `debug`, `noprint`, `altsymbols`, `overhead`, `remote`, `nonet`, `hidden`, `noCoreProbs`, `repeatAuth`, `hideSensitive` |
| `u` | server credentials `user:base64(scrambled password)` (`addCredentials`) |
| `f` | font size, either absolute or a fraction `n/d` of the screen-derived default (`parseFontSize`) |
| `c` | `maxBackups`, the number of server backups to keep |
| `b` / `r` | `backupName` / `restoreName` for server backups |
| `p` | `soloPort`, the single-instance port |
| `o` | stored in `optionO`, never read |

`resetOptions` clears all of these first, so the base options file and the
local `local/wraith.txt` are layered.

**Symbols and letters.** Formulas are stored in ASCII "maggie" notation.
`translateSymbols(s, from, to)` is a longest-leftmost substitution between two
parallel symbol tables (for example `maggie` -> `symbols`/kaplan). The overload
with an `int[]` shifts a list of character positions as the text grows or
shrinks (`shiftPositions`); this is how parser error columns are mapped from
the ASCII input to what the user sees. Text of the form `{n}` is skipped
(`findNumberedPlaceholder`). `translateToDocument` produces a Swing
`StyledDocument` and keeps highlighted ranges. `expandEscapes` interprets the
backslash escapes used in message texts: `\n` is a newline and `\l` toggles
"logic mode", in which the text is translated to logic symbols.

`setSyntax` switches between the two notations. It swaps the symbol tables
(`symbols`, `encodedSymbols`, `htmlSymbols`), the letter sets
(`sentenceLetters`, `predicateLetters`, `operationLetters`, `variableLetters`)
and `ruleDir`. `sentenceLetter(i)`, `variableLetter(i)` and the similar helpers
index into these sets modulo their length.

`parseFormula(s, allowTerm, allowPlaceholders, allowQuestionVars)` is the usual
entry point to the parsers. It runs `FormulaParser`, rejects `{n}` and `?ABC`
meta-variables unless they are allowed, runs a well-formedness check, and
rethrows lexer and parser errors with the column translated to the displayed
position.

**Files.** Data files are named by link keys, not by path:

- `openDataFile(key, preferWork[, scrambled])` tries `work/<key>` as plain text
  first, and otherwise the linked file (scrambled).
- `openLocalFile` reads the same file name from the local or edit directory.
- `openProblemFile` reads it from `ruleDir` or `configDir/local`.
- `appendSubmitLog` appends a scrambled `$name a<user> d<timestamp> w<md5>` record
  to a module's `*data.txt` log.
- `checkSubmitLog` sorts the problems chosen by a `ProblemSelector` into
  `missing`, `changed` and `handled` lists by comparing MD5s with the log. The
  main menu's quit summary is built from these lists.

**Misc.** `LogicProgram` also contains AWT helpers (`isDescendant`,
`boundsRelativeTo`, `findFrame`, `forwardKeyEvent`, which passes a key event to
the nearest `KeyListener` ancestor or clicks the default button on Enter),
Vector and Hashtable helpers (`flatten`, `mergeTables`, `indexOf`), file choosers
and debug logging. With `debug` set, `openDebugLogs` redirects `System.err` into
`work/errors.txt` through `ErrorLogStream` and opens `work/diagnostics.txt`.

## 2. Launcher, single instance and settings

- `ProgramLauncher` is a separate entry point for the old Windows installs.
  It reads a `paths.txt` or scrambled `maps.txt` file (`readPathFile`), fills in
  missing directories from `links.txt`/`ghost.txt` (`readLinkFile`), rewrites
  path prefixes when `progDir` moved (`rebase`), and then `exec`s
  `java -Dlink.dir=... -Dprog.dir=... [-Dcopy.dir=...] -cp <javaDir>
  edu.ucla.phil.logic.LogicProgram` in the work directory. `PathFileSpec` and
  `LaunchPaths` are its small value classes.
- `SingleInstanceGuard` ("TCPSolo") scans ports upward from `soloPort` until it
  can bind a `ServerSocket`. Each connection receives the signature line
  `cogito`. When a port is busy it connects to it (`probePort`); if the reply is
  the signature, another copy is running and startup stops.
- `OverrideSettings` (`override.txt`) and `PreferencesFile` (`prefs.txt`) are
  `Hashtable`s of `key:value` lines. `PreferencesFile` upper-cases keys for
  lookup and keeps the original spelling in `originalKeys` so that `save`
  writes it back unchanged.
- `LinkFileEditor` (`C_YE`) is a tiny command-line tool: argument 0 is
  `key:value`, and each later argument is a file in which the line starting with
  `key:` is replaced.

## 3. Data-file plumbing

`Scrambler` and `ScrambledReader` are described in the architecture overview.
`ALPHABET_INDEX` is a char -> index lookup table built by `buildIndexTable`.
`readScramblerMap` reads an optional `scrambler.txt` of `name:name` pairs; it
belongs to a development tool and is not used at runtime.

**TaggedRecord.** A record is parsed into two parallel lists: `tags` (a
`String`, one char per field) and `values`. Parsing scans for backquotes. The
character after a backquote is the tag of the text before it, and a doubled
backquote is a literal backquote. A leading backquote escapes a line that would
otherwise start with `#`. `toString` reverses this (`formatField`,
`escapeBackquotes`, `toLine`). The same object can also act as a reader: in
`open(reader, clearOnRead)`, each `readNext` skips blank and `#` lines and
parses the next one.

Tags with a fixed meaning across modules:

| tag | meaning | accessor |
|---|---|---|
| `$` | problem name | `getName` / `setName` / static `nameOf`, `withName` |
| `o` | original name of a copied problem | `getOriginalName` |
| `e` | error count | `getErrorCount` |
| `t` | timestamp | `getTimestamp`, `stripTimestamp` |
| `%` | `key:value` problem options, e.g. `eg` (example) and `hide` | `getKeyValues('%')`, `isExample`, `isHidden` |
| `!` | a note shown under the problem title | used by the modules |

**DelimitedTokenizer** takes a delimiter string whose first character is the
escape character (usually `\`). `nextToken` returns the text up to the next
unescaped delimiter, and `getDelimiter` reports which delimiter ended it.
`escape` inserts escape characters in front of delimiters. It is used for
message templates (`<param>`), button specs, and the `ProblemSelector` syntax.

## 4. Messages and dialogs

`Message.loadMessages` reads the global catalogue into `globalMessages`.
`loadModuleMessages(i)` loads a module's catalogue by reflection: it reads the
module's `messageClass`, then that class's `linkName`, and stores the table in
its static `messages` field. Each catalogue line is a `TaggedRecord` with
fields `n` (id), `i`/`e` (title; `e` marks an error), `x` (text) and `b`
(button spec).

`substitute(text, params[, providers])` replaces `<name>` with the value from
the parameter table. `<indentN>` sets the indent for the next value, and
unknown parameters are kept as they are. `addPluralSuffix` sets `<s>` from
`<n>`, so "1 problem" / "2 problems" work. `MessageRef` and `ErrorRef` carry an
id plus parameters; `ErrorRef` is also a `ResponseHandler`, so a server call
can fill it in.

A **DialogHandler** is built from a spec such as `"Retry:retry. Quit:quit;0"`.
It splits the spec into `labels` and `actions`, and the part after `;` is the
`defaultIndex`. `MessageDialog` creates one button per label. When a button is
pressed, every registered handler's `handleChoice(dialog)` runs
(`runHandlers`), and the dialog closes only if they all return true.
Subclasses (many live in the module groups) override `handleChoice` and use
`getSelectedAction`.

**MessageDialog** is a modal `BaseDialog`:

- With no owner it borrows an invisible undecorated `hiddenOwner` frame. After
  startup it uses a throw-away `ModuleFrame` instead, disposed when the dialog
  closes.
- `setBoundsKey` makes the dialog remember its last bounds in `savedBounds`.
- `showMessage` is the usual entry point. `showNotice`/`closeNotice` show a
  non-modal "please wait" box, and `showScrollingMessage` puts long text in a
  scroll pane.
- The index of the pressed button is left in `selectedButton` (-1 means
  Escape or closed).

`ProgressDialog` is the older, independent splash box. It can also show
buttons, and it knows how to dispose itself safely from a non-EDT thread.

## 5. Modules and frames

A module is a `LogicModule` (a `CellPanel`) inside a `ModuleFrame` (`JFrame`).
The frame forwards resize events to `module.resize()`. Closing the window calls
`closeModule`, which asks `module.shutdown(false)` first. Ctrl+Shift+H toggles
`ProblemSet.hideExtraProblems` when `hideSensitive` is on. This debugging aid
hides problems that are not in the course files and puts `h ` in front of the
problem-dialog titles (`markTitle`).

Startup of a module is asynchronous. `LPxxx.startup(bounds, busy, name)`
creates a subclass of `ModuleStartupTask` (`LogicModule$_A`), which carries
the busy indicator, bounds and problem name. If the problem list is not loaded
yet, the module reads it, merges in new exercises (`ProblemSet.mergeExercises`)
and calls `restateProblems(task)`. That starts a `ProblemRestateTask`, which
re-checks one problem per EDT turn and so keeps the UI responsive. It looks
only at problems in state *incomplete* or *unchecked*, recomputes their state
with `module.getProblemState(record)`, and when it finishes calls
`task.continueStartup()`. If any state changed it runs a second pass first.

`MainMenu` is itself a `LogicModule` with no problem. Each button either starts
a module (placed by `nextModuleBounds`, which cascades windows by 24 px), opens
a help PDF or URL through `DesktopLauncher`, or runs a user action (user info,
backup, copy, delete work/quit, quit). On quit, `closeAllModules` shuts down
every open instance. It then calls each module's `checkQuit(submitted,
local)`, which fills `handled`/`missing`/`changed` lists. If anything is
missing or changed, `showSubmitSummary` asks the user to resume or quit.

`SizedPanel` is the workhorse container: it clamps its preferred size, size
and bounds to `sizeLimit`, either exactly or as a maximum (`maximumOnly`). It
also forwards unconsumed key-typed events via `LogicProgram.forwardKeyEvent`.
`ProblemTitlePanel` is the header of every module: title label, statement,
status text ("Correct", "Incomplete", ...) and a note area.

## 6. Problem sets

A `ProblemSet` is a `Vector` of `ProblemEntry`. It also keeps a name index,
`entriesByName`, with upper-cased keys. Each module subclass supplies the
abstract hooks: `createEntry`, `getProblemStatement`, `hasWork`, `getWork`,
`removeWork`, `getModuleIndex` and `restateProblems`. The module's static fields
are reached by reflection (`getExercises`, `getInstances`,
`getDigestVersKey`).

- **Reading.** `LogicModule.readProblems` reads a file line by line. `#-`
  lines are collected as headings for the next problem (`headingsByName`). A
  plain `# xxx` line stores the file digest (`storedDigest`). Other lines go to
  `addProblem`, which inserts in sorted position for local problems
  (`findInsertIndex`; duplicates are rejected) or appends otherwise.
  `registerEntry` renames a clashing entry to `name-1`, `name-2`, ...
- **Merging exercises into work.** `mergeExercises` walks the module's
  exercise list. For each exercise it calls `mergeExercise`, which inserts
  problems missing from the work file and replaces those whose statement
  changed and that have no work yet (or are examples).
- **Work file integrity.** `LogicModule.writeProblems` writes every record and
  then `# <digest>`, where `computeDigest` is `UserInfo`'s MD5 over the records
  and the user. On reading, if the file was plain text (`readFromPlainFile`)
  and the digest does not match, the module reports "Could not digest file"
  and, unless the user is an instructor, refuses to continue.
- **Extra problems.** `ProblemEntry.findExtraProblems(workFile, set)` reads the
  names in the base and local problem files. Every entry whose name is not
  there is marked `extraProblem`; these are database or user problems.
- **States.** `ProblemEntry.state` is one of `STATE_NO_WORK` (0, "N"),
  `STATE_INCORRECT` (1, "I"), `STATE_CORRECT` (2, "C"), `STATE_INCOMPLETE` (3,
  "I") or `STATE_UNCHECKED` (4, "U"). The letter in `STATE_CODES` is what the
  submission reports as `evaluation`.

`createListView` builds a `ProblemListView` for the problem dialogs:

- Heading rows are disabled labels.
- Problem rows are coloured by state, and orange when a `ProblemSelector`
  marks the problem as restricted.
- Problems excluded by a second selector are left out.
- `rowToProblem` maps list rows back to problem indexes; heading rows map to
  -1.
- The list closes its owning `MessageDialog` on double-click/Enter (`accept`),
  or cancels on Escape.

## 7. ProblemSelector

Options such as `noCheck:{"1.7",~"1.72"}` restrict problems. The text
before `{` is a set of flag characters (`flagChars`, tested with `hasFlag`; `u`
covers user problems), optionally preceded by `~` to complement the set. Inside
the braces is a comma-separated list of quoted boundary names; a `~` in front of
a name makes it an "after" boundary instead of a "before" boundary (this is
also how `toString` prints it).

The set is stored like an interval set over strings:

- It is a sorted array of `SelectorBoundary` values. Each boundary is a name
  plus a flag saying whether it sits just *before* or just *after* that name.
- A `complemented` bit says whether the set starts "inside".
- Crossing a boundary toggles membership. `single(name)` is therefore the
  boundaries `(name, before)` and `(name, after)`, and `startingAt(name)` is one
  boundary.
- `intersect` merges two boundary lists in one pass, using a cursor on each
  list.
- `union` and `subtract` are built from `intersect` and `complement` (De
  Morgan).
- `contains(name)` tests whether `single(name) ∩ this` is non-empty.

`LogicProgram.selectorMatches(selector, name)` is the shared test: a `null`
name means a user problem, which is checked with the `u` flag. The same
design is used for integer sets in `C_n_F` (enumerated by
`IntRangeEnumeration`).

## 8. Notable and surprising details

- Several classes are dead or stubs:
  - `ProblemEditorFrame` builds two lists and does nothing more, and
    `ProblemEditorChecks.confirmClose` always returns true.
  - `ImportedProblemEntry`, `NamedIndex`, `UnusedIntHolder`,
    `UnusedEmptyClassPB`/`VC` and `EmptyEntryPoint` are never used.
  - `ModuleComponentMarker` is an empty interface that nothing tests.
- `DialogHandler.handleChoice` in the base class computes the action and then
  returns true on both branches.
- `LogicProgram.openRandomAccessFile` is the only instance method on
  `LogicProgram`, and nothing calls it.
- Nine `LogicProgram` constants (`f538` = 16, `f542`..`f549`) are never read,
  so they were left unnamed.
- `loadinfo.txt` is how a restart after an update passes state (link dir,
  course, `workDeleted`, `needBackup`) to the new process. It is deleted on
  exit unless an update has cleared `deleteLoadInfo`.
