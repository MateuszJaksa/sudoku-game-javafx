package pl.compprog.models;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuColumn extends Verifier implements Serializable {
    public SudokuColumn(SudokuField[] column) {
        for (SudokuField field: column) {
            field.addChangeListener(this);
        }
        this.arr = Arrays.asList(column);
    }

    public SudokuColumn clone() {
        return (SudokuColumn) super.clone();
    }
}