# Modified rules

- The Java rule UnusedPrivateField now ignores private fields, if the fields are annotated with any annotation or the enclosing class has any annotation. Annotations often enable a framework (such as dependency injection, mocking or e.g. Lombok) which use the fields by reflection or other means. This usage can’t be detected by static code analysis. Previously these frameworks where explicitly allowed by listing their annotations in the property “ignoredAnnotations”, but that turned out to be prone of false positive for any not explicitly considered framework. That’s why the property “ignoredAnnotations” has been deprecated for this rule.
- The Java rule CommentDefaultAccessModifier now by default ignores JUnit5 annotated methods. This behavior can be customized using the property ignoredAnnotations.

# Fixed Issues

## cli
- #4118: [cli] run.sh designer reports “integer expression expected”

## core
- #4116: [core] Missing –file arg in TreeExport CLI example

## doc
- #4072: [doc] Add architecture decision records
- #4109: [doc] Add page for 3rd party rulesets
- #4124: [doc] Fix typos in Java rule docs

## java
- #3431: [java] Add sample java project to regression-tester which uses new language constructs

## java-bestpractices
- #4033: [java] UnusedPrivateField - false positive with Lombok @ToString.Include
- #4037: [java] UnusedPrivateField - false positive with Spring @SpyBean

## java-codestyle
- #3859: [java] CommentDefaultAccessModifier is triggered in JUnit5 test class
- #4085: [java] UnnecessaryFullyQualifiedName false positive when nested and non-nested classes with the same name and in the same package are used together
- #4133: [java] UnnecessaryFullyQualifiedName - FP for inner class pkg.ClassA.Foo implementing pkg.Foo

## java-design
- #4090: [java] FinalFieldCouldBeStatic false positive with non-static synchronized block (regression in 6.48, worked with 6.47)

## java-errorprone
- #1718: [java] ConstructorCallsOverridableMethod false positive when calling super method
- #2348: [java] ConstructorCallsOverridableMethod occurs when unused overloaded method is defined
- #4099: [java] ConstructorCallsOverridableMethod should consider method calls with var access

## scala
- #4138: [scala] Upgrade scala-library to 2.12.7 / 2.13.9 and scalameta to 4.6.0


# External Contributions

- #4066: [lua] Add support for Luau syntax and skipping literal sequences in CPD - Matt Hargett (@matthargett)
- #4100: [java] Update UnusedPrivateFieldRule - ignore any annotations - Lynn (@LynnBroe)
- #4116: [core] Fix missing –file arg in TreeExport CLI example - mohan-chinnappan-n (@mohan-chinnappan-n)
- #4124: [doc] Fix typos in Java rule docs - Piotrek Żygieło (@pzygielo)
- #4128: [java] Fix False-positive UnnecessaryFullyQualifiedName when nested and non-nest… #4103 - Oleg Andreych (@OlegAndreych)
- #4130: [ci] GitHub Workflows security hardening - Alex (@sashashura)
- #4131: [doc] TooFewBranchesForASwitchStatement - Use “if-else” instead of “if-then” - Suvashri (@Suvashri)
- #4137: [java] Fixes 3859: Exclude junit5 test methods from the commentDefaultAccessModifierRule - Luis Alcantar (@lfalcantar)

