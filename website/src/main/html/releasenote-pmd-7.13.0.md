# New Rules

The new Apex rule `TypeShadowsBuiltInNamespace` finds Apex classes, enums, and interfaces that have the same name as a class, enum, or interface in the System or Schema namespace. Shadowing these namespaces in this way can lead to confusion and unexpected behavior.

# Fixed Issues

## core

- #5438: [core] Support language dialects
- #5448: Maintain a public PMD docker image
- #5525: [core] Add rule priority as level to Sarif report
- #5623: [dist] Make pmd launch script compatible with /bin/sh

## apex-bestpractices

- #5667: [apex] ApexUnitTestShouldNotUseSeeAllDataTrue false negative when seeAllData parameter is a string

## apex-errorprone

- #3184: [apex] Prevent classes from shadowing System Namespace

## java

- #5645: [java] Parse error on switch with yield

## java-bestpractices

- #5687: [java] UnusedPrivateMethodRule: exclude serialization method readObjectNoData()

## plsql

- #5675: [plsql] Parse error with TREAT function

# Merged pull requests

- #5438: [core] Support language dialects - Juan Martín Sotuyo Dodero (@jsotuyod)
- #5450: Fix #3184: [apex] New Rule: TypeShadowsBuiltInNamespace - Mitch Spano (@mitchspano)
- #5573: Fix #5525: [core] Add Sarif Level Property - julees7 (@julees7)
- #5623: [dist] Make pmd launch script compatible with /bin/sh - Clément Fournier (@oowekyala)
- #5648: Fix #5645: [java] Parse error with yield statement - Clément Fournier (@oowekyala)
- #5652: [java] Cleanup AccessorClassGenerationRule implementation - Pankraz76 (@Pankraz76)
- #5672: [doc] Fix its/it’s and doable/double typos - John Jetmore (@jetmore)
- #5674: Fix #5448: [ci] Maintain public Docker image - Andreas Dangel (@adangel)
- #5684: Fix #5667: [apex] ApexUnitTestShouldNotUseSeeAllDataTrue false negative when seeAllDate parameter is a string - Thomas Prouvot (@tprouvot)
- #5685: [doc] typo fix in PMD Designer reference - Douglas Griffith (@dwgrth)
- #5686: Fix #5675: [plsql] Support TREAT function with specified datatype - Andreas Dangel (@adangel)
- #5687: [java] UnusedPrivateMethodRule: exclude serialization method readObjectNoData() - Gili Tzabari (@cowwoc)
- #5688: [java] Fix Double Literal for Java19+ compatibility - Andreas Dangel (@adangel)
