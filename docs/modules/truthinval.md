# Truth Tables and Invalidity modules

This document covers two of the six modules: **Truth Tables**
(`LPTruthAnalysis`, module index 5, abbreviation `tru`) and **Invalidity**
(`LPInvalidation`, module index 1, abbreviation `inv`). It also covers a few
classes that ended up in the same partition: the shared `ChoiceListener`
interface, the `WrappedTextPanel` print cell and the Recognition module's
`RecognitionProblemPanel`.

Names are the ones in `mappings/truthinval.mapping`. Placeholders are given in
parentheses the first time a class is mentioned. `LPTruthAnalysis` and
`LPInvalidation` keep their original member names, because the module
framework reaches them by reflection (see ARCHITECTURE.md section 4).

## 1. Common module skeleton

Both modules follow the usual pattern:

| part | Truth Tables | Invalidity |
|---|---|---|
| module panel | `LPTruthAnalysis` | `LPInvalidation` |
| startup runnable | `TruthStartupTask` (`LPTruthAnalysis$C__A`) | `InvalidityStartupTask` (`LPInvalidation$C__A`) |
| problem panel | `TruthProblemPanel` (`C_k_E`), field `problem` | `InvalidityProblemPanel` (`C_SA`), field `problemPanel` |
| `ProblemSet` | `TruthProblemSet` (`C_O`) | `InvalidityProblemSet` (`C_PC`) |
| `ProblemEntry` | `TruthProblemEntry` (`C_f_C`) | `InvalidityProblemEntry` (`C_u_D`) |
| message class | `TruthMessage` (`C_FE`, link `truMessages`) | `InvalidityMessage` (`C_LA`, link `invMessages`) |
| static dialogs | `TruthDialogs` (`C_w_A`) | `InvalidityDialogs` (`C_CE`) |
| button bar | `TruthToolbar` (`C_u_E`) | `InvalidityToolbar` (`C_IB`) |
| print pages | `TruthResultsPage` (`C_A`), `TruthStatementsPage` (`C_c_D`), `TruthProblemsPage` (`C_x_`) | `InvalidityResultsPage` (`C_e_F`), `InvalidityStatementsPage` (`C_k_D`), `InvalidityProblemsPage` (`C_q_C`) |
| work file | `work/truwork.txt` | `work/invwork.txt` |
| problem file | `warlock` (`syntax?/`) | `werewolf` |

The two `*Dialogs` classes are almost line-for-line copies of each other; only
the module type and the help-context keys (`truChosen`/`invChosen`, ...)
differ. The Invalidity copy wraps every dialog in a throwaway `ModuleFrame`
as its owner, while the Truth copy uses the module's own frame.

- `showMessage` (4 overloads) looks an id up in the module's message
  catalogue and shows it. If the message defines buttons, it builds a
  `DialogHandler` for them (`InvalidityDialogHandler`, `C_GB`, in the
  Invalidity module).
- `confirmSaveChanges` asks "Do you wish to save the current problem?".
  Every navigation action goes through it first.
- `selectNextProblem` (Ctrl+N or right-click on Select) and `chooseProblem`
  (Ctrl+O) switch problems. If the chosen problem is already open in another
  window, that window is brought to the front instead.
- `deleteProblemOrWork`, `deleteWork` and `deleteMultipleProblems` delete
  problems or work. Exercises can only have their work erased; user problems
  can be removed.
- `chooseSubmitProblems`, `chooseUploadProblems` and `choosePrintProblems`
  return the selected indexes. The print dialog has four buttons: "Print"
  (full pages), "Print Results" (the `*ResultsPage`), "Print List"
  (`*StatementsPage`) and "Cancel".
