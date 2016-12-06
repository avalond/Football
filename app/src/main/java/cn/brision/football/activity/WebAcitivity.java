package cn.brision.football.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.brision.football.R;

/**
 * Created by brision on 16/9/14.
 */
public class WebAcitivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity_layout);
        showToolbar();
        setToolbarDividerEnable(false);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("URL");
        String title = bundle.getString("TITLE");
        setToolbarTitle(title);
        initWeb(url);
    }

    private void initWeb(String url) {
        WebView webView = (WebView) findViewById(R.id.webview);

        //WebView加载web资源
        webView.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
