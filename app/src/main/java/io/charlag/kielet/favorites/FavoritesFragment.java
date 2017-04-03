package io.charlag.kielet.favorites;

import javax.inject.Inject;

import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.common.TranslationsFragment;
import io.charlag.kielet.common.TranslationsListPresenter;

/**
 * Created by charlag on 03/04/2017.
 */

public class FavoritesFragment extends TranslationsFragment implements FavoritesContract.View {

    public static FavoritesFragment newInstance() { return new FavoritesFragment(); }

    public FavoritesFragment() {
    }

    @Inject FavoritesContract.Presenter presenter;

    @Override
    protected void inject() {
        DaggerFavoritesComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getAppComponent())
                .favoritesPresenterModule(new FavoritesPresenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected TranslationsListPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_translations;
    }

    @Override
    protected int getListId() {
        return R.id.list_translations;
    }
}
