package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by charlag on 01/04/2017.
 */

class TranslatorPresenterImpl implements TranslatorContract.Presenter {

    private final TranslationsProvider translationsProvider;
    private final TranslatorContract.View view;
    private final Observable<Language> fromLanguage;
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
        fromLanguage = view.languageFromPicked()
                .withLatestFrom(languages, (index, list) -> list.get(index));
    }

    @NonNull
    @Override
    public Observable<TranslationViewModel> translations() {
        Observable<TranslationViewModel> translations = view.translationInput()
                .filter(text -> !text.isEmpty()) // TODO: change to picked languages
                .flatMap(text -> translationsProvider.translate("ru", "en", text).toObservable())
                .map(translation -> new TranslationViewModel(translation.getText().get(0)));
        Observable<TranslationViewModel> clearText = view.clearButtonPressed()
                .map(v -> new TranslationViewModel("")); // cannot pass null through observable
        return Observable.merge(translations, clearText);
    }

    @NonNull
    @Override
    public Observable<Pair<String, String>> chosenLanguages() {
        // TODO: implement
        return Observable.just(new Pair<>("Russian", "English")).cache();
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
