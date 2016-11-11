package inburst.peoplemon.Stages;

import android.app.Application;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessagesListStage extends IndexedStage{
    public final SlideRigger rigger;

    public MessagesListStage(Application context){
        super(MessagesListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MessagesListStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_messages;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
