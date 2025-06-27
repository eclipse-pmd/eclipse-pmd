# New and noteworthy

## New Rules

The new Apex rule `AvoidBooleanMethodParameters` finds methods that take a boolean parameter. This can make method calls difficult to understand and maintain as the method is clearly doing two things.

# Fixed Issues

## apex-design

- #5427: [apex] New Rule: Avoid Boolean Method Parameters

## apex-security

- #5788: [apex] ApexCRUDViolation unable to detect insecure SOQL if it is a direct input argument

## doc

- #5790: [doc] Website rule reference pages are returning 404

## java-bestpractices

- #5785: [java] UnusedPrivateField doesn’t play well with UnnecessaryWarningSuppression
- #5793: [java] NonExhaustiveSwitch fails on exhaustive switch with sealed class

##    java-codestyle

- #1639: [java] UnnecessaryImport false positive for multiline @link Javadoc
- #2304: [java] UnnecessaryImport false positive for on-demand imports in JavaDoc
- #5832: [java] UnnecessaryImport false positive for multiline @see Javadoc

## java-design

- #5804: [java] UselessOverridingMethod doesn’t play well with UnnecessarySuppressWarning

# Merged pull requests

- #5738: chore: Remove unused private methods in test classes - Pankraz76 (@Pankraz76)
- #5745: [ci] New “Publish Release” workflow - Andreas Dangel (@adangel)
- #5791: [doc] Add a simple check whether generate rule doc pages exist - Andreas Dangel (@adangel)
- #5797: [doc] Update sponsors - Andreas Dangel (@adangel)
- #5800: Fix #5793: [java] NonExhaustiveSwitch should ignore “case null” - Andreas Dangel (@adangel)
- #5803: chore: Remove unnecessary suppress warnings - Andreas Dangel (@adangel)
- #5805: Fix #5804: [java] UselessOverridingMethod needs to ignore SuppressWarnings - Andreas Dangel (@adangel)
- #5806: [test] Verify suppressed violations in rule tests - Andreas Dangel (@adangel)
- #5814: Fix #5788: [apex] ApexCRUDViolation - consider deeper nested Soql - Andreas Dangel (@adangel)
- #5815: Fix #5785: [java] UnusedPrivateField should ignore SuppressWarnings - Andreas Dangel (@adangel)
- #5818: Fix #2304: [java] UnnecessaryImport FP for on-demand imports in JavaDoc - Lukas Gräf (@lukasgraef)
- #5821: [apex] New Rule: Avoid boolean method parameters - Mitch Spano (@mitchspano)
- #5823: [doc] Fix javadoc plugin configuration - Andreas Dangel (@adangel)
- #5833: Fix #1639 #5832: Use filtered comment text for UnnecessaryImport - Andreas Dangel (@adangel)
- #5851: chore: [java] ReplaceHashtableWithMap: Fix name of test - UncleOwen (@UncleOwen)
