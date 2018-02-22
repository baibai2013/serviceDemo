package com.example.li.servicedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //注册BroadcastReceiver
        var intentFilter = IntentFilter()
        intentFilter.addAction("com.demo.download")
        registerReceiver(myreceiver, intentFilter)


        button.setOnClickListener({

            //发送开启服务的intent
            var intent = Intent(MainActivity@this,DownloadService::class.java)
            intent.putExtra("url","http://wap.apk.anzhi.com/data1/apk/201802/07/1208063166e96231b8e0e5e2614fbc80_78464200.apk")
            startService(intent)
            progressBar.visibility = View.VISIBLE

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        //取消注册BroadcastReceiver
        unregisterReceiver(myreceiver)
    }


    /**
     * 匿名内部类 BroadCastReceiver 接收下载进度和完成的处理
     */
    var myreceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            //显示进度
            var progress = intent!!.getIntExtra("progress",0)
            Log.e("download","progress:"+progress)
            progressBar.setProgress(progress)
            var success = intent!!.getBooleanExtra("success",false)

            if(success){
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(baseContext, "下载完成",Toast.LENGTH_LONG).show()
            }
        }

    }

}
