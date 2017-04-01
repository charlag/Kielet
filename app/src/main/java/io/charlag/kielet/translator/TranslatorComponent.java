package io.charlag.kielet.translator;

import dagger.Component;
import io.charlag.kielet.annotation.FragmentScoped;
import io.charlag.kielet.data.source.TranslationsProviderComponent;

/**
 * Created by charlag on 01/04/2017.
 */

@FragmentScoped
@Component(dependencies = TranslationsProviderComponent.class,
        modules = TranslatorPresenterModule.class)
public interface TranslatorComponent {

    void inject(TranslatorFragment translatorFragment);
}
