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

Students' work is checked locally and submitted to the course server.

## Requirements

- A JDK, version 8 or newer (tested with OpenJDK 25). No other build tools or libraries are needed.
- To run the program, the course data from an existing Logic 2010 installation (see *Running*).

## Building

```sh
./build.sh
```

This compiles two jars into `build/`:
- `build/logic.jar`: the program. Its main class is `edu.ucla.phil.logic.LogicProgram`.
- `build/loader.jar`: the updater. Its main class is `edu.ucla.phil.logic.LPUpdateLoader`. The program runs it to install core updates downloaded from the server.

## Running

```sh
./run.sh [INSTALL_DIR]      # default INSTALL_DIR: /data/logic2010
```

The jar holds only code. Everything course-specific comes from an installation directory, laid out like the macOS app bundle:

```
INSTALL_DIR/
  Contents/Resources/     course data: problem, rule, theorem and message files,
                          links (server URLs, institution/term/course), options, help PDFs
  Contents/Resources/work/  the student's files: user.txt, prefs.txt, *work.txt, logs
  Contents/Java/          the installed program (used by the updater)
  certs/truststore.jks    optional, see below
```

`run.sh` starts `build/logic.jar` with the system properties the official launcher sets:
- `root.dir` is set to `INSTALL_DIR`.
- `config.dir` and `link.dir` are set to `Contents/Resources`.
- `prog.dir` is set to `Contents/Java`.

The program then reads and writes the student's work there, just like the official build.

Things to know:
- **Certificates:** the course server `logiclx.humnet.ucla.edu` sends an incomplete certificate chain. If `INSTALL_DIR/certs/truststore.jks` exists (the JDK cacerts plus the missing "InCommon RSA Server CA 2" intermediate, password `changeit`), `run.sh` uses it through `JAVA_TOOL_OPTIONS`. Processes the program spawns, such as the updater, inherit it that way too.
- **Updates:** if the program downloads a core update, the update is installed into the installation (`Contents/Java/logic.jar`), and the program restarts from there. You are then running the official build until you use `run.sh` again.

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
  - `Scrambler` and `ScrambledReader`: the course data files are scrambled with a fixed running-key cipher.
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
| `main` | Working branch. Make your own changes here. |
| `base-logic2010` | The unmodified source, exactly equivalent to the installed Logic 2010 (version 20161225 core). Keep it untouched as the reference point. |
| `reverse-engineering` | How this source was recovered. It holds the original jars, the naming mappings, the build pipeline that regenerates `base-logic2010`'s source, the equivalence checker, a decoder for the scrambled data files, and detailed architecture notes (`docs/`). |

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
