package ch.acanda.eclipse.pmd.ui.model;

import java.util.ArrayList;
import java.util.List;

import ch.acanda.eclipse.pmd.ui.model.ValidationProblem.Severity;

public class ValidationResult {

    private final List<ValidationProblem> problems = new ArrayList<>();
    private boolean hasErrors;

    public void add(final ValidationProblem problem) {
        problems.add(problem);
        if (problem.getSeverity() == Severity.ERROR) {
            hasErrors = true;
        }
    }

    public boolean isValid() {
        return !hasErrors;
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    /**
     * @return The message of the first validation problem with severity {@link Severity#ERROR} or {@code null} if there
     *         are no such validation problems.
     */
    public String getFirstErrorMessage() {
        for (final ValidationProblem problem : problems) {
            if (problem.getSeverity() == Severity.ERROR) {
                return problem.getMessage();
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (hasErrors ? 1231 : 1237);
        result = prime * result + problems.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValidationResult other = (ValidationResult) obj;
        if (hasErrors != other.hasErrors) {
            return false;
        }
        return !problems.equals(other.problems);
    }

}
