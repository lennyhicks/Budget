package inburst.peoplemon.Components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import inburst.budget.R;

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
                if (encodedString.length() > 200) {
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
        image = Bitmap.createScaledBitmap(image, 120, 120, false);
        return image;
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
}
