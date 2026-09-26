# Rules, theorems and derivation checking

This document covers the rule engine behind derivations: how rules and theorems
are loaded, how a rule application is matched against cited lines, and how a
derivation line's justification is checked. It also covers the small
Recognition module, which reuses the same matcher. Names follow
`mappings/core.mapping` and `mappings/rules.mapping`, with the placeholder name
in parentheses on first mention. Classes owned by other groups, such as
`Expression`, `SchemeInstantiation`, `DerivationLine` and `DerivationDialogs`,
are only described as far as this subsystem uses them.

## 1. Main classes

| class | role |
|---|---|
| `Rule` (`C_VB`) | A named rule. A *compound* rule such as `DN` has `components` (`DNE`, `DNI`). A *leaf* rule is a `SchematicRule`. |
| `SchematicRule` (`C_LF`) | One rule form: `premises[]` (schemata) `.:` `conclusion`. |
| `Theorem` (`C_QE`) | `T<n>`: a `SchematicRule` with no premises. `number` is its Integer. |
| `RuleTable` (`C_z_B`) | Hashtable from upper-case rule name to `Rule`, loaded from `banshee.txt`. It keeps `ruleNames` in file order, `headings`, the `RuleProperties`, and a cache of the `RT<n>` rules derived from theorems. |
| `TheoremTable` (`C_z_`) | Hashtable from Integer to `Theorem`, loaded from `fiend.txt`, plus the set of `theoremNumbers` and `headings`. |
| `RuleProperties` (`C_r_D`) | Structural properties (`notConditional`, `notConditionalBC`, `biconditional`, `hasConverse`) and the converse table. |
| `RulePropertySource` (`C_w_E`) | Interface `hasProperty(rule/theorem, prop)`, `getProofs`, `excludedProof`, `checkProof`. Implemented by `RuleProperties` (structural properties) and `LPDerivation` (`disabled`, `manual`, `manualOrDisabled`, `weakAss`, `assumed`). |
| `ArgumentParser` (`C_VC`) | Parses `"P . Q .: R"`. `matchRule(Rule)` drives Recognition. |
| `PermutationIterator` (`C_XE`) | Enumerates every permutation of `0..n-1`. |
| `BoundVariableMap` (`C__B`) | Maps a schema's bound variables to the instance's bound variables. It also detects variable capture. |
| `Justification` (`C_HD`) and its subclasses | The cached result of one justification step. See §4. |
| `DerivationLineChecker` (`C_a_`) | Parses a line's justification text and checks it step by step. See §5. |
| `IntervalSet`, `HighlightedText`, `TextHighlighter`, `RuleApplicationDisplay` | Display helpers. They render an application with the instantiated parts colored. See §7. |

## 2. Data files

Both files are scrambled (see `Scrambler`) and read with `ScrambledReader`.
There is one copy per syntax, under `syntax1/` and `syntax2/`.

**Rules (`banshee.txt`)**, one rule per line: `NAME<whitespace>BODY`.

```
#-Double Negation          <- "#-" comment: a heading shown in the rule list
DNE	~~P.:P                 <- body contains ".:" -> SchematicRule
DNI	P.:~~P
DN	DNE.DNI                <- no ".:" -> compound Rule of earlier rules
UI	@xFx .: FA
#To enable SC1, prove T33  <- plain "#" comment: ignored
```

- `RuleTable.addRuleLine` decides which kind of rule a line defines:
  - A body with `.:` is parsed by `SchematicRule.parseForm` through
    `ArgumentParser`.
  - Any other body is a `.`-separated list of names. `RuleTable.findRule`
    resolves each name, so the list may use `T<n>` and `RT<n>...` too.
- Consecutive `#-` lines are collected and stored under the next rule's name in
  `RuleTable.headings`. `DerivationDialogs` prints them as section titles in the
  rule list.
- Errors are only printed to stdout, for example `redefinition of rule X` or
  `error in rule X: ...`. The rule is then skipped.
- Every accepted rule is passed to `RuleProperties.registerConverses`.

