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
    private Float longitude;
    @SerializedName("Latitude")
    private Float latitude;

    public User(String userId, String userName, String avatarBase64, Float longitude, Float latitude, String created) {
        this.userId = userId;
        this.userName = userName;
        this.created = created;
        this.avatarBase64 = avatarBase64;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public User() {
    }
}
