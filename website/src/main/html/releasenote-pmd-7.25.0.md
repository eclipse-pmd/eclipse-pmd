# New Rules ️

- The new Java rule `JUnitJupiterTestNoPrivateModifier` find JUnit test classes and methods that are private. Test classes, test methods, and lifecycle methods are not required to be public, but they must not be private. Otherwise, they won’t be found by the test framework.
- The new Java rule `UnnecessaryBlock` reports blocks that are unnecessary as they don’t introduce a new scope. This rule helps simplify code structure by identifying and flagging redundant blocks that can make code harder to read and may be misleading.
- The new Java rule `VariableDeclarationUsageDistance` flags local variables that are declared far from their usage, which can make code harder to read. The rule has a property maxDistance that allows to configure the maximum allowed distance between declaration and usage.
- The new Java rule `AssertStatementInTest` detects usages of assert statement in tests. These should be replaced by framework assertion methods such as assertEquals. Such methods provide better error messages and make test behave correctly when running without -ea.

# Changed Rules ️

- The rule `OnlyOneReturn` has a new property allowGuardIfs. When this property is true, then guard ifs at the beginning of a method are allowed their return statements don’t count.
- The rules `UseUtilityClass` and `ClassNamingConventions` now use the same definition of what a utility class is. The most significant change is, that classes with main() methods are no longer considered utility classes by UseUtilityClass.
- We are continuously working to improve the precision of violation reporting for various rules. The goal is to ensure that rules report issues on the correct line and highlight only the relevant lines. For example, instead of flagging an entire class declaration (including its body), we now generally report only the class name. For more details, see [java] Single Line Warnings #730 and [java] Review reported locations of rules #3769. While this effort is still ongoing, the following Java rules have been updated in this release:
- `AbstractClassWithoutAbstractMethod`
- `AbstractClassWithoutAnyMethod`
- `AtLeastOneConstructor`
- `AvoidDollarSigns`
- `AvoidCatchingGenericException`
- `AvoidSynchronizedStatement` (now reports only on synchronized keyword and not the whole synchronized block)
- `ClassNamingConventions`
- `ClassWithOnlyPrivateConstructorsShouldBeFinal`
- `CommentDefaultAccessModifier`
- `CommentRequired`
- `CouplingBetweenObjects` (now reports only on class identifier and not whole compilation unit anymore)
- `CyclomaticComplexity`
- `DataClass`
- `ExcessiveImports` (now reports only on imports and not the whole compilation unit anymore)
- `ExcessiveParameterList`
- `ExcessivePublicCount`
- `ExhaustiveSwitchHasDefault` (now reports only on switch keyword and not the whole switch block)
- `GodClass`
- `ImplicitFunctionalInterface`
- `JUnit5TestShouldBePackagePrivate`
- `LocalHomeNamingConvention`
- `LocalInterfaceSessionNamingConvention`
- `MissingSerialVersionUID`
- `MissingStaticMethodInNonInstantiatableClass`
- `NcssCount`
- `NonExhaustiveSwitch` (now reports only on switch keyword and not the whole switch block)
- `NoPackage`
- `PublicMemberInNonPublicType`
- `ShortClassName`
- `SingleMethodSingleton`
- `SwitchDensity` (now reports only on switch keyword and not the whole switch block)
- `TestClassWithoutTestCases`
- `TooFewBranchesForSwitch` (now reports only on switch keyword and not the whole switch block)
- `TooManyFields` (now reports only on class identifier and not the whole class body anymore)
- `TooManyMethods` (now reports only on class identifier and not the whole class body anymore)
- `TooManyStaticImports` (now reports only on the first static import and not the whole compilation unit anymore)
- `UnnecessaryModifier`
- `UseUtilityClass`

# Renamed rules and properties ️

One rule and one property have been renamed to reflect the fact that they work for both JUnit 5 and 6:

- The rule `JUnitJupiterTestShouldBePackagePrivate` (Java Best Practices) was renamed from `JUnit5TestShouldBePackagePrivate`.
- The property `junitJupiterTestPattern` of rule `MethodNamingConventions` (Java Code Style) was renamed from `junit5TestPattern`.

The old names still work but are deprecated.

# Fixed Issues ️

## core

- #4972: [core] Update ANTLR to 4.13.2
- #6308: [core] CPD Markdown format: Add syntax highlighting

