package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import io.charlag.kielet.common.BasePresenter;
import io.charlag.kielet.util.Empty;
import io.reactivex.Observable;

/**
 * Created by charlag on 31/03/2017.
 *
 * Specifies contract between View and BasePresenter for Translator screen
 */

interface TranslatorContract {
    interface Presenter extends BasePresenter {
        @NonNull Observable<TranslationViewModel> translations();

        @NonNull Observable<String> inputFieldText();

        @NonNull Observable<Pair<Integer, Integer>> chosenLanguages();

        @NonNull Observable<List<LanguageViewModel>> languageFromList();

        @NonNull Observable<List<LanguageViewModel>> languageToList();
    }

    interface View {
        @NonNull Observable<String> translationInput();

        @NonNull Observable<Empty> clearButtonPressed();

        @NonNull Observable<Empty> languagesSwapPressed();

        @NonNull Observable<Integer> languageFromPicked();

        @NonNull Observable<Integer> languageToPicked();
    }
}