**Theorems (`fiend.txt`)**, one per line: `<number><whitespace><formula>`, for
example `4	(P->Q)->((Q->R)->(P->R))`. It uses the same `#-` heading convention.
`Theorem(Integer, String)` builds the form `".:" + formula`.

### Theorem-derived rules (`RT<n>`)

`RuleTable.findRule(name)` resolves a name in this order:

1. `T<n>` gives the `Theorem`.
2. `RT<n>[suffix]` gives a rule built lazily by `Rule.fromTheorem` and cached in
   `theoremRuleCache`.
3. Anything else is a plain table lookup.

`fromTheorem` turns a theorem into rule forms:

- For `A -> B`, it makes `RT<n>`: `A .: B`. If `A` is a conjunction, the
  premises are its conjuncts (`splitConjuncts`). If `A` has several conjuncts,
  it builds a compound instead: `RT<n>L` (`A .: B`) and `RT<n>LF` (conjuncts
  `.: B`).
- For `A <-> B`, it makes `RT<n>L` (`A .: B`) and `RT<n>R` (`B .: A`), plus
  `RT<n>LF` and `RT<n>RF` when that side is a conjunction.
- For anything else, it makes `RT<n>`: `.: formula`.

Each such rule remembers its `sourceTheorem`. Property and proof queries are
then delegated to the theorem.

## 3. Rule properties, proofs and converses

`Rule.testProperty(source, prop, any)` works like this:

- It returns true if the source theorem or the rule itself has the property.
- Otherwise it asks the components. With `any = true`, one matching component
  is enough. With `any = false`, all components must match.
- `getForms(source, prop)` flattens the component tree into leaf
  `SchematicRule`s and drops every form that has `prop`. For example,
  `getForms(lpd, "disabled")` gives the forms that are still enabled.

**Proven rules.**

- `SchematicRule.isProven(src)` holds in either of two cases:
  - The rule is not tied to any proof problem.
  - It is tied to proof problems (`getProofProblems`, i.e. `exercises.f618` in
    `LPDerivation`) and at least one of them is solved (state `C`).
- The current problem (`excludedProof`) does not count, and neither does a
  `weakAss` rule.
- `Rule.isProven` requires every component to be proven. `isAnyFormProven`
  requires only one.
- A form that is not yet proven is rejected with `dererr016`, and
  `proofMissing` is set.

**Structural properties.**

- `RuleProperties.getEquivalence(e, allowConditional)` strips leading
  universal quantifiers and returns the `<->` underneath. With `true`, it also
  accepts a top-level `->`.
- `getConditionalEquivalence` finds `C -> (A <-> B)` (possibly under `@x`). It
  also finds a biconditional whose side is itself a biconditional.
- The properties are defined as follows:
  - **notConditional**: the form cannot drive IE.
  - **notConditionalBC**: the form cannot drive CIE.
  - **biconditional**: the form has no premises and its conclusion is a
    (quantified) biconditional.

**Converses.** `registerConverses` pairs forms whose "from" and "to" sides match
crosswise (`isConversePair`), for example `DNE` and `DNI`. `getConverses` is
used so that IE may use a rule in the reverse direction if its converse is
enabled and proven.

## 4. Justifications (the per-line cache)

Each `DerivationLine` has a Vector (`f336`) with one `Justification` per step of
its justification. It is serialized into the work file as `encode()` =
`"<type>:<data>"` and read back by `Justification.decode`.

| type | class | label | data |
|---|---|---|---|
| 1 | `RuleApplication` (`C_HF`) | rule form name | `NAME{i,j,..}<instantiation>,<boundvars>`: `premiseOrder`, `SchemeInstantiation.encode`, `BoundVariableMap.encode` (`x:y.z:w`) |
| 2 | `PremiseJustification` (`C_l_`) | `PR<n>` | premise index |
| 3 | `IndirectAssumptionJustification` (`C_p_E`) | `ASS ID` | `+` means assume `~show`; `-` means the show line is `~A` and `A` is assumed |
| 4 | `InterchangeJustification` (`C_GA`) | `IE` / `CIE` | `<path>(<\|>)<source>[?(<\|>)<condition>]`. The source is a `RuleApplication` body, a line number, or `#<premise>`, each with an optional `,inst;boundvars` |
| 5 | `BiconditionalAssumptionJustification` (`C_c_E`) | `ASS BD` | `L` / `R` |

