package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.exceptions.InvalidValueException;
import pl.compprog.models.SudokuRow;
import pl.compprog.solvers.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SudokuRowTest {
    public SudokuRowTest(){
    }

    @org.junit.jupiter.api.Test
    public void testSudokuRowVerifyTrueCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuRow row = board.getRow(0);
        Assertions.assertTrue(row.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuRowVerifyFalseCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        board.set(0,0,1);
        board.set(0,4,1);
        SudokuRow row = board.getRow(0);
        Assertions.assertFalse(row.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuRowSetFailed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        assertThrows(InvalidValueException.class, () -> {
            board.setVerify(0,0,1);
            board.setVerify(1,0,1);
        });
    }

    @org.junit.jupiter.api.Test
    public void testSudokuRowSetVerifyPassed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            board.setVerify(0,0,1);
            board.setVerify(3,3,1);
        } catch(InvalidValueException e) {
            fail("Verifier should pass");
        }
    }

    @org.junit.jupiter.api.Test
    public void testDifferentRowClassComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i / 3, i % 3, i + 1);
            board.set(8, i, i + 1);
        }
        Assertions.assertNotEquals(board.getBox(0, 0), board.getRow(8));
    }

    @org.junit.jupiter.api.Test
    public void testSameRowComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(0, i, i + 1);
            board.set(1, i, i + 1);
        }
        Assertions.assertEquals(board.getRow(0), board.getRow(1));
        Assertions.assertEquals(board.getRow(0).hashCode(), board.getRow(1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testDifferentRowComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(0, i, i + 1);
            board.set(1, i, 9 - i);
        }
        Assertions.assertNotEquals(board.getRow(0), null);
        Assertions.assertNotEquals(board.getRow(0), board.getRow(1));
        Assertions.assertNotEquals(board.getRow(0).hashCode(), board.getRow(1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testColumnToString() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        String correctString = "";
        for (int i = 0; i < 9; ++i) {
            board.set(0, i, i + 1);
            correctString += (i + 1) + " ";
        }
        SudokuRow row = board.getRow(0);
        Assertions.assertEquals(row.toString(), correctString);
    }
}