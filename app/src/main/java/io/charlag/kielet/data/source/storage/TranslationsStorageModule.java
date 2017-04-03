package io.charlag.kielet.data.source.storage;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by charlag on 03/04/2017.
 */

@Module
public class TranslationsStorageModule {
    @Singleton
    @Provides
    TranslationsStorage provideTranslationsStorage(Context context) {
        return new TranslationsStorageImpl(context);
    }
}
