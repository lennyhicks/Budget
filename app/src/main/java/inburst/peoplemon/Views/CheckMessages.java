package inburst.peoplemon.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import inburst.budget.R;
import inburst.peoplemon.Adapters.MessageListAdapter;
import inburst.peoplemon.Models.Messages;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class CheckMessages extends LinearLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context context;
    private MessageListAdapter messagesAdapter;

    public CheckMessages(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        messagesAdapter = new MessageListAdapter(new ArrayList<Messages>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(messagesAdapter);

        RestClient restClient = new RestClient();
        restClient.getApiService().getConversations(0,10).enqueue(new Callback<Messages[]>() {
            @Override
            public void onResponse(Call<Messages[]> call, Response<Messages[]> response) {

                if (response.isSuccessful()) {
                    messagesAdapter.messages = new ArrayList<>(Arrays.asList(response.body()));
                    messagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Messages[]> call, Throwable t) {

            }
        });
    }
}