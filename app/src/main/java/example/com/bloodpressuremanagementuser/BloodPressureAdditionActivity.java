package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BloodPressureAdditionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText getMaxBP;
    EditText getMinBP;
    EditText getPulse;
    Button btEntry;
    Button btNext;
    TextView textDate;
    TextView textTime;
    String getDate;
    String getTime;

    public static String getNowDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_addition);
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
        Button setDate = findViewById(R.id.btDate);
        setDate.setTypeface(customFont);
        Button setTime = findViewById(R.id.btTime);
        setTime.setTypeface(customFont);
        textDate = findViewById(R.id.textDate);
        textDate.setTypeface(customFont);
        textTime = findViewById(R.id.textTime);
        textTime.setTypeface(customFont);


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
                if(maxBP.length()==0){
                    getMaxBP.setError(nullMsg);
                    return;
                } else if(minBP.length()==0){
                    getMinBP.setError(nullMsg);
                    return;
                } else if(pulse.length()==0){
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
                insertData(maxBP, minBP, pulse);
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

    public void insertData(String maxBP, String minBP, String pulse){
        ContentValues values = new ContentValues();
        DatabaseHelper helper = new DatabaseHelper(BloodPressureAdditionActivity.this);
        try (SQLiteDatabase data = helper.getWritableDatabase()){
            values.put("_date", getDate);
            values.put("_time", getTime);
            values.put("_maxBP", maxBP);
            values.put("_minBP", minBP);
            values.put("_pulse", pulse);
            data.insert("_BPtable", null, values);
        } catch (Exception e){
            e.printStackTrace();
        }
        /*
         dbをクローズしたものを再度開けようとしたから
         「java.lang.illegalStateException attempt to re-open an already-closed object」
         というエラーが出ていた。　 try-with-resourcesを用いて解決
         */
//        finally {
//            db.close();
//        }
    }
    // 日付取得ダイアログ  null登録できるが、グラフで落ちる
    // 過去の日付が現在より後ろでグラフになってしまう。。
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        getDate = String.format(Locale.JAPAN, "%d/%d/%d", year,
                                    month+1, dayOfMonth);
        textDate.setText(getDate);
    }
    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // 時間取得ダイアログ  null登録できるが、グラフで落ちる
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        getTime = String.format(Locale.JAPAN, "%d:%d", hourOfDay, minute);
        textTime.setText(getTime);
    }
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
