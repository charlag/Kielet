package io.charlag.kielet.data.source;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.net.API;

/**
 * Created by charlag on 01/04/2017.
 */

@Module
public class TranslationsProviderModule {

    @Provides
    @Singleton
    TranslationsProvider provideTrasnalationsProvider(API api) {
        return new TranslationsProviderImpl(api);
    }
}
