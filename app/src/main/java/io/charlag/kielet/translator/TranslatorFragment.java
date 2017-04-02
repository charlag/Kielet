package io.charlag.kielet.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

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
    AppCompatSpinner spinnerFrom;
    AppCompatSpinner spinnerTo;

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

        spinnerFrom = new AppCompatSpinner(getContext());
        ArrayAdapter<LanguageViewModel> adapter =
                new ArrayAdapter<>(getContext(), R.layout.view_language);

        spinnerFrom.setAdapter(adapter);
        toolbar.addView(spinnerFrom);

        spinnerTo = new AppCompatSpinner(getContext());
        ArrayAdapter<LanguageViewModel> adapterTo =
                new ArrayAdapter<>(getContext(), R.layout.view_language);
        spinnerTo.setAdapter(adapterTo);
        toolbar.addView(spinnerTo);

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

        presenter.languageFromList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapter.clear();
                    adapter.addAll(list);
                });

        presenter.chosenLanguages()
                .distinctUntilChanged()
                .map(p -> p.first)
                .subscribe(RxAdapterView.selection(spinnerFrom));

        presenter.chosenLanguages()
                .distinctUntilChanged()
                .map(p -> p.second)
                .subscribe(RxAdapterView.selection(spinnerTo));

        presenter.languageToList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapterTo.clear();
                    adapterTo.addAll(list);
                });

        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(0);

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
        return RxView.clicks(clearButton).map(Empty::fromAny);
    }

    @NonNull
    @Override
    public Observable<Void> languagesSwapPressed() {
        // TODO: implement
        return Observable.never();
    }

    @NonNull
    @Override
    public Observable<Integer> languageFromPicked() {
        // TODO: unsubscribe
        return RxAdapterView.itemSelections(spinnerFrom).filter(index -> index > 0);
    }

    @NonNull
    @Override
    public Observable<Integer> languageToPicked() {
        // TODO: unsubscribe
        return RxAdapterView.itemSelections(spinnerTo).filter(index -> index > 0);
    }
}
