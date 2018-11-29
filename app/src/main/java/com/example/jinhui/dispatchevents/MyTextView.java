package com.example.jinhui.dispatchevents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/28.
 *
 * 点击textView log信息:
 *
 * 2018-11-28 22:00:17.695 15253-15253/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
 * 2018-11-28 22:00:17.696 15253-15253/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
 * 2018-11-28 22:00:17.696 15253-15253/com.example.jinhui.dispatchevents E/MyTextView: dispatchTouchEvent ACTION_DOWN
 * 2018-11-28 22:00:17.696 15253-15253/com.example.jinhui.dispatchevents E/MyTextView: onTouchEvent ACTION_DOWN
 * 2018-11-28 22:00:17.696 15253-15253/com.example.jinhui.dispatchevents E/MyLinearLayout: onTouchEvent , ACTION_DOWN
 */
public class MyTextView extends AppCompatTextView {

    private static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * E/MyTextView: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:27:56.323 18376-18376/com.example.jinhui.dispatchevents E/MainActivity: mTextView onTouch ACTION_DOWN
     * 2018-11-29 09:27:56.396 18376-18376/com.example.jinhui.dispatchevents E/MyTextView: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:27:56.396 18376-18376/com.example.jinhui.dispatchevents E/MainActivity: mTextView onTouch ACTION_UP
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
                //return true 后面的ACTION_MOVE、和ACTION_UP能够得以执行，
                // 如果不做任何操作，即 break，由于textview默认是不可点击和长点击的，
                // 所以return false, 那么 dispatcTouchEvent 会 return false，
                // 导致后面的ACTION_MOVE 和 ACTION_UP不能执行
                // return true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
