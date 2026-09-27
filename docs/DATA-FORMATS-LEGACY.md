# The original Logic 2010 data files

This describes the data files as shipped with the official Logic 2010 releases, found in the installation's `Contents/Resources` directory. On the `main` branch they have been replaced by readable files; `tools/convert-data.py` converts one layout into the other.

The main branch's `data/README.md` describes the new formats. `tools/check-data-conversion.sh` verifies a conversion: it checks that the program, reading the converted files, sees exactly the data it would have read from the originals.

## 1. Overview

| Original file | Readable replacement | What it is | Read by |
|---|---|---|---|
| `spirit.txt` | `version.conf` | Core version and platform: `version:20200601`, `arch:macos`. Its presence switches the program into "scrambled mode". | `LogicProgram.readCoreInfo` |
| `ghost.txt` | `links.conf` | The *links table* (see §4): course identity, notation, directory aliases, the location of every other data file, PDFs, web pages and server URLs. | `LogicProgram.readLinks`, `ServerConnection.readDatabaseLinks` |
| `wraith.txt` | `options.rec` | Options for the program and each module (see §3.10). | `LogicProgram.readOptions` and each module's `readOptions` |
| `imp.txt` | `derivation-tips.rec` | Derivation strategy tips, an outline (§3.9). | `OutlineNode.readOutline`, `DerivationDialogs` |
| `spectre.txt` | `messages/general.rec` | Global message catalogue. | `Message.loadMessages` |
| `zombie.txt` | `messages/derivation.rec` | Derivation messages. | `DerivationMessage` |
| `tomb.txt` | `messages/invalidity.rec` | Invalidity messages. | `InvalidityMessage` |
| `shade.txt` | `messages/parsing.rec` | Parsing messages. | `ParsingMessage` |
| `demon.txt` | `messages/symbolization.rec` | Symbolization messages. | `SymbolizationMessages` |
| `crypt.txt` | `messages/truth-tables.rec` | Truth-table messages. | `TruthMessage` |
| `troll.txt` | `messages/recognition.rec` | Recognition messages. | `RecognitionMessage` |
| `syntaxN/ghoul.txt` | `syntaxN/derivation-problems.rec` | Derivation problems and worked examples. | `LPDerivation` |
| `syntaxN/werewolf.txt` | `syntaxN/invalidity-problems.rec` | Invalidity problems. | `LPInvalidation` |
| `syntaxN/vampire.txt` | `syntaxN/parsing-problems.rec` | Parsing problems. | `LPParsing` |
| `syntaxN/devil.txt` | `syntaxN/symbolization-problems.rec` | Symbolization problems. | `LPSymbolizer` |
| `syntaxN/mummy.txt` | `syntaxN/symbolization-answers.rec` | Symbolization answer keys. | `LPSymbolizer` (`symAnswers` link) |
| `syntaxN/warlock.txt` | `syntaxN/truth-table-problems.rec` | Truth-table problems. | `LPTruthAnalysis` |
| `syntaxN/goblin.txt` | `syntaxN/recognition-problems.rec` | Rule-recognition problems. | `LPRecognition` |
| `syntaxN/banshee.txt` | `syntaxN/rules.list` | Rules of inference (§3.11). | `RuleTable.read` |
| `syntaxN/fiend.txt` | `syntaxN/theorems.list` | Theorems (§3.11). | `TheoremTable.read` |
| `syntax2/text/*.pdf` | same | Textbook chapters (*Logic Text* button). | `MainMenu` |
| `local/*.txt` | `local/*.rec` | Instructor additions: problem files with the same names as in `syntaxN/`, plus `local/wraith.txt` (options). Read in addition to the core files. | `LogicProgram.openLocalFile` |
| `docs/*.pdf` | same | Help documents, linked from `ghost.txt`. | `DesktopLauncher` |
| `maps.txt`, `mapsa/d/o.txt` | dropped | Path maps for the old `ProgramLauncher` entry point (Windows/floppy installs); unused by the macOS bundle. | `ProgramLauncher` |
| `Logic2010.icns`, `en.lproj/` | dropped | macOS application icon and localization stub. | — |
| `work/` | unchanged | The student's own files (§5). Not data shipped with the program. | |

`syntaxN` is `syntax1` or `syntax2`, one per formula notation. The `syntax:` entry of the links table chooses between them, and `LogicProgram.setSyntax` sets `ruleDir`.

The file names are deliberately meaningless ("monster names"); the links table maps logical names to them.

## 2. Scrambling

