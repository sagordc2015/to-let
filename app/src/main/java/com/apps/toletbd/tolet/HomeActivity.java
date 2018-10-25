package com.apps.toletbd.tolet;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.toletbd.tolet.Utility.DownloadImageTask;
import com.apps.toletbd.tolet.adpost.PostAdActivity;
import com.apps.toletbd.tolet.adpost.PostsListActivity;
import com.apps.toletbd.tolet.database.ConstantKey;
import com.apps.toletbd.tolet.favorite.FavoriteActivity;
import com.apps.toletbd.tolet.favorite.FavoriteModel;
import com.apps.toletbd.tolet.favorite.FavoriteService;
import com.apps.toletbd.tolet.language.LocaleHelper;
import com.apps.toletbd.tolet.notification.NotificationActivity;
import com.apps.toletbd.tolet.signup.SignUpActivity;
import com.apps.toletbd.tolet.settings.SettingsActivity;
import com.apps.toletbd.tolet.signup.SignUpSignInActivity;
import com.apps.toletbd.tolet.user.UserModel;
import com.apps.toletbd.tolet.user.UserService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "HomeActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //Navigation
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String userMobile;
    private String userEmail;

    private Button list, filter;
    private Spinner filterLocation;
    private RangeSeekBar filterPriceRange;

    private Button[] propertyBtn = new Button[6];
    private Button propertyBtn_unfocused;
    private int[] propertyBtn_id = {R.id.propertyBtn0, R.id.propertyBtn1, R.id.propertyBtn2, R.id.propertyBtn3, R.id.propertyBtn4, R.id.propertyBtn5};

    private Button[] renterBtn = new Button[6];
    private Button renterBtn_unfocused;
    private int[] renterBtn_id = {R.id.renterBtn0, R.id.renterBtn1, R.id.renterBtn2, R.id.renterBtn3, R.id.renterBtn4, R.id.renterBtn5};

    private Button[] bedBtn = new Button[5];
    private Button bedBtn_unfocused;
    private int[] bedBtn_id = {R.id.bedBtn0, R.id.bedBtn1, R.id.bedBtn2, R.id.bedBtn3, R.id.bedBtn4};

    private Button filterBtn;
    private String propertyType;
    private String renterType;
    private String bedRooms;

    private UserService userService;
    private UserModel userModel;

    private ProgressDialog progress;
    private Activity context;

    private ArrayList<Double> latitude;
    private ArrayList<Double> longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.USER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.USER_IS_LOGGED_KEY, false);
        userMobile = preferences.getString(ConstantKey.USER_MOBILE_KEY, "Data not found");

        this.userService = new UserService(this);
        this.userModel = this.userService.getDataByMobile(userMobile);

        //Toast.makeText(this, "SharedPreferences is "+isLoggedIn+" for "+userMobile, Toast.LENGTH_SHORT).show();

        //==========================================| Change action bar title
        ActionBar bar = getSupportActionBar();
        bar.setTitle(getResources().getString(R.string.home_title));

        context = this;

        //==========================================| findViewById
        list = (Button) findViewById(R.id.list_id);
        list.setOnClickListener(new ActionHandler());
        filter = (Button) findViewById(R.id.filter_id);
        filter.setOnClickListener(new ActionHandler());

        /*Button button = (Button) findViewById(R.id.google_maps);
        if (isServicesOk()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                }
            });
        }*/

        //====================================================| To Display Navigation Bar Icon and Back
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_id);
        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_id);
        if (userModel != null) {
            if (userModel.getUserName() != null && userModel.getUserImage() != null) {
                View hView =  navigationView.getHeaderView(0);
                CircleImageView navUserPhoto = (CircleImageView)hView.findViewById(R.id.nav_header_photo);
                new DownloadImageTask(this, navUserPhoto).execute(userModel.getUserImagePath()+userModel.getUserImage()); //load user photo from server
                //navUserPhoto.setImageDrawable(this.getDrawable(R.drawable.admin));
                TextView navUserName = (TextView)hView.findViewById(R.id.nav_header_user);
                navUserName.setText(userModel.getUserName());
            }
        }
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        //separator Item Decoration
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(HomeActivity.this,DividerItemDecoration.VERTICAL));

        //====================================================| Google Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.home_maps);
        new BackTask(this, mapFragment).execute("posts_select");


    }

    //===============================================| Language Change
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    //====================================================| Google Maps
    private void getAddress(LatLng latLng, GoogleMap map) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> list;
        try {
            list = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
        } catch (IOException e) {
            return;
        }
        Address address = list.get(0);
        map.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title(String.valueOf(address.getAddressLine(0)))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house2));
    }
    private double[] createRandLocation(double latitude, double longitude) {
        return new double[] {
                latitude + ((Math.random() - 0.5) / 500),longitude + ((Math.random() - 0.5) / 500),150 + ((Math.random() - 0.5) * 10)
        };
    }

    //====================================================| Back press disabled and OptionsMenu
    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //====================================================| Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.settings_id) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
        if (menuItem.getItemId() == R.id.profile_id) {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        }
        if (menuItem.getItemId() == R.id.notice_id) {
            if (!userMobile.equals("Data not found") && userModel != null && userModel.getIsUserOwner().equals("true")) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            } else {
                alertDialog(getResources().getString( R.string.msg_owner_user));
            }
        }
        if (menuItem.getItemId() == R.id.favorite_id) {
            Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(intent);
        }
        if (menuItem.getItemId() == R.id.post_ad_id) {
            if (!userMobile.equals("Data not found") && userModel != null && userModel.getIsUserOwner().equals("true")) {
                if (userModel.getUserEmail() != null) {
                    Intent intent = new Intent(getApplicationContext(), PostAdActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog(getResources().getString( R.string.msg_email_update));
                }
            } else {
                alertDialog(getResources().getString( R.string.msg_register_user));
            }
        }
        if (menuItem.getItemId() == R.id.about_id) {
            aboutMe();
        }
        /*if (menuItem.getItemId() == R.id.maps_id) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }*/
        if (menuItem.getItemId() == R.id.log_out) {
            //===============================================| Remove SharedPreferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); //Remove from login.xml file
            editor.commit();

            Intent intent = new Intent(HomeActivity.this, SignUpSignInActivity.class);
            startActivity(intent);
        }
        return false;
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

    //====================================================| About
    public void aboutMe() {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("About")
                .setMessage(HomeActivity.this.getString(R.string.apps_details))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_SHORT);
                    }
                }).show();
    }

    //====================================================| Check google maps service
    public boolean isServicesOk() {
        Log.d(TAG, "Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "Google play services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error is occurred but we resolve it
            Log.d(TAG, "An error is occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity) getApplicationContext(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        return false;
    }

    //====================================================| Button events
    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.list_id) {
                Intent intent = new Intent(getApplicationContext(), PostsListActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.filter_id) {
                filterDialog();
            }
        }
    }

    //====================================================| Filter Custom AlertDialog
    protected void filterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        //builder.setIcon(R.mipmap.ic_launcher);
        //builder.setTitle("Filters");
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.post_filter, (ViewGroup) HomeActivity.this.findViewById(R.id.post_filter_id));
        builder.setView(view); // Set above view in alert dialog.
        builder.setCancelable(true);
        builder.create();

        final AlertDialog dialog = builder.show(); // Because only AlertDialog has cancel method.

        this.filterLocation = (Spinner) view.findViewById(R.id.filter_location);
        this.filterPriceRange = (RangeSeekBar) view.findViewById(R.id.filter_price_range);
        final EditText min = view.findViewById(R.id.range_min);
        final EditText max = view.findViewById(R.id.range_max);
        filterPriceRange.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                min.setText(minValue.toString());
                max.setText(maxValue.toString());
            }
        });

        //====================================================| Property Button
        for(int i = 0; i < propertyBtn.length; i++){
            propertyBtn[i] = (Button) view.findViewById(propertyBtn_id[i]);
            propertyBtn[i].setBackground(getResources().getDrawable(R.drawable.button_border));
            propertyBtn[i].setOnClickListener(new ActionHandlerProperty());
        }
        propertyBtn_unfocused = propertyBtn[0];

        //====================================================| Renter Button
        for(int i = 0; i < renterBtn.length; i++){
            renterBtn[i] = (Button) view.findViewById(renterBtn_id[i]);
            renterBtn[i].setBackground(getResources().getDrawable(R.drawable.button_border));
            renterBtn[i].setOnClickListener(new ActionHandlerRenter());
        }
        renterBtn_unfocused = renterBtn[0];

        //====================================================| Bedrooms Button
        for(int i = 0; i < bedBtn.length; i++){
            bedBtn[i] = (Button) view.findViewById(bedBtn_id[i]);
            bedBtn[i].setBackground(getResources().getDrawable(R.drawable.button_border));
            bedBtn[i].setOnClickListener(new ActionHandlerBedrooms());
        }
        bedBtn_unfocused = bedBtn[0];

        //====================================================| Filter Button
        filterBtn = (Button) view.findViewById(R.id.filter_btn);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!filterLocation.getSelectedItem().toString().contains("Select Location")) {
                    if (bedRooms != null && bedRooms.equals("4+")) {
                        passFilterValues(filterLocation.getSelectedItem().toString(),min.getText().toString(),max.getText().toString(),propertyType,renterType,"Any");
                    } else {
                        passFilterValues(filterLocation.getSelectedItem().toString(),min.getText().toString(),max.getText().toString(),propertyType,renterType,bedRooms);
                    }
                } else {
                    alertDialog(getResources().getString( R.string.msg_select_location));
                }
            }
        });


        TextView cancel = (TextView) view.findViewById(R.id.filter_dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView reset = (TextView) view.findViewById(R.id.filter_dialog_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Reset", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //====================================================| Filter values pass into intent
    private void passFilterValues(String loc,String min,String max,String pro,String rent,String bed) {
        Intent intent = new Intent(getApplicationContext(), PostsListActivity.class);
        intent.putExtra(ConstantKey.FILTER_KEY, loc+","+min+","+max+","+pro+","+rent+","+bed);
        startActivity(intent);
    }


    //====================================================| Property Button Action
    private class ActionHandlerProperty implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.propertyBtn0 :
                    propertyType = propertyBtn[0].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[0]);
                    break;
                case R.id.propertyBtn1 :
                    propertyType = propertyBtn[1].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[1]);
                    break;
                case R.id.propertyBtn2 :
                    propertyType = propertyBtn[2].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[2]);
                    break;
                case R.id.propertyBtn3 :
                    propertyType = propertyBtn[3].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[3]);
                    break;
                case R.id.propertyBtn4 :
                    propertyType = propertyBtn[4].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[4]);
                    break;
                case R.id.propertyBtn5 :
                    propertyType = propertyBtn[5].getText().toString();
                    setFocusProperty(propertyBtn_unfocused, propertyBtn[5]);
                    break;
            }
        }
    }
    private void setFocusProperty(Button propertyBtn_unfocused, Button propertyBtn_focus){
        propertyBtn_unfocused.setTextColor(Color.rgb(49, 50, 51));
        propertyBtn_unfocused.setBackground(getResources().getDrawable(R.drawable.button_border));
        propertyBtn_focus.setTextColor(Color.rgb(255, 255, 255));
        propertyBtn_focus.setBackgroundColor(Color.parseColor("#444444"));
        this.propertyBtn_unfocused = propertyBtn_focus;
    }

    //====================================================| Renter Button Action
    private class ActionHandlerRenter implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.renterBtn0 :
                    renterType = renterBtn[0].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[0]);
                    break;
                case R.id.renterBtn1 :
                    renterType = renterBtn[1].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[1]);
                    break;
                case R.id.renterBtn2 :
                    renterType = renterBtn[2].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[2]);
                    break;
                case R.id.renterBtn3 :
                    renterType = renterBtn[3].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[3]);
                    break;
                case R.id.renterBtn4 :
                    renterType = renterBtn[4].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[4]);
                    break;
                case R.id.renterBtn5 :
                    renterType = renterBtn[5].getText().toString();
                    setFocusRenter(renterBtn_unfocused, renterBtn[5]);
                    break;
            }
        }
    }
    private void setFocusRenter(Button btn_unfocused, Button btn_focus){
        btn_unfocused.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocused.setBackground(getResources().getDrawable(R.drawable.button_border));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.parseColor("#444444"));
        this.renterBtn_unfocused = btn_focus;
    }

    //====================================================| Bedrooms Button Action
    private class ActionHandlerBedrooms implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bedBtn0 :
                    bedRooms = bedBtn[0].getText().toString();
                    setFocusBed(bedBtn_unfocused, bedBtn[0]);
                    break;
                case R.id.bedBtn1 :
                    bedRooms = bedBtn[1].getText().toString();
                    setFocusBed(bedBtn_unfocused, bedBtn[1]);
                    break;
                case R.id.bedBtn2 :
                    bedRooms = bedBtn[2].getText().toString();
                    setFocusBed(bedBtn_unfocused, bedBtn[2]);
                    break;
                case R.id.bedBtn3 :
                    bedRooms = bedBtn[3].getText().toString();
                    setFocusBed(bedBtn_unfocused, bedBtn[3]);
                    break;
                case R.id.bedBtn4 :
                    bedRooms = bedBtn[4].getText().toString();
                    setFocusBed(bedBtn_unfocused, bedBtn[4]);
                    break;
            }
        }
    }

    private void setFocusBed(Button bedBtn_unfocused, Button bedBtn_focus){
        bedBtn_unfocused.setTextColor(Color.rgb(49, 50, 51));
        bedBtn_unfocused.setBackground(getResources().getDrawable(R.drawable.button_border));
        bedBtn_focus.setTextColor(Color.rgb(255, 255, 255));
        bedBtn_focus.setBackgroundColor(Color.parseColor("#444444"));
        this.bedBtn_unfocused = bedBtn_focus;
    }


    //====================================================| AsyncTask from MySQL Server
    private class BackTask extends AsyncTask<String,Void,String> {

        private Context context;
        private SupportMapFragment mapFragment;

        public BackTask(Context context, SupportMapFragment mapFragment) {
            this.context = context;
            this.mapFragment = mapFragment;
        }

        /*@Override
        protected void onPreExecute(){
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setMessage("Please wait.");
            progress.setCancelable(true);
            progress.setIndeterminate(true);
            progress.show();
        }*/

        @Override
        protected String doInBackground(String...params){
            String type = params[0];
            if(type.equals("posts_select")) {
                String select_url = "http://to-let.nhp-bd.com/postad_select.php";
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
                    /*if(progress != null) {
                        progress.dismiss(); //close the dialog if error occurs
                    }*/
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            /*if(progress != null) {
                progress.dismiss(); //close dialog
            }*/

            latitude = new ArrayList<>();
            longitude = new ArrayList<>();

            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    //double latitude[] = {23.793676, 23.789623, 23.817115, 23.869521, 23.728889};
                    //double longitude[] = {90.388561, 90.402894, 90.364209, 90.403708, 90.381704};
                    double[] randomLocation = new double[0];

                    for (int i = 0; i < latitude.size(); i++) {
                        randomLocation = createRandLocation(latitude.get(i), longitude.get(i));
                        getAddress(new LatLng(latitude.get(i), longitude.get(i)), googleMap);
                    }

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(randomLocation[0], randomLocation[1])).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    //------------| Display zoom in and out icon
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    //------------| Display current location icon
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    googleMap.setMyLocationEnabled(true);
                    //------------| Display traffic route
                    //googleMap.setTrafficEnabled(true);
                    //------------| Display maps type
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            });

            try{
                // Remove unexpected characters that might be added to beginning of the string
                result = result.substring(result.indexOf("["));
                JSONArray jArray = new JSONArray(result);
                for(int i=0; i<jArray.length(); i++){
                    JSONObject js = jArray.getJSONObject(i);
                    latitude.add(Double.parseDouble(js.getString("latitude")));
                    longitude.add(Double.parseDouble(js.getString("longitude")));
                }
            } catch(Exception e) {
                latitude.add(Double.parseDouble("23.793676"));
                longitude.add(Double.parseDouble("90.388561"));
                e.printStackTrace();
            }
        }

    }







}
