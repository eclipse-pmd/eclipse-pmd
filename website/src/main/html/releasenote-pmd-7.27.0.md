# New and Changed Rules

## New Rules

- The new java rule `UnusedReturnValue` (Java Error Prone) finds method calls whose result is not used,
  although ignoring the result of these method calls is likely a mistake.
  The rule is referenced in the quickstart.xml ruleset for Java.
- New rule `ProtectedMemberInFinalClass` (Java Design) finds protected members defined in final classes.
  Such members should use package or private visibility to clarify their intended scope.
  The rule replaces now deprecated rules `AvoidProtectedFieldInFinalClass` and `AvoidProtectedMethodInFinalClassNotExtending`
  and flags members that were previously not detected by either of these rules, such as nested types or constructors.
  The rule is referenced in the quickstart.xml ruleset for Java.

## Renamed Rules

- The rule `InstantiableUtilityClass` (Java Design) was renamed from `UseUtilityClass` to better reflect the problem.
  The old name still works but is deprecated.

## Changed Rules

- The rule `CommentRequired` (Java Documentation) has a new property `packageMethodCommentRequirement`.
  It controls whether Javadoc comments are required (or unwanted) for package-private 
  methods and constructors. Previously, only public and protected methods could be 
  configured (via `publicMethodCommentRequirement` and `protectedMethodCommentRequirement`).
  The new property defaults to Ignored, so existing rule configurations are unaffected.
- The rule `BooleanGetMethodName` (Java Codestyle) has a new property `includeWrappedType`.
  If set to true (default), the rule treats Boolean and boolean identical.
  If set to false, the rule follows the bean convention and treats Boolean like any other object.

## Deprecated Rules

- The java rule `CheckSkipResult` has been deprecated for removal in favor of the new rule `UnusedReturnValue`.
- The java rule `UselessPureMethodCall` has been deprecated for removal in favor of the new rule `UnusedReturnValue`.

# Fixed Issues

## apex

- #6478: [apex] Parser error when using CALENDAR_YEAR() in SOQL
- #6887: [apex] ParseException on Summer '26 multiline string literals ('''...''')

## apex-bestpractices

- #5904: [apex] ApexUnitTestShouldNotUseSeeAllDataTrue violation range should only be the annotation and not the entire test method

## java

- #5041: [java] Parsing failed in ParseLock#doParse(): IndexOutOfBoundsException
- #6010: [java] java.lang.OutOfMemoryError: Java heap space when accessing big Jar files with PMD 7
- #6374: [java] Support Java 27
- #6768: [java] Disambiguation IllegalStateException resolving a synthesized record accessor used as a call argument alongside an anonymous class
- #6932: [java] AssertionError when outer class is parsed before inner class with conflicting visibility

## java-bestpractices

- #1237: [java] AbstractClassWithoutAnyMethod: False positive for empty subclasses that inherit methods
- #1287: [java] GuardLogStatement: False positive when using negative guard conditions
- #2033: [jsp] NoClassAttribute: False positive for jsp:useBean
- #5514: [java] ExhaustiveSwitchHasDefault: False positive for non-exhaustive switch statements
- #5670: [java] ExhaustiveSwitchHasDefault: False positive with final fields not initialized in constructor
- #6200: [java] UnusedAssignment: False positive about the ++ unary operator
- #6393: [java] UnusedPrivateMethod: False positive with overloaded private methods called with values returned from methods of an unresolved type
- #6611: [java] UnnecessaryVarargsArrayCreation: False positive when removing the array creates overload ambiguity
- #6965: [java] AbstractClassWithoutAnyMethod: False Positive on derived abstract class

## java-codestyle

