package com.example.app_dev.healthcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.item.SurveyViewItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cjy11 on 2017-01-17.
 */

public class surveyAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inf;
    private ArrayList<SurveyViewItem> lv = new ArrayList<SurveyViewItem>();
    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;

    public HashMap<String, String> map = new HashMap<String, String>();

    public surveyAdapter(Context context, int layout, ArrayList<SurveyViewItem> lv) {
        this.context = context;
        this.layout = layout;
        this.lv = lv;
        this.inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lv.size();
    }

    @Override
    public Object getItem(int position) {
        return lv.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inf.inflate(layout, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.surveyQ);
        final RadioButton btn1 = (RadioButton) convertView.findViewById(R.id.num1);
        final RadioButton btn2 = (RadioButton) convertView.findViewById(R.id.num2);
        final RadioButton btn3 = (RadioButton) convertView.findViewById(R.id.num3);
        final RadioButton btn4 = (RadioButton) convertView.findViewById(R.id.num4);
        final RadioButton btn5 = (RadioButton) convertView.findViewById(R.id.num5);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                map.put(String.valueOf(mSelectedPosition), btn1.getText().toString());
                Toast.makeText(context, mSelectedPosition + "번 " + btn1.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        if (mSelectedPosition != position) {
            btn1.setChecked(false);
        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                map.put(String.valueOf(mSelectedPosition), btn2.getText().toString());
                Toast.makeText(context, mSelectedPosition + "번 " + btn2.getText(), Toast.LENGTH_LONG).show();
            }
        });

        if (mSelectedPosition != position) {
            btn2.setChecked(false);
        }
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                map.put(String.valueOf(mSelectedPosition), btn3.getText().toString());
                Toast.makeText(context, mSelectedPosition + "번 " + btn3.getText(), Toast.LENGTH_LONG).show();
            }
        });

        if (mSelectedPosition != position) {
            btn3.setChecked(false);
        }
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                map.put(String.valueOf(mSelectedPosition), btn4.getText().toString());
                Toast.makeText(context, mSelectedPosition + "번 " + btn4.getText(), Toast.LENGTH_LONG).show();

            }
        });

        if (mSelectedPosition != position) {
            btn4.setChecked(false);
        }
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                map.put(String.valueOf(mSelectedPosition), btn5.getText().toString());
                Toast.makeText(context, mSelectedPosition + "번 " + btn5.getText(), Toast.LENGTH_LONG).show();
            }
        });
        if (mSelectedPosition != position) {
            btn5.setChecked(false);
        }

        SurveyViewItem lvi = lv.get(position);
        txtView.setText(lvi.getQuestion());

        return convertView;
    }
}
