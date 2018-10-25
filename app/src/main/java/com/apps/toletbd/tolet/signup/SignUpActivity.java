package com.apps.toletbd.tolet.signup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.Utility.DownloadImageTask;
import com.apps.toletbd.tolet.asynctask.UserBackgroundWorker;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.signin.SignInActivity;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity{

    private static final String TAG = "SignUpActivity";
    private UserService userService;
    private UserModel userModel;

    private SharedPreferences preferences;
    private ImageView photo;
    private EditText name, phone, occupation, email, nid, address;
    private Button button;
    private RadioButton isRender, isOwner;
    private TextView userId;
    private Spinner relation;
    private TextInputLayout layoutName, layoutPhone, layoutEmail, layoutAddress;
    private ArrayAdapter adapter;

    private static final int RESULT_LOAD_IMAGE = 1;
    private OutputStream output;
    private String imagePath;
    private String imageName;

    private Activity context;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.userService = new UserService(this);

        //===============================================| findViewById
        userId = (TextView) findViewById(R.id.user_id);

        photo = (ImageView) findViewById(R.id.reg_passport);

        isRender = (RadioButton) findViewById(R.id.reg_render);
        isOwner = (RadioButton) findViewById(R.id.reg_owner);

        name = (EditText) findViewById(R.id.reg_full_name);
        name.addTextChangedListener(new MyTextWatcher(name));
        layoutName = (TextInputLayout) findViewById(R.id.layout_reg_full_name);

        phone = (EditText) findViewById(R.id.reg_phone_number);
        phone.addTextChangedListener(new MyTextWatcher(phone));
        layoutPhone = (TextInputLayout) findViewById(R.id.layout_reg_phone_number);

        relation = (Spinner) findViewById(R.id.reg_relation);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.relation_array));
        relation.setAdapter(adapter);

        occupation = (EditText) findViewById(R.id.reg_occupation);

        email = (EditText) findViewById(R.id.reg_email);
        //email.addTextChangedListener(new MyTextWatcher(email));
        //layoutEmail = (TextInputLayout) findViewById(R.id.layout_reg_email);

        nid = (EditText) findViewById(R.id.reg_nid);
        //nid.addTextChangedListener(new MyTextWatcher(nid));
        //layoutNid = (TextInputLayout) findViewById(R.id.layout_reg_nid);

        address = (EditText) findViewById(R.id.reg_address);
        //address.addTextChangedListener(new MyTextWatcher(address));
        //layoutAddress = (TextInputLayout) findViewById(R.id.layout_reg_address);

        //errMsg = (TextView) findViewById(R.id.reg_error_msg);
        button = (Button) findViewById(R.id.reg_button);


        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        String userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");

        //Log.d(TAG, String.valueOf(userMobile)+" "+String.valueOf(userModel.getUserId()));

        context = this;
        if (userMobile != null && !userMobile.isEmpty()) {
            new BackgroundWorker().execute("select_user", userMobile);
        }

        //===============================================| Insert Data and Update Data
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().isEmpty() && phone.getText().toString().trim().isEmpty()) {
                    layoutName.setError("required!");
                    layoutPhone.setError("required!");
                } else if (phone.getText().toString().trim().length() != 11) {
                    layoutPhone.setError("length must be 11 digits");
                } else {
                    if (userId.getText().toString().isEmpty()) {
                        insertData(name.getText().toString().trim(), phone.getText().toString().trim(), String.valueOf(isOwner.isChecked()));
                        insertUserServer(name.getText().toString().trim(), phone.getText().toString().trim(), String.valueOf(isOwner.isChecked()));
                    } else {
                        updateData(name.getText().toString().trim(), relation.getSelectedItem().toString(), occupation.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(), nid.getText().toString().trim(), address.getText().toString().trim(), imageName, "http://to-let.nhp-bd.com/UserPhoto/", String.valueOf(isOwner.isChecked()), userId.getText().toString());
                        updateUserServer(name.getText().toString().trim(), relation.getSelectedItem().toString(), occupation.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(), nid.getText().toString().trim(), address.getText().toString().trim(), imageName, "http://to-let.nhp-bd.com/UserPhoto/", String.valueOf(isOwner.isChecked()), userId.getText().toString());
                    }
                }
            }
        });
    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //===============================================| Insert Data
    private void insertData(String fullName, String phoneNumber, String isOwner) {
        UserModel model = new UserModel(fullName, phoneNumber, isOwner);
        long data = this.userService.addData(model);
        if (data > 0){
            Toast.makeText(getApplicationContext(),"Registration successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Do not registered unsuccessfully", Toast.LENGTH_LONG).show();
        }
    }

    //===============================================| Update Data
    private void updateData(String regName, String regRelation, String occupation, String regEmail, String regPhone, String nid, String regAddress, String image, String path, String isRegOwner, String userId) {
        UserModel model = new UserModel( userId, regName, regRelation, occupation, regEmail, regPhone, nid, regAddress, image, path, isRegOwner);
        long data = this.userService.updateByMobile(model, String.valueOf(regPhone));
        if (data > 0){
            Toast.makeText(getApplicationContext(), "Registration updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //===============================================| MySQL Server
    public void insertUserServer(String fullName, String phoneNumber, String isOwner){
        String type = "insert_user";
        UserBackgroundWorker worker = new UserBackgroundWorker(this);
        worker.execute(type, fullName, phoneNumber, isOwner);
    }
    public void updateUserServer(String regName, String regRelation, String occupation, String regEmail, String regPhone, String nid, String regAddress, String image, String path, String isRegOwner, String userId){
        String type = "update_user";
        String encodeImage = imageToServer();
        UserBackgroundWorker worker = new UserBackgroundWorker(this);
        worker.execute(type, regName, regRelation, occupation, regEmail, regPhone, nid, regAddress, image, path, isRegOwner, encodeImage, userId);
    }
    //Encoding from passport image
    public String imageToServer(){
        String encode = null;
        if (photo.getVisibility() == View.VISIBLE) {
            Bitmap bitmap = ((BitmapDrawable)photo.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            encode = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        }
        return encode;
    }

    //====================================================| For Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectImage = data.getData();
            /*if (userModel.getUserImage() == null) {
                imageName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".png";
            }*/
            imageName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".png";
            photo.setImageURI(selectImage);
        }
    }

    //===============================================| TextWatcher
    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.reg_full_name:
                    layoutName.setErrorEnabled(false);
                    break;
                case R.id.reg_phone_number:
                    layoutPhone.setErrorEnabled(false);
                    break;
                /*case R.id.reg_email:
                    layoutEmail.setErrorEnabled(false);
                    break;
                case R.id.reg_address:
                    layoutAddress.setErrorEnabled(false);
                    break;*/
            }
        }
    }

    //===============================================| Progress Bar
    public void progressBar() {
        progress = new ProgressDialog(this);
        //progress.setTitle(getResources().getString( R.string.loading_title));
        progress.setMessage(getResources().getString( R.string.progress));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
    }

    private class BackgroundWorker extends AsyncTask<String,Void,String> {

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
        protected String doInBackground(String... params) {
            String type = params[0];
            String userMobile = params[1];

            if (type.equals("select_user")) {
                String select_url = "http://to-let.nhp-bd.com/userselect.php?userMobile="+userMobile;
                try {
                    URLConnection connection = new URL(select_url).openConnection();
                    connection.setConnectTimeout(1000 * 30);
                    connection.setReadTimeout(1000 * 30);

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"iso-8859-1"));
                    String result="";
                    String line="";
                    while((line = bufferedReader.readLine())!= null) {
                        result += line;
                    }
                    bufferedReader.close();

                    //Log.d(TAG, String.valueOf(result));
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
        protected void onPostExecute(String result) {

            if (result != null && !result.isEmpty()) {

                photo.setVisibility(View.VISIBLE);
                relation.setVisibility(View.VISIBLE);
                occupation.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                nid.setVisibility(View.VISIBLE);
                address.setVisibility(View.VISIBLE);

                //Load image from gallery
                photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                });


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);

                    userId.setText(jsonObject.getString("id"));
                    name.setText(jsonObject.getString("user_name"));
                    relation.setSelection(adapter.getPosition(jsonObject.getString("user_relation")));
                    occupation.setText(jsonObject.getString("user_occupation"));
                    phone.setText(jsonObject.getString("user_mobile"));
                    email.setText(jsonObject.getString("user_email"));
                    nid.setText(jsonObject.getString("user_nid"));
                    address.setText(jsonObject.getString("user_address"));

                    if(jsonObject.getString("is_user_owner").equals("true")){
                        isOwner.setChecked(true);
                    } else {
                        isRender.setChecked(true);
                    }

                    imageName = jsonObject.getString("user_image");
                    String img = jsonObject.getString("user_path")+jsonObject.getString("user_image");
                    new DownloadImageTask(context, photo).execute(img);

                    if(progress != null) {
                        progress.dismiss(); //close the dialog if error occurs
                    }
                    //UserModel model = new UserModel(jsonObject.getString("id"),jsonObject.getString("user_name"),jsonObject.getString("user_relation"),jsonObject.getString("user_occupation"),jsonObject.getString("user_email"),jsonObject.getString("user_mobile"),jsonObject.getString("user_address"),jsonObject.getString("user_nid"),jsonObject.getString("user_image"),jsonObject.getString("user_path"),jsonObject.getString("is_user_owner"),jsonObject.getString("created_by_id"),jsonObject.getString("updated_by_id"),jsonObject.getString("created_at"));
                    //Log.d(TAG, String.valueOf(jsonObject.getString("user_name")));
                } catch (JSONException e) {
                    if(progress != null) {
                        progress.dismiss(); //close the dialog if error occurs
                    }
                    e.printStackTrace();
                }
            } else {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
            }
        }
    }

    //====================================================| Alert Message
    public void alertDialog(String msg) {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
