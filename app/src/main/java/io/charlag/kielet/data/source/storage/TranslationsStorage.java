package io.charlag.kielet.data.source.storage;

import java.util.List;

import io.charlag.kielet.data.Translation;
import io.reactivex.Single;

/**
 * Created by charlag on 03/04/2017.
 */

public interface TranslationsStorage {
    void addTranslation(Translation translation);

    void saveTranslation(long id);

    void unsaveTranslation(long id);

    Single<List<Translation>> getTranslations();
}
