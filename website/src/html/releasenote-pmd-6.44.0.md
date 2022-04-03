# New and noteworthy️

## Java 18 Support️

This release of PMD brings support for Java 18. There are no new standard language features.

Note: Support for Java 16 preview language features have been removed. The version “16-preview” is no longer available.
Better XML XPath support️

## XPath Rules

The new rule class DomXPathRule is intended to replace usage of the XPathRule for XML rules. This rule executes the XPath query in a different way, which sticks to the XPath specification. This means the expression is interpreted the same way in PMD as in all other XPath development tools that stick to the standard. You can for instance test the expression in an online XPath editor.

Prefer using this class to define XPath rules: replace the value of the class attribute with net.sourceforge.pmd.lang.xml.rule.DomXPathRule like so:

    <rule name="MyXPathRule"
          language="xml"
          message="A message"
          class="net.sourceforge.pmd.lang.xml.rule.DomXPathRule">
          <properties>
            <property name="xpath">
                <value><![CDATA[
                /a/b/c[@attr = "5"]
                ]]></value>
            </property>
            <!-- Note: the property "version" is ignored, remove it. The query is XPath 2. -->
          </properties>
    </rule>

The rule is more powerful than XPathRule, as it can now handle XML namespaces, comments and processing instructions. Please refer to the Javadoc of DomXPathRule for information about the differences with XPathRule and examples.

XPathRule is still perfectly supported for all other languages, including Apex and Java.
New XPath functions ✏️️

The new XPath functions pmd:startLine, pmd:endLine, pmd:startColumn, and pmd:endColumn are now available in XPath rules for all languages. They replace the node attributes @BeginLine, @EndLine and such. These attributes will be deprecated in a future release.

Please refer to the documentation of these functions for more information, including usage samples.

Note that the function pmd:endColumn returns an exclusive index, while the attribute @EndColumn is inclusive. This is for forward compatibility with PMD 7, which uses exclusive end indices.
New programmatic API ✏️️

    This release introduces a new programmatic API to replace the inflexible PMD class. Programmatic execution of PMD should now be done with a PMDConfiguration and a PmdAnalysis, for instance:
    
    PMDConfiguration config = new PMDConfiguration();
    config.setDefaultLanguageVersion(LanguageRegistry.findLanguageByTerseName("java").getVersion("11"));
    config.setInputPaths("src/main/java");
    config.prependAuxClasspath("target/classes");
    config.setMinimumPriority(RulePriority.HIGH);
    config.addRuleSet("rulesets/java/quickstart.xml");
    config.setReportFormat("xml");
    config.setReportFile("target/pmd-report.xml");
    
    try (PmdAnalysis pmd = PmdAnalysis.create(config)) {
        // note: don't use `config` once a PmdAnalysis has been created.
        // optional: add more rulesets
        pmd.addRuleSet(pmd.newRuleSetLoader().loadFromResource("custom-ruleset.xml"));
        // optional: add more files
        pmd.files().addFile(Paths.get("src", "main", "more-java", "ExtraSource.java"));
        // optional: add more renderers
        pmd.addRenderer(renderer);
    
        // or just call PMD
        pmd.performAnalysis();
    }


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

# External Contributions ✏️️

- #3773: [apex] EagerlyLoadedDescribeSObjectResult false positives with SObjectField.getDescribe() - @filiprafalowicz
- #3811: [doc] Improve “Edit me on github” button - @btjiong
- #3836: [doc] Make TOC scrollable when too many subheadings - @JerritEic

