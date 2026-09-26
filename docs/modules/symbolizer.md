# Symbolization module (`LPSymbolizer`) and the formula text widgets

Names come from `mappings/core.mapping` and `mappings/symbolizer.mapping`. The
placeholder name is given in parentheses the first time a class is mentioned.

The Symbolization module teaches students to translate English sentences into
formulas. The student does not type a formula. Instead they break the sentence
down step by step into a **symbolization tree**. Each node holds a piece of
English. The student chooses its main connective, and the program then creates
child nodes for the parts, which the student fills in with the English of each
part. The tree is checked by comparing it node by node with one or more stored
answer trees. Truth-functional equivalence is used as a fallback.

## 1. Classes at a glance

| class | role |
|---|---|
| `LPSymbolizer` | The `LogicModule` panel: static problem lists, options, load/save/submit, check. Member names are original (reflection). |
| `SymbolizationConstants` (`C_v_D`) | Node kinds `NONE`=0 ... `SYMBOLIC`=11, and the per-kind tables `connSymbol`, `connMenu`, `connHover`, `connWords`, `connOutTypes`, `connArgTypes`, `connChaps` |
| `SymbolizationNode` (`C_d_C`) | One node of the tree. The root node also holds the problem's metadata (name, statement, scheme, answers). |
| `SymbolizationTextPanel` (`C_x_C`) | Header of a node: the English text pane, optional `(` `)`, and the connective popup menu |
| `SymbolizationTextPane` (`C_x_E`) | The English text pane. Keyboard shortcuts apply connectives and move around the tree. |
| `SymbolizationConnectivePanel` (`C_VE`) | Shows the connective symbol plus a bound variable or atomic expression. Hosts the "Error" buttons. |
| `SymbolizationErrorButton` (`C_f_A`), `SymbolizationHint` (`C_CD`) | Error and hint explanations (`symerr00x`, `symnot001`) |
| `NodeMatchListener` (`C_i_D`) and `MatchCounter` (`C_0F`), `HintCollector` (`C_JA`), `ErrorMarker` (`C_r_C`) | Visitors for the tree comparison |
| `ChildRecordSnapshot` (`C_TF`) | Keeps the children's work when the student changes a node's connective |
| `AnswerSet` (`C_WD`) | (problem name, Vector of answer records) |
| `SymbolizationProblemSet` (`C_h_C`), `SymbolizationEntry` (`C__C`) | The module's `ProblemSet` / `ProblemEntry` subclasses |
| `SymbolizationMessages` (`C_h_E`) | Message catalogue `symMessages` (`demon.txt`) |
| `SymbolizationDialogs` (`C_WB`) | All static dialogs: choose, delete, submit, print, user problem, scheme editor, Answer Manager, direct entry |
| `SymbolizationToolbar` (`C_b_B`) | The button bar at the bottom |
| `SchemeEditor` (`C_y_B`), `SchemeCellPane` (`C_JC`) | Two-column "symbol : English" table for the symbolization scheme |
| `NodeMessageHandler` (`C_RE`), `SchemeDialogHandler` (`C_r_E`), `UserProblemDialogHandler` (`C_I`), `AnswerManagerHandler` (`C_QD`) | `DialogHandler` subclasses for the button actions of those dialogs |
| `AnswerListLabel` (`C_QC`) | Row label in the Answer Manager list |
| `AnswerPrinter` (`C_PD`), `SymbolizationProblemPrinter` (`C_TC`), `StatementListPrinter` (`C_WF`), `SymbolizationResultsPrinter` (`C_c_A`) | Print jobs (subclasses of the `C_CA` page base). Each one's `printSelected(int[])` queues a job on `LPSymbolizer.printQueue`. |

## 2. Window layout

`LPSymbolizer(boolean)` builds a `JSplitPane`:

- **left**: the `scheme` `SchemeEditor` (read-only here), which shows the
  sentence-letter / predicate dictionary for the problem;
- **right**: on top, `symbolized`, a read-only `StyledTextPane` that always
  shows `problem.toString()`, the formula built so far
  (`updateSymbolization`). Below it, the scrollable tree whose root is
  `problem` (a `SymbolizationNode`).

The title panel (`ProblemTitlePanel`) shows the name and statement, and a
status line: "Correct", "Incorrect", "Answer Not Available" and so on. The
toolbar sits at the bottom.

## 3. The symbolization tree

Every `SymbolizationNode` is a `GridBagLayout` panel. Row 0 holds its
`textPanel` (the English), and row 1 holds three `slots` (`putSlot`). The
`connective` field is one of the `SymbolizationConstants` kinds, and it sets
the slot contents in `setConnective(kind, label, showErrors)`:

