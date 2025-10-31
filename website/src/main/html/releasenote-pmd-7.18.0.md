# New and noteworthy

## New Rules

- The new Java rule `IdenticalConditionalBranches` finds conditional statements
  that do the same thing when the condition is true and false. This is either incorrect or redundant.
- The new Java rule `LabeledStatement` finds labeled statements in code.
  Labels make control flow difficult to understand and should be avoided. By default, the rule allows labeled
  loops (do, while, for). But it has a property to flag also those labeled loops.
- The new Java rule `UnusedLabel` finds unused labels which are unnecessary and
  only make the code hard to read. This new rule will be part of the quickstart ruleset.

## Changed Rules

- `ConfusingTernary` has a new property `nullCheckBranch` to control, whether null-checks
  should be allowed (the default case) or should lead to a violation.
- `AvoidCatchingGenericException` is now configurable with the new property
  `typesThatShouldNotBeCaught`.  
  ⚠️ The rule has also been moved from category "Design" to category "Error Prone". If you are currently bulk-adding
  all the rules from the "Design" category into your custom ruleset, then you need to add the rule explicitly
  again (otherwise it won't be included anymore):  
  `<rule ref="category/java/errorprone.xml/AvoidCatchingGenericException" />`

## Deprecated Rules

- The Java rule `AvoidCatchingNPE` has been deprecated in favor of the updated rule
  `AvoidCatchingGenericException`, which is now configurable.
- The Java rule `AvoidCatchingThrowable` has been deprecated in favor of the updated rule
  `AvoidCatchingGenericException`, which is now configurable.

# Fixed Issues

## apex

- #5935: [apex] @SuppressWarnings - allow whitespace around comma when suppressing multiple rules

## apex-design

- #6022: [apex] ExcessiveClassLength/ExcessiveParameterList include the metric in the message

## apex-documentation

- #6189: [apex] ApexDoc rule doesn't match published Salesforce ApexDoc specification

## java

- #4904: [java] Renderers output wrong class qualified name for nested classes
- #6127: [java] Incorrect variable name in violation
- #6132: [java] Implement main method launch protocol priorities
- #6146: [java] ClassCastException: class InferenceVarSym cannot be cast to class JClassSymbol

## java-bestpractices

- #2928: [java] New rules about labeled statements
- #4122: [java] CheckResultSet false-positive with local variable
- #6124: [java] UnusedLocalVariable: fix false negatives in pattern matching
- #6169: [java] AvoidUsingHardCodedIP: violation message should mention the hard coded address
- #6171: [java] AvoidUsingHardCodedIP: fix false positive for IPv6

## java-codestyle

- #5919: [java] ClassNamingConventions: Include integration tests in testClassPattern by default
- #6004: [java] Make ConfusingTernary != null configurable
- #6029: [java] Fix UnnecessaryCast false-negative in method calls
- #6057: [java] ModifierOrder false positive on "abstract sealed class"
- #6079: [java] IdenticalCatchBranches: False negative for overriden method calls
- #6123: [java] UselessParentheses FP around switch expression
- #6131: [java] ModifierOrder: wrong enum values documented, indirectly causing xml parse errors

## java-design

- #1499: [java] AvoidDeeplyNestedIfStmts violations can be unintentionally undetected
- #5569: [java] ExcessivePublicCount should report number of public "things"

## java-documentation

- #6058: [java] DanglingJavadoc FP in module-info files
- #6103: [java] DanglingJavadoc false positive on record compact constructors

## java-errorprone

- #5042: [java] CloseResource false-positive on Pattern Matching with instanceof
- #5878: [java] DontUseFloatTypeForLoopIndices false-negative if variable is declared before loop
- #6038: [java] Merge AvoidCatchingNPE and AvoidCatchingThrowable into AvoidCatchingGenericException
- #6055: [java] UselessPureMethodCall false positive with AtomicInteger::getAndIncrement
- #6060: [java] UselessPureMethodCall false positive on ZipInputStream::getNextEntry
- #6075: [java] AssignmentInOperand false positive with lambda expressions
- #6083: [java] New rule IdenticalConditionalBranches

## java-multithreading

- #5880: [java] DoubleCheckedLocking is not detected if more than 1 assignment or more than 2 if statements

## java-performance

- #6172: [java] InefficientEmptyStringCheck should include String#strip

## java-security

- #6191: [java] HardCodedCryptoKey: NPE when constants from parent class are used

## plsql-design

- #6077: [plsql] Excessive*/Ncss*Count/NPathComplexity include the metric

# Merged pull requests

- #6021: [java] Fix #5569: ExcessiveImports/ExcessiveParameterList/ExcessivePublicCount include the metric in the message - UncleOwen (@UncleOwen)
- #6022: [apex] ExcessiveClassLength/ExcessiveParameterList include the metric in the message - UncleOwen (@UncleOwen)
- #6023: [test] Fix #6012: Alphabetically sort all default rules - Zbynek Konecny (@zbynek)
- #6024: [java] Fix #5878: DontUseFloatTypeForLoopIndices now checks the UpdateStatement as well - UncleOwen (@UncleOwen)
- #6029: [java] Fix UnnecessaryCast false-negative in method calls - Zbynek Konecny (@zbynek)
- #6031: [java] Fix #5880: False Negatives in DoubleCheckedLocking - Lukas Gräf (@lukasgraef)
- #6039: [core] Fix #4714: trim token before feeding it to the extractor - UncleOwen (@UncleOwen)
- #6040: [java,apex,plsql,velocity] Change description of "minimum" parameter - UncleOwen (@UncleOwen)
- #6042: [java] Fix #2928: New Rules UnusedLabel and LabeledStatement - UncleOwen (@UncleOwen)
- #6043: [java] Reactivate deactivated test in LocalVariableCouldBeFinal - UncleOwen (@UncleOwen)
- #6051: [java] Fix #6038: Make AvoidCatchingGenericException configurable - UncleOwen (@UncleOwen)
- #6056: chore: fix dogfood issues from new rules - Andreas Dangel (@adangel)
- #6059: [java] Fix #6058: DanglingJavadoc FP in module-info files - Lukas Gräf (@lukasgraef)
- #6061: [core] chore: Bump minimum Java version required for building to 17 - Andreas Dangel (@adangel)
- #6071: [java] Fix #5919: Add integration tests to ClassNamingConventions testClassRegex - Anton Bobov (@abobov)
- #6073: [doc] Search improvements - Andreas Dangel (@adangel)
- #6074: [apex] Fix @SuppressWarnings with whitespace around comma - Juan Martín Sotuyo Dodero (@jsotuyod)
- #6077: [plsql] Excessive*/Ncss*Count/NPathComplexity include the metric - Andreas Dangel (@adangel)
- #6078: [java] Fix #6075: Fix FP in AssignmentInOperandRule - UncleOwen (@UncleOwen)
- #6080: [java] Fix #6079: IdenticalCatchBranches for overriden method calls - Zbynek Konecny (@zbynek)
- #6082: [java] Fix false positives in UselessPureMethodCall for streams and atomics - Zbynek Konecny (@zbynek)
- #6083: [java] New rule IdenticalConditionalBranches - Zbynek Konecny (@zbynek)
- #6085: [java] Fix false positive for ModifierOrder - Zbynek Konecny (@zbynek)
- #6093: [ci] Fix #5873: Run integration tests with Java 25 additionally - Andreas Dangel (@adangel)
- #6097: [doc] Add PMD versions dropdown - Andreas Dangel (@adangel)
- #6098: [doc] Add a copy URL button - Andreas Dangel (@adangel)
- #6100: [java] AvoidDeeplyNestedIfStmts: fix false negative with if-else - Zbynek Konecny (@zbynek)
- #6101: [doc] Highlight current header in TOC - Andreas Dangel (@adangel)
- #6112: [java] DanglingJavadoc: fix false positive for compact constructors - Zbynek Konecny (@zbynek)
- #6114: [java] Fix #4122: CheckResultSet false-positive with local variable - Lukas Gräf (@lukasgraef)
- #6116: [java] ConfusingTernary: add configuration property for null checks - Zbynek Konecny (@zbynek)
- #6124: [java] UnusedLocalVariable: fix false negatives in pattern matching - Zbynek Konecny (@zbynek)
- #6128: [java] Fix #4904: Correct class name in violation decorator - Andreas Dangel (@adangel)
- #6129: [java] Fix #6127: Correct var name in violation decorator - Andreas Dangel (@adangel)
- #6130: [java] UselessParentheses: fix false positives for switch expressions - Zbynek Konecny (@zbynek)
- #6132: [java] Implement main method launch protocol priorities - Zbynek Konecny (@zbynek)
- #6133: [java] Fix #5042: CloseResource: fix false positive with pattern matching - Zbynek Konecny (@zbynek)
- #6150: [core] Reduce memory usage of CPD's MatchCollector - Lukas Gräf (@lukasgraef)
- #6152: chore(deps): Update Saxon-HE from 12.5 to 12.9 - Zbynek Konecny (@zbynek)
- #6156: [java] Fix #6146: ClassCastException in TypeTestUtil - Clément Fournier (@oowekyala)
- #6164: [doc] Update reproducible build info with Java 17 - Andreas Dangel (@adangel)
- #6166: [doc] Use emoji variants - Andreas Dangel (@adangel)
- #6170: [java] Fix #6169: AvoidUsingHardCodedIP - mention address in message - Andreas Dangel (@adangel)
- #6171: [java] AvoidUsingHardCodedIP: fix false positive for IPv6 - Andreas Dangel (@adangel)
- #6172: [java] InefficientEmptyStringCheck should include String#strip - Zbynek Konecny (@zbynek)
- #6185: [java] Fix #6131: Correct enum values for ModifierOrder - Andreas Dangel (@adangel)
- #6190: [apex] Update ApexDoc rule to match the published specification - Mitch Spano (@mitchspano)
- #6191: [java] HardCodedCryptoKey: NPE when constants from parent class are used - Zbynek Konecny (@zbynek)