Every `.txt` file above (not the PDFs, and not the student's `work/` files) is scrambled line by line with a running-key cipher. `Scrambler.unscramble` / `scramble`; `ScrambledReader` is a `BufferedReader` whose `readLine` unscrambles.

- The alphabet is 96 characters: TAB, space, and ASCII `!` (0x21) through `~` (0x7E). Other characters, including line ends, pass through unchanged.
- The key is the fixed string `the Logic Program is protected by international copyright law`.
- For each line, `k = 0`. For each position `l`:
  - if `key[l % keylen]` is in the alphabet, add its index to `k`;
  - if the ciphertext character `c` is in the alphabet, output `A[95 - (k - idx(c) + 95) % 96]` (Java `%`) and set `k = idx(c)`.

So every output character depends on the key and on the previous ciphertext character. `tools/unscramble.py` implements both directions.

"Scrambled mode" is decided by `spirit.txt`. If the installation has `coreinfo.txt` instead, the program reads unscrambled files and the links table from `links.txt`: a development mode that was never shipped.

## 3. Record format ("tagged records")

Except for the links table, the version file and the rule/theorem lists, every file is a sequence of lines in the program's *tagged record* format (`TaggedRecord`).

- A line is a sequence of fields. Each field is its value followed by a backquote and a one-character **tag**: ``Deriv 1.0001`$~P.  Q->P  .:  ~Q`-``. The tag comes *after* the value.
- A doubled backquote (` `` `) is a literal backquote inside a value.
- A line starting with a backquote has that backquote removed first. (Records whose first value is empty begin with two backquotes.)
- Text after the last tag is ignored.
- Blank lines and lines starting with `#` are comments; lines starting with `#-` are **headings**. In problem, rule and theorem files the headings before a record are attached to it and shown in the program's lists (for example `#-                         CHAPTER I`).
- In problem files a `#` comment line is also remembered as a digest. That only matters for the student's work files, which end with `# <md5>`.
- Normally one line is one record (`TaggedRecord(reader, true)`, `readNext`). The tips file is read as a single record: `TaggedRecord(reader)` concatenates the fields of all lines.
- Values use the program's own conventions:
  - `\n` is a line break in messages
  - `\l` toggles translation of the ASCII formula notation ("Maggie-talk": `->`, `<->`, `@x`, `!x`, `~`) into logic symbols
  - `\.` escapes a dot in dot-separated lists
  - `<param>` is replaced in messages

The meaning of a tag depends on the file. The tables below give each tag's meaning and its field name in the readable format. Tags that only occur in the student's saved work are included because work and problem records share the same format: a work file is a copy of the problem file with the work added to each record.

### 3.1 Derivation problems: `ghoul.txt` → `derivation-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `-` | `show` | a Show line; the first one (written as 'statement') is the argument to derive |
| `+` | `collapsed-show` | a Show line whose box is collapsed |
| `<` | `line` | formula of an ordinary line |
| `>` | `reason` | justification (annotation) of the preceding line |
| `#` | `cancel` | cancels the current Show line with this justification and closes its box |
| `=` | `end-box` | closes a box that was not canceled |
| `:` | `cached-justification` | the program's cached analysis of the preceding line's justification |
| `s` | `command` | command that created the Show line |
| `m` | `message` | saved message, 'line:text' |
| `?` | `flag` | flag (not used by the program) |
| `p` | `proves` | rule or theorem this problem proves |

### 3.2 Invalidity problems: `werewolf.txt` → `invalidity-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `?` | `argument` | the argument to show invalid |
| `#` | `universe-size` | size of the universe |
| `=` | `interpretation` | interpretations, separated by '.' |
| `&` | `workspace` | free-form workspace text (encoded) |

### 3.3 Parsing problems: `vampire.txt` → `parsing-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `=` | `formula` | the formula to parse |
| `[` | `notation` | chosen notation: O official, I informal, N not well formed |
| `]` | `expansion` | which parse-tree nodes are expanded |
| `*` | `main-connective-answer` | answer in main-connective-only mode |

### 3.4 Symbolization problems: `devil.txt` → `symbolization-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `-` | `english` | the English sentence |
| `+` | `node` | a node of the symbolization tree, 'code:English', in pre-order |
| `=` | `scheme` | scheme of abbreviation, 'symbol:English' items separated by '.' |
| `@` | `answer-keys` | answer-key names (see symbolization-answers.rec), separated by '.' |
| `g` | `answer-group` | problems in the same group share their answers |
| `h` | `hints` | hint count (saved work) |

### 3.5 Symbolization answer keys: `mummy.txt` → `symbolization-answers.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `answer-key` | answer key, referred to from symbolization-problems.rec |
| `-` | `english` | the English sentence |
| `+` | `node` | a node of the answer's symbolization tree, 'code:English', in pre-order |
| `e` | `errors` | error count |
| `t` | `seconds` | time worked, in seconds |

### 3.6 Truth-table problems: `warlock.txt` → `truth-table-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `=` | `statement` | the argument or formula |
| `@` | `row` | work on one table row |
| `*` | `answer` | answer to the question: 0 yes, 1 no |
| `#` | `counterexample-row` | index of the row marked as counterexample |
| `&` | `setup` | setup-stage work |

### 3.7 Rule-recognition problems: `goblin.txt` → `recognition-problems.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `problem` | problem name, as shown in the problem list |
| `%` | `options` | per-problem options, e.g. eg (a worked example) |
| `!` | `note` | note shown under the problem title |
| `C` | `common-name` | common name of the problem (course database) |
| `o` | `original-name` | name the problem was copied from |
| `e` | `errors` | error count (saved work) |
| `t` | `seconds` | time worked, in seconds (saved work) |
| `=` | `argument` | the argument |
| `*` | `answer` | the student's answer (saved work) |
| `@` | `correct-rules` | rules that are accepted as correct answers, separated by '.' |
| `~` | `near-miss-rules` | rules that get a 'nearly right' response |
| `&` | `comment` | comment shown after a correct answer |

### 3.8 Messages: `spectre.txt`, `zombie.txt`, `tomb.txt`, `shade.txt`, `demon.txt`, `crypt.txt`, `troll.txt` → `messages/*.rec`

| tag | new field | meaning |
|---|---|---|
| `s` | `sort` | sort key (editorial, not used by the program) |
| `n` | `id` | message id, used by the program |
| `e` | `error` | title of an error message (shown in red) |
| `E` | `error-revised` | revised error title (not used by the program) |
| `i` | `info` | title of an information message (shown in gold) |
| `I` | `info-revised` | revised info title (not used by the program) |
| `x` | `text` | message text; \n is a line break, \l toggles symbol translation, <param> is replaced |
| `d` | `description` | when the message appears (for the programmer) |
| `p` | `programmer-note` | comment to the programmer |
| `b` | `buttons` | buttons: 'Label:action.Label:action;default-index' |

### 3.9 Derivation tips (an outline): `imp.txt` → `derivation-tips.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `text` | text of the next entry (several text fields are concatenated) |
| `+` | `entry` | creates an entry (collapsed) with the preceding text; the value is its title |
| `-` | `expanded-entry` | like entry, but initially expanded |
| `=` | `end` | ends the current level of the outline |

### 3.10 Options: `wraith.txt` → `options.rec`

| tag | new field | meaning |
|---|---|---|
| `$` | `section` | which part of the program the record configures: logic, derivation, parsing, ... |
| `+` | `set` | turns an on/off option on |
| `-` | `unset` | an on/off option that is left off |
| `?` | `option` | 'name:problem-selector', e.g. noErrMess:{"1.7","1.72"} |
| `u` (in `logic`) | `login` | program login for the course server, 'user:password' |
| `f` (in `logic`) | `font-size` | font size |
| `c` (in `logic`) | `backup-count` | number of server backups to keep |
| `b` (in `logic`) | `backup-name` | name of the server backup set |
| `r` (in `logic`) | `restore-name` | name of the backup set to restore from |
| `p` (in `logic`) | `instance-port` | port used to allow only one running copy |
| `o` (in `logic`) | `option-o` | (not used by the program) |
| `d` (in `derivation`) | `disable` | rules disabled for the selected problems, 'RULES:selector' |
| `D` (in `derivation`) | `disable-all-forms` | like disable, applied to every form of compound rules |
| `m` (in `derivation`) | `manual` | rules that must be applied without the program's help |
| `M` (in `derivation`) | `manual-all-forms` | like manual, applied to every form of compound rules |
| `a` (in `derivation`) | `assume-weakly` | rules/theorems that may be used without proof (weakly assumed) |
| `A` (in `derivation`) | `assume` | rules/theorems that may be used without proof |
| `a` (in `recognition`) | `activate-rules` | rules offered for the selected problems |

Notes:
- Message files: every value is padded with blanks (the files were exported from a spreadsheet). The program trims every field it uses, and the converter trims them all. `s`, `d`, `p`, `E` and `I` are editorial and never read. A button spec is `Label:action.Label:action;default-index`.
- Options: a record is `name`$` followed by options. The name is `logic` or a module (`derivation`, `parsing`, `truth`, `invalidation`, `symbolization`, `recognition`); one record even misspells it (`derviation`) and is therefore ignored. There is one line per option; the converter joins consecutive lines of the same section into one record (the readers loop over all fields, so this is equivalent). A *problem selector* like `{"1.7","1.72"}` lists problem-name boundaries. Flag characters before `{` (for example `u` for user problems) and a leading `~` (complement) modify it.
- The `u` option in the `logic` section is the program's own login for the course server, `logic:<base64 of the scrambled password>`.

### 3.11 Rules and theorems (`banshee.txt`, `fiend.txt`)

These are not tagged records. One per line, `NAME<whitespace>BODY`, where the name ends at the first blank, plus `#-` headings and `#` comments. The rule and formula parsers ignore blanks, and the converter aligns the columns.

- A rule body containing `.:` is a schema, `premises .: conclusion` with premises separated by `.` (`SchematicRule`).
- Any other body is a `.`-separated list of rules (and `T<n>` theorems) forming a compound rule (`Rule`).
- A theorem line is `number formula` (`Theorem`). The program derives the rules `RT<n>` from theorems.

## 4. The links table (`ghost.txt`) and version file (`spirit.txt`)

`key:value` lines (keys are case-insensitive) and `#` comments.

| Key(s) | Meaning |
|---|---|
| `institution`, `term`, `course`, `ident`, `version` | The course this installation is set up for. Institution `Demo` or `Test` means demo mode: no server account. The shipped file is the "Initial Demo course". `version` is the course data (text) version. |
| `syntax` | Formula notation, 1 or 2. It picks `syntax1/` or `syntax2/` as `ruleDir`. |
| `progDir`, `linkDir`, `ruleDir`, `localDir`, `adminDir`, `nonetDir` | Directory aliases. A value starting with `progDir/`, `linkDir/` or `ruleDir/` is rebased onto that directory. |
| `derwork.txt` … `recwork.txt`, `symAnswers` | Problem files of the six modules, and the symbolization answer keys. The key is also the name of the student's work file. |
| `messages`, `derMessages` … `recMessages` | Message catalogues. |
| `options`, `rules`, `theorems`, `tips` | The other data files. |
| `menuHelp`, `derStart`, `derHelp`, …, `about`, `using` | Help PDFs. |
| `feedback`, `website`, `assignments` | Web pages. |
| `logic_*` | Course-server endpoints (see the server module notes). |
| `browser`, `word`, `security`, `headVers`, `headlines` | Leftovers from the Windows version. |

In `links.conf` the readable names of the file keys are used instead (`derivation-problems`, `derivation-messages`, `derivation-tips`, …; `DataFiles.LINK_ALIASES`), and the file locations point at the readable files.

`spirit.txt` holds `version:` (the core version, compared with the server's for updates) and `arch:` (`macos`, `windows` or `noarch`; it selects which update package to download).

## 5. The student's work directory (`work/`)

The student's files are unscrambled and were not changed:
- `user.txt`: `key:value` user information
- `prefs.txt`
- one work file per module, `derwork.txt` … `truwork.txt` plus `keywork.txt`. Each is tagged records as in §3: the problems with the student's work added, and a final `# <md5>` digest line that the program checks.
- logs: `errors.txt`, `diagnostics.txt` and `*data.txt`

## 6. The converter

```sh
tools/convert-data.py /path/to/Contents/Resources out-dir
tools/check-data-conversion.sh /path/to/Contents/Resources out-dir /path/to/main-checkout
```

The converter decodes each file, then:
- turns every tagged record into a block of `field: value` lines, using the tables above
- turns headings into `## ` lines
- writes values with surrounding blanks, or a leading `"`, as JSON strings
- aligns the rule and theorem lists
- rewrites the links table with the readable key and file names

It also adds a header comment listing the fields to each file. The field-name tables must match `DataFiles.SCHEMAS` on the main branch.

The check compiles `tools/dataformat/CheckDataConversion.java` against the main branch's `build/logic.jar`. For every original file, it reads the converted file through the program's `DataFiles` reader and compares, using the program's own `TaggedRecord` parser:
- the headings
- every record's sequence of (tag, value) pairs
- rule and theorem names and bodies
- link keys and values

Comments are ignored. The only differences it allows are the ones listed above, all invisible to the program:
- trimmed message values
- merged option records
- the tips outline split into blocks
- trimmed rule bodies
- dropped lines that contain no tagged field at all (a record without a name is ignored by every reader)