Type 5 is written by `encode()`, but `Justification.decode` has no branch for
it. A cached `ASS BD` choice is therefore lost on reload, and the user is asked
again.

The cache mostly records choices the user made in dialogs: which premise, which
rule form, which variable, which IE position. On a re-check,
`Justification.reapply(checker)` replays the step without asking again. If the
replay fails, the cache entry is dropped and the step is checked from scratch.

## 5. Checking a derivation line

Checking starts in `DerivationLine.m595(interactive)` (phase `JUST_CHECK`),
which creates `new DerivationLineChecker(line, interactive)`. `interactive` is
true when the user has just entered the line. Only then may the checker open
dialogs, auto-fill an empty line, or accept the `SHOW …` variants.

### 5.1 The justification text is a stack program

The justification is read left to right. Digit runs are **line numbers**. Runs
of "name characters" (`isNameChar`: letters, `~!@#$%^&*(){}_+-=<>|/`, and
anything ≥ U+0100) are **rule names**. Everything else separates tokens.

`readNextStep()` does the following:

- It pushes the formula of each cited line onto `stack`, and the node onto
  `citedNodes`.
- Before pushing, it checks that the line exists (`dererr003`), that it may be
  cited (`DerivationLine.canUse`: it must be earlier and not inside a closed
  box, errors `dererr044`–`046`), and that it has a parsed formula
  (`dererr004`/`005`).
- It stops at the first rule name, which it stores in `ruleName` after
  `normalizeRuleName`.
- `normalizeRuleName` upper-cases the name. Anything after a `/` becomes
  `presetAnswers`. For example, `UI/a` pre-answers the "which term?" dialog, and
  serial mode uses this to avoid dialogs.
- `ASS` and `SHOW` combine with the following name, as in `ASS CD` or
  `SHOW CONCLUSION`.

The result of a step (`result`) is pushed back onto the stack before the next
step is read. A justification such as `1 2 MP 3 ADJ` is therefore two chained
steps: MP consumes lines 1 and 2, then ADJ consumes that result and line 3.
Chains are only allowed in `queuedMode` (`dererr050`).

The driver loop in `DerivationLine` works like this:

```
while (checker.readNextStep()) {
    if (ruleName == null) -> dererr049 (numbers but no rule)
    if (!checker.skipToNextStep())       // this was the last step
        serialMode:  return checkStep(matchLine=true) && boxCheck
        interactive: return checkStep(true)
        otherwise:   checkStep(false, finalStep=true); then compare result
                     with the line; on mismatch show dernot100/101
                     ("the rule gives X; replace?")
    checkStep(false)                      // intermediate step
}
```

`argumentCount` is the stack size when the step starts. It is the number of
formulas available to the rule. For an intermediate step, a form with `k`
premises may use the top `k` formulas (`k <= argumentCount`). For the final step
(`matchLine` or `finalStep`), `k` must equal `argumentCount`. When a step
succeeds, `popStack(k)` removes the formulas it used.

### 5.2 checkStep: one rule application

`checkStep(matchLine, finalStep)` does the following, in order:

1. It advances `stepIndex` into the line's justification cache.
2. It asks `LPDerivation.checkDerivationRule(ruleName, interactive)` whether
   the rule is allowed in this problem at all.
3. If the step is not the last one, the name must not be a box-closing rule
   (`CD ID DD UD BD`, `dererr009`). On the last step, a closing rule must be on
   the box's show line (`dererr007`/`008`).
4. It tries the cached `Justification`. If `reapply` succeeds, the step is
   done.
5. Otherwise it dispatches on the rule name:

**Box-closing rules.** These check the show line of the enclosing
`DerivationBox` against the top of the stack.

- **CD**: one cited formula, and it must match the consequent of the `->` show
  line.
- **ID**: two cited formulas that are `A` and `~A`.
- **DD**: one cited formula equal to the show formula.
- **UD**: the show line is `@x A` and the cited formula matches `A`. Also, `x`
  must not be used in any enclosing box (`isVariableUsedInOuterBoxes`,
  `dererr023`).
