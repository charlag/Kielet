package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;
import java.util.Locale;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by charlag on 01/04/2017.
 * <p>
 * UI for the Translator screen.
 */

final class TranslatorPresenterImpl implements TranslatorContract.Presenter {

    private final TranslationsProvider translationsProvider;
    private final TranslatorContract.View view;
    private final Observable<Pair<Language, Language>> chosenLanguages;
    private final ReplaySubject<Pair<Integer, Integer>> chosenLanguagesIndexes;
    private final Observable<List<Language>> languages;

    TranslatorPresenterImpl(TranslationsProvider translationsProvider,
                            TranslatorContract.View view) {
        this.translationsProvider = translationsProvider;
        this.view = view;

        languages = translationsProvider
                .getLanguages(Locale.getDefault().getLanguage())
                .toObservable()
                .share()
                .replay(1)
                .autoConnect();

        chosenLanguagesIndexes = ReplaySubject.createWithSize(1);

        // TODO: add "detect language" option
        Observable
                .combineLatest(view.languageFromPicked().startWith(0),
                        view.languageToPicked().startWith(0),
                        Pair::new)
                .subscribe(chosenLanguagesIndexes::onNext);

        view.languagesSwapPressed()
                .withLatestFrom(chosenLanguagesIndexes,
                        (p, indexes) -> new Pair<>(indexes.second, indexes.first))
                .subscribe(chosenLanguagesIndexes::onNext);

        // withLatestFrom*() won't work here because we get languages after we get indexes
        // and getting languages doesn't trigger onNext() (when it gets main observable it simply
        // checks that another one is ready).
        chosenLanguages = Observable.combineLatest(chosenLanguagesIndexes, languages,
                (indexes, langs) -> new Pair<>(langs.get(indexes.first), langs.get(indexes.second)))
                .share()
                .replay(1)
                .autoConnect();
    }

    @NonNull
    @Override
    public Observable<TranslationViewModel> translations() {
        return Observable.combineLatest(view.translationInput(), chosenLanguages, Pair::new)
                .filter(pair -> !pair.first.isEmpty())
                .flatMapSingle(pair ->
                        translationsProvider.translate(pair.second.first.getCode(),
                                pair.second.second.getCode(),
                                pair.first))
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

    private List<LanguageViewModel> languagesToViewModels(List<Language> languageList) {
        return Observable.fromIterable(languageList)
                .map(lang -> new LanguageViewModel(lang.getTitle()))
                .toList()
                .blockingGet();
    }

}
