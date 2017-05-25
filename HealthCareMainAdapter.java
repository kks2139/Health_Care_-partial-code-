package com.example.app_dev.healthcare.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.item.CardViewItem;

import java.util.ArrayList;

/**
 * Created by app_dev on 2017-01-17.
 */

public class HealthCareMainAdapter extends PagerAdapter {

    private Context mContext = null;
    LayoutInflater mInflater;

    private ArrayList<CardViewItem> mArrayList = new ArrayList<CardViewItem>();
    private View.OnClickListener mStatSaveClickListener;
    private View.OnClickListener mCardViewClickListener;

    public HealthCareMainAdapter(Context context) {
        super();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setArrayList(ArrayList<CardViewItem> mArrayList) {
        this.mArrayList = mArrayList;
    }

    public void clear() {
        mArrayList.clear();
    }

    public void setStateSvaeClickListener(View.OnClickListener mStatSaveClickListener){
        this.mStatSaveClickListener = mStatSaveClickListener;
    }
    public void setCardViewClickListener(View.OnClickListener mCardViewClickListener){
        this.mCardViewClickListener = mCardViewClickListener;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
//        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;

        if (v == null) {
            v = mInflater.inflate(R.layout.item_status_card_view, container, false);
        }

        TextView title = (TextView) v.findViewById(R.id.cardview_title_tv);
        TextView type = (TextView) v.findViewById(R.id.cardview_type_tv);
        ImageButton state = (ImageButton) v.findViewById(R.id.cardview_state_ib);
        TextView advice = (TextView) v.findViewById(R.id.cardview_advice_tv);
        TextView advice_hint = (TextView) v.findViewById(R.id.cardview_hint_tv);

        //test
        ImageButton button_stat_save = (ImageButton) v.findViewById(R.id.cardview_set_ib);
        button_stat_save.setOnClickListener(mStatSaveClickListener);
        button_stat_save.setTag(R.id.cardview_set_ib, position);

        CardView cardView = (CardView) v.findViewById(R.id.statue_cv);
        cardView.setOnClickListener(mCardViewClickListener);
        cardView.setTag(R.id.statue_cv, position);

        CardViewItem cvi_item = mArrayList.get(position);
        title.setText(cvi_item.getmCardTitle());
        type.setText(cvi_item.getmCardType());
        advice.setText(cvi_item.getmCardAdvice());
        advice_hint.setText(cvi_item.getmCardAdviceHint());


        setCardColor(v, cvi_item.getmCardType());

        container.addView(v);

        return v;
    }

    private void setCardColor(View v, String type) {

        CardView cardview = (CardView) v.findViewById(R.id.statue_cv);
        switch (type){
            case "Stress":
                cardview.setCardBackgroundColor(Color.parseColor("#f95691"));
                break;

            case "Melancholy":
                cardview.setCardBackgroundColor(Color.parseColor("#0076ba"));
                break;

            case "Fatigue":
                cardview.setCardBackgroundColor(Color.parseColor("#ff8d6a"));
                break;

            case "Sleep":
                cardview.setCardBackgroundColor(Color.parseColor("#6b5dc8"));
                break;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        super.destroyItem(container, position, object);
    }

}
