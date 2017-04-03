package io.charlag.kielet.data.source.storage.db;

import android.provider.BaseColumns;

/**
 * Created by charlag on 03/04/2017.
 */

public final class TranslationEntry implements BaseColumns {
    public static final String TABLE_NAME = "translation";
    public static final String COLUMN_NAME_FROM = "from";
    public static final String COLUMN_NAME_TO = "to";
    public static final String COLUMN_NAME_ORIGINAL = "original";
    public static final String COLUMN_NAME_RESULT = "result";
    public static final String COLUMN_NAME_IS_FAV = "fav";

    TranslationEntry() {}
}
