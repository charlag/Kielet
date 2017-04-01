package io.charlag.kielet.translator;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import io.charlag.kielet.util.Empty;
import io.reactivex.Observable;

/**
 * Created by charlag on 31/03/2017.
 */

interface TranslatorContract {
    interface Presenter {
        @NonNull Observable<TranslationViewModel> translations();

        @NonNull Observable<Pair<String, String>> chosenLanguages();

        @NonNull Observable<List<LanguageViewModel>> languageFromList();

        @NonNull Observable<List<LanguageViewModel>> languageToList();
    }

    interface View {
        @NonNull Observable<String> translationInput();

        @NonNull Observable<Empty> clearButtonPressed();

        @NonNull Observable<Void> languagesSwapPressed();

        @NonNull Observable<Void> languageFromPickPressed();

        @NonNull Observable<Void> languageToPickPressed();

        @NonNull Observable<Integer> languageFromPicked();

        @NonNull Observable<Integer> languageToPicked();
    }
}
