package com.apps.toletbd.tolet.signin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.HomeActivity;
import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.signup.SignUpActivity;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";

    private SharedPreferences preferences;
    private ProgressDialog progressBar;

    private EditText phone;
    private TextInputLayout layoutPhone;
    private TextView reg;

    private UserModel model;
    private UserService userService;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.userService = new UserService(this);
        context = this;

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY,false);

        if (isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }

        phone = (EditText) findViewById(R.id.login_phone_number);
        phone.addTextChangedListener(new MyTextWatcher(phone));
        layoutPhone = (TextInputLayout) findViewById(R.id.layout_login_phone_number);

        reg = (TextView) findViewById(R.id.sign_up_text);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        Button button = (Button) findViewById(R.id.sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BackgroundWorker(context).execute(phone.getText().toString().trim());

                if (phone.getText().toString().trim().isEmpty()) {
                    layoutPhone.setError("required!");
                } else if (phone.getText().toString().trim().length() != 11) {
                    layoutPhone.setError("length must be 11 digits");
                }
            }
        });

    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //===============================================| AsyncTask
    private class BackgroundWorker extends AsyncTask<String,Void,String> {

        private Context context;
        private ProgressDialog progress;

        public BackgroundWorker(Context context) {
            this.context = context;
        }

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
            String userMobile = params[0];
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

                Log.d(TAG, String.valueOf(result));
                return result;
            } catch (Exception e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("")) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    //===============================================| Writes SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(ConstantKey.USER_IS_LOGGED_KEY, true);
                    editor.putString(ConstantKey.USER_ID_KEY, jsonObject.getString("id")); //user id
                    editor.putString(ConstantKey.USER_NAME_KEY, jsonObject.getString("user_name")); //user name
                    editor.putString(ConstantKey.USER_MOBILE_KEY, phone.getText().toString().trim()); //user mobile
                    editor.apply();
                    editor.commit();

                    model = userService.getDataByMobile(phone.getText().toString().trim());
                    if (model==null) {
                        model = new UserModel(jsonObject.getString("id"), jsonObject.getString("user_name"), phone.getText().toString().trim(), jsonObject.getString("is_user_owner"));
                        long data = userService.addData(model);
                        if (data > 0) {
                            Log.d(TAG, String.valueOf("Insert successfully"));
                        }
                    } else {
                        model = new UserModel( jsonObject.getString("id"), jsonObject.getString("user_name"), jsonObject.getString("user_relation"), jsonObject.getString("user_occupation"), jsonObject.getString("user_email"), jsonObject.getString("user_mobile"), jsonObject.getString("user_nid"), jsonObject.getString("user_address"), jsonObject.getString("user_image"), jsonObject.getString("user_path"), jsonObject.getString("is_user_owner"));
                        long data = userService.updateByMobile(model, phone.getText().toString().trim());
                        if (data > 0){
                            Log.d(TAG, String.valueOf("Update successfully"));
                        }
                    }

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    if(progress != null) {
                        progress.dismiss(); //close the dialog if error occurs
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                layoutPhone.setError("Do not registered before");
                reg.setVisibility(View.VISIBLE);
            }
        }
    }


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
                case R.id.login_phone_number:
                    layoutPhone.setErrorEnabled(false);
                    break;
            }
        }
    }
}
