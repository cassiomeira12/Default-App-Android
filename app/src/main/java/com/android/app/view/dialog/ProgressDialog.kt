package com.android.app.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView


class ProgressDialog(context: Context?) : AlertDialog(context) {
    var text = "Carregando..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(8, 16, 0, 16)
        layout.gravity = Gravity.CENTER

        var textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.textSize = 16f
        textView.text = text

        layout.addView(ProgressBar(context))
        layout.addView(textView)

        setContentView(layout)

        setCanceledOnTouchOutside(false)
        setOnKeyListener { dialog, keyCode, event ->
            true
        }


        /*

        ProgressBar progressBar = new ProgressBar(getContext());

        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setText(text);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(8,16,0,16);

        linearLayout.addView(progressBar);
        linearLayout.addView(textView);

        linearLayout.setGravity(Gravity.CENTER);

        setContentView(linearLayout);

        setCanceledOnTouchOutside(false);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

        */
    }

}