package cn.brision.football.activity.logins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.activity.WebAcitivity;
import cn.brision.football.data.LoginAction;
import cn.brision.football.utils.PreferencesManager;

/**
 * Created by brision on 16/9/14.
 *
 */
public class LogoutActivity extends BaseActivity {

    @Bind(R.id.logout)
    TextView logout;

    private LoginAction loginAction;
    private final static String COPYRIGHT_URL ="https://api.brision.cn/PUBLIC/copyright.html";
    private final static String PRIVACY_URL ="https://api.brision.cn/PUBLIC/privacy.html";
    private final static String RESPECT_URL ="file:///android_asset/About.html";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);

        loginAction = dataManger.getLoginAction();

        showToolbar();
        setToolbarTitle(this.getString(R.string.setting));
        setToolbarDividerEnable(false);

        if (!isLoging()) {
            logout.setVisibility(View.GONE);
        } else {
            logout.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLoging() {
        PreferencesManager pm = PreferencesManager.getInstance(this);
        String accessToken = pm.get("Access_token", "");

        return accessToken.length() > 0;
    }

    @OnClick(R.id.logout)
    public void logOut(View view) {
        loginAction.logout(this);
    }

    @OnClick(R.id.copyright)
    public void copyRight(View view) {
        Intent intent = new Intent(this, WebAcitivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL",COPYRIGHT_URL);
        bundle.putString("TITLE",this.getString(R.string.copyright));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.privacy)
    public void privacy(View view) {
        Intent intent = new Intent(this, WebAcitivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL",PRIVACY_URL);
        bundle.putString("TITLE",this.getString(R.string.privacy));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.respect)
    public void respect(View view) {
        Intent intent = new Intent(this, WebAcitivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("URL",RESPECT_URL);
        bundle.putString("TITLE",this.getString(R.string.respect));
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
