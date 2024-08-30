# New and changed rules

## New Rules

- The new Java rule `AvoidSynchronizedStatement` finds synchronization blocks that
  could cause performance issues with virtual threads due to pinning.
- The new JavaScript rule `AvoidConsoleStatements` finds any function calls
  on the Console API (e.g. console.log). Using these in production code might negatively impact performance.

# Fixed Issues

## apex-performance
- #5139: [apex] `OperationWithHighCostInLoop`: false negative for triggers

## java
- #5062: [java] Support Java 23
- #5167: [java] java.lang.IllegalArgumentException: <?> cannot be a wildcard bound

## java-bestpractices
- #3602: [java] `GuardLogStatement`: False positive when compile-time constant is created from external constants
- #4731: [java] `GuardLogStatement`: Documentation is unclear why getters are flagged
- #5145: [java] `UnusedPrivateMethod`: False positive with method calls inside lambda
- #5151: [java] `GuardLogStatement`: Should not need to guard parameterized log messages where the replacement arg is a constant from another class
- #5152: [java] `GuardLogStatement`: Should not need to guard parameterized log messages where the replacement arg is "this"
- #5153: [java] `GuardLogStatement`: Should not need to guard parameterized log messages where the replacement arg is an array element

## java-design
- #5048: [java] `CognitiveComplexity`: Exception when using Map.of()
- #5162: [java] `SingularField`: False-positive when preceded by synchronized block

## java-multithreading
- #5175: [java] Update `AvoidSynchronizedAtMethodLevel` message to mention ReentrantLock, new rule AvoidSynchronizedStatement

## javascript-performance
- #5105: [javascript] Prohibit any console methods

## plsql
- #5125: [plsql] Improve merge statement (order of merge insert/update flexible, allow prefixes in column names)

## plsql-bestpractices
- #5132: [plsql] TomKytesDespair: XPathException for more complex exception handler

# External Contributions

- #5125: [plsql] Improve merge statement (order of merge insert/update flexible, allow prefixes in column names) - Arjen Duursma (@duursma)
- #5175: [java] Update `AvoidSynchronizedAtMethodLevel` message to mention ReentrantLock, new rule `AvoidSynchronizedStatement` - Chas Honton (@chonton)
