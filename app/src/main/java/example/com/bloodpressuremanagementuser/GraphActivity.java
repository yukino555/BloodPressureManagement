package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    LineChart mChart;
    DatabaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;
    ArrayList<Entry> valuesMaxBp;
    ArrayList<Entry> valuesMinBp;
    ArrayList<String> date;
    String labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        helper = new DatabaseHelper(GraphActivity.this);
        mChart = findViewById(R.id.lineChart);
        // データベースと接続
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_date", "_maxBP", "_minBP"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        valuesMaxBp = new ArrayList<>();
        valuesMinBp = new ArrayList<>();
        date        = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            valuesMaxBp.add(new Entry(i, cursor.getInt(1)));
            valuesMinBp.add(new Entry(i, cursor.getInt(2)));
            date.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        //  グラフViewの初期化
        initChart();
        // グラフに値をセットする
        setData();
        // データをアニメーションで出す。ミリ秒.数値が大きいと遅い
        mChart.animateX(1000);

        // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void initChart(){
        // グラフ説明テキストを表示するか
        mChart.getDescription().setEnabled(true);
        // グラフ説明テキスト「血圧マネジャー」
        mChart.getDescription().setText(getResources().getString(R.string.app_name));
        // グラフ説明テキストの文字色設定
//        mChart.getDescription().setTextColor(Color.BLACK);
        // グラフ説明テキストの文字サイズ設定
//        mChart.getDescription().setTextSize(10f);
        // グラフ説明テキストの表示位置設定
//        mChart.getDescription().setPosition(0, 0);
        // グラフの背景色
        mChart.setBackgroundColor(Color.WHITE);

        // ｘ軸の設定
        XAxis xAxis = mChart.getXAxis();
        // ｘ軸最大値最小値
        // 以下だと0~10番目が出力される(11個)
        // 書かないと、登録したデータ分表示される
//        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(10f);
        // データベースに登録した年月日時分秒をｘ軸に設定
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        // ｘ軸を破線にする(Dashed Line)
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // y軸の設定
        YAxis yAxis = mChart.getAxisLeft();
        // Y軸最大最小設定
        yAxis.setAxisMaximum(150f);
        yAxis.setAxisMinimum(50f);
        // y軸を破線にする
        yAxis.enableGridDashedLine(10f, 10f, 0f);
        yAxis.setDrawZeroLine(true);

        // 右側の目盛り。不要ならfalse
        mChart.getAxisRight().setEnabled(false);
    }



    private void setData() {


        LineDataSet maxBpLine;   // 最高血圧
        LineDataSet minBpLine;   //　最低血圧

        maxBpLine = new LineDataSet(valuesMaxBp, "最高血圧");
        maxBpLine.setColor(Color.RED);          // 線の色
        maxBpLine.setCircleColor(Color.RED);    // 座標の色
        maxBpLine.setLineWidth(5f);             // 線の太さ 1f~
        maxBpLine.setCircleRadius(5f);          // 座標の大きさ
        maxBpLine.setDrawCircleHole(false);     // 座標を塗りつぶす→false 塗りつぶさない→true
        maxBpLine.setValueTextSize(10f);        // データの値を記す。0fで記載なし。floatだから小数点がつく
        maxBpLine.setDrawFilled(true);          // 線の下を塗りつぶすか否か
        maxBpLine.setFillColor(Color.RED);      //　塗りつぶしたフィールドの色

        minBpLine = new LineDataSet(valuesMinBp, "最低血圧");
        minBpLine.setColor(Color.BLUE);
        minBpLine.setCircleColor(Color.BLUE);
        minBpLine.setLineWidth(5f);
        minBpLine.setCircleHoleRadius(5f);
        minBpLine.setDrawCircleHole(false);
        minBpLine.setValueTextSize(10f);
        minBpLine.setDrawFilled(true);
        minBpLine.setFillColor(Color.BLUE);

        // chartにラインをset
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(maxBpLine);
        dataSets.add(minBpLine);
        LineData lineData = new LineData(dataSets);
        mChart.setData(lineData);
    }

    public void onBack(View view) {
        finish();
    }
}


