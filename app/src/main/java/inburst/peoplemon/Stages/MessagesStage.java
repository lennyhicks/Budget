package inburst.peoplemon.Stages;

import android.app.Application;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessagesStage extends IndexedStage{
    public final SlideRigger rigger;

    public MessagesStage(Application context){
        super(MessagesStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MessagesStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.check_messages;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
