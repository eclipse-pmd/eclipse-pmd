# New and noteworthy

## New rules
- The new Apex rule AvoidDebugStatements finds usages of System.debug calls. Debug statements contribute to longer transactions and consume Apex CPU time even when debug logs are not being captured. You can try out this rule like so: <rule ref="category/apex/performance.xml/AvoidDebugStatements" />
- The new Apex rule InaccessibleAuraEnabledGetter checks that an AuraEnabled getter is public or global. This is necessary if it is referenced in Lightning components. You can try out this rule like so:  <rule ref="category/apex/errorprone.xml/InaccessibleAuraEnabledGetter" />

## Renamed rules
- The Java rule BadComparison has been renamed to ComparisonWithNaN to better reflect what the rule actually detects. It now considers usages of Double.NaN or Float.NaN in more cases and fixes false negatives.

# Fixed Issues

## apex
- #3307: [apex] Avoid debug statements since it impact performance
- #3321: [apex] New rule to detect inaccessible AuraEnabled getters (summer ‘21 security update)
- #3332: [apex] CognitiveComplexity - incorrect increment for “else if”
## core
- #2637: [cpd] Error Loading stylesheet cpdhtml.xslt
- #3323: [core] Adds fullDescription and tags in SARIF report
## java-bestpractices
- #957: [java] GuardLogStatement: False positive with compile-time constant arguments
- #3076: [java] UnusedAssignment reports unused variable when used in increment expr
- #3114: [java] UnusedAssignment false positive when reporting unused variables
- #3315: [java] LiteralsFirstInComparisons false positive with two constants
- #3341: [java] JUnitTestsShouldIncludeAssert should support Junit 5
- #3340: [java] NullPointerException applying rule GuardLogStatement
## java-codestyle
- #3317: [java] Update UnnecessaryImport to recognize usage of imported types in javadoc’s @exception tag
## java-errorprone
- #2895: [java] Improve BadComparison and rename to ComparisonWithNaN
- #3284: [java] InvalidLogMessageFormat may examine the value of a different but identically named String variable
- #3304: [java] NPE in MoreThanOneLoggerRule on a java 16 record
- #3305: [java] ConstructorCallsOverridableMethodRule IndexOutOfBoundsException on a java16 record
- #3343: [java] CloneMethodMustImplementCloneable: FN with local classes
## java-performance
- #3331: [java] UseArraysAsList false negative with for-each loop
- #3344: [java] InefficientEmptyStringCheck FN with trim.length on method call

# External Contributions

- #3276: [apex] Update ApexCRUDViolation and OperationWithLimitsInLoop docs - Jonathan Wiesel
- #3306: [java] More than one logger rule test null pointer exception - Arnaud Jeansen
- #3317: [java] Update UnnecessaryImport to recognize usage of imported types in javadoc’s @exception tag - Piotrek Żygieło
- #3319: [apex] New AvoidDebugStatements rule to mitigate performance impact - Jonathan Wiesel
- #3320: [java] Fix incorrect increment for “else if” branch in Cognitive Complexity docs - Denis Borovikov
- #3322: [apex] added rule to detect inaccessible AuraEnabled getters - Philippe Ozil
- #3323: [core] Adds fullDescription and tags in SARIF report - Clint Chester
- #3339: [java] JUnitTestsShouldIncludeAssert Tweak assertion definition to avoid false positive with modern JUnit5 - Arnaud Jeansen
