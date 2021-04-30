package pl.compprog.javafx;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.compprog.Difficulty;
import pl.compprog.LocaleSingleton;
import pl.compprog.SudokuBoard;
import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.InvalidValueException;
import pl.compprog.exceptions.NoClassException;
import pl.compprog.serializers.Dao;
import pl.compprog.serializers.SudokuBoardDaoFactory;
import pl.compprog.solvers.BacktrackingSudokuSolver;

public class GameController implements Initializable {
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private SudokuBoard board;
    private Difficulty difficulty;
    private Locale locale;
    private TextInputDialog dialog;
    private final TextField[][] textFields = new TextField[9][9];
    private final StringConverter<Number> converter = new NumberStringConverter();
    private final DecimalFormat format = new DecimalFormat( "#" );

    @FXML
    private Button languageGame;

    @FXML
    private Button load;

    @FXML
    private Button save;

    @FXML
    private GridPane grid;

    @FXML
    private Label authors;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        removeDigits();
        locale = new Locale("en");
        loadLanguage();
        handleAuthors();
        dialog = new TextInputDialog();
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        dialog.setTitle(bundle.getString("DialogTitle"));
        dialog.setHeaderText(bundle.getString("DialogTitle"));
        dialog.setContentText(bundle.getString("DialogContent"));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new TextField();
                textFields[i][j].setPrefSize(40, 40);
                Bindings.bindBidirectional(textFields[i][j].textProperty(), board.getField(i, j).getProperty(), converter);
                final int finalI = i, finalJ = j;
                if (!board.getField(finalI, finalJ).isModifiable()) {
                    textFields[finalI][finalJ].setEditable(false);
                }
                textFields[i][j].setTextFormatter( new TextFormatter<>(c ->
                {
                    if ( c.getControlNewText().isEmpty() )
                    {
                        return c;
                    }

                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse( c.getControlNewText(), parsePosition );

                    if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                    {
                        return null;
                    }
                    else
                    {
                        return c;
                    }
                }));

                textFields[i][j].textProperty().addListener((observableValue, s, t1) -> handleDigitClick(t1, finalI, finalJ));
                grid.add(textFields[i][j], i, j);
            }
        }

    }

    public void initData(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try (Dao dao = factory.getJdbcDao(result.get())) {
                dao.write(board);
            } catch (InputOutputException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleLoadAction(ActionEvent event) {
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try (Dao dao = factory.getJdbcDao(result.get())) {
                board = (SudokuBoard) dao.read();
                for(int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j ++) {
                        textFields[i][j].textProperty().set(String.valueOf(board.getField(i, j).getProperty().get()));
                    }
                }
            } catch (InputOutputException | NoClassException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDigitClick(String input, int x, int y) {
        try {
            switch (input) {
                case "1" -> board.setVerify(x, y, 1);
                case "2" -> board.setVerify(x, y, 2);
                case "3" -> board.setVerify(x, y, 3);
                case "4" -> board.setVerify(x, y, 4);
                case "5" -> board.setVerify(x, y, 5);
                case "6" -> board.setVerify(x, y, 6);
                case "7" -> board.setVerify(x, y, 7);
                case "8" -> board.setVerify(x, y, 8);
                case "9" -> board.setVerify(x, y, 9);
            }
        } catch (InvalidValueException e) {
            board.set(x, y, 0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
            alert.setTitle(bundle.getString("AlertBadTitle"));
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("AlertBadContent"));
            alert.showAndWait();
        }
    }

    private void removeDigits() {
        int removedDigits;
        Random r = new Random();
        switch (difficulty) {
            case EASY -> removedDigits = 20;
            case MEDIUM -> removedDigits = 40;
            case HARD -> removedDigits = 60;
            default -> removedDigits = 0;
        }
        while (removedDigits > 0) {
            int randomRow = r.nextInt(9);
            int randomColumn = r.nextInt(9);
            if (board.get(randomRow, randomColumn) != 0) {
                board.set(randomRow, randomColumn, 0);
                board.setBoardFieldModifiable(randomRow, randomColumn, true);
                removedDigits--;
            }
        }
    }

    @FXML
    private void handleLanguageActionGame(ActionEvent event) {
        if (LocaleSingleton.getInstance().getLocale().getDisplayLanguage().equals("English")) {
            LocaleSingleton.getInstance().setLocale("pl");
        } else {
            LocaleSingleton.getInstance().setLocale("en");
        }
        loadLanguage();
    }

    private void loadLanguage() {
        locale = LocaleSingleton.getInstance().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        languageGame.setText(bundle.getString("languagetext"));
        load.setText(bundle.getString("loadtext"));
        save.setText(bundle.getString("savetext"));
    }

    private void handleAuthors() {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.compprog.javafx.Authors", locale);
        authors.setText(bundle.getString("Author1") + "\n" + bundle.getString("Author2"));
    }
}
