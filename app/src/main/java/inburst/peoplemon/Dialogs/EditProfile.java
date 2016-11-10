package inburst.peoplemon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inburst.budget.R;
import inburst.peoplemon.Components.Constants;
import inburst.peoplemon.Events.ImageLoadedEvent;
import inburst.peoplemon.MainActivity;
import inburst.peoplemon.Models.Account;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Utils.decodeImage;

/**
 * Created by lennyhicks on 11/9/16.
 */

public class EditProfile extends Dialog {

    private Account account;
    private RestClient restClient;
    private Context context;

    @Bind(R.id.nameField)
    EditText nameField;

    @Bind(R.id.avatar)
    EditText avatar;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.editButton)
    Button editButton;

    @Bind(R.id.cancel)
    Button cancel;

    public EditProfile(Context context, Account account) {
        super(context);
        this.context = context;
        this.account = account;

    }


    @OnClick(R.id.avatar)
    public void pickAvatar() {
        ((MainActivity)context).getImage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_profile);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setupView();
        restClient = new RestClient();
        EventBus.getDefault().register(this);


    }

    @OnClick(R.id.editButton)
    public void selectTapped() {
        Account updated = new Account(nameField.getText().toString(), Constants.IMAGE);
        restClient.getApiService().updateInfo(updated).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
                dismiss();

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(context, "Profile Update Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @OnClick(R.id.cancel)
    public void cancelTapped() {
        dismiss();
    }

    private void setupView(){

    nameField.setText(account.getFullName());
    try {
        imageView.setImageBitmap(decodeImage(Constants.IMAGE));
    }catch (Exception e){
        imageView.setImageResource(R.drawable.pokeball);

    }
}

    @Override
    public void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void imageLoadedEvent(ImageLoadedEvent event){
        setupView();
    }
}
