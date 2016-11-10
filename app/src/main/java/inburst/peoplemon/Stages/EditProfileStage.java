package inburst.peoplemon.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;

/**
 * Created by lennyhicks on 11/9/16.
 */
public class EditProfileStage extends IndexedStage{
    public final SlideRigger rigger;

    public EditProfileStage(Application context){
        super(EditProfileStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public EditProfileStage(){
        this(PeopleMon.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }

    @Override
    public boolean isModal() {
        return true;
    }
}
