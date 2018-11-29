package com.example.jinhui.dispatchevents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/28.
 *
 * ViewGroup事件分发的流程是：
 *  dispatchTouchEvent–>onInterceptTouchEvent—>然后到手指点击View的事件分发（参考上面所说的View的事件分发）。
 */
public class MyLinearLayout extends LinearLayout {

    private static final String TAG = "MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * dispatchTouchEvent() -> return super.dispatchTouchEvent(ev) 消费当前事件，执行onInterceptTouchEvent()方法（即后面的方法都不执行）
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:05:27.386 2065-2065/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 10:05:27.386 2065-2065/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 10:05:27.386 2065-2065/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 10:05:27.443 2065-2065/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 10:05:27.443 2065-2065/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
     * 2018-11-29 10:05:27.443 2065-2065/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 10:05:27.443 2065-2065/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * dispatchTouchEvent() -> return true
     * 消费当前事件，不执行onInterceptTouchEvent()方法（即后面的方法都不执行）
     *  E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:08:33.098 2848-2848/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     *
     * dispatchTouchEvent() -> return false 不消费当前事件，不执行onInterceptTouchEvent()方法（即后面的方法都不执行）
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     *
     * MotionEvent.ACTION_DOWN -> return true
     * 消费了当前事件的MotionEvent.ACTION_DOWN行为，不执行后续方法的MotionEvent.ACTION_DOWN事件，
     * 并且拦截了onInterceptTouchEvent()事件，执行了onTouchEvent()的方法
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:24:25.351 9358-9358/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 10:24:25.351 9358-9358/com.example.jinhui.dispatchevents E/MyLinearLayout: onTouchEvent , ACTION_UP
     *
     * MotionEvent.ACTION_DOWN -> return false 不执行当前事件行为，不执行后续TouchEvent()方法（
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     *
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent , ACTION_DOWN");
//                return true;
//                return false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent , ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent , ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
//        return true;
//        return false;
    }

    /**
     * 事件拦截
     *
     * onInterceptTouchEvent() -> return super.onInterceptTouchEvent(ev)
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:43:34.976 14931-14931/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 10:43:34.976 14931-14931/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 10:43:34.977 14931-14931/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 10:43:35.041 14931-14931/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 10:43:35.041 14931-14931/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
     * 2018-11-29 10:43:35.041 14931-14931/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 10:43:35.041 14931-14931/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * onInterceptTouchEvent() -> return true  消费当前拦截事件，事件不再向下分发，事件交由自己处理
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:45:35.402 16581-16581/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 10:45:35.402 16581-16581/com.example.jinhui.dispatchevents E/MyLinearLayout: onTouchEvent , ACTION_DOWN
     *
     * onInterceptTouchEvent() -> return false 表示不拦截事件与return super.onInterceptTouchEvent(ev)一样的操作
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 10:55:04.525 22539-22539/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 10:55:04.526 22539-22539/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 10:55:04.526 22539-22539/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 10:55:04.606 22539-22539/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 10:55:04.606 22539-22539/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
     * 2018-11-29 10:55:04.606 22539-22539/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 10:55:04.607 22539-22539/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * MotionEvent.ACTION_DOWN -> return true
     * 消费当前拦截事件，事件不再向下分发，事件交由自己处理
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 11:16:00.804 29819-29819/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 11:16:00.805 29819-29819/com.example.jinhui.dispatchevents E/MyLinearLayout: onTouchEvent , ACTION_DOWN
     *
     * MotionEvent.ACTION_DOWN -> return false
     *  E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 11:17:38.777 30846-30846/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 11:17:38.777 30846-30846/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 11:17:38.777 30846-30846/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 11:17:38.780 30846-30846/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 11:17:38.781 30846-30846/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
     * 2018-11-29 11:17:38.781 30846-30846/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 11:17:38.781 30846-30846/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * MotionEvent.ACTION_DOWN -> break
     * E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
     * 2018-11-29 11:18:41.821 31551-31551/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
     * 2018-11-29 11:18:41.821 31551-31551/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 11:18:41.821 31551-31551/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 11:18:41.824 31551-31551/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
     * 2018-11-29 11:18:41.824 31551-31551/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
     * 2018-11-29 11:18:41.824 31551-31551/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 11:18:41.824 31551-31551/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     *
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent , ACTION_DOWN");
//                return true;
//                return false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent , ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent , ACTION_UP");
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
//        return true;
//        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent , ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent , ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent , ACTION_UP");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        Log.e(TAG, "enter requestDisallowInterceptTouchEvent");
    }
}
