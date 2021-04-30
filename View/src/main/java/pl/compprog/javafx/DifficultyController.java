package pl.compprog.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.compprog.Difficulty;
import pl.compprog.LocaleSingleton;

public class DifficultyController implements Initializable {
    private Difficulty difficulty = Difficulty.EASY;

    @FXML
    private Button newGame;

    @FXML
    private Button easy;

    @FXML
    private Button medium;

    @FXML
    private Button hard;

    @FXML
    private Button languageMenu;

    @FXML
    private Label nameLabel;

    public void clickEasy(ActionEvent event) {
        difficulty = Difficulty.EASY;
    }

    public void clickMedium(ActionEvent event) {
        difficulty = Difficulty.MEDIUM;
    }

    public void clickHard(ActionEvent event) {
        difficulty = Difficulty.HARD;
    }

    @FXML
    private void handleLanguageActionMenu(ActionEvent event) {
        if (LocaleSingleton.getInstance().getLocale().getDisplayLanguage().equals("English")) {
            LocaleSingleton.getInstance().setLocale("pl");
        } else {
            LocaleSingleton.getInstance().setLocale("en");
        }
        loadLanguage();
    }

    public void changeScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/pl/compprog/javafx/GameFXML.fxml"));
        GameController gameController = new GameController();
        gameController.initData(difficulty);
        loader.setController(gameController);
        Parent loadedFxml = loader.load();
        Scene scene = new Scene(loadedFxml);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private void loadLanguage() {
        Locale locale = LocaleSingleton.getInstance().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        newGame.setText(bundle.getString("newgametext"));
        easy.setText(bundle.getString("easytext"));
        medium.setText(bundle.getString("mediumtext"));
        hard.setText(bundle.getString("hardtext"));
        languageMenu.setText(bundle.getString("languagetext"));
        nameLabel.setText(bundle.getString("titlegame"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLanguage();
    }
}
