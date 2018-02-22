package com.example.li.servicedemo

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.util.Log
import org.xutils.common.Callback
import org.xutils.common.task.PriorityExecutor
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File

class DownloadService : Service() {


    private val MAX_DOWNLOAD_THREAD = 6

    private val executor = PriorityExecutor(MAX_DOWNLOAD_THREAD, true)

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent!!.getStringExtra("url")
        val filesavePath = Environment.getExternalStorageDirectory().path + "/serviceDemo/aaa.apk"

        val downLoadManagerCallBack = DownLoadManagerCallBack()
        val params = RequestParams(url)
        params.isAutoResume = true
        params.isAutoRename = false
        params.saveFilePath = filesavePath
        params.executor = executor
        params.isCancelFast = true
        x.http().get(params, downLoadManagerCallBack)

        return super.onStartCommand(intent, flags, startId)
    }


    inner class DownLoadManagerCallBack : Callback.CommonCallback<File>, Callback.ProgressCallback<File>, Callback.Cancelable {

        override fun isCancelled(): Boolean {
//            Log.e("download", "isCancelled")
            return false
        }


        override fun cancel() {
            Log.e("download", "cancel")
        }

        override fun onWaiting() {
            Log.e("download", "onWaiting")
        }

        override fun onStarted() {
            Log.e("download", "onStarted")
        }

        override fun onLoading(total: Long, current: Long, isDownloading: Boolean) {

            if (isDownloading) {
                Log.e("download", "total:$total--current:$current")
                var progress :Int = (current.toFloat()/total.toFloat() * 100.0f).toInt()

                //发送广播通知下载进度
                var intent = Intent()
                intent.setAction("com.demo.download")
                intent.putExtra("progress",progress)
                sendBroadcast(intent)
            }

        }

        override fun onSuccess(result: File) {
            Log.e("download", "name:" + result.name + "--path:" + result.absoluteFile)
            //发送广播通知完毕
            var intent = Intent()
            intent.setAction("com.demo.download")
            intent.putExtra("success",true)
            sendBroadcast(intent)
        }

        override fun onError(ex: Throwable, isOnCallback: Boolean) {
            ex.printStackTrace()
            Log.e("download", "onCancelled")
        }

        override fun onCancelled(cex: Callback.CancelledException) {
            cex.printStackTrace()
            Log.e("download", "onCancelled")
        }

        override fun onFinished() {
            Log.e("download", "onFinishied")
        }
    }

}

