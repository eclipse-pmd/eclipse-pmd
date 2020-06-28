# New Rules

The new Java Rule UnnecessaryCast (java-codestyle) finds casts that are unnecessary while accessing collection elements.

The new Java Rule AvoidCalendarDateCreation (java-performance) finds usages of java.util.Calendar whose purpose is just to get the current date. This can be done in a more lightweight way.

The new Java Rule UseIOStreamsWithApacheCommonsFileItem (java-performance) finds usage of FileItem.get() and FileItem.getString(). These two methods are problematic since they load the whole uploaded file into memory.

# Modified rules

The Java rule UseDiamondOperator (java-codestyle) now by default finds unnecessary usages of type parameters, which are nested, involve wildcards and are used within a ternary operator. These usages are usually only unnecessary with Java8 and later, when the type inference in Java has been improved.

In order to avoid false positives when checking Java7 only code, the rule has the new property java7Compatibility, which is disabled by default. Settings this to “true” retains the old rule behaviour.

# Fixed Issues

## apex-bestpractices
- #2554: [apex] Exception applying rule UnusedLocalVariable on trigger
## core
- #971: [apex][plsql][java] Deprecate overly specific base rule classes
- #2451: [core] Deprecate support for List attributes with XPath 2.0
- #2599: [core] Fix XPath 2.0 Rule Chain Analyzer with Unions
- #2483: [lang-test] Support cpd tests based on text comparison. For details see Testing your implementation in the developer documentation.
## c#
- #2551: [c#] CPD suppression with comments doesn’t work
## cpp
- #1757: [cpp] Support unicode characters
## java
- #2549: [java] Auxclasspath in PMD CLI does not support relative file path
## java-codestyle
- #2545: [java] UseDiamondOperator false negatives
- #2573: [java] DefaultPackage: Allow package default JUnit 5 Test methods
## java-design
- #2563: [java] UselessOverridingMethod false negative with already public methods
- #2570: [java] NPathComplexity should mention the expected NPath complexity
## java-errorprone
- #2544: [java] UseProperClassLoader can not detect the case with method call on intermediate variable
## java-performance
- #2591: [java] InefficientStringBuffering/AppendCharacterWithChar: Fix false negatives with concats in appends
- #2600: [java] UseStringBufferForStringAppends: fix false negative with fields
## scala
- #2547: [scala] Add cross compilation for scala 2.12 and 2.13
