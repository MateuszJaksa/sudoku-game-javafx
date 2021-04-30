package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.exceptions.InvalidValueException;
import pl.compprog.models.SudokuColumn;
import pl.compprog.solvers.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SudokuColumnTest {
    public SudokuColumnTest(){
    }

    @org.junit.jupiter.api.Test
    public void testSudokuColumnVerifyTrueCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuColumn column = board.getColumn(0);
        Assertions.assertTrue(column.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuColumnVerifyFalseCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        board.set(0,0,1);
        board.set(4,0,1);
        SudokuColumn column = board.getColumn(0);
        Assertions.assertFalse(column.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuColumnSetFailed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        assertThrows(InvalidValueException.class, () -> {
            board.setVerify(0,0,1);
            board.setVerify(0,1,1);
        });
    }

    @org.junit.jupiter.api.Test
    public void testSudokuColumnSetVerifyPassed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            board.setVerify(0,0,1);
            board.setVerify(3,3,1);
        } catch(InvalidValueException e) {
            fail("Verifier should pass");
        }
    }

    @org.junit.jupiter.api.Test
    public void testDifferentColumnClassComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i, 0, i + 1);
            board.set(0, i, i + 1);
        }
        Assertions.assertNotEquals(board.getColumn(0), board.getRow(0));
    }

    @org.junit.jupiter.api.Test
    public void testSameColumnComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i, 0, i + 1);
            board.set(i, 1, i + 1);
        }
        Assertions.assertEquals(board.getColumn(0), board.getColumn(1));
        Assertions.assertEquals(board.getColumn(0).hashCode(), board.getColumn(1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testDifferentColumnComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i, 0, i + 1);
            board.set(i, 1, 9 - i);
        }
        Assertions.assertNotEquals(board.getColumn(0), null);
        Assertions.assertNotEquals(board.getColumn(0), board.getColumn(1));
        Assertions.assertNotEquals(board.getColumn(0).hashCode(), board.getColumn(1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testColumnToString() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        String correctString = "";
        for (int i = 0; i < 9; ++i) {
            board.set(i, 0, i + 1);
            correctString += (i + 1) + " ";
        }
        SudokuColumn column = board.getColumn(0);
        Assertions.assertEquals(column.toString(), correctString);
    }
}