package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class UserFinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("UserFinish","Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_finish);

    }
    @Override
    public void onStart(){
        Log.i("UserFinish","Sub onStart() called.");
        super.onStart();
    }
    @Override
    public void onRestart(){
        Log.i("UserFinish","Sub onRestart() called.");
        super.onRestart();
    }
    @Override
    public void onResume(){
        Log.i("UserFinish","Sub onResume() called.");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.i("UserFinish","Sub onPause() called.");
        super.onPause();
    }
    @Override
    public void onStop(){
        Log.i("UserFinish","Sub onStop() called.");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i("UserFinish","Sub onDestroy() called.");
        super.onDestroy();
    }

    public void onBackButtonClick(View view) {
        finish();
    }
}