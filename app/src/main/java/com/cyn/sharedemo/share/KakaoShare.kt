package com.cyn.sharedemo.share

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.cyn.sharedemo.MainActivity
import com.cyn.sharedemo.R
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import java.io.File
import java.io.FileOutputStream

object KakaoShare {

    fun kakaoUploadImage(activity: Activity, callback: MainActivity.Callback){
        val bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.sample1)
        val file = File(activity.cacheDir, "sample1.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        ShareClient.instance.uploadImage(file) { imageUploadResult, error ->
            if (error != null) {
                callback.onFail()
                Log.e(MainActivity.TAG, "图片上传失败", error)
            } else if (imageUploadResult != null) {
                Log.i(MainActivity.TAG, "图片上传成功 \n${imageUploadResult.infos.original}")
                callback.onSuccess(imageUploadResult.infos.original.url)
            }
        }
    }

    fun buildShareTemplate(
        title: String = "",
        description: String = "",
        imageUrl: String = "",
        link: Link = Link()
    ) = FeedTemplate(
        content = Content(
            title = title,
            description = description,
            imageUrl = imageUrl,
            link = link
        )
    )


    fun kakaoShare(activity: Activity, temp: FeedTemplate) {
        ShareClient.instance.shareDefault(
            activity,
            temp
        ) { sharingResult, error ->
            if (error != null) {
                Log.e(MainActivity.TAG, "KakaoTalk 分享失败", error)
            } else if (sharingResult != null) {
                Log.d(MainActivity.TAG, "KakaoTalk分享成功 ${sharingResult.intent}")
                activity.startActivity(sharingResult.intent)

                // 虽然KakaoTalk共享成功，但如果存在以下警告消息，则某些内容可能无法正常运行
                Log.w(MainActivity.TAG, "Warning Msg: ${sharingResult.warningMsg}")
                Log.w(MainActivity.TAG, "Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    }
}