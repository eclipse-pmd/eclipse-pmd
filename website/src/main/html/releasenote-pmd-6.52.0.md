# New Rules

The new Java rule InvalidJavaBean identifies beans, that don’t follow the JavaBeans API specification, like beans with missing getters or setters.

<rule ref="category/java/design.xml/InvalidJavaBean"/>

# Renamed rules

The Java rule `BeanMembersShouldSerialize` has been renamed to `NonSerializableClass`. It has been revamped to only check for classes that are marked with Serializable and reports each field in it, that is not serializable.

The property `prefix` has been deprecated, since in a serializable class all fields have to be serializable regardless of the name.


# Modified rules

The rule `ClassNamingConventions` has a new property `testClassPattern`, which is applied to test classes. By default, test classes should end with the suffix “Test”. Test classes are top-level classes, that either inherit from JUnit 3 TestCase or have at least one method annotated with the Test annotations from JUnit4/5 or TestNG.

The property `ignoredAnnotations` of rule ImmutableField has been deprecated and doesn’t have any effect anymore. Since PMD 6.47.0, the rule only considers fields, that are initialized once and never changed. If the field is just declared but never explicitly initialized, it won’t be reported. That’s the typical case when a framework sets the field value by reflection. Therefore, the property is not needed anymore. If there is a special case where this rule misidentifies fields as immutable, then the rule should be suppressed for these fields explicitly.

# Fixed Issues

## cli
- #4215: NullPointerException when trying to open designer
## doc
- #4207: [doc] List all languages in rule doc
## java
- #3643: [java] More parser edge cases
- #4152: [java] Parse error on array type annotations
## java-codestyle
- #2867: [java] Separate pattern for test classes in ClassNamingConventions rule for Java
- #4201: [java] CommentDefaultAccessModifier should consider lombok’s @Value
## java-design
- #4175: [java] ImmutableField - deprecate property ignoredAnnotations
- #4177: [java] New Rule InvalidJavaBean
- #4188: [java] ClassWithOnlyPrivateConstructorsShouldBeFinal false positive with Lombok’s @NoArgsConstructor
- #4189: [java] AbstractClassWithoutAnyMethod should consider lombok’s @AllArgsConstructor
- #4200: [java] ClassWithOnlyPrivateConstructorsShouldBeFinal should consider lombok’s @Value
## java-errorprone
- #1668: [java] BeanMembersShouldSerialize is extremely noisy
- #4172: [java] InvalidLogMessageFormat false positive on externally formatted strings
- #4174: [java] MissingStaticMethodInNonInstantiatableClass does not consider nested builder class
- #4176: [java] Rename BeanMembersShouldSerialize to NonSerializableClass
- #4185: [java] InvalidLogMessageFormat rule produces a NPE
- #4224: [java] MissingStaticMethodInNonInstantiatableClass should consider Lombok’s @UtilityClass
- #4225: [java] MissingStaticMethodInNonInstantiatableClass should consider Lombok’s @NoArgsConstructor
## java-performance
- #4183: [java] AvoidArrayLoops regression: from false negative to false positive with final variables

# External Contributions

- #4184: [java][doc] TestClassWithoutTestCases - fix small typo in description - Valery Yatsynovich (@valfirst)
- #4198: [doc] Add supported CPD languages - Jeroen van Wilgenburg (@jvwilge)
- #4202: [java] Fix #4200 and #4201: ClassWithOnlyPrivateConstructorsShouldBeFinal, CommentDefaultAccessModifier: Exclude lombok @Value annotation - Lynn (@LynnBroe)
- #4205: [doc] Clarify Scala support (no built-in rules) - Eldrick Wega (@Eldrick19)
- #4226: [visualforce] Replace uses of Jorje types in pmd-visualforce - Aaron Hurst (@aaronhurst-google)
- #4227: [java] Fix #4225 MissingStaticMethodInNonInstantiatableClass: Exclude lombok’s @NoArgsConstructor annotation - Lynn (@LynnBroe)
- #4228: [java] Fix #4224 MissingStaticMethodInNonInstantiatableClass: Exclude lombok’s UtilityClass - Lynn (@LynnBroe)
- #4232: [doc] Fixing typos - Andreas Deininger (@deining)