- **BD**: the show line is `A <-> B`. The cited line and the assumption (or the
  second line) must supply both sides.
- In every case, the cited lines must be directly inside the box, not in a
  nested box (`getCitedNodeOutsideBox`, `dererr015`).
- In serial mode, the kind of box assumption (`ASS_STR`: D/I/C/B) must match
  the closing rule, and it is checked again as the rule `CD/C`, `DD/I` and so
  on.
- `matchesPattern` lets a show formula contain wildcards:
  - `?` means any formula.
  - `?PNX` means any prenex formula.
  - `?NOV` means quantifiers only at the outside of each subformula.
  - `?DNF` and `?CNF` mean normal forms.

  This is how "put X into prenex form" style problems are graded.

**Assumptions.** These take no cited lines and must directly follow a show
line.

- `ASS CD` assumes the antecedent.
- `ASS ID` assumes the negation. If the show line is `~A`, the user is asked
  to choose between `A` and `~~A`, and the choice is cached as type 3.
- `ASS BDL`, `ASS BDR` and `ASS BD` assume a side of the biconditional. The
  choice is cached as type 5.

**Premises.** `PR<n>`, or `PR` alone, which asks for a premise when there are
several. The choice is cached as type 2.

**SHOW variants.** Only for a show line entered interactively:

- `SHOW CONCLUSION` shows the problem's conclusion.
- `SHOW CONSEQUENT`, `CORRCOND`, `CONJUNCT`, `CONDITIONAL` and `INSTANCE`
  derive the new show goal from the enclosing show line.
- `SHOW UNNEGATION`, `ANTECEDENT`, `NEGCONSEQUENT` and `NEGDISJUNCT` take a
  line number or `PR<n>` operand (`readSourceFormula`).
- Any unique prefix of these names works.

**IE / CIE.** Interchange of equivalents. `DerivationDialogs` asks for the
position (`path`) and for the equivalence. The step is then checked through
`InterchangeJustification.reapply`. See §6.

**Everything else** is a named rule or theorem, handled by `matchNamedRule`
then `matchRule(Rule)`. See §5.3.

After a step produces `result`, `checkResultMatchesLine` compares it with the
line's own formula (`lineFormula`), for the final step only.

- In command mode, an empty interactive line is filled in with the result.
- A mismatch produces one of three messages: `dererr100` ("doesn't follow"),
  `dererr033`/`103` ("the rule gives X"), or `dererr064` when a replayed cache
  entry failed.

Every failure goes through `fail()`. It clears the line's cache, marks the box
as not being a pure IE/BD box (`f922`), and returns false.

### 5.3 matchRule: matching a rule against the cited formulas

`matchRule(Rule rule)` builds three lists of forms:

- `allForms`: `rule.getAllForms()`.
- `enabledForms`: forms that are not `disabled`.
- `automaticForms`: forms that are neither `manual` nor `disabled`, used when
  `interactive`. Some rules can be used only by typing them in, not by the
  automatic "apply rule" command.

For every form whose premise count fits `argumentCount`, the checker does the
following:

1. **Try every order.** A `PermutationIterator` over the `k` premises assigns
   `premise[perm[l]]` to stack slot `l - k`. Each premise schema is matched
   separately against its formula (`Expression.m1266(expr, inst)`, which
   extends a `SchemeInstantiation`).
2. **Merge.** The per-premise instantiations must merge consistently
   (`SchemeInstantiation.m1877`). If the merged instantiation is complete
   (`m1890`), `BoundVariableMap.matches` also checks that bound variables line
   up. The result is a `RuleApplication`, added to `premiseMatches`.
3. **Conclusion.** The instantiation must also be compatible with the
   conclusion schema. When `matchLine` is set, the conclusion schema is matched
   against the line's own formula first. If everything is determined, the
   application goes into `fullMatches`.
