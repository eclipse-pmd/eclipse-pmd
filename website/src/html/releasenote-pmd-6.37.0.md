# New and noteworthy

## Java 17 Support

This release of PMD brings support for Java 17. PMD supports JEP 409: Sealed Classes which has been promoted to be a standard language feature of Java 17.

## New Java rules.

PrimitiveWrapperInstantiation reports usages of primitive wrapper constructors. They are deprecated since Java 9 and should not be used.

<rule ref="category/java/bestpractices.xml/PrimitiveWrapperInstantiation" />

The rule is part of the quickstart.xml ruleset.

SimplifiableTestAssertion suggests rewriting some test assertions to be more readable.

<rule ref="category/java/bestpractices.xml/SimplifiableTestAssertion" />

The rule is part of the quickstart.xml ruleset.

ReturnEmptyCollectionRatherThanNull suggests returning empty collections / arrays instead of null.

<rule ref="category/java/errorprone.xml/ReturnEmptyCollectionRatherThanNull" />

The rule is part of the quickstart.xml ruleset.

## Renamed rules

The Java rule MissingBreakInSwitch has been renamed to ImplicitSwitchFallThrough (category error prone) to better reflect the rule’s purpose: The rule finds implicit fall-through cases in switch statements, which are most likely unexpected. The old rule name described only one way how to avoid a fall-through, namely using break but continue, throw and return avoid a fall-through as well. This enables us to improve this rule in the future.

## Deprecated rules

The following Java rules are deprecated and removed from the quickstart ruleset, as the new rule SimplifiableTestAssertion merges their functionality:
- UseAssertEqualsInsteadOfAssertTrue
- UseAssertNullInsteadOfAssertTrue
- UseAssertSameInsteadOfAssertTrue
- UseAssertTrueInsteadOfAssertEquals
- SimplifyBooleanAssertion

The Java rule ReturnEmptyArrayRatherThanNull is deprecated and removed from the quickstart ruleset, as the new rule ReturnEmptyCollectionRatherThanNull supersedes it.

The following Java rules are deprecated and removed from the quickstart ruleset, as the new rule PrimitiveWrapperInstantiation merges their functionality:
- BooleanInstantiation
- ByteInstantiation
- IntegerInstantiation
- LongInstantiation
- ShortInstantiation

The Java rule UnnecessaryWrapperObjectCreation is deprecated with no planned replacement before PMD 7. In it’s current state, the rule is not useful as it finds only contrived cases of creating a primitive wrapper and unboxing it explicitly in the same expression. In PMD 7 this and more cases will be covered by a new rule UnnecessaryBoxing.

# Fixed Issues

## apex
- #3201: [apex] ApexCRUDViolation doesn’t report Database class DMLs, inline no-arg object instantiations and inline list initialization
- #3329: [apex] ApexCRUDViolation doesn’t report SOQL for loops
## core
- #1603: [core] Language version comparison
- #2133: [xml] Allow to check Salesforce XML Metadata using XPath rules
- #3377: [core] NPE when specifying report file in current directory in PMD CLI
- #3387: [core] CPD should avoid unnecessary copies when running with –skip-lexical-errors
## java-bestpractices
- #2908: [java] Merge Junit assertion simplification rules
- #3235: [java] UseTryWithResources false positive when closeable is provided as a method argument or class field
## java-errorprone
- #3361: [java] Rename rule MissingBreakInSwitch to ImplicitSwitchFallThrough
- #3382: [java] New rule ReturnEmptyCollectionRatherThanNull
## java-performance
- #3420: [java] NPE in InefficientStringBuffering with Records

# External Contributions

- #3367: [apex] Check SOQL CRUD on for loops - Jonathan Wiesel
- #3373: [apex] Add ApexCRUDViolation support for database class, inline no-arg object construction DML and inline list initialization DML - Jonathan Wiesel
- #3385: [core] CPD: Optimize –skip-lexical-errors option - Woongsik Choi
- #3388: [doc] Add Code Inspector in the list of tools - Julien Delange
- #3417: [core] Support forcing a specific language from the command-line - Aidan Harding
