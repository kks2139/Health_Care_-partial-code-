package com.example.app_dev.healthcare.utils;

import android.util.Log;
import java.util.ArrayList;

import com.example.app_dev.healthcare.item.SurveyViewItem;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by cjy11 on 2017-02-21.
 */

public class JSONCall {

    JSONObject json;

    public JSONCall(String result){
        json = JSONUtil.getJsObject(result);
    }

    public JSONObject jsonResult(){
        return json;
    }

    public String[] jsonCallSurvey(){

        JSONArray jsonArray = JSONUtil.getJsArray(json, "rsList");
        String[] survey = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonRealObj = JSONUtil.getJsObject(jsonArray, i);
            survey[i] = JSONUtil.getJsString(jsonRealObj, "SURVEY", "null");
        }
        return survey;
    }

    public ArrayList<String> getBasicSurveyBodyInfo(){

        ArrayList<String> bodyInfo = new ArrayList<>();
        JSONObject obj = JSONUtil.getJsObject(json, "rsMapLastestDatas");

        bodyInfo.add(JSONUtil.getJsString(obj, "WAIST", "null"));
        bodyInfo.add(JSONUtil.getJsString(obj, "HIGHBLOODPR", "null"));
        bodyInfo.add(JSONUtil.getJsString(obj, "LOWBLOODPR", "null"));
        bodyInfo.add(JSONUtil.getJsString(obj, "AGLU", "null"));
        bodyInfo.add(JSONUtil.getJsString(obj, "TG", "null"));
        bodyInfo.add(JSONUtil.getJsString(obj, "HDLC", "null"));

        return bodyInfo;
    }

    public ArrayList<String> getBasicSurveyProfile(){

        ArrayList<String> profile = new ArrayList<>();
        JSONObject obj = JSONUtil.getJsObject(json, "rsMapProfile");

        profile.add(JSONUtil.getJsString(obj, "JOBCATEGORY", "null"));
        profile.add(JSONUtil.getJsString(obj, "MARRIAGE", "null"));
        profile.add(JSONUtil.getJsString(obj, "FAMILY", "null"));
        profile.add(JSONUtil.getJsString(obj, "EDUCATION", "null"));
        profile.add(JSONUtil.getJsString(obj, "JOBSTABILITY", "null"));
        profile.add(JSONUtil.getJsString(obj, "RELIGION", "null"));
        profile.add(JSONUtil.getJsString(obj, "HOUSETYPE", "null"));
        profile.add(JSONUtil.getJsString(obj, "LOCATION", "null"));

        return profile;
    }

}
