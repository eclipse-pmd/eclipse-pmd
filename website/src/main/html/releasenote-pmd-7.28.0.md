# New and Changed Rules

## New Rules

- The new Java rule `OnDemandImport` reports on-demand imports, also known as wildcard imports. By default, static imports from JUnit and TestNG are allowed. The allowed static and type import packages can be configured with allowStaticImportsFrom and allowTypeImportsFrom.
- The new Java rule `LongLiteralEndingWithLowercaseL` finds long literals ending with a lowercase l. That helps to avoid confusion between numbers ending with 1 and l. Uppercase L should be used to define long literals.
- The new Java rule `TypeNameMismatch` finds types that are not defined in a .java file with the same name. Enforcing a match between source file name and type name makes it easier to find source code for given type.
- The new Java rule `CStyleArrayDeclaration` finds C-style declarations of arrays (e.g. int numbers[]). That helps you use Java-style declarations (e.g. int[] numbers) consistently throughout the codebase.
- The new Java rule `InternalApiUsage` reports usages of internal or test-only APIs (e.g. annotated with `@VisibleForTesting`, `@TestOnly`, `@API(status=INTERNAL)` or `@ApiStatus.Internal`) from code that shouldn’t depend on them.
- The new Apex rule `ApexUnitTestClassShouldHaveRunRelevantTestsAnnotation` finds unit tests that do not use the new `@IsTest(critical=true)` or `@IsTest(testFor='...')` annotation parameters for tests. These parameters help to identify which tests should be executed during a RunRelevantTests deployment.
## Note: These annotation parameters are Beta and require Salesforce API 66.0+.

## Changed Rules

- The property `checkNonStaticMethods` of the rule `NonThreadSafeSingleton` is now deprecated and no longer has any effect. Its implementation did the opposite of what the documentation described. The rule now always reports both static and non-static methods; previously it reported only static methods by default.
  This may result in additional violations being reported.
  If you want to suppress violations for non-static methods, you can use suppression via XPath, e.g.
  `<property name="violationSuppressXPath" value=".[ancestor-or-self::MethodDeclaration[1][@Static = false()]]" />`
- The property `statementOrderMatters` of the rule `VariableCanBeInlined` is now deprecated. Setting it to false relaxes the rule under the unsafe assumption that intervening statements have no side effects, which can lead to false positives. The property will be removed in PMD 8.0.0.

# Fixed Issues

## apex-bestpractices

- #6988: [apex] New rule: Detect usage of @IsTest(critical=true) / @IsTest(testFor=’…’) annotations (RunRelevantTests, Beta, API 66.0+)

## core

- #7013: [core] PMDConfiguration - “Can’t mix setClasspath with getAuxClasspath!”

## cli

- #7090: [cli] Add the missing exit code 5 to the CLI help

## html

- #6135: [html] HtmlCpdLexer giving IndexOutOfBoundsException when script contains unescaped closing tag

## java

- #6926: [java] IllegalArgumentException (Mismatched list sizes) with inconsistent unresolved generic arity
- #7056: [java] Provide ability to disable auxClasspath warning added in 7.27.0
- #7081: [java] NoSuchFileException when auxClasspath is given as a classpath file (file: URL) (since 7.27.0)
- #7101: [java] ZipException when auxClasspath contains a non-jar file (since 7.27.0)

## java-bestpractices

- #5940: [java] UnusedAssignment: False positive when assignment is in conditional statement
- #6901: [java] MethodReturnsInternalArray: Various false negatives with local aliases and conditional expressions
- #7033: [java] New rule: TypeNameMismatch
- #7047: [java] New rule: OnDemandImport

## java-codestyle

- #3124: [java] UnnecessaryLocalBeforeReturn/VariableCanBeInlined: deprecate property statementOrderMatters
- #5732: [java] UnnecessaryCast false positive with package private methods
- #7026: [java] New rule: CStyleArrayDeclaration

## java-design

- #6513: [java] SimplifyConditional: False negative when null check and instanceof are separated by other && conditions
- #6694: [java] SimplifyBooleanReturns triggers inconsistently depending on redundant parentheses in return expression
- #6889: [java] New rule: InternalApiUsage

## java-documentation

- #6450: [java] DanglingJavadoc: False positive on /// comments for Java < 23

## java-errorprone

- #1050: [java] NullAssignment: False positive inside if statement for first assignment
- #6693: [java] CloneMethodMustImplementCloneable: False positive with throw-via-local
- #7009: [java] ReplaceJavaUtilDate: False negative when using pattern matching
- #7027: [java] New rule: LongLiteralEndingWithLowercaseL
- #7068: [java] UnusedReturnValue: False positive for calls made on Mockito.verify(mock)

## java-multithreading

- #6297: [java] AvoidUsingVolatile: Update documentation
- #6780: [java] NonThreadSafeSingleton: False negative with property checkNonStaticMethods

## java-security

- #7007: [java] HardCodedCryptoKey: False negative when a hard-coded key is constructed via new String(char[])
- #7008: [java] HardCodedCryptoKey: False positive when the key comes from System.getProperty()

## kotlin

- #6893: [kotlin] Add XPath functions and type attributes

## miscellaneous

- #6961: [doc] When a rule’s description has a link to another rule in the exact wrong position, the doc generation crashes

