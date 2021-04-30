package pl.compprog.serializers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import pl.compprog.LocaleSingleton;
import pl.compprog.SudokuBoard;
import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.NoClassException;
import pl.compprog.solvers.BacktrackingSudokuSolver;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String tableName;
    private final ResourceBundle bundle;

    public JdbcSudokuBoardDao(String tableName) {
        this.tableName = tableName;
        Locale locale = LocaleSingleton.getInstance().getLocale();
        bundle = ResourceBundle.getBundle("strings", locale);
    }

    @Override
    public SudokuBoard read() throws InputOutputException, NoClassException {
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/sudoku", "postgres", "admin")) {
            System.out.println(bundle.getString("PostgreSQLConnectionText"));
            connection.setAutoCommit(false);
            preStatement = connection
                .prepareStatement("SELECT * FROM public.sudoku_board" + " WHERE name=?");
            preStatement.setString(1, tableName);
            resultSet = preStatement.executeQuery();
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            if (resultSet.next()) {
                preStatement = connection
                    .prepareStatement("SELECT * FROM public.sudoku_field WHERE board_id=?");
                preStatement.setInt(1, resultSet.getInt("id"));
                ResultSet fields = preStatement.executeQuery();
                while (fields.next()) {
                    int i = fields.getInt("field_number");
                    board.set(i / 9, i % 9, fields.getInt("value"));
                    board.setBoardFieldModifiable(i / 9, i % 9, fields.getBoolean("modifiable"));
                }
                connection.commit();
            } else {
                throw new NoClassException();
            }
            return board;
        } catch (SQLException e) {
            throw new InputOutputException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new InputOutputException(e);
                }
            }
            if (preStatement != null) {
                try {
                    preStatement.close();
                } catch (SQLException e) {
                    throw new InputOutputException(e);
                }
            }
        }
    }

    @Override
    public void write(SudokuBoard obj) throws InputOutputException {
        PreparedStatement preStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/sudoku", "postgres", "admin")) {
            System.out.println(bundle.getString("PostgreSQLConnectionText"));
            connection.setAutoCommit(false);
            preStatement = connection
                .prepareStatement("SELECT * FROM public.sudoku_board WHERE name=?");
            preStatement.setString(1, tableName);
            resultSet = preStatement.executeQuery();
            int id;
            boolean next = resultSet.next();
            if (next) {
                id = resultSet.getInt("id");
            } else {
                preStatement = connection
                    .prepareStatement("INSERT INTO public.sudoku_board(name) values (?)");
                preStatement.setString(1, tableName);
                preStatement.executeUpdate();
                connection.commit();
                preStatement = connection
                    .prepareStatement("SELECT * FROM public.sudoku_board WHERE name=?");
                preStatement.setString(1, tableName);
                ResultSet board = preStatement.executeQuery();
                board.next();
                id = board.getInt("id");
            }
            preStatement = connection
                .prepareStatement("DELETE FROM public.sudoku_field WHERE board_id=?");
            preStatement.setInt(1, id);
            preStatement.executeUpdate();
            connection.commit();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String query = "INSERT INTO "
                        + "public.sudoku_field(board_id, field_number, value, modifiable)"
                        + "values (?, ?, ?, ?)";
                    preStatement = connection.prepareStatement(query);
                    preStatement.setInt(1, id);
                    preStatement.setInt(2, 9 * i + j);
                    preStatement.setInt(3, obj.get(i, j));
                    preStatement.setBoolean(4, obj.isBoardFieldModifiable(i, j));
                    preStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new InputOutputException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new InputOutputException(e);
                }
            }
            if (preStatement != null) {
                try {
                    preStatement.close();
                } catch (SQLException e) {
                    throw new InputOutputException(e);
                }
            }
        }
    }

    @Override
    public void close() {

    }
}