## doc

- #6708: [doc] Update minimal Java version for building PMD in documentation

## java

- #1102: [java] Improve consistency of utility class detection across rules
- #5721: [java] StackOverflowError in 7.17.0 with nested wildcard generics
- #5746: [java] Separate test sources and resources
- #6688: [java] LocalVariableCouldBeFinalRule API changed
- #6704: [java] Rename rules and properties with JUnit5 in the name

## java-bestpractices

- #3212: [java] Enhance UseStandardCharsets to flag some constructors of IO-related classes
- #3777: [java] New rule: AssertStatementInTest
- #5477: [java] JUnit5TestShouldBePackagePrivate is not applied when @Test method is only present in parent class
- #6606: [java] UnusedPrivateField: False positive on JUnit Jupiter @FieldSource
- #6681: [java] UnitTestShouldIncludeAssert: False positive with JUnitSoftAssertions Rule (JUnit 4)
- #6710: [java] UseStandardCharsets: False negative when using lowercase standard charset names
- #6719: [java] UseStandardCharsets: False negative with Java 22+ and UTF-32 charsets

## java-codestyle

- #2801: [java] OnlyOneReturn should have a property to allow early exits (guard clauses)
- #4350: [java] ClassNamingConventions: testClassPattern not applied to class that inherits all its @Test methods
- #6427: [java] UnnecessaryCast: False positive for long cast before bit-shift operations on int/byte
- #6602: [java] LocalVariableCouldBeFinal: False negative when multiple variables are declared at once
- #6622: [java] New rule: UnnecessaryBlock
- #6640: [java] New rule: VariableDeclarationUsageDistance

## java-design

- #559: [java] UseUtilityClass: False negative for constant only classes

## java-errorprone

- #3288: [java] New Rule: JUnit5TestNoPrivateModifier
- #4288: [java] Document that CallSuperFirst/CallSuperLast are Android specific
- #6163: [java] ConstructorCallsOverridableMethod: False positive when method is from enclosing class
- #6517: [java] UselessPureMethodCall: False negative for methods on IntStream/LongStream/DoubleStream
- #6652: [java] AvoidInstanceofChecksInCatchClause: false negative when pattern-matching instanceof
- #6712: [java] UnnecessaryBooleanAssertion: Use InvocationMatcher to find assertions

## java-multithreading

- #6520: [java] DoNotUseThreads: False positive on legitimate java.lang.Thread.onSpinWait() call
- #6636: [java] OverridingThreadRun: Fix false negatives with other methods and anonymous classes

## kotlin

- #6608: [kotlin] Lexer or parse errors are reported to stderr only without file context
- #6648: [kotlin] Multi-dollar interpolation parse error in annotations
- #6659: [kotlin] Parser hangs on complex files due to unbounded ATN prediction loop
- #6669: [kotlin] Add AST improvements, KotlinAstUtil

# Merged pull requests ️

