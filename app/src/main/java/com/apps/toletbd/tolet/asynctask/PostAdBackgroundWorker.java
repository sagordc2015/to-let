package com.apps.toletbd.tolet.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.adpost.PostsListActivity;

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

public class PostAdBackgroundWorker extends AsyncTask<String,Void,String> {

    private static final String TAG = "PostAdBackgroundWorker";
    private Context context;
    private AlertDialog alertDialog;
    private ProgressDialog progress;

    public PostAdBackgroundWorker(Context ctx) {
        context = ctx;
        progress = new ProgressDialog(ctx);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String server_url = "http://to-let.nhp-bd.com/postad_insert.php";
        if(type.equals("insert_ad")) {
            try {
                String ownerName = params[1];
                String ownerEmail = params[2];
                String ownerMobile = params[3];
                String ownerMobileHide = params[4];
                String propertyType = params[5];
                String renterType = params[6];
                String rentPrice = params[7];
                String bedrooms = params[8];
                String bathrooms = params[9];
                String squareFootage = params[10];
                String amenities = params[11];
                String location = params[12];
                String address = params[13];
                String latitude = params[14];
                String longitude = params[15];
                String description = params[16];
                String imageName = params[17];
                String imagesPath = params[18];
                String isEnable = params[19];
                String encodeImage = params[20];
                String createdById = "";
                String updatedById = "";
                String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

                String[] arr = encodeImage.replaceAll("[\\[\\]]", "").split(",");
                String[] arrrr = imageName.replaceAll("[\\[\\]]", "").split(",");
                //Log.d(TAG, arr[0]);
                //Log.d(TAG, arrrr[0]);

                URL url = new URL(server_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("ownerName","UTF-8")+"="+URLEncoder.encode(ownerName,"UTF-8")+"&"
                        +URLEncoder.encode("ownerEmail","UTF-8")+"="+URLEncoder.encode(ownerEmail,"UTF-8")+"&"
                        +URLEncoder.encode("ownerMobile","UTF-8")+"="+URLEncoder.encode(ownerMobile,"UTF-8")+"&"
                        +URLEncoder.encode("ownerMobileHide","UTF-8")+"="+URLEncoder.encode(ownerMobileHide,"UTF-8")+"&"
                        +URLEncoder.encode("propertyType","UTF-8")+"="+URLEncoder.encode(propertyType,"UTF-8")+"&"
                        +URLEncoder.encode("renterType","UTF-8")+"="+URLEncoder.encode(renterType,"UTF-8")+"&"
                        +URLEncoder.encode("rentPrice","UTF-8")+"="+URLEncoder.encode(rentPrice,"UTF-8")+"&"
                        +URLEncoder.encode("bedrooms","UTF-8")+"="+URLEncoder.encode(bedrooms,"UTF-8")+"&"
                        +URLEncoder.encode("bathrooms","UTF-8")+"="+URLEncoder.encode(bathrooms,"UTF-8")+"&"
                        +URLEncoder.encode("squareFootage","UTF-8")+"="+URLEncoder.encode(squareFootage,"UTF-8")+"&"
                        +URLEncoder.encode("amenities","UTF-8")+"="+URLEncoder.encode(amenities,"UTF-8")+"&"
                        +URLEncoder.encode("location","UTF-8")+"="+URLEncoder.encode(location,"UTF-8")+"&"
                        +URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"
                        +URLEncoder.encode("latitude","UTF-8")+"="+URLEncoder.encode(latitude,"UTF-8")+"&"
                        +URLEncoder.encode("longitude","UTF-8")+"="+URLEncoder.encode(longitude,"UTF-8")+"&"
                        +URLEncoder.encode("description","UTF-8")+"="+URLEncoder.encode(description,"UTF-8")+"&"
                        +URLEncoder.encode("imageName","UTF-8")+"="+URLEncoder.encode(imageName,"UTF-8")+"&"
                        +URLEncoder.encode("imagesPath","UTF-8")+"="+URLEncoder.encode(imagesPath,"UTF-8")+"&"
                        +URLEncoder.encode("isEnable","UTF-8")+"="+URLEncoder.encode(isEnable,"UTF-8")+"&"
                        +URLEncoder.encode("encodeImage","UTF-8")+"="+URLEncoder.encode(encodeImage,"UTF-8")+"&"
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
        //alertDialog.setTitle("Post Ad Status");
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Insert successfully")) {
            Intent intent = new Intent(context, PostsListActivity.class);
            context.startActivity(intent);
            if(progress != null) {
                progress.dismiss(); //close the dialog if error occurs
            }
            Toast.makeText(context, "Insert successfully", Toast.LENGTH_SHORT).show();
        }
        //alertDialog.setMessage(result);
        //alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
