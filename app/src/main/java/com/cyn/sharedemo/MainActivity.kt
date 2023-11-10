package com.cyn.sharedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.kakao.sdk.share.ShareClient

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_naver_share).setOnClickListener { naverShare() }
    }

    fun naverShare(){
        val bundle: Bundle = Bundle()
        val templateId = bundle.getLong("customMemo")

        ShareClient.instance.shareCustom(
            this,
            templateId
        ) { sharingResult, error ->
            if (error != null) {
                Log.e(TAG, "KakaoTalk 分享失败", error)
            } else if (sharingResult != null) {
                Log.d(TAG, "KakaoTalk分享成功 ${sharingResult.intent}")
                startActivity(sharingResult.intent)

                // 虽然KakaoTalk共享成功，但如果存在以下警告消息，则某些内容可能无法正常运行
                Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
            }
        }
    }
}