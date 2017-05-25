package com.example.app_dev.healthcare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.app_dev.healthcare.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;

// 2017.02.07 create colorpicker dialog
public class ColorPickerDialog extends Dialog {
    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;
    private OnColorSelectedListener mColorpickerListener;

//    public ColorPickerDialog(Context context) {
//        super(context);
//    }

    private TextView mColorPickerDialog_Title;
    private ColorPickerView mColorPickerView;
    private Button mColorPickerDialog_Cancel;
    private Button mColorPickerDialog_Confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.custom_colorpicker_dialog);

        mColorPickerDialog_Title = (TextView) findViewById(R.id.colorpicker_dialog_title_tv);
        mColorPickerView = (ColorPickerView) findViewById(R.id.colorpicker_view);
        mColorPickerDialog_Cancel = (Button) findViewById(R.id.colorpicker_dialog_cancel);
        mColorPickerDialog_Confirm = (Button) findViewById(R.id.colorpicker_dialog_confirm);

        mColorPickerView.addOnColorSelectedListener(mColorpickerListener);
        mColorPickerDialog_Confirm.setOnClickListener(mConfirmListener);
        mColorPickerDialog_Cancel.setOnClickListener(mCancelListener);
    }

    public ColorPickerDialog(Context context, OnColorSelectedListener colorPickerListener, View.OnClickListener confirmListener, View.OnClickListener cancellistener) {
        super(context);
        this.mColorpickerListener = colorPickerListener;
        this.mConfirmListener = confirmListener;
        this.mCancelListener = cancellistener;
    }
}
