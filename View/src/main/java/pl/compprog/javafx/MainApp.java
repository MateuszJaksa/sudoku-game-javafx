package pl.compprog.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/pl/compprog/javafx/MainFXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Sudoku Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     public static void main(String[] args) {
     launch(args);
     }
     * The main() method is ignored in correctly deployed JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
