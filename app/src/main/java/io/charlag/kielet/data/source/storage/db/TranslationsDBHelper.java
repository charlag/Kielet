package io.charlag.kielet.data.source.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by charlag on 03/04/2017.
 */

public class TranslationsDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "translations.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String COMMA = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TranslationEntry.TABLE_NAME + " (" +
                    TranslationEntry._ID + " INTEGER PRIMARY KEY," +
                    TranslationEntry.COLUMN_NAME_FROM + TEXT_TYPE + COMMA +
                    TranslationEntry.COLUMN_NAME_TO + TEXT_TYPE + COMMA +
                    TranslationEntry.COLUMN_NAME_ORIGINAL + TEXT_TYPE + COMMA +
                    TranslationEntry.COLUMN_NAME_RESULT + TEXT_TYPE + COMMA +
                    TranslationEntry.COLUMN_NAME_IS_FAV + BOOLEAN_TYPE +
                    " )";

    public TranslationsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
