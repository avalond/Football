package cn.brision.football.adapter;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import cn.brision.football.utils.GlideUtils;

/**
 * Created by wangchengcheng on 16/9/5.
 */
public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    public View mConvertView;
    public  int position;
    private SparseArray<View> mViews;
    private Context context;

    public CommonRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
        this.context = context;
    }

    /**
     * 得到item上的控件
     *
     * @param viewId    控件的id
     * @param <T>       对应的控件
     * @return view
     */
    public <T extends View> T getView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId    textView的id
     * @param text          字符串
     * @return  ViewHolder
     */
    public CommonRecyclerViewHolder setTextViewText(@IdRes int textViewId, String text){
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)){
            tv.setText(text);
        }
        return this;
    }

    /**
     * 为TextView设置Text,可以传空
     *
     * @param textViewId textView的Id
     * @param text       字符串的Id
     * @return ViewHolder
     */
    public CommonRecyclerViewHolder setTextViewTextCanEmpty(@IdRes int textViewId, String text){
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)){
            tv.setText(text);
        }else {
            tv.setText("");
        }
        return this;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId textView的Id
     * @param strId      字符串的Id
     * @return ViewHolder
     */
    public CommonRecyclerViewHolder setTextViewText(@IdRes int textViewId, @StringRes int strId){
        TextView tv = getView(textViewId);
        tv.setText(strId);
        return this;
    }

    /**
     * 为TextView设置Text
     *
     * @param textViewId             textView的Id
     * @param spannableStringBuilder 字符串的Builder用于改变字色   字体
     * @return ViewHolder
     */
    public CommonRecyclerViewHolder setTextViewText(@IdRes int textViewId, SpannableStringBuilder spannableStringBuilder) {
        TextView tv = getView(textViewId);
        tv.setText(spannableStringBuilder);
        return this;
    }

    /**
     * 获取TextView的Text
     *
     * @param textViewId textView的Id
     * @return textView的text
     */
    public String getTextViewText(@IdRes int textViewId) {
        TextView tv = getView(textViewId);
        return tv.getText().toString();
    }

    /**
     * 为ImageView设置资源图片
     *
     * @param imageViewId ImageView的Id
     * @param drawableId  图片资源Id
     * @return
     */
    public CommonRecyclerViewHolder setImageViewImage(@IdRes int imageViewId, @DrawableRes int drawableId) {
        ImageView imageView = getView(imageViewId);
//        imageView.setImageResource(drawableId);
        GlideUtils.get(context).getImage(drawableId,imageView);
        return this;
    }

    /**
     * 为CheckBox设置是否check
     *
     * @param checkBoxId CheckBox的Id
     * @param isCheck    是否选择
     * @return
     */
    public CommonRecyclerViewHolder setCheckBoxCheck(@IdRes int checkBoxId, boolean isCheck) {
        CheckBox cb = getView(checkBoxId);
        cb.setChecked(isCheck);
        return this;
    }

    /**
     * 为CheckBox设置CheckListener 回调带position
     *
     * @param checkBoxId    CheckBox的Id
     * @param checkListener Listener
     * @return
     */
    public CommonRecyclerViewHolder setCheckBoxCheckChangeListener(@IdRes int checkBoxId, ListenerWithPosition.OnCheckedChangeWithPositionListener checkListener) {
        CheckBox cb = getView(checkBoxId);
        ListenerWithPosition listener = new ListenerWithPosition(position, this);
        cb.setOnCheckedChangeListener(listener);
        listener.setCheckChangeListener(checkListener);
        return this;
    }

    /**
     * 为View设置ClickListener,可传多个ViewId
     *
     * @param viewIds       CheckBox的Id
     * @param clickListener Listener
     * @return
     */
    public CommonRecyclerViewHolder setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(position, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * 为View设置LongClickListener,可传多个ViewId
     *
     * @param viewIds           CheckBox的Id
     * @param longClickListener Listener
     * @return
     */
    public CommonRecyclerViewHolder setOnLongClickListener(ListenerWithPosition.OnLongClickWithPositionListener longClickListener, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(position, this);
        listener.setOnLongClickListener(longClickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnLongClickListener(listener);
        }
        return this;
    }

    /**
     * 为ImagmeView设置网络图片。(使用Glide加载图片)
     *
     * @param imageViewId imageViewId
     * @param drawableId  drawableId
     * @param url         图片url地址
     * @param resId       图片加载动画
     * @return
     */
    public CommonRecyclerViewHolder setImageViewImage(@IdRes int imageViewId, @DrawableRes int drawableId, String url, @AnimRes int resId) {
        ImageView imageView = getView(imageViewId);
        GlideUtils.get(context).getImage(url, context.getResources().getDrawable(drawableId), imageView, resId);
        return this;
    }

    /**
     * 为View设置选择状态
     *
     * @param ViewId     View的Id
     * @param isSelected 是否选择
     * @return
     */
    public CommonRecyclerViewHolder setViewSelect(@IdRes int ViewId, boolean isSelected) {
        View view = getView(ViewId);
        view.setSelected(isSelected);
        return this;
    }

    /**
     * 为View设置显示状态
     *
     * @param ViewId     View的Id
     * @param visibility 是否显示
     * @return
     */
    public CommonRecyclerViewHolder setViewVisibility(@IdRes int ViewId, int visibility) {
        View view = getView(ViewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * 为View设置背景
     *
     * @param ViewId     View的Id
     * @param drawableId drawable的Id
     * @return
     */
    public CommonRecyclerViewHolder setViewBackground(@IdRes int ViewId, @DrawableRes int drawableId) {
        View view = getView(ViewId);
        view.setBackgroundResource(drawableId);
        return this;
    }
}
