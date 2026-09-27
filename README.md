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
| `runtime/local/`, `runtime/server/` | Created by `run.sh` (git-ignored), laid out like the macOS app bundle that the program expects:<br>• `Contents/Resources/` is a copy of `data/`, refreshed from `data/` whenever a file there is newer<br>• `Contents/Resources/work/` holds the student's work: `user.txt`, `prefs.txt`, one readable record file per module (`derivation.rec`, `truth-tables.rec`, …; see [data/README.md](data/README.md#your-work)) and logs<br>• `Contents/Java/` holds the jars from `build/` |

Local and normal mode keep separate runtime directories, so local work never gets mixed into a server account. To start over, delete the runtime directory.

`run.sh` runs the program with the system properties the official launcher sets:
- `root.dir` points at the runtime directory
- `config.dir` and `link.dir` point at `Contents/Resources`
- `prog.dir` points at `Contents/Java`

## Quantifiers

Quantifiers are shown as `∀` and `∃` in both notations. The original program showed notation 1's as the wedges `⋀` and `⋁`; `set: altsymbols` in the `logic` section of `data/options.rec` brings the wedges back.

In typed formulas, commands and the data files, `forall x` may be written for `@x` and `exists x` for `!x`: `forall x (Fx -> exists y Gy)`. A blank between the word and its variable is optional. Internally the program still uses `@` and `!` (`QuantifierWords`, `LogicProgram.parseFormula`, `DataFiles`).

## Derivation problem list

The derivation **Problems** list (the **Select** button) has a search field and a count of your progress.
- **Search:** type to narrow the list. The search looks at problem names and at what each problem proves (its `proves:` field). So `MC1` or `T2` finds the problem that unlocks `MC1` and `T2`, and `DIST` finds every problem that unlocks a `DIST` rule. A compound rule's forms count too.
  - Every word you type must match.
  - A name ending in a number matches only that name, so `T2` does not find `T25`.
  - A matching problem is shown with the headings just above it, such as "To enable MC1 & SSimp5, prove T2."
  - Hovering over a problem shows what it proves.
  - Up and Down move the selection; Enter opens the selected problem.
- **Count:** below the list: problems completed (checked correct) and not completed. Worked examples (`options: eg`, some deliberately wrong) and your own problems are not counted. While searching, the count for the matches is shown too.

Implementation: `ProblemSearchPanel`, `ProblemListView.setFilter`, `DerivationProblemSet.getSearchNote`.

## Justification syntax extensions

A derivation line's justification is a small stack program: cited line numbers push their formulas, and each rule pops its premises and pushes its result for the next step, as in `2 pr1 MP 2 pr2 MP ID`. This version adds two things to that language. Neither changes how existing justifications are read.

**Asserted results: `RULE[formula]`.** A formula in brackets right after a rule name is the result that step must have.
- If the rule could produce several results, the bracket chooses one, so no dialog appears.
- If the rule cannot produce the formula, the step is an error.
- On the last step, the bracketed formula must be the line's formula.
- In Command Mode the line may be left empty: it is filled in from the brackets.

```
pr1 S[Q] pr2 MP      line 1 is P&Q: take the conjunct Q, then Modus Ponens with premise 2
ass id[P]            Show line ~P: assume P rather than ~~P
pr1 UI[Fa] pr2 MP    instantiate @xFx to Fa without asking for the term
pr2 pr1 SWAP[R]      after SWAP the top formula must be R
```

- The formula uses the current notation. Blanks inside the brackets are allowed.
- A choice that no formula can settle still asks, e.g. the text questions of IE. The `/` answers (`UI/a`) still fill those questions in.
- `IE` and `CIE` ask two things: which part to replace, and by which equivalence. The bracket settles the first (the part where the stack formula and the bracket differ) and a `/` answer the second, written before the bracket: `pr1 IE/DN[P&~~Q] SL` turns `~~P&~~Q` into `P&~~Q`. On the last step the line's formula settles the part, so `pr1 IE/DN` is enough. One IE step replaces one part; `~~P&~~Q` to `P&Q` takes two: `pr1 IE/DN[P&~~Q] IE/DN`.
- Rules whose applications all give the asserted formula take the first one.

