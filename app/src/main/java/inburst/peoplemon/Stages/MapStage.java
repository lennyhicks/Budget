package inburst.peoplemon.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/7/16.
 */

public class MapStage  extends IndexedStage{
    public final SlideRigger rigger;

    public MapStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MapStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.maps;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }
}


