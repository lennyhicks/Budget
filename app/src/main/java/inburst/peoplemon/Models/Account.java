package inburst.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import static inburst.peoplemon.Components.Constants.API_KEY;

/**
 * Created by lennyhicks on 11/6/16.
 */

public class Account {
    @SerializedName("Id")
    private String id;
    @SerializedName("Email")
    private String email;
    @SerializedName("HasRegistered")
    private Boolean hasRegistered;
    @SerializedName("LoginProvider")
    private String loginProvided;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("AvatarBase64")
    private String avatarBase64;
    @SerializedName("LastCheckInLongitude")
    private Double lastLng;
    @SerializedName("LastCheckInLatitude")
    private Double lastLat;
    @SerializedName("LastCheckInDateTime")
    private String lastCheckIn;
    @SerializedName("Password")
    private String password;
    @SerializedName("ApiKey")
    private String apiKey;
    @SerializedName("grant_type")
    private String grantType;


    public Account(String id, String email, Boolean hasRegistered, String loginProvided, String fullName, String avatarBase64, Double lastLng, Double lastLat, String lastCheckIn) {
        this.id = id;
        this.email = email;
        this.hasRegistered = hasRegistered;
        this.loginProvided = loginProvided;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.lastLng = lastLng;
        this.lastLat = lastLat;
        this.lastCheckIn = lastCheckIn;
    }

    public Account(Double lastLng, Double lastLat) {
        this.lastLng = lastLng;
        this.lastLat = lastLat;
    }

    public Account(String fullName, String avatarBase64) {
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
    }

    public Account(String email, String fullName, String avatarBase64, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = API_KEY;
        this.password = password;

    }

    public Account() {
    }
}
