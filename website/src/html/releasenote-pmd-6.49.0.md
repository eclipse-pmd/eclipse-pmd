# Fixed Issues

## apex
- #4096: [apex] ApexAssertionsShouldIncludeMessage and ApexUnitTestClassShouldHaveAsserts: support new Assert class (introduced with Apex v56.0)

## core
- #3970: [core] FileCollector.addFile ignores language parameter

## java-codestyle
- #4082: [java] UnnecessaryImport false positive for on-demand imports of nested classes

# External Contributions

- #4081: [apex] Remove Jorje leaks outside ast package - @eklimo
- #4083: [java] UnnecessaryImport false positive for on-demand imports of nested classes (fix for #4082) - @abyss638
- #4092: [apex] Implement ApexQualifiableNode for ASTUserEnum - @aaronhurst-google
- #4095: [core] CPD: Added begin and end token to XML reports - @pacvz
- #4097: [apex] ApexUnitTestClassShouldHaveAssertsRule: Support new Assert class (Apex v56.0) - @tprouvot
- #4104: [doc] Add MegaLinter in the list of integrations - @nvuillam
