package pl.compprog.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.compprog.exceptions.CannotCloneException;
import pl.compprog.exceptions.InvalidValueException;

public class SudokuField implements Cloneable, Serializable, Comparable<SudokuField> {
    private static final String PROPERTY_NAME = "value";
    private transient final IntegerProperty property = new SimpleIntegerProperty();
    private int value = 0;
    private boolean isModifiable = false;
    private List<VetoableChangeListener> listener = new ArrayList<>();

    public SudokuField() {
        property.set(value);
    }

    public IntegerProperty getProperty() {
        return property;
    }

    public boolean isModifiable() {
        return isModifiable;
    }

    public void setModifiable(boolean modifiable) {
        isModifiable = modifiable;
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        this.value = value;
        property.set(value);
    }

    public void setFieldValueVerify(int value) throws InvalidValueException {
        int oldValue = this.value;
        this.value = value;
        try {
            for (VetoableChangeListener name : listener) {
                name.vetoableChange(new PropertyChangeEvent(this, PROPERTY_NAME, oldValue, value));
            }
        } catch (PropertyVetoException e) {
            this.value = oldValue;
            throw new InvalidValueException();
        }
        property.set(value);
    }

    public void addChangeListener(VetoableChangeListener newListener) {
        listener.add(newListener);
    }

    public SudokuField clone() throws CannotCloneException {
        try {
            SudokuField field = (SudokuField) super.clone();
            field.listener = new ArrayList<>();
            return field;

        } catch (CloneNotSupportedException e) {
            throw new CannotCloneException(e);
        }
    }

    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new NullPointerException("The object you are comparing to does not exist");
        }
        return Integer.compare(this.value, o.getFieldValue());
    }
}