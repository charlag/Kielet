package io.charlag.kielet.translator;

/**
 * Created by charlag on 01/04/2017.
 */

final class TranslationViewModel {
    private final String text;

    TranslationViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