- `createUserProblem` lets the student type an argument, and
  `enterSubmittedProblem` lets an instructor paste a raw `TaggedRecord`.
  The Truth version has a "Truth Table Only" checkbox, which appends
  `taut`%` to the record.
- `createProblemListView` is a thin wrapper around `ProblemSet.m1781`.

Both toolbars are built by `create` → `buildButtons`. Each button is a
two-action button (`C__D`): left click and right click. The buttons are bound
to Ctrl+O/N/K/S/P and Alt+F4. The Invalidity toolbar adds four buttons:
**Derivation** (opens the argument in `LPDerivation`), **Truth Table** (opens
the current workspace selection in `LPTruthAnalysis`), **Copy** (copies the
problem into the workspace) and **Expand** (expands the outermost selected
quantifier; right click expands all of them).

The six print pages are small `C_CA` subclasses. Each overrides the page
builder `m70(Dimension)` to call the module's `getResults`, `getStatements`
or `getPrintProblems`. Each also has a static `printResults`,
`printStatements` or `printProblems(int[])` that queues a page on the
module's `printQueue`. The status column on these pages is a
`WrappedTextPanel` (`C_f_E`), which the other modules use too.

## 2. Truth Tables

### 2.1 Problem record format (`truwork.txt` / `warlock`)

A problem is a `TaggedRecord` line:

| tag | meaning |
|---|---|
| `$` | problem name |
| `=` | statement, e.g. `P->Q . Q .: P` or a single formula |
| `%` | options; `taut` means "just complete the table" (`assumeTautology`) |
| `@` | one per touched row: `TFT:cell0.cell1...cellN` (row assignment, then one code per premise column plus the conclusion column) |
| `*` | answer to the yes/no question: 0 = yes, 1 = no |
| `#` | index of the row marked as the counterexample |
| `&` | setup-stage work (see 2.4) |
| `e`, `t` | error count, work time in seconds |

`hasWork`/`getWork` look at the tags `@*#&`. `removeWork` keeps only
`$=%u!`.

A **cell code** (`TruthTableCell.getCode`) has the form
`<tree values><sign><value>`:

- `<tree values>` is the serialized T/F/? values of the cell's evaluation
  tree (`C_VF.m1407`).
- `<sign>` is `+` if the cell is fine and `-` if it was flagged wrong.
- `<value>` is the value shown on the cell button (`T`, `F` or `?`).

An empty cell is `+?`. The static helpers `findSignIndex`, `extractValue`,
`extractTreeValues` and `hasErrorSign` split a code into these parts.

### 2.2 The evaluator (`TruthTableEvaluator`, `C_HA`)

This is the only piece of real propositional semantics in the module.

- **Construction.** It is built from a single `Expression` or from an
  `ArgumentParser` (premises plus conclusion). `collectSentenceLetters` walks
  every `ConnectiveFormula` and collects each non-connective subformula (a
  sentence letter, or any atomic or quantified formula treated as opaque)
  into `sentenceLetters`. Duplicates are detected with
  `Expression.m1236(..., new C_MB())` (structural match).
- **Rows.** There are `2^n` rows. `rowAssignment(row)` maps bit *k* of the
  row number to letter *k*: a set bit means true. Note that the displayed
  row label is built differently, by `TruthTableGrid.rowAssignmentString(row, n)`.
  It writes `T` for a 0 bit and puts the least significant bit on the right,
  so row 0 is `TT...T`, as in textbooks.
- **Evaluation.** `evaluate(expr, assignment)` interprets `~ & | -> <->`
  recursively. `computeRows` fills `rowResults`:
  - For a formula, `rowResults[row]` is the formula's value.
  - For an argument, it starts from the conclusion's value and then sets
    `rowResults[row] = true` wherever some premise is false. A row is
    therefore false exactly when it is a counterexample.
- **Verdict.** `isAllTrue()` means "tautology" or "valid".
  `areEquivalent(a, b)` is a static helper: it builds `a <-> b` and tests
  it for tautology.
- **Letter order.** `parseLetterOrder("Q.P.R")` and `setLetterOrder(Vector)`
  reorder the sentence letters to match the order the student typed in the
  setup stage. They validate the input with `truerr015` (wrong count),
  `truerr016` (unparsable), `truerr017` (not a letter of this problem) and
  `truerr018` (duplicate), and then recompute the rows.

