# Formula subsystem: expressions, schemes and the parsers

This module covers how Logic 2010 represents formulas and terms, how it parses
them from text in either notation, and how it matches and instantiates rule
schemata. All names below come from `mappings/core.mapping` and
`mappings/formula.mapping`. The placeholder name is given in parentheses the
first time a class appears.

## 1. Main classes

| class | role |
|---|---|
| `Expression` (`C_RF`) | Abstract n-ary tree node with `kind`, `symbol`, `children` and `childCount`. Handles printing, paths, search, equality, variable linking, instantiation and matching. |
| `Formula` (`C_y_A`) / `Term` (`C_X`) | Abstract intermediate classes. `Formula.universalClosure` wraps the formula in `@x` for each free variable. |
| `AtomicFormula` (`C_q_A`, kind 0) | A sentence or predicate letter with argument terms. |
| `QuantifiedFormula` (`C_o_A`, kind 1) | `@x φ` (all) or `!x φ` (some). Its children are the variable (`SimpleTerm`) and the body. |
| `ConnectiveFormula` (`C_q_F`, kind 2) | `~`, `&`, `\|`, `->`, `<->`. It has 1 or 2 operands. |
| `SimpleTerm` (`C_i_`, kind 3) | A variable or name. Its `binder` field points at the node that binds it. |
| `OperationTerm` (`C_n_C`, kind 4) | An operation (function) letter with argument terms. |
| `DescriptionTerm` (`C_WE`, kind 5) | `%x φ`, a definite description. Its children are the variable and the body. |
| `IdentityFormula` (`C_x_D`, kind 6) | `t1 = t2`. |
| `MembershipFormula` (`C_t_D`, kind 7) | `t1 [m] t2`. |
| `ExpressionKinds` (`C_a_D`) | The constants `KIND_ATOMIC` through `KIND_MEMBERSHIP`. |
| `ExpressionPath` (`C_e_`) | A growable `int[]` of child indexes. Its text form is `{0,1,1}` (`format`/`parse`). |
| `VariableScope` (`C_JD`) | A `Hashtable` whose `push`/`pop` shadow and restore older bindings. It maps a variable symbol to its binder during linking. |
| `BoundVariableNames` (`C_L`) | A `Vector` of bound-variable names in binder order. Its text form is `x.y.z.` |
| `LetterGenerator` (`C_CF`) | Enumerates fresh letters: `a b c … a0 b0 … a1 …` |
| `LabeledExpression` (`C_VD`) / `PremiseExpression` (`C_KF`) | An expression with a label such as "Premise 2". `C_ZA` (the derivations group) is the "Line n" variant. |
| `FormulaParser` (`C_FB`) | Static front end. `syntax` (1 or 2) selects `Syntax1Parser` (pkgB) or `Syntax2Parser` (pkgA). |
| `FormulaParseException` (`C_k_B`), `FormulaLexerError` (`C_IC`) | Syntax-neutral bases of the two JavaCC exceptions. |
| `SchematicLetter` (`C_i_A`) and `TermLetter` (`C_PB`), `OperationLetter` (`C_W`), `PredicateLetter` (`C_w_C`) | Keys of a scheme instantiation: a letter plus its arity. |
| `LetterReplacement` (`C_GF`) | `pattern` → `replacement`, or a validation `error` (`ErrorRef`). |
| `SchemeInstantiation` (`C_j_D`) | A `Hashtable` from `SchematicLetter` to `LetterReplacement`. It also holds the `pendingLetters` and the last error (`errorId`, `errorParams`). |
| `DeferredMatch` (`C_m_B`) | A pattern/instance match that is postponed until its letter gets a value. |
| `BinderMap` (`C_MB`) / `BinderKey` (`C_o_D`) | Records which binder in one tree corresponds to which binder in another. This drives alpha-equivalence and the relinking of variables in copies. |
| `SchemeSubstitutionPanel` (`C_f_`) | A Swing panel that shows a `SchemeInstantiation` as rows of the form "pattern [replacement]". The user fills in the pending letters, and `readInstantiation` parses them back. |

## 2. The expression tree

Every node stores its own `symbol`: `"&"`, `"@"`, `"F"`, `"x"`, `"="` and so
on. The kind comes from `initKind()`, which each subclass overrides. The
children are kept in a `Vector`. The typed adders (`setLeft`, `setBody`,
`addArgument`, ...) all just append a child.

**Variable linking.** A `SimpleTerm` does not know by name whether it is bound.
Instead its `binder` field points at the `QuantifiedFormula` or
`DescriptionTerm` that binds it. The variable child of a quantifier is linked to
that quantifier too. The links are set in two places:

* The parser keeps a `binders` hashtable while it parses a quantifier or
  description body. A variable token that is found in the table is linked at
  once.
