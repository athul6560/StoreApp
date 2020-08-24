package com.azstudio.storeapp.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.azstudio.storeapp.R;

public class ProgressBar extends Dialog {
    Context context;

    public ProgressBar(Context context) {
        super(context);
    }

    public ProgressBar(Context context, int theme) {
        super(context, theme);
    }


    public void onWindowFocusChanged(boolean hasFocus) {

    }

    public static ProgressBar show(Context context, String colourcode, String message) {
        final ProgressBar dialog = new ProgressBar(context, R.style.ProgressHUD);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        android.widget.ProgressBar pb = (android.widget.ProgressBar) dialog.findViewById(R.id.progressBar2);
        pb.getIndeterminateDrawable().setColorFilter(Color.parseColor(colourcode), android.graphics.PorterDuff.Mode.MULTIPLY);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(View.GONE);
        } else {
            TextView txt = (TextView) dialog.findViewById(R.id.message);
            txt.setText(message);
        }
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        if (context != null) {
            dialog.show();
        }
        return dialog;
    }
}
