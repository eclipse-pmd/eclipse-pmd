# New Rules

The new Apex rule AvoidStatefulDatabaseResult detects Database.Stateful implementations that store database results in instance variables. This can cause serialization issues between successive batch iterations.

# Fixed Issues

## apex-errorprone

- #5305: [apex] New Rule: Avoid Stateful Database Results

## java

- #5442: [java] StackOverflowError with recursive generic types
- #5493: [java] IllegalArgumentException: <?> cannot be a wildcard bound
- #5505: [java] java.lang.StackOverflowError while executing a PmdRunnable

## java-bestpractices

- #3359: [java] UnusedPrivateMethod does not recognize Lombok @EqualsAndHashCode.Include annotation
- #5486: [java] UnusedPrivateMethod detected when class is referenced in another class
- #5504: [java] UnusedAssignment false-positive in for-loop with continue

## java-codestyle

- #4822: [java] UnnecessaryCast false-positive for raw types
- #5073: [java] UnnecessaryCast false-positive for cast in return position of lambda
- #5440: [java] UnnecessaryCast reported in stream chain map() call that casts to more generic interface
- #5523: [java] UnnecessaryCast false-positive for integer operations in floating-point context
- #5541: [java] Fix IdenticalCatchBranch reporting branches that call different overloads

## java-design

- #5018: [java] FinalFieldCouldBeStatic false-positive for access of super class field

## plsql

- #5522: [plsql] Parse error for operator in TRIM function call

# Merged pull requests

- #5425: [apex] New Rule: Avoid Stateful Database Results - Mitch Spano (@mitchspano)
- #5491: [docs] Call render_release_notes.rb within docs - Andreas Dangel (@adangel)
- #5492: [docs] Add security page with known vulnerabilities - Andreas Dangel (@adangel)
- #5503: [java] AvoidSynchronizedAtMethodLevel: Fixed error in code example - Balazs Glatz (@gbq6)
- #5507: Fix #5486: [java] Fix UnusedPrivateMethod - always search decls in current AST - Andreas Dangel (@adangel)
- #5508: Fix #3359: [java] UnusedPrivateMethod: Ignore lombok.EqualsAndHashCode.Include - Andreas Dangel (@adangel)
- #5510: [ci] Add signed releases - Andreas Dangel (@adangel)
- #5524: [ci] New optimized workflow for pull requests - Andreas Dangel (@adangel)
- #5526: Fix #5523: [java] UnnecessaryCast FP with integer division - Clément Fournier (@oowekyala)
- #5527: Fix #5522: [plsql] Allow arbitrary expressions for TRIM - Andreas Dangel (@adangel)
- #5528: Fix #5442: [java] Fix stackoverflow with recursive generic types - Clément Fournier (@oowekyala)
- #5529: Fix #5493: [java] IllegalArgumentException with wildcard bound - Clément Fournier (@oowekyala)
- #5530: Fix #5073: [java] UnnecessaryCast FP with lambdas - Clément Fournier (@oowekyala)
- #5537: Fix #5504: [java] UnusedAssignment FP with continue in foreach loop - Clément Fournier (@oowekyala)
- #5538: Add project icon for IntelliJ IDEA - Vincent Potucek (@pankratz227)
- #5539: [plsql] Add OracleDBUtils as regression testing project - Andreas Dangel (@adangel)
- #5541: [java] Fix IdenticalCatchBranch reporting branches that call different overloads - Clément Fournier (@oowekyala)
- #5542: Add GitHub issue links in IDEA git log - Andreas Dangel (@adangel)
- #5544: [javacc] Move grammar files into src/main/javacc - Andreas Dangel (@adangel)
- #5551: [doc] Update contributors for 7.11.0 - Andreas Dangel (@adangel)
- #5552: Fix #4822: [java] UnnecessaryCast FP with unchecked cast - Clément Fournier (@oowekyala)
- #5553: Fix #5018: [java] FinalFieldCouldBeStatic FP with super field access - Clément Fournier (@oowekyala)
