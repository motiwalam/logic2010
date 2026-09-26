# Parsing module and the generic UI toolkit

This document covers two things that were grouped together during naming:

1. **The Parsing module** (`LPParsing`). Students decide whether a string is a
   formula in official notation, a formula in informal notation, or not well
   formed. For well-formed formulas they then take the formula apart by
   clicking its main connectives, which builds a parse tree.
2. **Shared Swing infrastructure** used by every module: the truth-value tree
   widgets, the printing framework, custom layout managers and a set of small
   widgets.

Names are the ones in `mappings/parsing.mapping`. The placeholder name is given
in parentheses the first time a class is mentioned.

---

## Part 1: the Parsing module

### Classes

| class | role |
|---|---|
| `LPParsing` | The `LogicModule`. It holds the static problem list and one instance per open window. Options are read from the `parsing` option record. Its members keep their original names because the framework reaches them by reflection. |
| `ParsingStartupTask` (`LPParsing$C__A`) | Startup runnable. It calls `allocateParModule` and then `continueStartup`. |
| `ParsingProblemSet` (`C_ZC`) / `ParsingProblemEntry` (`C_l_D`) | The problem list and its entries. `ParsingProblemEntry.newProblemNames` lists problems that are not yet in the stored `parwork.txt`. |
| `ParsingMessage` (`C_ND`) | Message catalogue `parMessages` (`shade.txt`), with ids such as `parerr001`. |
| `ParsingDialogs` (`C_KA`) | Static dialogs: select, select next, delete, submit, upload, print, "User Problem" and "Submitted Problem". |
| `ParsingToolbar` (`C_RB`) | Button bar. It also binds Ctrl+O/N/K/S/P and Alt+F4. The buttons are `ActionButton`s: left click runs the primary action and right click runs the secondary one (for example Select versus Select Next). |
| `ParsingProblemPanel` (`C_y_D`) | Content of one parsing window. It holds a `NotationChooser` and a `ParseTreePanel`, loads a problem record, checks it and writes it back. |
| `NotationChooser` (`C_f_D`) + `NotationRadioButton` (`C_j_`) | The three radio buttons "Official Notation", "Informal Notation" and "Not Well Formed", which map to the codes `O`, `I` and `N`. There is also a result label. |
| `ParseTreePanel` (`C_EB`) | A status label ("Complete", "Incomplete", "Correct" ...) above the root `ParseTreeNodePanel`. |
| `ParseTreeNodePanel` (`C_AD`) | One node of the tree. It shows its formula text and its child nodes, and draws the connecting lines itself. |
| `ParseTreeFormulaText` (`C_k_`) | The clickable formula text of a node. |
| `ParseTreeLayout` (`C_YF`) | Layout for a node: the formula goes on top and the visible children go in a row underneath. |
| `FormulaParseNode` (`C_DD`) | The model underneath every tree. It maps each subexpression to its character range in the displayed text. |
| `ParsingStatementsPage` (`C_AE`), `ParsingResultsPage` (`C_o_E`) | Printouts for "Print List" and "Print Results". |

### FormulaParseNode: character ranges for subexpressions

`FormulaParseNode` wraps an `Expression`. Its constructor calls
`Expression.m1211(node)`, and each concrete `Expression` subclass fills in the
node's `children`, `offset` and `length`:

- `offset` is the position of the child relative to its parent;
- `length` is the size of the subexpression.

Both values are measured in the *stripped* text, which has no parentheses and
no spaces. Only the root keeps the actual display `text`. That text is either
the informal rendering (`Expression.m1207`, minimal parentheses) or the official
one (`m1209`, fully parenthesized), depending on `informal`.

`prepareText` builds two arrays over the display text, each with one entry per
character position:

- `strippedIndex[i]` is the index in the stripped text of display position `i`
  (`computeStrippedIndex`). It uses `LogicProgram.m996`, which applies string
  replacements while remapping an array of positions.
- `parenDepth[i]` is the parenthesis nesting depth (`computeParenDepth`).

