package inburst.peoplemon.Views;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import inburst.budget.R;
import inburst.peoplemon.Adapters.NearbyAdapter;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Constants.NEARBY_RADIUS_IN_METERS;
import static inburst.peoplemon.Views.MapsView.mLastLocation;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class NearbyView extends LinearLayout {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private Context context;
    private NearbyAdapter nearbyAdapter;

    public NearbyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        nearbyAdapter = new NearbyAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nearbyAdapter);

        RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(NEARBY_RADIUS_IN_METERS).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {

                if (response.isSuccessful()) {
                    nearbyAdapter.users = new ArrayList<>(Arrays.asList(response.body()));
                    for(User user : nearbyAdapter.users){
                        Location locat = new Location("");
                        locat.setLatitude(user.getLatitude());
                        locat.setLongitude(user.getLongitude());
                        user.setRadiusInMeters(mLastLocation.distanceTo(locat));
                    }
                    Collections.sort(nearbyAdapter.users);
                    nearbyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {

            }
        });
    }
}