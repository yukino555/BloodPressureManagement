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
    private TextView textView;
    private EditText editTextKey, editTextValue;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);

        editTextKey = findViewById(R.id.etUpperBloodPressure);
        editTextValue = findViewById(R.id.etLowerBloodPressure);

        textView = findViewById(R.id.text_view);

        Button insertButton = findViewById(R.id.btEntry);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(helper == null){
                    helper = new DatabaseHelper(getApplicationContext());
                }
                if(db == null){
                    db = helper.getWritableDatabase();
                }
                String key = editTextKey.getText().toString();
                String value = editTextValue.getText().toString();

                insertData(db, key, Integer.valueOf(value));
            }
        });
        Button readButton = findViewById(R.id.btShow);
        readButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                readData();
            }
        });
    }
    private void readData(){
        if(helper == null){
            helper = new DatabaseHelper(getApplicationContext());
        }
        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug", "**********Cursor");

        Cursor cursor = db.query(
                "bloodpressuredb",
                new String[] {"upperBloodPressure", "lowerBloodPressure"},
                null,
        );
        cursor.moveToFirst();

        StringBuilder sb = new StringBuilder();

        for(int i =0; i< cursor.getCount(); i++){
            sb.append(cursor.getString(0));
            sb.append(": ");
            sb.append(cursor.getInt(1));
            sb.append("\n");
            cursor.moveToNext();
        }
        cursor.close();

        Log.d("debug", "**********"+sb.toString());
        textView.setText(sb.toString());
    }
    private void insertData(SQLiteDatabase db, int upperBP, int lowerBP){
        ContentValues values = new ContentValues();
        values.put("upperBloodPressure", upperBP);
        values.put("lowerBloodPressure", lowerBP);

        db.insert("bloodpressuredb",null,values);
    }
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