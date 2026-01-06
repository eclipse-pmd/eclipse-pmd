# New and noteworthy

## Changed Rules

- The Java rule OnlyOneReturn has a new property ignoredMethodNames. This property by
  default is set to compareTo and equals, thus this rule now by default allows multiple return statements
  for these methods. To restore the old behavior, simply set this property to an empty value.

# Fixed Issues

## core

- #6330: [core] "Unable to create ValueRepresentation" when using @LiteralText (XPath)

## java

- #6234: [java] Parser fails to parse switch expressions in super() constructor calls
- #6299: [java] Fix grammar of switch label

## java-bestpractices

- #4282: [java] GuardLogStatement: False positive when guard is not a direct parent
- #6028: [java] UnusedPrivateMethod: False positive with raw type for generic method
- #6257: [java] UnusedLocalVariable: False positive with instanceof pattern guard
- #6291: [java] EnumComparison: False positive for any object when object.equals(null)
- #6328: [java] UnusedLocalVariable: False positive for pattern variable in for-each without braces

## java-codestyle

- #4257: [java] OnlyOneReturn: False positive with equals method
- #5043: [java] LambdaCanBeMethodReference: False positive on overloaded methods
- #6237: [java] UnnecessaryCast: ContextedRuntimeException when parsing switch expression with lambdas
- #6279: [java] EmptyMethodInAbstractClassShouldBeAbstract: False positive for final empty methods
- #6284: [java] UnnecessaryConstructor: False positive for JavaDoc-bearing constructor

## java-errorprone

- #6276: [java] NullAssignment: False positive when assigning null to a final field in a constructor
- #6343: [java] MissingStaticMethodInNonInstantiatableClass: False negative when method in nested class returns null

## java-performance

- #4158: [java] BigIntegerInstantiation: False negative with compile-time constant
- #4910: [java] ConsecutiveAppendsShouldReuse: False positive within if-statement without curly braces
- #5877: [java] AvoidArrayLoops: False negative when break inside switch statement

## maintenance

- #6230: [core] Single module snapshot build fails

# Merged pull requests

- #6262: [java] UnusedLocalVariable: fix false positive with guard in switch - Zbynek Konecny (@zbynek)
- #6285: [java] Fix #5043: FP in LambdaCanBeMethodReference when method ref would be ambiguous - Clément Fournier (@oowekyala)
- #6287: [doc] Explain how to build or pull snapshot dependencies for single module builds - Marcel (@mrclmh)
- #6288: [java] Fix #6279: EmptyMethodInAbstractClassShouldBeAbstract should ignore final methods - Marcel (@mrclmh)
- #6292: [java] Fix #6291: EnumComparison FP when comparing with null - Clément Fournier (@oowekyala)
- #6293: [java] Fix #6276: NullAssignment should not report assigning null to a final field in a constructor - Lukas Gräf (@lukasgraef)
- #6294: [java] Fix #6028: UnusedPrivateMethod FP - Clément Fournier (@oowekyala)
- #6295: [java] Fix #6237: UnnecessaryCast error with switch expr returning lambdas - Clément Fournier (@oowekyala)
- #6296: [java] Fix #4282: GuardLogStatement only detects guard methods immediately around it - Marcel (@mrclmh)
- #6299: [java] Fix grammar of switch label - Clément Fournier (@oowekyala)
- #6309: [java] Fix #4257: Allow ignoring methods in OnlyOneReturn - Marcel (@mrclmh)
- #6311: [java] Fix #6284: UnnecessaryConstructor reporting false-positive on JavaDoc-bearing constructor - Marcel (@mrclmh)
- #6313: [java] Fix #4910: if-statement triggers ConsecutiveAppendsShouldReuse - Marcel (@mrclmh)
- #6316: [java] Fix #5877: AvoidArrayLoops false-negative when break inside switch statement - Marcel (@mrclmh)
- #6342: [core] Fix #6330: Cannot access Chars attribute from XPath - Clément Fournier (@oowekyala)
- #6344: [java] Fix #6328: UnusedLocalVariable should consider pattern variable in for-each without curly braces - Mohamed Hamed (@mdhamed238)
- #6348: [jsp] Fix malformed Javadoc HTML in JspDocStyleTest - Gianmarco (@gianmarcoschifone)
- #6359: [java] Fix #6234: Parser fails to parse switch expressions in super() constructor calls - Mohamed Hamed (@mdhamed238)
- #6360: [java] Fix #4158: BigIntegerInstantiation false-negative with compile-time constant - Lukas Gräf (@lukasgraef)
- #6361: [vf] Fix invalid Javadoc syntax in VfDocStyleTest - Gianmarco (@gianmarcoschifone)
- #6363: [apex] Add sca-extra ruleset for Salesforce Apex testing - Beech Horn (@metalshark)
