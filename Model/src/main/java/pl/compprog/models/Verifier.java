package pl.compprog.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Verifier implements VetoableChangeListener, Cloneable, Serializable {
    protected List<SudokuField> arr;

    public boolean verify() {
        for (int i = 0; i < arr.size(); i++) {
            int numberOfOccurrences = 0;
            int number = arr.get(i).getFieldValue();
            if (number == 0) {
                continue;
            }

            for (SudokuField field:  arr) {
                if (field.getFieldValue() == number) {
                    numberOfOccurrences += 1;
                }
            }
            if (numberOfOccurrences != 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
        if (!verify()) {
            throw new PropertyVetoException("The value doesn't conform with sudoku rules.", event);
        }
    }

    public String toString() {
        String string = "";
        for (int i = 0;i < 9; ++i) {
            string = string.concat(arr.get(i).getFieldValue() + " ");
        }
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Verifier verifier = (Verifier) o;

        return new EqualsBuilder()
                .append(arr, verifier.arr)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(arr)
                .toHashCode();
    }

    public Verifier clone() {
        try {
            Verifier verifier = (Verifier) super.clone();
            SudokuField[] newArray = new SudokuField[9];
            for (int i = 0; i < 9; i++) {
                newArray[i] = new SudokuField();
                newArray[i].setFieldValue(arr.get(i).getFieldValue());
            }
            verifier.arr = Arrays.asList(newArray);
            return verifier;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}