package pl.compprog;

import java.util.Locale;

public class LocaleSingleton {
    private static LocaleSingleton instance;
    private static Locale locale;

    private LocaleSingleton() {
        locale = new Locale("en");
    }

    public static LocaleSingleton getInstance() {
        if (instance == null) {
            instance = new LocaleSingleton();
        }
        return instance;
    }

    public void setLocale(String lang) {
        locale = new Locale(lang);
    }

    public Locale getLocale() {
        return locale;
    }
}
