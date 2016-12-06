package cn.brision.football.activity;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleableRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bugtags.library.Bugtags;

import butterknife.ButterKnife;
import cn.brision.football.FootBallApplication;
import cn.brision.football.R;
import cn.brision.football.commen.ActivityManager;
import cn.brision.football.data.services.DataManger;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityManager activityManager = ActivityManager.getActivityManager(this);
    public DataManger dataManger;
    private static final int[] ATTRS = {R.attr.windowActionBarOverlay, R.attr.actionBarSize};
    protected View mRootView;
    private LayoutInflater mInflater;
    protected View mContentView;
    protected Toolbar mToolbar;
    private int idDinmes = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        activityManager.putActivity(this);
        dataManger = getApp().getDataManger();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mRootView = initRootView(layoutResID);
        super.setContentView(mRootView);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        mRootView = initRootView(view);
        super.setContentView(mRootView);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mRootView = initRootView(view);
        super.setContentView(mRootView, params);
        ButterKnife.bind(this);
    }

    protected View getRootView() {
        return mRootView;
    }


    /**
     * 隐藏toolbar
     */
    protected void hideToolbar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 显示toolbar
     */
    protected void showToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }

    /**
     * toolbar是否显示
     */
    protected boolean isToolbarShowing() {
        ActionBar actionBar = getSupportActionBar();
        return actionBar != null && actionBar.isShowing();
    }


    /**
     * Toolbar左侧按钮点击事件（默认退出当前页面）
     *
     * @param v view
     */
    protected void onToolbarLeftButtonClicked(View v) {
        finish();
    }

    /**
     * Toolbar右侧按钮点击事件
     *
     * @param v view
     */
    protected void onToolbarRightButtonClicked(View v) {

    }

    /**
     * 设置toolbar标题
     *
     * @param title 标题
     */
    protected void setToolbarTitle(String title) {
        if (mToolbar != null) {
            TextView titleView = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            titleView.setText(title);
        }
    }

    /**
     * 设置toolbar的分割线是否有效， 默认有效。
     *
     * @param enable 分割线是否有效
     */
    protected void setToolbarDividerEnable(boolean enable) {
        if (mToolbar != null) {
            View divider = mToolbar.findViewById(R.id.toolbar_divider_line);
            if (enable) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 创建toolbar
     *
     * @param inflater  inflater
     * @param container container
     * @return 创建toolbar, 如果返回null则不显示toolbar
     */
    protected View createToolbarView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.layout_toolbar, container);
        view.findViewById(R.id.toolbar_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarLeftButtonClicked(v);
            }
        });
        view.findViewById(R.id.toolbar_right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarRightButtonClicked(v);
            }
        });

        return view;
    }

    private View initRootView(View contentView) {
        /*直接创建一个相对布局，作为视图容器的父容器*/
        RelativeLayout rootView = new RelativeLayout(this);
        rootView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setFitsSystemWindows(true);

        /*实际的内容布局*/
        mContentView = contentView;
        RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*通过inflater获取toolbar的布局文件*/
        View toolbarLayout = createToolbarView(mInflater, null);
        if (toolbarLayout != null) {
            TypedArray typedArray = getTheme().obtainStyledAttributes(ATTRS);
            /*获取主题中定义的悬浮标志*/
            boolean overly = typedArray.getBoolean(0, false);
            /*获取主题中定义的toolbar的高度*/
            @StyleableRes int index = 1;

            int toolBarSize = getToolbarHeight();
//            int toolBarSize =  (int) typedArray.getDimension(index, (int) getResources().getDimension(R.dimen.action_bar_default_height))
            typedArray.recycle();

            if (!overly) {
                contentLayoutParams.addRule(RelativeLayout.BELOW, toolbarLayout.getId());
            }
            rootView.addView(mContentView, contentLayoutParams);
            RelativeLayout.LayoutParams toolbarLayoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, toolBarSize);
            rootView.addView(toolbarLayout, toolbarLayoutParams);

            mToolbar = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);
            if (mToolbar != null) {
                setSupportActionBar(mToolbar);
            }
        } else {
            rootView.addView(mContentView, contentLayoutParams);
        }
        return rootView;
    }

    public void setToolbarHeight(int id){
        idDinmes = id;
    }

    private int getToolbarHeight(){
        return idDinmes !=0 ? (int) getResources().getDimension(idDinmes):(int) getResources().getDimension(R.dimen.action_bar_default_height);
    }

    private View initRootView(@LayoutRes int contentViewResID) {
        return initRootView(mInflater.inflate(contentViewResID, null));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void configTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.removeActivity(this);
    }

    public void exit() {
        activityManager.exit();
    }

    /**
     * 通过类名启动Activity
     *
     * @param cls 需要跳转的类
     */
    protected void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    /**
     * 通过类名启动Activity，并且含有Flag标识
     *
     * @param cls
     * @param flag
     */
    protected void openActivity(Class<?> cls, int flag) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 保存数据 以免activity销毁数据丢失
     *
     * @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public FootBallApplication getApp() {
        return (FootBallApplication) this.getApplication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }



}



