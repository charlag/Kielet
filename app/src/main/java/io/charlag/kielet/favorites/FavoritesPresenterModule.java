package io.charlag.kielet.favorites;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.storage.TranslationsStorage;

/**
 * Created by charlag on 03/04/2017.
 */

@Module
public class FavoritesPresenterModule {
    private final FavoritesContract.View view;

    public FavoritesPresenterModule(FavoritesContract.View view) {
        this.view = view;
    }

    @Provides
    FavoritesContract.Presenter providePresenter(TranslationsStorage storage) {
        return new FavoritesPresenterImpl(view, storage);
    }
}
