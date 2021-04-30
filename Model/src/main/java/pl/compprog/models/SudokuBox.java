package pl.compprog.models;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuBox extends Verifier implements Serializable {

    public SudokuBox(SudokuField[] box) {
        for (SudokuField field: box) {
            field.addChangeListener(this);
        }
        this.arr = Arrays.asList(box);
    }

    public SudokuBox clone() {
        return (SudokuBox) super.clone();
    }
}