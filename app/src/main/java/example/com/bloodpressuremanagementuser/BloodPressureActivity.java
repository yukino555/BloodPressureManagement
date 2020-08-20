package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BloodPressureActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        findViewById(R.id.btEntry).setOnClickListener(this);
    }
    public void onClick(View view){
        //ここに遷移するための処理を追加する
        Intent intent = new Intent(this, UserFinishActivity.class);
        startActivity(intent);
    }
}
