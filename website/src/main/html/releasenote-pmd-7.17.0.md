# New and noteworthy

## New Rules

This release brings several new rules for both Java and Apex. Please try them out
and submit feedback on our issue tracker!

- The new apex rule AnnotationsNamingConventions enforces that annotations are used consistently in PascalCase.
  The rule is referenced in the quickstart.xml ruleset for Apex.
- The new java rule TypeParameterNamingConventions replaces the now deprecated rule
  GenericsNaming. The new rule is configurable and checks for naming conventions of type parameters in
  generic types and methods. It can be configured via a regular expression.
  By default, this rule uses the standard Java naming convention (single uppercase letter).
  The rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule OverrideBothEqualsAndHashCodeOnComparable finds missing
  hashCode() and/or equals() methods on types that implement Comparable. This is important if
  instances of these classes are used in collections. Failing to do so can lead to unexpected behavior in sets
  which then do not conform to the Set interface. While the Set interface relies on
  equals() to determine object equality, sorted sets like TreeSet use
  compareTo() instead. The same issue can arise when such objects are used
  as keys in sorted maps.
  This rule is very similar to OverrideBothEqualsAndHashcode which has always been
  skipping Comparable and only reports if one of the two methods is missing. The new rule will also report,
  if both methods (hashCode and equals) are missing.
  The rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule UselessPureMethodCall finds method calls of pure methods
  whose result is not used. Ignoring the result of such method calls is likely as mistake as pure
  methods are side effect free.
  The rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule RelianceOnDefaultCharset finds method calls that
  depend on the JVM's default charset. Using these method without specifying the charset explicitly
  can lead to unexpected behavior on different platforms.
  Thew new java rule VariableCanBeInlined finds local variables that are
  immediately returned or thrown. This rule replaces the old rule UnnecessaryLocalBeforeReturn
  which only considered return statements. The new rule also finds unnecessary local variables
  before throw statements.
  The rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule CollectionTypeMismatch detects calls to
  collection methods where we suspect the types are incompatible. This happens for instance
  when you try to remove a String from a Collection<Integer>: although it is allowed
  to write this because remove takes an Object parameter, it is most likely a mistake.
  This rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule DanglingJavadoc finds Javadoc comments that
  do not belong to a class, method or field. These comments are ignored by the Javadoc tool
  and should either be corrected or removed.
  The rule is referenced in the quickstart.xml ruleset for Java.
- The new java rule ModifierOrder (codestyle) finds incorrectly ordered modifiers
  (e.g., static public instead of public static). It ensures modifiers appear in the correct order as
  recommended by the Java Language Specification.

# Deprecated Rules

- The java rule GenericsNaming has been deprecated for removal in favor
  of the new rule TypeParameterNamingConventions.
- The java rule AvoidLosingExceptionInformation has been deprecated for removal
  in favor of the new rule UselessPureMethodCall.
- The java rule UselessOperationOnImmutable has been deprecated for removal
  in favor of the new rule UselessPureMethodCall.
- The java rule UnnecessaryLocalBeforeReturn has been deprecated for removal
  in favor of the new rule VariableCanBeInlined.

# Fixed Issues

## apex-codestyle

- #5650: [apex] New Rule: AnnotationsNamingConventions

## core

- #4721: [core] chore: Enable XML rule MissingEncoding in dogfood ruleset
- #5849: [core] Support Markdown Output for CPD Reports
- #5958: [core] CSVRenderer: Add begin and end for line and columns (default off)

## java

- #5874: [java] Update java regression tests with Java 25 language features
- #5960: [java] Avoid/reduce duplicate error messages for some rules
- #6014: [java] Crash when encountering a java comment at the end of a file

## java-bestpractices

- #2186: [java] New Rule: RelianceOnDefaultCharset
- #4500: [java] AvoidReassigningLoopVariables - false negatives within for-loops and skip allowed
- #4770: [java] UnusedFormalParameter should ignore public constructor as same as method
- #5198: [java] CheckResultSet false-positive with local variable checked in a while loop

## java-codestyle

- #972: [java] Improve naming conventions rules
- #4916: [java] UseExplicitTypes: cases where 'var' should be unobjectionable
- #5601: [java] New Rule: ModifierOrder
- #5770: [java] New Rule: VariableCanBeInlined: Local variables should not be declared and then immediately returned or thrown
- #5922: [java] New Rule: TypeParameterNamingConventions
- #5948: [java] UnnecessaryBoxing false positive when calling List.remove(int)
- #5982: [java] More detailed message for the UselessParentheses rule

## java-design

- #4911: [java] AvoidRethrowingException should allow rethrowing exception subclasses
- #5023: [java] UseUtilityClass implementation hardcodes a message instead of using the one defined in the XML

## java-documentation

- #5916: [java] New Rule: DanglingJavadoc

## java-errorprone

- #3401: [java] Improve AvoidUsingOctalValues documentation
- #3434: [java] False negatives in AssignmentInOperand Rule
- #5837: [java] New Rule: OverrideBothEqualsAndHashCodeOnComparable
- #5881: [java] AvoidLosingExceptionInformation does not trigger when inside if-else
- #5907: [java] New Rule: UselessPureMethodCall
- #5915: [java] AssignmentInOperand not raised when inside do-while loop
- #5949: [java] New Rule: CollectionTypeMismatch: for Collections methods that take Object as a parameter
- #5974: [java] CloseResourceRule: NullPointerException while analyzing

