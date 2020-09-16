package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper helper;
    SQLiteDatabase db;
    Button btShow;
    Button btAvg;
    Button btMaxValue;
    Button btBack;
    Cursor cursor;
    TextView textView;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        helper = new DatabaseHelper(HomeActivity.this);

        btShow = findViewById(R.id.btShow);
        btAvg = findViewById(R.id.btAvg);
        btMaxValue = findViewById(R.id.btMaxValue);
        btBack = findViewById(R.id.btBack);
    }
    public void onShow(View view) {
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_date", "_maxBP", "_minBP", " _pulse"},
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
            sb.append(" ");
            sb.append(cursor.getInt(1));
            sb.append("mmHg");
            sb.append(cursor.getInt(2));
            sb.append("mmHg");
            sb.append(cursor.getInt(3));
            sb.append("拍/分");
            sb.append("\n");
            cursor.moveToNext();
        }
        cursor.close();
        textView = findViewById(R.id.text_view);
        textView.setText(sb.toString());
    }
/*
 データベースに登録したデータを呼び出し平均を出したい
 */
//  maxBPを全部取り出し、繰り返して全部足す
    public void onAvg(View view) {
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_maxBP", "_minBP", "_pulse"},
                null,
                null,
                null,
                null,
                null
        );
        int addMaxBP = 0;
        int addMinBP = 0;
        int addPulse = 0;
        int number = cursor.getCount();
        cursor.moveToFirst();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {
            addMaxBP += cursor.getInt(0);
            addMinBP += cursor.getInt(1);
            addPulse += cursor.getInt(2);
            cursor.moveToNext();
        }
        cursor.close();
        sb.append("最高血圧 : ");
        sb.append(addMaxBP/number + "\n");
        sb.append("最低血圧 : ");
        sb.append(addMinBP/number + "\n");
        sb.append("脈拍 : ");
        sb.append(addPulse/number + "\n");
        textView2 = findViewById(R.id.text_view2);
        textView2.setText(sb.toString());
    }

    public void onMaxValue(View view) {
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_maxBP", "_minBP", "_pulse"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        int maxUpperBP = Integer.MIN_VALUE;
        int maxLowerBP = Integer.MIN_VALUE;
        int maxPulse = Integer.MIN_VALUE;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursor.getCount(); i++) {  // 列(column)が次に移る
            maxUpperBP = Math.max(maxUpperBP, cursor.getInt(0));
            maxLowerBP = Math.max(maxLowerBP, cursor.getInt(1));
            maxPulse = Math.max(maxPulse, cursor.getInt(2));
            cursor.moveToNext();  // 行(row)が次に移る
        }
        cursor.close();
        sb.append("最高血圧 : ");
        sb.append(maxUpperBP + "\n");
        sb.append("最低血圧 : ");
        sb.append(maxLowerBP + "\n");
        sb.append("脈拍 : ");
        sb.append(maxPulse + "\n");
        textView3 = findViewById(R.id.text_view3);
        textView3.setText(sb.toString());
    }

    public void onBackButtonClick(View view) {
        finish();
    }

    public void onGraph(View view) {
        Intent intent = new Intent(HomeActivity.this, GraphActivity.class);
        startActivity(intent);
    }
    // Activityにオーバーフローメニューを出現させるメソッド
    public boolean onCreateOptionsMenu(Menu menu){
        // メニューインフレーターの取得
        MenuInflater inflater = getMenuInflater();
        // オプションメニュー用.xml ファイルをインフレート
        inflater.inflate(R.menu.menu_options, menu);
        // 親クラスの同名メソッドを呼び出し。その戻り値を返却
        return super.onCreateOptionsMenu(menu);
    }
}