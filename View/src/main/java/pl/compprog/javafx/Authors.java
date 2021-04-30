package pl.compprog.javafx;

import java.util.ListResourceBundle;

public class Authors extends ListResourceBundle {
    private static final Object[][] contents = {
        { "Author1", "Bartlomiej Baranowski"},
        { "Author2", "Mateusz Jaksa"},
    };

    public Object[][] getContents() {
        return contents;
    }
}