4. **Failure kinds.** `RuleApplication.failureKind` records why the conclusion
   check failed. The values follow `DerivationConstants`:
   - `SCHEME_FAILED`: the conclusion did not match.
   - `SCHEME_INCOMP`: the conclusion was incompatible with the premises.
   - `VAR_VIOLATION`: the bound variables disagree.
   - `IMPROPER_SUB`: instantiating captures a variable. `failureDetail` holds
     the clashing pairs.
   - `SUB_ERROR`: the result differs from the line.
5. **Availability.** A full match is kept only if the form is in
   `enabledForms`, is also in `automaticForms` when `interactive`, and
   `isProven`. Otherwise one of three flags is set, which later selects
   `dererr041`, `dererr040` or `dererr016`.

`pruneDegenerateMatches` removes matches of `LL1`–`LL4` (Leibniz's law) and
`AV3` that are only formally valid. For example, it removes a match where the
replaced term does not occur, or where the replaced positions are inconsistent.

The result depends on how many matches survive:

- **None.** The checker picks the most specific message:
  - no form with that many premises: `dererr038`/`039`
  - a disabled, manual-only or unproven form
  - exactly one full match that differs from the line: `dererr033`/`103`
  - improper substitution: `dererr074`, naming the misbound variable
  - premises matched but the conclusion did not: `dererr100`
  - otherwise: `dererr042`/`063`, with the "rule forms" listed through the
    `m547` message-parameter hook
- **Several.** `DerivationDialogs.m780` removes duplicates by their highlighted
  display. If more than one remains, the user chooses.
- **Instantiation complete.** `result` is the instantiated conclusion. Bound
  variables are renamed through `BoundVariableMap.renameBinders`, which may ask
  the user to name bound variables it cannot infer. The chosen application is
  cached (type 1).
- **Instantiation incomplete.** This is the case for `UI`, `EG`, `EI`, `LL`,
  `EL` and similar rules, where the conclusion mentions letters that no premise
  fixes. `DerivationDialogs` asks for the missing term or variable
  (`m783`/`m784`/`m785`/`m788`/`m789`/`m790`/`m782`). The whole application is
  then re-verified against the premises and the line, and cached.

Rule-specific restrictions come last. `checkInstantiationRestrictions` checks
that the `EI` instance variable is a simple variable not already used
(`varNames`, `dererr034`).

### 5.4 Bound variables

Schemata such as `@xFx .: FA` contain their own bound variable `x`.

- `BoundVariableMap` records, for each schema binder, which variable the
  instance uses. `matchBinders` uses the binder correspondence computed by
  `C_MB` (a map from schema nodes to instance nodes).
- `renameBinders` renames the binders of a freshly instantiated formula. Where
  no name is known, it opens a dialog listing the unnamed binders, which it
  highlights with `TextHighlighter`.
- It then checks `Expression.m1259` for variable capture. If there is capture,
  the result is `dererr061`, and the clashing pairs are kept in `clashes`.

## 6. Interchange of equivalents (IE / CIE)

An `InterchangeJustification` has the following parts:

- a `path`: the position of the replaced subformula in the cited line
- a direction: `reversed` means right-to-left
- an equivalence source:
  - a `RuleApplication` of a biconditional or one-premise rule form
  - a derivation line (`equivalenceLine`)
  - a premise (`equivalencePremise`)
- for CIE only, a *condition*:
  - a line (`conditionLine`)
  - a premise (`conditionPremise`)
  - a `RuleInstance` (`C_PF`: the closed instance of a rule's conclusion)

`findApplications(checker, path, rule, condition)` enumerates every way the
rule's forms can rewrite the subformula at `path`:

- It takes the "from" and "to" sides from `RuleProperties.getFromSide` and
  `getToSide`, or the conditional variants for CIE.
- It tries both directions for biconditionals.
- `isRuleUsable` filters the forms:
  - the form is not disabled
  - the form is proven, or its converse is
  - a cited line may be used
- `matchEquivalence` and `matchConditionalEquivalence` do the scheme and
  binder matching.
- For CIE, `alignConditionQuantifiers` strips extra outer universals from the
  actual condition so that it matches the rule's condition.

`reapply` rebuilds the source formula and the result formula:

- `getSource` and `getResult` substitute the instantiated side into the cited
  formula at `path` with `replaceAt`.
- The source must equal the cited line exactly.
- The result becomes the step's `result`.
- Errors `dererr084`–`099` cover the IE-specific failures: a wrong line count,
  a cited line that is not a (conditional) biconditional, or an inner rule
  that is not suitable (`validateInnerRule`).

## 7. Display helpers

- `IntervalSet` (`C_n_F`) is a sorted list of toggle points plus a complement
  flag.
  - `{3,5}` means `[3,5)`, and `inverted` means the complement.
  - It supports union, intersection and difference, `shift`, and
    `selectChars`.
  - `TheoremTable.theoremNumbers` and the `LPDerivation` rule ranges use it as
    a set of integers.
- `HighlightedText` (`C_e_B`) is a string plus one `IntervalSet` per color
  layer. `append` shifts the appended ranges.
- `TextHighlighter` (`C_CB`) turns this into a Swing `StyledDocument`.
- `RuleApplicationDisplay` (`C_FD`) renders `premises .: conclusion` of an
  application, with instantiated schematic letters colored. The disambiguation
  dialog and the rule-choice dialogs use it.

## 8. Recognition module

`LPRecognition` asks the student to name the rule that justifies a given
argument, or `NONE`.

- **Problem data.**
  - The problem list is a `RecognitionProblemSet` (`C_j_A`) of
    `RecognitionProblemEntry` (`C_ED`).
  - Work is kept in `recwork.txt` and messages come from `recMessages`
    (`RecognitionMessage`, `C_BF`).
  - The problem panel is `RecognitionProblemPanel` (`C_AB`, truthinval group).
- **UI.**
  - `RecognitionButtonPanel` (`C_h_`) provides Select/User/Check/Save/Delete/
    Submit/Print/Quit, with Ctrl+O/N/K/S/P shortcuts.
  - `RecognitionDialogs` (`C_EC`) holds the static dialog code.
  - The print jobs are `RecognitionListPrintJob` and
    `RecognitionResultsPrintJob`.
  - Pressing Enter in `RecognitionEntryPane` (`C_YC`) runs `checkProblem`.

**Checking.** The panel's check (`C_AB.m221`) parses the argument once with
`ArgumentParser` and then works as follows:

- An answer in the problem's accepted list is correct. An answer in its
  explicit wrong list is incorrect.
- `NONE` is correct only if no active rule fully matches.
- Otherwise the named rule must exist (`recnot002`) and be active
  (`recnot007`), and `ArgumentParser.matchRule(rule)` must return 2.

`matchRule(rule)` uses the same algorithm as §5.3 (permutations,
per-premise instantiation, merge, binder check) and returns one of three
values:

| value | meaning | message |
|---|---|---|
| 0 | no form's premises match | `recnot003` |
| 1 | the premises match, but the conclusion does not | `recnot006` |
| 2 | full match | correct |

One extra condition applies: an `EI` match only counts if the instantiated term
is a variable. A user problem is typed as `P . Q .: R`. `normalizeDots`
collapses repeated dots before it is stored.

## 9. Notes and surprises

- `Rule.testProperty(src, p, any)` defaults to `!any` when no component matches
  and there are components. A compound rule therefore has "all components are
  disabled" semantics for `testProperty(.., "disabled", false)`.
- `InterchangeJustification.identityInstantiation` loops with `elementAt(0)`
  instead of `elementAt(j)`. This looks like a bug: it maps only the first
  schematic letter to itself, possibly several times.
- `DerivationLineChecker` has two unused constant fields (`CONST_TRUE` and
  `CONST_FALSE`). Their only traces are the `checker.getClass()` null checks
  that javac emits before the inlined constant.
- The `m547(key)` hook, from the `C_k_A` interface, is how error messages get
  their parameters: `rule name`, `stack`, `stackN`, `rule forms`, `rule form
  premises`, `rule form conclusion`, and small plural helpers (`rfs s`,
  `rfp are`, ...).
- `RuleProperties.addConverse` has an empty `if (!contains)` block before an
  unconditional `addElement`, so the converse lists can hold duplicates.