### 2.3 The UI

`TruthProblemPanel` (`C_k_E`) is `LPTruthAnalysis.problem`. From top to
bottom it contains:

- `questionPanel`, a `TautologyQuestionPanel` (`C_d_`). This shows "Is this
  formula a tautology?", or "Is this argument tautologically valid?", with a
  yes/no `C_b_A` chooser whose index goes into `answer`. It is hidden for
  `taut` problems.
- `table`, a `TruthTableGrid` (`C_XF`). The header row shows the sentence
  letters, then `Pr1..Prn`, then `Conc` (or `Form`). Then there is one
  `CellPanel` per row, holding:
  - the row's letter values as labels;
  - one `TruthTableCell` (`C_GD`) per premise plus one for the conclusion;
  - unless `assumeTautology` is set, a counterexample checkbox (`C_x_B`).
    These checkboxes behave like radio buttons through
    `TruthTableGrid.counterexampleRow`.

  `layoutColumns` sizes every row with one shared column layout (`C_u_`),
  so that the columns line up.
- `cellEditor`, a `TruthCellEditor` (`C_B`) inside `cellEditorContainer`,
  with an OK button bar (`TruthCellEditorButtons`, `C_K`).

`TruthTableCell` is a `JToggleButton`. Only one cell is selected at a time
(`TruthTableGrid.selectedCell`). Selecting a cell shows its two evaluation
trees in the cell editor:

- `valueTree` is a normal `C_VF` tree.
- `mirrorTree` is the same tree with an infix layout (`C_IA`).

The two trees are linked with `C_VF.m1402/m1403`, so editing one updates the
other. In these trees the student gives a T/F value to every subformula,
bottom-up. When the student deselects the cell or presses OK,
`commitTreeValue` → `getTreeCode` → `applyCode` copies the tree's root value
onto the cell. The cell is also flagged wrong if the tree's own
node-consistency check (`C_VF.f848`, maintained by the tree classes in
another group) failed. A wrong cell is painted in the error colors unless
`tableErrorsDisabled` is set.

When a cell is loaded (`loadCode(code, true)`) and the cell's formula is
itself a sentence letter, its value is compared directly with the row's
assignment.

### 2.4 Optional "setup" stage

If the `doSetUp` option selects the problem (`completeSetup`), the student
must first build the table skeleton. This happens in `TruthTableSetupPanel`
(`C_m_F`), driven by `TruthSetupButtons` (`C_RC`), which provides the prompt
and the OK button:

1. **Stage 0.** The student types the sentence letters, separated by periods,
   and the number of rows. `checkLettersAndRows` calls
   `evaluator.parseLetterOrder` and compares the row count with `2^n`
   (`truerr019`).
2. **Stage 1.** The student fills in a T/F chooser for every letter in every
   row. `checkAssignments` compares each chooser with the expected bit,
   `row >> (n-j-1) & 1`, and returns `truerr011` (wrong) or `truerr012`
   (incomplete). When this succeeds, `setupDone` is set and
   `TruthProblemPanel.showTable` swaps in the real grid.

The setup work is saved under `&` as `P.Q.R:TTFTF?...`: the letters, and
after the colon one character per chooser. It is just `P.Q.R` once the setup
is done.

### 2.5 Checking (`LPTruthAnalysis.checkFull`)

The checks run in this order. The first failure wins, and each result
carries a `summary` parameter, shown in the title bar:

1. Setup: the setup panel must exist and be finished (`truerr013`,
   `truerr014`, or the setup error itself).
2. Any cell flagged wrong → `truerr001` "Incorrect".
3. For non-`taut` problems:
   - answer "yes" but a counterexample row is marked → `truerr004`;
   - no answer → `truerr003`;
   - answer "no" but no row marked → `truerr006`.
