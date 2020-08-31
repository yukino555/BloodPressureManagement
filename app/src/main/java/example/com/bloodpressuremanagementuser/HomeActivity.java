package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper helper;
    SQLiteDatabase db;
    Button btShow;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        helper = new DatabaseHelper(HomeActivity.this);
        db = helper.getWritableDatabase();

        btShow = findViewById(R.id.btShow);
    }
        public void onShow(View view) {
            db = helper.getReadableDatabase();
            Cursor cursor = db.query(
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
                cursor.moveToNext();
            }
            cursor.close();
            textView = findViewById(R.id.text_view);
            textView.setText(sb.toString());
        }
}