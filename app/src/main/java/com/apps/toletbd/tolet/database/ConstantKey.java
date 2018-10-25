package com.apps.toletbd.tolet.database;

public class ConstantKey {

    public final static String COLUMN_ID = "id";
    public final static String FILTER_KEY = "id";
    public final static String USER_LOGIN_KEY = "loginInfo";
    public final static String USER_IS_LOGGED_KEY = "isLoggedIn";
    public final static String USER_NAME_KEY = "userName";
    public final static String USER_MOBILE_KEY = "userMobile";
    public final static String USER_EMAIL_KEY = "userEmail";
    public final static String USER_ID_KEY = "userId";

    //=======================| PostsModel |=======================
    public final static String AD_POSTS_TABLE_NAME = "ad_posts";
    public final static String AD_POSTS_COLUMN1 = "owner_name";
    public final static String AD_POSTS_COLUMN2 = "owner_email";
    public final static String AD_POSTS_COLUMN3 = "owner_mobile";
    public final static String AD_POSTS_COLUMN4 = "owner_mobile_hide";
    public final static String AD_POSTS_COLUMN5 = "property_type";
    public final static String AD_POSTS_COLUMN6 = "renter_type";
    public final static String AD_POSTS_COLUMN7 = "rent_price";
    public final static String AD_POSTS_COLUMN8 = "bedrooms";
    public final static String AD_POSTS_COLUMN9 = "bathrooms";
    public final static String AD_POSTS_COLUMN10 = "square_footage";
    public final static String AD_POSTS_COLUMN11 = "amenities";
    public final static String AD_POSTS_COLUMN12 = "location";
    public final static String AD_POSTS_COLUMN13 = "address";
    public final static String AD_POSTS_COLUMN14 = "latitude";
    public final static String AD_POSTS_COLUMN15 = "longitude";
    public final static String AD_POSTS_COLUMN16 = "description";
    public final static String AD_POSTS_COLUMN17 = "image_name";
    public final static String AD_POSTS_COLUMN18 = "images_path";
    public final static String AD_POSTS_COLUMN19 = "created_by_id";
    public final static String AD_POSTS_COLUMN20 = "updated_by_id";
    public final static String AD_POSTS_COLUMN21 = "created_at";

    protected final static String CREATE_AD_POSTS_TABLE = "CREATE TABLE " + AD_POSTS_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AD_POSTS_COLUMN1 + " TEXT, " + AD_POSTS_COLUMN2 + " TEXT, " + AD_POSTS_COLUMN3 + " TEXT, " + AD_POSTS_COLUMN4 + " TEXT, " + AD_POSTS_COLUMN5 + " TEXT, " + AD_POSTS_COLUMN6 + " TEXT, " + AD_POSTS_COLUMN7 + " TEXT, " + AD_POSTS_COLUMN8 + " TEXT, " + AD_POSTS_COLUMN9 + " TEXT, " + AD_POSTS_COLUMN10 + " TEXT, " + AD_POSTS_COLUMN11 + " TEXT, " + AD_POSTS_COLUMN12 + " TEXT, " + AD_POSTS_COLUMN13 + " TEXT, " + AD_POSTS_COLUMN14 + " TEXT, " + AD_POSTS_COLUMN15 + " TEXT, " + AD_POSTS_COLUMN16 + " TEXT, " + AD_POSTS_COLUMN17 + " TEXT, " + AD_POSTS_COLUMN18 + " TEXT, " + AD_POSTS_COLUMN19 + " TEXT, " + AD_POSTS_COLUMN20 + " TEXT, " + AD_POSTS_COLUMN21 + " TEXT ) ";
    protected final static String DROP_AD_POSTS_TABLE = "DROP TABLE IF EXISTS " + AD_POSTS_TABLE_NAME + " ";
    public final static String SELECT_AD_POSTS_TABLE = "SELECT * FROM " + AD_POSTS_TABLE_NAME;