**Stack operations: `DUP`, `DROP`, `SWAP`.** These rearrange the formulas cited so far:
- `DUP` pushes another copy of the top formula
- `DROP` removes the top formula
- `SWAP` exchanges the top two

For example, `pr1 DUP pr2 MP MP` derives Q from P and P->(P->Q). Each formula keeps the line it was cited from, so box-closing rules still check where their lines come from. A stack operation cannot be the last step, which must be a rule that gives the line's formula.

**Stack view.** The **Stack** button in the Derivation window opens a panel showing the stack of the justification you are editing, as it stands after the steps before the cursor:
- The top formula is first, and each formula is labeled with where it came from (`line 2`, `by MP`).
- It follows the cursor as you type or move it. A word the cursor is inside, or a bracket not yet closed, is not counted yet.
- If a step does not apply, the panel says why. That includes a step that would need a dialog, where a formula in brackets settles the choice.
- The steps run as the Check button runs them, but nothing in the derivation changes. Choices you already made in dialogs are used.
- With the cursor in a line's formula, the panel shows the stack after that line's whole justification.

**Applicable rules.** The **Applicable** button opens a panel next to the stack view. It lists every rule that could be the next step at the cursor, with what it would produce.
- **What is listed:**
  - each form of the rules list and of your own rules (`UR…`) whose premises match the top of the stack, in any order
  - `CD`, `ID`, `DD`, `UD` and `BD` when they can close the box here
  - `IE` and `CIE`
  - the premises (`PR1`, `PR2`, …)
  - on the first line of a box, the assumptions (`ASS CD`, `ASS ID`, `ASS BDL`, `ASS BDR`)
  - `DUP`, `DROP` and `SWAP`, in a separate section at the end
  - Theorems (`Tn`) are not listed.
- **Several results:** a rule that can give several results has one row per result. For example, `Adj` gives both `Q∧R` and `R∧Q`, `LL1` lists each choice of occurrences, and `EG` lists each choice of term occurrences (when there are at most three).
- **Unknowns:** parts the program would ask for are shown as unknowns, in blue:
  - `?P`, `?Q` for formulas
  - `?F(…)` for formulas with the arguments shown in them
  - `?t` for terms, and `?f(…)` for terms built around the arguments shown
  - `?x` for variables

  For example, `MC1` on `Q` gives `?P→Q`, `UI` on `∀xFx` gives `F?t`, and `EI` gives `F?x`.
- **Order:** rules with no unknowns come first, then rules with unknowns. Within each group:
  1. rules that close the box
  2. rules that use the stack
  3. rules that push a formula
- **Not allowed here:** rules the problem does not allow still appear, grayed and in their own section, with the reason. Reasons are:
  - disabled for this problem (`options.rec`)
  - a derived rule whose theorem is not proved yet (the problem to prove is named)
  - `manual` in Command Mode
  - `ASS` rules whose box no rule may close in this problem
- **Matching the line:** `= line` marks a result that is the line's formula.
- **Hover details:** hovering over a row shows:
  - the rule's form and the stack formulas it uses
  - what it gives, and the unknowns explained
  - why it is not allowed, if it is not
  - what to type. That is the rule name, or `RULE[formula]` when the rule has several results or unknowns, e.g. `EG[exists ?x G(?x b)]` with `?x` filled in. Typed that way, no dialog appears.

Implementation: `DerivationLineChecker` (`readAssertion`, `checkStep`, `applyStackOperation`, `occurrenceChoices`, and the preview mode used by `DerivationStackView`); `DerivationRulesView`; messages `DerErr110`–`DerErr114` in `data/messages/derivation.rec`. An asserted result also settles which occurrences `LL1` and `LL2` replace, e.g. `pr1 pr2 LL1[G(ba)]` from `a=b` and `G(aa)`.

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
| `main` | Working branch: `base-logic2010` plus your changes (so far: bundled course data, readable data and work files, local mode, justification syntax extensions, quantifier words and symbols, and fixes for deleting a problem's work, the menu layout on modern Java, and formula parse errors). |
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
