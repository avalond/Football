package cn.brision.football.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.brision.football.view.ptr.PtrClassicFrameLayout;


/**
 * 解决下拉滑动与header轮播图横向滑动冲突问题
 * 修改后disableWhenHorizontalMove(true);设置后会引起下拉滑动效果失效
 *
 * Created by lijin on 16-5-14.
 */
public class HomePtrClassicFrameLayout extends PtrClassicFrameLayout {
    private float mDownX;
    private float mDownY;
    public HomePtrClassicFrameLayout(Context context) {
        super(context);
    }

    public HomePtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                disableWhenHorizontalMove(false);
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getX()-mDownX)> Math.abs(ev.getY()-mDownY)){
                    disableWhenHorizontalMove(true);
                }else{
                    disableWhenHorizontalMove(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                disableWhenHorizontalMove(false);
                break;
            default:
                break;

        }
        return  super.dispatchTouchEvent(ev);
    }
}
