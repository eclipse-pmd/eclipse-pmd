# New and changed rules

## New Rules

The new Apex rule `AvoidNonRestrictiveQueries` finds SOQL and SOSL queries without a where or limit statement. This can quickly cause governor limit exceptions.

## Changed rules

`ClassNamingConventions` (Apex Codestyle): Two new properties to configure different patterns for inner classes and interfaces: innerClassPattern and innerInterfacePattern.

## Renamed rules

`InaccurateNumericLiteral` (JavaScript Error Prone) has been renamed from `InnaccurateNumericLiteral`. The old rule name still works but is deprecated.

# Fixed Issues

## apex
- #5094: [apex] “No adapter exists for type” error message printed to stdout instead of stderr

## apex-bestpractices
- #5095: [apex] ApexUnitTestShouldNotUseSeeAllDataTrue false negative due to casing (regression in PMD 7)

## apex-codestyle
- #4800: [apex] ClassNamingConvention: Support naming convention for inner classes

## apex-performance
- #635: [apex] New Rule: Avoid soql/sosl queries without a where clause or limit statement

## java-bestpractices
- #5106: [java] AccessorClassGeneration: Node was null for default constructor
- #5110: [java] UnusedPrivateMethod for method referenced by lombok.Builder.ObtainVia
- #5117: [java] UnusedPrivateMethod for methods annotated with jakarta.annotation.PostConstruct or PreDestroy

## java-errorprone
- #1488: [java] MissingStaticMethodInNonInstantiatableClass: False positive with Lombok Builder on Constructor

## javascript-errorprone
- #2367: [javascript] InnaccurateNumericLiteral is misspelled
- #4716: [javascript] InaccurateNumericLiteral with number 259200000

## plsql
- #5086: [plsql] Fixed issue with missing optional table alias in MERGE usage
- #5087: [plsql] Add support for SQL_MACRO
- #5088: [plsql] Add support for ‘DEFAULT’ clause on the arguments of some oracle functions
- #5133: [plsql] AssertionError: Root of the tree should implement RootNode for a PL/SQL type declaration

## cli
- #5120: [cli] Can’t start designer under Windows

## core
- #5091: [core] PMD CPD v7.3.0 gives deprecation warning for skipLexicalErrors even when not used

# External Contributions

- #5048: [apex] Added Inner Classes to Apex Class Naming Conventions Rule - Justin Stroud (@justinstroudbah / @sgnl-labs)
- #5086: [plsql] Fixed issue with missing optional table alias in MERGE usage - Arjen Duursma (@duursma)
- #5087: [plsql] Add support for SQL_MACRO - Arjen Duursma (@duursma)
- #5088: [plsql] Add support for ‘DEFAULT’ clause on the arguments of some oracle functions - Arjen Duursma (@duursma)
- #5107: [doc] Update maven.md - Typo fixed for maven target - karthikaiyasamy (@karthikaiyasamy)
- #5109: [java] Exclude constructor with lombok.Builder for MissingStaticMethodInNonInstantiatableClass - Krzysztof Debski (@kdebski85)
- #5111: [java] Fix UnusedPrivateMethod for @lombok.Builder.ObtainVia - Krzysztof Debski (@kdebski85)
- #5118: [java] FP for UnusedPrivateMethod with Jakarta @PostConstruct/PreDestroy annotations - Krzysztof Debski (@kdebski85)
- #5121: [plsql] Fixed issue with missing optional table alias in MERGE usage - Arjen Duursma (@duursma)
