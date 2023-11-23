package com.cyn.sharedemo.share

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import com.cyn.sharedemo.vk.VKServerUploadInfo
import com.cyn.sharedemo.vk.VKWallPostCommand
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKHttpPostCall
import com.vk.api.sdk.VKMethodCall
import java.util.concurrent.TimeUnit


object VkShare {
    const val TAG = "VkShare"
    const val IMAGE_REQ_CODE = 101

    fun requestPhoto(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, IMAGE_REQ_CODE)
    }



    fun share(message: String, uri: Uri? = null) {
        val photos = ArrayList<Uri>().apply { uri?.let { add(it) }}
        VK.execute(VKWallPostCommand(message, photos), object:
            VKApiCallback<Int> {
            override fun success(result: Int) {
                Log.i(TAG, "success $result")
            }

            override fun fail(error: Exception) {
                Log.e(TAG, error.toString())
            }
        })
    }


    fun share(activity: Activity, uri: Uri? = null){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        val resolveInfos: List<ResolveInfo> = activity.packageManager
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val vkPackageName = "com.vkontakte.android"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            val temp = resolveInfos.stream()
//                .filter { info: ResolveInfo -> vkPackageName == info.activityInfo.packageName }
//                .findFirst()
//
//        }
        intent.setPackage(vkPackageName)
//        for (info in resolveInfos) {
//            if (info.activityInfo.packageName == vkPackageName) {
//                intent.setPackage(vkPackageName)
//                break
//            }
//        }
        intent.putExtra(Intent.EXTRA_TITLE, "文本标题")
        intent.putExtra(Intent.EXTRA_TEXT, "https://www.jianshu.com/p/d6b7e46591b2")
        uri?.let {
            intent.putExtra(Intent.EXTRA_STREAM, it)
            intent.type = "image/*";
        }
//        intent.`package` = "com.vkontakte.android"
        intent.type = "text/plain";
        activity.startActivity(intent);

    }

    fun onActivityResult(activity: Activity,requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                Log.i(TAG, "onActivityResult: ${data.data}")
//                share("分享图片", data.data!!)
                share(activity, data.data!!)
            } else {
                share(activity)
            }
        }
    }
}