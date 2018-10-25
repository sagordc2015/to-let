package com.apps.toletbd.tolet.favorite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.database.SQLiteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FavoriteService {

    private SQLiteDAO dao;
    private ArrayList<FavoriteModel> arrayList;
    private FavoriteModel favoriteModel;

    public FavoriteService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addData(FavoriteModel model){
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.FAV_COLUMN1, model.getUserId());
        values.put(ConstantKey.FAV_COLUMN2, model.getUserMobile());
        values.put(ConstantKey.FAV_COLUMN3, model.getPostId());
        values.put(ConstantKey.FAV_COLUMN4, model.getPostOwnerMobile());
        values.put(ConstantKey.FAV_COLUMN5, "created by kamal");
        values.put(ConstantKey.FAV_COLUMN6, "");
        values.put(ConstantKey.FAV_COLUMN7, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.FAV_TABLE_NAME, values);
    }

    //Getting all objects by mobile
    public ArrayList<FavoriteModel> getAllDataByMobile(String mobile){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllDataByMobile(ConstantKey.FAV_TABLE_NAME, ConstantKey.FAV_COLUMN2, mobile);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.FAV_COLUMN7));

                FavoriteModel model = new FavoriteModel(id,col1,col2,col3,col4,col5,col6,col7);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

}
