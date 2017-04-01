package io.charlag.kielet.translator;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.TranslationsProvider;

/**
 * Created by charlag on 01/04/2017.
 */

@Module
class TranslatorPresenterModule {

    private final TranslatorContract.View view;

    TranslatorPresenterModule(TranslatorContract.View view) {
        this.view = view;
    }

    @Provides
    TranslatorContract.Presenter provideTranslatorPresenter(TranslationsProvider provider) {
        return new TranslatorPresenterImpl(provider, view);
    }
}
