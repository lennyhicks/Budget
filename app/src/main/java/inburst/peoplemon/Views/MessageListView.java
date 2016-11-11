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

    private Integer converId;
    private Context context;
    private MessageListViewAdapter messages;
    private ArrayList<MessageView> messageViews;
    private Messages mess;

    public MessageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

    }

    public void getUsers(Integer converId){

        this.converId = converId;

        RestClient restClient = new RestClient();

        restClient.getApiService().getMessages(converId, 1000, 0).enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                mess = response.body();

                messageViews = mess.getMessages();

                messages = new MessageListViewAdapter(messageViews, context);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(messages);
                recyclerView.scrollToPosition(messageViews.size() - 1);

            }

            @Override
            public void onFailure(Call<Messages> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.sendButton)
    public void sendMessage(){
        String message = messageField.getText().toString();
        String user;
        if(!UserStore.getInstance().getAccount().equals(mess.getSenderId())) {
            user = messageViews.get(0).getRecipentUserId();
        }
        else {
            user = messageViews.get(0).getSenderUserId();
        }

        Messages newMessage = new Messages(user, message);
        RestClient restClient = new RestClient();
        restClient.getApiService().sendMessage(newMessage).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getUsers(converId);
                Toast.makeText(context, "Message Sent to " + mess.getRecipientName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}