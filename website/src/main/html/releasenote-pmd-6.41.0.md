# Fixed Issues

## core
- #2954: Create GitHub Action for PMD
- #3424: [core] Migrate CLI to using GNU-style long options
- #3425: [core] Add a --version CLI option
- #3593: [core] Ant task fails with Java17
- #3635: [ci] Update sample projects for regression tester
## java-bestpractices
- #3595: [java] PrimitiveWrapperInstantiation: no violation on ‘new Boolean(val)’
- #3613: [java] ArrayIsStoredDirectly doesn’t consider nested classes
- #3614: [java] JUnitTestsShouldIncludeAssert doesn’t consider nested classes
- #3618: [java] UnusedFormalParameter doesn’t consider anonymous classes
- #3630: [java] MethodReturnsInternalArray doesn’t consider anonymous classes
## java-design
- #3620: [java] SingularField doesn’t consider anonymous classes defined in non-private fields
## java-errorprone
- #3624: [java] TestClassWithoutTestCases reports wrong classes in a file
## java-performance
- #3491: [java] UselessStringValueOf: False positive when valueOf(char [], int, int) is used

# External Contributions

- #3600: [core] Implement GNU-style long options and ‘–version’ - Yang
- #3612: [java] Created fix for UselessStringValueOf false positive - John Armgardt
- #3648: [doc] Rename Code Inspector to Codiga - Julien Delange
