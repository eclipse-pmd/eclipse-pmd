package ch.acanda.eclipse.pmd.exception;

/**
 * {@code EclipsePMDException} is the superclass of all exceptions that can be thrown by the eclipse-pmd plugin.
 */
public class EclipsePMDException extends RuntimeException {

    private static final long serialVersionUID = 4312111836815540119L;

    public EclipsePMDException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
