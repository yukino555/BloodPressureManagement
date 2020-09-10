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

        // Grid背景色
        mChart.setDrawGridBackground(false);
        // no description text
        mChart.getDescription().setText("血圧");

        // Grid縦軸を破線　xAxis横軸
        XAxis xMaxBp = mChart.getXAxis();
        xMaxBp.setAxisMaximum(30f);
        xMaxBp.setAxisMinimum(0f);
        xMaxBp.enableGridDashedLine(10f, 10f, 0f);
        xMaxBp.setPosition(XAxis.XAxisPosition.BOTTOM);

        // yAxis縦軸
        // 最高血圧
        YAxis yMaxBp = mChart.getAxisLeft();
        // Y軸最大最小設定
        yMaxBp.setAxisMaximum(150f);
        yMaxBp.setAxisMinimum(50f);
        // Grid横軸を破線(Dashed Line)
        yMaxBp.enableGridDashedLine(10f, 10f, 0f);
        yMaxBp.setDrawZeroLine(true);

        // 右側の目盛り.要らないならfalse
        mChart.getAxisRight().setEnabled(false);

        // add data
        setDataMaxBp();
//        setDataMinBp();
        // データをアニメーションで出す。ミリ秒.数値が大きいと遅い
        mChart.animateX(1000);
        //mChart.invalidate();

        // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void setDataMaxBp() {
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
            valuesMaxBp.add(new Entry(i, cursor.getInt(1), null, null));
            valuesMinBp.add(new Entry(i, cursor.getInt(2), null, null));
            cursor.moveToNext();
        }
        cursor.close();

        LineDataSet set1; // 最高血圧
        LineDataSet set2; //　最低血圧

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(valuesMaxBp);
            set2.setValues(valuesMinBp);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(valuesMaxBp, "最高血圧");

            set1.setDrawIcons(false);  // true false の違いが判らない
            set1.setColor(Color.RED);  // 線の色
            set1.setCircleColor(Color.RED);  // データのドットの色
            set1.setLineWidth(5f);  // 線の太さ 1f~
            set1.setCircleRadius(5f);  // データドットの大きさ
            set1.setDrawCircleHole(false);  // データドットを塗りつぶす→false 塗りつぶさない→true
            set1.setValueTextSize(10f);  // データの値を記す。0fで記載なし。floatだから小数点がつく
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            set1.setFillColor(Color.RED);

            ArrayList<ILineDataSet> dataSetsMaxBp = new ArrayList<>();
            dataSetsMaxBp.add(set1); // add the datasets

            // create a data object with the datasets
            LineData lineDataMaxBp = new LineData(dataSetsMaxBp);

            // set data
            mChart.setData(lineDataMaxBp);

            set2 = new LineDataSet(valuesMinBp, "最低血圧");
            set2.setDrawIcons(false);
            set2.setColor(Color.BLUE);
            set2.setCircleColor(Color.BLUE);
            set2.setLineWidth(5f);
            set2.setCircleHoleRadius(5f);
            set2.setDrawCircleHole(false);
            set2.setValueTextSize(10f);
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            set2.setFillColor(Color.BLUE);

            ArrayList<ILineDataSet> dataSetsMinBp = new ArrayList<>();
            dataSetsMinBp.add(set2);
            LineData lineDataMinBp = new LineData(dataSetsMinBp);
            mChart.setData(lineDataMinBp);
        }
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
