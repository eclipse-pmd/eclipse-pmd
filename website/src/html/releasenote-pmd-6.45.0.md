# New and noteworthy️

## apex
- #3817: [apex] Add designer bindings to display main attributes
## apex-performance
- #3773: [apex] EagerlyLoadedDescribeSObjectResult false positives with SObjectField.getDescribe()
## core
- #2693: [ci] Add integration tests with real open-source projects
- #3299: [core] Deprecate system properties of PMDCommandLineInterface
## java
- #3809: [java] Support JDK 18
## doc
- #2504: [doc] Improve “Edit me on github” button
- #3812: [doc] Documentation website table of contents broken on pages with many subheadings
## java-design
- #3850: [java] ImmutableField - false negative when field assigned in constructor conditionally
- #3851: [java] ClassWithOnlyPrivateConstructorsShouldBeFinal - false negative when a compilation unit contains two class declarations
## xml
- #2766: [xml] XMLNS prefix is not pre-declared in xpath query
- #3863: [xml] Make XPath rules work exactly as in the XPath spec

# External Contributions

- #3773: [apex] EagerlyLoadedDescribeSObjectResult false positives with SObjectField.getDescribe() - @filiprafalowicz
- #3811: [doc] Improve “Edit me on github” button - @btjiong
- #3836: [doc] Make TOC scrollable when too many subheadings - @JerritEic




# New and noteworthy

## Support for HTML

This version of PMD ships a new language module to support analyzing of HTML. Support for HTML is experimental and might change without notice. The language implementation is not complete yet and the AST doesn’t look well for text nodes and comment nodes and might be changed in the future. You can write your own rules, but we don’t guarantee that the rules work with the next (minor) version of PMD without adjustments.

Please give us feedback about how practical this new language is in discussions. Please report missing features or bugs as new issues.

# New rules

## AvoidInlineStyles

The HTML rule AvoidInlineStyles finds elements which use a style attribute. In order to help maintaining a webpage it is considered good practice to separate content and styles. Instead of inline styles one should use CSS files and classes.

    <rule ref="category/html/bestpractices.xml/AvoidInlineStyles" />

## UnnecessaryTypeAttribute

The HTML rule UnnecessaryTypeAttribute finds “link” and “script” elements which still have a “type” attribute. This is not necessary anymore since modern browsers automatically use CSS and JavaScript.

      <rule ref="category/html/bestpractices.xml/UnnecessaryTypeAttribute" />

## UseAltAttributeForImages

The HTML rule UseAltAttributeForImages finds “img” elements without an “alt” attribute. An alternate text should always be provided in order to help screen readers.

      <rule ref="category/html/bestpractices.xml/UseAltAttributeForImages" />

# Modified rules

    The Java rule UnusedPrivateField has a new property ignoredFieldNames. The default ignores serialization-specific fields (eg serialVersionUID). The property can be used to ignore more fields based on their name. Note that the rule used to ignore fields named IDENT, but doesn’t anymore (add this value to the property to restore the old behaviour).

# Fixed Issues

## core
- #3792: [core] Allow to filter violations in Report
- #3881: [core] SARIF renderer depends on platform default encoding
- #3882: [core] Fix AssertionError about exhaustive switch
- #3884: [core] XML report via ant task contains XML header twice
- #3896: [core] Fix ast-dump CLI when reading from stdin
## doc
- #2505: [doc] Improve side bar to show release date
## java
- #3068: [java] Some tests should not depend on real rules
- #3889: [java] Catch LinkageError in UselessOverridingMethodRule
## java-bestpractices
- #3910: [java] UnusedPrivateField - Allow the ignored fieldnames to be configurable
- #1185: [java] ArrayIsStoredDirectly false positive with field access
- #1474: [java] ArrayIsStoredDirectly false positive with method call
- #3879 [java] ArrayIsStoredDirectly reports duplicated violation
- #3929: [java] ArrayIsStoredDirectly should report the assignment rather than formal parameter
## java-design
- #3603: [java] SimplifiedTernary: no violation for ‘condition ? true : false’ case
## java-performance
- #3867: [java] UseArraysAsList with method call
## plsql
- #3687: [plsql] Parsing exception EXECUTE IMMEDIATE l_sql BULK COLLECT INTO statement
- #3706: [plsql] Parsing exception CURSOR statement with parenthesis groupings

# External Contributions

- #3883: [doc] Improve side bar by Adding Release Date - @jasonqiu98
- #3910: [java] UnusedPrivateField - Allow the ignored fieldnames to be configurable - @laoseth
- #3928: [plsql] Fix plsql parsing error in parenthesis groups - @LiGaOg
- #3935: [plsql] Fix parser exception in EXECUTE IMMEDIATE BULK COLLECT #3687 - @Scrsloota
- #3938: [java] Modify SimplifiedTernary to meet the missing case #3603 - @VoidxHoshi
- #3943: chore: Set permissions for GitHub actions - @naveensrinivasan
