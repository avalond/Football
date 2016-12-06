package cn.brision.football.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import cn.brision.football.R;


public class ImageEditText extends EditText {

    private Drawable mRDrawable;
    private Drawable mLDrawableFocus;
    private Drawable mLDrawableNormal;
    private Drawable mCurrentLeftDrawable;
    private Drawable mCurrentRightDrawable;
    private Rect mRBounds;

    public ImageEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ImageEditText.this.setCompoundDrawablesWithIntrinsicBounds(mLDrawableFocus,
                        null, mCurrentRightDrawable, null);
                    mCurrentLeftDrawable = mLDrawableFocus;
                } else {
                    ImageEditText.this.setCompoundDrawablesWithIntrinsicBounds(mLDrawableNormal,
                        null, null, null);
                    mCurrentLeftDrawable = mLDrawableNormal;
                }
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable text) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence text, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence text, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
                if (text.length() > 0) {
                    ImageEditText.this.setCompoundDrawablesWithIntrinsicBounds(mCurrentLeftDrawable,
                        null, mRDrawable, null);
                    mCurrentRightDrawable = mRDrawable;
                } else {
                    ImageEditText.this.setCompoundDrawablesWithIntrinsicBounds(mCurrentLeftDrawable,
                        null, null, null);
                    mCurrentRightDrawable = null;
                }
            }
        });
    }

    public ImageEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top,
                                                        Drawable right, Drawable bottom) {
        // TODO Auto-generated method stub
        if (right != null) {
            mRDrawable = right;
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && mRDrawable != null) {
            mRBounds = mRDrawable.getBounds();
            final int x = (int) event.getX();
            final int y = (int) event.getY();

            if (x >= (this.getRight() - mRBounds.width() * 2) && x <= (this.getRight() + mRBounds.width())
                && y >= (this.getPaddingTop() - mRBounds.height() * 2)
                && y <= (this.getHeight() + this.getPaddingBottom())) {
                //System.out.println("touch");
                this.setText("");
                event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
                this.requestFocus();
            }
            this.getY();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
            measureHeight(heightMeasureSpec));
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,  R.styleable.ImageEditText, 0, 0);
        try {
            mLDrawableNormal = a.getDrawable(R.styleable.ImageEditText_leftIconNormal);
            mLDrawableFocus = a.getDrawable(R.styleable.ImageEditText_leftIconFocus);
            mRDrawable = a.getDrawable(R.styleable.ImageEditText_rightIcon);
        } finally {
            a.recycle();
        }
        if (mLDrawableNormal != null) {
            setCompoundDrawablesWithIntrinsicBounds(mLDrawableNormal, null, null, null);
        }
    }

    /**
     * Determines the width of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = (int) this.getWidth() + getPaddingLeft()
                + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = (int) (this.getHeight()) + getPaddingTop()
                + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
