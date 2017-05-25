package com.example.app_dev.healthcare.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.adapter.medicalSurveyAdapter;
import com.example.app_dev.healthcare.adapter.surveyAdapter;
import com.example.app_dev.healthcare.item.SurveyViewItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BasicSurvey2 extends AppCompatActivity {

    private ImageButton detailSurveyBtn, writeDoneBtn;
    private TextView envContent, medicalContent, tendContent;
    int lightColor = Color.rgb(156, 198, 233);
    int deepColor = Color.rgb(54, 132, 199);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_survey2);

        ListView listView = (ListView) findViewById(R.id.surveyList);
        ListView tendListView = (ListView) findViewById(R.id.tendsurveyList);
        ListView medListView = (ListView) findViewById(R.id.medicalSurveyList);
        detailSurveyBtn = (ImageButton) findViewById(R.id.detail_survey_btn);
        writeDoneBtn = (ImageButton) findViewById(R.id.write_done_btn);
        envContent = (TextView) findViewById(R.id.user_environment_content);
        medicalContent = (TextView) findViewById(R.id.user_medical_history_content);
        tendContent = (TextView) findViewById(R.id.user_tendency_content);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setElevation(1); // degree of action bar bottom shadow

        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // for showing custom action bar
        actionbar.setCustomView(R.layout.activity_custom_action_bar); // apply defined custom action bar
        TextView actionBarText = (TextView) findViewById(R.id.action_bar_text);
        actionBarText.setText("기초설문");
        actionbar.getCustomView().findViewById(R.id.action_bar_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //change the particular text size and color using SpannableStringBuilder
        SpannableStringBuilder builderEnv = new SpannableStringBuilder(envContent.getText());
        SpannableStringBuilder builderMedi = new SpannableStringBuilder(medicalContent.getText());
        SpannableStringBuilder builderTend = new SpannableStringBuilder(tendContent.getText());
        builderEnv.setSpan(new ForegroundColorSpan(lightColor), 8, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderEnv.setSpan(new ForegroundColorSpan(deepColor), 22, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderMedi.setSpan(new ForegroundColorSpan(deepColor), 43, 52, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderTend.setSpan(new ForegroundColorSpan(lightColor), 8, 19, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builderTend.setSpan(new ForegroundColorSpan(deepColor), 22, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        envContent.setText(builderEnv);
        medicalContent.setText(builderMedi);
        tendContent.setText(builderTend);

        ArrayList<SurveyViewItem> lv = new ArrayList<>();
        ArrayList<SurveyViewItem> tlv = new ArrayList<>();
        ArrayList<SurveyViewItem> mlv = new ArrayList<>();

        final surveyAdapter adapter = new surveyAdapter(getApplicationContext(), R.layout.survey_basic_item, lv);
        final surveyAdapter tendAdapter = new surveyAdapter(getApplicationContext(), R.layout.survey_basic_tendency_item, tlv);
        final medicalSurveyAdapter medAdapter = new medicalSurveyAdapter(getApplicationContext(), R.layout.survey_basic_medical_history_item, mlv);

        listView.setAdapter(adapter);
        tendListView.setAdapter(tendAdapter);
        medListView.setAdapter(medAdapter);

        lv.add(new SurveyViewItem("1. 주변 밝기"));
        lv.add(new SurveyViewItem("2. 주변 소리"));
        lv.add(new SurveyViewItem("3. 주변 미세먼지"));

        mlv.add(new SurveyViewItem("1. 비만"));
        mlv.add(new SurveyViewItem("2. 당뇨"));
        mlv.add(new SurveyViewItem("3. 심장질환"));
        mlv.add(new SurveyViewItem("4. 고지혈증"));
        mlv.add(new SurveyViewItem("5. 암"));
        mlv.add(new SurveyViewItem("6. 흡연중독"));
        mlv.add(new SurveyViewItem("7. 알콜중독"));
        mlv.add(new SurveyViewItem("8. 소화불량"));
        mlv.add(new SurveyViewItem("9. 우울증"));
        mlv.add(new SurveyViewItem("10. 불면증"));
        mlv.add(new SurveyViewItem("11. 감기"));
        mlv.add(new SurveyViewItem("12. 알러지"));
        mlv.add(new SurveyViewItem("13. 신경과민증"));
        mlv.add(new SurveyViewItem("14. 고혈압"));
        mlv.add(new SurveyViewItem("15. 혈당"));


        tlv.add(new SurveyViewItem("1. 스트레스 성향"));
        tlv.add(new SurveyViewItem("2. 우울 성향"));
        tlv.add(new SurveyViewItem("3. 분노 성향"));
        tlv.add(new SurveyViewItem("4. 피로감"));

        detailSurveyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "상세설문", Toast.LENGTH_SHORT).show();
            }
        });

        writeDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "작성완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