- #2974: [java] Merge rules about protected in final class (AvoidProtectedFieldInFinalClass, AvoidProtectedMethodInFinalClassNotExtending)
- #5441: [java] UseDiamondOperator: False positive with interdependent generic vars
- #6958: [java] BooleanGetMethodName should have the option to treat boolean wrapper type differently
- #6274: [java] UselessParentheses: False positive in ternary else expression
- #6651: [java] UnnecessaryImport: False positive when Javadoc {@link} references an array type
- #6709: [java] LambdaCanBeMethodReference: False positive with array creation containing constructor call in receiver
- #6737: [java] TooManyStaticImports: @SuppressWarnings("PMD.TooManyStaticImports") has stopped working
- #6846: [java] VariableDeclarationUsageDistance: False positive with variables grouped at the top of a block
- #6867: [java] UnnecessaryFullyQualifiedName: ContextedAssertionError: This should be unreachable: unknown constant ScopeInfo: MODULE_IMPORT
- #6943: [java] UnnecessaryCast: False positives related to generics

## java-design

- #6714: [java] Rename UseUtilityClass to InstantiableUtilityClass
- #6844: [java] AvoidThrowingNewInstanceOfSameException: message inconsistent with logic
- #6881: [java] CognitiveComplexity does not count switch expressions
- #6925: [java] ImmutableField: False positive on picocli annotated fields

## java-documentation

- #6270: [java] CommentSize: False positive with file header comments
- #6880: [java] CommentRequired: add packageMethodCommentRequirement property

## java-errorprone

- #2840: [java] CloseResource: False positive on mocks
- #3880: [java] ReturnEmptyCollectionRatherThanNull: False negative when a null value is assigned to a local that is later returned
- #4623: [java] CloseResource: False positive with resource being closed in method
- #6435: [java] UnconditionalIfStatement: False negative for negated boolean constant
- #6537: [java] StaticEJBFieldShouldBeFinal: False Negative when using @stateless etc.
- #6547: [java] NonSerializableClass: False negative for generic element/value types of collections and maps
- #6625: [java] New rule: UnusedReturnValue
- #6695: [java] ReturnEmptyCollectionRatherThanNull: False negative when null is returned through a local variable
- #6742: [java] CloseResource: False positive when a correctly-closed resource is declared without initializer
- #6744: [java] ReturnEmptyCollectionRatherThanNull: False negatives when a returned expression can evaluate to null
- #6826: [java] AssertEqualsArgumentOrder: False positive for double assertEquals
- #6900: [java] DoubleCheckedLocking: False negative when the outer null check is written as !(x != null)

## java-multithreading

- #6747: [java] NonThreadSafeSingleton: False negative with ternary conditional operator

## kotlin

- #6795: [kotlin] Add kotlin-type-mapper infrastructure
- #6891: [kotlin] AnnotationFqnAnnotator: @TypeName not set on UnescapedAnnotation nodes

## miscellaneous

- #1995: [core] PMD should display number of rules violated or errors found
- #2527: [doc] CPD: Invalid link to String Tiling Algorithm
- #4952: [doc] Improve doc around PMDConfiguration#prependAuxclasspath #setClassloader
- #4953: [core] Deprecate PMDConfiguration#setClassloader and #getClassloader
- #6837: [ci] chore: actions/create-github-app-token: Input 'app-id' has been deprecated with message: Use 'client-id' instead
- #6865: [core] Include the running PMD version in the "Unable to find referenced rule" error
- #6913: [core] RuleSetLoader#loadFromString ignores previously configured Resource/ClassLoader
- #6952: [core] Ruleset references are not resolved relative to the referencing ruleset

# Merged pull requests

