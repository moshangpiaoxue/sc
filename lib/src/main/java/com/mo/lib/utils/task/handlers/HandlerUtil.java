package com.mo.lib.utils.task.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.mo.lib.k;


/**
 * @ author：mo
 * @ data：2019/2/13:13:44
 * @ 功能：
 */
public class HandlerUtil {
    private static Handler mainHandler;

    private HandlerUtil() {
    }

    static void init(Context context) {
        if (mainHandler == null) {
            mainHandler = new Handler(context.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Object obj = msg.obj;
                    if (obj instanceof Runnable) {
                        ((Runnable) obj).run();
                    }
                }
            };
        }
    }

    /**
     * 获取主线程 Handler
     *
     * @return 主线程 Handler
     */
    public static Handler getMainHandler() {
        init(k.app());
        return mainHandler;
    }

    /**
     * 在主线程 Handler 中执行任务
     *
     * @param runnable 可执行的任务
     */
    public static void postRunnable(Runnable runnable) {

        getMainHandler().post(runnable);
    }

    /**
     * 在主线程 Handler 中执行延迟任务
     *
     * @param runnable 可执行的任务
     * @param delay    延迟时间
     */
    public static void postRunnable(Runnable runnable, long delay) {
        getMainHandler().postDelayed(runnable, delay);
    }

    /**
     * 在主线程 Handler 中执行循环延迟任务
     *
     * @param runnable 可执行的任务
     * @param delay    延迟时间
     * @param times    次数
     * @param interval 间隔时间
     */
    public static void postRunnable(final Runnable runnable, long delay, final int times, final int interval) {
        Runnable loop = new Runnable() {
            private int mTimes;

            @Override
            public void run() {
                if (mTimes < times) {
                    runnable.run();
                    getMainHandler().postDelayed(this, interval);
                }
                mTimes++;
            }
        };
        getMainHandler().postDelayed(loop, delay);
    }

    /**
     * 在主线程 Handler 中清除任务
     *
     * @param runnable 需要清除的任务
     */
    public static void removeRunnable(Runnable runnable) {
        getMainHandler().removeCallbacks(runnable);
    }

    /**
     * 在主线程 Handler 中发送可执行任务的消息
     * <p>
     * 该消息封装了可执行的任务, 在处理消息时会自动执行.
     * <p>
     * 注意: 由于该 Handler 是全局的, 使用 what 进行消息区分时, 注意与全局其他消息的冲突,
     * 防止清除消息时发生误清.
     *
     * @param what     消息 Id, 用于区分不同的消息 / 任务
     * @param runnable 可执行任务
     */
    public static void sendMessage(int what, Runnable runnable) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = runnable;
        getMainHandler().sendMessage(message);
    }

    /**
     * 在主线程 Handler 中发送延迟的可执行任务的消息
     * <p>
     * 该消息封装了可执行的任务, 在处理消息时会自动执行.
     * <p>
     * 注意: 由于该 Handler 是全局的, 使用 what 进行消息区分时, 注意与全局其他消息的冲突,
     * 防止清除消息时发生误清.
     *
     * @param what     消息 Id, 用于区分不同的消息 / 任务
     * @param runnable 可执行任务
     * @param delay    延迟时间
     */
    public static void sendMessage(int what, Runnable runnable, long delay) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = runnable;
        getMainHandler().sendMessageDelayed(message, delay);
    }

    /**
     * 清除该主线程 Handler 消息队列中标记为 what 的消息
     *
     * @param what 消息 Id
     */
    public static void removeMessages(int what) {
        getMainHandler().removeMessages(what);
    }
}
