package cn.brision.football.view.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.brision.football.R;


public class LoadingMoreFooter extends LinearLayout {

    private Context context;
    private LinearLayout loadingViewLayout;
    private LinearLayout endLayout;
    private LinearLayout failLayout;

    public LoadingMoreFooter(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        setGravity(Gravity.CENTER);
        setLayoutParams(
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(this.context.getResources().getColor(R.color.white));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_footer_view, null);
        loadingViewLayout = (LinearLayout) view.findViewById(R.id.loading_view_layout);
        endLayout = (LinearLayout) view.findViewById(R.id.end_layout);
        failLayout = (LinearLayout) view.findViewById(R.id.fail_layout);
//        addFootLoadingView(new ProgressBar(context, null, android.R.attr.progressBarStyle));

//        TextView textView = new TextView(context);
//        textView.setText("已经到底啦~");
//        addFootEndView(textView);
//
//        TextView textView1 = new TextView(context);
//        textView1.setText("加载失败啦~");
//        addFailView(textView1);

        addView(view);
    }


    //设置底部加载中效果
    public void addFootLoadingView(View view) {
        loadingViewLayout.removeAllViews();
        loadingViewLayout.addView(view);
    }

    //设置底部到底了布局
    public void addFootEndView(View view) {
        endLayout.removeAllViews();
        endLayout.addView(view);
    }

    //设置底部到底了布局
    public void addFailView(View view) {
        failLayout.removeAllViews();
        failLayout.addView(view);
    }


    //设置已经没有更多数据
    public void setEnd() {
        setVisibility(VISIBLE);
        loadingViewLayout.setVisibility(GONE);
        endLayout.setVisibility(VISIBLE);
        failLayout.setVisibility(GONE);
    }

    public void setFail() {
        setVisibility(VISIBLE);
        loadingViewLayout.setVisibility(GONE);
        endLayout.setVisibility(GONE);
        failLayout.setVisibility(VISIBLE);
    }


    public void setVisible(){
        setVisibility(VISIBLE);
        loadingViewLayout.setVisibility(VISIBLE);
        endLayout.setVisibility(GONE);
        failLayout.setVisibility(GONE);
    }


    public void setGone(){
        setVisibility(GONE);
    }


}
