package io.charlag.kielet.favorites;

import dagger.Component;
import io.charlag.kielet.AppComponent;
import io.charlag.kielet.annotation.FragmentScoped;

/**
 * Created by charlag on 03/04/2017.
 */

@FragmentScoped
@Component(dependencies = AppComponent.class, modules = FavoritesPresenterModule.class)
interface FavoritesComponent {
    void inject(FavoritesFragment favoritesFragment);
}