    public final static String INSERT_AD_POSTS_DATA1 = "INSERT INTO ad_posts (owner_name, owner_email, owner_mobile, owner_mobile_hide, property_type, renter_type, rent_price, bedrooms, bathrooms, square_footage, amenities, location, address, latitude, longitude, description, image_name, images_path, created_by_id, updated_by_id, created_at) VALUES ('Abdul Haque', 'haque@gmail.com', '+8801914141707', 'true', 'Apartments', 'Family', '15,000', '4', '3', '2400', '{\"amenitiesArrays\":[\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\",\"WASA Connection\"]}', 'Dhaka Cantonment', 'Kachukhet, Dhaka Cantonment', '23', '90', 'Nothing to say..', '', '', 'created by kamal', '', '2018-09-18 00:05:30.729');";

    //=======================| FeedbackModel |=======================
    public final static String FB_TABLE_NAME = "feedback";
    public final static String FB_COLUMN1 = "fb_message";
    public final static String FB_COLUMN2 = "posted_id";
    public final static String FB_COLUMN3 = "user_id";
    public final static String FB_COLUMN4 = "user_name";
    public final static String FB_COLUMN5 = "user_image";
    public final static String FB_COLUMN6 = "user_image_path";
    public final static String FB_COLUMN7 = "created_by_id";
    public final static String FB_COLUMN8 = "updated_by_id";
    public final static String FB_COLUMN9 = "created_at";

    protected final static String CREATE_FB_TABLE = "CREATE TABLE " + FB_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FB_COLUMN1 + " TEXT, " + FB_COLUMN2 + " TEXT, " + FB_COLUMN3 + " TEXT, " + FB_COLUMN4 + " TEXT, " + FB_COLUMN5 + " TEXT, " + FB_COLUMN6 + " TEXT, " + FB_COLUMN7 + " TEXT, " + FB_COLUMN8 + " TEXT, " + FB_COLUMN9 + " TEXT ) ";
    protected final static String DROP_FB_TABLE = "DROP TABLE IF EXISTS " + FB_TABLE_NAME + " ";
    public final static String SELECT_FB_TABLE = "SELECT * FROM " + FB_TABLE_NAME;

    //=======================| NotificationModel |=======================
    public final static String NOT_TABLE_NAME = "notification";
    public final static String NOT_COLUMN1 = "user_id";
    public final static String NOT_COLUMN2 = "user_name";
    public final static String NOT_COLUMN3 = "user_marital_status";
    public final static String NOT_COLUMN4 = "user_mobile";
    public final static String NOT_COLUMN5 = "user_address";
    public final static String NOT_COLUMN6 = "user_occupation";
    public final static String NOT_COLUMN7 = "user_image";
    public final static String NOT_COLUMN8 = "user_image_path";
    public final static String NOT_COLUMN9 = "post_id";
    public final static String NOT_COLUMN10 = "post_owner_name";
    public final static String NOT_COLUMN11 = "post_owner_mobile";
    public final static String NOT_COLUMN12 = "post_property_type";
    public final static String NOT_COLUMN13 = "post_image_name";
    public final static String NOT_COLUMN14 = "post_image_path";
    public final static String NOT_COLUMN15 = "created_by_id";
    public final static String NOT_COLUMN16 = "updated_by_id";
    public final static String NOT_COLUMN17 = "created_at";

