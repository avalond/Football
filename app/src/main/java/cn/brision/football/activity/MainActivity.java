package cn.brision.football.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.brision.football.R;

import cn.brision.football.fragment.MiddleFragment;
import cn.brision.football.fragment.MyFragment;
import cn.brision.football.fragment.MomentsFragment;
import cn.brision.football.fragment.DataFragment;
import cn.brision.football.fragment.LivesFragment;
import cn.brision.football.utils.PreferencesManager;
import cn.brision.football.utils.ToastUtil;
import cn.brision.football.view.ptr.PtrClassicFrameLayout;


public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_moments_image)
    ImageView momentsImageView;

    @Bind(R.id.tab_data_image)
    ImageView dataImageView;

    @Bind(R.id.tab_lives_image)
    ImageView livesImageView;

    @Bind(R.id.tab_my_image)
    ImageView myImageView;

    @Bind(R.id.tab_middle_image)
    ImageView middleImageView;

    @Bind(R.id.tab_moments_text)
    TextView momentsText;

    @Bind(R.id.tab_middle_text)
    TextView middleText;

    @Bind(R.id.tab_data_text)
    TextView dataText;

    @Bind(R.id.tab_lives_text)
    TextView livesText;

    @Bind(R.id.tab_my_text)
    TextView myText;

    /**
     * 定义再按一次退出程序
     */
    private long exitTime = 0;

    private FragmentManager fragmentManager;
    private MomentsFragment momentsFragment;
//    private HomePageFragment homePageFragment;
    private DataFragment dataFragment;
    private LivesFragment livesFragment;
    private MyFragment myFragment;
    private MiddleFragment middleFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        showDefaultTab();

        PreferencesManager pm = PreferencesManager.getInstance(this);
        pm.put("seasonSelector", 0);

        hideToolbar();
        setToolbarDividerEnable(true);

    }

    private void showDefaultTab() {
        // TODO: Use selector
        momentsImageView.setBackgroundResource(R.mipmap.moments_on);
        momentsText.setTextColor(this.getResources().getColor(R.color.yellow_01));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (momentsFragment == null) {
            momentsFragment = new MomentsFragment();
            transaction.add(R.id.layout_content, momentsFragment);
        } else {
            transaction.show(momentsFragment);
        }
        transaction.commit();
    }

    /**
     * 首页按钮的点击事件
     */
    @OnClick(R.id.tab_moments_bar)
    public void momentsClick(View v) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //首先隐藏掉所有的Fragment,以防止有多个Fragment显示在界面上的情况
        hideFragment(transaction);
        rsetUi();
        momentsText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        //设置所有的Bottom的状态改变
        momentsImageView.setBackgroundResource(R.mipmap.moments_on);
        dataImageView.setBackgroundResource(R.mipmap.data_off);
        livesImageView.setBackgroundResource(R.mipmap.lives_off);
        myImageView.setBackgroundResource(R.mipmap.my_off);
        middleImageView.setBackgroundResource(R.mipmap.middle_off);


        if (momentsFragment == null) {
            //如果quanziFragment为空就创建一个新的quanziFragment,并把它添加到界面上
            momentsFragment = new MomentsFragment();
            transaction.add(R.id.layout_content, momentsFragment);
        } else {
            //如果quanziFragment不为空,直接将其显示出来
            transaction.show(momentsFragment);
        }
        transaction.commit();
    }

    /**
     * 数据按钮的点击事件
     */
    @OnClick(R.id.tab_data_bar)
    public void dataClick(View v) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        rsetUi();
        dataText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        momentsImageView.setBackgroundResource(R.mipmap.moments_off);
        dataImageView.setBackgroundResource(R.mipmap.data_on);
        livesImageView.setBackgroundResource(R.mipmap.lives_off);
        myImageView.setBackgroundResource(R.mipmap.my_off);
        middleImageView.setBackgroundResource(R.mipmap.middle_off);
        if (dataFragment == null) {
            dataFragment = new DataFragment();
            transaction.add(R.id.layout_content, dataFragment);
        } else {
            transaction.show(dataFragment);
        }
        transaction.commit();
    }

    /**
     * 圈子按钮的点击事件
     */
    @OnClick(R.id.tab_middle)
    public void middleClick(View v) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        rsetUi();
        middleText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        momentsImageView.setBackgroundResource(R.mipmap.moments_off);
        dataImageView.setBackgroundResource(R.mipmap.data_off);
        livesImageView.setBackgroundResource(R.mipmap.lives_off);
        myImageView.setBackgroundResource(R.mipmap.my_off);
        middleImageView.setBackgroundResource(R.mipmap.middle_on);
        if (middleFragment == null) {
            middleFragment = new MiddleFragment();
            transaction.add(R.id.layout_content, middleFragment);
        } else {
            transaction.show(middleFragment);
        }
        transaction.commit();
    }

    /**
     * 直播按钮的点击事件
     */
    @OnClick(R.id.tab_lives_bar)
    public void livesClick(View v) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        rsetUi();
        livesText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        momentsImageView.setBackgroundResource(R.mipmap.moments_off);
        dataImageView.setBackgroundResource(R.mipmap.data_off);
        livesImageView.setBackgroundResource(R.mipmap.lives_on);
        myImageView.setBackgroundResource(R.mipmap.my_off);
        middleImageView.setBackgroundResource(R.mipmap.middle_off);
        if (livesFragment == null) {
            livesFragment = new LivesFragment();
            transaction.add(R.id.layout_content, livesFragment);
        } else {
            transaction.show(livesFragment);
            PtrClassicFrameLayout ptrframe = livesFragment.isPtrframe();
            if (ptrframe != null)
                ptrframe.autoRefresh();
        }
        transaction.commit();
    }

    /**
     * 我的 按钮的点击事件
     */
    @OnClick(R.id.tab_my_bar)
    public void profileClick(View v) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        rsetUi();
        myText.setTextColor(this.getResources().getColor(R.color.yellow_01));
        momentsImageView.setBackgroundResource(R.mipmap.moments_off);
        dataImageView.setBackgroundResource(R.mipmap.data_off);
        livesImageView.setBackgroundResource(R.mipmap.lives_off);
        myImageView.setBackgroundResource(R.mipmap.my_on);
        middleImageView.setBackgroundResource(R.mipmap.middle_off);
        if (myFragment == null) {
            myFragment = new MyFragment();
            transaction.add(R.id.layout_content, myFragment);
        } else {
            transaction.show(myFragment);
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的Fragment
     *
     * @param transaction Fragment事务
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (momentsFragment != null) {
            transaction.hide(momentsFragment);
        }
        if (dataFragment != null) {
            transaction.hide(dataFragment);
        }
        if (livesFragment != null) {
            transaction.hide(livesFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (middleFragment != null) {
            transaction.hide(middleFragment);
        }
    }

    /**
     * 双击退出程序的方法
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showToastOnce(getApplicationContext(), "再次点击退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            exit();
        }
    }

    private void rsetUi(){
        momentsText.setTextColor(this.getResources().getColor(R.color.gray_128));
        middleText.setTextColor(this.getResources().getColor(R.color.gray_128));
        dataText.setTextColor(this.getResources().getColor(R.color.gray_128));
        livesText.setTextColor(this.getResources().getColor(R.color.gray_128));
        myText.setTextColor(this.getResources().getColor(R.color.gray_128));
    }

}
