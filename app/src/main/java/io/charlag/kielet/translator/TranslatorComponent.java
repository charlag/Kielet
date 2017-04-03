package io.charlag.kielet.translator;

import dagger.Component;
import io.charlag.kielet.AppComponent;
import io.charlag.kielet.annotation.FragmentScoped;

/**
 * Created by charlag on 01/04/2017.
 */

@FragmentScoped
@Component(dependencies = AppComponent.class,
        modules = TranslatorPresenterModule.class)
public interface TranslatorComponent {

    void inject(TranslatorFragment translatorFragment);
}
