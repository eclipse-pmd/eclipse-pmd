# Changed Rules

- SwitchStmtsShouldHaveDefault (Java Best Practices) doesn't report empty switch statements anymore.
  To detect these, use EmptyControlStatement.
- UnitTestShouldUseAfterAnnotation (Java Best Practices) now also considers JUnit 5 and TestNG tests.
- UnitTestShouldUseBeforeAnnotation (Java Best Practices) now also considers JUnit 5 and TestNG tests.
- TooFewBranchesForSwitch (Java Performance) doesn't report empty switches anymore.
  To detect these, use EmptyControlStatement.

# Renamed Rules

Several rules for unit testing have been renamed to better reflect their actual scope. Lots of them were called
after JUnit / JUnit 4, even when they applied to JUnit 5 and / or TestNG.

- UnitTestAssertionsShouldIncludeMessage (Java Best Practices) has been renamed from JUnitAssertionsShouldIncludeMessage.
- UnitTestContainsTooManyAsserts (Java Best Practices) has been renamed from JUnitTestContainsTooManyAsserts.
- UnitTestShouldIncludeAssert (Java Best Practices) has been renamed from JUnitTestsShouldIncludeAssert.
- UnitTestShouldUseAfterAnnotation (Java Best Practices) has been renamed from JUnit4TestShouldUseAfterAnnotation.
- UnitTestShouldUseBeforeAnnotation (Java Best Practices) has been renamed from JUnit4TestShouldUseBeforeAnnotation.
- UnitTestShouldUseTestAnnotation (Java Best Practices) has been renamed from JUnit4TestShouldUseTestAnnotation.

Several rules about switch statements and switch expression have been renamed, as they apply both to Switch Statements
and to Switch Expressions:

- DefaultLabelNotLastInSwitch (Java Best Practices) has been renamed from DefaultLabelNotLastInSwitchStmt.
- NonCaseLabelInSwitch (Java Error Prone) has been renamed from NonCaseLabelInSwitchStatement.
- TooFewBranchesForSwitch (Java Performance) has been renamed from TooFewBranchesForASwitchStatement.
- NonExhaustiveSwitch (Java Best Practices) has been renamed from SwitchStmtsShouldHaveDefault.

The old rule names still work but are deprecated.

# Fixed Issues

## apex-performance
- #5270: [apex] AvoidNonRestrictiveQueries when LIMIT is followed by bind expression

## java
- #4532: [java] Rule misnomer for JUnit* rules
- #5261: [java] Record patterns with empty deconstructor lists lead to NPE

## java-bestpractices
- #4286: [java] Rename rule SwitchStmtsShouldHaveDefault to NonExhaustiveSwitch
- #4813: [java] SwitchStmtsShouldHaveDefault false positive with pattern matching

## java-codestyle
- #5253: [java] BooleanGetMethodName: False-negatives with Boolean wrapper

## java-design
- #5030: [java] SwitchDensity false positive with pattern matching

## java-errorprone
- #3362: [java] ImplicitSwitchFallThrough should consider switch expressions
- #5067: [java] CloseResource: False positive for FileSystems.getDefault()
- #5244: [java] UselessOperationOnImmutable should detect java.time types
- #5257: [java] NonCaseLabelInSwitch should consider switch expressions

## java-performance
- #5249: [java] TooFewBranchesForASwitchStatement false positive for Pattern Matching
- #5250: [java] TooFewBranchesForASwitchStatement should consider Switch Expressions

# API Changes

## java-bestpractices
- The old rule name JUnit4TestShouldUseAfterAnnotation has been deprecated. Use the new name UnitTestShouldUseAfterAnnotation instead.
- The old rule name JUnit4TestShouldUseBeforeAnnotation has been deprecated. Use the new name UnitTestShouldUseBeforeAnnotation instead.
- The old rule name JUnit4TestShouldUseTestAnnotation has been deprecated. Use the new name UnitTestShouldUseTestAnnotation instead.
- The old rule name JUnitAssertionsShouldIncludeMessage has been deprecated. Use the new name UnitTestAssertionsShouldIncludeMessage instead.
- The old rule name JUnitTestContainsTooManyAsserts has been deprecated. Use the new name UnitTestContainsTooManyAsserts instead.
- The old rule name JUnitTestsShouldIncludeAssert has been deprecated. Use the new name UnitTestShouldIncludeAssert instead.
- The old rule name DefaultLabelNotLastInSwitchStmt has been deprecated. Use the new name DefaultLabelNotLastInSwitch instead.
- The old rule name SwitchStmtsShouldHaveDefault has been deprecated. USe the new name NonExhaustiveSwitch instead.

## java-errorprone
- The old rule name NonCaseLabelInSwitchStatement has been deprecated. Use the new name NonCaseLabelInSwitch instead.

## java-performance
- The old rule name TooFewBranchesForASwitchStatement has been deprecated. Use the new name TooFewBranchesForSwitch instead.

# Merged pull requests

- #4965: Fix #4532: [java] Rename JUnit rules with overly restrictive names - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5040: [cpp] Ignore literals and ignore identifiers capability to C++ CPD - Jakub Dupak (@jdupak)
- #5225: Fix #5067: [java] CloseResource: False positive for FileSystems.getDefault() - Lukas Gräf (@lukasgraef)
- #5241: Ignore javacc code in coverage report - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5245: [java] Improve UnitTestShouldUse{After,Before}Annotation rules to support JUnit5 and TestNG - Andreas Dangel (@adangel)
- #5247: Fix #5030: [java] SwitchDensity false positive with pattern matching - Andreas Dangel (@adangel)
- #5248: Fix #3362: [java] ImplicitSwitchFallThrough should consider switch expressions - Andreas Dangel (@adangel)
- #5251: Fix #5249 and #5250: [java] TooFewBranchesForSwitch ignore pattern matching and support switch expressions - Andreas Dangel (@adangel)
- #5252: Fix #4813: [java] SwitchStmtsShouldHaveDefault false positive with pattern matching - Andreas Dangel (@adangel)
- #5255: [java] Rename rule DefaultLabelNotLastInSwitch - Andreas Dangel (@adangel)
- #5256: Fix #5257: [java] NonCaseLabelInSwitch - support switch expressions - Andreas Dangel (@adangel)
- #5258: Ignore generated antlr classes in coverage reports - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5264: Fix #5261: [java] Fix NPE with empty pattern list - Clément Fournier (@oowekyala)
- #5267: [java] Rename rule SwitchStmtsShouldHaveDefault to NonExhaustiveSwitch - Andreas Dangel (@adangel)
- #5269: Fix #5253: [java] Support Boolean wrapper class for BooleanGetMethodName rule - Aryant Tripathi (@Aryant-Tripathi)
- #5273: Fix #5270: [apex] AvoidNonRestrictiveQueries: Fix regex for detecting LIMIT clause - Andreas Dangel (@adangel)
- #5275: Use plugin-classpath to simplify javacc-wrapper.xml - Andreas Dangel (@adangel)
- #5278: [java] CouplingBetweenObjects: improve violation message - Andreas Dangel (@adangel)
- #5279: Fix #5244: [java] UselessOperationOnImmutable: consider java.time.* types - Andreas Dangel (@adangel)
