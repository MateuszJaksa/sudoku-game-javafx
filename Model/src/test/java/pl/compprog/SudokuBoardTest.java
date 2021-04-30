package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.exceptions.CannotCloneException;
import pl.compprog.models.SudokuField;
import pl.compprog.solvers.BacktrackingSudokuSolver;

public class SudokuBoardTest {
    public SudokuBoardTest(){
    }

    @org.junit.jupiter.api.Test
    public void testBoardLayout() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        boolean correct = true;
        for(int column=0;column<9;++column) {
            for (int row = 0; row < 9; ++row) {
                if (!(board.getColumn(column).verify() && board.getRow(row).verify() && board.getBox(row / 3, column / 3).verify())) {
                    correct = false;
                }
            }
        }
        Assertions.assertTrue(correct);
    }

    @org.junit.jupiter.api.Test
    public void testIfBoardsDifferent() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuField[][] filledBoard = null;
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board2.solveGame();
        SudokuField[][] filledBoard2 = null;
        try {
            filledBoard = board.getBoard();
            filledBoard2 = board2.getBoard();
        } catch (CannotCloneException e) {
            System.out.println("Cloning impossible");
        }

        boolean different = false;
        for(int j=0;j<9;++j) {
            for (int i = 0; i < 9; ++i) {
                if(filledBoard[i][j].getFieldValue() != filledBoard2[i][j].getFieldValue()){
                    different = true;
                    break;
                }
            }
            if(different){
                break;
            }
        }
        Assertions.assertTrue(different);
    }

    @org.junit.jupiter.api.Test
    public void testBoardClone() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoard board1 = null;
        board1 = board.clone();

        SudokuField[][] arr1 = null;
        SudokuField[][] arr2 = null;
        try {
            arr1 = board.getBoard();
            arr2 = board1.getBoard();
        } catch (CannotCloneException e) {
            System.out.println("Cloning impossible");
        }
        Assertions.assertNotSame(board, board1);
        Assertions.assertNotNull(arr1);
        Assertions.assertArrayEquals(arr1, arr2);
    }
}
