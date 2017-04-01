package io.charlag.kielet.data.source.net;

import java.util.List;

import io.charlag.kielet.data.Language;
import io.charlag.kielet.data.source.net.response.TranslationResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by charlag on 31/03/2017.
 */

public interface API {
    @GET("getLangs")
    Single<List<Language>> languages(@Query("ui") String ui);

    @GET("translate?options=1")
    Single<TranslationResponse> translate(@Query("lang") String lang, @Query("text") String text);
}
