package com.example.remotserviceapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var myDealService: IMyAidlInterface? = null
    private var isbinded: Boolean = false

    private var mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            myDealService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myDealService = IMyAidlInterface.Stub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bindBtn.setOnClickListener({
            if (!isbinded) {
                val intent = Intent();
                intent.action = "com.example.remotserviceapp.IMyAidlInterface"
                //Android5.0后无法只通过隐式Intent绑定远程Service
                //需要通过setPackage()方法指定包名
                intent.setPackage("com.example.remotserviceapp")

                bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
                isbinded = true
            }
        })

        unbindBtn.setOnClickListener({
            if (isbinded) {
                unbindService(mConnection)
                isbinded = false
            }
        })

        banliBtn.setOnClickListener({
            try {
                val result = myDealService!!.deal(50000, "房姐", "土豪")
                Toast.makeText(baseContext, result, Toast.LENGTH_LONG).show()
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        })

    }
}
