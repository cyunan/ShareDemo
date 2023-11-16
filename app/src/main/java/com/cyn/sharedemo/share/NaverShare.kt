package com.cyn.sharedemo.share

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.cyn.sharedemo.R
import com.navercorp.nng.android.sdk.NNGLink
import java.io.File
import java.io.FileOutputStream

object NaverShare {
    const val LOUNGE_ID = "share demo"
    const val CLIEND_ID = "4muCzkKnIpjmOZZ01TyQ"
    const val CLIENT_SECRET = "G8M4eALzni"

    fun initNaverSdk(activity: Activity){
        NNGLink.initModule(activity, LOUNGE_ID, CLIEND_ID, CLIENT_SECRET);
    }
    fun share(activity: Activity){
        if (!NNGLink.checkInitialized()) initNaverSdk(activity)
        NNGLink.writeFeed(activity, 0, "title", "content", getUri(activity))
    }

    private fun getUri(activity: Activity): String{
        val bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.sample1)
        val file = File(activity.cacheDir, "sample1.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        return Uri.fromFile(file).toString()
    }
}