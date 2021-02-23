package com.duzzi.sdk.manager;

import android.content.Context;

import com.blankj.utilcode.util.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by free on 11/1/16.
 *
 * SDK操作入口，核心库，精简高效稳定
 *
 * 单利操作，获取实例方法 SDKManager.getInstance()
 *
 * 重要方法：
 *
 * 1）init(Context context, String appId, String appKey)
 *      初始化SDK
 * 2)
 */
public class SDKManager {
    public static final String TAG = SDKManager.class.getSimpleName();

    private ExecutorService defaultExecutor;

    private Context context;

    private static SDKManager instance;
    private String mApplicationId;

    private SDKManager() {
    }

    /**
     * 获取sdk实例
     * @return instance
     */
    public static synchronized SDKManager getInstance() {
        if (instance == null) {
            instance = new SDKManager();
        }
        return instance;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    public void setApplicationId(final String applicationId) {
        mApplicationId = applicationId;
    }

    public String getApplicationId() {
        return mApplicationId;
    }

    /**
     * SDK初始化
     * @param context   应用程序上下文
     */
    public void init(final Context context) {
        this.context = context;
        // 初始化线程池
        defaultExecutor = Executors.newFixedThreadPool(10);

    }

    /**
     * 释放sdk资源
     */
    public void destroy() {
        if (defaultExecutor != null) {
            defaultExecutor.shutdown();
            defaultExecutor = null;
        }
    }

    public ExecutorService getDefaultExecutor() {
        return defaultExecutor;
    }

    public Context getContext() {
        return context;
    }

}
