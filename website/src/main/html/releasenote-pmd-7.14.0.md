# New and noteworthy

## New Rule UnnecessaryWarningSuppression (experimental)

This new Java rule `UnnecessaryWarningSuppression` reports unused suppression annotations and comments. Violations of this rule cannot be suppressed.

How to use it? Just include it in your ruleset:

```xml
<rule ref="category/java/bestpractices.xml/UnnecessaryWarningSuppression" />
```

Note: This rule is currently experimental. It is available for now only for Java. The rule for now only reports annotations specific to PMD, like `@SuppressWarnings("PMD")`. In the future we might be able to check for other common ones like `@SuppressWarnings("unchecked")` or "fallthrough". Since violations of this rule cannot be suppressed, we opted here on the side of false-negatives and don’t report every unused case yet. However, suppressing specific PMD rules is working as expected.

# Fixed Issues

## core

- #648: [core] Warn on unneeded suppression
- #5700: [core] Don’t accidentally catch unexpected runtime exceptions in CpdAnalysis
- #5705: [cli] PMD’s start script fails if PMD_HOME is set

## java-bestpractices

- #5061: [java] UnusedLocalVariable false positive when variable is read as side effect of an assignment
- #5621: [java] UnusedPrivateMethod with method ref
- #5724: [java] ImplicitFunctionalInterface should not be reported on sealed interfaces

## java-codestyle

- #2462: [java] LinguisticNaming must ignore setters that returns current type (Builder pattern)
- #5634: [java] CommentDefaultAccessModifier doesn’t recognize /* package */ comment at expected location for constructors

## java-design

- #5568: [java] High NPathComplexity in switch expression
- #5647: [java] NPathComplexity does not account for returns

## java-errorprone

- #5702: [java] InvalidLogMessageFormat: Lombok @Slf4j annotation is not interpreted by PMD

## java-performance

- #5711: [java] UseArraysAsList false positive with Sets

## visualforce

- #5476: [visualforce] NPE when analyzing standard field references in visualforce page

# Merged pull requests

- #5584: [ci] New workflow “Publish Snapshot” - Andreas Dangel (@adangel)
- #5599: [java] Rewrite NPath complexity metric - Clément Fournier (@oowekyala)
- #5609: [core] Add rule to report unnecessary suppression comments/annotations - Clément Fournier (@oowekyala)
- #5699: Fix #5702: [java] First-class support for lombok @Slf4j - Clément Fournier (@oowekyala)
- #5700: [core] Don’t accidentally catch unexpected runtime exceptions in CpdAnalysis - Elliotte Rusty Harold (@elharo)
- #5712: Fix #5711: [java] UseArrayAsList - only consider List.add - Andreas Dangel (@adangel)
- #5715: Fix #5476: [visualforce] Resolve data types of standard object fields - Andreas Dangel (@adangel)
- #5716: Fix #5634: [java] CommentDefaultAccessModifier: Comment between annotation and constructor not recognized - Lukas Gräf (@lukasgraef)
- #5726: Fix #5724: [java] Implicit functional interface FP with sealed interface - Clément Fournier (@oowekyala)
- #5727: Fix #5621: [java] Fix FPs with UnusedPrivateMethod - Clément Fournier (@oowekyala)
- #5728: [ci] Improvements for “Publish Pull Requests” - Andreas Dangel (@adangel)
- #5730: [ci] Refactor git-repo-sync - Andreas Dangel (@adangel)
- #5731: [cli] Share more CLI options between CPD and PMD - Clément Fournier (@oowekyala)
- #5736: Fix #5061: [java] UnusedLocalVariable FP when using compound assignment - Lukas Gräf (@lukasgraef)
- #5741: [cli] Make CLI default to multithreaded - Clément Fournier (@oowekyala)
- #5742: [ci] publish-snapshot/old build: migrate to central portal - Andreas Dangel (@adangel)
- #5743: [ci] Make build a reuseable workflow - Andreas Dangel (@adangel)
- #5744: Fix #5705: [cli] Always determine PMD_HOME based on script location - Andreas Dangel (@adangel)
- #5748: [core] Reformat SarifLog to comply to coding standards - Andreas Dangel (@adangel)
- #5763: [java] Support annotated constructor return type in symbol API - Clément Fournier (@oowekyala)
- #5764: Fix #2462: [java] LinguisticNaming should ignore setters for Builders - Lukas Gräf (@lukasgraef)
