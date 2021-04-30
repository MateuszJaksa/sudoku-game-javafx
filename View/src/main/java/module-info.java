module View {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.logging;
    requires Model;

    opens pl.compprog.javafx to javafx.graphics, javafx.fxml;
}