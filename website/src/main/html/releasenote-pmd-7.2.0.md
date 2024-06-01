# Fixed Issues

## java

- #4912: [java] Unable to parse some Java9+ resource references
- #4973: [java] Stop parsing Java for CPD
- #4980: [java] Bad intersection, unrelated class types java.lang.Object[] and java.lang.Number
- #4988: [java] Fix impl of ASTVariableId::isResourceDeclaration / VariableId/@ResourceDeclaration
- #4990: [java] Add an attribute @PackageQualifier to ASTClassType
- #5006: [java] Bad intersection, unrelated class types Child and Parent<? extends Child>
- #5029: [java] PMD 7.x throws stack overflow in TypeOps$ProjectionVisitor while parsing a Java class

## java-bestpractices

- #4278: [java] UnusedPrivateMethod FP with Junit 5 @MethodSource and default factory method name
- #4852: [java] ReplaceVectorWithList false-positive (neither Vector nor List usage)
- #4975: [java] UnusedPrivateMethod false positive when using @MethodSource on a @Nested test
- #4985: [java] UnusedPrivateMethod false-positive / method reference in combination with custom object

## java-codestyle

- #1619: [java] LocalVariableCouldBeFinal on ‘size’ variable in for loop
- #3122: [java] LocalVariableCouldBeFinal should consider blank local variables
- #4903: [java] UnnecessaryBoxing, but explicit conversion is necessary
- #4924: [java] UnnecessaryBoxing false positive in PMD 7.0.0 in lambda
- #4930: [java] EmptyControlStatement should not allow empty try with concise resources
- #4954: [java] LocalVariableNamingConventions should allow unnamed variables by default
- #5028: [java] FormalParameterNamingConventions should accept unnamed parameters by default

## java-errorprone

- #4042: [java] A false negative about the rule StringBufferInstantiationWithChar
- #5007: [java] AvoidUsingOctalValues triggers on non-octal double literals with a leading 0

## java-multithreading

- #2368: [java] False positive UnsynchronizedStaticFormatter in static initializer


# External Contributions

- #5020: [java] Fix AvoidUsingOctalValues false-positive - Gold856 (@Gold856)
