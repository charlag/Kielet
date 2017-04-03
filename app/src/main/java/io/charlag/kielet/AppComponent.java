package io.charlag.kielet;

import javax.inject.Singleton;

import dagger.Component;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.charlag.kielet.data.source.TranslationsProviderModule;
import io.charlag.kielet.data.source.net.NetModule;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.charlag.kielet.data.source.storage.TranslationsStorageModule;

/**
 * Created by charlag on 03/04/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class,
        TranslationsStorageModule.class, TranslationsProviderModule.class})
public interface AppComponent {
    TranslationsStorage translationsStorage();
    TranslationsProvider translationsProvider();
}
