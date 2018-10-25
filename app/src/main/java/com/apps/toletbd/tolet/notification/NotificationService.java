package com.apps.toletbd.tolet.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.database.SQLiteDAO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationService {

    private SQLiteDAO dao;
    private ArrayList<NotificationModel> arrayList;
    private NotificationModel notificationModel;

    public NotificationService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addData(NotificationModel model){
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.NOT_COLUMN1, model.getUserId());
        values.put(ConstantKey.NOT_COLUMN2, model.getUserName());
        values.put(ConstantKey.NOT_COLUMN3, model.getUserMaritalStatus());
        values.put(ConstantKey.NOT_COLUMN4, model.getUserMobile());
        values.put(ConstantKey.NOT_COLUMN5, model.getUserAddress());
        values.put(ConstantKey.NOT_COLUMN6, model.getUserOccupation());
        values.put(ConstantKey.NOT_COLUMN7, model.getUserImage());
        values.put(ConstantKey.NOT_COLUMN8, model.getUserImagePath());
        values.put(ConstantKey.NOT_COLUMN9, model.getPostId());
        values.put(ConstantKey.NOT_COLUMN10, model.getPostOwnerName());
        values.put(ConstantKey.NOT_COLUMN11, model.getPostOwnerMobile());
        values.put(ConstantKey.NOT_COLUMN12, model.getPostPropertyType());
        values.put(ConstantKey.NOT_COLUMN13, model.getPostImageName());
        values.put(ConstantKey.NOT_COLUMN14, model.getPostImagePath());
        values.put(ConstantKey.NOT_COLUMN15, "created by kamal");
        values.put(ConstantKey.NOT_COLUMN16, "");
        values.put(ConstantKey.NOT_COLUMN17, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.NOT_TABLE_NAME, values);
    }

    //Getting all objects
    public ArrayList<NotificationModel> getAllData(){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllData(ConstantKey.SELECT_NOT_TABLE);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN9));
                String col10 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN10));
                String col11 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN11));
                String col12 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN12));
                String col13 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN13));
                String col14 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN14));
                String col15 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN15));
                String col16 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN16));
                String col17 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN17));

                NotificationModel model = new NotificationModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //Getting all objects by mobile
    public ArrayList<NotificationModel> getAllDataByMobile(String mobile){
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllDataByMobile(ConstantKey.NOT_TABLE_NAME, ConstantKey.NOT_COLUMN11, mobile);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN9));
                String col10 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN10));
                String col11 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN11));
                String col12 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN12));
                String col13 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN13));
                String col14 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN14));
                String col15 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN15));
                String col16 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN16));
                String col17 = cursor.getString(cursor.getColumnIndex(ConstantKey.NOT_COLUMN17));

                NotificationModel model = new NotificationModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17);

                Log.i("NotificationService", id+" ===== "+col10 );
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //Deleting single object
    public long deleteDataById(String id) {
        return dao.deleteDataById(ConstantKey.NOT_TABLE_NAME, id);
    }

    //Updating single object
    public long updateDataById(NotificationModel model, String id) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.NOT_COLUMN1, model.getUserId());
        values.put(ConstantKey.NOT_COLUMN2, model.getUserName());
        values.put(ConstantKey.NOT_COLUMN3, model.getUserMaritalStatus());
        values.put(ConstantKey.NOT_COLUMN4, model.getUserMobile());
        values.put(ConstantKey.NOT_COLUMN5, model.getUserAddress());
        values.put(ConstantKey.NOT_COLUMN6, model.getUserOccupation());
        values.put(ConstantKey.NOT_COLUMN7, model.getUserImage());
        values.put(ConstantKey.NOT_COLUMN8, model.getUserImagePath());
        values.put(ConstantKey.NOT_COLUMN9, model.getPostId());
        values.put(ConstantKey.NOT_COLUMN10, model.getPostOwnerName());
        values.put(ConstantKey.NOT_COLUMN11, model.getPostOwnerName());
        values.put(ConstantKey.NOT_COLUMN12, model.getPostPropertyType());
        values.put(ConstantKey.NOT_COLUMN13, model.getPostImageName());
        values.put(ConstantKey.NOT_COLUMN14, model.getPostImagePath());
        values.put(ConstantKey.NOT_COLUMN15, "created by kamal");
        values.put(ConstantKey.NOT_COLUMN16, "");
        values.put(ConstantKey.NOT_COLUMN17, String.valueOf(new Timestamp(System.currentTimeMillis())));

        Log.i("updateDataById======= ", id+" "+String.valueOf(model.getUserName()) );

        return dao.updateById(ConstantKey.NOT_TABLE_NAME, values, id);
    }


    //Getting single object by id
    public NotificationModel getDataById(String id){
        Cursor cursor = dao.getDataById(ConstantKey.NOT_TABLE_NAME, id);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            notificationModel = new NotificationModel(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17));
        }
        return notificationModel;
    }

    //Getting single object by mobile
    public NotificationModel getDataByMobile(String mobile){
        Cursor cursor = dao.getDataByMobile(ConstantKey.USER_TABLE_NAME, mobile);
        if(cursor.getCount() > 0 && cursor != null) {
            cursor.moveToFirst();
            notificationModel = new NotificationModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17));
        }
        return notificationModel;
    }

}
