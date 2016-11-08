package inburst.peoplemon.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Base64;
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
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import inburst.budget.R;
import inburst.peoplemon.MainActivity;
import inburst.peoplemon.Models.User;
import inburst.peoplemon.Network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static inburst.peoplemon.Components.Constants.RADIUS_IN_METERS;

public class MapsView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private Location mLastLocation;
    private LocationManager locationManager;
    private Context context;
    private Marker marker;
    private ArrayList<Marker> visibleMarkers = new ArrayList<>();
    private RestClient restClient;



    @Bind(R.id.mapView)
    MapView mapView;
//
//    @Bind(R.id.checkIn)
//    FloatingActionButton checkIn;


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
        LatLng sydney = new LatLng(-41, 23);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


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
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Location location = new Location("");
        Toast.makeText(context, location.getLatitude() + "", Toast.LENGTH_SHORT).show();
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        return true;
    }


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            mMap.clear();
            mLastLocation = location;
            restClient.getApiService().findNearby(RADIUS_IN_METERS).enqueue(new Callback<User[]>() {
                @Override
                public void onResponse(Call<User[]> call, Response<User[]> response) {
                    Log.i("USERS", Arrays.asList(response.body()).toString());
                    User[] userArray = response.body();
                    for (User user : userArray) {
                        String[] haxor = user.getAvatarBase64().split(",");
                        byte[] decodedString = Base64.decode(haxor[1], Base64.DEFAULT);

                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        decodedByte = Bitmap.createScaledBitmap(decodedByte, 120, 120, false);

                        mMap.addMarker(new MarkerOptions().position(new LatLng(user.getLatitude(), user.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(decodedByte)).title(user.getUserName()));
                    }
                }

                @Override
                public void onFailure(Call<User[]> call, Throwable t) {

                }
            });
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            marker = mMap.addMarker(new MarkerOptions().position(loc));
            visibleMarkers.add(0, marker);
            for (Marker marker : visibleMarkers) {
                //mMap.addMarker(new MarkerOptions(marker));
            }
            if (mMap != null) {
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };
//
//    @OnClick(R.id.checkIn)
//    public void checkIn(){
//        User user = new User(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//        restClient.getApiService().checkIn(user).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                Toast.makeText(context, "Checked in", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
//
//    }

    GoogleMap.OnMarkerClickListener markerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Toast.makeText(context, "Marker Clicked", Toast.LENGTH_SHORT).show();
            marker.showInfoWindow();
            return true;
        }
    };

}