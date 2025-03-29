# New Rules

The new Java rule `ImplicitFunctionalInterface` reports functional interfaces that were
not explicitly declared as such with the annotation `@FunctionalInterface`. If an interface is accidentally a functional
interface, then it should bear a `@SuppressWarnings("PMD.ImplicitFunctionalInterface")`
annotation to make this clear.

# Fixed Issues

## core

- #5593: [core] Make renderers output files in deterministic order even when multithreaded

## apex

- #5567: [apex] Provide type information for CastExpression

## apex-design

- #5616: [apex] ExcessiveParameterList reports entire method instead of signature only

## java

- #5587: [java] Thread deadlock during PMD analysis in ParseLock.getFinalStatus

## java-bestpractices

- #2849: [java] New Rule: ImplicitFunctionalInterface
- #5369: [java] UnusedPrivateMethod false positives with lombok.val
- #5590: [java] LiteralsFirstInComparisonsRule not applied on constant
- #5592: [java] UnusedAssignment false positive in record compact constructor

## java-codestyle

- #5079: [java] LocalVariableCouldBeFinal false-positive with lombok.val
- #5452: [java] PackageCase: Suppression comment has no effect due to finding at wrong position in case of JavaDoc comment

## plsql

- #4441: [plsql] Parsing exception with XMLQUERY function in SELECT
- #5521: [plsql] Long parse time and eventually parse error with XMLAGG order by clause

# Merged pull requests

- #5550: Fix #5521: [plsql] Improve parser performance by reducing lookaheads - Andreas Dangel (@adangel)
- #5554: Fix #5369: [java] Consider that lombok.val and var are inferred - Clément Fournier (@oowekyala)
- #5555: Fix #2849: [java] Add rule ImplicitFunctionalInterface - Clément Fournier (@oowekyala)
- #5556: [ci] New workflow "Publish Results from Pull Requests" - Andreas Dangel (@adangel)
- #5574: Fix #5567: [apex] Provide type info for CastExpression - Andreas Dangel (@adangel)
- #5583: [java] Fix race condition in ClassStub for inner classes - Andreas Dangel (@adangel)
- #5586: [java/core] Micro optimizations - Clément Fournier (@oowekyala)
- #5588: [java] Fix crash when parsing class for anonymous class - Clément Fournier (@oowekyala)
- #5591: Fix #5587: [java] Fix deadlock while loading ClassStub - Clément Fournier (@oowekyala)
- #5593: [core] Make renderers output files in deterministic order even when multithreaded - Clément Fournier (@oowekyala)
- #5595: Fix #5590: [java] LiteralsFirstInComparisons with constant field - Clément Fournier (@oowekyala)
- #5596: Fix #4441: [plsql] XMLQuery - Support identifier as XQuery_string parameter - Andreas Dangel (@adangel)
- #5598: Fix #5592: [java] Fix UnusedAssignment FP with compact record ctor - Clément Fournier (@oowekyala)
- #5600: Fix #5079: [java] LocalVariableCouldBeFinal false-positive with lombok.val - Clément Fournier (@oowekyala)
- #5611: Fix #5452: [java] PackageCase reported on wrong line - Clément Fournier (@oowekyala)
- #5617: Fix #5616: [apex] ExcessiveParameterList: Report only method signature - Andreas Dangel (@adangel)
- #5618: [doc] Fix search index - Andreas Dangel (@adangel)
