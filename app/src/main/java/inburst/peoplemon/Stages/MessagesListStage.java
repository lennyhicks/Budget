package inburst.peoplemon.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;

import inburst.budget.R;
import inburst.peoplemon.PeopleMon;
import inburst.peoplemon.Riggers.SlideRigger;
import inburst.peoplemon.Views.MessageListView;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessagesListStage extends IndexedStage{
    public final SlideRigger rigger;
    private Integer converId;

    public MessagesListStage(Application context, Integer converId){
        super(MessagesListStage.class.getName());
        this.rigger = new SlideRigger(context);
        this.converId = converId;
        addComponents(new DataComponent());


    }

    public MessagesListStage(Integer converId){
        this(PeopleMon.getInstance(), converId);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_messages;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    private class DataComponent implements Component {
        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            MessageListView conversationView = (MessageListView) stage.getView();
            conversationView.getUsers(converId);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {

        }
    }
}
