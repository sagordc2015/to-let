package com.apps.toletbd.tolet.adpost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.database.SQLiteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class PostsService {

    private SQLiteDAO dao;
    private ArrayList<PostsModel> arrayList;

    public PostsService(Context context) {
        arrayList = new ArrayList<>();
        dao = new SQLiteDAO(context);
    }

    //Adding single object
    public long addPost(PostsModel model) {
        final ContentValues values = new ContentValues();
        values.put(ConstantKey.AD_POSTS_COLUMN1, model.getOwnerName());
        values.put(ConstantKey.AD_POSTS_COLUMN2, model.getOwnerEmail());
        values.put(ConstantKey.AD_POSTS_COLUMN3, model.getOwnerMobile());
        values.put(ConstantKey.AD_POSTS_COLUMN4, model.getOwnerMobileHide());
        values.put(ConstantKey.AD_POSTS_COLUMN5, model.getPropertyType());
        values.put(ConstantKey.AD_POSTS_COLUMN6, model.getRenterType());
        values.put(ConstantKey.AD_POSTS_COLUMN7, model.getRentPrice());
        values.put(ConstantKey.AD_POSTS_COLUMN8, model.getBedrooms());
        values.put(ConstantKey.AD_POSTS_COLUMN9, model.getBathrooms());
        values.put(ConstantKey.AD_POSTS_COLUMN10, model.getSquareFootage());
        values.put(ConstantKey.AD_POSTS_COLUMN11, model.getAmenities());
        values.put(ConstantKey.AD_POSTS_COLUMN12, model.getLocation());
        values.put(ConstantKey.AD_POSTS_COLUMN13, model.getAddress());
        values.put(ConstantKey.AD_POSTS_COLUMN14, model.getLatitude());
        values.put(ConstantKey.AD_POSTS_COLUMN15, model.getLongitude());
        values.put(ConstantKey.AD_POSTS_COLUMN16, model.getDescription());
        values.put(ConstantKey.AD_POSTS_COLUMN17, model.getImageName());
        values.put(ConstantKey.AD_POSTS_COLUMN18, model.getImagesPath());
        values.put(ConstantKey.AD_POSTS_COLUMN19, "created by kamal");
        values.put(ConstantKey.AD_POSTS_COLUMN20, "");
        values.put(ConstantKey.AD_POSTS_COLUMN21, String.valueOf(new Timestamp(System.currentTimeMillis())));

        return dao.addData(ConstantKey.AD_POSTS_TABLE_NAME, values);
    }

    //Getting all objects
    public ArrayList<PostsModel> getAllPosts() {
        arrayList = new ArrayList<>();
        Cursor cursor = dao.getAllData(ConstantKey.SELECT_AD_POSTS_TABLE);
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex(ConstantKey.COLUMN_ID));
                String col1 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN1));
                String col2 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN2));
                String col3 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN3));
                String col4 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN4));
                String col5 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN5));
                String col6 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN6));
                String col7 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN7));
                String col8 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN8));
                String col9 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN9));
                String col10 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN10));
                String col11 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN11));
                String col12 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN12));
                String col13 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN13));
                String col14 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN14));
                String col15 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN15));
                String col16 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN16));
                String col17 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN17));
                String col18 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN18));
                String col19 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN19));
                String col20 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN20));
                String col21 = cursor.getString(cursor.getColumnIndex(ConstantKey.AD_POSTS_COLUMN21));

                PostsModel model = new PostsModel(id,col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12,col13,col14,col15,col16,col17,col18,col19,col20,col21);
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        return arrayList;
    }

    //Deleting single object
    public long deletePostById(String id) {
        return dao.deleteDataById(ConstantKey.AD_POSTS_TABLE_NAME, id);
    }

    //Updating single object
    public long updatePostById(PostsModel model, String id) {
        ContentValues values = new ContentValues();
        values.put(ConstantKey.AD_POSTS_COLUMN1, model.getOwnerName());
        values.put(ConstantKey.AD_POSTS_COLUMN2, model.getOwnerEmail());
        values.put(ConstantKey.AD_POSTS_COLUMN3, model.getOwnerMobile());
        values.put(ConstantKey.AD_POSTS_COLUMN4, model.getOwnerMobileHide());
        values.put(ConstantKey.AD_POSTS_COLUMN5, model.getPropertyType());
        values.put(ConstantKey.AD_POSTS_COLUMN6, model.getRenterType());
        values.put(ConstantKey.AD_POSTS_COLUMN7, model.getRentPrice());
        values.put(ConstantKey.AD_POSTS_COLUMN8, model.getBedrooms());
        values.put(ConstantKey.AD_POSTS_COLUMN9, model.getBathrooms());
        values.put(ConstantKey.AD_POSTS_COLUMN10, model.getSquareFootage());
        values.put(ConstantKey.AD_POSTS_COLUMN11, model.getAmenities());
        values.put(ConstantKey.AD_POSTS_COLUMN12, model.getLocation());
        values.put(ConstantKey.AD_POSTS_COLUMN13, model.getAddress());
        values.put(ConstantKey.AD_POSTS_COLUMN14, model.getLatitude());
        values.put(ConstantKey.AD_POSTS_COLUMN15, model.getLongitude());
        values.put(ConstantKey.AD_POSTS_COLUMN16, model.getDescription());
        values.put(ConstantKey.AD_POSTS_COLUMN17, model.getImageName());
        values.put(ConstantKey.AD_POSTS_COLUMN18, model.getImagesPath());
        values.put(ConstantKey.AD_POSTS_COLUMN19, "created by kamal");
        values.put(ConstantKey.AD_POSTS_COLUMN20, "");
        values.put(ConstantKey.AD_POSTS_COLUMN21, String.valueOf(new Timestamp(System.currentTimeMillis())));

        Log.i("updateDataById======= ", id+" "+String.valueOf(model.getOwnerName()) );

        return dao.updateById(ConstantKey.AD_POSTS_TABLE_NAME, values, id);
    }



    //===============================================| ArrayList to JASON and JASON to ArrayList
    private String arrayListToJason(ArrayList<String> amenities) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("amenitiesArrays", new JSONArray(amenities));
        return json.toString();
    }
    private ArrayList<String> getJasonToArrayList(String string) throws JSONException {
        JSONObject json = new JSONObject(string);
        JSONArray jsonArray = json.optJSONArray("amenitiesArrays");

        ArrayList<String> stringArray = new ArrayList<String>();
        for(int i = 0, count = jsonArray.length(); i< count; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringArray.add(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringArray;
    }
}
