package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.models.SudokuField;
import pl.compprog.solvers.BacktrackingSudokuSolver;

public class SudokuFieldTest {

    @org.junit.jupiter.api.Test
    public void testDifferentFieldClass() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(0);
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        Assertions.assertNotEquals(field1, board);
    }

    @org.junit.jupiter.api.Test
    public void testSameField() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(0);
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(0);
        Assertions.assertEquals(field1, field2);
        Assertions.assertEquals(field1.hashCode(), field2.hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testDifferentField() {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(0);
        SudokuField field2 = new SudokuField();
        field2.setFieldValue(1);
        Assertions.assertNotEquals(field1, null);
        Assertions.assertNotEquals(field1, field2);
        Assertions.assertNotEquals(field1.hashCode(), field2.hashCode());

    }

    @org.junit.jupiter.api.Test
    public void testFieldString() {
        SudokuField field = new SudokuField();
        field.setFieldValue(0);
        Assertions.assertEquals(field.toString(), "0");
    }

    @org.junit.jupiter.api.Test
    public void testFieldCompareTo() {
        SudokuField field0 = new SudokuField();
        field0.setFieldValue(0);
        SudokuField field0v2 = new SudokuField();
        field0v2.setFieldValue(0);
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(1);
        Assertions.assertEquals(field0v2.compareTo(field0), 0);
        Assertions.assertEquals(field1.compareTo(field0), 1);
        Assertions.assertEquals(field0.compareTo(field1), -1);
    }
}
