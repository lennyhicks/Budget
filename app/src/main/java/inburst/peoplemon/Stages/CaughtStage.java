package inburst.peoplemon.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class CaughtStage  extends IndexedStage{
    public final SlideRigger rigger;

    public CaughtStage(Application context){
        super(CaughtStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public CaughtStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.caught_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}