`findDisplayRange(strippedIndex, parenDepth, start, len, widen)` turns a
stripped range back into a display range. It finds the display positions at
the minimum depth that cover the range, and it can optionally widen the result
to the enclosing parentheses (`widenToParens`). `getTextRange()` uses it to
answer "where does this subexpression appear on screen".

Ranges are handled as interval sets (`C_n_F`, a sorted list of boundaries).

- `getRange` is the node's own span.
- `getOperatorRanges` is that span minus the spans of its children. What is left
  is the main connective, together with any quantifier or predicate symbols of
  that node.

`ParseTreeFormulaText.getOperatorPixelRanges` converts these ranges to pixel x
positions with `LogicTextArea.getCharLocation`. The result is used for two
things: to decide whether a click hit the main connective, and to find where
the tree lines should start (`AnchorProvider.getAnchorX`).

Other helpers:

- `getNotationCode()` compares the displayed text, minus spaces, with the
  official rendering. It returns `O` or `I`, or `N` if the string failed to
  parse (`parseFormula` returns null).
- `getStructureString()` gives the correct fully expanded tree shape, written as
  `childCount,child1...,child2...`. A leaf is `0`.
- `checkParenthesization` checks the informal-notation parenthesis rules:
  outer parentheses may be dropped, `&` and `|` chains may be flattened on the
  left, and `->` and `<->` need parentheses under a binary parent.
- `findLetterNodes`, `findTermNodes`, `addLetterRanges` and `toStyledText` are
  used by other modules to highlight occurrences of letters or terms.

### Interaction and checking

When a problem is loaded, `ParsingProblemPanel.loadRecord` shows the statement
as a single unexpanded root node. The student then does two things:

1. **Chooses a notation** in the `NotationChooser`. If `checkNow` (the
   `autoCheck` option) is on, the choice is checked immediately against
   `getNotationCode()`, and the tree panel is shown only when the answer is
   right. If "Not Well Formed" is chosen, the tree is cleared.
2. **Expands the tree.** A click on a node's formula text
   (`ParseTreeFormulaText.mousePressed`) is tested against that node's operator
   ranges:
   - A hit expands the node (`setExpanded(true)`, which shows the child panels
     created earlier from the `FormulaParseNode` children) and briefly flashes
     the symbol green.
   - A miss flashes it red, beeps, and increments `errorCount`.

   `ParseTreePanel.unexpandedCount` counts the nodes that still have hidden
   children. When it reaches zero the tree is "Complete".

In **main-connective-only mode** (the `mainOnly` option, `LPParsing.noDescent`)
the student marks only the main connective of the root. The clicked symbol range
is stored in `selectedRange` and drawn in orange, and `selectionCorrect`
records whether it was right.

`ParsingProblemPanel.checkProblem` returns an `ErrorRef` with one of these ids:

- `parerr001`: no notation chosen;
- `parerr002`: wrong notation;
- `parerr003`: tree incomplete;
- `parerr004`: wrong main connective.

It also returns a `summary` parameter of "Correct", "Incomplete" or
"Incorrect".

### Work record format

A parsing problem or work line is a `TaggedRecord`. `getWorkRecord` writes it
like this:

