# New and noteworthy ️

## Swift Changes ️

The Swift parser now forwards syntax errors as usual processing errors. Before it just logged any errors and tried to move on, resulting in an incomplete AST with error nodes. As part of this change, the grammar has been slightly improved around macro declarations, generic parameters and parameter packs.
This means that PMD might fail now on Swift files with processing errors, when it previously ran without obvious problems. The Swift module in PMD now behaves like other modules in regard to error handling.

# New and Changed Rules ️

## New Rules ️

- The new Java rule `WrongTestAnnotation` detects when test annotations from the wrong testing framework (JUnit 4, JUnit Jupiter, or TestNG) are used in your code, preventing tests from being silently skipped due to framework mismatches. This helps avoid the silent failure where tests compile but don’t execute because the test runner doesn’t recognize the annotation.
- The new Java rule `AssertEqualsArgumentOrder` detects assertions where the expected and actual arguments were swapped. This helps find assertions that are producing a confusing error message when they fail.
- The new Kotlin rule `LocalVariableShadowsParameter` detects local variable declarations that use the same name as a parameter of the enclosing function. This shadows the parameter and may lead to confusion about which value is used.
- The new Apex rule `InvocableClassNoArgConstructor` detects classes that use @InvocableVariable properties, but that don’t provide a no-arg constructor. Without such a constructor, runtime exception occur when Salesforce Flow tries to instantiate such classes.

## Deprecated Rules ️

The rule `UseObjectForClearerAPI` was deprecated. Use `ExcessiveParameterList` instead. The old rule name still works.

# Fixed Issues ️

## apex

- #6806: [apex] ANTLR runtime mismatch 4.9.1 used for code generation does not match the current runtime version 4.13.2

## apex-errorprone

- #6793: [apex] New Rule: Invocable Classes require a no argument constructor

## apex-security

- #2955: [apex] ApexSOQLInjection: False positive when passing local var with concatenating strings
- #3877: [apex] ApexCRUDViolation: False positive with Lists of Objects with getSObjectType().getDescribe()

## core

- #6764: [core] ANTLR: Report syntax errors as processing errors

## cpp

- #6641: [cpp]: IndexOutOfBoundsException in CPD when a duplication is at end of file with UTF8-BOM

## java

- #6812: [java] Rename ASTMethodDeclaration#isOverridden() to isOverride()

## java-bestpractices

- #6627: [java] UnusedPrivateMethod: could not handle javax.annotation
- #6692: [java] ForLoopCanBeForeach: inconsistent detection between i += 1 and i = i + 1 update forms
- #6736: [java] JUnitJupiterTestShouldBePackagePrivate: False negative when the only tests are in a @Nested class
- #6782: [java] UseStandardCharsets: ArrayIndexOutOfBoundsException in line 81

## java-codestyle

- #6239: [java] UseDiamondOperator: False positive with Guice TypeLiteral
- #6775: [java] UselessParentheses: False negative when on the right-hand side of an assignment statement

## java-design

- #3741: [java] Deprecate UseObjectForClearerAPI
- #6459: [java] PublicMemberInNonPublicType: False positive for main(…) methods
- #6460: [java] PublicMemberInNonPublicType: False negative for overridden methods
- #6814: [java] AvoidDeepNestedIfStmts: count ifs properly in else branch

## java-errorprone

- #2846: [java] New Rule: WrongTestAnnotation
- #5011: [java] TestClassWithoutTestCases: False positive for test classes extending a class with tests (in nested classes)
- #6743: [java] CloseResource: False positive for closeable initialized with (T) null
- #6781: [java] UselessPureMethodCall: False positive for Stream.forEach

## java-performance

- #6740: [java] OptimizableToArrayCall: False positive when new T[0x0] is used instead of new T[0]

## kotlin

- #6677: [kotlin] Add auxClasspath language property

## kotlin-bestpractices

- #6732: [kotlin] New Rule: LocalVariableShadowsParameter

## swift

- #6801: [swift] Report syntax errors as processing errors

# Merged pull requests ️

