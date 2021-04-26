# New and noteworthy

## New rules

The new Java rule UseStandardCharsets finds usages of Charset.forName, where StandardCharsets can be used instead.

This rule is also part of the Quickstart Ruleset (rulesets/java/quickstart.xml) for Java.

The new Java rule UnnecessaryImport replaces the rules UnusedImports, DuplicateImports, ImportFromSamePackage, and DontImportJavaLang.

This rule is also part of the Quickstart Ruleset (rulesets/java/quickstart.xml) for Java.

## Modified rules

The Apex rule ApexCRUDViolation does not ignore getters anymore and also flags SOQL/SOSL/DML operations without access permission checks in getters. This will produce false positives now for VF getter methods, but we can’t reliably detect, whether a getter is a VF getter or not. In such cases, the violation should be suppressed.

## Deprecated rules

### java-bestpractices
- UnusedImports: use the rule UnnecessaryImport instead
### java-codestyle
- DuplicateImports: use the rule UnnecessaryImport instead
- DontImportJavaLang: use the rule UnnecessaryImport instead
### java-errorprone
- ImportFromSamePackage: use the rule UnnecessaryImport instead

# Fixed Issues

## apex-performance
- #3198: [apex] OperationWithLimitsInLoopRule: Support more limit consuming static method invocations
## apex-security
- #3202: [apex] ApexCRUDViolationRule fails to report CRUD violation on COUNT() queries
- #3210: [apex] ApexCRUDViolationRule false-negative on non-VF getter
## java-bestpractices
- #3190: [java] Use StandardCharsets instead of Charset.forName
- #3224: [java] UnusedAssignment crashes with nested records
## java-codestyle
- #3128: [java] New rule UnnecessaryImport, deprecate DuplicateImports, ImportFromSamePackage, UnusedImports
## java-errorprone
- #2757: [java] CloseResource: support Lombok’s @Cleanup annotation
- #3169: [java] CheckSkipResult: NPE when using pattern bindings

# External Contributions

- #3193: [java] New rule: UseStandardCharsets - Andrea Aime
- #3198: [apex] OperationWithLimitsInLoopRule: Support more limit consuming static method invocations - Jonathan Wiesel
- #3211: [apex] ApexCRUDViolationRule: Do not assume method is VF getter to avoid CRUD checks - Jonathan Wiesel
- #3234: [apex] ApexCRUDViolation: COUNT is indeed CRUD checkable since it exposes data (false-negative) - Jonathan Wiesel
