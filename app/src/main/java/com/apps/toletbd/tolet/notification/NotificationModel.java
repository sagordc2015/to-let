package com.apps.toletbd.tolet.notification;

public class NotificationModel {
    private String notId;
    private String userId;
    private String userName;
    private String userMaritalStatus;
    private String userMobile;
    private String userAddress;
    private String userOccupation;
    private String userImage;
    private String userImagePath;
    private String postId;
    private String postOwnerName;
    private String postOwnerMobile;
    private String postPropertyType;
    private String postImageName;
    private String postImagePath;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public NotificationModel(String notId, String userId, String userName, String userMaritalStatus, String userMobile, String userAddress, String userOccupation, String userImage, String userImagePath, String postId, String postOwnerName, String postOwnerMobile, String postPropertyType, String postImageName, String postImagePath, String createdById, String updatedById, String createdAt) {
        this.notId = notId;
        this.userId = userId;
        this.userName = userName;
        this.userMaritalStatus = userMaritalStatus;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.postId = postId;
        this.postOwnerName = postOwnerName;
        this.postOwnerMobile = postOwnerMobile;
        this.postPropertyType = postPropertyType;
        this.postImageName = postImageName;
        this.postImagePath = postImagePath;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public NotificationModel(String userId, String userName, String userMaritalStatus, String userMobile, String userAddress, String userOccupation, String userImage, String userImagePath, String postId, String postOwnerName, String postOwnerMobile, String postPropertyType, String postImageName, String postImagePath, String createdById, String updatedById, String createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.userMaritalStatus = userMaritalStatus;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.postId = postId;
        this.postOwnerName = postOwnerName;
        this.postOwnerMobile = postOwnerMobile;
        this.postPropertyType = postPropertyType;
        this.postImageName = postImageName;
        this.postImagePath = postImagePath;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public NotificationModel(String userId, String userName, String userMaritalStatus, String userMobile, String userAddress, String userOccupation, String userImage, String userImagePath, String postId, String postOwnerName, String postOwnerMobile, String postPropertyType, String postImageName, String postImagePath) {
        this.userId = userId;
        this.userName = userName;
        this.userMaritalStatus = userMaritalStatus;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
        this.userOccupation = userOccupation;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.postId = postId;
        this.postOwnerName = postOwnerName;
        this.postOwnerMobile = postOwnerMobile;
        this.postPropertyType = postPropertyType;
        this.postImageName = postImageName;
        this.postImagePath = postImagePath;
    }

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMaritalStatus() {
        return userMaritalStatus;
    }

    public void setUserMaritalStatus(String userMaritalStatus) {
        this.userMaritalStatus = userMaritalStatus;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }

    public String getPostOwnerMobile() {
        return postOwnerMobile;
    }

    public void setPostOwnerMobile(String postOwnerMobile) {
        this.postOwnerMobile = postOwnerMobile;
    }

    public String getPostPropertyType() {
        return postPropertyType;
    }

    public void setPostPropertyType(String postPropertyType) {
        this.postPropertyType = postPropertyType;
    }

    public String getPostImageName() {
        return postImageName;
    }

    public void setPostImageName(String postImageName) {
        this.postImageName = postImageName;
    }

    public String getPostImagePath() {
        return postImagePath;
    }

    public void setPostImagePath(String postImagePath) {
        this.postImagePath = postImagePath;
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
