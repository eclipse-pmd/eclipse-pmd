# New Rules

The new Apex rule FieldDeclarationsShouldBeAtStart (apex-codestyle) helps to ensure that field declarations are always at the beginning of a class.

The new Apex rule UnusedLocalVariable (apex-bestpractices) detects unused local variables.


# Fixed Issues

## apex-design
- #2358: [apex] Invalid Apex in Cognitive Complexity tests
## apex-security
- #2210: [apex] ApexCRUDViolation: Support WITH SECURITY_ENFORCED
- #2399: [apex] ApexCRUDViolation: false positive with security enforced with line break
## core
- #1286: [core] Export Supporting JSON Format
- #2019: [core] Insufficient deprecation warnings for XPath attributes
- #2357: Add code of conduct: Contributor Covenant
- #2426: [core] CodeClimate renderer links are dead
- #2432: [core] Close ZIP data sources even if a runtime exception or error is thrown
## doc
- #2355: [doc] Improve documentation about incremental analysis
- #2356: [doc] Add missing doc about pmd.github.io
- #2412: [core] HTMLRenderer doesn’t render links to source files
- #2413: [doc] Improve documentation about the available renderers (PMD/CPD)
## java
- #2378: [java] AbstractJUnitRule has bad performance on large code bases
## java-bestpractices
- #2398: [java] AbstractClassWithoutAbstractMethod false negative with inner abstract classes
## java-codestyle
- #1164: [java] ClassNamingConventions suggests to add Util for class containing only static constants
- #1723: [java] UseDiamondOperator false-positive inside lambda
## java-design
- #2390: [java] AbstractClassWithoutAnyMethod: missing violation for nested classes
## java-errorprone
- #2402: [java] CloseResource possible false positive with Primitive Streams
## java-multithreading
- #2313: [java] Documenation for DoNotUseThreads is outdated
## javascript
- #1235: [javascript] Use of let results in an Empty Statement in the AST
- #2379: [javascript] Support for-of loop
## javascript-errorprone
- #384: [javascript] Trailing commas not detected on French default locale


# External Contributions

- #2312: [apex] Update ApexCRUDViolation Rule - Joshua S Arquilevich
- #2314: [doc] maven integration - Add version to plugin - Pham Hai Trung
- #2353: [plsql] xmlforest with optional AS - Piotr Szymanski
- #2383: [apex] Fix invalid apex in documentation - Gwilym Kuiper
- #2395: [apex] New Rule: Unused local variables - Gwilym Kuiper
- #2396: [apex] New rule: field declarations should be at start - Gwilym Kuiper
- #2397: [apex] fixed WITH SECURITY_ENFORCED regex to recognise line break characters - Kieran Black
- #2401: [doc] Update DoNotUseThreads rule documentation - Saikat Sengupta
- #2403: [java] #2402 fix false-positives on Primitive Streams - Bernd Farka
- #2409: [java] ClassNamingConventions suggests to add Util for class containing only static constants, fixes #1164 - Binu R J
- #2411: [java] Fix UseAssertEqualsInsteadOfAssertTrue Example - Moritz Scheve
- #2423: [core] Fix Checkstyle OperatorWrap in AbstractTokenizer - Harsh Kukreja
- #2432: [core] Close ZIP data sources even if a runtime exception or error is thrown - Gonzalo Exequiel Ibars Ingman
