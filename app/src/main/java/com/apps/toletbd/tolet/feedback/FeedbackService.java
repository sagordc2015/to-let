package com.apps.toletbd.tolet.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.database.SQLiteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class FeedbackService {

    private SQLiteDAO dao;
    private ArrayList<FeedbackModel> arrayList;

    public FeedbackService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addData(FeedbackModel model){
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.FB_COLUMN1, model.getFbMessage());
        values.put(ConstantKey.FB_COLUMN2, model.getPostedId());
        values.put(ConstantKey.FB_COLUMN3, model.getUserId());
        values.put(ConstantKey.FB_COLUMN4, model.getUserName());
        values.put(ConstantKey.FB_COLUMN5, model.getUserImage());
        values.put(ConstantKey.FB_COLUMN6, model.getUserImagePath());
        values.put(ConstantKey.FB_COLUMN7, "created by kamal");
        values.put(ConstantKey.FB_COLUMN8, "");
        values.put(ConstantKey.FB_COLUMN9, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.FB_TABLE_NAME, values);
    }

    //Getting all objects
    public ArrayList<FeedbackModel> getAllDatas(){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllData(ConstantKey.SELECT_FB_TABLE);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.FB_COLUMN9));

                FeedbackModel model = new FeedbackModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }
}
