package cn.brision.football.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.brision.football.R;


public class CustomProgressDialog extends Dialog {

    public static final int LOADING_SUB = 1;
    public static final int LOADING_UN = 2;
    public static final int SUCCESS = 3;
    public static final int FAIL = 4;
    public static final int DEFEAT = 5;
    public static final int MISSFAIL = 6;
    private CountDownTimerUtil cTimer;
    private boolean contTime = false;

    public CustomProgressDialog(@NonNull Context context) {
        this(context, 0);
    }

    public CustomProgressDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        //判断是否不计时
        if (contTime) {
            cTimer.start();
        }
    }


    public void setStyle(Context context, boolean conttime, boolean cancelable, OnCancelListener cancelListener,
                         int state) {

        contTime = conttime;

        ProgressBar mProgressBar;
        ImageView mIvSuccess;
        ImageView mIvFail;
        TextView mText;

        this.setContentView(R.layout.layout_custom_progressdialog);
        mProgressBar = (ProgressBar) this.findViewById(R.id.progress_icon_loading);
        mIvSuccess = (ImageView) this.findViewById(R.id.progress_icon_success);
        mIvFail = (ImageView) this.findViewById(R.id.progress_icon_fail);
        mText = (TextView) this.findViewById(R.id.progress_loadingmsg);

        switch (state) {
            case LOADING_SUB:
                mProgressBar.setVisibility(View.VISIBLE);
                mIvSuccess.setVisibility(View.GONE);
                mIvFail.setVisibility(View.GONE);
                mText.setText(context.getString(R.string.progress_loading_text1));
                break;
            case LOADING_UN:
                mProgressBar.setVisibility(View.VISIBLE);
                mIvSuccess.setVisibility(View.GONE);
                mIvFail.setVisibility(View.GONE);
                mText.setText(context.getString(R.string.progress_loading_text2));
                break;
            case SUCCESS:
                mProgressBar.setVisibility(View.GONE);
                mIvSuccess.setVisibility(View.VISIBLE);
                mIvFail.setVisibility(View.GONE);
                mText.setText(context.getString(R.string.progress_success_text));
                break;
            case FAIL:
                mProgressBar.setVisibility(View.GONE);
                mIvSuccess.setVisibility(View.GONE);
                mIvFail.setVisibility(View.VISIBLE);
                mText.setText(context.getString(R.string.progress_fail_text));
                break;
            case DEFEAT:
                mProgressBar.setVisibility(View.GONE);
                mIvSuccess.setVisibility(View.GONE);
                mIvFail.setVisibility(View.VISIBLE);
                mText.setText(context.getString(R.string.progress_fail));
                break;
            case MISSFAIL:
                mProgressBar.setVisibility(View.GONE);
                mIvSuccess.setVisibility(View.GONE);
                mIvFail.setVisibility(View.VISIBLE);
                mText.setText(context.getString(R.string.progressmiss_fail));
                break;
            default:
                break;
        }

        this.setCancelable(cancelable);
        this.setOnCancelListener(cancelListener);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        this.getWindow().setAttributes(lp);

        cTimer = new CountDownTimerUtil(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                dismiss();
            }
        };
    }


    @Override
    public void dismiss() {
        if (cTimer != null) {
            cTimer.cancel();
            cTimer = null;
        }
        super.dismiss();
    }
}
