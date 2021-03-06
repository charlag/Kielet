package io.charlag.kielet.history;

import java.util.List;

import io.charlag.kielet.common.TranslationsListBasePresenter;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

class HistoryPresenterImpl extends TranslationsListBasePresenter
        implements HistoryContract.Presenter {

    private Observable<List<Translation>> translations;

    HistoryPresenterImpl(TranslationsStorage storage, HistoryContract.View view) {
        super(view, storage);
    }

    @Override
    protected Observable<List<Translation>> realTranslationsList() {
        if (translations == null) {
            translations = storage.getTranslations().share().replay(1).autoConnect();
        }
        return translations;
    }
}
