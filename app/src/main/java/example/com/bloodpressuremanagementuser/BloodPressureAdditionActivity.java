package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureAdditionActivity extends AppCompatActivity {
    EditText getMaxBP;
    EditText getMinBP;
    EditText getPulse;
    Button btEntry;
    Button btNext;
    DatabaseHelper helper;
    SQLiteDatabase db;

    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);
        helper = new DatabaseHelper(BloodPressureAdditionActivity.this);
        db = helper.getWritableDatabase();
        // カスタムフォントの処理
        Typeface customFont = Typeface.createFromAsset(getAssets(), "Ronde-B_square.otf");
        TextView time = findViewById(R.id.tvCountingTime);
        // textviewのidとカスタムフォントを繫ぐ
        time.setTypeface(customFont);
        TextView upperBP = findViewById(R.id.tvUpperBloodPressure);
        upperBP.setTypeface(customFont);
        TextView mmHg1 = findViewById(R.id.tvMmhg1);
        mmHg1.setTypeface(customFont);
        TextView lowerBP = findViewById(R.id.tvLowerBloodPressure);
        lowerBP.setTypeface(customFont);
        TextView mmHg2 = findViewById(R.id.tvMmhg2);
        mmHg2.setTypeface(customFont);
        TextView pulse = findViewById(R.id.tvPulse);
        pulse.setTypeface(customFont);
        TextView pulseUnit = findViewById(R.id.tvPulseUnit);
        pulseUnit.setTypeface(customFont);


        btEntry = findViewById(R.id.btEntry);
        btEntry.setTypeface(customFont);
        btEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // R値と結び、int型に直してからテキストにしている。
                getMaxBP = findViewById(R.id.etUpperBloodPressure);
                String maxBP = (getMaxBP.getText().toString());

                getMinBP = findViewById(R.id.etLowerBloodPressure);
                String minBP = (getMinBP.getText().toString());

                getPulse = findViewById(R.id.etPulse);
                String pulse = (getPulse.getText().toString());

                String nullMsg = "値を入力してください";
                if(maxBP.length()==0 || Integer.parseInt(maxBP)<1){
                    getMaxBP.setError(nullMsg);
                    return;
                } else if(minBP.length()==0 || Integer.parseInt(minBP)<1){
                    getMinBP.setError(nullMsg);
                    return;
                } else if(pulse.length()==0 || Integer.parseInt(pulse)<1){
                    getPulse.setError(nullMsg);
                    return;
                }
                String errorMsg = "この値は入力できません";
                if(!(80 <= Integer.parseInt(maxBP) && Integer.parseInt(maxBP) <= 180)){
                    getMaxBP.setError(errorMsg);
                    return;
                } else if(!(50 <= Integer.parseInt(minBP) && Integer.parseInt(minBP) <= 140)){
                    getMinBP.setError(errorMsg);
                    return;
                } else if(!(40 <= Integer.parseInt(pulse) && Integer.parseInt(pulse) <= 120)){
                    getPulse.setError(errorMsg);
                    return;
                }
                // データベースに値を登録するメソッド
                insertData(db, maxBP, minBP, pulse);
                // 登録に成功したらトーストがでる
                Toast toast = Toast.makeText(BloodPressureAdditionActivity.this, "登録しました", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
//                getMaxBP.setText("");
//                getMinBP.setText("");
//                getPulse.setText("");
            }
        });

        btNext = findViewById(R.id.btNext);
        btNext.setTypeface(customFont);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloodPressureAdditionActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insertData(SQLiteDatabase db, String maxBP, String minBP, String pulse){
        ContentValues values = new ContentValues();
        try (SQLiteDatabase d = helper.getWritableDatabase()){
            values.put("_date", getNowDate());
            values.put("_maxBP", maxBP);
            values.put("_minBP", minBP);
            values.put("_pulse", pulse);
            d.insert("_BPtable", null, values);
        }
        /*
         dbをクローズしたものを再度開けようとしたから
         「java.lang.illegalStateException attempt to re-open an already-closed object」
         というエラーが出ていた。　 を用いて解決
         */
//        finally {
//            db.close();
//        }
    }
}