- #6084: [java] Shrink reported locations for some rules - Sören Glimm (@UncleOwen)
- #6522: [java] Fix #6520: DoNotUseThreads: fix false positive on Thread.onSpinWait() - leemeii (@leemeii)
- #6524: [java] Fix #6517: UselessPureMethodCall: fix false negative for primitive streams - leemeii (@leemeii)
- #6553: [java] Fix StackOverflowError in TypeOps projection of cyclic captured type vars - Sebastian Lövdahl (@slovdahl)
- #6557: [java] New rule: AssertStatementInTest - Zbynek Konecny (@zbynek)
- #6561: [java] Fix #6163: ConstructorCallsOverridableMethod: False positive with call to enclosing class - Lukas Gräf (@lukasgraef)
- #6573: [java] Fix #6427: Add bitwise and/or/xor to BINARY_PROMOTED_OPS - Sören Glimm (@UncleOwen)
- #6587: [java] Fix #2801: Add a property to OnlyOneReturnRule to allow guard ifs - Sören Glimm (@UncleOwen)
- #6597: [java] Fix #3212: Enhance UseStandardCharsets - Sören Glimm (@UncleOwen)
- #6601: [java] Fix #4288: Document that CallSuperFirst and CallSuperLast are android only - Sören Glimm (@UncleOwen)
- #6603: [java] Fix #6602: Fix false negative in LocalVariableCouldBeFinalRule - Sören Glimm (@UncleOwen)
- #6604: [java] Fix #3288: New rule JUnit5TestNoPrivateModifierRule - Sören Glimm (@UncleOwen)
- #6605: [java] Fix #6308: Add syntax highlighting to MarkdownRenderer - Sören Glimm (@UncleOwen)
- #6619: [java] Fix #5746: Separate test sources and resources - Sören Glimm (@UncleOwen)
- #6623: [java] Cleanup: Remove TODO from ModifierOwner.getVisibility() - Sören Glimm (@UncleOwen)
- #6636: [java] OverridingThreadRun: Fix false negatives with other methods and anonymous classes - Zbynek Konecny (@zbynek)
- #6638: [java] Fix #559: Improve UseUtilityClassRule to trigger also on static members - Sören Glimm (@UncleOwen)
- #6639: [java] New rule: UnnecessaryBlock - Sören Glimm (@UncleOwen)
- #6640: [java] New rule: VariableDeclarationUsageDistance - Zbynek Konecny (@zbynek)
- #6646: [test] Split up AbstractRuleSetFactoryTest.testAllPMDBuiltInRulesMeetConventions() - Sören Glimm (@UncleOwen)
- #6650: [kotlin] Fix #6608: Improve kotlin parser error handling - Peter Paul Bakker (@stokpop)
- #6653: [kotlin] Fix #6648: Multi-dollar interpolation for regular strings - Peter Paul Bakker (@stokpop)
- #6654: [swift] Fix invalid swift token OSXApplicationExtension - Andreas Dangel (@adangel)
- #6657: [java] AvoidSynchronizedStatement: Improve rule doc - Andreas Dangel (@adangel)
- #6658: [doc] Fix capitalization of ANTLR in release notes - Zbynek Konecny (@zbynek)
- #6660: [kotlin] Fix #6659: Prevent parser hang via InterruptibleParserATNSimulator and parse timeout - Peter Paul Bakker (@stokpop)
- #6661: [java] Fix #6652: Support new-style instanceof (with pattern matching) in AvoidInstanceofChecksInCatchClause - Sören Glimm (@UncleOwen)
- #6670: [kotlin] Add AST improvements, KotlinAstUtil - Peter Paul Bakker (@stokpop)
- #6671: [java] Part of #4841: Deprecate unnecessary public methods in FieldDeclarationsShouldBeAtStartOfClassRule/CyclomaticComplexityRule/SwitchDensityRule - Sören Glimm (@UncleOwen)
- #6679: [chore] Fix typos in comments and documentation - Zbynek Konecny (@zbynek)
- #6680: [java] Fix #5477: JUnit5TestShouldBePackagePrivate is not applied when @Test method is only present in parent class - Sören Glimm (@UncleOwen)
- #6683: Fix duplicated “the” in Java and Visualforce comments - vip892766gma (@vip892766gma)
- #6686: [java] False positive for UnusedPrivateField referenced by @FieldSource - Zbynek Konecny (@zbynek)
- #6687: LocalVariableCouldBeFinalRule: Revert API change - Sören Glimm (@UncleOwen)
- #6689: [java] Fix #4350: Fix ClassNamingConventions by teaching TestFrameworkUtil about type resolution - Sören Glimm (@UncleOwen)
- #6691: [java] Fix #1102: improve consistency of utility class detection across rules - Sören Glimm (@UncleOwen)
- #6705: [java] Fix #6681: UnitTestShouldIncludeAssert: False positive with JUnitSoftAssertions Rule (JUnit 4) - Lukas Gräf (@lukasgraef)
- #6707: [java] Fix #6704: rename rules and properties with JUnit5 in the name - Sören Glimm (@UncleOwen)
- #6708: [doc] Update minimal Java version for building PMD in documentation - Zbynek Konecny (@zbynek)
- #6712: [java] UnnecessaryBooleanAssertion: Use InvocationMatcher to find assertions - Zbynek Konecny (@zbynek)
- #6716: [java] Fix #6710: Case insensitive comparison in UseStandardCharsets - Sören Glimm (@UncleOwen)
- #6726: [java] Fix #6719: UseStandardCharsets UTF-32 on Java >= 22 - Sören Glimm (@UncleOwen)

