package com.apps.toletbd.tolet.notification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.language.LocaleHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    private static final String TAG = "NotificationActivity";

    private ListView notList;

    private SharedPreferences preferences;

    private ProgressDialog progress;
    private Activity context;

    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationModel> notificationModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //==========================================| Change action bar title
        ActionBar bar = getSupportActionBar();
        bar.setTitle(getResources().getString(R.string.notification_list_title));

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        String userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");

        notList = (ListView) findViewById(R.id.notification_list);

        context = this;
        if (userMobile != null && !userMobile.isEmpty()) {
            new BackTask().execute(userMobile);
        }

        //===============================================| Service
        //this.notificationService = new NotificationService(this);
        //this.modelArrayList = this.notificationService.getAllDataByMobile(userMobile);
        //this.modelArrayList = this.notificationService.getAllData();

        //===============================================| ArrayAdapter
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        for(NotificationModel obj : modelArrayList){
            Log.d(TAG, ""+obj.getUserName()+"\n"+obj.getPostOwnerName());
            adapter.add(obj.getUserName()+"\n"+obj.getPostOwnerName());
        }
        notList.setAdapter(adapter);*/
    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //====================================================| AsyncTask from MySQL Server
    private class BackTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(context);
            //progress.setTitle("Retrieving data");
            progress.setMessage(context.getString(R.string.progress));
            progress.setCancelable(true);
            progress.setIndeterminate(true);
            progress.show();
        }

        @Override
        protected String doInBackground(String...params){
            String userMobile = params[0];
            String select_url = "http://to-let.nhp-bd.com/notification_select.php?postOwnerMobile="+userMobile;
            String result="";
            String line="";
            try {
                URLConnection connection = new URL(select_url).openConnection();
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
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if(progress != null) {
                progress.dismiss();
            }
            notificationModelArrayList = new ArrayList<NotificationModel>();
            notificationAdapter =  new NotificationAdapter(context, notificationModelArrayList);
            notList.setAdapter(notificationAdapter);

            if (result != null && !result.isEmpty()) {
                try{
                    String res = result.substring(result.indexOf("[")); // Remove unexpected characters that might be added to beginning of the string
                    JSONArray jArray = new JSONArray(res);
                    for(int i=0; i<jArray.length(); i++){
                        JSONObject js = jArray.getJSONObject(i);
                        NotificationModel p = new NotificationModel(js.getString("id"),js.getString("user_id"),js.getString("user_name"),js.getString("user_marital_status"),js.getString("user_mobile"),js.getString("user_address"),js.getString("user_occupation"),js.getString("user_image"),js.getString("user_image_path"),js.getString("post_id"),js.getString("post_owner_name"),js.getString("post_owner_mobile"),js.getString("post_property_type"),js.getString("post_image_name"),js.getString("post_image_path"),js.getString("created_by_id"),js.getString("updated_by_id"),js.getString("created_at"));
                        notificationModelArrayList.add(p);
                        Log.e(TAG, js.getString("user_name")+ "");
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            notificationAdapter.notifyDataSetChanged(); //notify the ListView to get new records
        }

    }


}
