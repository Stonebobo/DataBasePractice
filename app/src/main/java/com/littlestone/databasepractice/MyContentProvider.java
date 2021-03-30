package com.littlestone.databasepractice;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    public static final int BOOK_DIR = 0;
    public static final int BOOK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;
    public static final String AUTHORITY = "com.littlestone.databasepractice";
    public static UriMatcher uriMatcher;
    public MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 3);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delRows = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                delRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String Book_id = uri.getPathSegments().get(1);
                delRows = db.delete("Book", "id=?", new String[]{Book_id});
                break;
            case CATEGORY_DIR:
                delRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String Category_id = uri.getPathSegments().get(1);
                delRows = db.delete("Category", "id=?", new String[]{Category_id});
                break;
        }
        return delRows;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Uri uriReturn = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book/" + newCategoryId);
                break;
        }
        return uriReturn;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String BOOK_id = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id=?", new String[]{BOOK_id}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String CATEGORY_id = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id=?", new String[]{CATEGORY_id}, null, null, sortOrder);
                break;

        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateRows = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String Book_id = uri.getPathSegments().get(1);
                updateRows = db.update("Book", values, "id=?", new String[]{Book_id});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String Category_id = uri.getPathSegments().get(1);
                updateRows = db.update("Category", values, "id=?", new String[]{Category_id});
                break;
        }
        return updateRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.littlestone.databasepractice.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.littlestone.databasepractice.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.littlestone.databasepractice.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.littlestone.databasepractice.category";
        }
        return null;
    }
}