package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BloodPressureAdditionActivity extends AppCompatActivity {
    TextView textView;
    EditText maxBP;
    EditText minBP;
    Button btEntry;
    Button btShow;
    DatabaseHelper helper;
    SQLiteDatabase db;
    int _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);
        helper = new DatabaseHelper(BloodPressureAdditionActivity.this);

        textView = findViewById(R.id.text_view);
        maxBP = findViewById(R.id.etUpperBloodPressure);
        minBP = findViewById(R.id.etLowerBloodPressure);
        btEntry = findViewById(R.id.btEntry);
        btEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getWritableDatabase();
//                try{
//                    /*
//                    　ステートメント、SQL文を実行するオブジェクト
//                     */
//                    SQLiteStatement stmt;
//                    String sqlInsert =
//                            "INSERT INTO bloodpressuredb (_id, _maxBP, _minBP) VALUES(int,int,int)";
//                    stmt = db.compileStatement(sqlInsert);
//                    stmt.executeInsert();
//                }
//                finally {
//                    db.close();
//                }

                // 上はテキストに沿った形。動かない。。SQL文使いたいのにね
                ContentValues values = new ContentValues();

                String upperBP = maxBP.getText().toString();
                String lowerBP = minBP.getText().toString();

                values.put("maxBP", upperBP);
                values.put("minBP", lowerBP);

                db.insert("bloodpressuredb", null, values);
                db.close();
            }
        });

        btShow = findViewById(R.id.btShow);
        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });
    }
    public void readData(){
        db = helper.getReadableDatabase();
        /*
         テキストのSQL文を使いたいのだが、_cocktailIdが当てはまるのが何かがわからない
         */
        String sql = "SELECT * FROM bloodpressuredb WHERE _id = " + _id;
        Cursor cursor = db.query(
                "bloodpressuredb",
                new String[] {"maxBP","minBP"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<cursor.getCount(); i++){
            sb.append(cursor.getInt(0));
            sb.append("mmHg");
            sb.append(cursor.getInt(1));
            sb.append("mmHg");
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(sb.toString());
    }
}
//    public void onEntryButtonClick(View view){
//        EditText maxBP = findViewById(R.id.etUpperBloodPressure);
//        EditText minBp = findViewById(R.id.etLowerBloodPressure);
//    }
//    public class onClickListener implements View.OnClickListener{
//        @Override
//            public void onClick(View view){
                // DataBase
//                DatabaseHelper helper = new DatabaseHelper(BloodPressureAdditionActivity.this);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//
//
//                String upperBP = maxBP.getText().toString();
//                String lowerBP = minBP.getText().toString();
//
//                values.put("maxBP", upperBP);
//                values.put("minBP", lowerBP);
//
//                db.insert("bloodpressuredb", null, values);
//        }
//            public void saveData(View view){
//                SQLiteDatabase db = helper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//            }




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