4. For `taut` problems, or with `doAllRows`, or when the answer is "yes",
   every row must be complete (no `?`) → `truerr002`. The `doAllWffs`
   option relaxes this: without it, a row that already shows it is "valid"
   need not be completed.
5. Answer "yes": every row must be valid (conclusion T or some premise F),
   otherwise `truerr005`. Answer "no": the marked row must be a real
   counterexample, otherwise `truerr008`; with `doAllRows` off it must also
   be complete (`truerr007`).

The row predicates are the static `checkRowError`, `checkRowComplete` and
`checkRowValid`.

Note that `checkFull` never consults `TruthTableEvaluator.rowResults`. It
judges the student's own cell values, and correctness of the cells comes
from the per-cell tree checks. The evaluator's rows are used for the setup
stage, for sentence-letter cells, and by `areEquivalent`.

## 3. Invalidity

The student has to show that a first-order argument is invalid by building a
finite model in which every premise is true and the conclusion is false.

### 3.1 Problem record format (`invwork.txt` / `werewolf`)

| tag | meaning |
|---|---|
| `$` | name |
| `?` | the argument (`unparsed`), parsed into `statement` by `ArgumentParser.m1382` |
| `#` | size of the universe (0 to 16) |
| `=` | interpretations, separated by `.` (see below) |
| `&` | the free-form workspace text, encoded with `C_UB` and then Base64 |
| `e`, `t` | error count, work time |

`removeWork` keeps `$?%u!`.

**Interpretation syntax** (`SymbolInterpretation.parse` / `encode`). Each
entry is `name(arity)` followed by values. Tuples use `ExpressionPath`
notation, `{i,j,...}`.

- **Predicate** (`PredicateInterpretation`, `C_a_C`): the extension is a list
  of tuples. `F(1){0}{2}` means F is true of 0 and 2. `G(2){0,1}{1,1}` is a
  binary relation. A 0-place predicate is a sentence letter:
  `P(0){}` is true and `P(0)` is false.
- **Operation or name** (`OperationInterpretation`, `C_g_E`): `value{tuple}...`
  groups separated by `;`, plus an optional bare default value.
  `f(1)1{0}{2};0` maps 0 and 2 to 1 and everything else to 0. `a(0)2`
  denotes 2. `parse` tells the two kinds apart by whether the text before
  the first `{` or `,` is empty: a predicate starts directly with `{`.
  `groupByValue` inverts the tuple→value table so that `encode` can write
  one group per value.

`restrictToUniverse(size)` is called when the universe shrinks. It drops
tuples that mention elements `>= size`, and default values that are out of
range. `clearValues` wipes the interpretation (this is the "Delete Work"
action). `describeValues(size)` produces the text shown next to each symbol:
`True`/`False`, `{0, 2}`, `{(0,1), (1,1)}`, or `f(0)=1; f(1)=0; ...`.

### 3.2 The UI

`InvalidityProblemPanel` (`C_SA`) contains:

- a "Size of the Universe" chooser (1-16, a `C_b_A` whose `ChoiceListener`
  callback calls `LPInvalidation.setSize`) and a `Universe: {0, 1, ...}`
  label;
- `workspaceField`, a `FormulaEntryField` where the student can rewrite and
  expand formulas;
- `symbolsPanel`, with one `SymbolInterpretationRow` (`C_s_A`) per
  non-logical symbol.

`refresh` rebuilds this panel from the module state. It calls
`LPInvalidation.getSymbolList`, which uses `SymbolCollector` (`C_h_D`) as
follows:

- `collect` walks the premises and conclusion and records every
  `AtomicFormula` (as a predicate) and every `OperationTerm` (as an
  operation) by name and arity;
- `mergeInterpretations` substitutes the interpretations the module already
  has (equality is by name plus arity, see `SymbolInterpretation.equals`);
- `getAllSymbols` returns predicates followed by operations, and this list
  becomes `LPInvalidation.symbols`.

