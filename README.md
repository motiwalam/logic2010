# Logic 2010, decompiled

Reverse-engineered source for **Logic 2010** (UCLA Philosophy's logic-teaching
program), recovered from the installed jars so it can be read, understood and
rebuilt from scratch.

## Layout

| Path | What |
|---|---|
| `original/` | The untouched input jars: `logic.jar` (main program, yGuard-obfuscated) and `loader.jar` (updater/launcher, not obfuscated). |
| `logic/src/main/java` | Decompiled, deobfuscated source of `logic.jar`. |
| `loader/src/main/java` | Decompiled source of `loader.jar`. |
| `mappings/*.mapping` | Human-chosen names for obfuscated classes/fields/methods. |
| `mappings/ids.txt` | Generated: every placeholder id (`f123`, `m45`, `C_xY`) with its original obfuscated owner/name/descriptor. |
| `tools/remap/Remap.java` | ASM-based remapper that applies the mappings to the bytecode. |
| `tools/deobfuscate.sh` | Regenerates `logic/src/main/java` from `original/` + `mappings/`. |
| `build.sh` | Compiles both jars into `build/`. Needs only a JDK. |

## Building

```sh
./build.sh          # -> build/logic.jar, build/loader.jar
```

## How the deobfuscation works

`logic.jar` was obfuscated with yGuard: class names like `bB`, method names like
`A`/`B` (heavily overloaded, including by return type only, which Java source
can't express), and field names made of IPA characters (`ʒ`, `ɭ`). There is no
debug info. String literals, and names needed for reflection, were left alone.

1. `tools/remap` gives every obfuscated identifier a unique placeholder:
   classes `C_bB` (lowercase letters get a trailing `_`, `$` becomes `0`, so no two names differ only in case),
   fields `f1..`, methods `m1..`. Methods that override each other share an id.
2. Entries in `mappings/*.mapping` replace placeholders with real names:
   ```
   class bB MainWindow     # obfuscated class name -> new name
   m123 parseFormula       # method id -> name
   f45  currentUser        # field id -> name
   ```
   The remapper refuses mappings that would make methods accidentally override each other.
3. Vineflower decompiles the remapped jar.
