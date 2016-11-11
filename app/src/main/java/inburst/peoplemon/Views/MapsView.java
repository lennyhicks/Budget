package inburst.peoplemon.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.MainActivity;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Stages.CaughtStage;
import inburst.peoplemon.Stages.MessagesStage;
import inburst.peoplemon.Stages.NearbyStage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Constants.RADIUS_IN_METERS;
import static inburst.peoplemon.Components.Utils.circleMaker;

public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleApiClient googleApiClient;
    public static GoogleMap mMap;
    public static Location mLastLocation;
    private LocationManager locationManager;
    private Context context;
    private Marker marker;
    private ArrayList<Marker> visibleMarkers = new ArrayList<>();
    private RestClient restClient;



    @Bind(R.id.mapView)
    MapView mapView;

    @Bind(R.id.checkIn)
    FloatingActionButton checkIn;

    @Bind(R.id.caughtUsers)
    FloatingActionButton caughtUsers;

    @Bind(R.id.mail_button)
    FloatingActionButton checkMail;



    public MapsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    protected void onFinishInflate() {


        ButterKnife.bind(this);
        super.onFinishInflate();
        mapView.getMapAsync(this);

        mapView.onCreate(((MainActivity) getContext()).savedInstanceState);
        mapView.onResume();
        restClient = new RestClient();



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        mMap.setOnMarkerClickListener(markerClick);


        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
        mMap.clear();


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Location location = new Location("");
        Toast.makeText(context, location.getLatitude() + "", Toast.LENGTH_SHORT).show();
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        return true;
    }


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(final Location location) {
            checkIn(location);
            circleMaker(location, ValueAnimator.INFINITE);
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 18));
            restClient.getApiService().findNearby(RADIUS_IN_METERS).enqueue(new Callback<User[]>() {
                @Override
                public void onResponse(Call<User[]> call, Response<User[]> response) {
                    User[] userList = response.body();
                    if (userList != null) {
                        mMap.clear();
                        Log.i("FOUND ", userList.length +" People");
                        for (User user : userList) {
                            Location locat = new Location("");
                            locat.setLatitude(user.getLatitude());
                            locat.setLongitude(user.getLongitude());
                            user.setRadiusInMeters(location.distanceTo(locat));
                          if (location.distanceTo(locat) <= RADIUS_IN_METERS) {
                                Bitmap image = Utils.decodeImage(user.getAvatarBase64());
                                if (image != null) {
                                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(image)).position(new LatLng(user.getLatitude(), user.getLongitude())).title(user.getUserId()).snippet(user.getUserName()));
                                } else {
                                    Bitmap bit = Utils.getRandomPokemon(context);
                                    mMap.addMarker(new MarkerOptions().position(new LatLng(user.getLatitude(), user.getLongitude())).title(user.getUserId()).snippet(user.getUserName()).icon(BitmapDescriptorFactory.fromBitmap(bit)));

                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<User[]> call, Throwable t) {

                }
            });
        }
    };

    @OnClick(R.id.checkIn)
    public void nearbyList(){
        Flow flow = PeopleMon.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new NearbyStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

    @OnClick(R.id.mail_button)
    public void getMail(){
        Flow flow = PeopleMon.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new MessagesStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);

    }

    @OnClick(R.id.caughtUsers)
    public void caughtTapped() {
        Flow flow = PeopleMon.getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new CaughtStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    GoogleMap.OnMarkerClickListener markerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(final Marker marker) {

            final String userId = marker.getTitle();
            final Location userLoc = new Location("");
            userLoc.setLatitude(marker.getPosition().latitude);
            userLoc.setLongitude(marker.getPosition().longitude);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            User user = new User(userId, mLastLocation.distanceTo(userLoc));
                            restClient.getApiService().catchUser(user).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Successfully Caught " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            Location location = new Location("");
            location.setLatitude(marker.getPosition().latitude);
            location.setLongitude(marker.getPosition().longitude);
            circleMaker(location, 2);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Catch " + marker.getSnippet()).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

            return true;
        }
    };



    public void checkIn(Location location){
        mLastLocation = location;
        User user = new User(location.getLatitude(), location.getLongitude());
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));

        restClient.getApiService().checkIn(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
              //  Toast.makeText(context, "Checked in", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }



}