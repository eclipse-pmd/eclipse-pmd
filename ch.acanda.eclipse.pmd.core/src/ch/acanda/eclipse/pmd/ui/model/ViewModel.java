package ch.acanda.eclipse.pmd.ui.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

/**
 * Base class for a view model.
 */
public abstract class ViewModel {

    public static final String DIRTY_PROPERTY = "dirty";
    public static final String VALID_PROPERTY = "valid";

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Set<String> validatedProperties;
    private boolean isDirty;
    private boolean isValid;
    private ValidationResult validationResult;

    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected <T> void setProperty(final String propertyName, final T oldValue, final T newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        final boolean newIsDirty = updateDirty();
        propertyChangeSupport.firePropertyChange(DIRTY_PROPERTY, isDirty, isDirty = newIsDirty);
        if (validatedProperties == null) {
            validatedProperties = createValidatedPropertiesSet();
        }
        if (validatedProperties.contains(propertyName)) {
            final ValidationJob job = new ValidationJob(this, propertyName);
            job.schedule();
        }
    }

    /**
     * Checks if the model was changed (is dirty). The implementation must not invoke
     * {@link ViewModel#setProperty(String, Object, Object)} as this would result in an infinite recursion.
     *
     * @return {@code true} if the model is valid, {@code false} if the model is invalid.
     */
    protected abstract boolean updateDirty();

    /**
     * Validates the model. The implementation must validate the entire model. The provided {@code propertyName} merely
     * indicates which property has changed. The implementation must not invoke
     * {@link ViewModel#setProperty(String, Object, Object)} for validated properties as this would result in an
     * infinite recursion.
     *
     * @param propertyName The name of the property that has changed.
     * @param validationResult
     */
    protected abstract void validate(String propertyName, ValidationResult validationResult);

    /**
     * Returns the names of the properties that are validated. If a property is changed and its name is not in this set,
     * {@link #validate(String)} will not be invoked. This method is only called once (before the first time
     * {@link #validate(String)} is invoked), so the method does not have to be optimized.
     *
     * @return An immutable set of the names of validated properties.
     */
    protected abstract Set<String> createValidatedPropertiesSet();

    public boolean isValid() {
        return isValid;
    }

    void setValidationResult(final ValidationResult result) {
        isValid = result.isValid();
        propertyChangeSupport.firePropertyChange(VALID_PROPERTY, validationResult, validationResult = result);
    }

    public void addDirtyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(DIRTY_PROPERTY, listener);
    }

    public void removeDirtyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(DIRTY_PROPERTY, listener);
    }

    public void addValidationChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(VALID_PROPERTY, listener);
    }

    public void removeValidationChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(VALID_PROPERTY, listener);
    }

}
