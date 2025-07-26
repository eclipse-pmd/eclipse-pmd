#New and noteworthy

## Java 25 Support

This release of PMD brings support for Java 25.

There are the following new standard language features:

- JEP 511: Module Import Declarations
- JEP 512: Compact Source Files and Instance Main Methods
- JEP 513: Flexible Constructor Bodies

# Fixed Issues

## core

- #4328: [ci] Improve Github Actions Workflows
- #5597: [core] POM Incompatibility with Maven 4

## java

- #5344: [java] IllegalArgumentException: Invalid type reference for method or ctor type annotation: 16
- #5478: [java] Support Java 25

## java-codestyle

- #5892: [java] ShortVariable false positive for java 22 unnamed variable _

## java-design

- #5858: [java] FinalFieldCouldBeStatic false positive for array initializers

## java-errorprone

- #2862: [java] New Rules: Avoid java.util.Date and Calendar classes

# Merged pull requests

- #5733: [css] Add new CPD language - Thomas Prouvot (@tprouvot)
- #5859: Fix #5858: [java] Fix false positive in FinalFieldCouldBeStatic for array initializers - Zbynek Konecny (@zbynek)
- #5872: [java] Add Support for Java 25 - Andreas Dangel (@adangel)
- #5876: chore: license header cleanup - Andreas Dangel (@adangel)
- #5883: Fix #2862: [java] Add rules discouraging the use of java.util.Calendar and java.util.Date - UncleOwen (@UncleOwen)
- #5893: chore: Fix Mockito javaagent warning for Java 21+ - Andreas Dangel (@adangel)
- #5894: chore: Fix JUnit warning about invalid test factory - Andreas Dangel (@adangel)
- #5895: Fix #5597: Move dogfood profile to separate settings.xml - Andreas Dangel (@adangel)
- #5899: Fix #5344: [java] Just log invalid annotation target type - Andreas Dangel (@adangel)
- #5909: [ci] Create a pre-release for snapshot builds - Andreas Dangel (@adangel)
- #5911: [doc] Reference CPD Capable Languages in CPD CLI docu - Andreas Dangel (@adangel)
- #5914: Fix #5892: [java] ShortVariable FP for java 22 Unnamed Variable - Lukas Gräf (@lukasgraef)
- #5918: chore: [cli] Improve symbolic link tests for Windows - Andreas Dangel (@adangel)
- #5920: chore: [scala] Fix javadoc config - Andreas Dangel (@adangel)