- #6795: [kotlin] Add kotlin-type-mapper infrastructure - Peter Paul Bakker (@stokpop)
- #6811: [java] Fix #4623: CloseResource: False positive with resource being closed in method - Lukas Gräf (@lukasgraef)
- #6822: [java] Fix #5670, #5514: ExhaustiveSwitchHasDefault when default is necessary - Sören Glimm (@UncleOwen)
- #6823: [cli] Print PMD analysis summary - DragonFSKY (@DragonFSKY)
- #6825: [doc] Update "Merging pull requests" - Sören Glimm (@UncleOwen)
- #6827: [java] Fix #6826: AssertEqualsArgumentOrder false positive for double/float delta - Dan Halperin (@dhalperi)
- #6828: [java] Fix #6709: Fix false positive: LambdaCanBeMethodReference should not flag lambda… - Subhadeep (@dweep-js)
- #6829: [core] test: cover pmd analysis configuration - amir (@amirdeljouyi)
- #6838: [java] Follow-up on #6809: Add tests - Sören Glimm (@UncleOwen)
- #6840: chore: Fix #6837: Use client id for create-github-app-token - Andreas Dangel (@adangel)
- #6841: [core] refactor: AnalysisCache based on Path - Andreas Dangel (@adangel)
- #6842: [java] #4730: Add a test for FinalFieldCouldBeStatic that shows that #4730 was already fixed - Sören Glimm (@UncleOwen)
- #6843: [java] Fix #6714: Rename UseUtilityClass to InstantiableUtilityClass - Sören Glimm (@UncleOwen)
- #6845: [core] Fix #4953: Deprecate PMDConfiguration#getClassLoader - Andreas Dangel (@adangel)
- #6858: [java] Fix AvoidThrowingNewInstanceOfSameException false positive - Subhadeep (@dweep-js)
- #6859: [java] Fix #6010: Add replacement for ClasspathClassloader - Andreas Dangel (@adangel)
- #6860: [java] Fix #6846: VariableDeclarationUsageDistance: False positive with variables grouped at the top of a block - Gamja-rani (@onetuks)
- #6863: [java] chore: Add test for ReportStatsListener - Sören Glimm (@UncleOwen)
- #6866: [java] Fix #5041: IndexOutOfBoundsException for type annotations on inner class method signatures - Niklas Keller (@kelunik)
- #6868: [java] Fix #6867: Handle module imports in UnnecessaryFullyQualifiedName - DragonFSKY (@DragonFSKY)
- #6870: [core] Fix #6865: Improve missing rule reference error - DragonFSKY (@DragonFSKY)
- #6871: [java] Fix #6768: Resolve record component types before inference - DragonFSKY (@DragonFSKY)
- #6880: [java] CommentRequired: add packageMethodCommentRequirement property - legacynode (@legacynode)
- #6883: [java] CognitiveComplexity: count switch expressions - Kurath (@KurathSec)
- #6884: [java] Fix #6651: UnnecessaryImport: False positive for array-typed Javadoc {@link} parameters - hexonal (@hexonal)
- #6886: [doc] chore: release_notes - Use 4 space indentation - Andreas Dangel (@adangel)
- #6892: [kotlin] Fix #6891: Use KtModifiers container in AnnotationFqnAnnotator - Peter Paul Bakker (@stokpop)
- #6894: [java] Support Java 27 - Andreas Dangel (@adangel)
- #6902: [java] Fix #6737: Use next annotatable sibling for supressing top level - Andreas Dangel (@adangel)
- #6905: Fix Regression-Tester config: Replace Schedul-o-matic-9000 with declarative-lookup-rollup-summaries - Sören Glimm (@UncleOwen)
- #6906: [jsp] Fix #2033: NoClassAttribute for jsp:useBean - Columbus Labs (@ColumbusLabs)
- #6916: [java] Fix #6625: New rule: UnusedReturnValue - Sören Glimm (@UncleOwen)
- #6917: [java] Fix #6900: Modifies isNullCheck to accept negated expressions - Will-6543 (@Will-6543)
- #6918: [java] Fix #6742: CloseResource false positive for a wrapped resource assigned without an initializer - Eljees (@Eljees)
- #6919: [java] Prepare deprecation of asCtx in java-bestpractices (part of #4814) - Sören Glimm (@UncleOwen)
- #6920: [java] Fix #1287: GuardLogStatement false positive with a guard clause - Eljees (@Eljees)
- #6921: [core] Fix #6913: Use the configured class loader in RuleSetLoader#loadFromString - renechoi (@renechoi)
- #6933: [java] Fix #6932: Handle conflicting inner class visibility modifiers - Scrates1 (@Scrates1)
- #6934: [java] Fix #5441: Resolve interdependent inference variables simultaneously - Sebastian Lövdahl (@slovdahl)
- #6936: [java] Fix #6925: ImmutableField: false positive on picocli @Option/@Parameters fields - dev_Hakaze (@arimu1)
- #6938: [java] Fix #6747: NonThreadSafeSingleton flags ternary lazy init - fudian (@fudianchn)
- #6939: [java] Fix #6435: UnconditionalIfStatement flags arbitrarily negated boolean literals - fudian (@fudianchn)
- #6941: [java] Fix #6744: ReturnEmptyCollectionRatherThanNull: Analyze possible null return values - fudian (@fudianchn)
- #6942: [java] Fix #6547: NonSerializableClass checks collection/map generic element types - fudian (@fudianchn)
- #6944: chore: Remove unnecessary casts - Sören Glimm (@UncleOwen)
- #6945: [java] Fix #6537: StaticEJBFieldShouldBeFinal detects @Stateless/@Stateful/@Singleton/@MessageDriven EJB classes - fudian (@fudianchn)
- #6947: [java] Fix #6393: UnusedPrivateMethod FP on overloaded methods when overload resolution fails - fudian (@fudianchn)
- #6948: [java] Fix #6270: CommentSize skips file header comments - fudian (@fudianchn)
- #6949: [java] Fix #6611: UnnecessaryVarargsArrayCreation ignores overload ambiguity - fudian (@fudianchn)
- #6950: [java] Fix #6274: UselessParentheses treats ternary else-branch parentheses as clarifying - fudian (@fudianchn)
- #6953: [core] Fix #6952: Resolve ruleset references relative to the referencing ruleset - Lukas Gräf (@lukasgraef)
- #6954: [java] Fix #2840: CloseResource: allow Mockito mocks by default - Eljees (@Eljees)
- #6955: [apex] Fix ApexUnitTestShouldNotUseSeeAllDataTrue violation location - Taran (@tarann26)
- #6957: [java] Fix dataflow state for conditional initializers - subotac (@subotac)
- #6959: [chore] Add oout to allowed list of typos - Sören Glimm (@UncleOwen)
- #6962: [java] Fix #6958: Add configurable Boolean handling to BooleanGetMethodName - Harshit Sinha (@harshitsinha11)
- #6964: [java] New rule: ProtectedMemberInFinalClass - Zbynek Konecny (@zbynek)
- #6966: [java] AssertEqualsArgumentOrder: False negative for assertEquals with delta - Zbynek Konecny (@zbynek)
- #6968: [java] Fix #6967: Make violation message of UnusedReturnValue consistent by removing type parameters - Sören Glimm (@UncleOwen)
- #6972: [java] Fix #6965: AbstractClassWithoutAnyMethod false positive on derived abstract class - Abdullah (@AzazelSensei)
- #6973: chore: Enforce bytecode version JDK 8 - Andreas Dangel (@adangel)
- #6974: [java] Introduce JPackageSymbol - Andreas Dangel (@adangel)
- #6976: chore: Fix AssertEqualsArgumentOrder - Andreas Dangel (@adangel)
- #6978: [java] UnusedReturnValue: Fix description - Sören Glimm (@UncleOwen)
- #6981: [doc] Fix #2527: restore CPD tiling article link - Abdullah (@AzazelSensei)
- #6982: [java] Fix #6943: UnnecessaryCast: false positives related to generics - dev_Hakaze (@arimu1)
- #6989: [doc] Update release notes for 7.27.0 - Andreas Dangel (@adangel)
- #6990: [java] Move ProtectedMemberInFinalClass to design category - Zbynek Konecny (@zbynek)
