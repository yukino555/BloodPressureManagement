package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BloodPressureAdditionActivity extends AppCompatActivity {
    TextView tv_maxBP;
    TextView tv_minBP;
    Button _btEntry;
    Button _btShow;
    DatabaseHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);

        tv_maxBP = findViewById(R.id.tvUpperBloodPressure);
        tv_minBP = findViewById(R.id.tvLowerBloodPressure);
        _btEntry = findViewById(R.id.btEntry);
        _btEntry.setOnClickListener(new onClickListener());
        _btShow  = findViewById(R.id.btShow);
        _btShow.setOnClickListener(new onClickListener());

        // DataBase
        DatabaseHelper helper = new DatabaseHelper(BloodPressureAdditionActivity.this);
    }
//    public void onEntryButtonClick(View view){
//        EditText maxBP = findViewById(R.id.etUpperBloodPressure);
//        EditText minBp = findViewById(R.id.etLowerBloodPressure);
//    }
    public class onClickListener implements View.OnClickListener{
        @Override
            public void onClick(View view){
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                EditText maxBP = findViewById(R.id.etUpperBloodPressure);
                EditText minBp = findViewById(R.id.etLowerBloodPressure);
                String upperBP = maxBP.getText().toString();
                String lowerBP = minBp.getText().toString();

                values.put("maxBP", upperBP);
                values.put("minBP", lowerBP);

                db.insert("bloodpressuredb", null, values);
        }
//            public void saveData(View view){
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//            }
    }



//    private void insertData(SQLiteDatabase db, int maxBP, int minBP){
//        ContentValues values = new ContentValues();
//        values.put("upperBloodPressure", maxBP);
//        values.put("lowerBloodPressure", minBP);
//
//        db.insert("bloodpressuredb",null,values);
//    }
//    private void setViews(){
//        Button button = (Button)findViewById(R.id.btEntry);
//        button.setOnClickListener(onClick_button);
//    }
//    private View.OnClickListener onClick_button = new View.OnClickListener(){
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(BloodPressureAdditionActivity.this, HomeActivity.class);
//            startActivity(intent);
//        }
//    };

     //登録ボタンがタップされた時の処理メソッド
//    public void onEntryButtonClick(View view) {
//        EditText etUpperBloodPressure = findViewById(R.id.etUpperBloodPressure);
//        EditText etLowerBloodPressure = findViewById(R.id.etLowerBloodPressure);
        // 第一引数、このページ　第二引数、遷移したいページ
//        Intent intent = new Intent(UserFinishActivity.this, BloodPressureAdditionActivity.class);
//        startActivity(intent);


}