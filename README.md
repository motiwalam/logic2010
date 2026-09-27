# Logic 2010

Source code for **Logic 2010**, UCLA Philosophy's program for learning formal logic.
It has six exercise modules:

| Module | Class | What the student does |
|---|---|---|
| Symbolization | `LPSymbolizer` | Translate English sentences into logical formulas. |
| Parsing | `LPParsing` | Break formulas into their parse trees. |
| Truth Tables | `LPTruthAnalysis` | Fill in truth tables; decide validity and tautology. |
| Derivations | `LPDerivation` | Build natural-deduction proofs. |
| Invalidity | `LPInvalidation` | Give interpretations showing that arguments are invalid. |
| Recognizing Rules | `LPRecognition` | Identify rule instances. |

Students' work is checked locally. Normally it is also submitted to, and backed up on,
UCLA's course server. This repository is self-contained, though: it includes all the
course data, and it has a **local mode** that needs no server or account at all.

## Quick start

On any machine with a JDK (version 8 or newer; tested with OpenJDK 25):

```sh
git clone <this repo> logic2010 && cd logic2010
./build.sh           # compile build/logic.jar and build/loader.jar
./run.sh --local     # start Logic 2010, fully offline
```

In local mode you go straight to the main menu; the first launch just asks for display preferences. Every module and problem is available, and work is saved in `runtime/local/Contents/Resources/work/`.

## Running

```
./run.sh --local [--syntax 1|2]   fully offline (see below)
./run.sh                          normal mode, using the course server
./run.sh --install DIR            normal mode, using an existing installation's data and work
./run.sh --help
```

Options:

| Option | Meaning |
|---|---|
| `--local` | Local mode. |
| `--syntax 1\|2` | Formula notation in local mode. The default is 1, taken from `data/links.conf`. Notation 2 is the one used by the current textbook; it also enables the *Logic Text* menu button, which opens the textbook chapters. |
| `--home DIR` | Runtime directory to use instead of `runtime/local` or `runtime/server`. |

### Local mode

`--local` passes `-Dlogic.local=true` to the program (`LogicProgram.localMode`), which does three things:

- **Turns on the program's own `nonet` option.** This means:
  - no server verification, update checks, submissions, backups or uploads
  - the *Submit* and *Backup* buttons are hidden
- **Turns on the program's demo mode.** There is no institution, term or course selection and no registration: a local user ("Logic User") is created automatically.
- **Blocks network access** as a safety net (`ServerConnection.checkNetworkAllowed`). If anything tries to reach the course server anyway, the attempt fails and a stack trace is printed.

The course-specific *Assignments* web link is also hidden. *Feedback* and the help links still open in your browser.

### Normal mode

Without `--local`, the program behaves like the official release:
- First run: choose an institution, term and course, then register or log in.
- Your work is verified with, submitted to and backed up on `logiclx.humnet.ucla.edu`.
- The server can push course files and core updates.

This needs a valid course account.

The server sends an incomplete TLS certificate chain. `run.sh` therefore builds a truststore on first use (`runtime/server/truststore.jks`): this JDK's CA certificates plus the missing intermediate, `certs/InCommonRSAServerCA2.pem`. It passes the truststore via `JAVA_TOOL_OPTIONS`, so the updater that the program launches inherits it too.

If the server installs a core update, it replaces the program in `runtime/server/Contents/Java` and restarts it. The next `./run.sh` puts your build back.

### Where things live

| Path | Contents |
|---|---|
| `data/` | All the course data the program reads, as readable plain text: problems and answer keys, rules and theorems (`syntax1/`, `syntax2/`), message catalogues (`messages/`), options (`options.rec`), the links file (`links.conf`: the demo course, notation, file locations, server URLs), help PDFs (`docs/`) and textbook chapters (`syntax2/text/`). See [`data/README.md`](data/README.md) for the file formats. |
| `runtime/local/`, `runtime/server/` | Created by `run.sh` (git-ignored), laid out like the macOS app bundle that the program expects:<br>• `Contents/Resources/` is a copy of `data/`, refreshed from `data/` whenever a file there is newer<br>• `Contents/Resources/work/` holds the student's work: `user.txt`, `prefs.txt`, `*work.txt` and logs<br>• `Contents/Java/` holds the jars from `build/` |

