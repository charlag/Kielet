package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by charlag on 01/04/2017.
 */

final class TranslationViewModel {
    @NonNull private final String text;

    TranslationViewModel(@NonNull String text) {
        this.text = text;
    }

    public @NonNull String getText() {
        return text;
    }
}
