package inburst.peoplemon.Models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import static inburst.peoplemon.Components.Constants.HEADER_VALUE;

/**
 * Created by lennyhicks on 11/7/16.
 */

public class Auth {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private Integer expiresIn;
    @SerializedName("userName")
    private String userName;
    @SerializedName(".issued")
    private String issued;
    @SerializedName(".expires")
    private String expires;

    public Auth(String accessToken, String tokenType, Integer expiresIn, String userName, String issued, String expires) {
        this.accessToken = accessToken;
        HEADER_VALUE = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.userName = userName;
        this.issued = issued;
        this.expires = expires;
        Log.i("HEADER_VALUE", HEADER_VALUE);
    }

    public Auth() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
