package com.apps.toletbd.tolet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class SQLiteDAO {

    private static final String DATABASE_NAME = "to_let";
    private static final int DATABASE_VERSION = 1; //After creating table or adding column then must be increment database version

    private final SQLiteDatabase database;
    private final SQLiteOpenHelper helper;

    public SQLiteDAO(final Context context) {
        this.helper = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(final SQLiteDatabase db) {
                db.execSQL(ConstantKey.CREATE_AD_POSTS_TABLE);
                db.execSQL(ConstantKey.CREATE_USER_TABLE);
                db.execSQL(ConstantKey.CREATE_FB_TABLE);
                db.execSQL(ConstantKey.CREATE_NOT_TABLE);
                db.execSQL(ConstantKey.CREATE_FAV_TABLE);

                //db.execSQL(ConstantKey.INSERT_AD_POSTS_DATA1);
            }
            @Override
            public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
                db.execSQL(ConstantKey.DROP_AD_POSTS_TABLE);
                db.execSQL(ConstantKey.DROP_USER_TABLE);
                db.execSQL(ConstantKey.DROP_FB_TABLE);
                db.execSQL(ConstantKey.DROP_NOT_TABLE);
                db.execSQL(ConstantKey.DROP_FAV_TABLE);
                this.onCreate(db);
            }
        };
        this.database = this.helper.getWritableDatabase();
    }

    public long addData(String tableName, ContentValues values) {
        long data = this.database.insert(tableName, null, values);
        //this.database.close();
        return data;
    }

    public long deleteById(String tableName, final String id) {
        long data = this.database.delete(tableName, id, null);
        //this.database.close();
        return data;
    }

    public long deleteDataById(String tableName, final String id) {
        long data = this.database.delete(tableName, ConstantKey.COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        //this.database.close();
        return data;
    }

    public Cursor getAllData(String query) {
        final Cursor cursor = this.database.rawQuery(query,null);
        //this.database.close();
        return cursor;
    }

    public Cursor getAllDataByMobile(String tableName, String columnName, final String mobile) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName + "=?", new String[] {mobile});
        return cursor;
    }

    public Cursor getDataById(String tableName, final String id) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + ConstantKey.COLUMN_ID + "=?", new String[] {id});
        //this.database.close();
        return cursor;
    }

    public Cursor getDataByMobile(String tableName, final String mobile) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + ConstantKey.NOT_COLUMN4 + "=?", new String[] {mobile});
        //this.database.close();
        return cursor;
    }

    /*public Cursor getDataByAny(String tableName, String location, String passWord) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM " + tableName + " WHERE " + ConstantKey.AD_POSTS_COLUMN12 + "=? OR " + ConstantKey.AD_POSTS_COLUMN8 + "BETWEEN ? " + ConstantKey.AD_POSTS_COLUMN7 + "=?", new String[] {location, passWord});
        //SELECT username, password FROM login_table WHERE username=userName AND password=passWord
        //final Cursor cursor = this.database.query(ConstantKey.LOGIN_TABLE_NAME, new String[]{"id, username, password"}, "username=? AND password=?", new String[]{userName, passWord}, null, null, null);
        //this.database.close();
        return cursor;
    }*/

    public long updateById(String tableName, ContentValues values, String id) {
        long data = this.database.update(tableName, values, ConstantKey.COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        //this.database.close();
        return data;
    }

    public long updateByMobile(String tableName, ContentValues values, String mobile) {
        long data = this.database.update(tableName, values, ConstantKey.USER_COLUMN5 + " = ?", new String[] { String.valueOf(mobile) });
        //this.database.close();
        return data;
    }

}
