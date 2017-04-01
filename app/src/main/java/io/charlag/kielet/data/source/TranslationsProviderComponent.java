package io.charlag.kielet.data.source;

import javax.inject.Singleton;

import dagger.Component;
import io.charlag.kielet.data.source.net.NetModule;

/**
 * Created by charlag on 01/04/2017.
 */

@Singleton
@Component(modules = {NetModule.class, TranslationsProviderModule.class})
public interface TranslationsProviderComponent {
    TranslationsProvider translationsProvider();
}
