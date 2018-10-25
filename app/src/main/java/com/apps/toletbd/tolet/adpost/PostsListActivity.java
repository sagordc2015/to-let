package com.apps.toletbd.tolet.adpost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.favorite.FavoriteService;
import com.apps.toletbd.tolet.feedback.FeedbackModel;
import com.apps.toletbd.tolet.feedback.FeedbackService;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.notification.NotificationService;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PostsListActivity extends AppCompatActivity {

    private static final String TAG = "PostsListActivity"; //logt + Tab

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String userMobile;
    private String userEmail;
    private String userName;
    private String userId;

    //private PostsService postsService;
    private PostsListAdapter postsListAdapter;
    private ArrayList<PostsModel> modelArrayList;

    //private FeedbackService feedbackService;
    //private ArrayList<FeedbackModel> feedbackArrayList;

    private UserService userService;
    private UserModel userModel;

    //private NotificationService notificationService;
    private FavoriteService favoriteService;

    private ListView postsList;

    private ProgressDialog progress;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        //==========================================| Change action bar title
        ActionBar bar = getSupportActionBar();
        bar.setTitle(getResources().getString(R.string.posts_list_title));

        context = this;

        //===============================================| Getting SharedPreferences and User model
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");
        userId = preferences.getString(ConstantKey.USER_ID_KEY, "Data not found");

        this.userService = new UserService(this);
        this.userModel = this.userService.getDataByMobile(userMobile);
        
        //this.postsService = new PostsService(this); //To get from service
        //this.feedbackService = new FeedbackService(this);
        //this.notificationService = new NotificationService(this);
        this.favoriteService = new FavoriteService(this);


        //===============================================| Getting All Data in ListView
        postsList = (ListView) findViewById(R.id.posts_list_view_id);

        String filterValue = getIntent().getStringExtra(ConstantKey.FILTER_KEY);

        if (filterValue != null && !filterValue.isEmpty()) {
            String[] arr = filterValue.split(",");
            if (!arr[0].contains("Select Location")) {
                new BackTask().execute("posts_filter",arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]);
                Log.d("Filter", "Location: "+arr[0]+" Min: "+arr[1]+" Max: "+arr[2]+" Property: "+arr[3]+" Renter: "+arr[4]+" Beds: "+arr[5]);
            }
        } else {
            new BackTask().execute("posts_select");
        }



        /*try {
            modelArrayList = (ArrayList) postsService.getAllPosts();
            feedbackArrayList = this.feedbackService.getAllDatas();
            postsListAdapter = new PostsListAdapter(PostsListActivity.this, modelArrayList, postsService, feedbackArrayList, feedbackService, userModel, notificationService, favoriteService);
            postsList.setAdapter(postsListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        //===============================================| Custom adapter search
        /*E10 = (EditText) findViewById(R.id.purchase_search);
        E10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                int textlength = cs.length();
                ArrayList<PurchasesModel> tempArrayList = new ArrayList<PurchasesModel>();
                for(PurchasesModel c: purArrayList){
                    if (textlength <= c.getProductName().length()) {
                        if (c.getProductName().toLowerCase().contains(cs.toString().toLowerCase())) {
                            tempArrayList.add(c);
                        }
                    }
                }
                purAdapter = new PurchasesAdapter(PurchasesActivity.this, tempArrayList, purService, pArrayList, pServie, supArrayList, supService);
                purListView.setAdapter(purAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });*/

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        adapter.add("Click here to see details");
        postsList.setAdapter(adapter);*/

        Button addNewPost = (Button) findViewById(R.id.add_new_post_btn);
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel != null && userModel != null && userModel.getIsUserOwner().equals("true")) {
                    Log.d(TAG, userModel.getUserEmail()+"");
                    if (userModel.getUserEmail() != null) {
                        Intent intent = new Intent(getApplicationContext(), PostAdActivity.class);
                        startActivity(intent);
                    } else {
                        alertDialog(getResources().getString(R.string.msg_email_update));
                    }
                } else {
                    alertDialog(getResources().getString(R.string.msg_register_user));
                }

            }
        });


    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //====================================================| Alert Dialog
    public void alertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //====================================================| For Activity Starting and Closing
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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

            if(type.equals("posts_filter")) {
                //location, rentMinPrice, rentMaxPrice, propertyType, bedrooms, bathrooms,
                String filter_url = "http://to-let.nhp-bd.com/postad_filter.php?location="+params[1]+"&rentMinPrice="+params[2]+"&rentMaxPrice="+params[3]+"&propertyType="+params[4]+"&bedrooms="+params[5]+"&bathrooms="+params[6];
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

            if(type.equals("posts_select")) {
                String select_url = "http://to-let.nhp-bd.com/postad_select.php";
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if(progress != null) {
                progress.dismiss(); //close dialog
            }
            modelArrayList = new ArrayList<PostsModel>();
            postsListAdapter = new PostsListAdapter(PostsListActivity.this, modelArrayList, userModel, favoriteService);
            postsList.setAdapter(postsListAdapter);
            try{
                // Remove unexpected characters that might be added to beginning of the string
                result = result.substring(result.indexOf("["));
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i<jArray.length(); i++){
                    JSONObject js = jArray.getJSONObject(i);
                    PostsModel p = new PostsModel(js.getString("id"),js.getString("owner_name"),js.getString("owner_email"),js.getString("owner_mobile"),js.getString("owner_mobile_hide"),js.getString("property_type"),js.getString("renter_type"),js.getString("rent_price"),js.getString("bedrooms"),js.getString("bathrooms"),js.getString("square_footage"),js.getString("amenities"),js.getString("location"),js.getString("address"),js.getString("latitude"),js.getString("longitude"),js.getString("description"),js.getString("image_name"),js.getString("images_path"),js.getString("is_enable"),js.getString("created_by_id"),js.getString("updated_by_id"),js.getString("created_at"));
                    modelArrayList.add(p);
                    Log.e(TAG, js.getString("owner_name")+ "");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            postsListAdapter.notifyDataSetChanged(); //notify the ListView to get new records
        }

    }

}
