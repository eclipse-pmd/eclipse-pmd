# New and noteworthy

## Javascript module now requires at least Java 8.

The latest version of Rhino, the implementation of JavaScript we use for parsing JavaScript code, requires at least Java 8. Therefore we decided to upgrade the pmd-javascript module to Java 8 as well. This means that from now on, a Java 8 or later runtime is required in order to analyze JavaScript code. Note that PMD core still only requires Java 7.

## New rules

- `JUnit5TestShouldBePackagePrivate` enforces the convention that JUnit 5 tests should have minimal visibility. You can try out this rule like so:
- `CognitiveComplexity` uses the cognitive complexity metric to find overly complex code. This metric improves on the similar cyclomatic complexity in several ways, for instance, it incentivizes using clearly readable shorthands and idioms. See the rule documentation for more details. You can try out this rule like so:
- `MutableStaticState` finds non-private static fields that are not final. These fields break encapsulation since these fields can be modified from anywhere within the program. You can try out this rule like so:

## Modified rules

- The Java rule CompareObjectsWithEquals has now a new property typesThatCompareByReference. With that property, you can configure types, that should be whitelisted for comparison by reference. By default, java.lang.Enum and java.lang.Class are allowed, but you could add custom types here. Additionally comparisons against constants are allowed now. This makes the rule less noisy when two constants are compared. Constants are identified by looking for an all-caps identifier.

## Deprecated rules

- The java rule DefaultPackage has been deprecated in favor of CommentDefaultAccessModifier. The rule “DefaultPackage” assumes that any usage of package-access is accidental, and by doing so, prohibits using a really fundamental and useful feature of the language. To satisfy the rule, you have to make the member public even if it doesn’t need to, or make it protected, which muddies your intent even more if you don’t intend the class to be extended, and may be at odds with other rules like AvoidProtectedFieldInFinalClass. The rule CommentDefaultAccessModifier should be used instead. It flags the same thing, but has an escape hatch.
- The Java rule CloneThrowsCloneNotSupportedException has been deprecated without replacement. The rule has no real value as CloneNotSupportedException is a checked exception and therefore you need to deal with it while implementing the clone() method. You either need to declare the exception or catch it. If you catch it, then subclasses can’t throw it themselves explicitly. However, Object.clone() will still throw this exception if the Cloneable interface is not implemented. Note, this rule has also been removed from the Quickstart Ruleset (rulesets/java/quickstart.xml).

# Fixed Issues

## apex
- #3183: [apex] ApexUnitTestMethodShouldHaveIsTestAnnotation false positive with helper method
- #3243: [apex] Correct findBoundary when traversing AST
## java
- #3269: [java] Fix NPE in MethodTypeResolution
## java-bestpractices
- #1175: [java] UnusedPrivateMethod FP with Junit 5 @MethodSource
- #2219: [java] Document Reasons to Avoid Reassigning Parameters
- #2737: [java] Fix misleading rule message on rule SwitchStmtsShouldHaveDefault with non-exhaustive enum switch
- #3236: [java] LiteralsFirstInComparisons should consider constant fields (cont’d)
- #3239: [java] PMD could enforce non-public methods for Junit5 / Jupiter test methods
- #3254: [java] AvoidReassigningParameters reports violations on wrong line numbers
## java-codestyle
- #2655: [java] UnnecessaryImport false positive for on-demand imports
- #3206: [java] Deprecate rule DefaultPackage
- #3262: [java] FieldDeclarationsShouldBeAtStartOfClass: false negative with anon classes
- #3265: [java] MethodArgumentCouldBeFinal: false negatives with interfaces and inner classes
- #3266: [java] LocalVariableCouldBeFinal: false negatives with interfaces, anon classes
- #3274: [java] OnlyOneReturn: false negative with anonymous class
- #3275: [java] UnnecessaryLocalBeforeReturn: false negatives with lambda and anon class
## java-design
- #2780: [java] DataClass example from documentation results in false-negative
- #2987: [java] New Rule: Public and protected static fields must be final
- #2329: [java] Cognitive complexity rule for Java
## java-errorprone
- #3110: [java] Enhance CompareObjectsWithEquals with list of exceptions
- #3112: [java] Deprecate rule CloneThrowsCloneNotSupportedException
- #3205: [java] Make CompareObjectWithEquals allow comparing against constants
- #3248: [java] Documentation is wrong for SingletonClassReturningNewInstance rule
- #3249: [java] AvoidFieldNameMatchingTypeName: False negative with interfaces
- #3268: [java] ConstructorCallsOverridableMethod: IndexOutOfBoundsException with annotations
## java-performance
- #1438: [java] InsufficientStringBufferDeclaration false positive for initial calculated StringBuilder size
## javascript
- #699: [javascript] Update Rhino library to 1.7.13
- #2081: [javascript] Failing with OutOfMemoryError parsing a Javascript file

External Contributions

- #3272: [apex] correction for ApexUnitTestMethodShouldHaveIsTestAnnotation false positives - William Brockhus
- #3246: [java] New Rule: MutableStaticState - Vsevolod Zholobov
- #3247: [java] New rule: JUnit5TestShouldBePackagePrivate - Arnaud Jeansen
- #3293: [java] Cognitive Complexity Metric - Denis Borovikov
- pmd.github.io#12: Update quickstart.html - Igor Lyadov
