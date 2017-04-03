package io.charlag.kielet.common;

/**
 * Created by charlag on 03/04/2017.
 */

public final class TranslationItemViewModel {
    private final boolean isFav;
    private final String originalText;
    private final String translatedText;
    private final String languageFrom;
    private final String languageTo;

    public TranslationItemViewModel(boolean isFav, String originalText, String translatedText,
                                    String languageFrom, String languageTo) {
        this.isFav = isFav;
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public boolean isFav() {
        return isFav;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getLanguageFrom() {
        return languageFrom;
    }

    public String getLanguageTo() {
        return languageTo;
    }
}
