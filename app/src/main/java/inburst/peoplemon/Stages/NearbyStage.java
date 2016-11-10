package inburst.peoplemon.Stages;

import android.app.Application;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class NearbyStage extends IndexedStage{
    public final SlideRigger rigger;

    public NearbyStage(Application context){
        super(NearbyStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public NearbyStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
