# Modified Rules

The Apex rule ApexDoc has two new properties: reportPrivate and
reportProtected. Previously the rule only considered public and global classes, methods, and
properties. With these properties, you can verify the existence of ApexDoc comments for private
and protected methods as well. By default, these properties are disabled to preserve backwards
compatible behavior.

# Fixed Issues

## apex-documentation
- #3075: [apex] ApexDoc should support private access modifier
## java
- #3101: [java] NullPointerException when running PMD under JRE 11
## java-bestpractices
- #3132: [java] UnusedImports with static imports on subclasses
## java-errorprone
- #2716: [java] CompareObjectsWithEqualsRule: False positive with Enums
- #3089: [java] CloseResource rule throws exception on spaces in property types
- #3133: [java] InvalidLogMessageFormat FP with StringFormattedMessage and ParameterizedMessage
## plsql
- #3106: [plsql] ParseException while parsing EXECUTE IMMEDIATE 'drop database link ' || linkname;

# External Contributions

- #3098: [apex] ApexDoc optionally report private and protected - Jonathan Wiesel
- #3107: [plsql] Fix ParseException for EXECUTE IMMEDIATE str1||str2; - hvbtup
- #3125: [doc] Fix sample code indentation in documentation - Artur Dryomov
