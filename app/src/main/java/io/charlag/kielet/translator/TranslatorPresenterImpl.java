package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;
import java.util.Locale;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.reactivex.Observable;

/**
 * Created by charlag on 01/04/2017.
 *
 * UI for the Translator screen.
 */

final class TranslatorPresenterImpl implements TranslatorContract.Presenter {

    private final TranslationsProvider translationsProvider;
    private final TranslatorContract.View view;
    private final Observable<Pair<Language, Language>> chosenLanguages;
    private final Observable<Pair<Integer, Integer>> chosenLanguagesIndexes;
    private final Observable<List<Language>> languages;

    TranslatorPresenterImpl(TranslationsProvider translationsProvider,
                            TranslatorContract.View view) {
        this.translationsProvider = translationsProvider;
        this.view = view;

        languages = translationsProvider
                .getLanguages(Locale.getDefault().getLanguage())
                .toObservable()
                .share().replay(1).autoConnect();

        // TODO: add "detect language" option
        chosenLanguagesIndexes = Observable
                .combineLatest(view.languageFromPicked(), view.languageToPicked(), Pair::new)
                .startWith(new Pair<>(0, 0))
                .share()
                .replay(1)
                .autoConnect();
        chosenLanguages = chosenLanguagesIndexes
                .withLatestFrom(languages, (indexes, langs) ->
                        new Pair<>(langs.get(indexes.first), langs.get(indexes.second)));
    }

    @NonNull
    @Override
    public Observable<TranslationViewModel> translations() {
        Observable<TranslationViewModel> translations =
                Observable.combineLatest(view.translationInput(), chosenLanguages, Pair::new)
                        .filter(pair -> !pair.first.isEmpty())
                        .switchMap(pair ->
                                translationsProvider.translate(pair.second.first.getCode(),
                                        pair.second.second.getCode(),
                                        pair.first)
                                        .toObservable())
                        .map(translation -> new TranslationViewModel(translation.getText().get(0)));
        Observable<TranslationViewModel> clearText = view.clearButtonPressed()
                .map(v -> new TranslationViewModel("")); // cannot pass null through observable
        return Observable.merge(translations, clearText);
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
