package com.example.remotserviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DealService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        //返回继承者Binder的Stub类型的Binder，业务逻辑的实现处理类
        Log.e("deal", "onBind")
       return  mBinder
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.e("deal", "onUnbind")
        return super.onUnbind(intent)
    }

    var mBinder = object : IMyAidlInterface.Stub() {
        override fun deal(dealMoney: Int, yezhu: String?, maijia: String?): String {

            Log.e("deal", "dealMoney:$dealMoney--yezhu:$yezhu--maijia:$maijia")
            return "成交！"
        }
    }
}
