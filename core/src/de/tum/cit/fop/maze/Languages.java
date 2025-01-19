package de.tum.cit.fop.maze;

import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {
    private ResourceBundle languages;
    private Locale locale;

    public Languages() {
        setLanguage("en");
    }

    public void setLanguage(String languageCode) {
        locale = new Locale(languageCode);
        languages = ResourceBundle.getBundle("language", locale);
    }

    public String get(String key) {
        try {
            return languages.getString(key);
        } catch (Exception e) {
            return "Key not found: " + key;
        }
    }
}
