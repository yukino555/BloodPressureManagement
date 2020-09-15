package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPressureAdditionActivity extends AppCompatActivity implements TextWatcher{
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
                getMaxBP = findViewById(R.id.etUpperBloodPressure);
                final int maxBP = Integer.parseInt(getMaxBP.getText().toString());
                getMinBP = findViewById(R.id.etLowerBloodPressure);
                final int minBP = Integer.parseInt(getMinBP.getText().toString());
                getPulse = findViewById(R.id.etPulse);
                final int pulseInt = Integer.parseInt(getPulse.getText().toString());


                String errorMsg = "この値は入力できません";
                if(!(80 <= maxBP && maxBP <= 180)){
                    getMaxBP.setError(errorMsg);
                    return;
                } else if(!(50 <= minBP && minBP <= 140)){
                    getMinBP.setError(errorMsg);
                    return;
                } else if(!(40 <= pulseInt && pulseInt <= 120)){
                    getPulse.setError(errorMsg);
                    return;
                }
                // データベースに値を登録するメソッド
                insertData(db, maxBP, minBP, pulseInt);
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
//} catch (Exception e){
//        if(!(80 <= maxBP && maxBP <= 180)){
//        getMaxBP.setError("この値は入力できません");
//        }
//        if(!(50 <= minBP && minBP <= 140)){
//        getMinBP.setError("この値は入力できません");
//        }
//        if(!(40 <= pulse && pulse <= 120)){
//        getPulse.setError("この値は入力できません");
//        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}






