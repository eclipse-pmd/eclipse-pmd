package ch.acanda.eclipse.pmd.ui.model;

/**
 * @author Philip Graf
 */
public final class ValidationUtil {

    private ValidationUtil() {
        // hide constructor of utility class
    }

    /**
     * Adds a validation error to the validation result if {@code value} is {@code null} or a blank string.
     *
     * @return {@code true} if a validation error was added to the validation result.
     */
    public static boolean errorIfBlank(final String propertyName, final String value, final String message, final ValidationResult result) {
        if (value == null || value.trim().length() == 0) {
            result.add(new ValidationProblem(propertyName, ValidationProblem.Severity.ERROR, message));
            return true;
        }
        return false;
    }

}
