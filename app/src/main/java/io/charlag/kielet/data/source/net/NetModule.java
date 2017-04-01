package io.charlag.kielet.data.source.net;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.charlag.kielet.data.source.net.response.LanguagesAdapter;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by charlag on 01/04/2017.
 */

@Module
public class NetModule {

    private final String baseUrl;
    private final String apiKey;

    public NetModule(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(new LanguagesAdapter())
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();

            HttpUrl url = original.url().newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        })
                .build();

    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(Moshi moshi, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    API providesAPI(Retrofit retrofit) {
        return retrofit.create(API.class);
    }
}
