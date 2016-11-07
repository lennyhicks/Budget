package inburst.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Models.Auth;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Stages.RegisterStage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Constants.GRANT_TYPE;
import static inburst.peoplemon.Components.Constants.HEADER_VALUE;
import static inburst.peoplemon.Components.Constants.radiusInMeters;
import static inburst.peoplemon.PeopleMon.API_BASE_URL;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class LoginView extends LinearLayout {
    private Context context;
    private Response auth;

    @Bind(R.id.emailField)
    EditText emailField;


    @Bind(R.id.passwordField)
    EditText passwordField;

    @Bind(R.id.registerButton)
    Button registerButton;

    @Bind(R.id.loginButton)
    Button loginButton;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void showRegisterView(){
        Flow flow = PeopleMon.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.loginButton)
    public void showMapView(){


        InputMethodManager inm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        inm.hideSoftInputFromInputMethod(passwordField.getWindowToken(), 0);

        inm.hideSoftInputFromInputMethod(emailField.getWindowToken(), 0);


        String password = passwordField.getText().toString();
        String email = emailField.getText().toString();
        String grantType = GRANT_TYPE;


        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Please Complete all fields", Toast.LENGTH_LONG).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please Enter a valid email", Toast.LENGTH_LONG).show();
        } else {
           // registerButton.setEnabled(false);
            //spinner.setVisibility(VISIBLE);

            final RestClient restClient = new RestClient();
            restClient.getApiService().getToken(grantType, email, password).enqueue(new Callback<Auth>() {


                @Override
                public void onResponse(Call<Auth> call, final Response<Auth> response) {
                    if (response.isSuccessful()){
                        Log.i("RESPONSE", response.message());
                        Auth auth = response.body();
                        HEADER_VALUE = "Bearer "+auth.getAccessToken();
                        Log.i("AUTHTOKEN ",  HEADER_VALUE + " Test");
                        getToken();
//                        User authUser = response.body();
//                        UserStore.getInstance().setToken(authUser.getToken());
//                        UserStore.getInstance().setTokenExpiration(authUser.getExpiration());

  //                      Flow flow = PeopleMon.getMainFlow();
                      //  resetView();
//                        userNameField.setText("");
//                        passwordField.setText("");
//                        confirmPassword.setText("");
//                        emailField.setText("");
//                        avatar.setText("");
   //                     flow.goBack();
                    } else {
                        Toast.makeText(context, "Failed Registering" + " : " + response.code(), Toast.LENGTH_LONG).show();
                        //resetView();


                    }
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    Log.e("MyApp", "Caught error", t);
                    Toast.makeText(context, "Having Difficulties Registering", Toast.LENGTH_LONG).show();
                    //resetView();
                }
            });
        }
    }

    public void getToken(){
        API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";
        final RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(radiusInMeters).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("RESPONSE", response.message());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Getting Users?" , Toast.LENGTH_LONG).show();

            }
        });
    }
}