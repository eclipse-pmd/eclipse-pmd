# Fixed Issues

## apex
- #5138: [apex] Various false-negatives since 7.3.0 when using triggers (ApexCRUDViolation, CognitiveComplexity, OperationWithLimitsInLoop)
- #5163: [apex] Parser error when using toLabel in SOSL query
- #5182: [apex] Parser error when using GROUPING in a SOQL query
- #5218: [apex] Parser error when using nested subqueries in SOQL
- #5228: [apex] Parser error when using convertCurrency() in SOQL

## core
- #5059: [core] xml output doesn’t escape CDATA inside its own CDATA
- #5201: [core] PMD sarif schema file points to nonexistent location
- #5222: [core] RuleReference/RuleSetWriter don’t handle changed default property values correctly
- #5229: [doc] CLI flag --show-suppressed needs to mention xml, html, summaryhtml

## java
- #5190: [java] NPE in type inference

## java-codestyle
- #5046: [java] LocalVariableCouldBeFinal false positive with try/catch

## java-errorprone
- #5068: [java] MissingStaticMethodInNonInstantiatableClass: false positive with builder pattern
- #5207: [java] CheckSkipResult: false positve for a private method void skip(int) in a subclass of FilterInputStream

# Merged pull requests

- #5186: [java] Cleanup things about implicit classes - Clément Fournier (@oowekyala)
- #5188: [apex] Use new apex-parser 4.2.0 - Andreas Dangel (@adangel)
- #5191: [java] Fix #5046 - FPs in LocalVariableCouldBeFinal - Clément Fournier (@oowekyala)
- #5192: [java] Fix #5190 - NPE in type inference caused by null type - Clément Fournier (@oowekyala)
- #5195: [apex] Fix various FNs when using triggers - Andreas Dangel (@adangel)
- #5202: [core] Sarif format: refer to schemastore.org - David Schach (@dschach)
- #5208: [doc] Added Codety to “Tools / Integrations” - Tony (@random1223)
- #5210: [core] Fix PMD’s XMLRenderer to escape CDATA - Andreas Dangel (@adangel)
- #5211: Change branch master to main - Andreas Dangel (@adangel)
- #5212: [java] Adjust signature matching in CheckSkipResultRule - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5223: [core] Fix RuleReference / RuleSetWriter handling of properties - Andreas Dangel (@adangel)
- #5224: [java] Fix #5068: Class incorrectly identified as non-instantiatable - Lukas Gräf (@lukasgraef)
- #5230: [doc] Documentation update for –show-suppressed flag - David Schach (@dschach)
- #5237: [apex] Support convertCurrency() in SOQL/SOSL - Andreas Dangel (@adangel)
