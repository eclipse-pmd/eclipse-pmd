# Fixed Issues

## core
- #3999: [cli] All files are analyzed despite parameter --file-list
- #4009: [core] Cannot build PMD with Temurin 17

## java-bestpractices
- #3824: [java] UnusedPrivateField: Do not flag fields annotated with @Version
- #3825: [java] UnusedPrivateField: Do not flag fields annotated with @Id or @EmbeddedId

## java-design
- #3823: [java] ImmutableField: Do not flag fields in @Entity
- #3981: [java] ImmutableField reports fields annotated with @Value (Spring)
- #3998: [java] ImmutableField reports fields annotated with @Captor (Mockito)
- #4004: [java] ImmutableField reports fields annotated with @GwtMock (GwtMockito) and @Spy (Mockito)
- #4008: [java] ImmutableField not reporting fields that are only initialized in the declaration
- #4011: [java] ImmutableField: Do not flag fields annotated with @Inject
- #4020: [java] ImmutableField reports fields annotated with @FindBy and @FindBys (Selenium)

## java-errorprone
- #3936: [java] AvoidFieldNameMatchingMethodName should consider enum class
- #3937: [java] AvoidDuplicateLiterals - uncompilable test cases

# External Contributions

- #3985: [java] Fix false negative problem about Enum in AvoidFieldNameMatchingMethodName #3936 - @Scrsloota
- #3993: [java] AvoidDuplicateLiterals - Add the method “buz” definition to test cases - @dalizi007
- #4002: [java] ImmutableField - Ignore fields annotated with @Value (Spring) or @Captor (Mockito) - @jjlharrison
- #4003: [java] UnusedPrivateField - Ignore fields annotated with @Id/@EmbeddedId/@Version (JPA) or @Mock/@Spy/@MockBean (Mockito/Spring) - @jjlharrison
- #4006: [doc] Fix eclipse plugin update site URL - @shiomiyan
- #4010: [core] Bump kotlin to version 1.7.0 - @maikelsteneker
