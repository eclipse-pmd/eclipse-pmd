# New Rules

- The new Java rule UnnecessaryVarargsArrayCreation reports explicit array creation when a varargs is expected. This is more heavy to read and could be simplified.
- The new Java rule ConfusingArgumentToVarargsMethod reports some confusing situations where a varargs method is called with an inexact argument type. These may end up in a mismatch between the expected parameter type and the actual value.
- The new Java rule LambdaCanBeMethodReference reports lambda expressions that can be replaced with a method reference. Please read the documentation of the rule for more info. This rule is now part of the Quickstart ruleset.

# Rule Changes

- JUnitTestsShouldIncludeAssert and JUnitTestContainsTooManyAsserts have a new property named extraAssertMethodNames. With this property, you can configure which additional static methods should be considered as valid verification methods. This allows to use custom mocking or assertion libraries.

# Fixed Issues

## apex
- #4418: [apex] ASTAnnotation.getImage() does not return value as written in the class

## apex-errorprone
- #3953: [apex] EmptyCatchBlock false positive with formal (doc) comments

## cpp
- #2438: [cpp] Repeated Duplication blocks

## java
- #4899: [java] Parsing failed in ParseLock#doParse() java.io.IOException: Stream closed
- #4902: [java] “Bad intersection, unrelated class types” for Constable[] and Enum[]
- #4947: [java] Broken TextBlock parser

## java-bestpractices
- #1084: [java] Allow JUnitTestsShouldIncludeAssert to configure verification methods
- #3216: [java] New rule: UnnecessaryVarargsArrayCreation
- #4435: [java] [7.0-rc1] UnusedAssignment for used field
- #4569: [java] ForLoopCanBeForeach reports on loop for (int i = 0; i < list.size(); i += 2)
- #4618: [java] UnusedAssignment false positive with conditional assignments of fields

## java-codestyle
- #4602: [java] UnnecessaryImport: false positives with static imports
- #4785: [java] False Positive: PMD Incorrectly report violation for UnnecessaryImport
- #4779: [java] Examples in documentation of MethodArgumentCanBeFinal do not trigger the rule
- #4881: [java] ClassNamingConventions: interfaces are identified as abstract classes (regression in 7.0.0)

## java-design
- #2440: [java] FinalFieldCouldBeStatic FN when the right side of the assignment is a constant expression
- #3694: [java] SingularField ignores static variables
- #4873: [java] AvoidCatchingGenericException: Can no longer suppress on the exception itself

## java-errorprone
- #2056: [java] CloseResource false-positive with URLClassLoader in cast expression
- #4751: [java] PMD crashes when analyzing CloseResource Rule
- #4928: [java] EmptyCatchBlock false negative when allowCommentedBlocks=true
- #4948: [java] ImplicitSwitchFallThrough: False-positive with nested switch statements

## java-performance
- #3845: [java] InsufficientStringBufferDeclaration should consider literal expression
- #4874: [java] StringInstantiation: False-positive when using new String(charArray)
- #4886: [java] BigIntegerInstantiation: False Positive with Java 17 and BigDecimal.TWO

## pom-errorprone
- #4388: [pom] InvalidDependencyTypes doesn’t consider dependencies at all

# External Contributions

- #4864: Fix #1084 [Java] add extra assert method names to Junit rules - Erwan Moutymbo (@emouty)
- #4894: Fix #4791 Error caused by space in JDK path - Scrates1 (@Scrates1)