Local and normal mode keep separate runtime directories, so local work never gets mixed into a server account. To start over, delete the runtime directory.

`run.sh` runs the program with the system properties the official launcher sets:
- `root.dir` points at the runtime directory
- `config.dir` and `link.dir` point at `Contents/Resources`
- `prog.dir` points at `Contents/Java`

## Source layout

```
logic/src/main/java/edu/ucla/phil/logic/          the program (single package, plus:)
logic/src/main/java/edu/ucla/phil/logic/syntax1/  formula parser for notation 1 (JavaCC-generated)
logic/src/main/java/edu/ucla/phil/logic/syntax2/  formula parser for notation 2 (JavaCC-generated)
logic/src/main/resources/                         icons and bundled fonts
loader/src/main/java/                             the updater
```

Where to start reading:

- **Startup:** `LogicProgram` holds global state, configuration, symbol translation and file helpers. `MainMenu` is the launcher window.
- **Module framework:**
  - `LogicModule`: base class of the six `LP*` module windows.
  - `ProblemSet`/`ProblemEntry`: exercises and saved work.
  - `ModuleConstants`
  - `Message` and `MessageDialog`: message catalogues and dialogs.
- **Data files:**
  - `TaggedRecord`: the record format used by every problem, work, option and message file.
  - `DataFiles`: reads the data files (`data/README.md`) and hands them to the rest of the program as `TaggedRecord` lines. It also reads the original, scrambled format (`Scrambler`/`ScrambledReader`), which the course server may still send.
- **Formulas:**
  - `Expression` is the expression tree, with `Formula` and `Term` subclasses.
  - `FormulaParser` picks the `syntax1` or `syntax2` parser.
  - `SchemeInstantiation` does schematic matching for rules.
- **Proof checking:** `DerivationLineChecker` checks a derivation line's justification against `Rule`s and `Theorem`s. `ArgumentParser` handles rule recognition.
- **Truth tables:** `TruthTableEvaluator`.
- **Server:** `ServerConnection` and `ServerSession`.
  - Requests are HTTP POSTs, signed with MD5 digests over a server-issued nonce.
  - `Submission` handles exercise submission.
  - `UserSetup` handles registration and login.

## Branches and workflow

| Branch | Purpose |
|---|---|
| `main` | Working branch: `base-logic2010` plus your changes (so far: bundled course data, local mode, and a menu-layout fix for modern Java). |
| `base-logic2010` | The unmodified source, exactly equivalent to the official Logic 2010 program (core version 20200601), still needing an existing installation to run. Keep it untouched as the reference point. |
| `reverse-engineering` | How this source was recovered. It holds the original jars, the naming mappings, the build pipeline that regenerates `base-logic2010`'s source, the equivalence checker, the converter from the original scrambled data files to the readable ones (and documentation of the old formats), and detailed architecture notes (`docs/`). |

Common tasks:

```sh
git diff base-logic2010 main                  # everything you've changed
git log base-logic2010..main                  # your commits
git format-patch base-logic2010..main -o patches/   # export your changes as patch files
git diff base-logic2010 main --stat -- logic/  # files touched in the program
```

To check how a build of `main` differs from the original program, use the tools on the `reverse-engineering` branch:

```sh
git worktree add ../logic2010-re reverse-engineering
cd ../logic2010-re && tools/verify.sh        # builds that branch, creates build/logic-remapped.jar
java -cp "$(ls tools/asm-*.jar | tr '\n' :)" tools/compare/Compare.java \
     build/logic-remapped.jar /path/to/main/build/logic.jar --known tools/compare/known-differences.txt
```

The comparison lists every method whose behaviour differs from the original, which should be exactly the ones you changed.

For a detailed tour of each subsystem, see `docs/ARCHITECTURE.md` and `docs/modules/*.md` on the `reverse-engineering` branch. Those notes also list known quirks and dead code.
