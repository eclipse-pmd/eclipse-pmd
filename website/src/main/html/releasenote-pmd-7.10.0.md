# New and changed rules

## New Rules

The new Java rule `ExhaustiveSwitchHasDefault` finds switch statements and
expressions, that cover already all cases but still have a default case. This default case is unnecessary
and prevents getting compiler errors when e.g. new enum constants are added without extending the switch.

# Fixed Issues

## apex

- #5388: [apex] Parse error with time literal in SOQL query
- #5456: [apex] Issue with java dependency apex-parser-4.3.1 but apex-parser-4.3.0 works

## apex-security

- #3158: [apex] ApexSuggestUsingNamedCred false positive with Named Credential merge fields

## java-performance

- #5311: [java] TooFewBranchesForSwitch false positive for exhaustive switches over enums without default case

# Merged pull requests

- #5327: [apex] Update apex-parser and summit-ast - Andreas Dangel (@adangel)
- #5412: [java] Support exhaustive switches - Andreas Dangel (@adangel)
- #5449: Use new gpg key (A0B5CA1A4E086838) - Andreas Dangel (@adangel)
- #5458: [doc] Move Wiki pages into main documentation, cleanups - Andreas Dangel (@adangel)
- #5471: [java] Support Java 24 - Andreas Dangel (@adangel)
- #5488: [apex] Fix #3158: Recognize Named Credentials merge fields in ApexSuggestUsingNamedCredRule - William Brockhus (@YodaDaCoda)
