package io.charlag.kielet.util;

/**
 * Created by charlag on 01/04/2017.
 */

public enum Empty {
    INSTANCE;

    public static Empty fromAny(Object object) {
        return INSTANCE;
    }
}
