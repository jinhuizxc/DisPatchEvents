package com.example.jinhui.dispatchevents;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * https://blog.csdn.net/zhujiangtaotaise/article/details/54706252
 * 谈谈我对Android View事件分发的理解
 *
 * @1 当我们点击屏幕上的View的时候首先触发的是View的dispatchTouchEvent事件, 可查看源码
 * <p>
 * View的执行顺序:
 *
 *
 * 说明 1：长按事件是在onTouchEvent中的ACTION_DOWN中触发的（如果你设置了长按事件），
 * 而点击onclick事件是在ACTION_UP中触发的。
 *
 * 说明2: 由于我是为了说明前言里面的问题，所以ViewGroup的touch事件分发，
 * 我不作过多的说明。
 * ViewGroup事件分发的流程是：
 * dispatchTouchEvent–>onInterceptTouchEvent—>然后到手指点击View的事件分发（参考上面所说的View的事件分发）。
 *
 * onInterceptTouchEvent默认返回false，表示是否拦截事件。
 * ViewGroup的dispatchTouchEvent的源码
 *
 * 总结：
 * 1.Touch事件是从顶层的View一直往下分发到手指按下的最里面的View，如果这个View的onTouchEvent()返回false,即不消费Touch事件，这个Touch事件就会向上找父布局调用其父布局的onTouchEvent()处理，如果这个View返回true,表示消费了Touch事件，就不调用父布局的onTouchEvent()。
 * 2.一个clickable或者longClickable的View会永远消费Touch事件，不管他是enabled还是disabled的。
 * 3.View的长按事件是在ACTION_DOWN中执行，要想执行长按事件该View必须是longClickable的，如果设置的长按事件中返回true，那么clickable事件不会触发。并且不能产生ACTION_MOVE。
 * 4.View的点击事件是在ACTION_UP中执行，想要执行点击事件的前提是消费了ACTION_DOWN和ACTION_MOVE,并且是在没有设置OnLongClickListener的情况下，如设置了OnLongClickListener的情况，则必须使onLongClick()返回false。
 * 5.如果View设置了onTouchListener了，并且onTouch()方法返回true，则不执行View的onTouchEvent()方法，也表示View消费了Touch事件，返回false则继续执行onTouchEvent()。
 * 6.Touch事件是从最顶层的View一直分发到手指touch的最里层的View,如果最里层View消费了ACTION_DOWN事件（设置onTouchListener，并且onTouch()返回true 或者onTouchEvent()方法返回true）才会触发ACTION_MOVE,ACTION_UP的发生,如果某个ViewGroup拦截了Touch事件，则Touch事件交给ViewGroup处理。
 *
 */
public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private Button mButton;
    private TextView mTextView;
    private LinearLayout mLinearLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.my_btn);
        mTextView = (TextView) findViewById(R.id.my_textview);
        mLinearLayout = (LinearLayout) findViewById(R.id.id_my_linearlayout);

//        mLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "mLinearLayout ,  onClick");
//            }
//        });

//        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e(TAG, "mLinearLayout , onTouch ACTION_DOWN");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "mLinearLayout , onTouch ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.e(TAG, "mLinearLayout ,onTouch ACTION_UP");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });

//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "mTextView onClick");
//            }
//        });

        /**
         * 上面的mOnTouchListener 就是我们在Activity中设置的Touch事件，
         * 我们设置的时候在onTouch中返回的是false，所以会接着执行下面的onTouchEvent方法，
         * 可以看出onTouchEvent的返回值就是dispatchTouchEvent 的返回值。
         * onTouchEvent这个方法源码比较长，我截断了。
         *
         * MotionEvent.ACTION_DOWN return true:
         * E/MyButton: dispatchTouchEvent ACTION_DOWN
         * 2018-11-29 08:59:33.538 11584-11584/com.example.jinhui.dispatchevents E/MainActivity: mButton onTouch ACTION_DOWN
         * 2018-11-29 08:59:33.539 11584-11584/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
         * 2018-11-29 08:59:33.539 11584-11584/com.example.jinhui.dispatchevents E/MainActivity: mButton onTouch ACTION_UP
         * 2018-11-29 08:59:33.539 11584-11584/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
         *
         * MotionEvent.ACTION_DOWN return false:
         * E/MyButton: dispatchTouchEvent ACTION_DOWN
         * 2018-11-29 09:03:51.246 12934-12934/com.example.jinhui.dispatchevents E/MainActivity: mButton onTouch ACTION_DOWN
         * 2018-11-29 09:03:51.246 12934-12934/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_DOWN
         * 2018-11-29 09:03:51.251 12934-12934/com.example.jinhui.dispatchevents E/MyButton: dispatchTouchEvent ACTION_UP
         * 2018-11-29 09:03:51.251 12934-12934/com.example.jinhui.dispatchevents E/MainActivity: mButton onTouch ACTION_UP
         * 2018-11-29 09:03:51.251 12934-12934/com.example.jinhui.dispatchevents E/MyButton: onTouchEvent ACTION_UP
         *
         */
//        mButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e(TAG, "mButton onTouch ACTION_DOWN");
//                        return false;
////                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "mButton onTouch ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.e(TAG, "mButton onTouch ACTION_UP");
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });

        /**
         *
         *
         * setOnTouchListener()方法返回true
         *
         * 由于返回true，我们从dispatchTouchEvent 源码的第10行可以看出mOnTouchListener.onTouch( this, event))即返回true，
         * 那么if条件就成立了，dispatchTouchEvent 直接返回true，
         *
         * 接着执行后面的ACTION_MOVE和ACTION_UP，（ACTION_MOVE如果你点击的时候滑动了才会执行）。
         * 但是后面的onTouchEvent就执行不到了。
         *
         * 没有执行ACTION_MOVE是因为我快速点击且没有滑动，从log可以看出执行完dispatchTouchEvent
         * 的ACTION_DOWN之后又执行了dispatchTouchEvent 的ACTION_UP。但并没有执行onTouchEvent。
         */
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "mTextView onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "mTextView onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "mTextView onTouch ACTION_UP");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }
}
