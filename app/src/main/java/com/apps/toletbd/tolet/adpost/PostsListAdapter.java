package com.apps.toletbd.tolet.adpost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.Utility.DownloadImageTask;
import com.apps.toletbd.tolet.asynctask.FeedBackBackgroundWorker;
import com.apps.toletbd.tolet.asynctask.NotificationBackgroundWorker;
import com.apps.toletbd.tolet.favorite.FavoriteModel;
import com.apps.toletbd.tolet.favorite.FavoriteService;
import com.apps.toletbd.tolet.feedback.FeedbackModel;
import com.apps.toletbd.tolet.feedback.FeedbackService;
import com.apps.toletbd.tolet.notification.NotificationModel;
import com.apps.toletbd.tolet.notification.NotificationService;
import com.apps.toletbd.tolet.user.UserModel;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostsListAdapter extends BaseAdapter {

    private static final int IO_BUFFER_SIZE = 4 * 1024;

    private static final String TAG = "PostsListAdapter";
    private Activity context;
    protected ArrayList<PostsModel> arrayList;
    //private PostsService service;
    protected ArrayList<FeedbackModel> fbArrayList;
    //private FeedbackService fbService;
    private UserModel userModel;
    //private NotificationService notService;
    private FavoriteService favoriteService;


    /*public PostsListAdapter(Activity context, ArrayList<PostsModel> arrayList, PostsService service, ArrayList<FeedbackModel> fbArrayList, FeedbackService fbService, UserModel userModel, NotificationService notService, FavoriteService favoriteService) {
        this.context = context;
        this.arrayList = arrayList;
        this.service = service;
        this.fbArrayList = fbArrayList;
        this.fbService = fbService;
        this.userModel = userModel;
        this.notService = notService;
        this.favoriteService = favoriteService;
    }*/

    public PostsListAdapter(Activity context, ArrayList<PostsModel> arrayList, UserModel userModel, FavoriteService favoriteService) {
        this.context = context;
        this.arrayList = arrayList;
        this.userModel = userModel;
        this.favoriteService = favoriteService;
    }

    public static class ViewHolder {
        TextView price, address, details;
        ImageView image; //post_image, post_share, post_favorite;
        Button share, favorite; //post_image, post_share, post_favorite;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        final PostsModel dataModel = arrayList.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.post_list_row, parent, false);
            holder.price = (TextView) convertView.findViewById(R.id.post_rent_price);
            holder.address = (TextView) convertView.findViewById(R.id.post_address);
            holder.details = (TextView) convertView.findViewById(R.id.post_bed_bath_size);
            holder.image = (ImageView) convertView.findViewById(R.id.post_image);
            holder.share = (Button) convertView.findViewById(R.id.post_share);
            holder.favorite = (Button) convertView.findViewById(R.id.post_favorite);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.price.setText("TK "+arrayList.get(position).getRentPrice()+" /monthly");
        holder.address.setText(arrayList.get(position).getAddress());
        holder.details.setText(arrayList.get(position).getBedrooms()+" Beds, "+arrayList.get(position).getBathrooms()+" Baths, "+arrayList.get(position).getSquareFootage()+" (sq.ft)");
        try {
            //holder.image.setImageBitmap(loadImageToListView(position)); //Image load into ListView
            String[] arr = arrayList.get(position).getImageName().replaceAll("[\\[\\]]", "").split(",");
            String imgUrl = arrayList.get(position).getImagesPath()+arr[0];
            new DownloadImageTask(context, holder.image).execute(imgUrl);
        } catch (Exception e){
            e.getStackTrace();
        }

        final int positionWindow = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItem(positionWindow, parent);
                //Toast.makeText(parent.getContext(), "view clicked: " + dataModel.getFullName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    //====================================================| Show Details
    private void showListItem(final int position, final ViewGroup parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        View view = LayoutInflater.from(context).inflate(R.layout.post_details, (ViewGroup) context.findViewById(R.id.post_details_id));
        builder.setView(view); // Set above view in alert dialog.
        builder.setCancelable(true);
        builder.create();

        final AlertDialog dialog = builder.show(); // Because only AlertDialog has cancel method.

        final ImageView img = (ImageView) view.findViewById(R.id.image1);
        //img.setImageBitmap(loadImageToListView(position));
        String[] arr = arrayList.get(position).getImageName().replaceAll("[\\[\\]]", "").split(",");
        String imgUrl = arrayList.get(position).getImagesPath()+arr[0];
        new DownloadImageTask(context, img).execute(imgUrl);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewerDialog(position, parent);
            }
        });

        final TextView loc = (TextView) view.findViewById(R.id.post_details_location);
        loc.setText(arrayList.get(position).getLocation());
        final TextView price = (TextView) view.findViewById(R.id.post_details_price);
        price.setText("TK "+arrayList.get(position).getRentPrice()+" /monthly");
        final TextView owner = (TextView) view.findViewById(R.id.post_details_owner);
        owner.setText("Posted by "+arrayList.get(position).getOwnerName());
        final TextView date = (TextView) view.findViewById(R.id.post_details_date);
        date.setText("Posted at "+dateFormatFromTimestamp(arrayList.get(position).getCreatedAt()));
        final TextView property = (TextView) view.findViewById(R.id.post_details_property);
        property.setText(arrayList.get(position).getPropertyType());
        final TextView renter = (TextView) view.findViewById(R.id.post_details_renter);
        renter.setText(arrayList.get(position).getRenterType());
        final TextView beds = (TextView) view.findViewById(R.id.post_details_beds);
        beds.setText(arrayList.get(position).getBedrooms());
        final TextView baths = (TextView) view.findViewById(R.id.post_details_baths);
        baths.setText(arrayList.get(position).getBathrooms());
        final TextView size = (TextView) view.findViewById(R.id.post_details_size);
        size.setText(arrayList.get(position).getSquareFootage());
        final TextView amenities = (TextView) view.findViewById(R.id.post_details_amenities);
        amenities.setText(arrayList.get(position).getAmenities());
        final TextView desc = (TextView) view.findViewById(R.id.post_details_desc);
        desc.setText(arrayList.get(position).getDescription());

        //-----------| Static Google Maps
        String url = "http://maps.google.com/maps/api/staticmap?center=" + arrayList.get(position).getLatitude() + "," + arrayList.get(position).getLongitude() + "&zoom=17&size=500x200&sensor=false&markers=icon:"+ BitmapDescriptorFactory.fromResource(R.drawable.ic_house2)+"|"+ arrayList.get(position).getLatitude() + "," + arrayList.get(position).getLongitude();
        final ImageView stMaps = (ImageView) view.findViewById(R.id.static_google_maps);
        new DownloadImageTask(context, stMaps).execute(url);

        //-----------| Favorite
        Button fav = (Button) view.findViewById(R.id.post_favorite);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel  != null) {
                    if (userModel.getUserMobile() != null && !userModel.getUserMobile().isEmpty() && !arrayList.get(position).getOwnerMobile().isEmpty()) {
                        FavoriteModel model = new FavoriteModel(userModel.getUserId(),userModel.getUserMobile(),arrayList.get(position).getPostId(), arrayList.get(position).getOwnerMobile());
                        long data = favoriteService.addData(model);
                        if (data > 0) {
                            Toast.makeText(context, ""+context.getString(R.string.msg_favorite_add), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        alertDialog(context.getString(R.string.msg_find_reg_user));

                    }
                }
            }
        });

        //-----------| Share
        Button share = (Button) view.findViewById(R.id.post_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, bitmap);
                context.startActivity(Intent.createChooser(share,"Share via"));
            }
        });

        //-----------| Interest | Notification
        Button interest = (Button) view.findViewById(R.id.post_details_interested);
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel  != null && userModel.getIsUserOwner().equals("false")) {
                    if (userModel.getUserRelation() != null && !userModel.getUserRelation().isEmpty() && userModel.getUserAddress() != null && !userModel.getUserAddress().isEmpty() && userModel.getUserOccupation() != null && !userModel.getUserOccupation().isEmpty()) {
                        //---------------------------------------------------------------------------------------------------------------------
                        new AlertDialog.Builder(context).setTitle(context.getString(R.string.msg_interest_title)).setMessage(context.getString(R.string.msg_interest_msg))
                                .setPositiveButton(context.getString(R.string.msg_yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //-------------------------------------| Insert into Database
                                        /*NotificationModel model = new NotificationModel(userModel.getUserId(),userModel.getUserName(), userModel.getUserRelation(), userModel.getUserMobile(), userModel.getUserAddress(), userModel.getUserOccupation(), userModel.getUserImage(), userModel.getUserImagePath(), arrayList.get(position).getPostId(), arrayList.get(position).getOwnerName(), arrayList.get(position).getOwnerMobile(), arrayList.get(position).getPropertyType(), arrayList.get(position).getImageName(), arrayList.get(position).getImagesPath());
                                        long data = notService.addData(model);
                                        if (data > 0) {
                                            Toast.makeText(context, "Your interest has been sent to owner", Toast.LENGTH_SHORT).show();
                                        }*/
                                        new NotificationBackgroundWorker(context).execute("insert_notification", userModel.getUserId(),userModel.getUserName(), userModel.getUserRelation(), userModel.getUserMobile(), userModel.getUserAddress(), userModel.getUserOccupation(), userModel.getUserImage(), userModel.getUserImagePath(), arrayList.get(position).getPostId(), arrayList.get(position).getOwnerName(), arrayList.get(position).getOwnerMobile(), arrayList.get(position).getPropertyType(), arrayList.get(position).getImageName(), arrayList.get(position).getImagesPath());
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton(context.getString(R.string.msg_neg), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                        //---------------------------------------------------------------------------------------------------------------------
                    } else {
                        alertDialog(context.getString(R.string.msg_occupation_update));
                    }
                } else {
                    alertDialog(context.getString(R.string.msg_renter));
                }
            }
        });

        //-----------| Reviews|Feedback
        final ListView feedbackList = (ListView) view.findViewById(R.id.post_feedback_list);
        //fbArrayList = (ArrayList) fbService.getAllDatas(); //get all data
        //if (fbArrayList == null) {feedbackList.setVisibility(View.VISIBLE);}
        //getAdapterData(feedbackList);
        new FeedBackBackgroundWorker(context, feedbackList).execute("feedback_select", arrayList.get(position).getPostId());

        final EditText feedback = (EditText) view.findViewById(R.id.post_details_feedback);
        Button fbBtn = (Button) view.findViewById(R.id.post_feedback_btn);
        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userModel  != null) {
                    if (!feedback.getText().toString().trim().isEmpty()) {
                        /*FeedbackModel model = new FeedbackModel(feedback.getText().toString(), arrayList.get(position).getPostId(), userModel.getUserId(),userModel.getUserName(), userModel.getUserImage(), userModel.getUserImagePath());
                        long data = fbService.addData(model); //add row
                        if (data>0){
                            fbArrayList = (ArrayList) fbService.getAllDatas(); //get all data
                            getAdapterData(feedbackList);
                            notifyDataSetChanged();
                            feedback.setText("");
                            Toast.makeText(context, "Your feedback has been sent", Toast.LENGTH_SHORT).show();
                        }*/
                        if (userModel.getIsUserOwner().equals("false")) {
                            new FeedBackBackgroundWorker(context, feedbackList).execute("feedback_insert", feedback.getText().toString(), arrayList.get(position).getPostId(), userModel.getUserId(),userModel.getUserName(), userModel.getUserImage(), userModel.getUserImagePath());
                            feedback.setText("");
                        } else {
                            alertDialog(context.getString(R.string.msg_renter));
                        }
                    }
                } else {
                    alertDialog(context.getString(R.string.msg_register_user));
                }
            }
        });

        //-----------| Call
        Button call = (Button) view.findViewById(R.id.post_details_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.get(position).getOwnerMobileHide().equals("false")) {
                    intentCall(arrayList.get(position).getOwnerMobile());
                } else {
                    alertDialog(context.getString(R.string.msg_phone_not_available));
                }
            }
        });

        //-----------| Email
        Button email = (Button) view.findViewById(R.id.post_details_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentEmail(arrayList.get(position).getOwnerEmail());
            }
        });

        //-----------| Close Button
        Button btn = (Button) view.findViewById(R.id.close_post_details);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //====================================================| Image Viewer
    private void imageViewerDialog(int position, ViewGroup parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.post_image_viewer, (ViewGroup) context.findViewById(R.id.post_image_viewer_id));
        builder.setView(view); // Set above view in alert dialog.
        builder.setCancelable(false);
        builder.create();

        final AlertDialog dialog = builder.show(); // Because only AlertDialog has cancel method.

        final LinearLayout imgGroup = (LinearLayout) view.findViewById(R.id.post_all_image);

        String[] arr = arrayList.get(position).getImageName().replaceAll("[\\[\\]]", "").split(",");
        for (int i=0; i<arr.length; i++) {
            ImageView img = new ImageView(parent.getContext());
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));
            img.setBackground(parent.getContext().getResources().getDrawable(R.drawable.image_border));
            imgGroup.addView(img);
            new DownloadImageTask(context, img).execute(arrayList.get(position).getImagesPath()+arr[i]);
        }

        final Button btn = (Button) view.findViewById(R.id.close_post_image_viewer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //No need
    private void getAdapterData(ListView feedbackList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        for(FeedbackModel obj : fbArrayList){
            adapter.add(obj.getUserName()+" : "+obj.getFbMessage() +"\nPosted at "+dateFormatFromTimestamp(obj.getCreatedAt()));
        }
        feedbackList.setAdapter(adapter);
    }


    //====================================================| Call
    private void intentCall(String number) {
        Intent dial = new Intent(Intent.ACTION_DIAL);
        try {
            dial.setData(Uri.parse("tel:" + number));
            context.startActivity(dial);
        } catch (ActivityNotFoundException e) {
            alertDialog(context.getString(R.string.msg_no_dialer));
        }
    }

    //====================================================| Email
    private void intentEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        try {
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "For Rent");
            context.startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            alertDialog(context.getString(R.string.msg_no_email));
        }
    }

    //====================================================| Image Load
    public Bitmap loadImageToListView(int position){
        Bitmap bitmap = null;
        try {
            String[] arr = arrayList.get(position).getImageName().replaceAll("[\\[\\]]", "").split(","); //third bracket replace and comma separate
            Log.d("Adapter", String.valueOf(arr.length));
            if (arr.length>0){
                File file = new File(arrayList.get(position).getImagesPath(), arr[0]);
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
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

    //====================================================| Date Format
    public String dateFormatFromTimestamp(String input) {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(input);
        Date date = new Date(ts.getTime());
        return String.valueOf(sdf.format(date));
    }

}