# New rules

The new Java rule FinalParameterInAbstractMethod detects parameters that are declared as final in interfaces or abstract methods. Declaring the parameters as final is useless because the implementation may choose to not respect it.
<rule ref="category/java/codestyle.xml/FinalParameterInAbstractMethod" />
The rule is part of the quickstart.xml ruleset.

# Modified rules

The Apex rule ApexDoc has a new property reportProperty. If set to false (default is true if unspecified) doesn’t report missing ApexDoc comments on properties. It allows you to enforce ApexDoc comments for classes and methods without requiring them for properties.

# Fixed Issues

## core
- #3328: [core] designer.bat errors when JAVAFX_HOME contains spaces
## java
- #3698: [java] Error resolving Symbol Table
## java-bestpractices
- #3209: [java] UnusedPrivateMethod false positive with static method and cast expression
- #3468: [java] UnusedPrivateMethod false positive when outer class calls private static method on inner class
## java-design
- #3679: [java] Make FinalFieldCouldBeStatic detect constant variable
## java-errorprone
- #3644: [java] InvalidLogMessageFormat: false positives with logstash structured logging
- #3686: [java] ReturnEmptyCollectionRatherThanNull - false negative with conditioned returns
- #3701: [java] MissingStaticMethodInNonInstantiatableClass false positive with method inner classes
- #3721: [java] ReturnEmptyCollectionRatherThanNull - false positive with stream and lambda
## java-performance
- #3492: [java] UselessStringValueOf: False positive when there is no initial String to append to
- #3639: [java] UseStringBufferLength: false negative with empty string variable
- #3712: [java] InsufficientStringBufferDeclaration false positive with StringBuilder.setLength(0)
## javascript
- #3703: [javascript] Error - no Node adapter class registered for XmlPropRef

# External Contributions

- #3631: [java] Fixed False positive for UselessStringValueOf when there is no initial String to append to - John Armgardt
- #3683: [java] Fixed 3468 UnusedPrivateMethod false positive when outer class calls private static method on inner class - John Armgardt
- #3688: [java] Bump log4j to 2.16.0 - Sergey Nuyanzin
- #3693: [apex] ApexDoc: Add reportProperty property - Steve Babula
- #3704: [java] Fix for #3686 - Fix ReturnEmptyCollectionRatherThanNull - Oleksii Dykov
- #3713: [java] Enhance UnnecessaryModifier to support records - Vincent Galloy
- #3719: [java] Upgrade log4j to 2.17.1 - Daniel Paul Searles
- #3720: [java] New rule: FinalParameterInAbstractMethod - Vincent Galloy
- #3724: [java] Fix for #3679 - fix FinalFieldCouldBeStatic - Oleksii Dykov
- #3727: [java] #3724 - fix FinalFieldCouldBeStatic: triggers only if the referenced name is static - Oleksii Dykov
- #3742: [java] Fix #3701 - fix MissingStaticMethodInNonInstantiatableClass for method local classes - Oleksii Dykov
- #3744: [core] Updated SaxonXPathRuleQueryTest.java - Vyom Yadav
- #3745: [java] Fix #3712: InsufficientStringBufferDeclaration setLength false positive - Daniel Gredler
- #3747: [visualforce] Updated DataType.java - Vyom Yadav
