package inburst.peoplemon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inburst.budget.R;
import inburst.peoplemon.Models.Messages;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Utils.decodeImage;

/**
 * Created by lennyhicks on 11/9/16.
 */

public class SendMessage extends Dialog {

    private User user;
    private RestClient restClient;
    private Context context;

    @Bind(R.id.nameField)
    TextView nameField;

    @Bind(R.id.messageField)
    EditText messageField;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.sendButton)
    Button editButton;

    @Bind(R.id.cancel)
    Button cancel;

    public SendMessage(Context context, User user) {
        super(context);
        this.context = context;
        this.user = user;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_message);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        setupView();
        restClient = new RestClient();



    }

    @OnClick(R.id.sendButton)
    public void sendTapped() {
        Messages message = new Messages(user.getUserId(), messageField.getText().toString());
        restClient.getApiService().sendMessage(message).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                dismiss();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Message Send Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @OnClick(R.id.cancel)
    public void cancelTapped() {
        dismiss();
    }

    private void setupView(){

    nameField.setText(user.getUserName());
    try {
        if(user.getAvatarBase64().length() > 200) {
            imageView.setImageBitmap(decodeImage(user.getAvatarBase64()));
        } else {
            imageView.setImageResource(R.drawable.pokeball);
        }
    }catch (Exception e){
        imageView.setImageResource(R.drawable.pokeball);

    }
    }
}
