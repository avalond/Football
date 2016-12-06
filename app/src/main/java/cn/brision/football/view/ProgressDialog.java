package cn.brision.football.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.brision.football.R;


public class ProgressDialog extends Dialog {
    private AnimationDrawable mAnimationDrawable;

    public ProgressDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        setContentView(R.layout.layout_progress_dialog);
        ImageView imageView = (ImageView) findViewById(R.id.iv_loading);
        mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        } else {
            mAnimationDrawable.stop();
        }
    }

    public void setMessage(CharSequence message) {
        TextView txt = (TextView) findViewById(R.id.message);
        if (!TextUtils.isEmpty(message)) {
            txt.setVisibility(View.VISIBLE);
            txt.setText(message);
            txt.invalidate();
        } else {
            txt.setVisibility(View.GONE);
        }
    }
}