| kind | slots 0 / 1 / 2 | printed form (`toString`) |
|---|---|---|
| `NONE` (0) | (none) | the English text itself |
| `NEGATION` (1) | symbol / - / child | `~A` |
| `IMPLICATION`..`EQUIVALENCE` (2-5) | child "(" / symbol / child ")" | `(A -> B)` |
| `UNIVERSAL`, `EXISTENTIAL`, `DESCRIPTIVE` (6-8) | symbol+variable / - / child | `@x A`, `!x A`, `%x A` |
| `EQUATION`, `MEMBER` (9, 10) | child / symbol / child | `a = b`, `a [m] b` |
| `SYMBOLIC` (11) | glue / atomic expression / glue, with 0..n term children | `Fxy`, `F(ab)`, or `P` |

`outType` and `argTypes` record whether a node yields and accepts a formula
(`TYPE_FORMULA`), a term (`TYPE_TERM`) or either (`TYPE_ANY`). The values come
from `connOutTypes`/`connArgTypes`. `setConnective` refuses a kind whose output
type does not fit the parent's slot (message `symnot011`). For binders it
proposes a fresh variable with `suggestBoundVariable`: the first letter of
`xyzuvwlmnopqrst` that no enclosing binder uses. For an atomic expression it
asks with `SymbolizationDialogs.askForSymbol`. The atomic text is parsed to
decide whether it is a term or a formula. It may also carry an explicit
`{argTypes}outType` suffix, as in the stored form below.

When a node already has children and its kind changes, a
`ChildRecordSnapshot` captures each child's type and serialized subtree. After
the rebuild it restores those children whose types are still compatible, so
the student's work below the node survives.

**Editing keys** (`SymbolizationTextPane`). Ctrl+Shift plus the letter shown in
`connHover` applies a connective: N=negation, C=conditional, A=and, O=or,
B=biconditional, U=universal, E=existential, D=description, `=`=equality,
M/Enter=member, @/2=atomic, T=truncate. Adding Alt (Meta) also copies the text
to the clipboard. Ctrl+Shift+/ asks for a hint. Alt+arrows move to the parent,
the first child or a sibling. Plain Enter, or a right click, opens the
connective popup menu (`SymbolizationTextPanel.showConnectiveMenu`). The
English text is normalized with `collapseWhitespace`, which collapses runs of
whitespace other than newlines and keeps the selection indexes in step.

**Direct entry** (`enterDirectSymbolization`, the "Direct" button) lets the
student type a whole formula. `buildFromText` parses it and
`buildFromExpression` rebuilds the tree from the `Expression`. If the resulting
formula equals the closest answer's formula (`Expression.m1236`, compared up to
bound variables), `copyTextFrom` copies that answer's English into the new
nodes, with bound variables renamed.

## 4. Data formats

A symbolization problem, and the student's work on it, is a single
`TaggedRecord` line. Tags:

| tag | meaning |
|---|---|
| `$` | problem name |
| `-` | English statement (only when the `+` records hold work) |
| `+` | one record per tree node, in pre-order: `code:English` |
| `=` | scheme: `symbol:English.symbol:English...` (the `.` inside a value is escaped as `\.`) |
| `@` | dot-separated **answer keys**, looked up in the answer table |
| `g` | answer-group key (problems that share their answers; see duplicates below) |
| `o` | original problem name, when the work was saved under a new name |
| `%` | per-problem options; `eg` marks a worked example (`dontChange`) |
| `!` | comment. `C`: common name. `e`/`h`/`t`: error count, hint count, seconds worked. |

The node `code` (`getNodeCode`) is the `connSymbol` of the kind. For binders
the variable follows, as in `@x`. For atomic nodes it is `*` + expression +
`{argTypes}` + outType, for example `*W{}0` (sentence letter W, formula) or
`*F{11}0` (two-place predicate taking two terms). A bare problem is stored as
`?:English` (kind NONE). Real lines from `syntax2/devil.txt` and
`syntax2/mummy.txt`:

```
Symb 1.004`$?:The Bruins go to the Rose Bowl provided that the Trojans don't win`+U:  the Bruins go to the Rose Bowl.     T:  the Trojans win.`=1\.004`@Symb 1.004`C
1.004`$->:The Bruins go to the Rose Bowl provided that the Trojans don't win`+~:the Trojans don't win`+*T:the Trojans win`+*U:the Bruins go to the Rose Bowl`+
```

The answer record is itself a tree: `(~T -> U)`. `readNodes` rebuilds a tree
from the `+` values recursively. `toRecord(true)` writes one back out. The
statement is omitted when the tree is still the untouched statement.

**Files.**

- `symwork.txt` (`devil.txt` in `ruleDir`/`local`) holds the exercises, and
  `work/symwork.txt` holds the student's work.
- `symAnswers` (`mummy.txt`) is the official answer table, loaded into
  `LPSymbolizer.answers`. Each line is keyed by its name field.
- `work/keywork.txt` is `LPSymbolizer.userKey`: answers that the student or
  instructor entered for **user problems**. `useUserKey` /
  `userAnswerKey` choose which table `lookupAnswers` uses.
- `work/symdata.txt` is the submission log.

## 5. Checking (`LPSymbolizer.checkProblem`)

1. `clearErrors` removes the old error buttons. If there are no answers
   (`countAnswers() == 0`), the check stops and the status stays "Answer Not
   Available".
2. If any node is still `NONE` (`isIncomplete`), the result is **Incomplete**.
3. `findMatchingAnswer` compares the tree **structurally** with every answer of
   every `AnswerSet` (`matchTree`). Two nodes match when:
   - they have the same kind. A `NONE` node in the answer matches anything,
     which lets answers leave parts unanalysed.
   - for atomic nodes (`atomicMatches`), the out/arg types agree and the two
     atomic expressions are equal. This test runs after the answer's bound
     variables are renamed to the student's (`translateExpression`). The two
     parallel `Vector`s of "binders" are the variable names bound on the path
     from the root, one list for each tree. Renaming wraps the expression in
     universal quantifiers, rebinds, renames and unwraps. It fails when a
     variable would be captured (`hasBindingConflicts`).
   On a match the status is **Correct**. It is **Duplicate of X** when another
   problem of the same answer group (`answerGroup`, from the `g` tag) was
   already solved correctly with the same answer (`findDuplicateSolution`).
4. Otherwise `findEquivalentAnswer` converts both trees to `Expression`s
   (`toExpression`, which parses `toString()`) and tests them with
   `areEquivalent`. That method builds `A <-> B`, abstracts it to its
   sentential form (`Expression.m1248`) and asks the truth-table evaluator
   `C_HA` whether it is a tautology. So equivalence here is **truth-functional
   only**. The status is "Correct Equivalent" when the `equCounts` option
   covers the problem, and "Equivalent, but Incorrect" otherwise.
5. Otherwise the result is **Incorrect**. `findClosestAnswer` picks the answer
   with the most matching nodes (`countMatchingNodes`, using a
   `MatchCounter`). `matchTree` is then run against it with an `ErrorMarker`.
   On each mismatch, the enclosing node's connective panel gets a
   `SymbolizationErrorButton` (`showError`). If the mismatch is an atomic term
   that failed only because of variable binding, the binder nodes that caused
   it get error buttons too. `ErrorMarker` also reports "Quantifier
   Unrestricted". This covers a universal whose body should be a conditional,
   or an existential whose body should be a conjunction, where the student
   chose the right quantifier but the wrong connective under it.

Clicking an error button shows `symerr001` (wrong connective), `symerr002`
(right connective, wrong variable or atom) or `symerr003` (variable not
translatable). The dialog's `NodeMessageHandler` offers **Up** (explain the
parent node), **Text** (copy the answer's English, with variables renamed by
`substituteVariables`) and **Symb** (apply the answer's connective or atom).

**Hints** (Ctrl+Shift+/, `showHint`) run `matchTree` from the root with a
`HintCollector` for the focused node. If that node lines up with the closest
answer, a `SymbolizationHint` (`symnot001`) names the right connective ("right
statement", "right type"). If it does not, the collector shows an error button
on the nearest ancestor. `hintCount` and `errorCount` are saved in the record
(`h`/`e`) and sent with the submission as `help_count`.

**State for problem lists and submission.** `SymbolizationNode.evaluateWork`
(called from `LPSymbolizer.getProblemState`) runs the same structural and
equivalence tests without the UI. It stores the matched answer index in
`SymbolizationEntry.answerIndex` and sets `state` to 2 (correct) when the match
is not a duplicate, or 1 (incorrect) otherwise.

## 6. User problems, schemes and the Answer Manager

- **User** (`createUserProblem`) asks for an English sentence
  (`UserProblemDialogHandler`, validated by
  `LPSymbolizer.validateUserProblem`). It then opens the scheme dialog
  (`showSchemeDialog`, `SchemeDialogHandler`). **Browse** in that dialog copies
  the scheme of another problem (`chooseScheme`).
- The **Edit ▸ Statement / Scheme / Answers** menu is only present when
  `ServerConnection.f480` is set, which looks like an instructor/developer
  flag. The **Answer Manager** (`showAnswerManager`, `AnswerManagerHandler`)
  lists the answers of the current problem. **Add** stores the current tree
  under a new key `name-N` in `userKey` and appends the key to the `@` list.
  **Use** loads an answer into the workspace. **Delete** and **Replace** edit
  the list. Answers of built-in exercises cannot be edited directly.
  `copyAnswersToUserKey` first copies them into the user key and detaches the
  problem from its original (`originalName`).
- **Right-click on User** (with the `workEntry` credential) pastes a submitted
  work record for grading (`enterSubmittedProblem`).

## 7. The shared formula text widgets

These classes are used by every module, not just this one.

- **`StyledTextPane`** (`C_e_E`) is a `JTextPane` with the program font and no
  default colours. `setTextForeground`/`setTextBackground`/`setUnderlined`
  apply a character attribute to the whole document and the input attributes.
  `wrapLines == false` makes the pane grow horizontally inside a viewport
  instead of wrapping; `inWidthCheck` guards against re-entry.
  `replaceRange`/`insertText` edit the document directly. Its static
  initializer removes Swing's default Ctrl+Shift+O binding, because that
  shortcut means "or".
- **`EditableTextPane`** (`C_p_A`) adds min/max width (`setFixedWidth`), select
  all on Ctrl+A, Tab and (for single-line panes) Enter as focus traversal,
  clipboard helpers and an `UndoManager` (`undo`/`redo`).
- **`FormulaTextPane`** (`C_s_D`) is the formula editor:
  - `shortcutSymbol` maps Ctrl+Shift+A/B/C/D/E/I/N/O/T/U/Enter to
    `& <-> -> % ! <> ~ | .: @ [m]` in the current display symbols.
  - Option+1..9 (and the Mac Option characters `¡™£¢∞§¶•ª`) insert schematic
    letters.
  - Ctrl+B selects the innermost enclosing bracket pair and widens the
    selection each time it is pressed (`selectEnclosingBrackets`, with
    `findOpenBracket`/`findCloseBracket` over `({[` / `)}]`).
  - Ctrl+E widens the selection using `C_DD`. This appears to select the
    enclosing well-formed subexpression.
  - A right click calls `showKeypad`.
- **`FormulaEntryField`** (`C_IF`) is a borderless `FormulaTextPane`. Its
  `showKeypad` opens a `SymbolKeypadDialog` (`C_a_E`) under the field: rows of
  connectives, sentence letters, brackets, predicate and name letters,
  variables and digits, plus space/backspace/copy/paste. Each row is built by
  `C_JF` and shows the Ctrl+Shift shortcut as a tooltip. `ownerDialog` is set
  when the field sits inside a `MessageDialog`, so the keypad gets the right
  parent.
- **`KeypadDialog`** (`C_T`) is the base popup. It implements `KeypadTarget`
  (`C_r_B`), which is the interface the keypad buttons (`C_JF`) use to read the
  target's text and selection and to insert or delete text. `translateKey` maps
  button names to text ("paste" reads the clipboard; "copy" and backspace are
  handled directly). The popup closes on Esc, on losing focus, or when another
  window is activated, and then returns focus to the target (`refocusTarget`).
  `C_BA`, the derivation keypad, is the other subclass.

## 8. Notes and oddities

- The root `SymbolizationNode` doubles as the "problem" object: it carries
  `problemName`, `statement`, `scheme`, `answerKeys`, `answers` and
  `answerGroup`, while every other node leaves those fields unused.
- Answers are whole symbolization trees, not formulas. A student can
  therefore be told "Incorrect" for a formula that is equivalent (even
  identical) when their decomposition differs, unless `equCounts` is on.
  Equivalence is truth-functional only, so quantified parts must match as
  opaque atoms.
- `isNonPredicateSymbol` (`FGHIJKLMNO` test) is computed in `setConnective`,
  but its result is discarded, so it is dead code. `removeUserAnswers`,
  `bindsSameVariable` and `isTranslatable` have no callers.
  `SymbolizationTextPanel.connectiveMenu` (`C_NB`) is never created, since the
  popup `C__E` is used instead, and `UNUSED_FLAG` is never read.
- `SymbolizationTextPane.keyPressed` still contains a debugging
  `System.out.println` for Alt+Left.
- `insertDBProbs`/`updateDBProbs` build raw SQL `insert into logic_problem ...`
  strings and send them to the server's SQL generator. This is a developer-only
  path, gated by the `addToDB`/`updateDB` options and the "developer"
  credential.
