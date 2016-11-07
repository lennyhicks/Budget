package inburst.peoplemon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Network.UserStore;
import inburst.peoplemon.Stages.LoginStage;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;

    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        flow = PeopleMon.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

        //testCalls();

        if(UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null){
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        }
    }

    @Override
    public void onBackPressed(){
        if (!flow.goBack()){
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new PeopleMon()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }

    }

//    private void testCalls(){
//        RestClient restClient = new RestClient();
//        restClient.getApiService().getPost(1).enqueue(new Callback<TestPost>() {
//            @Override
//            public void onResponse(Call<TestPost> call, Response<TestPost> response) {
//                Log.i(TAG, "Getpost - Title: " + response.body().getTitle() + "\n Body: " + response.body().getBody());
//            }
//
//            @Override
//            public void onFailure(Call<TestPost> call, Throwable t) {
//                Log.i(TAG, "GetPost Failed");
//            }
//        });
//
//        TestPost testPost = new TestPost(1, "Test post Title", "Test post Body");
//        restClient.getApiService().postPost(testPost).enqueue(new Callback<TestPost>() {
//            @Override
//            public void onResponse(Call<TestPost> call, Response<TestPost> response) {
//                Log.i(TAG, "post Post - Body: " + response.body().getBody());
//            }
//
//            @Override
//            public void onFailure(Call<TestPost> call, Throwable t) {
//                Log.i(TAG, "post Post Failed");
//            }
//        });
//
//        restClient.getApiService().getAllPost().enqueue(new Callback<TestPost[]>() {
//            @Override
//            public void onResponse(Call<TestPost[]> call, Response<TestPost[]> response) {
//                Log.i(TAG, "Post: " + response.body().length
//                );
//            }
//
//            @Override
//            public void onFailure(Call<TestPost[]> call, Throwable t) {
//                Log.i(TAG, "FAILUREEEE");
//            }
//        });
//    }

}
