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
import inburst.peoplemon.Adapters.CaughtAdapter;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class CaughtView extends LinearLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context context;
    private CaughtAdapter caughtAdapter;

    public CaughtView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        caughtAdapter = new CaughtAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(caughtAdapter);

        RestClient restClient = new RestClient();
        restClient.getApiService().caughtUsers().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {

                if (response.isSuccessful()) {
                    caughtAdapter.user = new ArrayList<>(Arrays.asList(response.body()));
                    caughtAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {

            }
        });
    }
}