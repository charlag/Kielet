package io.charlag.kielet.data.source.net.response;

import java.util.List;

/**
 * Created by charlag on 31/03/2017.
 */

public final class TranslationResponse {
    private final int code;
    private final String lang;
    private final List<String> text;

    public TranslationResponse(int code, String lang, List<String> text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }
}
