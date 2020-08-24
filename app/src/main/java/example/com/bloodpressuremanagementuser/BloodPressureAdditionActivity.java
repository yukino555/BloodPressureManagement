package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BloodPressureAdditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);
        setViews();
    }
    private void setViews(){
        Button button = (Button)findViewById(R.id.btEntry);
        button.setOnClickListener(onClick_button);
    }
    private View.OnClickListener onClick_button = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BloodPressureAdditionActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    };

     //登録ボタンがタップされた時の処理メソッド
    public void onEntryButtonClick(View view) {
        EditText etUpperBloodPressure = findViewById(R.id.etUpperBloodPressure);
        EditText etLowerBloodPressure = findViewById(R.id.etLowerBloodPressure);
        // 第一引数、このページ　第二引数、遷移したいページ
//        Intent intent = new Intent(UserFinishActivity.this, BloodPressureAdditionActivity.class);
//        startActivity(intent);
    }

}