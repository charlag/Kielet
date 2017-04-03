package io.charlag.kielet.history;

import java.util.List;

import io.charlag.kielet.common.TranslationItemViewModel;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

class HistoryPresenterImpl implements HistoryContract.Presenter {

    private final TranslationsStorage storage;

    HistoryPresenterImpl(TranslationsStorage storage, HistoryContract.View view) {
        this.storage = storage;
    }

    @Override
    public Observable<List<TranslationItemViewModel>> translations() {
        return storage.getTranslations().map(this::translationsToViewModels).toObservable();
    }

    List<TranslationItemViewModel> translationsToViewModels(List<Translation> translations) {
        return Observable.fromIterable(translations)
                .map(tr -> new TranslationItemViewModel(tr.isFav(), tr.getOriginalText(),
                        tr.getText().get(0), tr.getFrom(), tr.getTo()))
                .toList()
                .blockingGet();
    }
}
