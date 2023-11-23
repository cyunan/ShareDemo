package com.cyn.sharedemo.share

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import com.cyn.sharedemo.R
import com.navercorp.nng.android.sdk.NNGCallbackListener
import com.navercorp.nng.android.sdk.NNGLink
import com.navercorp.nng.android.sdk.NNGLink.setSdkLoadListener
import java.io.File
import java.io.FileOutputStream


object NaverShare {
    var CLIEND_ID = "UKvNABLDsyEJusJGsXL2"
    var CLIENT_SECRET = "rK4suc_Qd0"
    private var LOUNGE_ID = "naver_game_4developer"

    fun initNaverSdk(activity: Activity){
        NNGLink.initModule(activity, LOUNGE_ID, CLIEND_ID, CLIENT_SECRET);
        setSdkLoadListener(object : NNGCallbackListener {
            override fun onSdkDidLoaded() {}
            override fun onSdkDidUnloaded() {}
            override fun onCallInGameMenuCode(inGameMenuCode: String) {
                Toast.makeText(
                    activity,
                    "onCallInGameMenuCode [$inGameMenuCode]",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNaverLoggedIn() {}
            override fun onNaverLoggedOut() {}
        })
    }
    fun share(activity: Activity){
        if (!NNGLink.checkInitialized()) initNaverSdk(activity)
        if(NNGLink.checkInitialized()){
            NNGLink.writeFeed(activity, 0, "title", "content", null)
        }

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