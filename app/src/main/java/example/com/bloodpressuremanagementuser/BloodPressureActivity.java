package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BloodPressureActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("BloodPressureManegementUser","Main onCreate() called.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
    // 登録ボタンを押したら画面遷移用の記述
//        findViewById(R.id.btEntry).setOnClickListener(this);
        
    }
    @Override
    public void onStart(){
        Log.i("BloodPressureManegementUser","Main onStart() called.");
        super.onStart();
    }
    @Override
    public void onRestart(){
        Log.i("BloodPressureManegementUser","Main onRestart() called.");
        super.onRestart();
    }
    @Override
    public void onResume(){
        Log.i("BloodPressureManegementUser","Main onResume() called.");
        super.onResume();
    }
    @Override
    public void onPause(){
        Log.i("BloodPressureManegementUser","Main onPause() called.");
        super.onPause();
    }
    @Override
    public void onStop(){
        Log.i("BloodPressureManegementUser","Main onStop() called.");
        super.onStop();
    }
    @Override
    public void onDestroy(){
        Log.i("BloodPressureManegementUser","Main onDestroy() called.");
        super.onDestroy();
    }
    /*
    登録ボタンを押したら画面遷移する処理  public class Blood~manegement に以下をインプリメントする
    implements View.OnClickListener{

     */
//    public void onClick(View view){
//        //ここに遷移するための処理を追加する
//        Intent intent = new Intent(this, UserFinishActivity.class);
//        startActivity(intent);
//    }
}