* `Expression.linkVariables()` walks the tree with a `VariableScope`.
  Quantifiers and descriptions `push` their variable before they visit the body
  and `pop` it afterwards. Each `SimpleTerm` then looks itself up.
  `LogicProgram`'s parse helper calls this after every parse.
  `findMislinkedVariables` performs the same walk, but it reports
  `[expectedBinder, term]` pairs whose link differs instead of fixing them.

A binder can also be an `AtomicFormula` or an `OperationTerm`. These are the
nodes for which `bindsArguments()` is true, and this case arises inside a
`LetterReplacement`. `isBoundVariable()` means "linked to a quantifier or
description". `isArgumentPlaceholder()` means "linked to a letter application";
see section 5.

**Printing.** Printing uses two formats, both parameterized by an "inequality
mode":

* `formatMinimal(mode)` is used by `toString()`. It drops parentheses where
  precedence allows. `&` and `|` bind tighter than `->` and `<->`. A chain of
  the same `&` or `|` is printed without parentheses when it is left-nested.
  Mixing `&` with `|` always gets parentheses. The body of a quantifier or
  description is parenthesized only if it is a binary connective.
* `formatFull(mode)` puts parentheses around every binary connective.
* `mode` controls how `~(a=b)` is printed. With -1 (`toCanonicalString`) it is
  always shown as `a<>b`. With 0 it is shown as `a<>b` only if the node was
  written that way (`displayAsInequality`). Otherwise it prints as `~a=b`.
* A predicate letter from `LogicProgram`'s predicate-letter set (`F`–`O`,
  `isPredicateLetter`) is printed as `Fa` without parentheses. Other letters
  and all operation letters get parentheses around their arguments: `P(a)`,
  `f(a b)`. Arguments are written next to each other with no commas.

`layoutDisplayTree(C_DD)` builds a parallel tree of display nodes that records
the character offset and width of each child. The parsing and recognition
modules use it to map mouse positions to subformulas.

**Paths and search.** `getSubexpression(path[, binders])` follows an
`ExpressionPath`. The variant that takes a `Vector` also collects the binders it
passes on the way down. The search methods (`findOccurrences`,
`findLetterOccurrences`, `findSymbolOccurrences` and
`findBoundVariableOccurrences`) return vectors of paths. `findDifferences`
returns the paths where two trees diverge. `getDifferencePath` reduces them to
their common prefix, which is the smallest subtree that contains every
difference. Derivation checking uses this to find the part of a line that a
rule changed.

**Equality.**

* `isIdentical` compares symbols and shapes only.
* `isAlphaEquivalent(other, BinderMap)` records the correspondence between
  quantifiers and descriptions as it descends. A bound variable then matches
  when its binder corresponds to the other variable's binder, whatever the
  variable's name. `IdentityFormula.abstractQuantifiers` sorts the two sides of
  `=` by `toCanonicalString`, so identity counts as symmetric in that
  normalization.

**Other transformations.**

* `renameBoundVariables(Vector)`: assigns names to bound variables in binder
  order. It is the counterpart of `getBoundVariableNames()`.
* `expandQuantifiers(n, prefix)`: `LPInvalidation` uses this for finite
  domains. It rewrites `@x Fx` as `F(prefix0)&F(prefix1)&…`, and `!` as a
  disjunction.
* `toTruthFunctionalForm()`: replaces each quantified or description subtree by
  a fresh sentence letter or operation letter. Identical subtrees get the same
  letter; `C_HA.m681` tests truth-functional equivalence. The result can then be
  checked truth-functionally. `!x φ` is first turned into `~@x~φ`. The fresh
  letters are taken from the end of the alphabet.

## 3. Lexical syntax

The two notations share the operators and differ only in their letter classes.
All letters may carry a numeric subscript (`0` or `[1-9][0-9]*`). Internally
everything is the ASCII "Maggie" notation. The UI maps to and from the display
fonts with `LogicProgram.m995`.

| token | syntax 1 (pkgB) | syntax 2 (pkgA) |
|---|---|---|
| `VAR` (variables and names) | `[a-z]` | `[i-z]` |
| `OP` (operation letters) | `[A-E]` | `[a-h]` |
| `PRED` (predicate letters) | `[F-O]` | `[A-O]` |
| `SEN` (sentence letters) | `[P-Z]` | `[P-Z]` |
| `VAR` placeholder | `{` `[1-9][0-9]*` `}` | same |
| `UNK` (unknown letter) | `?` `[A-Z]*` | same |
| `EOL` | `"\n"` | same |

The fixed tokens are `<->`, `->`, `&`, `|`, `=`, `<>`, `[m]`, `~`, `@`, `!`,
`(`, `)` and `%`. Space, `\t` and `\r` are skipped.

