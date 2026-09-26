# The Derivation module

The Derivation module (`LPDerivation`) is the largest of the six modules. It is a
structured editor for Kalish–Montague style natural-deduction proofs: "Show"
lines open subderivations, the student fills in lines with a formula and a
justification, and a finished subderivation is **boxed and canceled** (the word
"Show" is struck through and a bracket is drawn around the body). Correctness
checking of each justification is done by `DerivationLineChecker` (documented
with the rules group). This document covers the editor, the data model, the
file format, the dialogs and the glue.

Names below are those in `mappings/derivation.mapping` (placeholders in
parentheses on first mention).

## 1. Main classes

| class | role |
|---|---|
| `LPDerivation` | The module panel (a `LogicModule`). Holds the problem tree, layout metrics, per-problem options, the static problem list and option selectors. Member names are original (reflection). |
| `DerivationNode` (`C_0B`) | Interface shared by boxes and lines: text access, messages, navigation, numbering, references, checking, serialization. |
| `DerivationBox` (`C__`) | One (sub)derivation. Extends the collapsible container `C_v_A`. Child 0 is the Show line; later children are lines and nested boxes. |
| `DerivationLine` (`C_G`) | One row: number label, formula editor, annotation (justification) editor, message pane and "?" button. |
| `LineReference` (`C_AA`) | A line number cited inside an annotation, tracked live so it can be renumbered. |
| `DerivationLineEditor` (`C_l_E`) | The `FormulaTextPane` used for both the formula and the annotation of a line; all keyboard navigation lives here. |
| `LineLabel` (`C_t_E`) | The "Show"/"Problem:" label and the line-number label; can draw a strike-through (`canceled`). |
| `LinePanel` (`C_M`) | `SizedPanel` used for rows, spacers and the number column. When empty it paints the segment of the box bracket that passes it. |
| `DerivationBoxLayout` (`C_DF`) | Lays out a box, vertically centers the `BoxToggleButton` on the Show line and computes `bracketBounds`. |
| `DerivationDialogs` (`C_KB`) | All static dialogs: messages, problem choosing/saving/deleting/printing, the rule-instantiation queries, Strategic Advice, the Inference Rules list. |
| `DerivationToolbar` (`C_PE`), `DerivationMenuBar` (`C_R`) | Buttons (Select, User, Check, Print, Rules, FAQ, Advice, Upload ...) with key bindings, and the Problem/Program menus. |
| `DerivationKeypad` (`C_BA`), `KeypadGrid` (`C_JF`), `KeypadKey` (`C_q_D`) | Clickable keypad popup for a line editor. |
| `TermOccurrenceSelector` (`C_h_F`) + subclasses | "Highlight the occurrences and press Alt+1" fields used by the EG, LL and EL queries. |
| `DerivationProblemSet` (`C_MD`), `DerivationProblemEntry` (`C_EE`), `DerivationMessage` (`C_n_`) | Module-specific `ProblemSet`, `ProblemEntry` and message catalogue (`derMessages` = `zombie.txt`). |
| `DerivationProblemsPage` / `DerivationResultsPage` / `DerivationStatementsPage` (`C_MC`/`C_Z`/`C_g_A`) | Print jobs for Print / Print Results / Print List. `TextFilePrintJob` (`C_v_B`) prints the advice file. |

## 2. The derivation tree

The window's `problemPanel` holds a column of line numbers (`LPDerivation.numbers`)
next to the root `DerivationBox` (`LPDerivation.problem`). The root box's Show
line is the "Problem:" line holding the argument, e.g. `~Q .: (P->Q)->~P`;
`LPDerivation.parseProblem` splits it into `premises` and `conclusion`.

A `DerivationBox` has:

- `showLine`: its first child, always a `DerivationLine` with a `showLabel` and
  no annotation editor. The formula is what the box is supposed to show.
- body children: `DerivationLine`s and nested `DerivationBox`es. The toggle
  arrow buttons (`ExpandToggleButton`, `C_ZD`) are also children, so indexes
  are translated by the base class; `getNode(i)` returns the i-th real node.
- `cancelLine`: the last line when the box has been boxed and canceled. That
  line has no formula, only an annotation such as `6 5 ID`, and it is stored
  with the `#` tag.
- `parentBox`, `module`, and scratch state filled in during checking:
  `assumptionType` (index into `ASS_STR` = D/I/C/B: which kind of assumption
  opened the box), `assumedSide` (which side of a biconditional was assumed,
  -1 if none, 2 = "either"), `boxVariables` (variables that occur in the box,
  for the UD restriction) and `strategyConsistent` (false once a line in the
  box breaks the serial-check assumptions).

Lines are converted in place. `makeShowLine` turns an ordinary line into the
Show line of a new box that replaces it in its parent. `makePlainLine` does the
opposite and moves the box's body out. `boxAndCancel`/`uncancel` add or remove
the cancel state. `indentIntoOpenBox`/`outdentFollowingLines` (Alt+Right and
Alt+Left) move lines across box boundaries. Typing `Show P` in the formula
field (`applyShowPrefix`) is the same as pressing Ctrl+S.

