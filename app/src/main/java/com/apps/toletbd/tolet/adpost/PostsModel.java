package com.apps.toletbd.tolet.adpost;

public class PostsModel {

    private String postId;
    private String ownerName;
    private String ownerEmail;
    private String ownerMobile;
    private String ownerMobileHide;
    private String propertyType;
    private String renterType;
    private String rentPrice;
    private String bedrooms;
    private String bathrooms;
    private String squareFootage;
    private String amenities;
    private String location;
    private String address;
    private String latitude;
    private String longitude;
    private String description;
    private String imageName;
    private String imagesPath;
    private String isEnable;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public PostsModel(String postId, String ownerName, String ownerEmail, String ownerMobile, String ownerMobileHide, String propertyType, String renterType, String rentPrice, String bedrooms, String bathrooms, String squareFootage, String amenities, String location, String address, String latitude, String longitude, String description, String imageName, String imagesPath, String isEnable, String createdById, String updatedById, String createdAt) {
        this.postId = postId;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerMobile = ownerMobile;
        this.ownerMobileHide = ownerMobileHide;
        this.propertyType = propertyType;
        this.renterType = renterType;
        this.rentPrice = rentPrice;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFootage = squareFootage;
        this.amenities = amenities;
        this.location = location;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageName = imageName;
        this.imagesPath = imagesPath;
        this.isEnable = isEnable;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public PostsModel(String ownerName, String ownerEmail, String ownerMobile, String ownerMobileHide, String propertyType, String renterType, String rentPrice, String bedrooms, String bathrooms, String squareFootage, String amenities, String location, String address, String latitude, String longitude, String description, String imageName, String imagesPath, String isEnable, String createdById, String updatedById, String createdAt) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerMobile = ownerMobile;
        this.ownerMobileHide = ownerMobileHide;
        this.propertyType = propertyType;
        this.renterType = renterType;
        this.rentPrice = rentPrice;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFootage = squareFootage;
        this.amenities = amenities;
        this.location = location;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageName = imageName;
        this.imagesPath = imagesPath;
        this.isEnable = isEnable;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public PostsModel(String ownerName, String ownerEmail, String ownerMobile, String ownerMobileHide, String propertyType, String renterType, String rentPrice, String bedrooms, String bathrooms, String squareFootage, String amenities, String location, String address, String latitude, String longitude, String description, String imageName, String imagesPath, String isEnable) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerMobile = ownerMobile;
        this.ownerMobileHide = ownerMobileHide;
        this.propertyType = propertyType;
        this.renterType = renterType;
        this.rentPrice = rentPrice;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.squareFootage = squareFootage;
        this.amenities = amenities;
        this.location = location;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageName = imageName;
        this.imagesPath = imagesPath;
        this.isEnable = isEnable;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(String ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public String getOwnerMobileHide() {
        return ownerMobileHide;
    }

    public void setOwnerMobileHide(String ownerMobileHide) {
        this.ownerMobileHide = ownerMobileHide;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getRenterType() {
        return renterType;
    }

    public void setRenterType(String renterType) {
        this.renterType = renterType;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(String squareFootage) {
        this.squareFootage = squareFootage;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(String updatedById) {
        this.updatedById = updatedById;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