- #6678: [kotlin] Fix #6677: Add auxClasspath language property - Peter Paul Bakker (@stokpop)
- #6703: [cpp] Fix #6641: CPD: IndexOutOfBoundsException when a duplication is at end of file with UTF8-BOM - Lukas Gräf (@lukasgraef)
- #6713: [java] New rule: AssertEqualsArgumentOrder - Zbynek Konecny (@zbynek)
- #6727: [java] Fix #6692: ForLoopCanBeForeach detect i = i + 1 update form - hyeonjune (@qwerty7878)
- #6728: chore: Fix pmd test setup - Andreas Dangel (@adangel)
- #6730: [core] RuleSetWriter: fix indent-number attribute - Andreas Dangel (@adangel)
- #6733: [kotlin] Fix #6732: Add LocalVariableShadowsParameter rule - Peter Paul Bakker (@stokpop)
- #6735: [java] Fix #2846: New Rule: WrongTestAnnotation - Sören Glimm (@UncleOwen)
- #6738: [java] Fix #6736: Add JTypeMirror.streamClasses() - Sören Glimm (@UncleOwen)
- #6741: [cli] Designer: Fix quotes in PMD_OPENJFX_MODULE_PATH setting - Philip Graf (@acanda)
- #6745: [java] Fix #6239: UseDiamondOperator: Implement heuristic for Super Type Token Pattern - Sören Glimm (@UncleOwen)
- #6748: [java] Fix #6743: CloseResource: False positive for closeable initialized with (T) null - Lukas Gräf (@lukasgraef)
- #6761: chore: Fix PMD issues from new dogfood rules - Andreas Dangel (@adangel)
- #6763: [doc] Split old release notes page - Andreas Dangel (@adangel)
- #6764: [core] ANTLR: Report syntax errors as processing errors - Andreas Dangel (@adangel)
- #6765: [apex] Fix #2955: ApexSOQLInjection: False positive when concatenating strings - Lukas Gräf (@lukasgraef)
- #6767: [chore] #6641: Remove comment from test-data to reproduce original issue - Lukas Gräf (@lukasgraef)
- #6769: [apex] Fix #3877: ApexCRUDViolation false positive on Lists of Objects with getSObjectType().getDescribe() - Lukman Hakim (@lukman48)
- #6776: [java] Fix #6775: UselessParentheses: not reported on the right-hand side of an as… - Subhadeep (@dweep-js)
- #6777: [chore] #6641: Further improve the test - Andreas Dangel (@adangel)
- #6778: [java] Fix examples in javadocs for InvocationMatcher - Sören Glimm (@UncleOwen)
- #6779: [java] Fix #3741: Deprecate UseObjectForClearerApi - Sören Glimm (@UncleOwen)
- #6783: [java] Fix #6782: Add missing check for varargs - Sören Glimm (@UncleOwen)
- #6791: chore: Keep .ci/tools/typos.sh version in sync with github actions - Andreas Dangel (@adangel)
- #6792: [ci] chore: Improve publish-release job dependencies - Andreas Dangel (@adangel)
- #6794: [apex] New rule: InvocableClassNoArgConstructor - Daniel Ballinger (@FishOfPrey)
- #6796: [java] Fix #6460: Fix false negative for overridden methods in PublicMemberInNonPublicType - Sören Glimm (@UncleOwen)
- #6797: [java] Fix #6781: False positive in UselessPureMethodCall with unresolved types - Zbynek Konecny (@zbynek)
- #6801: [swift] Report syntax errors as processing errors - Andreas Dangel (@adangel)
- #6802: [java] #6461: PublicMemberInNonPublicType: Verify that we can suppress on the method - Sören Glimm (@UncleOwen)
- #6803: [chore] git-commit-id-maven-plugin: Use native git - Sören Glimm (@UncleOwen)
- #6804: [java] Fix #6459: special case main in PublicMemberInNonPublicType - Sören Glimm (@UncleOwen)
- #6805: [java] #4960: Add regression test for UnusedAssignment - Sören Glimm (@UncleOwen)
- #6807: [apex] Fix #6806: Upgrade vf-parser to 2.0.0-beta.1 - Andreas Dangel (@adangel)
- #6809: [java] Fix #5011: Fix FP in TestClassWithoutTestCases when the only tests are in a superclass - Sören Glimm (@UncleOwen)
- #6812: [java] Rename ASTMethodDeclaration#isOverridden() to isOverride() - Sören Glimm (@UncleOwen)
- #6813: [java] Fix #6740: Fix FP in OptimizableToArrayCall - Sören Glimm (@UncleOwen)
- #6814: [java] AvoidDeepNestedIfStmts: count ifs properly in else branch - Zbynek Konecny (@zbynek)
- #6821: [java] Fix #6627: UnusedPrivateMethod: add javax to ignored annotations - Sören Glimm (@UncleOwen)
