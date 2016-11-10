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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getHasRegistered() {
        return hasRegistered;
    }

    public void setHasRegistered(Boolean hasRegistered) {
        this.hasRegistered = hasRegistered;
    }

    public String getLoginProvided() {
        return loginProvided;
    }

    public void setLoginProvided(String loginProvided) {
        this.loginProvided = loginProvided;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public Double getLastLng() {
        return lastLng;
    }

    public void setLastLng(Double lastLng) {
        this.lastLng = lastLng;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public String getLastCheckIn() {
        return lastCheckIn;
    }

    public void setLastCheckIn(String lastCheckIn) {
        this.lastCheckIn = lastCheckIn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
