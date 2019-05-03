package org.geometerplus.android.fbreader.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.geometerplus.zlibrary.ui.android.R;

public class FBLoadingDialog extends Dialog {

    private ImageView ivLoading;
    private TextView tvMessage;
    private CharSequence mMessage;

    private AnimationDrawable animationDrawable;

    public FBLoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.transparent);
            window.setDimAmount(0f);
        }

        setContentView(R.layout.fb_loading_dialog);

        ivLoading = findViewById(R.id.ivLoading);
        tvMessage = findViewById(R.id.tvMessage);

        animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public static FBLoadingDialog show(Context context, CharSequence title,
                                       CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static FBLoadingDialog show(Context context, CharSequence title,
                                       CharSequence message, boolean indeterminate,
                                       boolean cancelable, OnCancelListener cancelListener) {
        FBLoadingDialog dialog = new FBLoadingDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    public void show() {
        super.show();
        tvMessage.setText(mMessage);
        animationDrawable.start();
    }

    @Override
    public void dismiss() {
        animationDrawable.stop();
        super.dismiss();
    }

    @Override
    public void onDetachedFromWindow() {
        animationDrawable.stop();
        super.onDetachedFromWindow();
    }
}
