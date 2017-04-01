package io.charlag.kielet.data.source.net.response;

import com.squareup.moshi.FromJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.charlag.kielet.data.Language;

/**
 * Created by charlag on 01/04/2017.
 */

public class LanguagesAdapter {
    @FromJson List<Language> languagesFromJson(LanguagesResponse response) {
        ArrayList<Language> result = new ArrayList<>(response.langs.size());
        for (Map.Entry<String, String> entry : response.langs.entrySet()) {
            result.add(new Language((entry.getKey()), entry.getValue()));
        }
        return result;
    }
}

