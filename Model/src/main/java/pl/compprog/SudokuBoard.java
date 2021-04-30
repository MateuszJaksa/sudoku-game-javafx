package pl.compprog;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import pl.compprog.exceptions.CannotCloneException;
import pl.compprog.exceptions.InvalidValueException;
import pl.compprog.models.SudokuBox;
import pl.compprog.models.SudokuColumn;
import pl.compprog.models.SudokuField;
import pl.compprog.models.SudokuRow;
import pl.compprog.solvers.SudokuSolver;

public class SudokuBoard implements Cloneable, Serializable {
    private static final int SUDOKU_SIDE_LENGTH = 9;
    private final SudokuField[][] board = new SudokuField[SUDOKU_SIDE_LENGTH][SUDOKU_SIDE_LENGTH];
    private final SudokuSolver solver;
    private final SudokuBox[][] boxes =
        new SudokuBox[SUDOKU_SIDE_LENGTH / 3][SUDOKU_SIDE_LENGTH / 3];
    private final List<SudokuRow> rows;
    private final List<SudokuColumn> columns;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;
        SudokuRow[] arrayRows = new SudokuRow[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
            arrayRows[i] = new SudokuRow(board[i]);
        }
        rows = Arrays.asList(arrayRows);

        SudokuColumn[] arrayColumns = new SudokuColumn[9];
        for (int j = 0; j < 9; j++) {
            SudokuField[] column = new SudokuField[9];
            for (int i = 0; i < 9; i++) {
                column[i] = board[i][j];
            }
            arrayColumns[j] = new SudokuColumn(column);
        }
        columns = Arrays.asList(arrayColumns);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                SudokuField[] box = new SudokuField[9];
                int index = 0;
                for (int i = 3 * x; i < 3 * x + 3; i++) {
                    for (int j = 3 * y; j < 3 * y + 3; j++) {
                        box[index] = board[i][j];
                        index += 1;
                    }
                }
                boxes[x][y] = new SudokuBox(box);
            }
        }
    }

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
        return board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        board[x][y].setFieldValue(value);
    }

    public void setVerify(int x, int y, int value) throws InvalidValueException {
        board[x][y].setFieldValueVerify(value);
    }

    public SudokuRow getRow(int x) {
        return rows.get(x).clone();
    }

    public SudokuColumn getColumn(int y) {
        return columns.get(y).clone();
    }

    public SudokuBox getBox(int x, int y) {
        return boxes[x][y].clone();
    }

    public SudokuField[][] getBoard() throws CannotCloneException {
        SudokuField[][] newBoard = new SudokuField[SUDOKU_SIDE_LENGTH][SUDOKU_SIDE_LENGTH];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newBoard[i][j] = board[i][j].clone();
            }
        }
        return newBoard;
    }

    public void setBoardFieldModifiable(int x, int y, boolean value) {
        board[x][y].setModifiable(value);
    }

    public boolean isBoardFieldModifiable(int x, int y) {
        return board[x][y].isModifiable();
    }

    public SudokuField getField(int x, int y) {
        return board[x][y];
    }

    public SudokuBoard clone() {
        SudokuBoard clone = new SudokuBoard(solver);
        for (int i = 0; i < SUDOKU_SIDE_LENGTH; i++) {
            for (int j = 0; j < SUDOKU_SIDE_LENGTH; j++) {
                clone.set(i, j, board[i][j].getFieldValue());
            }
        }
        return clone;
    }
}