The `{n}` placeholder form lexes as a `VAR`. This lets schemes such as `F({1})`
and `{1}={2}` be parsed like ordinary formulas; see section 5.

## 4. Grammar (reconstructed EBNF)

The production names are the parser's own trace strings (`trace_call("conjexp")`
and so on). They are identical in both packages. Every alternative is guarded by
`LOOKAHEAD(2147483647)`, which is unbounded syntactic lookahead. As a result the
parser tries the alternatives in the order below with full backtracking. This is
why there are 32 `jj_2_n`/`jj_3_n` routines and 7 shared `jj_3R_n` routines.

```ebnf
one_line  = formula EOL          (* -> Formula *)
          | term EOL             (* -> Term *)
          | EOL                  (* -> null (blank line) *)
          | EOF ;                (* -> null *)

formula   = conjexp { ( "<->" | "->" ) conjexp } ;      (* left-assoc *)
conjexp   = unary   { ( "&"   | "|"  ) unary   } ;      (* left-assoc *)

unary     = "~" unary
          | "@" VAR unary                               (* universal   *)
          | "!" VAR unary                               (* existential *)
          | equation
          | member
          | primary ;

equation  = term "="  term
          | term "<>" term ;     (* built as ~(t=t), marked displayAsInequality *)

member    = term "[m]" term ;

primary   = "(" formula ")"
          | ( PRED | SEN ) "(" term { term } ")"        (* F(a b), P(a) *)
          | PRED term                                   (* Fa *)
          | SEN | UNK ;                                 (* P, ?A *)

term      = VAR
          | OP "(" term { term } ")"
          | OP                                          (* 0-ary operation *)
          | "%" VAR unary ;                             (* description *)
```

Some notes on the grammar:

* `->` and `<->` share one precedence level, and so do `&` and `|`. All of them
  associate to the left, so `P->Q->R` parses as `(P->Q)->R`. The printer adds
  parentheses to any `->`/`<->` operand of a `->`/`<->`, so this ambiguity never
  shows up in output.
* Argument lists have no commas. Terms are written next to each other.
* A quantifier's scope is a `unary`, so `@x Fx & Gx` is `(@x Fx) & Gx`.

## 5. Parser runtime (JavaCC)

Both packages are the output of JavaCC 2.x/3.x with `STATIC=true`. The static
fields are why the classes contain "Second call to constructor of static parser"
checks. `FormulaParser` therefore never constructs a parser per call. It calls
`reinit(Reader)` and then `parse()`, which calls `one_line()`. The standard
pieces are:

* `Syntax{1,2}CharStream`: the JavaCC `SimpleCharStream`, the old
  `ASCII_CharStream`. It has a ring `buffer` with `bufline`/`bufcolumn`,
  `readChar`, `BeginToken`, `backup`, `GetImage` and `ExpandBuff`/`FillBuff`.
  Tabs advance the column to the next multiple of 8.
* `Syntax{1,2}TokenManager`: `getNextToken` skips blanks and then runs
  `jjMoveStringLiteralDfa0_0` for the fixed operators. That falls back to
  `jjMoveNfa_0` for the letter classes. `jjFillToken` builds the `Token` from
  `jjstrLiteralImages` or `GetImage()`. The only lexical state is `DEFAULT`.
* `Syntax{1,2}Parser`: `jj_consume_token(kind)`, `jj_scan_token`, `getToken`,
  the lookahead memo `jj_2_rtns` of `JJCalls` records, and
  `generateParseException`. Tracing (`trace_call`/`trace_token`) is enabled by
  default. `LogicProgram.initialize` switches it off through
  `FormulaParser.disableTracing()`.
* The exceptions derive from `FormulaParseException` and `FormulaLexerError`.
  This lets callers catch them without knowing which syntax is active.

`LogicProgram.m1008/m1009` is the real parse entry point. It appends `"\n"`,
calls `FormulaParser`, links the variables, rejects placeholders `{n}` unless
the caller allows them, and checks well-formedness with a `C_DD` layout pass.

## 6. Schemes: matching and instantiation

Rules and theorems are stored as schemata, which are ordinary expressions whose
letters act as metavariables. A **schematic letter** is one of these:

* `TermLetter`: a free `SimpleTerm` (a name or free variable). Bound variables
  are not letters.
* `OperationLetter(letter, arity)`: an `OperationTerm`.
* `PredicateLetter(letter, arity)`: an `AtomicFormula`.

`Expression.getSchematicLetter()` returns the key for a node, and
`toExpression()` rebuilds `F({1},{2})` from a key.

