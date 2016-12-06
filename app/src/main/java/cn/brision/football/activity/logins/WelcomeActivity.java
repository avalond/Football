package cn.brision.football.activity.logins;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.activity.MainActivity;
import cn.brision.football.utils.PreferencesManager;

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";
    private Handler mHandler;
    private static final int DEFAULT_TIME_OUT = 2000;
    public static final String IS_FIRST_OPEN = "is_first_open";
    private PreferencesManager preferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_layout);
        init();
        goNextActivity(DEFAULT_TIME_OUT);
    }

    private void init(){
        hideToolbar();
        mHandler = new Handler(getMainLooper());
        preferencesManager = PreferencesManager.getInstance(this);
    }


    private void goNextActivity(int delay) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferencesManager.get(IS_FIRST_OPEN,false)){
                    Intent i = new Intent(WelcomeActivity.this, WelcomeActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, delay);
    }
}
