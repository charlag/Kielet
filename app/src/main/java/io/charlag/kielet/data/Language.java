package io.charlag.kielet.data;

/**
 * Created by charlag on 31/03/2017.
 */

public final class Language {
    private final String code;
    private final String title;

    public Language(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
