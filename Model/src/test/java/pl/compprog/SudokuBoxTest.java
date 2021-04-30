package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.exceptions.InvalidValueException;
import pl.compprog.models.SudokuBox;
import pl.compprog.solvers.BacktrackingSudokuSolver;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SudokuBoxTest {
    public SudokuBoxTest(){
    }

    @org.junit.jupiter.api.Test
    public void testSudokuBoxVerifyTrueCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBox box = board.getBox(0,0);
        Assertions.assertTrue(box.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuBoxVerifyFalseCase() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        board.set(0,0,1);
        board.set(1,1,1);
        SudokuBox box = board.getBox(0,0);
        Assertions.assertFalse(box.verify());
    }

    @org.junit.jupiter.api.Test
    public void testSudokuBoxSetVerifyFailed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        assertThrows(InvalidValueException.class, () -> {
            board.setVerify(0,0,1);
            board.setVerify(1,1,1);
        });
    }

    @org.junit.jupiter.api.Test
    public void testSudokuBoxSetVerifyPassed() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            board.setVerify(0,0,1);
            board.setVerify(3,3,1);
        } catch(InvalidValueException e) {
            fail("Verifier should pass");
        }
    }

    @org.junit.jupiter.api.Test
    public void testDifferentBoxClassComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i / 3, i % 3, i + 1);
            board.set(i, 8, i + 1);
        }
        Assertions.assertNotEquals(board.getBox(0, 0), board.getColumn(8));
    }

    @org.junit.jupiter.api.Test
    public void testSameBoxComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i / 3, i % 3, i + 1);
            board.set(i / 3, 3 + i % 3, i + 1);
        }
        Assertions.assertEquals(board.getBox(0, 0), board.getBox(0, 1));
        Assertions.assertEquals(board.getBox(0, 0).hashCode(), board.getBox(0, 1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testDifferentBoxComparison() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        for (int i = 0; i < 9; ++i) {
            board.set(i / 3, i % 3, i + 1);
            board.set(i / 3, 3 + i % 3, 9 - i);
        }
        Assertions.assertNotEquals(board.getBox(0, 0), null);
        Assertions.assertNotEquals(board.getBox(0, 0), board.getBox(0, 1));
        Assertions.assertNotEquals(board.getBox(0, 0).hashCode(), board.getBox(0, 1).hashCode());
    }

    @org.junit.jupiter.api.Test
    public void testBoxToString() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        String correctString = "";
        for (int i = 0; i < 9; ++i) {
            board.set(i / 3, i % 3, i + 1);
            correctString += (i + 1) + " ";
        }
        SudokuBox box = board.getBox(0, 0);
        Assertions.assertEquals(box.toString(), correctString);
    }
}