**Numbering.** `renumberLines(start)` walks the tree depth-first. Each line
takes the next number, so a box's Show line is numbered before its body.
`findLine(n)` does a binary search over a box's children by line number and
then recurses.

**Navigation.** `getNextNode`/`getPreviousNode(visibleOnly)` walk the tree in
display order and skip collapsed boxes when asked. The collapse state is the
`C_v_A` "expanded" flag. `areEnclosingBoxesExpanded`/`expandEnclosingBoxes`
make sure a focused line is visible.

## 3. Line references

When the annotation of a line is committed (`parseReferences`), every run of
digits that is not glued to an operator character (`isOperatorChar`) becomes a
`LineReference`: `offset` is the distance from the end of the previous
reference, `length` is the text length, `target` is the cited node and
`source` is the citing line. The source keeps the list in `references`; the
target keeps the back-links in `referrers`.

- Relative references such as `-2` are turned into absolute numbers first
  (`resolveRelativeReferences` / `getRelativeNode`).
- After lines are inserted or removed, `refreshReferenceNumbers` rewrites the
  digits in every annotation from the targets' current numbers. It also shifts
  the editor's saved selection (`adjustSavedSelection`) so the caret does not
  jump.
- Deleting a line calls `detachReferrers`. Every annotation that cited it now
  shows `<deleted>` in place of the number.
- `canUse(node)` enforces the scope rule. A line may cite an earlier line only
  if that line is in the same box or in an enclosing box, or if it is a whole
  earlier sibling subderivation. The error text of the fallback branch still
  names the original method, `LPDerLine.canUse(ILPDerLine)`.

## 4. Checking

Checking runs in numbered **phases** (`LPDerivation.setPhase`). A line keeps
one message at a time. A new message replaces the current one only when no
message is shown or the new phase is lower, so a parse error (phase 1) hides a
later rule error.

| phase | done by | work |
|---|---|---|
| 1 | `parseFormula` | Parse the formula with `LogicProgram` → `FormulaParser`. Failures give `dererr059`/`dererrtxt`. Sets `syntaxOk`. |
| 2 | `parseReferences` | Build the `LineReference`s. |
| 3 | `checkLine` | Check the justification with `DerivationLineChecker`. |
| 4 | `DerivationBox.verify` | Check the box structure and the whole problem. |

Leaving a field triggers checks. Focus loss on the formula runs `parseFormula`
and then `checkRedundantShow`, which gives the notice `dernot053` "Why use a
Show line ... it follows directly from line n". Focus loss on the annotation
runs `parseReferences`. In **command mode** (the default; turned off with the
`noCommand` option), pressing Enter runs `commitEdit`, which checks the line at
once. If the line's rule is a box-and-cancel rule (CD, ID, DD, UD, BD) and
nothing follows it, `readyToCancel` is set and the box is canceled
automatically.

`checkLine(interactive)` creates a `DerivationLineChecker` and loops over the
justifications queued on the line (**queued mode**, e.g. `PR1 PR2 MT`
followed by further steps). Each step's parsed `Justification` is cached in the
line's `justifications` vector and saved with the `:` tag. When a rule's
premises match but the conclusion does not, the user gets `dernot100`/`101`
with the rule form.

**Check** (`LPDerivation.checkProblem`) sets `serialMode` and calls
`problem.checkSyntax() & problem.verify()`:

- The root box needs a parsed conclusion, and one of its body lines must equal
  it (`dererr052..054`).
- Each sub-box must have a `cancelLine` (`dererr055`).
- `verify` recurses into every node. Each line re-runs `checkLine(false)`.
- In serial mode the interactive queries cannot open. They fail with
  `dererr064` and mark the problem incomplete instead.
- In serial mode an error inside a collapsed box also puts `dererr078`
  ("incorrect subderivation") on the enclosing Show line (`flagEnclosingBox`),
  so errors cannot hide in collapsed boxes.
- The result goes into the title panel as Correct, Incorrect or Incomplete
  (`derinf005`, `dererr057`, `dererr058`).

**Variables.** `collectVariables`, `addShowVariablesToModule` and
`exportVariablesToParent` gather the variables of each line into the module's
`varNames` and the box's `boxVariables`. `isUniversalVariableFree` implements
the UD restriction: the variable of an `@x` Show line must not occur in any
enclosing box.

**Messages.** `showMessage(id, checker, phase, params)` looks the id up in
`DerivationMessage`, colours the message pane (error or info), counts errors
in `LPDerivation.errorCount` and fills `<param>` placeholders. The parameter
values come from `MessageParamSource.getParamValue` (`C_k_A`), which
`DerivationLine` and `DerivationLineChecker` both implement. Supported
parameter names include `line number`, `wff`, `show`, `premises`, `premise N`,
`branch lines left` and `arg N`. The "?" `MessageDetailsButton` opens the long
explanation (`x` field) in a `MessageDialog` together with any action buttons
(`b` field).

## 5. Interactive rule queries (`DerivationDialogs`)

Some rules need extra input before they can be checked. The dialog methods
still carry the original action names, which appear in the message file's
button specs:

- `instanceSchemeQuery`: asks for a scheme of substitution for a rule form
  (`derdlg007`/`019`).
- `universalTermQuery` (UI), `existentialVarQuery` (EI), `dummyVarQuery` (EG
  variable): single text fields.
- `generalizationTermQuery` (EG), `leibniz12TermQuery` (LL1/2),
  `leibniz34TermQuery` (LL3/4), `eulerTermQuery` (EL): the user selects
  occurrences in a `TermOccurrenceSelector` and presses Alt+1. Each selection
  is replaced by the placeholder `{1}`; Ctrl+Z undoes a replacement through
  `PlaceholderEdit`. Each subclass maps the failure cases (not well formed, not
  a term, not a name letter, bound occurrence, different term) to its own
  `dernotNNN` message. A `SubstitutionValuesPanel` shows the chosen value for
  each placeholder.
- `interchangeFormulaQuery`, `interchangeRuleQuery`, `cieRuleQuery` and
  `applyInterchangeRule`: IE and CIE (interchange of equivalents). The user
  selects a subformula, names a rule, theorem, premise or line, and optionally
  a condition.
- `chooseFormula`, `chooseSideToShow` and `chooseRuleInstance`: radio-button
  choices built from `RadioGroupPanel`, `RadioOptionRow` and
  `DialogRadioButton`.

The problem-level dialogs follow the pattern used in the other modules:
`confirmSaveChanges`, `chooseProblem`, `openNextProblem`,
`chooseProblemsToSubmit`/`Upload`/`Print`, `deleteProblemsDialog`,
`enterUserProblem` and `enterSubmittedProblem` (instructors only).
`showInferenceRules` lists the rules and theorems and ticks the ones enabled
for the current problem. With no problem open it uses the non-modal
`RuleListDialog`. It also lists user rules: a `UserRule` (`C_DB`) is made from
a solved problem whose name starts with `UR`, and its schema is that
problem's argument.

## 6. Data format

A derivation is saved as one `TaggedRecord` line (`encodeWork`). It is
rebuilt by `LPDerivation.loadProblem`, which walks the tags in order and keeps
track of the current box:

| tag | meaning |
|---|---|
| `$` | problem title |
| `-` / `+` | a Show line. The first one is the problem statement. Later ones open a sub-box; `+` means the box is collapsed. |
| `<` | an ordinary line's formula |
| `>` | that line's annotation (justification) |
| `#` | a cancel line (annotation only), which also closes the current box |
| `=` | closes a box that was not canceled |
| `:` | a cached `Justification` of the previous line, `n:...` |
| `s` | the "command log" of a Show line: the command that created it, e.g. `"SHOW CONC"` |
| `m` | a saved message `lineNo:text` (`encodeMessages`) |
| `e`, `t` | error count and work time in seconds |
| `%`, `!`, `C`, `?`, `p` | problem options (`eg` = worked example), instructions text, state, and so on |

Example from `syntax2/ghoul.txt` (the worked example Deriv 1.001EG2):

```
Deriv 1.001EG2`$ ~Q .: (P->Q)->~P`-(P->Q)->~P`-"SHOW CONC"`sP->Q`<ASS CD`>~Q`<PR`>~P`<3 2 MT`>4 CD`#`=eg`%...
```

Annotations are stored in "rob" notation and formulas in Maggie-talk. Both are
converted to the display symbol table with `LogicProgram.m995`.

## 7. Layout

`LPDerivation.setWidths` computes the geometry from the font size:

- `indent` = fontSize·10/7, and spacers of indent/4.
- A 5-column `widthInfo` table: number, formula, annotation, message, "?". It
  gives a fixed part in indents and a stretch weight for each column.
- The formula column grows with the maximum box nesting depth
  (`getMaxBoxDepth`).

`layoutColumns` pushes the widths into each row's `C_u_A` column layout. The
root "Problem:" line uses `problemWidths`, which merge the formula and
annotation columns.

Colours come from `LPDerivation.colors`: 0 = foreground, 1 = background,
2 = field, 3/4 = error background/foreground, 5/6 = accent. The focused editor
is drawn inverted, and `highlightShowLabel` inverts the Show label of the box
that contains the focus.

## 8. Notable details

- `LineLabel.canceled` draws the strike-through word by word at 7/16 of the
  text height. This is how a canceled "Show" is displayed.
- `DerivationLine.m545` (`isDerivationLine`) always returns true and has no
  callers. `LineFormula` (`C_ZA`) is unused. So are a few flags:
  `DerivationLine.unusedFlag`, `DerivationKeypad.unusedFlag`,
  `DerivationLineEditor.unusedCounter` and `UNUSED_FLAG`.
- `TermOccurrenceSelector$1.onFocusGained` is not a real `focusGained`
  override. The obfuscator renamed it, so the anonymous `FocusAdapter` never
  calls it.
- `DerivationMessage.format` returns the raw text without `<param>`
  substitution unless the problem enables `doSubs`.
- Loading work sets `LPDerivation.restating`, which suppresses all line
  messages. `DerivationProblemSet` replays every saved problem this way in a
  hidden `LPDerivation(false)` to compute problem states.
