package com.apps.toletbd.tolet.favorite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.Utility.DownloadImageTask;
import com.apps.toletbd.tolet.adpost.PostsModel;

import java.util.ArrayList;

public class FavoriteAdapter extends BaseAdapter {

    private static final String TAG = "FavoriteAdapter";
    private Activity context;
    private ArrayList<PostsModel> arrayList;

    public FavoriteAdapter(Activity context, ArrayList<PostsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public static class ViewHolder {
        TextView price, address, details;
        ImageView image;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PostsModel dataModel = arrayList.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.post_list_row, parent, false);
            holder.price = (TextView) convertView.findViewById(R.id.post_rent_price);
            holder.address = (TextView) convertView.findViewById(R.id.post_address);
            holder.details = (TextView) convertView.findViewById(R.id.post_bed_bath_size);
            holder.image = (ImageView) convertView.findViewById(R.id.post_image);
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

        return convertView;
    }
}
