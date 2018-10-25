package com.apps.toletbd.tolet.adpost;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.R;
import com.apps.toletbd.tolet.asynctask.PostAdBackgroundWorker;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostAdActivity extends AppCompatActivity {

    private static final String TAG = "PostAdActivity"; //logt + Tab

    private SharedPreferences preferences;
    private ProgressDialog progressBar;
    private String userMobile;
    private String userName;
    private String id;

    private static final int RESULT_LOAD_IMAGE = 1;
    private int imageCounter = 0;
    private OutputStream output;
    private ArrayList<String> postImageName = new ArrayList<>();

    private TextView userId;
    private Button addPostImageBtn, addPostBtn;
    private EditText name, email, mobile, price, size, addr, desc;
    private CheckBox isMobile;
    private Spinner property, renter, beds, baths, location;
    private LinearLayout checkboxGroup;
    private TableRow imgGroup;

    private UserService userService;
    private UserModel userModel;

    private String lat;
    private String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        //==========================================| Change action bar title
        ActionBar bar = getSupportActionBar();
        bar.setTitle(getResources().getString(R.string.post_ad_title));

        //====================================================| SharedPreferences and User
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        userName = preferences.getString(ConstantKey.USER_NAME_KEY, "Data not found");
        userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");
        id = preferences.getString(ConstantKey.USER_ID_KEY, "Data not found");

        this.userService = new UserService(this);
        this.userModel = this.userService.getDataByMobile(userMobile);

        Toast.makeText(this, ""+id+userModel.getUserEmail(), Toast.LENGTH_SHORT).show();


        //====================================================| findViewById Initialing
        this.name = (EditText) findViewById(R.id.owner_name);
        name.setText(userName);
        this.email = (EditText) findViewById(R.id.owner_email);
        email.setText(userModel.getUserEmail());
        this.mobile = (EditText) findViewById(R.id.owner_mobile);
        mobile.setText(userMobile);
        this.isMobile = (CheckBox) findViewById(R.id.hide_mobile);
        this.property = (Spinner) findViewById(R.id.property_type);
        this.renter = (Spinner) findViewById(R.id.renter_type);
        this.price = (EditText) findViewById(R.id.rent_price);
        this.beds = (Spinner) findViewById(R.id.bedrooms);
        this.baths = (Spinner) findViewById(R.id.bathrooms);
        this.size = (EditText) findViewById(R.id.square_footage);
        this.checkboxGroup = (LinearLayout) findViewById(R.id.checkbox_group); //https://stackoverflow.com/questions/24322022/getting-multiple-value-of-checked-check-boxes-in-an-array
        this.location = (Spinner) findViewById(R.id.location);
        this.addr = (EditText) findViewById(R.id.address);
        this.desc = (EditText) findViewById(R.id.description);

        this.imgGroup = (TableRow) findViewById(R.id.image_group);

        TextView maps = (TextView) findViewById(R.id.show_maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapsDialog();
            }
        });

        this.addPostBtn = (Button) findViewById(R.id.add_post_btn);

        if (!userMobile.equals("Data not found") && userModel.getIsUserOwner().equals("true")) {
            if (userModel.getUserEmail() != null) {

                addPostBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //------------------------------------------------------
                        String ownerName = name.getText().toString().trim();
                        String ownerEmail = email.getText().toString().trim();
                        String ownerMobile = mobile.getText().toString().trim();
                        String isMobileHide = "false";
                        if (isMobile.isChecked()) {
                            isMobileHide = "true";
                        }
                        String selectProperty = property.getSelectedItem().toString();
                        String selectRenter = renter.getSelectedItem().toString();
                        String priceRate = price.getText().toString().trim();
                        String selectBeds = beds.getSelectedItem().toString();
                        String selectBaths = baths.getSelectedItem().toString();
                        String propertySize = size.getText().toString().trim();
                        String getCheckboxText = "";
                        for (int i = 0; i < checkboxGroup.getChildCount(); i++) {
                            CheckBox checkbox = (CheckBox) checkboxGroup.getChildAt(i);
                            if (checkbox.isChecked()) {
                                getCheckboxText += checkbox.getText().toString() + ", ";
                            }
                        }
                        //Log.d(TAG, String.valueOf(getCheckboxText));

                        String selectLocation = location.getSelectedItem().toString();
                        String address = addr.getText().toString().trim();
                        String description = desc.getText().toString().trim();

                        Log.d(TAG, "====" + String.valueOf(postImageName.size()));

                        if (postImageName.size() > 0 && !address.isEmpty()) {
                            insertPostAdServer(ownerName, ownerEmail, ownerMobile, isMobileHide, selectProperty, selectRenter, priceRate, selectBeds, selectBaths, propertySize, getCheckboxText, selectLocation, address, lat, lng, description, Arrays.toString(postImageName.toArray()), "http://to-let.nhp-bd.com/PostedImage/", "");
                        } else {
                            alertDialog(getResources().getString(R.string.msg_photo_add));
                        }
                        //------------------------------------------------------
                    }
                });

                //====================================================| Add Image
                this.addPostImageBtn = (Button) findViewById(R.id.add_post_image_btn);
                addPostImageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                });

            } else {
                alertDialog(getResources().getString(R.string.msg_email_update));
            }
        } else {
            alertDialog(getResources().getString(R.string.msg_register_user));
        }

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

    //====================================================| Google Maps Dialog
    private void mapsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PostAdActivity.this);
        View view = LayoutInflater.from(PostAdActivity.this).inflate(R.layout.post_maps, (ViewGroup) PostAdActivity.this.findViewById(R.id.post_maps_id));
        builder.setView(view);
        builder.setCancelable(true);
        builder.create();

        final AlertDialog dialog = builder.show();

        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    getAddress(new LatLng(23.793676, 90.388561), googleMap);
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            lat = String.valueOf(latLng.latitude);
                            lng = String.valueOf(latLng.longitude);

                            //Get address from google maps using Geocoder
                            Geocoder geocoder = new Geocoder(PostAdActivity.this, Locale.getDefault());
                            List<Address> list;
                            try {
                                list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            } catch (IOException e) {
                                return;
                            }
                            Address address = list.get(0);
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title(String.valueOf(address.getAddressLine(0)))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house2));//position:(lat,lng).title(address).setIcon(drawable)

                            addr.setVisibility(View.VISIBLE);
                            addr.setText(address.getAddressLine(0));
                            dialog.dismiss();
                        }
                    });
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    private void getAddress(LatLng latLng, GoogleMap map) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> list;
        try {
            list = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
        } catch (IOException e) {
            return;
        }
        Address address = list.get(0);
        //-------------------------------------------------------------------
        LatLng pos = new LatLng(latLng.latitude, latLng.longitude);
        //map.addMarker(new MarkerOptions().position(pos).title(String.valueOf(address.getAddressLine(0)))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house2));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(pos).zoom(12).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.getUiSettings().setZoomControlsEnabled(true);
        //Permission to my location
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            Log.d(TAG, "You have to accept to enjoy all app's services!");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }
        }
    }


    //====================================================| For Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectImage = data.getData();

            if (imageCounter>=0 && imageCounter<5){
                //Dynamically ImageView set in TableLayout
                ImageView img = new ImageView(this);
                img.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                img.setBackground(getResources().getDrawable(R.drawable.image_border));
                //img.setId(R.id.image+imageCounter);
                imgGroup.addView(img);
                img.setImageURI(selectImage);
                try {
                    postImageName.add("img_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".png");
                } catch (ArrayIndexOutOfBoundsException e) {
                    alertDialog(getResources().getString(R.string.msg_range));
                }
            } else {
                addPostImageBtn.setVisibility(View.GONE);
            }
            imageCounter ++;

        }
    }

    //===============================================| MySQL Server
    public void insertPostAdServer(String ownerName, String ownerEmail, String ownerMobile, String isMobileHide, String selectProperty, String selectRenter, String priceRate, String selectBeds, String selectBaths, String propertySize, String getCheckboxText, String selectLocation, String address, String lat, String lng, String description, String imgName, String absolutePath, String isEnable){
        String type = "insert_ad";
        String encode = encodingImage();
        PostAdBackgroundWorker worker = new PostAdBackgroundWorker(this);
        worker.execute(type, ownerName, ownerEmail, ownerMobile, isMobileHide, selectProperty, selectRenter, priceRate, selectBeds, selectBaths, propertySize, getCheckboxText, selectLocation, address, lat, lng, description, imgName, absolutePath, isEnable, encode);
    }
    //Encoding from passport image
    public String encodingImage(){
        ArrayList<String> arrayList = new ArrayList<>();

        //Save images from table-layout
        for(int i=0; i<imgGroup.getChildCount(); i++) {
            ImageView img = (ImageView) imgGroup.getChildAt(i);

            if (img.getVisibility() == View.VISIBLE) {
                Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                arrayList.add(Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT));
            }

            /*if (img.getDrawable() != null) {
                saveToInternalStorage(((BitmapDrawable)img.getDrawable()).getBitmap(),postImageName.get(i));
            }*/
        }

        return Arrays.toString(arrayList.toArray());
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





}
