package io.charlag.kielet.translator;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.util.Empty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class TranslatorFragment extends Fragment implements TranslatorContract.View {

    private static final String TAG = TranslatorFragment.class.getSimpleName();

    @Inject TranslatorContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.et_input) EditText inputField;
    @BindView(R.id.btn_clear) ImageButton clearButton;
    @BindView(R.id.result_field) TextView resultField;

    public TranslatorFragment() {
    }

    public static TranslatorFragment newInstance() {
        return new TranslatorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_translator, container, false);
        ButterKnife.bind(this, root);

        DaggerTranslatorComponent.builder()
                .translatorPresenterModule(new TranslatorPresenterModule(this))
                .translationsProviderComponent(((App) getActivity().getApplication())
                        .getTranslationsProviderComponent())
                .build()
                .inject(this);

        presenter.translations()
                .map(TranslationViewModel::getText)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(RxTextView.text(resultField));

        AppCompatSpinner spinner = new AppCompatSpinner(getContext());
        ArrayAdapter<LanguageViewModel> adapter =
                new ArrayAdapter<>(getContext(), R.layout.view_language);

        presenter.languageFromList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapter.clear();
                    adapter.addAll(list);
                });
        spinner.setAdapter(adapter);
        toolbar.addView(spinner);

        return root;
    }

    @NonNull
    @Override
    public Observable<String> translationInput() {
        return RxTextView.textChangeEvents(inputField).map(event -> event.text().toString());
    }

    @NonNull
    @Override
    public Observable<Empty> clearButtonPressed() {
        return RxView.clicks(clearButton).map(Empty::fromAny).doOnNext(something -> Log.d("TAG", something.toString()));
    }

    @NonNull
    @Override
    public Observable<Void> languagesSwapPressed() {
        // TODO: implement
        return Observable.never();
    }

    @NonNull
    @Override
    public Observable<Void> languageFromPickPressed() {
        // TODO: implement
        return Observable.never();
    }

    @NonNull
    @Override
    public Observable<Void> languageToPickPressed() {
        // TODO: implement
        return Observable.never();
    }

    @NonNull
    @Override
    public Observable<Integer> languageFromPicked() {
        // TODO: implement
        return Observable.never();
    }

    @NonNull
    @Override
    public Observable<Integer> languageToPicked() {
        // TODO: implement
        return Observable.never();
    }

    static final class LanguagesAdapter implements SpinnerAdapter {

        final List<LanguageViewModel> languages;

        LanguagesAdapter(List<LanguageViewModel> languages) {
            this.languages = languages;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            FrameLayout view = (FrameLayout) convertView;
            if (view == null) {
                view = (FrameLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_language, parent, false);
            }
            TextView tv = (TextView) view.findViewById(R.id.tv_language_name);
            tv.setText(languages.get(position).getName());
            return view;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public int getCount() {
            return languages.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return languages.isEmpty();
        }
    }
}