# Merged pull requests

- #6864: [java] Fix #6780: NonThreadSafeSingleton checkNonStaticMethods contradicting impl - Subhadeep (@dweep-js)
- #6885: [java] Fix #6693: CloneMethodMustImplementCloneable false positive for local-var throw - hexonal (@hexonal)
- #6893: [kotlin] Add XPath functions and type attributes - Peter Paul Bakker (@stokpop)
- #6922: [java] Fix #6694: Trigger SimplifyBooleanReturns when expressions require parentheses - Will-6543 (@Will-6543)
- #6935: [java] Fix #6926: skip file on unresolved generic arity mismatch - Burak Kalaycı (@kalayciburak)
- #6940: [java] Fix #6901: MethodReturnsInternalArray: Track internal array escapes through expressions - fudian (@fudianchn)
- #6946: [java] Fix #6513: SimplifyConditional detects null check separated from instanceof by a && chain - fudian (@fudianchn)
- #6951: [java] Fix #6450: DanglingJavadoc should ignore markdown javadoc (///) before Java 23 - fudian (@fudianchn)
- #6963: [doc] Fix #6961: dont choke on cut off links - Sören Glimm (@UncleOwen)
- #6975: [java] Prepare deprecation of asCtx in java-codestyle (part of #4814) - Sören Glimm (@UncleOwen)
- #6991: [apex] Fix #6988: Add ApexUnitTestClassShouldHaveRunRelevantTestsAnnotation rule - Thomas Prouvot (@tprouvot)
- #6992: [java] Fix #6297: Update AvoidUsingVolatile description - Sören Glimm (@UncleOwen)
- #6993: [java] Fix #5732: UnnecessaryCast false positive for package-private members - Tanvir Alam (@tanvir-ux)
- #6994: [java] New rule: InternalApiUsage - Zbynek Konecny (@zbynek)
- #7005: [java] Fix rule reference from ProtectedMemberInFinalField to ProtectedMemberInFinalClass - Piotrek Żygieło (@pzygielo)
- #7010: [java] Fix #7009: ReplaceJavaUtilDate/Calendar miss pattern variables and record components - renechoi (@renechoi)
- #7011: [doc] chore: Improve RuleTagChecker to find invalid in-ruleset references - Andreas Dangel (@adangel)
- #7012: [java] Fix #7008: HardCodedCryptoKey: False positive when a default value of System.getProperty() is treated as a hard-coded key - MakerYuichi (@MakerYuichi)
- #7016: [java] Fix #5940: UnusedAssignment FP when constant operand short-circuits the condition - fudian (@fudianchn)
- #7019: [core] Fix #7013: Only access PMDConfiguration.auxClasspath if it is actually set. - Sören Glimm (@UncleOwen)
- #7025: [ci] gh-actions: Update environment variables for setup-java 6.0.0 - Sören Glimm (@UncleOwen)
- #7028: [html] Fix #6135: HtmlCpdLexer giving IndexOutOfBoundsException when script contains unescaped closing tag - Lukas Gräf (@lukasgraef)
- #7029: [java] Fix #7007: HardCodedCryptoKey detection for String(char[]) - suhanrain (@suhanrain)
- #7030: [java] Fix #7027: New rule: LongLiteralEndingWithLowercaseL - Copilot (@Copilot)
- #7031: [java] New rule: CStyleArrayDeclaration - Copilot (@Copilot)
- #7032: [doc] Strip rule description when generating docs - Zbynek Konecny (@zbynek)
- #7034: [java] New rule: TypeNameMismatch - Zbynek Konecny (@zbynek)
- #7035: [java] Fix #1050: NullAssignment false positive inside if statement for first assignment - Lukas Gräf (@lukasgraef)
- #7052: [java] New rule: OnDemandImport - suhanrain (@suhanrain)
- #7061: [core] Fix off-by-one error in AbstractJjtreeNode.fitTokensToChildren() - Sören Glimm (@UncleOwen)
- #7064: chore: Remove protobuf-java from - [Sören Glimm](https://github.com/UncleOwen) (@UncleOwen)
- #7065: [java] Fix #3124: Deprecate statementOrderMatters property of VariableCanBeInlined - fudian (@fudianchn)
- #7069: [java] Fix #7068: UnusedReturnValue false positive after Mockito.verify - Burak Kalaycı (@kalayciburak)
- #7084: [java] Followup for #7008 (HardCodedCryptoKey): Fix FN when hard-coded fallback value is present - Lukas Gräf (@lukasgraef)
- #7085: [java] Fix #7081: Exception when auxClasspath is a file URL - Andreas Dangel (@adangel)
- #7086: [java] Fix #7056: Add JavaLanguageProperty to disable auxClasspath warnings - Andreas Dangel (@adangel)
- #7089: [doc] TOC highlighting improvements - Zbynek Konecny (@zbynek)
- #7090: [cli] Add the missing exit code 5 to the CLI help - Iain (@NotAFlightRisk)
- #7102: [java] Fix #7101: Skip non-archive files on the auxclasspath - Burak Kalaycı (@kalayciburak)
- #7106: [doc] Update release notes for 7.28.0 - Andreas Dangel (@adangel)
- #7111: [doc] Add gradle environment vars example - Andreas Dangel (@adangel)
