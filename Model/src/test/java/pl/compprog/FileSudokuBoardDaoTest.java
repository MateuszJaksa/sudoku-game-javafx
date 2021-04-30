package pl.compprog;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import pl.compprog.exceptions.CannotCloneException;
import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.NoClassException;
import pl.compprog.models.SudokuField;
import pl.compprog.serializers.FileSudokuBoardDao;
import pl.compprog.serializers.SudokuBoardDaoFactory;
import pl.compprog.solvers.BacktrackingSudokuSolver;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class FileSudokuBoardDaoTest {
    private static final String DIR_PATH = "test";
    private final ResourceBundle bundle;

    FileSudokuBoardDaoTest(){
        Locale locale = LocaleSingleton.getInstance().getLocale();
        bundle = ResourceBundle.getBundle("strings", locale);
    }

    @BeforeAll
    static void beforeAll() {
        new File(DIR_PATH).mkdirs();
    }

    @org.junit.jupiter.api.Test
    public void testWriteAndRead() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        SudokuBoard board2 = null;
        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test")) {
            try {
                dao.write(board);
            } catch (InputOutputException e) {
                System.out.println(bundle.getString("InputOutputExceptionText"));
            }

            try {
                board2 = dao.read();
            } catch (InputOutputException e) {
                System.out.println(bundle.getString("InputOutputExceptionText"));
            } catch (NoClassException e) {
                System.out.println(bundle.getString("NoClassExceptionText"));
            }
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
        Assertions.assertArrayEquals(arr1, arr2);
    }

    @org.junit.jupiter.api.Test
    public void testReadTwoDifferentFiles() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board2.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (FileSudokuBoardDao dao1 = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test1")) {
            dao1.write(board1);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }

        try (FileSudokuBoardDao dao1 = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test1")) {
            board1 = dao1.read();
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e) {
            System.out.println(bundle.getString("NoClassExceptionText"));
        }

        try (FileSudokuBoardDao dao2 = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test2")) {
            dao2.write(board2);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }

        try (FileSudokuBoardDao dao2 = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test2")) {
            board2 = dao2.read();
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        } catch (NoClassException e) {
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
        Assertions.assertFalse(Arrays.equals(arr1, arr2));
    }

    @org.junit.jupiter.api.Test
    public void testReadSameFileTwice() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test")) {
            dao.write(board);
        } catch (InputOutputException e) {
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }

        SudokuBoard board1 = null;
        SudokuBoard board2 = null;
        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test")) {
            board1 = dao.read();
        }
        catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }

        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/test")) {
            board2 = dao.read();
        }
        catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        catch (NoClassException e){
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
        Assertions.assertArrayEquals(arr1, arr2);
    }

    @org.junit.jupiter.api.Test
    public void testReadNonExistingFile() {
        SudokuBoardDaoFactory daoFactory = new SudokuBoardDaoFactory();
        SudokuBoard board = null;
        try (FileSudokuBoardDao dao = (FileSudokuBoardDao) daoFactory.getFileDao(DIR_PATH + "/nofile")) {
            board = dao.read();
        }
        catch (InputOutputException e){
            System.out.println(bundle.getString("InputOutputExceptionText"));
        }
        catch (NoClassException e){
            System.out.println(bundle.getString("NoClassExceptionText"));
        }
        Assertions.assertNull(board);
    }

    @AfterAll
    static void afterAll() {
        File dir = new File(DIR_PATH);
        for (File file: Objects.requireNonNull(dir.listFiles())) {
            file.delete();
        }
        dir.delete();
    }
}