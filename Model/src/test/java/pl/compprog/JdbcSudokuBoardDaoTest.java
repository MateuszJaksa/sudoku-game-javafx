package pl.compprog;

import org.junit.jupiter.api.Assertions;
import pl.compprog.exceptions.CannotCloneException;
import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.NoClassException;
import pl.compprog.models.SudokuField;
import pl.compprog.serializers.JdbcSudokuBoardDao;
import pl.compprog.serializers.SudokuBoardDaoFactory;
import pl.compprog.solvers.BacktrackingSudokuSolver;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDaoTest {
    private static final String DIR_PATH = "test";
    private final ResourceBundle bundle;

    JdbcSudokuBoardDaoTest(){
        Locale locale = LocaleSingleton.getInstance().getLocale();
        bundle = ResourceBundle.getBundle("strings", locale);
    }

    @org.junit.jupiter.api.Test
    public void testJdbcWriteAndRead() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test")) {
            dao.write(board);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        SudokuBoard board2 = null;
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test")) {
            board2 = dao.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }
        SudokuField[][] arr1 = null;
        SudokuField[][] arr2 = null;
        try {
            arr1 = board.getBoard();
            arr2 = board2.getBoard();
        } catch (CannotCloneException e) {
            System.out.println(bundle.getString("CannotCloneExceptionText"));
        }
        Assertions.assertNotNull(arr1);
        Assertions.assertNotNull(arr2);
        Assertions.assertNotSame(board, board2);
        Assertions.assertArrayEquals(arr1, arr2);
    }

    @org.junit.jupiter.api.Test
    public void testJdbcReadTwoDifferentFiles() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board2.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (JdbcSudokuBoardDao dao1 = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test1")) {
            dao1.write(board1);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        try (JdbcSudokuBoardDao dao1 = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test1")) {
            board1 = dao1.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }

        try (JdbcSudokuBoardDao dao2 = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test2")) {
            dao2.write(board2);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        try (JdbcSudokuBoardDao dao2 = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test2")) {
            board2 = dao2.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }
        SudokuField[][] arr1 = null;
        SudokuField[][] arr2 = null;
        try {
            arr1 = board1.getBoard();
            arr2 = board2.getBoard();
        } catch (CannotCloneException e) {
            System.out.println(bundle.getString("CannotCloneExceptionText"));
        }
        Assertions.assertNotNull(arr1);
        Assertions.assertNotNull(arr2);
        Assertions.assertNotSame(board1, board2);
        Assertions.assertFalse(Arrays.equals(arr1, arr2));
    }

    @org.junit.jupiter.api.Test
    public void testJdbcReadSameFileTwice() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test")) {
            dao.write(board);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        SudokuBoard board1 = null;
        SudokuBoard board2 = null;
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test")) {
            board1 = dao.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao(DIR_PATH + "test")) {
            board2 = dao.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }
        SudokuField[][] arr1 = null;
        SudokuField[][] arr2 = null;
        try {
            arr1 = board1.getBoard();
            arr2 = board2.getBoard();
        } catch (CannotCloneException e) {
            System.out.println(bundle.getString("CannotCloneExceptionText"));
        }
        Assertions.assertNotSame(board1, board2);
        Assertions.assertArrayEquals(arr1, arr2);
    }

    @org.junit.jupiter.api.Test
    public void testJdbcReadNonExistingFile() {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        SudokuBoard board = null;
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) daoFactory.getJdbcDao("nofile")) {
            board = dao.read();
        } catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }
        Assertions.assertNull(board);
    }
}
