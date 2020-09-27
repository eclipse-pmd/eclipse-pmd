# Fixed Issues

## cpd
- #2758: [cpd] Improve AnyTokenizer
- #2760: [cpd] AnyTokenizer doesn’t count columns correctly
## apex-security
- #2774: [apex] ApexSharingViolations does not correlate sharing settings with class that contains data access
## java
- #2738: [java] Custom rule with @ExhaustiveEnumSwitch throws NPE
- #2755: [java] [6.27.0] Exception applying rule CloseResource on file … java.lang.NullPointerException
- #2756: [java] TypeTestUtil fails with NPE for anonymous class
- #2767: [java] IndexOutOfBoundsException when parsing an initializer BlockStatement
- #2783: [java] Error while parsing with lambda of custom interface
## java-bestpractices
- #2759: [java] False positive in UnusedAssignment
## java-design
- #2708: [java] False positive FinalFieldCouldBeStatic when using lombok Builder.Default

# External Contributions

- #2735: [ci] Add github actions for a fast view of pr succeed/not - XenoAmess
- #2747: [java] Don’t trigger FinalFieldCouldBeStatic when field is annotated with lombok @Builder.Default - Ollie Abbey
- #2773: [java] issue-2738: Adding null check to avoid npe when switch case is default - Nimit Patel
- #2789: Add badge for reproducible build - Dan Rollo
- #2791: [apex] Analyze inner classes for sharing violations - Jeff Bartolotta
