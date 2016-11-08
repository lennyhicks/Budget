package inburst.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lennyhicks on 11/6/16.
 */

public class User {


    @SerializedName("UserId")
    private String userId;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Created")
    private String created;
    @SerializedName("AvatarBase64")
    private String avatarBase64;
    @SerializedName("CaughtUserId")
    private String caughtUserId;
    @SerializedName("RadiusInMeters")
    private Integer radiusInMeters;
    @SerializedName("Longitude")
    private Double longitude;
    @SerializedName("Latitude")
    private Double latitude;

    public User(String userId, String userName, String avatarBase64, Double longitude, Double latitude, String created) {
        this.userId = userId;
        this.userName = userName;
        this.created = created;
        this.avatarBase64 = avatarBase64;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public User() {
    }

    public User(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getCaughtUserId() {
        return caughtUserId;
    }

    public void setCaughtUserId(String caughtUserId) {
        this.caughtUserId = caughtUserId;
    }

    public Integer getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Integer radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