Clicking a symbol's button calls `InvalidityDialogs.editInterpretation`. This
opens an `InterpretationEditor` (`C_e_A`): a grid with `size` columns and
`size^(arity-1)` rows, holding either checkboxes (`C_NE`, for predicates) or
number choosers (`C_d_E`, for operations). "OK" runs `applyToSymbol`, which
writes the grid back into the interpretation.

### 3.3 Model checking (`LPInvalidation.evaluate`)

`evaluate(Expression, C_JD bindings)` is a small recursive model checker over
the universe `{0 .. size-1}`. It returns `Boolean`, `Integer` or `null` (for
"undefined").

- **Bound variables.** A symbol that is bound in `bindings` (`C_JD`, a
  variable→value stack) evaluates to its value.
- **Interpreted symbols.** A symbol that matches an interpretation by name and
  arity has its arguments evaluated to integers, and then
  `SymbolInterpretation.getValue(int[])` is called.
- **Quantifiers.** `@x` (all) and `!x` (some) loop over the universe, binding
  x to each element.
- **Definite descriptions.** `%x` (the unique x such that ...) returns the
  unique witness. If there is no unique witness it returns
  `LPInvalidation.nonidentical`, the conventional "nothing" value.
- **Connectives.** `~ & | -> <->` behave as usual; `=` compares integers.

`checkProblem` evaluates the universal closure of every premise and of the
conclusion. It succeeds when all premises are `TRUE` and the conclusion is
`FALSE`. The Check button (`evaluate()`) shows either "Correct" or a
summary such as `T.F.:T`, with `N` for undefined, in the title bar.

### 3.4 Links to other modules

- **Derivation.** This opens the argument in `LPDerivation`. When a finite
  universe is required it adds the premise `@x(x=a0 | x=a1 | ...)`.
- **Truth Table.** This expands every quantifier over the universe
  (`Expression.m1250(size, nameLetter)`) and opens the resulting
  propositional argument in `LPTruthAnalysis`. The toolbar button currently
  passes `flag = false`, which sends only the workspace selection.
- **Expand.** This replaces the selected quantified formula in the workspace
  with its finite expansion (`Expression.m1251`).

## 4. Recognition problem panel (`RecognitionProblemPanel`, `C_AB`)

This class belongs to `LPRecognition`, but it was partitioned here. The panel
shows an argument (`=`) and asks for the name of the rule that licenses it
(`*`, `answer`). The answer key comes from the matching exercise:

- `@` lists the correct rules;
- `~` lists rules that are close but not right (`nearMissRules`);
- `&` holds the success comment.

`checkAnswer` works through these cases in order:

1. an empty answer (`recnot001`);
2. a rule on the key (correct);
3. a near miss (`recnot006`);
4. "None", which is correct only if no active rule applies
   (`ArgumentParser.m1389(activeRules) != 2`);
5. an unknown rule (`recnot002`) or an inactive rule (`recnot007`);
6. otherwise it matches the rule's schema against the argument with
   `ArgumentParser.m1389(rule)`.

When the rule does not match, `isLenientSinglePremiseRule` lets `EG`, `AV`
and `AV3` produce the "near miss" message instead of the "does not apply"
message.

## 5. Notable details

- `ChoiceListener.choiceChanged(button, oldIndex, newIndex)` is the only
  callback interface of the `C_b_A` choice button. `InvalidityProblemPanel`,
  `TautologyQuestionPanel` and `TruthTableSetupPanel` implement it, and so
  do classes outside this group.
- `InvalidityConstants` (`C_h_B`) is an empty interface that only
  re-exports `LogicConstants`.
- `SymbolInterpretationRow.rowLayout` and `flowLayout` are declared but never
  assigned.
- `TruthTableCell.commitTreeValue` contains an empty
  `if (... charAt(0) == '?') {}`. This looks like leftover code.
- The two dialog helpers and the two toolbars are copy-paste twins. The
  same holds for the other modules' helpers; for example, the derivation
  module's `C_KB` follows the same layout.
