package com.apps.toletbd.tolet.favorite;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.adpost.PostsModel;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class FavoriteActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String userMobile;

    private FavoriteAdapter favoriteAdapter;
    private ArrayList<PostsModel> postsModelArrayList;

    private UserService userService;
    private UserModel userModel;

    private FavoriteService favoriteService;
    private ArrayList<FavoriteModel> modelArrayList;

    private ListView postsList;

    private ProgressDialog progress;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        context = this;
        ArrayList<String> ids = new ArrayList<>();

        postsList = (ListView) findViewById(R.id.posts_list_fav);

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");

        this.userService = new UserService(this);
        this.userModel = this.userService.getDataByMobile(userMobile);

        this.favoriteService = new FavoriteService(this);
        this.modelArrayList = this.favoriteService.getAllDataByMobile(userMobile);
        for (int i=0; i<modelArrayList.size(); i++) {
            Log.d("FAV", String.valueOf(modelArrayList.get(i).getPostId()));
            ids.add(String.valueOf(modelArrayList.get(i).getPostId()));
        }

        if (ids != null) {
            new BackTask().execute("posts_favorite", Arrays.toString(ids.toArray()));
        }

    }

    //====================================================| AsyncTask from MySQL Server
    private class BackTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setMessage(context.getString(R.string.progress));
            progress.setCancelable(true);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected String doInBackground(String...params){
            String type = params[0];

            if(type.equals("posts_favorite")) {
                //location, rentMinPrice, rentMaxPrice, propertyType, bedrooms, bathrooms,
                String filter_url = "http://to-let.nhp-bd.com/favorite_select.php?postId="+params[1];
                String result="";
                String line="";
                try {
                    URLConnection connection = new URL(filter_url).openConnection();
                    connection.setConnectTimeout(1000 * 30);
                    connection.setReadTimeout(1000 * 30);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"iso-8859-1"));
                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }
                    bufferedReader.close();
                    return result;
                } catch (Exception e) {
                    if(progress != null) {
                        progress.dismiss(); //close the dialog if error occurs
                    }
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){

            Log.d("FAV", String.valueOf(result));

            if(progress != null) {
                progress.dismiss(); //close dialog
            }
            postsModelArrayList = new ArrayList<PostsModel>();
            favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, postsModelArrayList);
            postsList.setAdapter(favoriteAdapter);
            try{
                // Remove unexpected characters that might be added to beginning of the string
                result = result.substring(result.indexOf("["));
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i<jArray.length(); i++){
                    JSONObject js = jArray.getJSONObject(i);
                    PostsModel p = new PostsModel(js.getString("id"),js.getString("owner_name"),js.getString("owner_email"),js.getString("owner_mobile"),js.getString("owner_mobile_hide"),js.getString("property_type"),js.getString("renter_type"),js.getString("rent_price"),js.getString("bedrooms"),js.getString("bathrooms"),js.getString("square_footage"),js.getString("amenities"),js.getString("location"),js.getString("address"),js.getString("latitude"),js.getString("longitude"),js.getString("description"),js.getString("image_name"),js.getString("images_path"),js.getString("is_enable"),js.getString("created_by_id"),js.getString("updated_by_id"),js.getString("created_at"));
                    postsModelArrayList.add(p);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            favoriteAdapter.notifyDataSetChanged(); //notify the ListView to get new records
        }

    }


}
