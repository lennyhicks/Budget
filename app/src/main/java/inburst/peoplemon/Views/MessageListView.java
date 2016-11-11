package inburst.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import inburst.budget.R;
import inburst.peoplemon.Adapters.MessageListViewAdapter;
import inburst.peoplemon.Components.CurrentDataStore;
import inburst.peoplemon.Models.MessageView;
import inburst.peoplemon.Models.Messages;
import inburst.peoplemon.Network.RestClient;
import inburst.peoplemon.Network.UserStore;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessageListView extends LinearLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.messageField)
    EditText messageField;

    @Bind(R.id.sendButton)
    Button sendButton;

    private Context context;
    private MessageListViewAdapter messages;
    private ArrayList<MessageView> messageViews;

    public MessageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        messageViews = CurrentDataStore.messageView;
        messages = new MessageListViewAdapter(messageViews, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messages);

    }

    @OnClick(R.id.sendButton)
    public void sendMessage(){
        String user;
        String message = messageField.getText().toString();
        RestClient restClient = new RestClient();
        if(UserStore.getInstance().getAccount() == CurrentDataStore.currentMessage.getSenderId())
            user = CurrentDataStore.currentMessage.getRecipientId();
        else {
            user = CurrentDataStore.currentMessage.getSenderId();
        }
        Messages newMessage = new Messages(user, message);
        restClient.getApiService().sendMessage(newMessage).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}