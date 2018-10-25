package com.apps.toletbd.tolet.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;

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
import java.net.URLEncoder;
import java.sql.Timestamp;

public class NotificationBackgroundWorker extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog progress;

    public NotificationBackgroundWorker(Context ctx) {
        context = ctx;
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

        String type = params[0];
        String server_url = "http://to-let.nhp-bd.com/notification_insert.php";

        if(type.equals("insert_notification")) {
            try {
                String userId = params[1];
                String userName = params[2];
                String userMaritalStatus = params[3];
                String userMobile = params[4];
                String userAddress = params[5];
                String userOccupation = params[6];
                String userImage = params[7];
                String userImagePath = params[8];
                String postId = params[9];
                String postOwnerName = params[10];
                String postOwnerMobile = params[11];
                String postPropertyType = params[12];
                String postImageName = params[13];
                String postImagePath = params[14];
                String createdById = "";
                String updatedById = "";
                String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

                URL url = new URL(server_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("userMaritalStatus","UTF-8")+"="+URLEncoder.encode(userMaritalStatus,"UTF-8")+"&"
                        +URLEncoder.encode("userMobile","UTF-8")+"="+URLEncoder.encode(userMobile,"UTF-8")+"&"
                        +URLEncoder.encode("userAddress","UTF-8")+"="+URLEncoder.encode(userAddress,"UTF-8")+"&"
                        +URLEncoder.encode("userOccupation","UTF-8")+"="+URLEncoder.encode(userOccupation,"UTF-8")+"&"
                        +URLEncoder.encode("userImage","UTF-8")+"="+URLEncoder.encode(userImage,"UTF-8")+"&"
                        +URLEncoder.encode("userImagePath","UTF-8")+"="+URLEncoder.encode(userImagePath,"UTF-8")+"&"
                        +URLEncoder.encode("postId","UTF-8")+"="+URLEncoder.encode(postId,"UTF-8")+"&"
                        +URLEncoder.encode("postOwnerName","UTF-8")+"="+URLEncoder.encode(postOwnerName,"UTF-8")+"&"
                        +URLEncoder.encode("postOwnerMobile","UTF-8")+"="+URLEncoder.encode(postOwnerMobile,"UTF-8")+"&"
                        +URLEncoder.encode("postPropertyType","UTF-8")+"="+URLEncoder.encode(postPropertyType,"UTF-8")+"&"
                        +URLEncoder.encode("postImageName","UTF-8")+"="+URLEncoder.encode(postImageName,"UTF-8")+"&"
                        +URLEncoder.encode("postImagePath","UTF-8")+"="+URLEncoder.encode(postImagePath,"UTF-8")+"&"
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
        if(progress != null) {
            progress.dismiss(); //close dialog
        }
        if (result.equals("Insert successfully")) {
            Toast.makeText(context, "Insert successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
