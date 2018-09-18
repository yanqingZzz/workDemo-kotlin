package com.yanqing.kotlindemo.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.yanqing.kotlindemo.R
import kotlinx.android.synthetic.main.activity_toolbar.*

abstract class ToolbarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
//        toolbar.setTitleTextColor(Color.WHITE)
        toolbar_container.addView(initContentLayout())
        setSupportActionBar(toolbar)
    }

    abstract fun initContentLayout(): View

    protected fun addBackArrowEnable() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}