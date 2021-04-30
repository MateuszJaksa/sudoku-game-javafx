package pl.compprog.exceptions;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import pl.compprog.LocaleSingleton;

public class CannotCloneException extends CloneNotSupportedException {
    private static final Logger LOGGER;

    static {
        try {
            LogManager.getLogManager().readConfiguration(CannotCloneException.class
                .getClassLoader().getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER = Logger.getLogger(CannotCloneException.class.getName());
    }

    public CannotCloneException(Throwable cause) {
        Locale locale = LocaleSingleton.getInstance().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        LOGGER.log(Level.INFO, bundle.getString("CustomExceptionThrownText")
            + ": ", bundle.getString("CannotCloneExceptionText"
            + " - " + cause.getMessage()));
    }
}
