# New and noteworthy

# New Rules

- The new Apex rule OverrideBothEqualsAndHashcode brings the well known Java rule to Apex. In Apex the same principle applies: equals and hashCode should always be overridden together to ensure collection classes such as Maps and Sets work as expected.
- The new Visualforce rule VfHtmlStyleTagXss checks for potential XSS problems when using <style> tags on Visualforce pages.

# Deprecated rules

## java-performance
- AvoidUsingShortType: arithmetic on shorts is not significantly slower than on ints, whereas using shorts may provide significant memory savings in arrays.
- SimplifyStartsWith: the suggested code transformation has an insignificant performance impact, and decreases readability.


# Fixed Issues

## core
- #2953: [core] Support SARIF JSON Format
- #2970: [core] PMD 6.30.0 release is not reproducible
- #2994: [core] Fix code climate severity strings
## java-bestpractices
- #575: [java] LiteralsFirstInComparisons should consider constant fields
- #2454: [java] UnusedPrivateMethod violation for disabled class in 6.23.0
- #2833: [java] NPE in UseCollectionIsEmptyRule with enums
- #2876: [java] UnusedPrivateField cannot override ignored annotations property
- #2957: [java] Ignore unused declarations that have special name
## java-codestyle
- #2960: [java] Thread issue in MethodNamingConventionsRule
## java-design
- #3006: [java] NPE in SingularFieldRule with concise resource syntax
## java-errorprone
- #2976: [java] CompareObjectsWithEquals: FP with array.length
- #2977: [java] 6.30.0 introduces new false positive in CloseResource rule?
- #2979: [java] UseEqualsToCompareStrings: FP with “var” variables
- #3004: [java] UseEqualsToCompareStrings false positive with PMD 6.30.0
- #3062: [java] CloseResource FP with reassigned stream
## java-performance
- #2296: [java] Deprecate rule AvoidUsingShortType
- #2740: [java] Deprecate rule SimplifyStartsWith
- #3088: [java] AvoidInstantiatingObjectsInLoops - false positive with Collections
## vf-security
- #3081: [vf] VfUnescapeEl: Inherently un-XSS-able built-in functions trigger false positives


# External Contributions

- #2666: [swift] Manage swift5 string literals - kenji21
- #2959: [apex] New Rule: override equals and hashcode rule - recdevs
- #2963: [cpp] Add option to ignore sequences of literals - Maikel Steneker
- #2964: [cs] Update C# grammar for additional C# 7 and C# 8 features - Maikel Steneker
- #2965: [cs] Improvements for ignore sequences of literals functionality - Maikel Steneker
- #2968: [java] NPE in UseCollectionIsEmptyRule with enums - foxmason
- #2983: [java] LiteralsFirstInComparisons should consider constant fields - Ozan Gulle
- #2994: [core] Fix code climate severity strings - Vincent Maurin
- #3005: [vf] [New Rule] Handle XSS violations that can occur within Html Style tags - rmohan20
- #3073: [core] Include SARIF renderer - Manuel Moya Ferrer
- #3084: [vf] VfUnescapeEl false-positive with builtin functions - Josh Feingold
