package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by charlag on 01/04/2017.
 * <p>
 * UI for the Translator screen.
 */

final class TranslatorPresenterImpl implements TranslatorContract.Presenter {

    private final TranslationsProvider translationsProvider;
    private final TranslationsStorage translationsStorage;
    private final TranslatorContract.View view;
    private final Observable<Pair<Language, Language>> chosenLanguages;
    private final ReplaySubject<Pair<Integer, Integer>> chosenLanguagesIndexes;
    private final Observable<List<Language>> languages;
    // used to save translations to the database with pause (debounce)
    private final PublishSubject<Pair<Translation, String>> addToStorage = PublishSubject.create();

    private final CompositeDisposable disposable = new CompositeDisposable();

    TranslatorPresenterImpl(TranslationsProvider translationsProvider,
                            TranslationsStorage translationsStorage,
                            Scheduler scheduler,
                            TranslatorContract.View view) {
        this.translationsProvider = translationsProvider;
        this.translationsStorage = translationsStorage;
        this.view = view;

        languages = translationsProvider
                .getLanguages(Locale.getDefault().getLanguage())
                .retry(3)
                .toObservable()
                .share()
                .replay(1)
                .autoConnect();

        chosenLanguagesIndexes = ReplaySubject.createWithSize(1);

        // TODO: add "detect language" option
        disposable.add(Observable
                .combineLatest(view.languageFromPicked().startWith(0),
                        view.languageToPicked().startWith(0),
                        Pair::new)
                .subscribe(chosenLanguagesIndexes::onNext)
        );

        disposable.add(view.languagesSwapPressed()
                .withLatestFrom(chosenLanguagesIndexes,
                        (p, indexes) -> new Pair<>(indexes.second, indexes.first))
                .subscribe(chosenLanguagesIndexes::onNext)
        );

        // withLatestFrom*() won't work here because we get languages after we get indexes
        // and getting languages doesn't trigger onNext() (when it gets main observable it simply
        // checks that another one is ready).
        chosenLanguages = Observable.combineLatest(chosenLanguagesIndexes, languages,
                (indexes, langs) -> new Pair<>(langs.get(indexes.first), langs.get(indexes.second)))
                .share()
                .replay(1)
                .autoConnect();

        disposable.add(addToStorage.debounce(1, TimeUnit.SECONDS, scheduler)
                .subscribe(pair -> this.addTranslation(pair.first, pair.second))
        );
    }

    @NonNull
    @Override
    public Observable<TranslationViewModel> translations() {
        return Observable.combineLatest(view.translationInput(), chosenLanguages, Pair::new)
                .filter(pair -> !pair.first.isEmpty())
                .switchMap(pair -> translationsProvider.translate(pair.second.first.getCode(),
                        pair.second.second.getCode(),
                        pair.first)
                        .doOnSuccess(tr -> addToStorage.onNext(new Pair<>(tr, pair.first)))
                        .toObservable()
                        .onErrorResumeNext(Observable.empty())
                )
                .map(translation -> new TranslationViewModel(translation.getText().get(0)));
    }

    @NonNull
    @Override
    public Observable<String> inputFieldText() {
        return view.clearButtonPressed().map(v -> "");
    }

    @NonNull
    @Override
    public Observable<Pair<Integer, Integer>> chosenLanguages() {
        return chosenLanguagesIndexes;
    }

    @NonNull
    @Override
    public Observable<List<LanguageViewModel>> languageFromList() {
        return languages.map(this::languagesToViewModels);
    }

    @NonNull
    @Override
    public Observable<List<LanguageViewModel>> languageToList() {
        return languages.map(this::languagesToViewModels);
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    private List<LanguageViewModel> languagesToViewModels(List<Language> languageList) {
        return Observable.fromIterable(languageList)
                .map(lang -> new LanguageViewModel(lang.getTitle()))
                .toList()
                .blockingGet();
    }

    void addTranslation(Translation translation, String originalText) {
        final Translation newTranslation = new Translation(-1, false, originalText,
                translation.getText().get(0), translation.getTo(), translation.getFrom());
        translationsStorage.addTranslation(newTranslation);
    }

}
