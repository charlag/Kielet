package io.charlag.kielet;

import android.app.Application;

import io.charlag.kielet.data.source.TranslationsProviderModule;
import io.charlag.kielet.data.source.net.NetModule;
import io.charlag.kielet.data.source.storage.TranslationsStorageModule;

/**
 * Created by charlag on 01/04/2017.
 */

public class App extends Application {


    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        NetModule netModule = new NetModule("https://translate.yandex.net/api/v1.5/tr.json/",
                getString(R.string.api_key));

        appComponent = DaggerAppComponent.builder()
                .netModule(netModule)
                .appModule(new AppModule(this))
                .translationsStorageModule(new TranslationsStorageModule())
                .translationsProviderModule(new TranslationsProviderModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
