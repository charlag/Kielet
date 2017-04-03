package io.charlag.kielet.translator;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
    TranslatorContract.Presenter provideTranslatorPresenter(TranslationsProvider provider,
                                                            TranslationsStorage storage) {
        return new TranslatorPresenterImpl(provider, storage, AndroidSchedulers.mainThread(), view);
    }
}
