package inburst.peoplemon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Components.Constants;
import inburst.peoplemon.Dialogs.EditProfile;
import inburst.peoplemon.Events.ImageLoadedEvent;
import inburst.peoplemon.Models.Account;
import inburst.peoplemon.Network.RestClient;
import inburst.peoplemon.Stages.LoginStage;
import inburst.peoplemon.Stages.MapStage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.BitmapFactory.decodeFile;
import static inburst.peoplemon.Components.Constants.RESULT_LOAD_IMAGE;
import static inburst.peoplemon.Components.Utils.encodeTobase64;
import static inburst.peoplemon.Network.UserStore.getInstance;
import static inburst.peoplemon.Network.UserStore.setToken;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private Flow flow;
    private ScreenplayDispatcher dispatcher;
    private Context context;
    RestClient restClient;
    public Bundle savedInstanceState;

    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;
        restClient = new RestClient();

        ButterKnife.bind(this);
        flow = PeopleMon.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container);
        dispatcher.setUp(flow);
        this.context = getApplicationContext();

        if (Build.VERSION.SDK_INT >= 23) {
            if (!(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        //testCalls();


        if(getInstance().getToken() == null){
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
        Flow flow = PeopleMon.getMainFlow();
        switch (item.getItemId()) {
            case R.id.editProfile:
                restClient.getApiService().getUserInfo().enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Account account = response.body();
                        if (account.getAvatarBase64().length() > 200) {
                            Constants.IMAGE = account.getAvatarBase64();
                        } else {
                            Constants.IMAGE = "No Image";
                        }

                        final EditProfile editDialog = new EditProfile(MainActivity.this, account);
                        editDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        editDialog.show();
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                    }
                });

                return true;
            case R.id.logout:
                setToken(null);
                History logoutHistory = History.single(new LoginStage());
                flow.setHistory(logoutHistory, Flow.Direction.REPLACE);
                logout();
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

public void logout(){
    restClient.getApiService().logout().enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    });
}

public void getImage(){
    if (Build.VERSION.SDK_INT >= 23) {
        if (!(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (!(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    Intent i = new Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    startActivityForResult(i, RESULT_LOAD_IMAGE);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap image = decodeFile(picturePath);
            encodeTobase64(image);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            EventBus.getDefault().post(new ImageLoadedEvent());
            try {
                imageView.setImageBitmap(image);
            } catch (Exception e){
                e.printStackTrace();
            }


        }
    }


}
