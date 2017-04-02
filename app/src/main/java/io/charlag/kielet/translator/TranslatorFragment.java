package io.charlag.kielet.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.util.Empty;
import io.charlag.kielet.util.TouchSelectedEventsObservable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public final class TranslatorFragment extends Fragment implements TranslatorContract.View {

    private static final String TAG = TranslatorFragment.class.getSimpleName();

    @Inject TranslatorContract.Presenter presenter;

    @BindView(R.id.et_input) EditText inputField;
    @BindView(R.id.btn_clear) ImageButton clearButton;
    @BindView(R.id.result_field) TextView resultField;
    @BindView(R.id.spinner_from) AppCompatSpinner spinnerFrom;
    @BindView(R.id.spinner_to) AppCompatSpinner spinnerTo;
    @BindView(R.id.btn_swap) AppCompatImageButton swapLanguagesButton;

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

        ArrayAdapter<LanguageViewModel> adapterFrom =
                new ArrayAdapter<>(getContext(), R.layout.view_language, R.id.tv_language_name);
        adapterFrom.setDropDownViewResource(R.layout.view_language_dropdown);
        spinnerFrom.setAdapter(adapterFrom);

        ArrayAdapter<LanguageViewModel> adapterTo =
                new ArrayAdapter<>(getContext(), R.layout.view_language, R.id.tv_language_name);
        adapterFrom.setDropDownViewResource(R.layout.view_language_dropdown);
        spinnerTo.setAdapter(adapterTo);

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

        presenter.chosenLanguages()
                .distinctUntilChanged()
                .map(p -> p.first)
                .subscribe(index -> {
                    spinnerFrom.setSelection(index);
                    spinnerFrom.setTag(index);
                });

        presenter.languageFromList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    adapterFrom.clear();
                    adapterFrom.addAll(list);
                });

        presenter.chosenLanguages()
                .distinctUntilChanged()
                .map(p -> p.second)
                .subscribe(index -> {
                    spinnerTo.setSelection(index);
                    spinnerTo.setTag(index);
                });

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
    public Observable<Empty> languagesSwapPressed() {
        return RxView.clicks(swapLanguagesButton).map(Empty::fromAny);
    }

    @NonNull
    @Override
    public Observable<Integer> languageFromPicked() {
        // TODO: unsubscribe
        return new TouchSelectedEventsObservable(spinnerFrom);
    }

    @NonNull
    @Override
    public Observable<Integer> languageToPicked() {
        // TODO: unsubscribe
        return new TouchSelectedEventsObservable(spinnerTo);
    }
}
