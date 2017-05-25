package com.example.app_dev.healthcare.item;

/**
 * Created by app_dev on 2017-01-20.
 */

public class CardViewItem {
    private String mCardTitle;
    private String mCardType;
    private String mCardState;
    private String mCardAdvice;
    private String mCardAdviceHint;

    public CardViewItem(){
    }

    public CardViewItem(String title, String type, String state, String advice, String hint){
        mCardTitle = title;
        mCardType = type;
        mCardState = state;
        mCardAdvice = advice;
        mCardAdviceHint = hint;
    }

    public String getmCardTitle() {
        return mCardTitle;
    }

    public void setmCardTitle(String mCardTitle) {
        this.mCardTitle = mCardTitle;
    }

    public String getmCardType() {
        return mCardType;
    }

    public void setmCardType(String mCardType) {
        this.mCardType = mCardType;
    }

    public String getmCardState() {
        return mCardState;
    }

    public void setmCardState(String mCardState) {
        this.mCardState = mCardState;
    }

    public String getmCardAdvice() {
        return mCardAdvice;
    }

    public void setmCardAdvice(String mCardAdvice) {
        this.mCardAdvice = mCardAdvice;
    }

    public String getmCardAdviceHint() {
        return mCardAdviceHint;
    }

    public void setmCardAdviceHint(String mCardAdviceHint) {
        this.mCardAdviceHint = mCardAdviceHint;
    }
}