package com.apps.toletbd.tolet.feedback;

public class FeedbackModel {
    private String fbId;
    private String fbMessage;
    private String postedId;
    private String userId;
    private String userName;
    private String userImage;
    private String userImagePath;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public FeedbackModel(String fbId, String fbMessage, String postedId, String userId, String userName, String userImage, String userImagePath, String createdById, String updatedById, String createdAt) {
        this.fbId = fbId;
        this.fbMessage = fbMessage;
        this.postedId = postedId;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public FeedbackModel(String fbMessage, String postedId, String userId, String userName, String userImage, String userImagePath) {
        this.fbMessage = fbMessage;
        this.postedId = postedId;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.userImagePath = userImagePath;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbMessage() {
        return fbMessage;
    }

    public void setFbMessage(String fbMessage) {
        this.fbMessage = fbMessage;
    }

    public String getPostedId() {
        return postedId;
    }

    public void setPostedId(String postedId) {
        this.postedId = postedId;
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
