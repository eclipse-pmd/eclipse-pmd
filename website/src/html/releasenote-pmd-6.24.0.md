# New Rules

The new Java Rule LiteralsFirstInComparisons (java-bestpractices) find String literals, that are used in comparisons and are not positioned first. Using the String literal as the receiver of e.g. equals helps to avoid NullPointerExceptions.

This rule is replacing the two old rules PositionLiteralsFirstInComparisons and PositionLiteralsFirstInCaseInsensitiveComparisons and extends the check for the methods compareTo, compareToIgnoreCase and contentEquals in addition to equals and equalsIgnoreCase.

Note: This rule also replaces the two mentioned rules in Java’s quickstart ruleset.


# Fixed Issues

## apex-bestpractices
- #2468: [apex] Unused Local Variable fails on blocks
## core
- #2444: [core] Support reproducible builds
- #2484: [core] Update maven-enforcer-plugin to require Java 118
## c#
- #2495: [c#] Support for interpolated verbatim strings
## java
- #2472: [java] JavaCharStream throws an Error on invalid escape
## java-bestpractices
- #2145: [java] Deprecate rules PositionLiteralsFirstIn(CaseInsensitive)Comparisons in favor of LiteralsFirstInComparisons
- #2288: [java] JUnitTestsShouldIncludeAssert: Add support for Hamcrest MatcherAssert.assertThat
- #2437: [java] AvoidPrintStackTrace can’t detect the case e.getCause().printStackTrace()
## java-codestyle
- #2476: [java] MethodNamingConventions - Add support for JUnit 5 method naming
## java-errorprone
- #2477: [java] JUnitSpelling false-positive for JUnit5/4 tests
## swift
- #2473: [swift] Swift 5 (up to 5.2) support for CPD


# External Contributions

- #2446: [core] Update maven-compiler-plugin to 3.8.1 - Artem Krosheninnikov
- #2448: [java] Operator Wrap check - Harsh Kukreja
- #2449: [plsql] Additional info in SqlStatement, FormalParameter and FetchStatement - Grzegorz Sudolski
- #2452: [doc] Fix “Making Rulesets” doc sample code indentation - Artur Dryomov
- #2457: [xml] Adding XML to CPD supported languages - Fernando Cosso
- #2465: [dependencies] Upgrade hamcrest, mockito and JUnit - Artem Krosheninnikov
- #2469: [apex] fix false positive unused variable if only a method is called - Gwilym Kuiper
- #2475: [swift] Swift 4.2-5.2 support - kenji21
- #2476: [java] MethodNamingConventions - Add support for JUnit 5 method naming - Bruno Ritz
- #2478: [java] New rule: LiteralsFirstInComparisons - John-Teng
- #2479: [java] False positive with Hamcrest’s assertThat - andreoss
- #2481: [java] Fix JUnitSpellingRule false positive - Artem Krosheninnikov
- #2493: [java] Deprecate redundant String Comparison rules - John-Teng
- #2495: [c#] Support for interpolated verbatim strings - Maikel Steneker
