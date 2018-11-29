package com.example.jinhui.dispatchevents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2018/11/28.
 *
 * 点击MyButton后显示log信息:
 * 2018-11-28 21:47:06.184 13375-13375/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_DOWN
 * 2018-11-28 21:47:06.184 13375-13375/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_DOWN
 * 2018-11-28 21:47:06.184 13375-13375/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_DOWN
 * 2018-11-28 21:47:06.184 13375-13375/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
 * 2018-11-28 21:47:06.227 13375-13375/com.example.jinhui.dispatchevents E/MyLinearLayout: dispatchTouchEvent , ACTION_UP
 * 2018-11-28 21:47:06.227 13375-13375/com.example.jinhui.dispatchevents E/MyLinearLayout: onInterceptTouchEvent , ACTION_UP
 * 2018-11-28 21:47:06.227 13375-13375/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
 * 2018-11-28 21:47:06.228 13375-13375/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
 */
public class MyButton extends AppCompatButton {

    private static final String TAG = "MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Button
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:27:03.774 18376-18376/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 09:27:03.776 18376-18376/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:27:03.776 18376-18376/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     * @param event
     * @return
     *
     * MotionEvent.ACTION_DOWN 如果返回false，则不执行MotionEvent.ACTION_MOVE与MotionEvent.ACTION_UP。
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:34:15.835 22372-22372/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     *
     * MotionEvent.ACTION_DOWN 如果返回true或者直接break, 则会执行MotionEvent.ACTION_MOVE与MotionEvent.ACTION_UP。
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:35:46.424 22907-22907/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 09:35:46.484 22907-22907/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:35:46.484 22907-22907/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * onTouchEvent() -> return false
     *  E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 10:01:40.647 32000-32000/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     *
     * onTouchEvent() -> return true或者super.onTouchEvent(event)
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 10:02:42.705 32632-32632/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 10:02:42.705 32632-32632/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 10:02:42.705 32632-32632/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
//                return true;
//                return false;
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
//        return false;
//        return true;    // super.onTouchEvent(event) 与return的使用结果是如出一辙的，一样的
    }

    /**
     * MotionEvent.ACTION_DOWN -> return false 不执行当前事件
     *  E/MyButton: dispatchTouchEvent ACTION_DOWN
     *
     *  MotionEvent.ACTION_DOWN -> return true，说明由dispatchTouchEvent()来消费MotionEvent.ACTION_DOWN的事件，而不由onTouchEvent()来消费MotionEvent.ACTION_DOWN的事件
     *  E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:44:19.386 24618-24618/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:44:19.387 24618-24618/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * MotionEvent.ACTION_DOWN -> break
     *  E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:45:11.897 25539-25539/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 09:45:11.906 25539-25539/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:45:11.906 25539-25539/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     *
     * dispatchTouchEvent方法的返回值 -> return true,消费了当前事件，不执行onTouchEvent()方法
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:47:30.323 26144-26144/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     *
     * dispatchTouchEvent方法的返回值 -> return false 不消费当前事件，不执行onTouchEvent()方法
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     *
     * dispatchTouchEvent方法的返回值 -> return super.dispatchTouchEvent(event) 消费当前事件，执行onTouchEvent()方法
     * E/MyButton: dispatchTouchEvent ACTION_DOWN
     * 2018-11-29 09:51:26.439 27853-27853/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
     * 2018-11-29 09:51:26.441 27853-27853/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
     * 2018-11-29 09:51:26.441 27853-27853/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");
//                return true;
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
//        return true;
//        return false;
    }
}
