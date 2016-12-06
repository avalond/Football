package cn.brision.football.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import cn.brision.football.activity.BaseActivity;
import cn.brision.football.data.services.DataManger;
import cn.brision.football.utils.SystemBarHelper;

/**
 * Created by wangchengcheng on 16/8/11.
 * 通用的Fragment
 */
public abstract class BaseFragment extends Fragment {

    protected View mView;//当前的View
    protected Context mContext;//Activity的对象
    int ResId;// Res ID
    View view;// View
    public DataManger dataManger;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        dataManger = ((BaseActivity) getActivity()).getApp().getDataManger();
    }

    /**
     * 获取试图的id
     *
     * @param id id
     */
    protected void setContentView(int id) {
        this.ResId = id;
    }

    /**
     * 获取试图
     *
     * @param view 试图View
     */
    protected void setContentView(View view) {
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (ResId != 0) {
            mView = inflater.inflate(ResId, container, false);
            return mView;
        } else if (view != null) {
            mView = view;
            return view;
        } else {
            TextView textView = new TextView(mContext);
            mView = textView;
            textView.setText("未添加试图");
            textView.setTextSize(30);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, mView);
        initView();
        getOkhttpData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 在此方法中初始化
     */
    protected abstract void initView();

    /**
     * 此方法中获取http数据
     */
    protected abstract void getOkhttpData();

    /**
     * 查找view, 不需要强制转换
     *
     * @param id   控件id
     * @param <VT> View类型
     * @return 对应的View
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }

    /**
     * 给多个View绑定点击事件
     *
     * @param listener 点击事件监听器
     * @param views    需要绑定的所有View
     */
    protected void setOnClickListeners(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 打印出当前类名的log, 调用Log.d()
     *
     * @param s 需要打印的log字符串
     */
    protected void logForDebug(String s) {
        if (!TextUtils.isEmpty(s)) {
            Log.d(this.getClass().getSimpleName(), s);
        }
    }

    /**
     * 跳转至相应的Activity
     *
     * @param cla 跳转的Activity
     */
    protected void openActivity(Class cla) {
        Intent intent = new Intent(mContext, cla);
        mContext.startActivity(intent);
    }
}