    protected final static String CREATE_NOT_TABLE = "CREATE TABLE " + NOT_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOT_COLUMN1 + " TEXT, " + NOT_COLUMN2 + " TEXT, " + NOT_COLUMN3 + " TEXT, " + NOT_COLUMN4 + " TEXT, " + NOT_COLUMN5 + " TEXT, " + NOT_COLUMN6 + " TEXT, " + NOT_COLUMN7 + " TEXT, " + NOT_COLUMN8 + " TEXT, " + NOT_COLUMN9 + " TEXT, " + NOT_COLUMN10 + " TEXT, " + NOT_COLUMN11 + " TEXT, " + NOT_COLUMN12 + " TEXT, " + NOT_COLUMN13 + " TEXT, " + NOT_COLUMN14 + " TEXT, " + NOT_COLUMN15 + " TEXT, " + NOT_COLUMN16 + " TEXT, " + NOT_COLUMN17 + " TEXT ) ";
    protected final static String DROP_NOT_TABLE = "DROP TABLE IF EXISTS " + NOT_TABLE_NAME + " ";
    public final static String SELECT_NOT_TABLE = "SELECT * FROM " + NOT_TABLE_NAME;

    //=======================| FavoriteModel |=======================
    public final static String FAV_TABLE_NAME = "favorite";
    public final static String FAV_COLUMN1 = "user_id";
    public final static String FAV_COLUMN2 = "user_mobile";
    public final static String FAV_COLUMN3 = "post_id";
    public final static String FAV_COLUMN4 = "post_owner_mobile";
    public final static String FAV_COLUMN5 = "created_by_id";
    public final static String FAV_COLUMN6 = "updated_by_id";
    public final static String FAV_COLUMN7 = "created_at";

    protected final static String CREATE_FAV_TABLE = "CREATE TABLE " + FAV_TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAV_COLUMN1 + " TEXT, " + FAV_COLUMN2 + " TEXT, " + FAV_COLUMN3 + " TEXT, " + FAV_COLUMN4 + " TEXT, " + FAV_COLUMN5 + " TEXT, " + FAV_COLUMN6 + " TEXT, " + FAV_COLUMN7 + " TEXT ) ";
    protected final static String DROP_FAV_TABLE = "DROP TABLE IF EXISTS " + FAV_TABLE_NAME + " ";
    public final static String SELECT_FAV_TABLE = "SELECT * FROM " + FAV_TABLE_NAME;

    //=======================| UserModel |=======================
    public final static String USER_TABLE_NAME = "users";
    public final static String USER_COLUMN1 = "user_name";
    public final static String USER_COLUMN2 = "user_relation";
    public final static String USER_COLUMN3 = "user_occupation";
    public final static String USER_COLUMN4 = "user_email";
    public final static String USER_COLUMN5 = "user_mobile";
    public final static String USER_COLUMN6 = "user_address";
    public final static String USER_COLUMN7 = "user_nid";
    public final static String USER_COLUMN8 = "user_image";
    public final static String USER_COLUMN9 = "user_path";
    public final static String USER_COLUMN10 = "is_user_owner";
    public final static String USER_COLUMN11 = "created_by_id";
    public final static String USER_COLUMN12 = "updated_by_id";
    public final static String USER_COLUMN13 = "created_at";

    protected final static String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE_NAME + " (" + COLUMN_ID + " TEXT, " + USER_COLUMN1 + " TEXT, " + USER_COLUMN2 + " TEXT, " + USER_COLUMN3 + " TEXT, " + USER_COLUMN4 + " TEXT, " + USER_COLUMN5 + " TEXT, " + USER_COLUMN6 + " TEXT, " + USER_COLUMN7 + " TEXT, " + USER_COLUMN8 + " TEXT, " +  USER_COLUMN9 + " TEXT, " +  USER_COLUMN10 + " TEXT, " +  USER_COLUMN11 + " TEXT, " + USER_COLUMN12 + " TEXT, " + USER_COLUMN13 + " TEXT ) ";
    protected final static String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + " ";
    public final static String SELECT_USER_TABLE = "SELECT * FROM " + USER_TABLE_NAME;

    public final static String INSERT_USER_DATA1 = "INSERT INTO users (name, email, mobile, address, image, path, is_owner, created_by_id, updated_by_id, created_at) VALUES ('admin', 'mustofa2008@gmail.com', '01914141707', 'Dhaka Cantonment', '', '', 'Owner', 'created by kamal', '', '2018-09-18 00:05:30.729');";



}
