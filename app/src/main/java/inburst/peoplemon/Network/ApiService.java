package inburst.peoplemon.Network;

import inburst.peoplemon.Models.Account;
import inburst.peoplemon.Models.Auth;
import inburst.peoplemon.Models.Messages;
import inburst.peoplemon.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lennyhicks on 10/31/16.
 */
public interface ApiService {


    //Account Calls
    @GET("/api/Account/UserInfo")
    Call<Account> getUserInfo();

    @POST("/api/Account/UserInfo") //Fullname, Avatar
    Call<Account> updateInfo(@Body Account account);

    @POST("/api/Account/Logout")
    Call<Account> logout();

    @POST("/api/Account/Register")//Email, FullName, Avatar, APIkey, Password;
    Call<Void> register(@Body Account account);


    //User Calls
    @GET("v1/User/Nearby")
    Call<User> findNearby(@Query("radiusInMeters") Integer radiusInMeters);

    @POST("/v1/User/CheckIn")// Lat, Long
    Call<User> checkIn(@Body User user); //Body Longitude Latitude, ints

    @POST("/v1/User/Catch")//CaughtUserId, RadiusInMeters
    Call<User> catchUser(@Body User user ); //Body CaughtUserId - string, CaughtUserId - int

    @GET("/v1/User/Caught")
    Call<User[]> caughtUsers();

    @GET("/v1/User/Conversations")
    Call<Messages[]> getConversations(@Query("pageSize") Integer pageSize, @Query("pageNumber") Integer pageNumber);

    @GET("/v1/User/Conversation")
    Call<Messages[]> getConversations(@Query("id") Integer id, @Query("pageSize") Integer pageSize, @Query("pageNumber") Integer pageNumber);

    @POST("/v1/User/Conversation")//RecipientId, Message
    Call<Messages> sendMessage(@Body Messages message);


    //Auth
    @FormUrlEncoded
    @POST("/token")
    Call<Auth> getToken(@Field(value = "grant_type", encoded = true) String grantType, @Field(value = "username", encoded = true) String username, @Field(value = "password", encoded = true) String password);

}
