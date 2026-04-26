# New Rules ️

The new Apex rule `AvoidInterfaceAsMapKey` reports Map declarations (fields, variables, parameters) whose key type is an interface that has at least one abstract implementing class defining equals or hashCode. Using such maps results in potentially duplicated map entries or not being able to get entries by key.

The new Java rule `OverridingThreadRun` finds overridden Thread::run methods. This is not recommended. Instead, implement Runnable and pass an instance to the thread constructor.

# Fixed Issues ️

## apex

- #5386: [apex] Apex files ending in “Test” are skipped with a number of rules

## apex-errorprone

- #6492: [apex] New rule: Prevent use of interface -> abstract class with equals/hashCode as key in Map

## apex-security

- #5385: [apex] ApexCRUDViolation not reported even if SOQL doesn’t have permissions check on it

## java-bestpractices

- #4272: [java] JUnitTestsShouldIncludeAssert: False positive with assert in lambda

## java-multithreading

- #595: [java] New rule: Implement Runnable instead of extending Thread

## kotlin

- #6003: [kotlin] Support multidollar interpolation (Kotlin 2.2)

# Merged pull requests ️

- #6493: [apex] New Rule: AvoidInterfaceAsMapKeyRule - Jonny Alexander Power (@JonnyPower)
- #6497: [kotlin] Fix kotlin grammar for parsing multidollar interpolation - Peter Paul Bakker (@stokpop)
- #6555: [java] New rule: OverridingThreadRun to prefer using Runnable - Zbynek Konecny (@zbynek)
- #6556: [java] Fix #4272: False positive in UnitTestShouldIncludeAssert when using assertion in lambda - Lukas Gräf (@lukasgraef)
- #6563: [apex] Remove class name suffix “Test” as indicator of test classes - David Schach (@dschach)
- #6576: [test] chore: Throw a TestAbortedException on disabled tests - UncleOwen (@UncleOwen)
- #6577: [dist] chore: Improve error message for missing JAVA_HOME in AntIT.java - UncleOwen (@UncleOwen)
- #6607: [doc] basic.xml has been gone for a long time - UncleOwen (@UncleOwen)
