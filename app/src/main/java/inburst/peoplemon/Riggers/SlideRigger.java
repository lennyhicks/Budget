package inburst.peoplemon.Riggers;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;

import inburst.budget.R;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class SlideRigger extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn = R.anim.slide_in_right;
        params.backIn = R.anim.slide_in_left;
        params.backOut = R.anim.slide_out_right;
        params.forwardOut = R.anim.slide_out_left;

    }

    public SlideRigger(Application conext){
        super(conext, params);
    }
}
