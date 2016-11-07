package inburst.peoplemon.Stages;

import android.app.Application;

import inburst.budget.R;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class PeopleMonStage extends IndexedStage {

    public final SlideRigger rigger;

    public PeopleMonStage(Application context){
        super(PeopleMonStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public PeopleMonStage(){
        this(inburst.peoplemon.PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.budget_list_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
