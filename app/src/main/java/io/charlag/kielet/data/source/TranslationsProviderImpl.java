package io.charlag.kielet.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.net.API;
import io.reactivex.Single;

/**
 * Created by charlag on 31/03/2017.
 */

public final class TranslationsProviderImpl implements TranslationsProvider {

    private final API api;

    public TranslationsProviderImpl(API api) {
        this.api = api;
    }

    @Override
    public Single<List<Language>> getLanguages(String ui) {
        return api.languages(ui);
    }

    @Override
    public Single<Translation> translate(@Nullable String fromLanguage,
                                             @NonNull String toLanguage, @NonNull String text) {
        String langString;
        if (fromLanguage != null) {
            langString = fromLanguage + "-" + toLanguage;
        } else {
            langString = toLanguage;
        }
        return api.translate(langString, text).map(response -> {
            int delimIndex = response.getLang().indexOf('-');
            String from = response.getLang().substring(0, delimIndex);
            String to = response.getLang().substring(delimIndex + 1);
            return new Translation(from, to, response.getText());
        });
    }
}
