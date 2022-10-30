# New Rules

The new Apex rule ApexUnitTestClassShouldHaveRunAs ensures that unit tests use System.runAs() at least once. This makes the tests more robust, and independent from the user running it.

   <rule ref="category/apex/bestpractices.xml/ApexUnitTestClassShouldHaveRunAs"/>

# Modified rules

The Java rule TestClassWithoutTestCases has a new property testClassPattern. This is used to detect empty test classes by name. Previously this rule could only detect empty JUnit3 test cases properly. To switch back to the old behavior, this property can be set to an empty value which disables the test class detection by pattern.

# Fixed Issues


## apex
- #4149: [apex] New rule: ApexUnitTestClassShouldHaveRunAs

## doc
- #4144: [doc] Update docs to reflect supported languages
- #4163: [doc] Broken links on page “Architecture Decisions”

## java-bestpractices
- #4140: [java] \[doc] AccessorClassGeneration violations hidden with Java 11

## java-codestyle
- #4139: [java] UnnecessaryFullyQualifiedName FP when the same simple class name exists in the current package

## java-documentation
- #4141: [java] UncommentedEmptyConstructor FP when constructor annotated with @Autowired

## java-performance
- #1167: [java] AvoidArrayLoops false positive on double assignment
- #2080: [java] StringToString rule false-positive with field access
- #2692: [java] \[doc] AvoidArrayLoops flags copy assignment in same array as sub-optimal
- #3437: [java] StringToString doesn’t trigger on Bar.class.getSimpleName().toString()
- #3681: [java] StringToString doesn’t trigger on string literals
- #3847: [java] AvoidArrayLoops should consider final variables
- #3977: [java] StringToString false-positive with local method name confusion
- #4091: [java] AvoidArrayLoops false negative with do-while loops
- #4148: [java] UseArrayListInsteadOfVector ignores Vector when other classes are imported

## java-errorprone
- #929: [java] Inconsistent results with TestClassWithoutTestCases
- #2636: [java] TestClassWithoutTestCases false positive with JUnit5 ParameterizedTest

## javascript
- #4165: [javascript] InaccurateNumericLiteral underscore separator notation false positive

# External Contributions

- #4142: [java] fix #4141 Update UncommentedEmptyConstructor - ignore @Autowired annotations - Lynn (@LynnBroe)
- #4147: [java] Added support for Do-While for AvoidArrayLoops - Yasar Shaikh (@yasarshaikh)
- #4150: [apex] New rule ApexUnitTestClassShouldHaveRunAs #4149 - Thomas Prouvot (@tprouvot)
