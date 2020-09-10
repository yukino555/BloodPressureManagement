package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    LineChart mChart;
    DatabaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        helper = new DatabaseHelper(GraphActivity.this);

        mChart = findViewById(R.id.lineChart);

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
        XAxis xMaxBp = mChart.getXAxis();
        // ｘ軸最大値最小値
        xMaxBp.setAxisMaximum(30f);
        xMaxBp.setAxisMinimum(0f);
        // ｘ軸を破線にする(Dashed Line)
        xMaxBp.enableGridDashedLine(10f, 10f, 0f);
        xMaxBp.setPosition(XAxis.XAxisPosition.BOTTOM);

        // y軸の設定
        YAxis yMaxBp = mChart.getAxisLeft();
        // Y軸最大最小設定
        yMaxBp.setAxisMaximum(150f);
        yMaxBp.setAxisMinimum(50f);
        // y軸を破線にする
        yMaxBp.enableGridDashedLine(10f, 10f, 0f);
        yMaxBp.setDrawZeroLine(true);

        // 右側の目盛り。不要ならfalse
        mChart.getAxisRight().setEnabled(false);

        // グラフに値をセットする
        setData();
        // データをアニメーションで出す。ミリ秒.数値が大きいと遅い
        mChart.animateX(1000);

        // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void setData() {
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
        ArrayList<Entry> valuesMaxBp = new ArrayList<>();
        ArrayList<Entry> valuesMinBp = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            valuesMaxBp.add(new Entry(i, cursor.getInt(1)));
            valuesMinBp.add(new Entry(i, cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();

        LineDataSet maxBpLine;   // 最高血圧
        LineDataSet minBpLine;   //　最低血圧

        maxBpLine = new LineDataSet(valuesMaxBp, "最高血圧");
        maxBpLine.setDrawIcons(false);  // true false の違いが判らない
        maxBpLine.setColor(Color.RED);  // 線の色
        maxBpLine.setCircleColor(Color.RED);  // データのドットの色
        maxBpLine.setLineWidth(5f);  // 線の太さ 1f~
        maxBpLine.setCircleRadius(5f);  // データドットの大きさ
        maxBpLine.setDrawCircleHole(false);  // データドットを塗りつぶす→false 塗りつぶさない→true
        maxBpLine.setValueTextSize(10f);  // データの値を記す。0fで記載なし。floatだから小数点がつく
        maxBpLine.setDrawFilled(true);
        maxBpLine.setFormLineWidth(1f);
        maxBpLine.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        maxBpLine.setFormSize(15.f);
        maxBpLine.setFillColor(Color.RED);

        minBpLine = new LineDataSet(valuesMinBp, "最低血圧");
        minBpLine.setDrawIcons(false);
        minBpLine.setColor(Color.BLUE);
        minBpLine.setCircleColor(Color.BLUE);
        minBpLine.setLineWidth(5f);
        minBpLine.setCircleHoleRadius(5f);
        minBpLine.setDrawCircleHole(false);
        minBpLine.setValueTextSize(10f);
        minBpLine.setDrawFilled(true);
        minBpLine.setFormLineWidth(1f);
        minBpLine.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        minBpLine.setFormSize(15.f);
        minBpLine.setFillColor(Color.BLUE);

        // ここがchartにラインを作っている
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

//    private void setDataMinBp() {
//        db = helper.getReadableDatabase();
//        cursor = db.query(
//                "_BPtable",
//                new String[]{"_date", "_minBP"},
//                null,
//                null,
//                null,
//                null,
//                null
//                );
//        cursor.moveToFirst();
//        ArrayList<Entry> valuesMinBp = new ArrayList<>();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            valuesMinBp.add(new Entry(i, cursor.getInt(1), null, null));
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        // 最低血圧の線
//        LineDataSet set2;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//
//            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
//            set2.setValues(valuesMinBp);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type
//            set2 = new LineDataSet(valuesMinBp, "最低血圧");
//
//            set2.setDrawIcons(false);  // true false の違いが判らない
//            set2.setColor(Color.BLUE);  // 線の色
//            set2.setCircleColor(Color.BLUE);  // データのドットの色
//            set2.setLineWidth(5f);  // 線の太さ 1f~
//            set2.setCircleRadius(5f);  // データドットの大きさ
//            set2.setDrawCircleHole(false);  // データドットを塗りつぶす→false 塗りつぶさない→true
//            set2.setValueTextSize(10f);  // データの値を記す。0fで記載なし。floatだから小数点がつく
//            set2.setDrawFilled(true);
//            set2.setFormLineWidth(1f);
//            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set2.setFormSize(15.f);
//
//            set2.setFillColor(Color.BLUE);
//
//            ArrayList<ILineDataSet> dataSetsMinBp = new ArrayList<>();
//            dataSetsMinBp.add(set2); // add the datasets
//
//            // create a data object with the datasets
//            LineData lineDataMinBp = new LineData(dataSetsMinBp);
//
//            // set data
//            mChart.setData(lineDataMinBp);
//        }
//
//
//    }
