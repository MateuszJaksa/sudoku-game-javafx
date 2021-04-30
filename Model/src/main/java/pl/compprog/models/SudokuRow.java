package pl.compprog.models;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuRow extends Verifier implements Serializable {
    public SudokuRow(SudokuField[] row) {
        for (SudokuField field: row) {
            field.addChangeListener(this);
        }
        this.arr = Arrays.asList(row);
    }


    public SudokuRow clone() {
        return (SudokuRow) super.clone();
    }
}