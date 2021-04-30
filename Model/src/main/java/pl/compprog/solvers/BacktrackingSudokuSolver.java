package pl.compprog.solvers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import pl.compprog.SudokuBoard;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    @Override
    public boolean solve(SudokuBoard board) {
        int row = -1;
        int column = -1;
        boolean isFilled = true;
        for (int j = 0;j < 9; ++j) {
            for (int i = 0; i < 9; ++i) {
                if (board.get(i, j) == 0) {
                    row = i;
                    column = j;
                    isFilled = false;
                    break;
                }
            }
            if (!isFilled) {
                break;
            }
        }

        if (isFilled) {
            return true;
        }
        ArrayList<Integer> possibleNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; ++i) {
            possibleNumbers.add(i);
        }
        for (int num = 0; num < 9; num++) {
            Collections.shuffle(possibleNumbers);
            int number = possibleNumbers.get(0);
            board.set(row, column, number);
            if (board.getColumn(column).verify() && board.getRow(row).verify()
                    && board.getBox(row / 3, column / 3).verify()) {
                if (solve(board)) {
                    return true;
                } else {
                    board.set(row, column, 0);
                }
            } else {
                board.set(row, column, 0);
                possibleNumbers.remove(0);
            }
        }
        return false;
    }

}
