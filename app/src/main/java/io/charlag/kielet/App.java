package io.charlag.kielet;

import android.app.Application;

import io.charlag.kielet.data.source.DaggerTranslationsProviderComponent;
import io.charlag.kielet.data.source.TranslationsProviderComponent;
import io.charlag.kielet.data.source.TranslationsProviderModule;
import io.charlag.kielet.data.source.net.NetModule;

/**
 * Created by charlag on 01/04/2017.
 */

public class App extends Application {

    private NetModule netModule;
    private TranslationsProviderComponent translationsProviderComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netModule = new NetModule("https://translate.yandex.net/api/v1.5/tr.json/",
                getString(R.string.api_key));

        translationsProviderComponent = DaggerTranslationsProviderComponent.builder()
                .translationsProviderModule(new TranslationsProviderModule())
                .netModule(netModule)
                .build();
    }

    public NetModule getNetModule() {
        return netModule;
    }

    public TranslationsProviderComponent getTranslationsProviderComponent() {
        return translationsProviderComponent;
    }
}
