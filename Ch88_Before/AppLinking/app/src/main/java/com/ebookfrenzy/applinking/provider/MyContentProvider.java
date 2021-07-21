package com.ebookfrenzy.applinking.provider;

import com.ebookfrenzy.applinking.MyDBHandler;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

    private LandmarksDatabaseHelper myDB;

    private static final String AUTHORITY =
            "com.ebookfrenzy.applinking.provider.MyContentProvider";
    private static final String LOCATIONS_TABLE = "locations";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + LOCATIONS_TABLE);

    public static final int PRODUCTS = 1;
    public static final int PRODUCTS_ID = 2;

    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, LOCATIONS_TABLE, PRODUCTS);
        sURIMatcher.addURI(AUTHORITY, LOCATIONS_TABLE + "/#",
                PRODUCTS_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsDeleted = sqlDB.delete(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                        selection,
                        selectionArgs);
                break;

            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                            MyDBHandler.Companion.getCOLUMN_ID() + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                            MyDBHandler.Companion.getCOLUMN_ID() + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = myDB.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case PRODUCTS:
                id = sqlDB.insert(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                        null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(LOCATIONS_TABLE + "/" + id);

    }

    @Override
    public boolean onCreate() {
        myDB = new LandmarksDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDBHandler.Companion.getTABLE_LOCATIONS());

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case PRODUCTS_ID:
                queryBuilder.appendWhere(MyDBHandler.Companion.getCOLUMN_ID() + "="
                        + uri.getLastPathSegment());
                break;
            case PRODUCTS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsUpdated =
                        sqlDB.update(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                                values,
                                selection,
                                selectionArgs);
                break;
            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                                    values,
                                    MyDBHandler.Companion.getCOLUMN_ID() + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(MyDBHandler.Companion.getTABLE_LOCATIONS(),
                                    values,
                                    MyDBHandler.Companion.getCOLUMN_ID() + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;
    }

    public class LandmarksDatabaseHelper extends SQLiteAssetHelper {

        private static final String DATABASE_NAME = "landmarks.db";
        private static final int DATABASE_VERSION = 1;

        public LandmarksDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
    }
}
