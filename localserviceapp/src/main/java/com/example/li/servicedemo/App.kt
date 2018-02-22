package com.example.li.servicedemo

import android.app.Application
import org.xutils.x

/**
 * Created by li on 2018/2/22.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //xutils 3 初始化
        x.Ext.init(this)
        x.Ext.setDebug(false)
    }
}
