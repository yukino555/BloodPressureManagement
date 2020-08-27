package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureAdditionActivity extends AppCompatActivity {
    TextView textView;
    EditText getMaxBP;
    EditText getMinBP;
    EditText getPulse;
    Button btEntry;
    Button btShow;
    DatabaseHelper helper;
    SQLiteDatabase db;
    int _id;

    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);
        helper = new DatabaseHelper(BloodPressureAdditionActivity.this);

        btEntry = findViewById(R.id.btEntry);
        btEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = helper.getWritableDatabase();
                textView = findViewById(R.id.text_view);
                getMaxBP = findViewById(R.id.etUpperBloodPressure);
                final int maxBP = Integer.parseInt(getMaxBP.getText().toString());
                getMinBP = findViewById(R.id.etLowerBloodPressure);
                final int minBP = Integer.parseInt(getMinBP.getText().toString());
                getPulse = findViewById(R.id.etPulse);
                final int pulse = Integer.parseInt(getPulse.getText().toString());
                insertData(db, maxBP, minBP, pulse);  // データベースに値を登録するメソッド
//                insertData(db,maxBP,minBP);
                BPEntryDialogFragment dialogFragment = new BPEntryDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "BPDialogFragment");
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

    //  最高血圧、最低血圧、脈拍
    public void insertData(SQLiteDatabase db, int maxBP, int minBP, int pulse) {
        ContentValues values = new ContentValues();
        try {
            values.put("date", getNowDate());
            values.put("maxBP", maxBP);
            values.put("minBP", minBP);
            values.put("pulse", pulse);
            db.insert("bloodpressuredb", null, values);
        } finally {
            db.close();
        }
    }

    public void readData() {
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                "bloodpressuredb",
                new String[]{"maxBP", "minBP"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            sb.append(cursor.getInt(0));
            sb.append("mmHg");
            sb.append(cursor.getInt(1));
            sb.append("mmHg");
            cursor.moveToNext();

        }
    }
}

//  脈拍を入力しなかった場合のメソッド
//    public void insertData(SQLiteDatabase db, int maxBP, int minBP){
//        ContentValues values = new ContentValues();
//        values.put("date", getNowDate());
//        values.put("maxBP", maxBP);
//        values.put("minBP", minBP);
//
//        db.insert("bloodpressuredb", null, values);
//        db.close();
//    }


//    }
//    public void readData(){
//        db = helper.getReadableDatabase();
//        /*
//         テキストのSQL文を使いたいのだが、_cocktailIdが当てはまるのが何かがわからない
//         */
//        String sql = "SELECT * FROM bloodpressuredb WHERE _id = " + _id;
//        Cursor cursor = db.query(
//                "BPdb",
//                new String[] {"maxBP","minBP"},
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        cursor.moveToFirst();
//        StringBuilder sb = new StringBuilder();
//        for(int i=0; i<cursor.getCount(); i++){
//            sb.append(cursor.getInt(0));
//            sb.append("mmHg");
//            sb.append(cursor.getInt(1));
//            sb.append("mmHg");
//            cursor.moveToNext();
//        }
//        cursor.close();
//        textView.setText(sb.toString());
//    }



