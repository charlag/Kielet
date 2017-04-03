package io.charlag.kielet.history;

import java.util.List;

import io.charlag.kielet.common.TranslationItemViewModel;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by charlag on 03/04/2017.
 */

class HistoryPresenterImpl implements HistoryContract.Presenter {

    private final TranslationsStorage storage;
    private final CompositeDisposable disposable = new CompositeDisposable();

    HistoryPresenterImpl(TranslationsStorage storage, HistoryContract.View view) {
        this.storage = storage;

        Observable<List<Translation>> translations = storage.getTranslations().replay(1).autoConnect();

        disposable.add(view.favoriteSelected()
                .withLatestFrom(translations, (index, list) -> list.get(index))
                .observeOn(Schedulers.io())
                .subscribe(translation -> {
                    if (translation.isFav()) {
                        storage.unsaveTranslation(translation.getId());
                    } else {
                        storage.saveTranslation(translation.getId());
                    }
                })
        );
    }

    @Override
    public Observable<List<TranslationItemViewModel>> translations() {
        return storage.getTranslations().map(this::translationsToViewModels);
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    List<TranslationItemViewModel> translationsToViewModels(List<Translation> translations) {
        return Observable.fromIterable(translations)
                .map(tr -> new TranslationItemViewModel(tr.isFav(), tr.getOriginalText(),
                        tr.getText().get(0), tr.getFrom(), tr.getTo()))
                .toList()
                .blockingGet();
    }
}