## test

- #5973: [test] Enable XML validation for rule tests

# Merged pull requests

- #5601: [java] New Rule: ModifierOrder - Clément Fournier (@oowekyala)
- #5822: [apex] Fix #5650: New Rule: AnnotationsNamingConventions - Mitch Spano (@mitchspano)
- #5847: [java] Fix #5770: New Rule: VariableCanBeInlined - Vincent Potucek (@Pankraz76)
- #5856: [java] Fix #5837: New Rule: OverrideBothEqualsAndHashCodeOnComparable - Vincent Potucek (@Pankraz76)
- #5907: [java] New Rule: UselessPureMethodCall - Zbynek Konecny (@zbynek)
- #5916: [java] New Rule: DanglingJavadoc - Zbynek Konecny (@zbynek)
- #5922: [java] Fix #972: Add a new rule TypeParameterNamingConventions - UncleOwen (@UncleOwen)
- #5924: [java] Fix #5915: Fix AssignmentInOperandRule to also work an do-while loops and switch statements - UncleOwen (@UncleOwen)
- #5930: [java] Fix #4500: Fix AvoidReassigningLoopVariablesRule to allow only simple assignments in the forReassign=skip case - UncleOwen (@UncleOwen)
- #5931: [java] Fix #5023: Fix UseUtilityClassRule to use the message provided in design.xml - UncleOwen (@UncleOwen)
- #5932: [ci] Reuse GitHub Pre-Releases - Andreas Dangel (@adangel)
- #5933: [test] Fix QuickstartRulesetTests to detect deprecated rules again - Andreas Dangel (@adangel)
- #5934: [java] Fix #2186: New Rule: RelianceOnDefaultCharset - UncleOwen (@UncleOwen)
- #5938: [doc] Update suppression docs to reflect PMD 7 changes - Zbynek Konecny (@zbynek)
- #5939: [java] Fix #5198: CheckResultSet FP when local variable is checked - Lukas Gräf (@lukasgraef)
- #5954: [core] Fix #4721: Enable XML rule MissingEncoding in dogfood ruleset - Andreas Dangel (@adangel)
- #5955: chore: Fix LiteralsFirstInComparison violations in test code - Andreas Dangel (@adangel)
- #5957: [java] Fix #3401: Improve message/description/examples for AvoidUsingOctalValues - UncleOwen (@UncleOwen)
- #5958: [core] CSVRenderer: Add begin and end for line and columns (default off) - Jude Pereira (@judepereira)
- #5959: [java] Fix #5960: AddEmptyString: Improve report location - Zbynek Konecny (@zbynek)
- #5961: [java] Fix #5960: Add details to the error message for some rules - Zbynek Konecny (@zbynek)
- #5965: [java] Fix #5881: AvoidLosingException - Consider nested method calls - Andreas Dangel (@adangel)
- #5967: [doc][java] ReplaceJavaUtilDate - improve doc to mention java.sql.Date - Andreas Dangel (@adangel)
- #5968: [doc] Add logging page to sidebar under dev docs - Andreas Dangel (@adangel)
- #5969: [doc] Add CSS in PMD's description - Andreas Dangel (@adangel)
- #5970: chore: CI improvements - Andreas Dangel (@adangel)
- #5971: [java] Fix #5948: UnnecessaryBoxingRule: Check if unboxing is required for overload resolution - UncleOwen (@UncleOwen)
- #5972: [java] Fix #3434: False negatives in AssignmentInOperand rule - UncleOwen (@UncleOwen)
- #5975: [test] Fix #5973: Enable XML Validation for rule tests - Andreas Dangel (@adangel)
- #5979: [java] Fix #5974: NPE in CloseResourceRule - Lukas Gräf (@lukasgraef)
- #5980: chore: Fix typos - Zbynek Konecny (@zbynek)
- #5981: [java] Fix #4911: AvoidRethrowingException consider supertypes in following catches - UncleOwen (@UncleOwen)
- #5982: [java] More detailed message for the UselessParentheses rule - Zbynek Konecny (@zbynek)
- #5989: [java] Improve performance of RelianceOnDefaultCharset - UncleOwen (@UncleOwen)
- #5994: [java] Fix #4770: UnusedFormalParameter should ignore public constructor as same as method - UncleOwen (@UncleOwen)
- #5995: [html] Add test case that tests the end of a reported violation (test for #3951) - UncleOwen (@UncleOwen)
- #5996: [java] Fix #4916: UseExplicitTypes cases where 'var' should be unobjectionable - UncleOwen (@UncleOwen)
- #6006: [java] Fix #5949: New Rule: CollectionTypeMismatch - UncleOwen (@UncleOwen)
- #6008: [core] Fix #5849: Support Markdown Output for CPD Reports - Lukas Gräf (@lukasgraef)
- #6009: [java] More detailed message for AvoidInstanceofChecksInCatchClause - Zbynek Konecny (@zbynek)
- #6016: [java] Fix #6014: Crash when encountering a java comment at the end of a file - UncleOwen (@UncleOwen)
