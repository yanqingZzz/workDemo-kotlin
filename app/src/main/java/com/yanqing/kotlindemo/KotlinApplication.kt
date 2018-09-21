package com.yanqing.kotlindemo

import android.app.Application
import com.yanqing.kotlindemo.db.utils.DbUtils

class KotlinApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        DbUtils.initDataBase(this)
    }
}