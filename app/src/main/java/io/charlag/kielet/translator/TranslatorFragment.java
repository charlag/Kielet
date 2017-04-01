package io.charlag.kielet.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.charlag.kielet.App;
import io.charlag.kielet.R;
import io.charlag.kielet.data.source.DaggerTranslationsProviderComponent;
import io.charlag.kielet.data.source.TranslationsProviderModule;
import io.charlag.kielet.data.source.net.API;
import io.charlag.kielet.data.source.TranslationsProvider;
import io.charlag.kielet.data.source.TranslationsProviderImpl;
import io.charlag.kielet.data.source.net.NetModule;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class TranslatorFragment extends Fragment implements TranslatorContract.View {

    private static final String TAG = TranslatorFragment.class.getSimpleName();

    @Inject TranslatorContract.Presenter presenter;

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

        return root;
    }

    @NonNull
    @Override
    public Observable<String> translationInput() {
        return RxTextView.textChangeEvents(inputField).map(event -> event.text().toString());
    }

    @NonNull
    @Override
    public Observable<Void> clearButtonPressed() {
        return RxView.clicks(clearButton).cast(Void.class);
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
}
