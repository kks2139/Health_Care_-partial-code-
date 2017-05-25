package com.example.app_dev.healthcare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.utils.HttpPostSend;
import com.example.app_dev.healthcare.utils.JSONCall;
import com.example.app_dev.healthcare.utils.JSONUtil;
import com.example.app_dev.healthcare.utils.SharedPreferencesUtil;
import com.example.app_dev.healthcare.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class BasicSurvey1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et1, et2, et3, et4, et5, et6, et7;
    Spinner s1, s2, s3, s4, s5, s6, s7, s8;
    ArrayAdapter a1, a2, a3, a4, a5, a6, a7, a8;
    View line1, line2, line3, line4, line5, line6, line7;
    Button nextBtn, backBtn;

    String[] jobCategory = {"직업을 선택해 주세요.", "농/임/어업", "공무원", "교육직", "전문직", "경영직", "사무직", "생산/기술직", "서비스/영업직", "자영업", "프리랜서", "전업주부", "학생", "무직", "기타"};
    String[] marriage = {"결혼 유무를 선택해 주세요.", "미혼", "기혼"};
    String[] family = {"가족 구성원수를 선택해 주세요.", "독거", "2명", "3명", "4명", "5명 이상"};
    String[] education = {"최종 학력을 선택해 주세요.", "고졸이하", "전문대졸업", "대학교졸업", "대학원졸업"};
    String[] jobStability = {"고용 안정성을 선택해 주세요.", "무직", "아르바이트", "비정규직", "정규직", "사업주"};
    String[] religion = {"종교를 선택해 주세요.", "불교", "기독교", "천주교", "무교", "기타"};
    String[] houseType = {"거주 유형을 선택해 주세요.", "아파트", "단독주택", "연립주택", "기타"};
    String[] region = {"거주 지역을 선택해 주세요.","서울 강북구","서울 광진구","서울 노원구","서울 도봉구","서울 동대문구","서울 성동구","서울 중랑구","서울 마포구","서울 서대문구","서울 은평구","서울 용산구","서울 종로구","서울 중구","서울 강남구","서울 강동구","서울 서초구","서울 송파구","서울 강서구","서울 관악구","서울 구로구","서울 금천구","서울 동작구","서울 양천구","서울 영등포구"
            ,"인천 계양구","인천 남구","인천 동구","인천 부평구","인천 서구","인천 중구","인천 강화군","인천 남동구","인천 연수구","인천 웅진군"
            ,"경기 가평군","경기 구리시","경기 남양주시","경기 동두천시","경기 양주시","경기 양평군","경기 연천군","경기 의정부","경기 파주시","경기 포천시","경기 광명","경기 광주시","경기 군포","경기 수원시","경기 시흥","경기 안산","경기 안양","경기 의왕","경기 하남시","경기 화성시","경기 고양시","경기 과천","경기 김포시","경기 부천","경기 성남","경기 안성시","경기 여주군","경기 오산시","경기 용인시","경기 이천시","경기 평택시","해당목록없음"};

    String lineDefault = "#efefef";
    String lineFocused = "#3ccd91";

    Context mContext;

    ArrayList<String> bodyInfo;
    ArrayList<String> profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // prevent screen rotation
        setContentView(R.layout.activity_basic_survey1);

        getSupportActionBar().setElevation(1); // degree of action bar bottom shadow
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // for showing custom action bar
        getSupportActionBar().setCustomView(R.layout.activity_custom_action_bar); // apply defined custom action bar
        TextView actionBarText = (TextView) findViewById(R.id.action_bar_text);
        actionBarText.setText("기초설문");

        mContext = this;

        bodyInfo = new ArrayList<>();
        profile = new ArrayList<>();


        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);

        line1 = (View) findViewById(R.id.line1);
        line2 = (View) findViewById(R.id.line2);
        line3 = (View) findViewById(R.id.line3);
        line4 = (View) findViewById(R.id.line4);
        line5 = (View) findViewById(R.id.line5);
        line6 = (View) findViewById(R.id.line6);
        line7 = (View) findViewById(R.id.line7);


        new TEST_SERVER().execute("21"); // call AsyncTask to get JSON object from the server


        // To change line color for each editText when they are focused //
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line1.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line1.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line2.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line2.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line3.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line3.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line4.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line4.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line5.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line5.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line6.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line6.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });
        et7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line7.setBackgroundColor(Color.parseColor(lineFocused));
                } else {
                    line7.setBackgroundColor(Color.parseColor(lineDefault));
                }
            }
        });


        // spinner settings //
        s1 = (Spinner) findViewById(R.id.s1);
        s1.setOnItemSelectedListener(this);
        s2 = (Spinner) findViewById(R.id.s2);
        s2.setOnItemSelectedListener(this);
        s3 = (Spinner) findViewById(R.id.s3);
        s3.setOnItemSelectedListener(this);
        s4 = (Spinner) findViewById(R.id.s4);
        s4.setOnItemSelectedListener(this);
        s5 = (Spinner) findViewById(R.id.s5);
        s5.setOnItemSelectedListener(this);
        s6 = (Spinner) findViewById(R.id.s6);
        s6.setOnItemSelectedListener(this);
        s7 = (Spinner) findViewById(R.id.s7);
        s7.setOnItemSelectedListener(this);
        s8 = (Spinner) findViewById(R.id.s8);
        s8.setOnItemSelectedListener(this);

        a1 = new ArrayAdapter(this, R.layout.spinner1, jobCategory);
        a1.setDropDownViewResource(R.layout.spinner_dropdown);
        a2 = new ArrayAdapter(this, R.layout.spinner2, marriage);
        a2.setDropDownViewResource(R.layout.spinner_dropdown);
        a3 = new ArrayAdapter(this, R.layout.spinner3, family);
        a3.setDropDownViewResource(R.layout.spinner_dropdown);
        a4 = new ArrayAdapter(this, R.layout.spinner4, education);
        a4.setDropDownViewResource(R.layout.spinner_dropdown);
        a5 = new ArrayAdapter(this, R.layout.spinner5, jobStability);
        a5.setDropDownViewResource(R.layout.spinner_dropdown);
        a6 = new ArrayAdapter(this, R.layout.spinner6, religion);
        a6.setDropDownViewResource(R.layout.spinner_dropdown);
        a7 = new ArrayAdapter(this, R.layout.spinner7, houseType);
        a7.setDropDownViewResource(R.layout.spinner_dropdown);
        a8 = new ArrayAdapter(this, R.layout.spinner8, region);
        a8.setDropDownViewResource(R.layout.spinner_dropdown);

        s1.setAdapter(a1);
        s2.setAdapter(a2);
        s3.setAdapter(a3);
        s4.setAdapter(a4);
        s5.setAdapter(a5);
        s6.setAdapter(a6);
        s7.setAdapter(a7);
        s8.setAdapter(a8);

        int location = SharedPreferencesUtil.getValue(this,SharedPreferencesUtil.LAST_LIVING_LOCATION, -1);
        if(location != -1){
            s8.setSelection(location);
        }

        nextBtn = (Button) findViewById(R.id.btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BasicSurvey1.this, "다음", Toast.LENGTH_SHORT).show();

                new TEST_SERVER().execute("21");

                Intent intent = new Intent(BasicSurvey1.this, BasicSurvey2.class);
                intent.putExtra("key", "value");
                startActivity(intent);
            }
        });

        backBtn = (Button) findViewById(R.id.action_bar_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } // onCreate

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        // To apply gray text at the first item in each spinner //
        TextView t1 = (TextView) findViewById(R.id.spinner_style1);
        TextView t2 = (TextView) findViewById(R.id.spinner_style2);
        TextView t3 = (TextView) findViewById(R.id.spinner_style3);
        TextView t4 = (TextView) findViewById(R.id.spinner_style4);
        TextView t5 = (TextView) findViewById(R.id.spinner_style5);
        TextView t6 = (TextView) findViewById(R.id.spinner_style6);
        TextView t7 = (TextView) findViewById(R.id.spinner_style7);
        TextView t8 = (TextView) findViewById(R.id.spinner_style8);

        if (parent.getId() == R.id.s1) {
            if (position == 0) {
                t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t1.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t1.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s2) {
            if (position == 0) {
                t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t2.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t2.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s3) {
            if (position == 0) {
                t3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t3.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t3.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s4) {
            if (position == 0) {
                t4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t4.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t4.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s5) {
            if (position == 0) {
                t5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t5.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t5.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s6) {
            if (position == 0) {
                t6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t6.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t6.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s7) {
            if (position == 0) {
                t7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t7.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t7.setTextColor(Color.parseColor("#6f6f6f"));
            }
        } else if (parent.getId() == R.id.s8) {
            if (position == 0) {
                t8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                t8.setTextColor(Color.parseColor("#bfbfbf"));
            } else {
                t8.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                t8.setTextColor(Color.parseColor("#6f6f6f"));
            }
            SharedPreferencesUtil.put(this,SharedPreferencesUtil.LAST_LIVING_LOCATION, position);
            //Toast.makeText(this,position+" "+s8.getSelectedItem(),Toast.LENGTH_SHORT).show();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) { }


    private class TEST_SERVER extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = HttpPostSend.executeTest(params[0]);
            Log.d("zxc"," "+HttpPostSend.executeSaveTest(params[0]));

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                JSONCall jsonCall = new JSONCall(result);
                bodyInfo = jsonCall.getBasicSurveyBodyInfo();
                profile = jsonCall.getBasicSurveyProfile();

                //Toast.makeText(mContext,"",Toast.LENGTH_LONG).show();
                Log.d("asd"," "+bodyInfo);
                Log.d("qwe"," "+profile);
                Log.d("www"," "+jsonCall.jsonResult());


                Log.i("healthmax_test", "S !!!");
//                try {
//                    String loginResult = json.getString("s_type");
//                    if (loginResult.equals("S")) {
//                        Log.i("HealthUp", "get main info success");
//                    } else {
//                        Log.i("HealthUp", "send main info fail");
//                        Toast.makeText(getBaseContext(), "get main info fail", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                Log.e("healthmax_test", "F !!!");
            }
        }
    }

}
