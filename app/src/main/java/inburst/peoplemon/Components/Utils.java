package inburst.peoplemon.Components;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Base64;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import inburst.budget.R;

import static inburst.peoplemon.Components.Constants.RADIUS_IN_METERS;
import static inburst.peoplemon.Views.MapsView.mMap;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class Utils {

    public static Bitmap decodeImage(String encodedString) {
        byte[] decodedString;
        Bitmap decodedByte;
        if (encodedString != null) {
            if (encodedString.contains(",")) {
                String[] haxor = encodedString.split(",");
                decodedString = Base64.decode(haxor[1], Base64.DEFAULT);
            } else {
                if (encodedString.length() > 2000) {
                    decodedString = Base64.decode(encodedString, Base64.DEFAULT);
                } else {
                    decodedString = null;
                }
            }
            if (decodedString != null) {
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                decodedByte = resize(decodedByte);
                return decodedByte;
            }
        }
        return null;
    }
    public static String encodeTobase64(Bitmap bitmap) {
        bitmap = resize(bitmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        Constants.IMAGE = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap resize(Bitmap image) {
        try {
            image = Bitmap.createScaledBitmap(image, 120, 120, false);
            return image;
        } catch (NullPointerException e){
            return null;
        }
    }

    public static Bitmap getRandomPokemon(Context context){
        final TypedArray imgs = context.getResources().obtainTypedArray(R.array.randPoke);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        final int resID = imgs.getResourceId(rndInt, 0);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(resID);
        Drawable myDrawable = context.getResources().getDrawable(resID);
        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
        return myLogo;
    }

    public static void circleMaker(Location location, Integer repeat) {
        final Circle circle = mMap.addCircle(new CircleOptions().center(new LatLng(location.getLatitude(), location.getLongitude()))
                .fillColor(Color.argb(50, 0, 0, 255)).radius(RADIUS_IN_METERS));

        ValueAnimator vAnimator = new ValueAnimator();
        vAnimator.setRepeatCount(repeat);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
        vAnimator.setIntValues(0, 100);
        vAnimator.setDuration(2500);
        vAnimator.setEvaluator(new IntEvaluator());
        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                //Log.e("", "" + animatedFraction);
                circle.setRadius(animatedFraction * RADIUS_IN_METERS);
            }
        });
        vAnimator.start();
    }
}
