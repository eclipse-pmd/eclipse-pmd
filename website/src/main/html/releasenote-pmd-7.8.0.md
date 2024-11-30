# New Rules

- The new Apex rule QueueableWithoutFinalizer detects when the Queueable interface is used but a Finalizer is not attached. Without attaching a Finalizer, there is no way of designing error recovery actions should the Queueable action fail.


# Fixed Issues

## apex

- #5302: [apex] New Rule: Queueable Should Attach Finalizer
- #5333: [apex] Token recognition errors for string containing unicode escape sequence

## html

- #5322: [html] CPD throws exception on when HTML file is missing closing tag

## java

- #5283: [java] AssertionError “this should be unreachable” with scala library
- #5293: [java] Deadlock when executing PMD in multiple threads
- #5324: [java] Issue with type inference of nested lambdas
- #5329: [java] Type inference issue with unknown method ref in call chain
- #5338: [java] Unresolved target type for lambdas make overload resolution fail

## java-bestpractices

- #4113: [java] JUnitTestsShouldIncludeAssert - false positive with SoftAssertionsExtension
- #5083: [java] UnusedPrivateMethod false positive when method reference has no target type
- #5097: [java] UnusedPrivateMethod FP with raw type missing from the classpath
- #5318: [java] PreserveStackTraceRule: false-positive on Pattern Matching with instanceof

## java-codestyle

- #5214: [java] Wrong message for LambdaCanBeMethodReference with method of enclosing class
- #5263: [java] UnnecessaryFullyQualifiedName: false-positive in an enum that uses its own static variables
- #5315: [java] UnnecessaryImport false positive for on-demand imports

## java-design

- #4763: [java] SimplifyBooleanReturns - wrong suggested solution

## java-errorprone

- #5070: [java] ConfusingArgumentToVarargsMethod FP when types are unresolved

## java-performance

- #5287: [java] TooFewBranchesForSwitch false-positive with switch using list of case constants
- #5314: [java] InsufficientStringBufferDeclarationRule: Lack of handling for char type parameters
- #5320: [java] UseStringBufferLength: false-negative on StringBuffer of sb.toString().equals(“”)

# Merged pull requests

- #5240: Release notes improvements - Andreas Dangel (@adangel)
- #5284: [apex] Use case-insensitive input stream to avoid choking on Unicode escape sequences - Willem A. Hajenius (@wahajenius)
- #5286: [ant] Formatter: avoid reflective access to determine console encoding - Andreas Dangel (@adangel)
- #5289: [java] TooFewBranchesForSwitch - allow list of case constants - Andreas Dangel (@adangel)
- #5296: [xml] Have pmd-xml Lexer in line with other antlr grammars - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5300: Add rule test cases for issues fixed with PMD 7.0.0 - Andreas Dangel (@adangel)
- #5303: [apex] New Rule: Queueable Should Attach Finalizer - Mitch Spano (@mitchspano)
- #5309: [java] Fix #5293: Parse number of type parameters eagerly - Andreas Dangel (@adangel)
- #5310: [java] Fix #5283 - inner class has public private modifiers - Clément Fournier (@oowekyala)
- #5325: [java] Fix inference dependency issue with nested lambdas - Clément Fournier (@oowekyala)
- #5326: [java] UseStringBufferLength - consider sb.toString().equals(“”) - Andreas Dangel (@adangel)
- #5328: [html] Test for a closing tag when determining node positions - Andreas Dangel (@adangel)
- #5330: [java] Propagate unknown type better when mref is unresolved - Clément Fournier (@oowekyala)
- #5331: [java] PreserveStackTrace - consider instance type patterns - Andreas Dangel (@adangel)
- #5332: [java] InsufficientStringBufferDeclaration: Fix CCE for Character - Andreas Dangel (@adangel)
- #5334: [java] UnitTestShouldIncludeAssert - consider SoftAssertionsExtension - Andreas Dangel (@adangel)
- #5335: [kotlin] Prevent auxiliary grammars from generating lexers - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5336: [gherkin] Remove generated gherkin code from coverage report - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5337: [doc] Introducing PMD Guru on Gurubase.io - Kursat Aktas (@kursataktas)
- #5339: [java] Allow lambdas with unresolved target types to succeed inference - Clément Fournier (@oowekyala)
- #5340: [java] Fix #5097 - problem with unchecked conversion - Clément Fournier (@oowekyala)
- #5341: [java] Fix #5083 - UnusedPrivateMethod false positive with mref without target type but with exact method - Clément Fournier (@oowekyala)
- #5342: [julia] Ignore generated code in Julia module - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5345: [coco] Remove generated coco files form coverage - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5346: [typescript] Add cleanup after generating ts lexer - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5347: [tsql] Flag generated lexer as generated - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5352: [java] Add permitted subtypes to symbol API - Clément Fournier (@oowekyala)
- #5353: [java] Fix #5263 - UnnecessaryFullyQualifiedName FP with forward references - Clément Fournier (@oowekyala)
- #5354: [apex] Updated the docs for UnusedMethod as per discussion #5200 - samc-gearset (@sam-gearset)
- #5370: [java] Fix #5214 - LambdaCanBeMethodReference issue with method of enclosing class - Clément Fournier (@oowekyala)
- #5371: [doc] Improve docs on adding Antlr languages - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5372: [java] Fix #5315 - UnusedImport FP with import on demand - Clément Fournier (@oowekyala)
- #5373: [java] Fix #4763 - wrong message for SimplifyBooleanReturns - Clément Fournier (@oowekyala)
- #5374: [java] Fix #5070 - confusing argument to varargs method FP when types are unknown - Clément Fournier (@oowekyala)
