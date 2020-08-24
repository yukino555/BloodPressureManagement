package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserFinishActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.i("UserFinish","Sub onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_finish);
        Button button = findViewById(R.id.btNext);
        button.setOnClickListener(UserFinishActivity.this);
//      findViewById(R.id.btNext)
    }
    public void onClick(View view){
        //ここに遷移するための処理を追加する
        Intent intent = new Intent(UserFinishActivity.this, BloodPressureAdditionActivity.class);
        startActivity(intent);
    }public void onBackButtonClick(View view) {
        finish();
    }

//    @Override
//    public void onStart(){
//        Log.i("UserFinish","Sub onStart() called.");
//        super.onStart();
//    }
//    @Override
//    public void onRestart(){
//        Log.i("UserFinish","Sub onRestart() called.");
//        super.onRestart();
//    }
//    @Override
//    public void onResume(){
//        Log.i("UserFinish","Sub onResume() called.");
//        super.onResume();
//    }
//    @Override
//    public void onPause(){
//        Log.i("UserFinish","Sub onPause() called.");
//        super.onPause();
//    }
//    @Override
//    public void onStop(){
//        Log.i("UserFinish","Sub onStop() called.");
//        super.onStop();
//    }
//    @Override
//    public void onDestroy(){
//        Log.i("UserFinish","Sub onDestroy() called.");
//        super.onDestroy();
//    }


}