package cn.brision.football.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.brision.football.utils.GlideUtils;

/**
 * Created by wangchengcheng on 16/8/12.
 * 自定义通用ViewHolder
 */
public class SimpleViewHolder {

    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context context;

    /**
     * ViewHolder构造方法, 不需要手动构造ViewHolder
     *
     * @param context  上下文
     * @param parent   父容器
     * @param layouId  layoutId
     * @param position position
     */
    private SimpleViewHolder(Context context, ViewGroup parent, int layouId,
                             int position) {
        this.context = context;
        this.mPosition = position;
        this.mViews = new SparseArray<>();

        mConvertView = LayoutInflater.from(context).inflate(layouId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 得到ViewHolder
     *
     * @param context     上下文
     * @param convertView itemView
     * @param parent      parent
     * @param layouId     item布局文件
     * @param position    当前position
     * @return ViewHolder
     */
    public static SimpleViewHolder getHolder(Context context, View convertView,
                                             ViewGroup parent, int layouId, int position) {
        if (convertView == null) {
            return new SimpleViewHolder(context, parent, layouId, position);
        } else {
            SimpleViewHolder holder = (SimpleViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 得到item上的控件
     *
     * @param viewId 控件Id
     * @return 对应的组件
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * adapter返回的convertView
     *
     * @return convertView
     */
    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 返回当前holder的position  防止混乱
     *
     * @return 当前holder对应的position
     */
    public int getmPosition() {
        return mPosition;
    }

    /**
     * 给textView设置text
     *
     * @param textViewId textViewId
     * @param text       需要设置的内容
     * @return ViewHolder
     */
    public SimpleViewHolder setTextViewText(@IdRes int textViewId, String text) {
        TextView tv = getView(textViewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
        }
        return this;
    }

    /**
     * 给textView设置text
     *
     * @param textViewId textViewId
     * @param strId      字符串id
     * @return ViewHolder
     */
    public SimpleViewHolder setTextViewText(@IdRes int textViewId, @StringRes int strId) {
        TextView tv = getView(textViewId);
        tv.setText(strId);
        return this;
    }

    /**
     * 给textView设置text
     *
     * @param textViewId             textViewId
     * @param spannableStringBuilder 字符串的Builder用于改变字色   字体
     * @return ViewHolder
     */
    public SimpleViewHolder setTextViewText(@IdRes int textViewId, SpannableStringBuilder spannableStringBuilder) {
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
     * 设置TextView的TextColor
     *
     * @param textViewId textView的Id
     * @param color      颜色
     * @return ViewHolder
     */
    public SimpleViewHolder setTextViewTextColor(@IdRes int textViewId, @ColorInt int color) {
        TextView tv = getView(textViewId);
        tv.setTextColor(color);
        return this;
    }

    /**
     * 为ImageView设置资源图片
     *
     * @param imageViewId ImageView的Id
     * @param drawableId  图片资源Id
     * @return
     */
    public SimpleViewHolder setImageViewImage(@IdRes int imageViewId, @DrawableRes int drawableId) {
        ImageView imageView = getView(imageViewId);
//        imageView.setImageResource(drawableId);
        GlideUtils.get(context).getImage(drawableId,imageView);
        return this;
    }

    /**
     * 为ImageView设置资源图片
     *
     * @param bitmap bitmap对象
     * @return
     */
    public SimpleViewHolder setImageViewImage(@IdRes int imageViewId, Bitmap bitmap) {
        ImageView imageView = getView(imageViewId);
        imageView.setImageBitmap(bitmap);
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
    public SimpleViewHolder setImageViewImage(@IdRes int imageViewId, @DrawableRes int drawableId, String url, @AnimRes int resId) {
        ImageView imageView = getView(imageViewId);
        GlideUtils.get(context).getImage(url, context.getResources().getDrawable(drawableId), imageView, resId);
        return this;
    }

    /**
     * 为View设置ClickListener,可传多个ViewId
     *
     * @param clickListener Listener
     * @param data
     * @param viewIds       CheckBox的Id  @return
     */
    public SimpleViewHolder setOnClickListener(ListenerWithPosition.OnClickWithPositionListener clickListener, Object data, @IdRes int... viewIds) {
        ListenerWithPosition listener = new ListenerWithPosition(mPosition, this);
        listener.setOnClickListener(clickListener);
        for (int id : viewIds) {
            View v = getView(id);
            v.setOnClickListener(listener);
        }
        return this;
    }


    /**
     * 为View设置背景
     *
     * @param ViewId     View的Id
     * @param drawableId drawable的Id
     * @return
     */
    public SimpleViewHolder setViewBackground(@IdRes int ViewId, @DrawableRes int drawableId) {
        View view = getView(ViewId);
        view.setBackgroundResource(drawableId);
        return this;
    }

    /**
     * 为View设置显示状态
     *
     * @param ViewId     View的Id
     * @param visibility 是否显示
     * @return
     */
    public SimpleViewHolder setViewVisibility(@IdRes int ViewId, int visibility) {
        View view = getView(ViewId);
        view.setVisibility(visibility);
        return this;
    }
}
