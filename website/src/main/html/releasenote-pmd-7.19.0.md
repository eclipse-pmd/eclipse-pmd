# New and noteworthy

## New Rules

- The new Apex rule AvoidFutureAnnotation finds usages of the @Future
  annotation. It is a legacy way to execute asynchronous Apex code. New code should implement
  the Queueable interface instead.
- The new Java rule EnumComparison finds usages of equals() on
  enum constants or values. Enums should be compared directly with == instead of equals() which
  has some advantages (e.g. static type checking at compile time).
- The new Apex rule NcssCount replaces the four rules "ExcessiveClassLength",
  "NcssConstructorCount", "NcssMethodCount", and "NcssTypeCount". The new rule uses the metrics framework
  to achieve the same. It has two properties, to define the report level for method and class sizes separately.
  Constructors and methods are considered the same.
- The rule has been added to the quickstart ruleset.
  Note: The new metric is implemented more correct than in the old rules. E.g. it considers now also
  switch statements and correctly counts if-statements only once and ignores method calls that are
  part of an expression and not a statement on their own. This leads to different numbers. Keep in mind,
  that NCSS counts statements and not lines of code. Statements that are split on multiple lines are
  still counted as one.
- The new PL/SQL rule NcssCount replaces the rules "ExcessiveMethodLength",
  "ExcessiveObjectLength", "ExcessivePackageBodyLength", "ExcessivePackageSpecificationLength",
  "ExcessiveTypeLength", "NcssMethodCount" and "NcssObjectCount". The new rule uses the metrics framework
  to achieve the same. It has two properties, to define the report level for method and object sizes separately.
  Note: the new metric is implemented more correct than in the old rules, so that the actual numbers of
  the NCSS metric from the old rules might be different from the new rule "NcssCount". Statements that are
  split on multiple lines are still counted as one.

## Deprecated Rules

- The Apex rule ExcessiveClassLength has been deprecated. Use NcssCount to
  find big classes or create a custom XPath based rule using
  `//ApexFile[UserClass][@EndLine - @BeginLine > 1000]`.
- The Apex rules NcssConstructorCount, NcssMethodCount, and
  NcssTypeCount have been deprecated in favor or the new rule NcssCount.
- The PL/SQL rule ExcessiveMethodLength has been deprecated. Use NcssCount
  instead or create a custom XPath based rule using
  `//(MethodDeclaration|ProgramUnit|TriggerTimingPointSection|TriggerUnit|TypeMethod)[@EndLine - @BeginLine > 100]`.
- The PL/SQL rule ExcessiveObjectLength has been deprecated. Use NcssCount
  instead or create a custom XPath based rule using
  `//(PackageBody|PackageSpecification|ProgramUnit|TriggerUnit|TypeSpecification)[@EndLine - @BeginLine > 1000]`.
- The PL/SQL rule ExcessivePackageBodyLength has been deprecated. Use NcssCount
  instead or create a custom XPath based rule using
  `//PackageBody[@EndLine - @BeginLine > 1000]`.
- The PL/SQL rule ExcessivePackageSpecificationLength has been deprecated. Use NcssCount
  instead or create a custom XPath based rule using
  `//PackageSpecification[@EndLine - @BeginLine > 1000]`.
- The PL/SQL rule ExcessiveTypeLength has been deprecated. Use NcssCount
  instead or create a custom XPath based rule using
  `//TypeSpecification[@EndLine - @BeginLine > 1000]`.
- The PL/SQL rules NcssMethodCount and NcssObjectCount have been
  deprecated in favor of the new rule NcssCount.

# Fixed Issues

## core

- #4767: [core] Deprecate old symboltable API

## apex-bestpractices

- #6203: [apex] New Rule: Avoid Future Annotation

## apex-design

- #2128: [apex] Merge NCSS count rules for Apex

## java

- #5689: [java] Members of record should be in scope in record header
- #6256: [java] java.lang.IllegalArgumentException: Invalid target type of type annotation for method or ctor type annotation: 19

## java-bestpractices

