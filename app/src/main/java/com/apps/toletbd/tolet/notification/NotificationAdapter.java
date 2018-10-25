package com.apps.toletbd.tolet.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.Utility.DownloadImageTask;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

    private static final String TAG = "NotificationAdapter";
    private Activity context;
    private ArrayList<NotificationModel> arrayList;

    public NotificationAdapter(Activity context, ArrayList<NotificationModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public static class ViewHolder {
        TextView renterName, renterMobile, ownerProperty, ownerName; //user_name, user_mobile, post_property_type, post_owner_name
        ImageView renterImage;
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
        final NotificationModel dataModel = arrayList.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.notification_list_row, parent, false);
            holder.renterName = (TextView) convertView.findViewById(R.id.user_name);
            holder.renterMobile = (TextView) convertView.findViewById(R.id.user_mobile);
            holder.ownerProperty = (TextView) convertView.findViewById(R.id.post_property_type);
            holder.ownerName = (TextView) convertView.findViewById(R.id.post_owner_name);
            holder.renterImage = (ImageView) convertView.findViewById(R.id.post_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.renterName.setText(arrayList.get(position).getUserName());
        holder.renterMobile.setText(arrayList.get(position).getUserMobile());
        holder.ownerProperty.setText(arrayList.get(position).getPostPropertyType());
        holder.ownerName.setText(arrayList.get(position).getPostOwnerName());
        try {
            String result = arrayList.get(position).getPostImageName();
            String imgName = result.replaceAll("[\\[\\]]", "");
            new DownloadImageTask(context, holder.renterImage).execute(arrayList.get(position).getPostImagePath()+imgName);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final int positionWindow = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListItem(positionWindow, parent);
                //Toast.makeText(context, ""+dataModel.getPostOwnerName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private void showListItem(final int position, ViewGroup parent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        View view = LayoutInflater.from(context).inflate(R.layout.notification_details, (ViewGroup) context.findViewById(R.id.notification_details_id));
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();

        final AlertDialog dialog = builder.show();

        final TextView renterName = (TextView) view.findViewById(R.id.not_renter_name);
        renterName.setText(arrayList.get(position).getUserName());
        final TextView status = (TextView) view.findViewById(R.id.not_renter_marital_status);
        status.setText(arrayList.get(position).getUserMaritalStatus());
        final TextView occupation = (TextView) view.findViewById(R.id.not_renter_occupation);
        occupation.setText(arrayList.get(position).getUserOccupation());
        final TextView mobile = (TextView) view.findViewById(R.id.not_renter_mobile);
        mobile.setText(arrayList.get(position).getUserMobile());
        final TextView address = (TextView) view.findViewById(R.id.not_renter_address);
        address.setText(arrayList.get(position).getUserAddress());
        final TextView ownerName = (TextView) view.findViewById(R.id.not_owner_name);
        ownerName.setText(arrayList.get(position).getPostOwnerName());
        final TextView ownerType = (TextView) view.findViewById(R.id.not_owner_ad_type);
        ownerType.setText(arrayList.get(position).getPostPropertyType());

        final ImageView img = (ImageView) view.findViewById(R.id.not_renter_photo);
        try {
            String[] arr = arrayList.get(position).getUserImage().replaceAll("[\\[\\]]", "").split(",");
            String imgUrl = arrayList.get(position).getUserImagePath()+arr[0];
            new DownloadImageTask(context, img).execute(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Button call = (Button) view.findViewById(R.id.not_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(arrayList.get(position).getUserMobile());
            }
        });

        //-----------| Close Button
        final Button btn = (Button) view.findViewById(R.id.not_close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
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
