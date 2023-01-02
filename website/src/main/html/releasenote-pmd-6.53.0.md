# Modified rules

The Java rule `UnusedPrivateField` has a new property `reportForAnnotations`. This is a list of fully qualified names of the annotation types that should be reported anyway. If an unused field has any of these annotations, then it is reported. If it has any other annotation, then it is still considered to be used and is not reported.

# Deprecated rules

The Java rules `ExcessiveClassLength` and `ExcessiveMethodLength` have been deprecated. The rule `NcssCount` can be used instead. The deprecated rules will be removed with PMD 7.0.0.

The Java rule `EmptyStatementNotInLoop` is deprecated. Use the rule `UnnecessarySemicolon` instead. Note: Actually it was announced to be deprecated since 6.46.0 but the rule was not marked as deprecated yet. This has been done now.

# Fixed Issues

## core
- #4248: [core] Can’t analyze sources in zip files

## apex-security
- #4146: [apex] ApexCRUDViolation: Recognize User Mode in SOQL + DML

## java
- #4266: [java] PMD fails to process a record with lambda in compact constructor

## java-bestpractices
- #4166: [java] UnusedPrivateField doesn’t find annotated unused private fields anymore
- #4250: [java] WhileLoopWithLiteralBoolean - false negative with complex expressions still occurs in PMD 6.52.0

## java-design
- #2127: [java] Deprecate rules ExcessiveClassLength and ExcessiveMethodLength

## java-errorprone
- #4164: [java]/[doc] AvoidAssertAsIdentifier and AvoidEnumAsIdentifier - clarify use case

## java-multithreading
- #4210: [java] DoNotUseThreads report duplicate warnings

# External Contributions

- #4244: [apex] ApexCRUDViolation: user mode and system mode with test cases added - Tarush Singh (@Tarush-Singh35)
- #4274: [java] Fix finding lambda scope in record compact constructor - kdebski85 (@kdebski85)
