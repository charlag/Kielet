package io.charlag.kielet.data;

import java.util.List;

/**
 * Created by charlag on 31/03/2017.
 */

public final class Translation {

    private final String from;
    private final String to;
    private final List<String> text;

    public Translation(String from, String to, List<String> text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<String> getText() {
        return text;
    }
}
