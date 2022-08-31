# New rules

- The new Apex rule EagerlyLoadedDescribeSObjectResult finds DescribeSObjectResults which could have been loaded eagerly via SObjectType.getDescribe().

# Modified rules

- The Apex rule ApexUnitTestClassShouldHaveAsserts has a new property additionalAssertMethodPattern. When specified the pattern is evaluated against each invoked method name to determine whether it represents a test assertion in addition to the standard names.
- The Apex rule ApexDoc has a new property reportMissingDescription. If set to false (default is true if unspecified) doesn’t report an issue if the @description tag is missing. This is consistent with the ApexDoc dialect supported by derivatives such as SfApexDoc and also with analogous documentation tools for other languages, e.g., JavaDoc, ESDoc/JSDoc, etc.
- The Apex rule ApexCRUDViolation has a couple of new properties: These allow specification of regular-expression-based patterns for additional methods that should be considered valid for pre-CRUD authorization beyond those offered by the system Apex checks and ESAPI, e.g., sirono-common’s AuthorizationUtil class. Two new properties have been added per-CRUD operation, one to specify the naming pattern for a method that authorizes that operation and another to specify the argument passed to that method that contains the SObjectType instance of the type being authorized.
- The Apex rule EmptyStatementBlock has two new properties: Setting reportEmptyPrivateNoArgConstructor to false ignores empty private no-arg constructors that are commonly used in singleton pattern implementations and utility classes in support of prescribed best practices. Setting reportEmptyVirtualMethod to false ignores empty virtual methods that are commonly used in abstract base classes as default no-op implementations when derived classes typically only override a subset of virtual methods. By default, both properties are true to not change the default behaviour of this rule.
- The Apex rule EmptyCatchBlock has two new properties modeled after the analgous Java rule: The allowCommentedBlocks property, when set to true (defaults to false), ignores empty blocks containing comments. The allowExceptionNameRegex property is a regular expression for exception variable names for which empty catch blocks should be ignored by this rule. For example, using the default property value of ^(ignored|expected)$, the following empty catch blocks will not be reported.
- The Apex rule OneDeclarationPerLine has a new property reportInForLoopInitializer: If set to false (default is true if unspecified) doesn’t report an issue for multiple declarations in a for loop’s initializer section. This is support the common idiom of one declaration for the loop variable and another for the loop bounds condition.
- The Java rule ClassNamingConventions uses a different default value of the property utilityClassPattern: This rule was detecting utility classes by default since PMD 6.3.0 and enforcing the naming convention that utility classes has to be suffixed with Util or Helper or Constants. However this turned out to be not so useful as a default configuration, as there is no standard naming convention for utility classes. With PMD 6.40.0, the default value of this property has been changed to [A-Z][a-zA-Z0-9]* (Pascal case), effectively disabling the special handling of utility classes. This is the same default pattern used for concrete classes. This means, that the feature to enforce a naming convention for utility classes is now a opt-in feature and can be enabled on demand.

# Fixed Issues

## apex
- #1089: [apex] ApexUnitTestClassShouldHaveAsserts: Test asserts in other methods not detected
- #1090: [apex] ApexCRUDViolation: checks not detected if done in another method
- #3532: [apex] Promote usage of consistent getDescribe() info
- #3566: [apex] ApexDoc rule should not require “@description”
- #3568: [apex] EmptyStatementBlock: should provide options to ignore empty private constructors and empty virtual methods
- #3569: [apex] EmptyCatchBlock: should provide an option to ignore empty catch blocks in test methods
- #3570: [apex] OneDeclarationPerLine: should provide an option to ignore multiple declarations in a for loop initializer
- #3576: [apex] ApexCRUDViolation should provide an option to specify additional patterns for methods that encapsulate authorization checks
- #3579: [apex] ApexCRUDViolation: false negative with undelete
## java-bestpractices
- #3542: [java] MissingOverride: False negative for enum method
## java-codestyle
- #1595: [java] Discuss default for utility classes in ClassNamingConventions
- #3563: [java] The ClassNamingConventionsRule false-positive’s on the class name “Constants”
## java-errorprone
- #3560: [java] InvalidLogMessageFormat: False positive with message and exception in a block inside a lambda
## java-performance
- #2364: [java] AddEmptyString false positive in annotation value
## java-security
- #3368: [java] HardcodedCryptoKey false negative with variable assignments

# External Contributions

- #3538: [apex] New rule EagerlyLoadedDescribeSObjectResult - Jonathan Wiesel
- #3549: [java] Ignore AddEmptyString rule in annotations - Stanislav Myachenkov
- #3561: [java] InvalidLogMessageFormat: False positive with message and exception in a block inside a lambda - Nicolas Filotto
- #3565: [doc] Fix resource leak due to Files.walk - lujiefsi
- #3571: [apex] Fix for #1089 - Added new configuration property additionalAssertMethodPattern to ApexUnitTestClassShouldHaveAssertsRule - Scott Wells
- #3572: [apex] Fix for #3566 - Added new configuration property reportMissingDescription to ApexDocRule - Scott Wells
- #3573: [apex] Fix for #3568 - Added new configuration properties reportEmptyPrivateNoArgConstructor and reportEmptyVirtualMethod to EmptyStatementBlock - Scott Wells
- #3574: [apex] Fix for #3569 - Added new configuration properties allowCommentedBlocks and allowExceptionNameRegex to EmptyCatchBlock - Scott Wells
- #3575: [apex] Fix for #3570 - Added new configuration property reportInForLoopInitializer to OneDeclarationPerLine - Scott Wells
- #3577: [apex] Fix for #3576 - Added new configuration properties *AuthMethodPattern and *AuthMethodTypeParamIndex to ApexCRUDViolation rule - Scott Wells
- #3578: [apex] ApexCRUDViolation: Documentation changes for #3576 - Scott Wells
- #3580: [doc] Release notes updates for the changes in issue #3569 - Scott Wells
- #3581: [apex] #3569 - Requested changes for code review feedback - Scott Wells
