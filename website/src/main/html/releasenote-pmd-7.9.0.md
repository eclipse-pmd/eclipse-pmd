# Fixed Issues


## cli

- #5399: [cli] Windows: PMD fails to start with special characters in path names
- #5401: [cli] Windows: Console output doesn't use unicode

## java
- #5096: [java] StackOverflowError with recursively bound type variable

## java-bestpractices

- #4861: [java] UnusedPrivateMethod - false positive with static methods in core JDK classes

## java-documentation

- #2996: [java] CommentSize rule violation is not suppressed at method level


# Merged pull requests


- #4939: [java] Fix #2996 - CommentSize/CommentContent suppression - Clément Fournier (@oowekyala)
- #5376: [java] Fix #4861 - UnusedPrivateMethod FP in JDK classes - Clément Fournier (@oowekyala)
- #5387: [java] Fix #5096 - StackOverflowError with recursively bounded tvar - Clément Fournier (@oowekyala)
- #5400: Fix #5399: [cli] pmd.bat: Quote all variables when using SET - Andreas Dangel (@adangel)
- #5402: Fix #5401: [cli] pmd.bat: set codepage to 65001 (UTF-8) - Andreas Dangel (@adangel)
- #5404: [doc] Update tools / integrations / ide plugins / news pages - Andreas Dangel (@adangel)
- #5414: Add Rust CPD - Julia Paluch (@juliapaluch)
