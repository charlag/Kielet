package io.charlag.kielet.data.source.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import io.charlag.kielet.data.Translation;
import io.charlag.kielet.data.source.storage.db.TranslationEntry;
import io.charlag.kielet.data.source.storage.db.TranslationsDBHelper;
import io.reactivex.Single;

/**
 * Created by charlag on 03/04/2017.
 */

public class TranslationsStorageImpl implements TranslationsStorage {

    private TranslationsDBHelper dbHelper;

    public TranslationsStorageImpl(Context context) {
        this.dbHelper = new TranslationsDBHelper(context);
    }

    @Override
    public void addTranslation(Translation translation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TranslationEntry.COLUMN_NAME_FROM, translation.getFrom());
        values.put(TranslationEntry.COLUMN_NAME_TO, translation.getTo());
        values.put(TranslationEntry.COLUMN_NAME_ORIGINAL, translation.getOriginalText());
        values.put(TranslationEntry.COLUMN_NAME_RESULT, translation.getText().get(0));
        values.put(TranslationEntry.COLUMN_NAME_IS_FAV, translation.isFav());

        db.insert(TranslationEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void saveTranslation(long id) {
        updateTaskFavorite(id, true);
    }

    @Override
    public void unsaveTranslation(long id) {
        updateTaskFavorite(id, false);
    }

    @Override
    public Single<List<Translation>> getTranslations() {
        return Single.create(source -> {
            String[] projection = {
                    TranslationEntry._ID,
                    TranslationEntry.COLUMN_NAME_FROM,
                    TranslationEntry.COLUMN_NAME_TO,
                    TranslationEntry.COLUMN_NAME_ORIGINAL,
                    TranslationEntry.COLUMN_NAME_RESULT,
                    TranslationEntry.COLUMN_NAME_IS_FAV
            };

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor c = db.query(TranslationEntry.TABLE_NAME, projection,
                    null, null, null, null, null);
            List<Translation> result = new ArrayList<>();

            if (c != null && c.getCount() > 0) {
                while (c.moveToNext()) {
                    long itemId = c.getInt(c.getColumnIndexOrThrow(TranslationEntry._ID));
                    String from = c.getString(
                            c.getColumnIndexOrThrow(TranslationEntry.COLUMN_NAME_FROM));
                    String to = c.getString(
                            c.getColumnIndexOrThrow(TranslationEntry.COLUMN_NAME_TO));
                    String original = c.getString(
                            c.getColumnIndexOrThrow(TranslationEntry.COLUMN_NAME_ORIGINAL));
                    String translationResult = c.getString(c.getColumnIndexOrThrow(
                            TranslationEntry.COLUMN_NAME_FROM));
                    boolean isFav = c.getInt(
                            c.getColumnIndexOrThrow(TranslationEntry.COLUMN_NAME_IS_FAV)) == 1;
                    Translation translation = new Translation(itemId, isFav, original,
                            translationResult, to, from);
                    result.add(translation);
                }
            }
            if (c != null) {
                c.close();
            }
            db.close();
            source.onSuccess(result);
        });
    }

    private void updateTaskFavorite(long id, boolean isFavorite) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TranslationEntry.COLUMN_NAME_IS_FAV, isFavorite);
        String selection = TranslationEntry._ID + " LIKE ?";
        String[] selectionArgs = { Long.toString(id) };
        db.update(TranslationEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}
