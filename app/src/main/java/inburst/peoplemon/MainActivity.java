package inburst.peoplemon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Network.UserStore;
import inburst.peoplemon.Stages.LoginStage;
import inburst.peoplemon.Stages.MapStage;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    public Bundle savedInstanceState;
    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        ButterKnife.bind(this);
        flow = PeopleMon.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);

        //testCalls();


        if(UserStore.getInstance().getToken() == null){
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE);
        } else {
            History newHistory = History.single(new MapStage());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile:
                Toast.makeText(this, "Clicked: Edit Profile", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                Toast.makeText(this, "Clicked: Logout", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m_menu, menu);

        return true;
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
