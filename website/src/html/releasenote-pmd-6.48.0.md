# Fixed Issues

## apex
- #4056: [apex] ApexSOQLInjection: Add support count query

## core
- #3796: [core] CPD should also provide a --debug flag
- #4021: [core] CPD: Add total number of tokens to XML reports
- #4031: [core] If report is written to stdout, stdout should not be closed
- #4051: [doc] Additional rulesets are not listed in documentation
- #4053: [core] Allow building PMD under Java 18+

## java
- #4015: [java] Support JDK 19

## java-bestpractices
- #3455: [java] WhileLoopWithLiteralBoolean - false negative with complex expressions

## java-design
- #3729: [java] TooManyMethods ignores “real” methods which are named like getters or setters
- #3949: [java] FinalFieldCouldBeStatic - false negative with unnecessary parenthesis

## java-performance
- #3625: [java] AddEmptyString - false negative with empty var

## lua
- #4061: [lua] Fix several related Lua parsing issues found when using CPD

# External Contributions

- #3984: [java] Fix AddEmptyString false-negative issue - @LiGaOg
- #3988: [java] Modify WhileLoopWithLiteralBoolean to meet the missing case #3455 - @VoidxHoshi
- #3992: [java] FinalFieldCouldBeStatic - fix false negative with unnecessary parenthesis - @dalizi007
- #3994: [java] TooManyMethods - improve getter/setter detection (#3729) - @341816041
- #4017: Add Gherkin support to CPD - @ASBrouwers
- #4021: [core] CPD: Add total number of tokens to XML reports - @maikelsteneker
- #4056: [apex] ApexSOQLInjection: Add support count query - @gwilymatgearset
- #4061: [lua] Fix several related Lua parsing issues found when using CPD - @matthargett
