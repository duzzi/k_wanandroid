package com.duzzi.kotlin.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.just.agentweb.AgentWeb


@Route(path = "/web/WebActivity")
class WebActivity : AppCompatActivity() {

    companion object {
        fun show(context: Context) {
            val intent = Intent(context, WebActivity::class.java)
            context.startActivity(intent)
        }
    }

    // 通过name来映射URL中的不同参数
    @JvmField
    @Autowired(name = "url")
    var url = ""

    @JvmField
    @Autowired(name = "title")
    var title = ""


    private lateinit var mAgentWeb: AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_web)
        supportActionBar?.let {
            it.title = title
            it.setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
            it.setHomeButtonEnabled(true); //设置返回键可用
            it.hide()
        }

        val view = findViewById<View>(R.id.ll_root)
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent((view as LinearLayout?)!!, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) true else super.onKeyDown(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }


    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }


}