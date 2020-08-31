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
    Button btNext;
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
        db = helper.getWritableDatabase();


        btEntry = findViewById(R.id.btEntry);
        btEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                db = helper.getReadableDatabase();
                Cursor cursor = db.query(
                        "_BPtable",
                        new String[]{"_date", "_maxBP", "_minBP"," _pulse"},
                        null,
                        null,
                        null,
                        null,
                        null
                );
                cursor.moveToFirst();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cursor.getCount(); i++) {
                    sb.append(cursor.getString(0));
                    sb.append(cursor.getInt(1));
                    sb.append("mmHg");
                    sb.append(cursor.getInt(2));
                    sb.append("mmHg");
                    sb.append(cursor.getInt(3));
                    cursor.moveToNext();
                }
                cursor.close();
                textView = findViewById(R.id.text_view);
                textView.setText(sb.toString());
            }
        });
        btNext = findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloodPressureAdditionActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
    }
    public void insertData(SQLiteDatabase db, int maxBP, int minBP, int pulse) {
        ContentValues values = new ContentValues();
        try {
            values.put("_date", getNowDate());
            values.put("_maxBP", maxBP);
            values.put("_minBP", minBP);
            values.put("_pulse", pulse);
            db.insert("_BPtable", null, values);
        } finally {
            db.close();
        }
    }
}




//
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



