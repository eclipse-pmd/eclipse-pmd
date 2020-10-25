# New and noteworthy

## Updated Apex Support
The Apex language support has been bumped to version 50 (Winter ‘21). All new language features are now properly parsed and processed. Especially the Safe Navigation Operator is now supported. See also Salesforce Winter ‘21 Release Notes

## New Rules
The new Apex rule OperationWithLimitsInLoop (apex-performance) finds operations in loops that may hit governor limits such as DML operations, SOQL queries and more. The rule replaces the three rules “AvoidDmlStatementsInLoops”, “AvoidSoqlInLoops”, and “AvoidSoslInLoops”.

## Renamed Rules
The Java rule DoNotCallSystemExit has been renamed to DoNotTerminateVM, since it checks for all the following calls: System.exit(int), Runtime.exit(int), Runtime.halt(int). All these calls terminate the Java VM, which is bad, if the VM runs an application server which many independent applications.

## Deprecated Rules
The Apex rules AvoidDmlStatementsInLoops, AvoidSoqlInLoops and AvoidSoslInLoops (apex-performance) are deprecated in favour of the new rule OperationWithLimitsInLoop. The deprecated rules will be removed with PMD 7.0.0.

# Fixed Issues

## apex
- #2839: [apex] Apex classes with safe navigation operator from Winter 21 (50.0) are skipped
## apex-performance
- #1713: [apex] Mark Database DML statements in For Loop
## core
- #2831: [core] Fix XMLRenderer newlines when running under IBM Java
## java-errorprone
- #2157: [java] Improve DoNotCallSystemExit: permit call in main(), flag System.halt
- #2764: [java] CloseResourceRule does not recognize multiple assignment done to resource
## miscellaneous
- #2823: [doc] Renamed/Moved rules are missing in documentation
## vf (Salesforce VisualForce)
- #2765: [vf] Attributes with dot cause a VfParseException

# External Contributions
- #2803: [java] Improve DoNotCallSystemExit (Fixes #2157) - Vitaly Polonetsky
- #2809: [java] Move test config from file to test class - Stefan Birkner
- #2810: [core] Move method “renderTempFile” to XMLRendererTest - Stefan Birkner
- #2811: [java] CloseResource - Fix #2764: False-negative when re-assigning variable - Andi Pabst
- #2813: [core] Use JUnit’s TemporaryFolder rule - Stefan Birkner
- #2816: [apex] Detect ‘Database’ method invocations inside loops - Jeff Bartolotta
- #2829: [doc] Small correction in pmd_report_formats.md - Gustavo Krieger
- #2834: [vf] Allow attributes with dot in Visualforce - rmohan20
- #2842: [core] Bump antlr4 from 4.7 to 4.7.2 - Adrien Lecharpentier
- #2865: [java] Update ExcessiveImports example code for clarity - Gustavo Krieger
- #2866: [java] Fix example for CouplingBetweenObjects - Gustavo Krieger
