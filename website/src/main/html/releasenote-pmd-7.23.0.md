# Fixed Issues

## core

- #6503: [core] Links in HTML report are broken

## java-errorprone

- #6502: [java] CloseResource: False positive for allowedResourceMethodPatterns entries when using unqualified method calls

## java-security

- #6531: [java] InsecureCryptoIv: False negative with fixed IVs from array initializers

# Merged pull requests

- #6467: [ci] Use typos gh-action - Andreas Dangel (@adangel)
- #6488: [doc] Update security.md for CVE-2026-28338 - Andreas Dangel (@adangel)
- #6489: [doc] CPD: document --report-file parameter - Andreas Dangel (@adangel)
- #6504: [core] Fix #6503: Don't escape externalInfoUrl in reports - Andreas Dangel (@adangel)
- #6505: [java] Fix #6502: CloseResource should consider unqualified method calls - Andreas Dangel (@adangel)
- #6545: [java] Fix #6531: False negative in InsecureCryptoIv with array initializers - Zbynek Konecny (@zbynek)
