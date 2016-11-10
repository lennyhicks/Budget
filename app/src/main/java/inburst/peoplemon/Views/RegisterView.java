package inburst.peoplemon.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import inburst.budget.R;
import inburst.peoplemon.Components.Constants;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.MainActivity;
import inburst.peoplemon.Models.Account;
import inburst.peoplemon.Network.RestClient;
import inburst.peoplemon.PeopleMon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class RegisterView extends LinearLayout {
    private Context context;


    @Bind(R.id.nameField)
    EditText userNameField;

    @Bind(R.id.passwordField)
    EditText passwordField;

    @Bind(R.id.confirmPassword)
    EditText confirmPassword;

    @Bind(R.id.avatar)
    EditText avatar;

    @Bind(R.id.emailField)
    EditText emailField;

    @Bind(R.id.registerButton)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    @Bind(R.id.imageView)
    ImageView imageView;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        spinner.setVisibility(GONE);
    }

    @OnClick(R.id.registerButton)
    public void register() {
        //avatar.setHint("Select Avatar");
        InputMethodManager inm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromInputMethod(userNameField.getWindowToken(), 0);
        inm.hideSoftInputFromInputMethod(passwordField.getWindowToken(), 0);
        inm.hideSoftInputFromInputMethod(confirmPassword.getWindowToken(), 0);
        inm.hideSoftInputFromInputMethod(emailField.getWindowToken(), 0);

        final String username = userNameField.getText().toString();
        String password = passwordField.getText().toString();
        String conPassword = confirmPassword.getText().toString();
        String email = emailField.getText().toString();
        String avatarString = avatar.getText().toString();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || conPassword.isEmpty()) {
            Toast.makeText(context, "Please Complete all fields", Toast.LENGTH_LONG).show();
        } else if (!password.equals(conPassword)) {
            Toast.makeText(context, "Please Make sure both password fields are matching", Toast.LENGTH_LONG).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please Enter a valid email", Toast.LENGTH_LONG).show();
        } else {
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);
            imageView.buildDrawingCache();
            Bitmap bmap = imageView.getDrawingCache();
            avatarString = Utils.encodeTobase64(bmap);
            Log.i("STRING", Constants.IMAGE);

            Account account = new Account(email, username, Constants.IMAGE, password);
            RestClient restClient = new RestClient();
            restClient.getApiService().register(account).enqueue(new Callback<Void>() {


                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.isSuccessful()) {
                        Log.i("RESPONSE", response.message());
//                        User authUser = response.body();
//                        UserStore.getInstance().setToken(authUser.getToken());
//                        UserStore.getInstance().setTokenExpiration(authUser.getExpiration());

                        Flow flow = PeopleMon.getMainFlow();
                        resetView();
//                        userNameField.setText("");
//                        passwordField.setText("");
//                        confirmPassword.setText("");
//                        emailField.setText("");
//                        avatar.setText("");
                        flow.goBack();
                    } else {
                        Toast.makeText(context, "Failed Registering" + " : " + response.code(), Toast.LENGTH_LONG).show();
                        resetView();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Having Difficulties Registering", Toast.LENGTH_LONG).show();
                    resetView();

                }
            });
        }
    }


    @OnClick(R.id.avatar)
    public void pickAvatar() {

        ((MainActivity)context).getImage();

}

    private void resetView() {
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }
}
