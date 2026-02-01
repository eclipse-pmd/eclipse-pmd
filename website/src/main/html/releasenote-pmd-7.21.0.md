# New and noteworthy

## Java 26 Support

This release of PMD brings support for Java 26.

# New and Changed Rules

## New Rules

- The new Java rule `PublicMemberInNonPublicType` detects public members (such as methods
  or fields) within non-public types. Non-public types should not declare public members, as their effective
  visibility is limited, and using the public modifier can create confusion.
- The new Java rule `UnsupportedJdkApiUsage` flags the use of unsupported and non-portable
  JDK APIs, including sun.* packages, sun.misc.Unsafe, and jdk.internal.misc.Unsafe. These APIs are unstable,
  intended for internal use, and may change or be removed. The rule complements Java compiler warnings by
  highlighting such usage during code reviews and encouraging migration to official APIs like VarHandle and
  the Foreign Function & Memory API.

## Changed Rules

The following rules have been changed to use a consistent implementation of enum based
rule properties:

- The property checkAddressTypes of rule AvoidUsingHardCodedIP has changed:  
  Instead of IPv4 use ipv4  
  Instead of IPv6 use ipv6  
  Instead of IPv4 mapped IPv6 use ipv4MappedIpv6  
  The old values still work, but you'll see a deprecation warning.
- The property nullCheckBranch of rule ConfusingTernary has changed:  
  Instead of Any use any  
  Instead of Then use then  
  Instead of Else use else  
  The old values still work, but you'll see a deprecation warning.
- The property typeAnnotations of rule ModifierOrder has changed:  
  Instead of ontype use onType  
  Instead of ondecl use onDecl  
  The old values still work, but you'll see a deprecation warning.
- The values of the properties of rule CommentRequired have changed:  
  Instead of Required use required  
  Instead of Ignored use ignored  
  Instead of Unwanted use unwanted  
  The old values still work, but you'll see a deprecation warning.

## Deprecated Rules

The Java rule `DontImportSun` has been deprecated. It is replaced by `UnsupportedJdkApiUsage`.

# Fixed Issues

## core

- #6184: [core] Consistent implementation of enum properties

## apex

- #6417: [apex] Support CPD suppression with "CPD-OFF" & "CPD-ON"

## apex-codestyle

- #6349: [apex] FieldDeclarationsShouldBeAtStart: False positive with properties

## cli

- #6290: [cli] Improve Designer start script

## java

- #5871: [java] Support Java 26
- #6364: [java] Parse error with yield lambda inside switch

## java-design

- #6231: [java] New Rule: PublicMemberInNonPublicType

## java-errorprone

- #3601: [java] InvalidLogMessageFormat: False positive when final parameter is Supplier<Throwable>
- #5882: [java] UnconditionalIfStatement: False negative when true/false is not literal but local variable
- #5923: [java] New Rule: Catch usages of sun.misc.Unsafe or jdk.internal.misc.Unsafe

## java-performance

- #3857: [java] InsufficientStringBufferDeclaration: False negatives with String constants

# Merged pull requests

- #6231: [java] New Rule: PublicMemberInNonPublicType - Andreas Dangel (@adangel)
- #6232: [java] New Rule: UnsupportedJdkApiUsage - Thomas Leplus (@thomasleplus)
- #6233: [core] Fix #6184: More consistent enum properties - Andreas Dangel (@adangel)
- #6290: [cli] Improve Designer start script - Andreas Dangel (@adangel)
- #6315: [java] Fix #5882: UnconditionalIfStatement false-negative if true/false is not literal - Marcel (@mrclmh)
- #6362: chore: Fix typos - Zbynek Konecny (@zbynek)
- #6366: [java] Fix #3857: InsufficientStringBufferDeclaration should consider constant Strings - Lukas Gräf (@lukasgraef)
- #6373: [java] Support Java 26 - Andreas Dangel (@adangel)
- #6377: [doc] chore: update last_updated - Andreas Dangel (@adangel)
- #6384: chore: helper script check-all-contributors.sh - Andreas Dangel (@adangel)
- #6386: [core] chore: Bump minimum Java version required for building to 21 - Andreas Dangel (@adangel)
- #6387: [ci] publish-pull-requests: download latest build result - Andreas Dangel (@adangel)
- #6389: chore: update javadoc deprecated tags - Andreas Dangel (@adangel)
- #6390: chore: update javadoc experimental tags - Andreas Dangel (@adangel)
- #6391: chore: update javadoc internal API tags - Andreas Dangel (@adangel)
- #6392: [doc] ADR 3: Clarify javadoc tags - Andreas Dangel (@adangel)
- #6394: [apex] Fix #6349: FieldDeclarationsShouldBeAtStart false positive with properties - Mohamed Hamed (@mdhamed238)
- #6407: [java] Fix #3601: InvalidLogMessageFormat: False positive when final parameter is Supplier - Lukas Gräf (@lukasgraef)
- #6417: [apex] Support CPD suppression with "CPD-OFF" & "CPD-ON" - Jade (@goto-dev-null)
- #6428: [ci] chore: run extensive integration tests under linux only - Andreas Dangel (@adangel)
- #6429: [doc] chore: add keywords for auxclasspath in Java documentation - Andreas Dangel (@adangel)
- #6430: [java] Fix #6364: Parse error with yield lambda - Andreas Dangel (@adangel)
