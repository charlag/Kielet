package io.charlag.kielet.common;

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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by charlag on 03/04/2017.
 */

public abstract class TranslationsFragment extends Fragment implements TranslationsView {

    abstract protected void inject();
    abstract protected TranslationsListPresenter getPresenter();
    abstract protected int getLayoutRes();
    abstract protected int getListId();

    private RecyclerView translationsList;
    private TranslationsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        translationsList = (RecyclerView) view.findViewById(getListId());
        translationsList.setLayoutManager(new LinearLayoutManager(getContext()));
        List<TranslationItemViewModel> translations = new ArrayList<>();
        adapter = new TranslationsListAdapter(translations);
        translationsList.setAdapter(adapter);

        inject();

        getPresenter().translations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trs -> {
                    translations.clear();
                    translations.addAll(trs);
                    adapter.notifyDataSetChanged();
                });

        return view;
    }

    @Override
    public Observable<Integer> favoriteSelected() {
        return Observable.create(subscriber -> adapter.setFavPressedListener(subscriber::onNext));
    }
}