A **LetterReplacement** maps `F({1} {2})` to an expression. That expression may
contain the placeholders `{1}` and `{2}`. The constructor copies the replacement
and calls `linkArgumentPlaceholders(pattern)`, which points every `{i}`
`SimpleTerm` in the replacement at the pattern node.
`SchemeInstantiation.validateReplacement` enforces these rules:

* the pattern's arguments must be distinct placeholders (`dererr094`);
* formulas replace predicate letters (`dererr068`);
* terms replace term and operation letters (`dererr070`);
* a bound variable can be neither a pattern (`dererr067`) nor a replacement
  (`dererr069`);
* quantifiers and connectives are not letters (`dererr071`).

A **SchemeInstantiation** can be serialized. `encode()` writes
`pattern:replacement.pattern:replacement…`, and `decode()` parses that form back
(each part through `LogicProgram.m1008` with placeholders allowed). The encoded
form appears in derivation work records, for example in `C_HF`'s justification
string.

### Instantiation

`Expression.instantiate(context, inst, binderMap, stack)` copies the tree.
`copy()` is the same call with `inst == null`. The steps are:

1. At a letter node that has a replacement, the result is
   `replacement.instantiate(thisNode, …)`. The letter node becomes the
   *context*.
2. Inside a replacement, each argument-placeholder `SimpleTerm` `{i}` is replaced
   by the instantiation of the context's *i*-th argument. The `stack` records
   which placeholders are being expanded, so nested scheme applications resolve
   correctly.
3. Quantifiers and descriptions register `(context, oldBinder, stack) → newBinder`
   in the `BinderMap`. Each copied bound variable looks up its new binder there,
   so the copy's variable links are correct.

### Matching

`pattern.match(instance, inst)` walks the pattern and the instance in parallel.

* Quantifiers and descriptions must agree in symbol. They record the
  correspondence of their binders in the `BinderMap`.
* A bound variable matches when its binder corresponds to the instance
  variable's binder.
* At a letter node, `matchLetter` runs:
  * If the letter already has a replacement, the replacement is matched against
    the instance, with the letter node as context. This makes a letter
    consistent across all of its occurrences.
  * Otherwise `buildReplacement` makes one. Each pattern argument must be a bound
    variable. Its counterpart variable in the instance is found through the
    `BinderMap`, and both are renamed to `{i}`. The result is
    `F({1}) → instance[with that variable ↦ {1}]`.

    This is simple higher-order pattern matching. It lets a rule such as
    `@x Fx .: Fa` match `@y (Gy & Hy)` with `F({1}) := G{1}&H{1}`.
* A replacement that conflicts with an earlier one fails with `dererr073`.

Some letters occur only in positions that can't determine them, for example
only in a conclusion, or only as the argument of a letter that is still unknown.
`addPendingLetters` lists these in `pendingLetters`. Matches that depend on such
a letter become `DeferredMatch` objects attached to the letter
(`getDeferredMatches`). They are re-run (`checkDeferredMatches`) when
`putReplacement` finally gives the letter a value (`dererr062` on failure).
`assignFreshLetters` fills the remaining pending letters with fresh ones from
`LetterGenerator`. The derivation UI can instead show the pending letters in a
`SchemeSubstitutionPanel` for the student to fill in. That panel rejects
replacements that use undeclared placeholders (`hasUndeclaredPlaceholder`,
`dererr072`).

The fresh-letter alphabets are:

* term letters: `a…z`;
* operation letters: the reversed operation-letter set;
* predicate letters: the reversed predicate- or sentence-letter set.

Because the sets are reversed, generated letters start at the end of the
alphabet, for example `O`, `N`, ... or `Z`, `Y`, .... They then continue with
suffixed forms such as `O0`.

## 7. Notable details

* The two parser packages are byte-for-byte the same grammar. Only the
  token manager's letter classes (the NFA bitmasks) and `jjnextStates` order
  differ, so the member ids of pkgA and pkgB correspond one-to-one in source
  order. The mapping was generated that way.
* `<>` has no node of its own. It becomes `~(a=b)` with `displayAsInequality`
  set, so that the printer can reproduce what the user typed.
* `AtomicFormula(Formula)`/`wrappedFormula`, the private `variable`/`body`
  fields of `DescriptionTerm`, `PremiseExpression` and `DeferredMatch.contextTerms`
  (never assigned by its constructor) all look unused, which suggests leftovers
  from earlier versions.
* `jj_3R_n` numbers are assigned in declaration order. JavaCC's original
  numbering can't be recovered.
* Class names: core.mapping already names the pkgA/pkgB classes `Syntax2Token`
  and so on, so the standard JavaCC class names (`Token`, `SimpleCharStream`,
  `ParseException`, `TokenMgrError`) appear here only as descriptions. Using
  them for both packages would break the program-wide rule that class names are
  unique.
