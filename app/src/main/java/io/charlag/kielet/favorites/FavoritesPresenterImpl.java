package io.charlag.kielet.favorites;

import java.util.List;

import io.charlag.kielet.common.TranslationsListBasePresenter;
import io.charlag.kielet.common.TranslationsView;
import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.TranslationsStorage;
import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

public class FavoritesPresenterImpl extends TranslationsListBasePresenter
        implements FavoritesContract.Presenter {

    Observable<List<Translation>> translations;

    public FavoritesPresenterImpl(TranslationsView view, TranslationsStorage storage) {
        super(view, storage);
    }

    @Override
    protected Observable<List<Translation>> realTranslationsList() {
        if (translations == null) {
            translations = storage.getFavorites().share().replay(1).autoConnect();
        }
        return translations;
    }
}
