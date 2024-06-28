# New and noteworthy
## New Rules

The new Java rule `UseEnumCollections` reports usages for HashSet and HashMap when the keys are of an enum type. The specialized enum collections are more space- and time-efficient.

# Fixed Issues

## cli
- #2827: [cli] Consider processing errors in exit status

## apex
- #4922: [apex] SOQL syntax error with TYPEOF in sub-query
- #5053: [apex] CPD fails to parse string literals with escaped characters
- #5055: [apex] SOSL syntax error with WITH USER_MODE or WITH SYSTEM_MODE

## apex-bestpractices
- #5000: [apex] UnusedLocalVariable FP with binds in SOSL / SOQL

## java
- #4885: [java] AssertionError: Method should be accessible
- #5050: [java] Problems with pattern variables in switch branches

## java-bestpractices
- #577: [java] New Rule: Check that Map<K,V> is an EnumMap if K is an enum value
- #5047: [java] UnusedPrivateMethod FP for Generics & Overloads

## plsql
- #1934: [plsql] ParseException with MERGE statement in anonymous block
- #2779: [plsql] Error while parsing statement with (Oracle) DML Error Logging
- #4270: [plsql] Parsing exception COMPOUND TRIGGER with EXCEPTION handler