```
<name>`$<statement>`=<O|I|N>`[<expansion>`]<T|F><range...>`*
```

| tag | meaning |
|---|---|
| `$` | problem name |
| `=` | statement (ASCII "Maggie-talk") |
| `[` | chosen notation code |
| `]` | expansion string from `getExpansionString`, for example `2,0,1,0`. Each node writes its child count (or `0` if collapsed), followed by its children. `restoreExpansion` reads it back. |
| `*` | main-only mode only: `T` or `F` for correctness, followed by the comma-separated selected character range |

`LPParsing.getProblemState_static` works out the state. A problem is correct
(2) if the notation matches and either the expansion string equals the full
`getStructureString()` or the `*` value starts with `T`. Otherwise it is
incomplete (1), or 0 if the record has no work tags at all.

---

## Part 2: truth-value trees

These classes are used by the truth-analysis module, inside `TruthTableCell`,
to evaluate a formula node by node.

- `TruthValueTree` (`C_VF`) is a `CellPanel` tree that mirrors the connective
  structure of a `FormulaParseNode`. Each node has a `TruthValueTreeLabel`
  (`C_BC`): the formula text above a `ChoiceButton` with the values `T`, `F`
  and `?`.
- `checkValues` recomputes the correctness of every node from its children
  (`~`, `&`, `|`, `->`, `<->`). If the `completeAllNodes` option is off, a node
  that is already determined by one child is accepted. For example, `T` for
  `P|Q` is accepted when `P` is `T`, even if `Q` is still `?`.
- `setValues` and `getValues` serialize the node values in pre-order, as a
  string of `?`, `T` and `F`.
- **Linked trees.** `linkedTree` forms a ring of trees that show the same formula
  in different rows. Most operations walk the ring, and `propagateValue` copies a
  chosen value to the other trees in it. `alignmentTree` is another tree whose
  horizontal positions this one copies, so that stacked trees line up.
- `compact` trees use `InlineTreeNodeLayout` (`C_IA`), which puts the left
  child, the connective and the right child on one row. Normal trees use
  `TreeNodeLayout` (`C_h_A`, label above a row of children) and draw their own
  branch lines. `StackedPairLayout` (`C_t_C`) centres the value button under
  the main connective.

---

## Part 3: the printing framework

```
PrintTask (C_l_B, Runnable)
 └─ PrintPage (C_CA, Printable)       abstract getPrintComponents(Dimension)
     ├─ ParsingStatementsPage, ParsingResultsPage, RecognitionProblemsPage, ...
     └─ (about 18 per-module subclasses)
```

- `PrintTask.schedule()` runs the task on the EDT. It adds itself to the
  module's `PrintQueue` (`C_c_C`, a named `Vector` with `PrintQueueListener`s),
  shows the system print dialog, prints, and removes itself when done.
  `PrintTask.waitForQueue` shows a `PrintWaitDialog` (`C_AC`) with an Abort
  button (`PrintAbortHandler`, `C_ZB`). The dialog closes when the queue
  becomes empty.
- Everything is drawn at a logical 10-point font size. It is then scaled by
  `scale = 10 / fontSize`, with a minimum margin of `minMargin` = 36pt.
- `PrintPage.print` asks the subclass for a `Vector` of components, one per
  problem. An entry can itself be a `Vector` of components; only its last
  component counts as the end of an item. `PrintPaginator` (`PrintTask$C__A`)
  then:
  - lays the components out in an offscreen `JFrame`;
  - measures each one (`PrintItemSize`: its height and a scale factor that
    shrinks it to the page width);
  - splits the list into `PrintPageSpan`s (first index, last index, and vertical
    offset into the first item). Items are kept whole where possible, and an
    item taller than a page is cut across pages;
  - prints the page header on every page. `PrintPageHeader` (`C_t_B`) shows the
    class, name, date and "Page n of m". A separator component (a strut by
    default) is printed between items.
- `OutlineNode` (`C_JE`) has its own `print` for the tips outline.

---

## Part 4: the generic UI toolkit

### Layout managers

| class | behaviour |
|---|---|
| `VerticalStackLayout` (`C_m_A`) | A vertical column. Flags: `LEFT`/`H_CENTER`/`RIGHT`/`H_FILL` (bits 0-1) and `V_TOP`/`V_CENTER`/`V_BOTTOM` (bits 2-3). The most-used layout in the program. |
| `FlexGridLayout` (`C_QF`) | A grid filled in order or with `Point(col,row)` constraints. Column widths and row heights can be uniform or variable, and each cell can be aligned or filled. |
| `AlignedFlowLayout` (`C_b_C`) | A single-row flow with top, centre or bottom vertical alignment. Used by the module toolbars. |
| `FixedColumnLayout` (`C_u_`) / `FixedColumnLineLayout` (`C_u_A`) | One row with fixed column widths. The first column can be indented by the x offset of a reference component. The line variant is used for derivation lines. |
| `IndentedTreeLayout` (`C_DC`) / `OutlineLayout` (`C_g_`) | Vertical, indented layout for `CollapsibleNode` children, with the toggle arrow placed in the indent. |
| `ParseTreeLayout`, `TreeNodeLayout`, `InlineTreeNodeLayout`, `StackedPairLayout` | Tree layouts, described above. |
| `DerivationOverlayLayout` (`C_ZF`) | Places derivation overlay components beside the line they refer to. |

### Collapsible outline

`CollapsibleNode` (`C_v_A`) is a `JComponent` with three parts: an optional
header (component 0), a body, and a toggle arrow (`C_ZD`). When the node is the
child of another `CollapsibleNode`, the toggle is added to the parent. `add` and
`remove` create or drop the toggle as the body goes between empty and non-empty.
`DerivationBox` extends it, so derivation subproofs can be collapsed.

`OutlineNode` (`C_JE`) reads the tips file (a `TaggedRecord` stream). In that
stream:

- `$` values collect text;
- `+` starts an expanded child entry and `-` starts a collapsed one, each titled
  with its value;
- `=` goes up one level.

### Widgets

- `LogicLabel` (`C_ZE`) and `LogicTextArea` (`C_NC`) are the standard label and
  text area. They use the program font, and forward key events to
  `LogicProgram.m1086` for global shortcuts. They support `CellRenderable`
  recolouring and `FocusPreference`. `LogicTextArea` tracks the viewport width
  only while the text fits.
- `MessageTextArea` (`C_s_B`) is read-only word-wrapped text for dialogs.
- `ChoiceButton` (`C_b_A`) is a button showing the current choice. A click pops
  up the choices as a menu of `SmallMenuItem`s, or as a `ChoiceList` when
  `useListPopup` is set. It notifies `ChoiceListener`s with the old and new
  index. It can be locked, and it can be sized to its widest choice.
- `ActionButton` (`C__D`) is a toolbar button with a primary action, a
  right-click action and an HTML tooltip.
- `ScaledCheckBox`/`ScaledCheckBoxIcon` and `LogicRadioButton`/`ScaledRadioIcon`
  have icons that scale with `LogicProgram.fontSize`. `LogicRadioButton` can be
  deselected even when it is in a `ButtonGroup`. `ExclusiveCheckBox` gives
  radio-like behaviour inside the truth-table grid.
- `SizedTextField` (`C_a_B`) is a text field with minimum and maximum widths
  and undo/redo. `SizedSeparator` (`C_CC`) is a separator with an explicit
  thickness. `MarginBevelBorder`, `WideMenuButton`, `CheckMarkLabel`,
  `FontLabel` and `CenteredFlowPanel` are small cosmetic helpers.
- `SymbolizationNodeMenu` (`C_MA`), `SymbolizationPopupMenu` (`C__E`) and
  `SymbolizationMenuButton` (`C_NB`) make up the symbolizer's connective and
  edit popup. They offer the connectives, Inequality, Hint, restricted
  generalizations and Cut/Copy/Paste, filtered by the problem's chapter.

### Utilities

- `MergeSorter` (`C_WA`) is a stable bottom-up merge sort. It uses galloping
  merges and is driven by an `OrderPredicate` (`LexicalOrder`, or `CourseOrder`
  for `CourseInfo`). `ServerConnection` uses it to sort course lists.
- `CombinationIterator` (`C_VA`) steps through the k-subsets of n indices,
  forwards or backwards. It is unused.
- `HtmlTableCell` (`C_i_F`) is a table cell with its row and column spans. It
  is used by the HTML response-table parser `C_r_`.
- `EventQueueFlusher` (`C_YD`) pushes a temporary `EventQueue` and dispatches
  every pending event. It is unused.

## Notes and oddities

- `ExpressionLevelsPanel`, `ExpressionButton`, `FontMetricsProvider`,
  `CharMetricsProvider`, `CombinationIterator` and `EventQueueFlusher` have no
  callers. They are leftovers.
- `FormulaParseNode.adjustRangeEnds` (m489) has an apparent bug: its second loop
  increments the wrong variable (`k++` instead of `j++`). It is never called.
- Several `FlowLayout` subclasses declare a public no-argument method that
  always returns `false` (`isFixed*Layout`). These are probably leftovers of an
  old interface.
- `AlignedFlowLayout.layoutContainer` adds each component's width *before*
  placing it. As the decompiled code reads, each component is therefore shifted
  right by its own width. This has not been checked in the running program.
