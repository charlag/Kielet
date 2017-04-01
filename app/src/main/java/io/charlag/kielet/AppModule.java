package io.charlag.kielet;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by charlag on 01/04/2017.
 */

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
