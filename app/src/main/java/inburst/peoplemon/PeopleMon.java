package inburst.peoplemon;

import android.app.Application;

import flow.Flow;
import flow.History;
import inburst.peoplemon.Stages.PeopleMonStage;

/**
 * Created by lennyhicks on 10/31/16.
 */
public class PeopleMon extends Application {
    private static PeopleMon application;
    public final Flow mainFlow = new Flow(History.single(new PeopleMonStage()));
    public static String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    public static PeopleMon getInstance() {
        return application;
    }

    public static Flow getMainFlow() {
        return  getInstance().mainFlow;
    }
/*
Map to fill background
Three floating action buttons


https://icons8.com

menu button .. logout, edit profile

 */

}
