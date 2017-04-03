package io.charlag.kielet.data.source.storage;

import javax.inject.Singleton;

import dagger.Component;
import io.charlag.kielet.AppModule;
import io.charlag.kielet.data.source.TranslationsProviderModule;

/**
 * Created by charlag on 03/04/2017.
 */

@Singleton
@Component(modules = {AppModule.class, TranslationsStorageModule.class,
        TranslationsProviderModule.class})
public interface TranslationsStorageComponent {
    TranslationsStorage translationsStorage();
}
