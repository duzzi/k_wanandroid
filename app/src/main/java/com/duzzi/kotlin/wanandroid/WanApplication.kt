package com.duzzi.kotlin.wanandroid

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout


class WanApplication : MultiDexApplication() {

    companion object {
        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white) //全局设置主题颜色
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Utils.init(this)
    }



    override fun onCreate() {
        super.onCreate()
//        LoadSir.beginBuilder()
//            .addCallback(LErrorCallback) //添加各种状态页
//            .addCallback(EmptyCallback())
//            .addCallback(LoadingCallback())
//            .addCallback(TimeoutCallback())
//            .addCallback(CustomCallback())
//            .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
//            .commit()
    }
}