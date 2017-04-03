package io.charlag.kielet.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.common.TranslationItemViewModel;
import io.charlag.kielet.common.TranslationsListAdapter;
import io.reactivex.Observable;

/**
 * Created by charlag on 03/04/2017.
 */

public class HistoryFragment extends Fragment implements HistoryContract.View {

    @Inject HistoryContract.Presenter presenter;

    @BindView(R.id.list_history) RecyclerView historyList;

    private TranslationsListAdapter adapter;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        historyList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<TranslationItemViewModel> vms = new ArrayList<>();
        vms.add(new TranslationItemViewModel(false, "Hey", "HOo", "en", "kd"));
        vms.add(new TranslationItemViewModel(true, "Hey", "HOo", "pc", "sl"));
        adapter = new TranslationsListAdapter(vms);
        historyList.setAdapter(adapter);

        DaggerHistoryComponent.builder()
                .appComponent(((App) getActivity().getApplication()).getAppComponent())
                .historyPresenterModule(new HistoryPresenterModule(this))
                .build()
                .inject(this);

        return view;
    }

    @Override
    public Observable<Integer> favoriteSelected() {
        // potential memory problem
        return Observable.create(subscriber -> adapter.setFavPressedListener(subscriber::onNext));
    }
}
