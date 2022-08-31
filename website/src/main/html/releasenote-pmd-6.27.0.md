# Java 15 Support

This release of PMD brings support for Java 15. PMD can parse Text Blocks which have been promoted to be a standard language feature of Java.

# New Rules

The new Java rule AvoidReassigningCatchVariables (java-bestpractices) finds cases where the variable of the caught exception is reassigned. This practice is surprising and prevents further evolution of the code like multi-catch.

# Modified rules

The Java rule CloseResource (java-errorprone) has a new property closeNotInFinally. With this property set to true the rule will also find calls to close a resource, which are not in a finally-block of a try-statement. If a resource is not closed within a finally block, it might not be closed at all in case of exceptions.
As this new detection would yield many new violations, it is disabled by default. It might be enabled in a later version of PMD.

# Deprecated rules

The Java rule DataflowAnomalyAnalysis (java-errorprone) is deprecated in favour of UnusedAssignment (java-bestpractices), which was introduced in PMD 6.26.0.

# Fixed Issues

## core
- #724: [core] Avoid parsing rulesets multiple times
- #1962: [core] Simplify Report API
- #2653: [lang-test] Upgrade kotlintest to Kotest
- #2656: [all] Ensure PMD/CPD uses tab width of 1 for tabs consistently
- #2690: [core] Fix java7 compatibility
## java
- #2646: [java] Support JDK 15
## java-bestpractices
- #2471: [java] New Rule: AvoidReassigningCatchVariables
- #2663: [java] NoClassDefFoundError on upgrade from 6.25.0 to 6.26.0
- #2668: [java] UnusedAssignment false positives
- #2673: [java] UnusedPrivateField and SingularField false positive with lombok annotation EqualsAndHashCode
- #2684: [java] UnusedAssignment FP in try/catch
- #2686: [java] UnusedAssignment must not flag abstract method parameters in interfaces and abstract classes
## java-design
- #2108: [java] [doc] ImmutableField rule: Description should clarify shallow immutability
- #2461: [java] ExcessiveParameterListRule must ignore a private constructor
## java-errorprone
- #2264: [java] SuspiciousEqualsMethodName: Improve description about error-prone overloading of equals()
- #2410: [java] ProperCloneImplementation not valid for final class
- #2431: [java] InvalidLogMessageFormatRule throws IndexOutOfBoundsException when only logging exception message
- #2439: [java] AvoidCatchingThrowable can not detect the case: catch (java.lang.Throwable t)
- #2470: [java] CloseResource false positive when resource included in return value
- #2531: [java] UnnecessaryCaseChange can not detect the case like: foo.equals(bar.toLowerCase())
- #2647: [java] Deprecate rule DataFlowAnomalyAnalysis
## java-performance
- #1868: [java] false-positive for SimplifyStartsWith if string is empty
- #2441: [java] RedundantFieldInitializer can not detect a special case for char initialize: char foo = '\0';
- #2530: [java] StringToString can not detect the case: getStringMethod().toString()
## dart
- #2750: [dart] [cpd] Cpd Dart escaped dollar

# External Contributions

- #2656: [all] Ensure PMD/CPD uses tab width of 1 for tabs consistently - Maikel Steneker
- #2659: [java] StringToString can not detect the case: getStringMethod().toString() - Mykhailo Palahuta
- #2662: [java] UnnecessaryCaseChange can not detect the case like: foo.equals(bar.toLowerCase()) - Mykhailo Palahuta
- #2671: [java] CloseResource false positive when resource included in return value - Mykhailo Palahuta
- #2674: [java] add lombok.EqualsAndHashCode in AbstractLombokAwareRule - berkam
- #2677: [java] RedundantFieldInitializer can not detect a special case for char initialize: char foo = '\0'; - Mykhailo Palahuta
- #2678: [java] AvoidCatchingThrowable can not detect the case: catch (java.lang.Throwable t) - Mykhailo Palahuta
- #2679: [java] InvalidLogMessageFormatRule throws IndexOutOfBoundsException when only logging exception message - Mykhailo Palahuta
- #2682: [java] New Rule: AvoidReassigningCatchVariables - Mykhailo Palahuta
- #2697: [java] ExcessiveParameterListRule must ignore a private constructor - Mykhailo Palahuta
- #2699: [java] ProperCloneImplementation not valid for final class - Mykhailo Palahuta
- #2700: [java] Fix OnlyOneReturn code example - Jan-Lukas Else
- #2722: [doc] [java] ImmutableField: extend description, fixes #2108 - Mateusz Stefanski
- #2723: [doc] [java] SimplifyStartsWith: update description and example, fixes #1868 - Mateusz Stefanski
- #2724: [doc] [java] SuspiciousEqualsMethodName: update description, fixes #2264 - Mateusz Stefanski
- #2725: Cleanup: change valueOf to parse when we need primitive return value. - XenoAmess
- #2726: Cleanup: replace StringBuffer with StringBuilder - XenoAmess
- #2727: Cleanup: replace indexOf() < 0 with contains - XenoAmess
- #2728: Cleanup: javadoc issues - XenoAmess
- #2729: Cleanup: use print instead of printf if no format exists - XenoAmess
- #2730: Cleanup: StringBuilder issues - XenoAmess
- #2731: Cleanup: avoid compiling Patterns repeatedly - XenoAmess
- #2732: Cleanup: use StandardCharsets instead of Charset.forName - XenoAmess
- #2733: Cleanup: Collection::addAll issues - XenoAmess
- #2734: Cleanup: use try with resources - XenoAmess
- #2744: Cleanup: fix typos - XenoAmess
- #2745: [core] Fix a NPE in buildUsageText - XenoAmess
- #2749: [dart] [cpd] Improvements for Dart interpolated strings - Maikel Steneker
- #2750: [dart] [cpd] Cpd Dart escaped dollar - Maikel Steneker
