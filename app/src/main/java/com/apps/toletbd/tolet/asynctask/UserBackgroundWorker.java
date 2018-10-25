package com.apps.toletbd.tolet.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.apps.toletbd.tolet.HomeActivity;
import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.signin.SignInActivity;
import com.apps.toletbd.tolet.user.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.sql.Timestamp;

public class UserBackgroundWorker extends AsyncTask<String,Void,String> {

    private static final String TAG = "UserBackgroundWorker";
    private Context context;
    private AlertDialog alertDialog;
    private ProgressDialog progress;

    public UserBackgroundWorker(Context ctx) {
        context = ctx;
        progress = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        //String server_url = "http://192.185.81.151/user.php";

        if(type.equals("insert_user")) {
            //progressDialog();
            String insert_url = "http://to-let.nhp-bd.com/user_insert.php";
            try {
                String userName = params[1];
                String userMobile = params[2];
                String isOwner = params[3];
                String createdById = "";
                String updatedById = "";
                String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("userMobile","UTF-8")+"="+URLEncoder.encode(userMobile,"UTF-8")+"&"
                        +URLEncoder.encode("isOwner","UTF-8")+"="+URLEncoder.encode(isOwner,"UTF-8")+"&"
                        +URLEncoder.encode("createdById","UTF-8")+"="+URLEncoder.encode(createdById,"UTF-8")+"&"
                        +URLEncoder.encode("updatedById","UTF-8")+"="+URLEncoder.encode(updatedById,"UTF-8")+"&"
                        +URLEncoder.encode("createdAt","UTF-8")+"="+URLEncoder.encode(createdAt,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            } catch (IOException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            }
        }

        if(type.equals("update_user")) {
            //progressDialog();


            String update_url = "http://to-let.nhp-bd.com/user_update.php";
            try {
                String userName = params[1];
                String userRelation = params[2];
                String userOccupation = params[3];
                String userEmail = params[4];
                String userMobile = params[5];
                String userNid = params[6];
                String userAddress = params[7];
                String userImage = params[8];
                String userImagePath = params[9];
                String isOwner = params[10];
                String encodeImage = params[11];
                String userId = params[12];
                String createdById = "";
                String updatedById = "";
                String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("userRelation","UTF-8")+"="+URLEncoder.encode(userRelation,"UTF-8")+"&"
                        +URLEncoder.encode("userOccupation","UTF-8")+"="+URLEncoder.encode(userOccupation,"UTF-8")+"&"
                        +URLEncoder.encode("userEmail","UTF-8")+"="+URLEncoder.encode(userEmail,"UTF-8")+"&"
                        +URLEncoder.encode("userMobile","UTF-8")+"="+URLEncoder.encode(userMobile,"UTF-8")+"&"
                        +URLEncoder.encode("userNid","UTF-8")+"="+URLEncoder.encode(userNid,"UTF-8")+"&"
                        +URLEncoder.encode("userAddress","UTF-8")+"="+URLEncoder.encode(userAddress,"UTF-8")+"&"
                        +URLEncoder.encode("userImage","UTF-8")+"="+URLEncoder.encode(userImage,"UTF-8")+"&"
                        +URLEncoder.encode("userImagePath","UTF-8")+"="+URLEncoder.encode(userImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("isOwner","UTF-8")+"="+URLEncoder.encode(isOwner,"UTF-8")+"&"
                        +URLEncoder.encode("encodeImage","UTF-8")+"="+URLEncoder.encode(encodeImage,"UTF-8")+"&"
                        +URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("createdById","UTF-8")+"="+URLEncoder.encode(createdById,"UTF-8")+"&"
                        +URLEncoder.encode("updatedById","UTF-8")+"="+URLEncoder.encode(updatedById,"UTF-8")+"&"
                        +URLEncoder.encode("createdAt","UTF-8")+"="+URLEncoder.encode(createdAt,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            } catch (IOException e) {
                if(progress != null) {
                    progress.dismiss(); //close the dialog if error occurs
                }
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        progress.setMessage(context.getString(R.string.progress));
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("User Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Insert successfully")) {
            if(progress != null) {
                progress.dismiss(); //close the dialog if error occurs
            }
            Intent intent = new Intent(context, SignInActivity.class);
            context.startActivity(intent);
            Toast.makeText(context, "Insert successfully", Toast.LENGTH_SHORT).show();
        } else {
            //alertDialog("Server problem!");
        }

        if (result.equals("Updated successfully")) {
            if(progress != null) {
                progress.dismiss(); //close the dialog if error occurs
            }
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            //alertDialog("Server problem!");
        }
        /*try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = null;
            for(int i=0; i<jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("user_name");
                Log.d("UserBackgroundWorker", String.valueOf(name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //alertDialog.setMessage(result);
        //alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    //====================================================| Alert Message
    public void alertDialog(String msg) {
        new AlertDialog.Builder(context)
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
