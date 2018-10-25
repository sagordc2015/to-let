package com.apps.toletbd.tolet.favorite;

public class FavoriteModel {
    private String favId;
    private String userId;
    private String userMobile;
    private String postId;
    private String postOwnerMobile;
    private String createdById;
    private String updatedById;
    private String createdAt;

    public FavoriteModel(String favId, String userId, String userMobile, String postId, String postOwnerMobile, String createdById, String updatedById, String createdAt) {
        this.favId = favId;
        this.userId = userId;
        this.userMobile = userMobile;
        this.postId = postId;
        this.postOwnerMobile = postOwnerMobile;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
    }

    public FavoriteModel(String userId, String userMobile, String postId, String postOwnerMobile) {
        this.userId = userId;
        this.userMobile = userMobile;
        this.postId = postId;
        this.postOwnerMobile = postOwnerMobile;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostOwnerMobile() {
        return postOwnerMobile;
    }

    public void setPostOwnerMobile(String postOwnerMobile) {
        this.postOwnerMobile = postOwnerMobile;
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
