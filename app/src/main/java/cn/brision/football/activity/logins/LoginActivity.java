package cn.brision.football.activity.logins;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.commen.NotificationReceiver;
import cn.brision.football.data.LoginAction;
import cn.brision.football.leancloud.MyUser;
import cn.brision.football.model.UserLogin;
import cn.brision.football.utils.Const;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.ToastUtil;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.user_phonenumber)
    EditText phoneNumber;
    @Bind(R.id.auth_code)
    EditText authCode;
    @Bind(R.id.get_auth_code)
    TextView getAuthCode;

    private TimeCount time;//按钮倒计时

    private static final int VERIFY_RIGHT = 100;// 用户提交验证码，验证结果正确
    private static final int VERIFY_WRONG = 101;// 用户提交验证码，验证结果错误

    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "15e4ad9498738";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "035836e679a1a40d8be216f11cb08064";

    private static final int ACTIVITY_RESULT_CODE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        hideToolbar();
        ButterKnife.bind(this);
//        boolean loging = isLoging();
//        if (loging) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            LoginActivity.this.finish();
//        }

        time = new TimeCount(60000, 1000);
        initSMSSDK();
    }

    private void initSMSSDK() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET);

        // EventHandler表示将处理不包含事件数据的方法（短信验证码）
        EventHandler eh = new EventHandler() {
            @Override
            // afterEvent在操作结束时被触发,event为操作的类型,data事件操作的结果，其具体取值根据参数result而定，
            // result是操作结果SMSSDK.RESULT_COMPLETE表示操作成功，SMSSDK.RESULT_ERROR表示操作失败。
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }
        };
        // 短信SDK采用"广播"的方式发送操作数据。每次调用registerEventHandler都会设置一个新的EventHandler到SDK内部，
        // 当事件发生时，这些注册进来的EventHandler都能收到信息而不会发生"后者替换前者"的问题。
        SMSSDK.registerEventHandler(eh);// 注册回调
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(LoginActivity.this);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }


    /**
     * 处理验证码
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Message resultMsg = new Message();

            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    ToastUtil.showToastOnce(getApplicationContext(), "提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    ToastUtil.showToastOnce(getApplication(), "验证码正在发生至你的手机,请稍等。");
                }
            } else {
                int status;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");// 错误描述
                    status = object.optInt("status");// 错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        ToastUtil.showToastOnce(LoginActivity.this, des);
                        time.cancel();
                        getAuthCode.setText("短信验证码");
                        getAuthCode.setEnabled(true);
                        return;
                    }
                    if (status == 468) {
                        ToastUtil.showToastOnce(getApplicationContext(), "提交验证码成功");
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance();
                }
            }
        }
    };

    @OnClick(R.id.get_auth_code)
    public void getAuthCodeClick(View view) {
        if (phoneNumber.getText().toString().isEmpty()) {
            ToastUtil.showToastOnce(this, "请输入手机号码!");
            return;
        }

        SMSSDK.getVerificationCode("86", phoneNumber.getText().toString());
        time.start();
    }

    @OnClick(R.id.login)
    public void loginClick(View view) {
        if (authCode.getText().toString().isEmpty() || phoneNumber.getText().toString().isEmpty()) {
            ToastUtil.showToastOnce(this, "请输入手机号码和验证码后再登录!");
            return;
        }

        LoginAction loginAction = dataManger.getLoginAction();
        loginAction.login(phoneNumber.getText().toString(), authCode.getText().toString(), new LoginAction.LoginDataListener() {
            @Override
            public void loginData(UserLogin mUserLogin) {
                if (mUserLogin != null) {
                    if (mUserLogin.isStatus()) {
                        PreferencesManager pm = PreferencesManager.getInstance(LoginActivity.this);
                        pm.put("Access_token", mUserLogin.getAccess_token());
                        pm.put("user_number", phoneNumber.getText().toString());
                        Log.d("Access_token", mUserLogin.getAccess_token());

                        AVUser.AVThirdPartyUserAuth userAuth = new AVUser.AVThirdPartyUserAuth(mUserLogin.getAccess_token(), null, "brision", mUserLogin.getOpenid());
                        AVUser.loginWithAuthData(MyUser.class, userAuth, new LogInCallback<MyUser>() {
                            @Override
                            public void done(MyUser myUser, AVException e) {
                                if (e == null) {
                                    myUser.setMobilePhoneNumber(phoneNumber.getText().toString());
                                    myUser.saveEventually();
                                    sentLoginStateChangeBroadcast(LoginActivity.this);
                                }
                            }
                        });
                        Intent intent = new Intent();
                        intent.putExtra("isLogin", true);
                        setResult(ACTIVITY_RESULT_CODE, intent);
                        LoginActivity.this.finish();
                    } else {
                        ToastUtil.showToastOnce(LoginActivity.this, "登录失败");
                    }
                }
            }
        });

    }

    @OnClick(R.id.login_back)
    public void backClick(View view) {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        exit();
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 登陆状态变更的广播
     *
     */
    private void sentLoginStateChangeBroadcast(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
//        intent.putExtra(Const.LOGIN_STATE_CHANGE_RESULT, objectId);
        intent.setAction(Const.LOGIN_STATE_CHANGE);
        context.sendBroadcast(intent);   //发送广播
        Intent intent1 = new Intent();
//        intent.putExtra(Const.LOGIN_STATE_CHANGE_RESULT, objectId);
        intent1.setAction(Const.LOGIN_STATE_CHANGE);
        context.sendBroadcast(intent1);   //发送广播
    }


    class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    总时长
         * @param countDownInterval 计时的时间间隔
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            getAuthCode.setEnabled(false);
            getAuthCode.setText("短信验证码(" + l / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            getAuthCode.setText("获取验证码");
            getAuthCode.setEnabled(true);
        }
    }
}
