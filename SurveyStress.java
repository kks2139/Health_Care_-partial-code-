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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.adapter.surveyAdapter;
import com.example.app_dev.healthcare.item.SurveyViewItem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SurveyStress extends AppCompatActivity implements OnChartValueSelectedListener {
    private ScrollView scrollView;
    private LineChart mlnChart;
    private TextView BMIIndex, BMIResult, diagnoContent;
    private ImageButton writeBtn, basicBtn, totalEvalBtn, adviceBtn;
    int point = 7;
    int lightColor = Color.rgb(255, 122, 141);
    int deepColor = Color.rgb(255, 58, 86);
    int size = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_stress);

        ListView listView = (ListView) findViewById(R.id.surveyList);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        ArrayList<SurveyViewItem> lv = new ArrayList<>();
        BMIIndex = (TextView) findViewById(R.id.BMI2);
        BMIResult = (TextView) findViewById(R.id.BMI3);
        diagnoContent = (TextView) findViewById(R.id.diagno_content);
        writeBtn = (ImageButton) findViewById(R.id.writeSurvey);
        basicBtn = (ImageButton) findViewById(R.id.basic);
        totalEvalBtn = (ImageButton) findViewById(R.id.evalTotal);
        adviceBtn = (ImageButton) findViewById(R.id.advice);

        //change the particular text size and color using SpannableStringBuilder
        SpannableStringBuilder builderIndex = new SpannableStringBuilder(BMIIndex.getText());
        SpannableStringBuilder builderResult = new SpannableStringBuilder(BMIResult.getText());
        SpannableStringBuilder builderDiagno = new SpannableStringBuilder(diagnoContent.getText());
        builderIndex.setSpan(new ForegroundColorSpan(deepColor), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderIndex.setSpan(new AbsoluteSizeSpan(size), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderResult.setSpan(new ForegroundColorSpan(deepColor), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagno.setSpan(new ForegroundColorSpan(lightColor), 54, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderDiagno.setSpan(new ForegroundColorSpan(deepColor), 68, 80, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        BMIIndex.setText(builderIndex);
        BMIResult.setText(builderResult);
        diagnoContent.setText(builderDiagno);

        final surveyAdapter adapter = new surveyAdapter(getApplicationContext(), R.layout.survey_stress_item, lv);

        listView.setAdapter(adapter);
        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setElevation(1); // degree of action bar bottom shadow
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // for showing custom action bar
        actionbar.setCustomView(R.layout.activity_custom_action_bar); // apply defined custom action bar
        TextView actionBarText = (TextView) findViewById(R.id.action_bar_text);
        actionBarText.setText("스트레스 진단");
        actionbar.getCustomView().findViewById(R.id.action_bar_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv.add(new SurveyViewItem("Q1. 자신에 대해 무가치하고 창피스럽게 느낀다."));
        lv.add(new SurveyViewItem("Q2. 나의 미래는 어둡다."));
        lv.add(new SurveyViewItem("Q3. 가슴이 답답하다."));
        lv.add(new SurveyViewItem("Q4. 나는 무섭고 거의 공포 상태이다."));
        lv.add(new SurveyViewItem("Q5. 가족이나 친구가 도와주더라도 울적한 기분을 떨칠 수가 없다."));

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeBtn.setSelected(!writeBtn.isSelected());
                JSONObject json = new JSONObject();
                for (int i = 0; i < 5; i++) {
                    if (adapter.map.get(i + "") != null) {
                        try {
                            json.put(String.valueOf(i), adapter.map.get((i + "")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), "j:" + json, Toast.LENGTH_SHORT).show();
            }
        });

        basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyStress.this, BasicSurvey1.class);
                startActivity(intent);
            }
        });

        totalEvalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyStress.this, SurveyTotalEval.class);
                startActivity(intent);
            }
        });

        adviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyStress.this, Advice.class);
                startActivity(intent);
            }
        });


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
        xAxis.setTextColor(Color.rgb(162, 162, 162));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mlnChart.getAxisLeft();
        leftAxis.setTextColor(Color.rgb(162, 162, 162));
        leftAxis.setTextSize(10f);
        leftAxis.setAxisMaximum(6f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGridColor(Color.rgb(162, 162, 162));
        leftAxis.setDrawGridLines(true); //draw YAxis grid line
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = mlnChart.getAxisRight();
        rightAxis.setTextColor(Color.rgb(162, 162, 162));
        rightAxis.setAxisMaximum(0);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

    }


    //set line chart data
    private void setData(int count) {

        LineDataSet set;
        int value[] = new int[]{2, 1, 4, 3, 1, 5, 4};
        final String[] dates = new String[]{"01.01", "01.02", "01.03", "01.04", "01.05", "01.06", "01.07"};

        ArrayList<Entry> yVals1 = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(i, value[i]));
        }

        if (mlnChart.getData() != null &&
                mlnChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mlnChart.getData().getDataSetByIndex(0);
            set.setValues(yVals1);

            mlnChart.getData().notifyDataChanged();
            mlnChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set = new LineDataSet(yVals1, "우울감 지수");

            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(deepColor);
            set.setCircleColor(deepColor);
            set.setLineWidth(2f);
            set.setCircleRadius(5f);
            set.setFillAlpha(20);
            set.setCubicIntensity(0.2f);
            set.setDrawFilled(true);
            set.setFillColor(deepColor);
            set.setHighlightLineWidth(0f);
            set.setHighLightColor((deepColor));
            set.setDrawCircleHole(true);
            set.setDrawValues(false);

            ArrayList<LineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

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

            LineData data = new LineData(set);

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

