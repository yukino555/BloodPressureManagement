package example.com.bloodpressuremanagementuser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    LineChart mChartMaxBp, mChartMinBp;
    DatabaseHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        helper = new DatabaseHelper(GraphActivity.this);

        mChartMaxBp = findViewById(R.id.lineChartMaxBp);
        mChartMinBp = findViewById(R.id.lineChartMinBp);

        // Grid背景色
        mChartMaxBp.setDrawGridBackground(false);
        // no description text
        mChartMaxBp.getDescription().setText("血圧");

        // Grid縦軸を破線　xAxis横軸
        // 最高血圧
        XAxis xMaxBp = mChartMaxBp.getXAxis();
        xMaxBp.setAxisMaximum(30f);
        xMaxBp.setAxisMinimum(0f);
        xMaxBp.enableGridDashedLine(10f, 10f, 0f);
        xMaxBp.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 最低血圧
        XAxis xMinBp = mChartMinBp.getXAxis();
        xMinBp.setAxisMaximum(30f);
        xMinBp.setAxisMinimum(0f);
        xMinBp.enableGridDashedLine(10f, 10f, 0f);
        xMinBp.setPosition(XAxis.XAxisPosition.BOTTOM);

        // yAxis縦軸
        // 最高血圧
        YAxis yMaxBp = mChartMaxBp.getAxisLeft();
        // Y軸最大最小設定
        yMaxBp.setAxisMaximum(150f);
        yMaxBp.setAxisMinimum(50f);
        // Grid横軸を破線(Dashed Line)
        yMaxBp.enableGridDashedLine(10f, 10f, 0f);
        yMaxBp.setDrawZeroLine(true);
        // 最低血圧
        YAxis yMinBp = mChartMinBp.getAxisLeft();
        yMinBp.setAxisMaximum(150f);
        yMinBp.setAxisMinimum(50f);
        yMinBp.enableGridDashedLine(10f, 10f, 0f);
        yMinBp.setDrawZeroLine(true);

        // 右側の目盛り.要らないならfalse
        mChartMaxBp.getAxisRight().setEnabled(false);
        mChartMinBp.getAxisRight().setEnabled(false);

        // add data
        setDataMaxBp();
        setDataMinBp();
        // データをアニメーションで出す。ミリ秒.数値が大きいと遅い
        mChartMaxBp.animateX(1000);
        mChartMinBp.animateX(1000);
        //mChart.invalidate();

        // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private void setDataMaxBp() {
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_date", "_maxBP"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        ArrayList<Entry> valuesMaxBp = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            valuesMaxBp.add(new Entry(i, cursor.getInt(1), null, null));
            cursor.moveToNext();
        }
        cursor.close();

        // 最高血圧の線
        LineDataSet set1;

        if (mChartMaxBp.getData() != null &&
                mChartMaxBp.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) mChartMaxBp.getData().getDataSetByIndex(0);
            set1.setValues(valuesMaxBp);
            mChartMaxBp.getData().notifyDataChanged();
            mChartMaxBp.notifyDataSetChanged();
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
            mChartMaxBp.setData(lineDataMaxBp);
        }
    }

    private void setDataMinBp() {
        db = helper.getReadableDatabase();
        cursor = db.query(
                "_BPtable",
                new String[]{"_date", "_minBP"},
                null,
                null,
                null,
                null,
                null
                );
        cursor.moveToFirst();
        ArrayList<Entry> valuesMinBp = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            valuesMinBp.add(new Entry(i, cursor.getInt(1), null, null));
            cursor.moveToNext();
        }
        cursor.close();

        // 最低血圧の線
        LineDataSet set2;

        if (mChartMinBp.getData() != null &&
                mChartMinBp.getData().getDataSetCount() > 0) {

            set2 = (LineDataSet) mChartMinBp.getData().getDataSetByIndex(0);
            set2.setValues(valuesMinBp);
            mChartMinBp.getData().notifyDataChanged();
            mChartMinBp.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set2 = new LineDataSet(valuesMinBp, "最低血圧");

            set2.setDrawIcons(false);  // true false の違いが判らない
            set2.setColor(Color.BLUE);  // 線の色
            set2.setCircleColor(Color.BLUE);  // データのドットの色
            set2.setLineWidth(5f);  // 線の太さ 1f~
            set2.setCircleRadius(5f);  // データドットの大きさ
            set2.setDrawCircleHole(false);  // データドットを塗りつぶす→false 塗りつぶさない→true
            set2.setValueTextSize(10f);  // データの値を記す。0fで記載なし。floatだから小数点がつく
            set2.setDrawFilled(true);
            set2.setFormLineWidth(1f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set2.setFormSize(15.f);

            set2.setFillColor(Color.BLUE);

            ArrayList<ILineDataSet> dataSetsMinBp = new ArrayList<>();
            dataSetsMinBp.add(set2); // add the datasets

            // create a data object with the datasets
            LineData lineDataMinBp = new LineData(dataSetsMinBp);

            // set data
            mChartMinBp.setData(lineDataMinBp);
        }


    }
}