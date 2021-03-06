package io.charlag.kielet.history;

import javax.inject.Inject;

import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.common.TranslationsFragment;
import io.charlag.kielet.common.TranslationsListPresenter;

/**
 * Created by charlag on 03/04/2017.
 */

public class HistoryFragment extends TranslationsFragment implements HistoryContract.View {

    @Inject HistoryContract.Presenter presenter;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    protected void inject() {
        DaggerHistoryComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getAppComponent())
                .historyPresenterModule(new HistoryPresenterModule(this))
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}
