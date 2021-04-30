package pl.compprog.serializers;

public class SudokuBoardDaoFactory {
    public Dao getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao getJdbcDao(String tableName) {
        return new JdbcSudokuBoardDao(tableName);
    }
}
