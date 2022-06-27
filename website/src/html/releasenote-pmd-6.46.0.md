# New Rules

This release ships with 2 new Java rules.

## EmptyControlStatement

EmptyControlStatement reports many instances of empty things, e.g. control statements whose body is empty, as well as empty initializers.
EmptyControlStatement also works for empty for and do loops, while there were previously no corresponding rules.
This new rule replaces the rules EmptyFinallyBlock, EmptyIfStmt, EmptyInitializer, EmptyStatementBlock, EmptySwitchStatements, EmptySynchronizedBlock, EmptyTryBlock, and EmptyWhileStmt.

    <rule ref="category/java/codestyle.xml/EmptyControlStatement"/>

The rule is part of the quickstart.xml ruleset.

## UnnecessarySemicolon

UnnecessarySemicolon reports semicolons that are unnecessary (so called “empty statements” and “empty declarations”).
This new rule replaces the rule EmptyStatementNotInLoop.

    <rule ref="category/java/codestyle.xml/UnnecessarySemicolon"/>

The rule is part of the quickstart.xml ruleset.

# Deprecated Rules

The following Java rules are deprecated and removed from the quickstart ruleset, as the new rule EmptyControlStatement merges their functionality:

- EmptyFinallyBlock
- EmptyIfStmt
- EmptyInitializer
- EmptyStatementBlock
- EmptySwitchStatements
- EmptySynchronizedBlock
- EmptyTryBlock
- EmptyWhileStmt

The Java rule EmptyStatementNotInLoop is deprecated and removed from the quickstart ruleset. Use the new rule UnnecessarySemicolon instead.

# Fixed Issues

## cli
- #1445: [core] Allow CLI to take globs as parameters

## core
- #2352: [core] Deprecate <lang>-<ruleset> hyphen notation for ruleset references
- #3787: [core] Internalize some methods in Ant Formatter
- #3835: [core] Deprecate system properties of CPDCommandLineInterface
- #3942: [core] common-io path traversal vulnerability (CVE-2021-29425)

## cs (c#)
- #3974: [cs] Add option to ignore C# attributes (annotations)

## go
- #2752: [go] Error parsing unicode values

## html
- #3955: [html] Improvements for handling text and comment nodes
- #3978: [html] Add additional file extensions htm, xhtml, xht, shtml

## java
- #3423: [java] Error processing identifiers with Unicode

## java-bestpractices
- #3954: [java] NPE in UseCollectionIsEmptyRule when .size() is called in a record

## java-design
- #3874: [java] ImmutableField reports fields annotated with @Autowired (Spring) and @Mock (Mockito)

## java-errorprone
- #3096: [java] EmptyStatementNotInLoop FP in 6.30.0 with IfStatement

## java-performance
- #3379: [java] UseArraysAsList must ignore primitive arrays
- #3965: [java] UseArraysAsList false positive with non-trivial loops

## javascript
- #2605: [js] Support unicode characters
- #3948: [js] Invalid operator error for method property in object literal

## python
- #2604: [python] Support unicode identifiers

# External Contributions

- #3961: [java] Fix #3954 - NPE in UseCollectionIsEmptyRule with record - @flyhard
- #3964: [java] Fix #3874 - ImmutableField: fix mockito/spring false positives - @lukelukes
- #3974: [cs] Add option to ignore C# attributes (annotations) - @maikelsteneker