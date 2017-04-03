package io.charlag.kielet.data;

import java.util.Collections;
import java.util.List;

/**
 * Created by charlag on 31/03/2017.
 */

public final class Translation {
    private transient long id;
    private transient boolean fav;
    private transient String originalText;
    private final String to;
    private final String from;
    private final List<String> text;

    @SuppressWarnings("unused") // let Moshi assign default value to the id and fav fields;
    private Translation() {
        id = -1;
        fav = false;
        originalText = null;
        from = null;
        to = null;
        text = null;
    }

    public Translation(long id, boolean fav, String originalText, String text, String to,
                       String from) {
        this.id = id;
        this.fav = fav;
        this.originalText = originalText;
        this.from = from;
        this.to = to;
        this.text = Collections.singletonList(text);
    }

    public Translation(String from, String to, List<String> text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public boolean isFav() {
        return fav;
    }

    public String getOriginalText() {
        return originalText;
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