- #5820: [java] GuardLogStatement recognizes that a string is a compile-time constant expression only if at first position
- #6188: [java] UnitTestShouldIncludeAssert false positive when TestNG @Test.expectedException present
- #6193: [java] New Rule: Always compare enum values with ==

## java-codestyle

- #6053: [java] ModifierOrder false-positives with type annotations and type parameters (typeAnnotations = anywhere)

## java-errorprone

- #4742: [java] EmptyFinalizer should not trigger if finalize method is final and class is not
- #6072: [java] OverrideBothEqualsAndHashCodeOnComparable should not be required for record classes
- #6092: [java] AssignmentInOperand false positive in 7.17.0 for case blocks in switch statements
- #6096: [java] OverrideBothEqualsAndHashCodeOnComparable on class with lombok.EqualsAndHashCode annotation
- #6199: [java] AssignmentInOperand: description of property allowIncrementDecrement is unclear
- #6273: [java] TestClassWithoutTestCases documentation does not mention test prefixes

## java-performance

- #4577: [java] UseArraysAsList with condition in loop
- #5071: [java] UseArraysAsList should not warn when elements are skipped in array

## plsql-design

- #4326: [plsql] Merge NCSS count rules for PL/SQL

## maintenance

- #5701: [core] net.sourceforge.pmd.cpd.SourceManager has public methods

# Merged pull requests

- #6081: [java] Fix #6072: OverrideBothEqualsAndHashCodeOnComparable should not be required for record classes - UncleOwen (@UncleOwen)
- #6192: [java] Fix #6053: ModifierOrder - consider type params - Andreas Dangel (@adangel)
- #6194: chore: always place type annotations on the type - Andreas Dangel (@adangel)
- #6195: chore: always compare enums with == - Andreas Dangel (@adangel)
- #6196: [java] New Rule: EnumComparison - Andreas Dangel (@adangel)
- #6198: [apex] New rule NcssCount to replace old Ncss*Count rules - Andreas Dangel (@adangel)
- #6201: [java] Fix #6199: AssignmentInOperandRule: Update description of allowIncrementDecrement property - Lukas Gräf (@lukasgraef)
- #6202: [java] Fix #6188: UnitTestsShouldIncludeAssert - FP when TestNG @Test.expectedException is present - Lukas Gräf (@lukasgraef)
- #6204: [apex] Add rule to limit usage of @Future annotation - Mitch Spano (@mitchspano)
- #6214: [plsql] New rule NcssCount to replace old Ncss*Count rules - Andreas Dangel (@adangel)
- #6217: [doc] Add Blue Cave to known tools using PMD - Jude Pereira (@judepereira)
- #6227: [java] UseArraysAsList: check increment - Andreas Dangel (@adangel)
- #6228: [java] UseArraysAsList: skip when if-statements - Andreas Dangel (@adangel)
- #6229: chore: remove public methods from SourceManager - Andreas Dangel (@adangel)
- #6238: [java] Fix #6096: Detect Lombok generated equals/hashCode in Comparable - Marcel (@mrclmh)
- #6249: [core] Deprecate old symboltable API - Andreas Dangel (@adangel)
- #6250: chore: fail build for compiler warnings - Andreas Dangel (@adangel)
- #6251: [java] Fix #6092: AssignmentInOperand false positive in 7.17.0 for case statements - Marcel (@mrclmh)
- #6255: [java] Fix #4742: EmptyFinalizer should not trigger if finalize method is final and class is not - Marcel (@mrclmh)
- #6258: [java] Fix #5820: GuardLogStatement recognizes that a string is a compile-time constant expression only if at first position - Marcel (@mrclmh)
- #6259: [java] Fix #5689: Issue with scoping of record members - Clément Fournier (@oowekyala)
- #6277: [doc] Add button to copy configuration snippet - Zbynek Konecny (@zbynek)
- #6278: [doc] TestClassWithoutTestCases: Mention test prefixes - Marcel (@mrclmh)
- #6280: [ci] Exclude build resources from spring-framework for regression tester - Clément Fournier (@oowekyala)
- #6282: [java] Fix #6256: ignore invalid annotation type - Andreas Dangel (@adangel)
