package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.Collections;
import java.util.List;

import io.charlag.kielet.data.source.TranslationsProvider;
import io.reactivex.Observable;

/**
 * Created by charlag on 01/04/2017.
 */

class TranslatorPresenterImpl implements TranslatorContract.Presenter {
    private final TranslationsProvider translationsProvider;
    private final TranslatorContract.View view;

    TranslatorPresenterImpl(TranslationsProvider translationsProvider,
                            TranslatorContract.View view) {
        this.translationsProvider = translationsProvider;
        this.view = view;
    }

    @NonNull
    @Override
    public Observable<TranslationViewModel> translations() {
        return view.translationInput()
                .filter(text -> !text.isEmpty()) // TODO: change to picked languages
                .flatMap(text -> translationsProvider.translate("ru", "en", text).toObservable())
                .map(translation -> new TranslationViewModel(translation.getText().get(0)));
    }

    @NonNull
    @Override
    public Observable<Pair<String, String>> chosenLanguages() {
        // TODO: implement
        return Observable.just(new Pair<>("Russian", "English"));
    }

    @NonNull
    @Override
    public Observable<List<LanguageViewModel>> languageFromList() {
        // TODO: implement
        LanguageViewModel viewModel = new LanguageViewModel("Russian");
        return Observable.just(Collections.singletonList(viewModel));
    }

    @NonNull
    @Override
    public Observable<List<LanguageViewModel>> languageToList() {
        // TODO: implement
        LanguageViewModel viewModel = new LanguageViewModel("English");
        return Observable.just(Collections.singletonList(viewModel));
    }
}
