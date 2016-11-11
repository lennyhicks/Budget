package inburst.peoplemon.Network;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import inburst.peoplemon.Components.Constants;
import inburst.peoplemon.Models.Account;
import inburst.peoplemon.PeopleMon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Constants.currentAccount;
import static inburst.peoplemon.Components.Constants.token;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class UserStore {
    private static UserStore ourInstance = new UserStore();

    public static UserStore getInstance(){
        return ourInstance;
    }

    private static SharedPreferences sharedPrefs = PeopleMon.getInstance().getSharedPreferences("PeoplemonGoPrefs", Context.MODE_PRIVATE);

    public String getToken(){
        String theToken = sharedPrefs.getString(token, null);
        return theToken;
    }

    public static void setToken(String token){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(token, token);
        editor.apply();

    }

    public Date getTokenExpiration(){
        Long expiration = sharedPrefs.getLong(Constants.tokenExpiration, 0);
        Date date = new Date(expiration);
        if (date.before(new Date())){
            return null;
        }
        return date;
    }


    public void setTokenExpiration(Date expiration){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(Constants.tokenExpiration, expiration.getTime());
        editor.apply();
    }

    public void setAccount(){
        RestClient restClient = new RestClient();
        restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                Account account = response.body();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(Constants.currentAccount, account.getId());
                editor.apply();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    public String getAccount(){
        String currAccount = sharedPrefs.getString(currentAccount, null);
        return currAccount;
    }
}
