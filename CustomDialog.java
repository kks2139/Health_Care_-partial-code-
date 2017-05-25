package com.example.app_dev.healthcare.item;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;

public class CustomDialog extends Dialog {

    Button ok;
    Button cancel;

    public CustomDialog(Context context) {
        super(context);
        final Context c = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_dialog);

        ok = (Button) findViewById(R.id.dialBtn1);
        cancel = (Button) findViewById(R.id.dialBtn2);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "cancel", Toast.LENGTH_SHORT).show();
                CustomDialog.this.dismiss();
            }
        });
    }
}