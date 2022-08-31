# New Rules

The new Java rule UnusedAssignment (java-bestpractices) finds assignments to variables, that are never used and are useless. The new rule is supposed to entirely replace DataflowAnomalyAnalysis.

# Modified rules

The Java rule ArrayIsStoredDirectly (java-bestpractices) now ignores by default private methods and constructors. You can restore the old behavior by setting the new property allowPrivate to “false”.

# Fixed Issues

## apex
- #2610: [apex] Support top-level enums in rules
## apex-bestpractices
- #2626: [apex] UnusedLocalVariable - false positive on case insensitivity allowed in Apex
## apex-performance
- #2598: [apex] AvoidSoqlInLoops false positive for SOQL with in For-Loop
## apex-security
- #2620: [visualforce] False positive on VfUnescapeEl with new Message Channel feature
## core
- #710: [core] Review used dependencies
- #2594: [core] Update exec-maven-plugin and align it in all project
- #2615: [core] PMD/CPD produces invalid XML (insufficient escaping/wrong encoding)
## java-bestpractices
- #2543: [java] UseCollectionIsEmpty can not detect the case this.foo.size()
- #2569: [java] LiteralsFirstInComparisons: False negative for methods returning Strings
- #2622: [java] ArrayIsStoredDirectly false positive with private constructor/methods
## java-codestyle
- #2546: [java] DuplicateImports reported for the same import… and import static…
## java-design
- #2174: [java] LawOfDemeter: False positive with ‘this’ pointer
- #2181: [java] LawOfDemeter: False positive with indexed array access
- #2189: [java] LawOfDemeter: False positive when casting to derived class
- #2580: [java] AvoidThrowingNullPointerException marks all NullPointerException objects as wrong, whether or not thrown
- #2625: [java] NPathComplexity can’t handle switch expressions
## java-errorprone
- #2578: [java] AvoidCallingFinalize detects some false positives
- #2634: [java] NullPointerException in rule ProperCloneImplementation
## java-performance
- #1736: [java] UseStringBufferForStringAppends: False positive if only one concatenation
- #2207: [java] AvoidInstantiatingObjectsInLoops: False positive - should not flag objects when assigned to lists/arrays

# External Contributions

- #2558: [java] Fix issue #1736 and issue #2207 - Young Chan
- #2560: [java] Fix false positives of LawOfDemeter: this and cast expressions - xioayuge
- #2590: Update libraries snyk is referring to as unsafe - Artem Krosheninnikov
- #2597: [dependencies] Fix issue #2594, update exec-maven-plugin everywhere - Artem Krosheninnikov
- #2621: [visualforce] add new safe resource for VfUnescapeEl - Peter Chittum
- #2640: [java] NullPointerException in rule ProperCloneImplementation - Mykhailo Palahuta
- #2641: [java] AvoidThrowingNullPointerException marks all NullPointerException… - Mykhailo Palahuta
- #2643: [java] AvoidCallingFinalize detects some false positives (2578) - Mykhailo Palahuta
- #2651: [java] False negative: LiteralsFirstInComparisons for methods… (2569) - Mykhailo Palahuta
- #2652: [java] UseCollectionIsEmpty can not detect the case this.foo.size() - Mykhailo Palahuta
