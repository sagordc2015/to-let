package com.apps.toletbd.tolet.asynctask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.feedback.FeedbackModel;

import org.json.JSONArray;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedBackBackgroundWorker extends AsyncTask<String,Void,String> {

    private Context context;
    private ProgressDialog progress;

    private ArrayList<FeedbackModel> modelArrayList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public FeedBackBackgroundWorker(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
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
    protected String doInBackground(String...params){
        String type = params[0];

        if(type.equals("feedback_insert")) {
            String insert_url = "http://to-let.nhp-bd.com/feedback_insert.php";
            try {
                String fbMessage = params[1];
                String postedId = params[2];
                String userId = params[3];
                String userName = params[4];
                String userImage = params[5];
                String userImagePath = params[6];
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

                String post_data = URLEncoder.encode("fbMessage","UTF-8")+"="+URLEncoder.encode(fbMessage,"UTF-8")+"&"
                        +URLEncoder.encode("postedId","UTF-8")+"="+URLEncoder.encode(postedId,"UTF-8")+"&"
                        +URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("userName","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("userImage","UTF-8")+"="+URLEncoder.encode(userImage,"UTF-8")+"&"
                        +URLEncoder.encode("userImagePath","UTF-8")+"="+URLEncoder.encode(userImagePath,"UTF-8")+"&"
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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(type.equals("feedback_select")) {
            String postedId = params[1];
            String select_url = "http://to-let.nhp-bd.com/feedback_select.php?postedId="+postedId;
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

        modelArrayList = new ArrayList<FeedbackModel>();
        try{
            result = result.substring(result.indexOf("[")); // Remove unexpected characters that might be added to beginning of the string
            JSONArray jArray = new JSONArray(result);
            for(int i=0; i<jArray.length(); i++){
                JSONObject js = jArray.getJSONObject(i);
                FeedbackModel p = new FeedbackModel(js.getString("id"),js.getString("fb_message"),js.getString("posted_id"),js.getString("user_id"),js.getString("user_name"),js.getString("user_image"),js.getString("user_image_path"),js.getString("created_by_id"),js.getString("updated_by_id"),js.getString("created_at"));
                modelArrayList.add(p);
                listView.setVisibility(View.VISIBLE);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        for(FeedbackModel obj : modelArrayList){
            adapter.add(obj.getUserName()+" : "+obj.getFbMessage() +"\nPosted at "+dateFormatFromTimestamp(obj.getCreatedAt()));
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); //notify the ListView to get new records
    }

    //====================================================| Date Format
    public String dateFormatFromTimestamp(String input) {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(input);
        Date date = new Date(ts.getTime());
        return String.valueOf(sdf.format(date));
    }

}
