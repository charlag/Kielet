package io.charlag.kielet.common;

import java.util.List;

import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by charlag on 03/04/2017.
 */

public abstract class TranslationsListBasePresenter implements TranslationsListPresenter {

    protected final CompositeDisposable disposable = new CompositeDisposable();
    protected TranslationsStorage storage;

    public TranslationsListBasePresenter(TranslationsView view, TranslationsStorage storage) {
        this.storage = storage;
        disposable.add(view.favoriteSelected()
                .withLatestFrom(realTranslationsList(), (index, list) -> list.get(index))
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

    public Observable<List<TranslationItemViewModel>> translations() {
        return realTranslationsList().map(this::translationsToViewModels);
    }

    protected abstract Observable<List<Translation>> realTranslationsList();

    List<TranslationItemViewModel> translationsToViewModels(List<Translation> translations) {
        return Observable.fromIterable(translations)
                .map(tr -> new TranslationItemViewModel(tr.isFav(), tr.getOriginalText(),
                        tr.getText().get(0), tr.getFrom(), tr.getTo()))
                .toList()
                .blockingGet();
    }

    @Override
    public void unsubscribe() {
        disposable.dispose();
    }
}
