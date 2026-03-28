# New and Changed Rules

## New Rules

The new Java rule UnnecessaryInterfaceDeclaration detects classes that
implement interfaces that are already implemented by its superclass, and interfaces
that extend other interfaces already declared by their superinterfaces.
These declarations are redundant and can be removed to simplify the code.

## Changed Rules

The rule CloseResource introduces a new property, allowedResourceMethodPatterns,
which lets you specify method invocation patterns whose return values are resources managed externally.
This is useful for ignoring managed resources - for example, Reader/Writer instances obtained from
HttpServletRequest/HttpServletResponse - because the servlet container, not application code,
is responsible for closing them. By default, the rule ignores InputStream/OutputStream/Reader/Writer
resources returned by methods on (Http)ServletRequest and (Http)ServletResponse
(both javax.servlet and jakarta.servlet).

# Fixed Issues

## core

- #6471: [core] BaseAntlrTerminalNode should return type instead of index for getTokenKind()
- #6475: [core] Fix stored XSS in VBHTMLRenderer and YAHTMLRenderer

## doc

- #6396: [doc] Mention test-pmd-tool as alternative for testing

## java-bestpractices

- #6431: [java] UnitTestShouldIncludeAssert: False positive with SoftAssertionsExtension on parent/grandparent classes

## java-codestyle

- #6458: [java] New Rule: UnnecessaryInterfaceDeclaration

## java-errorprone

- #5787: [java] InvalidLogMessageFormat: False positive with lombok @Value generated methods
- #6436: [java] CloseResource: Allow to ignore managed resources

# Merged pull requests

- #6396: [doc] Mention test-pmd-tool as alternative for testing - Beech Horn (@metalshark)
- #6397: [java] Add support for Lombok-generated getters in symbol resolution - Anurag Agarwal (@altaiezior)
- #6420: [ci] build: Add typos as spell checker - Andreas Dangel (@adangel)
- #6432: [java] UnitTestShouldIncludeAssert: False positive with SoftAssertionsExtension on parent/grandparent classes - Artur Kalimullin (@kaliy)
- #6434: [java] chore(style): Fix lambda argument indentation for checkstyle compliance - Kai (@aclfe)
- #6437: [java] CloseResource: Allow to ignore managed resources - Gildas Cuisinier (@gcuisinier)
- #6445: chore: Fix FieldNamingConventions - Andreas Dangel (@adangel)
- #6446: [doc] Add new IntelliJ Plugin "PMD X" - Andreas Dangel (@adangel)
- #6447: chore: Small release process fixes - Andreas Dangel (@adangel)
- #6458: [java] New Rule: UnnecessaryInterfaceDeclaration - Zbynek Konecny (@zbynek)
- #6472: [core] Fix BaseAntlrTerminalNode getTokenKind to return type instead of index - Peter Paul Bakker (@stokpop)
- #6475: [core] Fix stored XSS in VBHTMLRenderer and YAHTMLRenderer - Andreas Dangel (@adangel)
