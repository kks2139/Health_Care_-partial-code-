package com.example.app_dev.healthcare.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class SurveyTotalEval extends AppCompatActivity implements OnChartValueSelectedListener {

    private HorizontalBarChart mbarChart;
    private LineChart mlnChart;
    private ScrollView scrollView;
    private TextView BMIIndex, BMIResult, strNumSel, ftNumSel, slNumSel, dpNumSel;
    private ImageButton evalStressBtn, evalDepressBtn, evalFatigueBtn, evalSleepBtn, mainBtn, adviceBtn;
    int point = 7;
    int dpCol = Color.rgb(128, 103, 233);
    int ftCol = Color.rgb(156, 209, 150);
    int stCol = Color.rgb(255, 122, 141);
    int slCol = Color.rgb(255, 215, 91);
    int chartCol = Color.rgb(162, 162, 162);
    int BMIIndexCol = Color.rgb(71, 71, 71);
    int diagSelCol = Color.rgb(91, 91, 91);
    int size = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_total_eval);

        evalStressBtn = (ImageButton) findViewById(R.id.evalTotal_stress_btn);
        evalDepressBtn = (ImageButton) findViewById(R.id.evalTotal_depress_btn);
        evalFatigueBtn = (ImageButton) findViewById(R.id.evalTotal_fatigue_btn);
        evalSleepBtn = (ImageButton) findViewById(R.id.evalTotal_sleep_btn);
        mainBtn = (ImageButton) findViewById(R.id.evalTotal_main_btn);
        adviceBtn = (ImageButton) findViewById(R.id.evalTotal_advice_btn);
        strNumSel = (TextView) findViewById(R.id.stress_value_content);
        ftNumSel = (TextView) findViewById(R.id.fatigue_value_content);
        slNumSel = (TextView) findViewById(R.id.sleep_value_content);
        dpNumSel = (TextView) findViewById(R.id.depress_value_content);
        BMIIndex = (TextView) findViewById(R.id.BMI2);
        BMIResult = (TextView) findViewById(R.id.BMI3);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setElevation(1); // degree of action bar bottom shadow
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // for showing custom action bar
        actionbar.setCustomView(R.layout.activity_custom_action_bar); // apply defined custom action bar
        TextView actionBarText = (TextView) findViewById(R.id.action_bar_text);
        actionBarText.setText("종합평가");
        actionbar.getCustomView().findViewById(R.id.action_bar_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //change the particular text size and color using SpannableStringBuilder
        SpannableStringBuilder builderIndex = new SpannableStringBuilder(BMIIndex.getText());
        //SpannableStringBuilder builderResult = new SpannableStringBuilder(BMIResult.getText());
        SpannableStringBuilder builderDiagnostr = new SpannableStringBuilder(strNumSel.getText());
        SpannableStringBuilder builderDiagnodp = new SpannableStringBuilder(dpNumSel.getText());
        SpannableStringBuilder builderDiagnoft = new SpannableStringBuilder(ftNumSel.getText());
        SpannableStringBuilder builderDiagnosl = new SpannableStringBuilder(slNumSel.getText());
        builderIndex.setSpan(new ForegroundColorSpan(BMIIndexCol), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderIndex.setSpan(new AbsoluteSizeSpan(size), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagnostr.setSpan(new ForegroundColorSpan(diagSelCol), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagnodp.setSpan(new ForegroundColorSpan(diagSelCol), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagnosl.setSpan(new ForegroundColorSpan(diagSelCol), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagnoft.setSpan(new ForegroundColorSpan(diagSelCol), 10, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //builderResult.setSpan(new ForegroundColorSpan(BMIResultColor), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        BMIIndex.setText(builderIndex);
        strNumSel.setText(builderDiagnostr);
        dpNumSel.setText(builderDiagnodp);
        ftNumSel.setText(builderDiagnoft);
        slNumSel.setText(builderDiagnosl);
        //BMIResult.setText(builderResult);

        evalStressBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                evalStressBtn.setSelected(!evalStressBtn.isSelected());
                Toast.makeText(getApplicationContext(), "스트레스진단", Toast.LENGTH_SHORT).show();
            }
        });
        evalDepressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evalDepressBtn.setSelected(!evalDepressBtn.isSelected());
                Toast.makeText(getApplicationContext(), "우울증진단", Toast.LENGTH_SHORT).show();
            }
        });
        evalFatigueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evalFatigueBtn.setSelected(!evalFatigueBtn.isSelected());
                Toast.makeText(getApplicationContext(), "피로감진단", Toast.LENGTH_SHORT).show();
            }
        });
        evalSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evalSleepBtn.setSelected(!evalSleepBtn.isSelected());
                Toast.makeText(getApplicationContext(), "수면상태진단", Toast.LENGTH_SHORT).show();
            }
        });

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyTotalEval.this, HealthCareMainActivity.class);
                startActivity(intent);
            }
        });

        adviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyTotalEval.this, Advice.class);
                startActivity(intent);
            }
        });

        //horizontal chart
        mbarChart = (HorizontalBarChart) findViewById(R.id.barchart);

        mbarChart.setOnChartValueSelectedListener(this);
        mbarChart.setDrawBarShadow(false);
        mbarChart.setDrawValueAboveBar(true);
        mbarChart.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // add data
        mbarChart.setMaxVisibleValueCount(100);
        // enable scaling and dragging
        mbarChart.setPinchZoom(false);
        mbarChart.setScaleEnabled(false);
        // enable touch gestures
        mbarChart.setTouchEnabled(false);
        // set an alternative background color
        // draw shadows for each bar that show the maximum value
        mbarChart.setDrawGridBackground(false);

        XAxis xl = mbarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setTextColor(chartCol);
        xl.setGranularity(10f);

        YAxis yl = mbarChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setTextColor(chartCol);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis yr = mbarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setTextColor(chartCol);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        setDataH(4, 10);
        mbarChart.setFitBars(true);

        // get the legend (only possible after setting data)
        Legend h = mbarChart.getLegend();
        // modify the legend
        //hide label name
        h.setEnabled(false);


        //line chart
        mlnChart = (LineChart) findViewById(R.id.linechart);

        mlnChart.setOnChartValueSelectedListener(this);
        // no description text
        mlnChart.getDescription().setEnabled(false);
        // enable touch gestures
        //mlnChart.setTouchEnabled(true);
        mlnChart.setDragDecelerationFrictionCoef(10f);

        // enable scaling and dragging
        mlnChart.setDragEnabled(false);
        mlnChart.setScaleEnabled(false);
        mlnChart.setDrawGridBackground(false);
        mlnChart.setHighlightPerDragEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mlnChart.setPinchZoom(false);
        mlnChart.setDragEnabled(true);

        // set an alternative background color
        mlnChart.setBackgroundColor(Color.rgb(254, 254, 254));
        mlnChart.setGridBackgroundColor(Color.rgb(254, 254, 254));

        // add data
        setData(point);
        mlnChart.animateX(0);

        // get the legend (only possible after setting data)
        Legend l = mlnChart.getLegend();
        // modify the legend
        //hide label name
        l.setEnabled(false);

        XAxis xAxis = mlnChart.getXAxis();
        xAxis.setTextSize(10f);
        xAxis.setTextColor(chartCol);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mlnChart.getAxisLeft();
        leftAxis.setTextColor(chartCol);
        leftAxis.setTextSize(10f);
        leftAxis.setAxisMaximum(6f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGridColor(chartCol);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = mlnChart.getAxisRight();
        rightAxis.setTextColor(chartCol);
        rightAxis.setAxisMaximum(0);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

    }

    //set horizontal bar chart data
    private void setDataH(int count, float range) {

        float barWidth = 5f;
        final float spaceForBar = 10f;//space between bar datas
        int value[] = new int[]{5, 7, 10, 3};
        final String[] diagnosis = new String[]{"스트레스", "우울증", "피로감", "수면상태"};
        final String[] yLeft = new String[]{"Good", "", "", "", "", "", "", "", "", "", "Bad"};
        final String[] yRight = new String[]{"", "", "낮음", "", "", "", "보통", "", "", "", "높음"};

        final ArrayList<String> xLabels = new ArrayList<>();
        for (int i = 0; i < diagnosis.length; i++) {
            xLabels.add(diagnosis[i]);
        }

        final ArrayList<String> yLeftLabels = new ArrayList<>();
        for (int i = 0; i < yLeft.length; i++) {
            yLeftLabels.add(yLeft[i]);
        }

        final ArrayList<String> yRightLabels = new ArrayList<>();
        for (int i = 0; i < yRight.length; i++) {
            yRightLabels.add(yRight[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals1.add(new BarEntry(i * spaceForBar, value[i]));
        }
        BarDataSet set1;

        if (mbarChart.getData() != null &&
                mbarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mbarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mbarChart.getData().notifyDataChanged();
            mbarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "사용자 웰빙지수");
            set1.setColor(dpCol);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            //convert the x-axis integer value to string
            IAxisValueFormatter formX = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    value = value / spaceForBar;
                    if (value < 0 || value >= xLabels.size()) {
                        return "";
                    } else {
                        return xLabels.get((int) value);
                    }
                }
            };

            //convert the y-axis(left) integer value to string
            IAxisValueFormatter formYLeft = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if (value < 0 || value >= yLeftLabels.size()) {
                        return "";
                    } else {
                        return yLeftLabels.get((int) value);
                    }
                }
            };

            //convert the y-axis(right) integer value to string
            IAxisValueFormatter formYRight = new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if (value < 0 || value >= yRightLabels.size()) {
                        return "";
                    } else {
                        return yRightLabels.get((int) value);
                    }
                }
            };

            mbarChart.getXAxis().setValueFormatter(formX);
            mbarChart.getAxisLeft().setValueFormatter(formYLeft);
            mbarChart.getAxisRight().setValueFormatter(formYRight);

            BarData data = new BarData(dataSets);

            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);

            mbarChart.setData(data);
            mbarChart.invalidate();
        }
    }

    //set line chart data
    private void setData(int count) {

        LineDataSet stress, depress, fatigue, sleep;
        int valueSt[] = new int[]{4, 5, 4, 4, 3, 3, 4};
        int valueDp[] = new int[]{3, 3, 5, 5, 4, 5, 4};
        int valueFt[] = new int[]{2, 1, 3, 3, 1, 4, 5};
        int valueSl[] = new int[]{1, 2, 2, 1, 0, 1, 0};

        final String[] dates = new String[]{"01.01", "01.02", "01.03", "01.04", "01.05", "01.06", "01.07"};

        ArrayList<Entry> stVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            stVals.add(new Entry(i, valueSt[i]));
        }

        ArrayList<Entry> dpVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            dpVals.add(new Entry(i, valueDp[i]));
        }

        ArrayList<Entry> ftVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ftVals.add(new Entry(i, valueFt[i]));
        }

        ArrayList<Entry> slVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            slVals.add(new Entry(i, valueSl[i]));
        }

        if (mlnChart.getData() != null &&
                mlnChart.getData().getDataSetCount() > 0) {
            stress = (LineDataSet) mlnChart.getData().getDataSetByIndex(0);
            depress = (LineDataSet) mlnChart.getData().getDataSetByIndex(1);
            fatigue = (LineDataSet) mlnChart.getData().getDataSetByIndex(2);
            sleep = (LineDataSet) mlnChart.getData().getDataSetByIndex(3);

            stress.setValues(stVals);
            depress.setValues(dpVals);
            fatigue.setValues(ftVals);
            sleep.setValues(slVals);

            mlnChart.getData().notifyDataChanged();
            mlnChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            stress = new LineDataSet(stVals, "스트레스 지수");

            stress.setAxisDependency(YAxis.AxisDependency.LEFT);
            stress.setColor(stCol);
            stress.setCircleColors(stCol);
            stress.setLineWidth(2f);
            stress.setCircleRadius(5f);
            stress.setFillAlpha(20);
            stress.setCubicIntensity(0.2f);
            stress.setDrawFilled(true);
            stress.setFillColor(stCol);
            stress.setHighlightLineWidth(0f);
            stress.setHighLightColor(stCol);
            stress.setDrawCircleHole(true);
            stress.setDrawValues(false);

            // create a dataset and give it a type
            depress = new LineDataSet(dpVals, "우울감 지수");

            depress.setAxisDependency(YAxis.AxisDependency.LEFT);
            depress.setColor(dpCol);
            depress.setCircleColors(dpCol);
            depress.setLineWidth(2f);
            depress.setCircleRadius(5f);
            depress.setFillAlpha(20);
            depress.setCubicIntensity(0.2f);
            depress.setDrawFilled(true);
            depress.setFillColor(dpCol);
            depress.setHighlightLineWidth(0f);
            depress.setHighLightColor(dpCol);
            depress.setDrawCircleHole(true);
            depress.setDrawValues(false);

            // create a dataset and give it a type
            fatigue = new LineDataSet(ftVals, "피로감 지수");

            fatigue.setAxisDependency(YAxis.AxisDependency.LEFT);
            fatigue.setColor(ftCol);
            fatigue.setCircleColors(ftCol);
            fatigue.setLineWidth(2f);
            fatigue.setCircleRadius(5f);
            fatigue.setFillAlpha(20);
            fatigue.setCubicIntensity(0.2f);
            fatigue.setDrawFilled(true);
            fatigue.setFillColor(ftCol);
            fatigue.setHighlightLineWidth(0f);
            fatigue.setHighLightColor(ftCol);
            fatigue.setDrawCircleHole(true);
            fatigue.setDrawValues(false);

            // create a dataset and give it a type
            sleep = new LineDataSet(slVals, "수면상태 지수");

            sleep.setAxisDependency(YAxis.AxisDependency.LEFT);
            sleep.setColor(slCol);
            sleep.setCircleColors(slCol);
            sleep.setLineWidth(2f);
            sleep.setCircleRadius(5f);
            sleep.setFillAlpha(20);
            sleep.setCubicIntensity(0.2f);
            sleep.setDrawFilled(true);
            sleep.setFillColor(slCol);
            sleep.setHighlightLineWidth(0f);
            sleep.setHighLightColor(slCol);
            sleep.setDrawCircleHole(true);
            sleep.setDrawValues(false);

            ArrayList<LineDataSet> dataSets = new ArrayList<>();


            //convert the x-axis integer value to string
            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return dates[(int) value];
                }
            };

            XAxis xAxis = mlnChart.getXAxis();
            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setValueFormatter(formatter);

            LineData data = new LineData(stress, depress, fatigue, sleep);

            // set data
            mlnChart.setData(data);
            mlnChart.invalidate();
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        RectF bounds = mOnValueSelectedRectF;
        //mbarChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mbarChart.getPosition(e, mbarChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);

        Log.i("Entry selected", e.toString());

        mlnChart.centerViewToAnimated(e.getX(), e.getY(), mlnChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        Toast.makeText(getApplicationContext(), e.getX() + "," + e.getY(), Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

}
