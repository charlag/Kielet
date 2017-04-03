package io.charlag.kielet.history;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.storage.TranslationsStorage;

/**
 * Created by charlag on 03/04/2017.
 */

@Module
class HistoryPresenterModule {
    private final HistoryContract.View view;

    HistoryPresenterModule(HistoryContract.View view) {
        this.view = view;
    }

    @Provides
    HistoryContract.Presenter provideHistoryPresenter(TranslationsStorage storage) {
        return new HistoryPresenterImpl(storage, view);
    }
}
