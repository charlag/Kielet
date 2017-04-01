package io.charlag.kielet.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.Translation;
import io.reactivex.Single;

/**
 * Created by charlag on 31/03/2017.
 */

public interface TranslationsProvider {
    Single<List<Language>> getLanguages(String ui);
    Single<Translation> translate(@Nullable String fromLanguage,
                                  @NonNull String toLanguage,
                                  @NonNull String text);
}
