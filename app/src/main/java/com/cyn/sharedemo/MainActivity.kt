package com.cyn.sharedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import com.cyn.sharedemo.share.KakaoShare
import com.cyn.sharedemo.share.NaverShare
import com.cyn.sharedemo.share.VkShare
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.navercorp.nng.android.sdk.NNGLink
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope


class MainActivity : ComponentActivity() {
    companion object{
        const val TAG = "MainActivity"
    }

    private lateinit var authLauncher: ActivityResultLauncher<Collection<VKScope>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var CLIEND_ID = "UKvNABLDsyEJusJGsXL2"
        var CLIENT_SECRET = "rK4suc_Qd0"
        var LOUNGE_ID = "naver_game_4developer"
        NNGLink.initModule(this,
            LOUNGE_ID,
            CLIEND_ID,
            CLIENT_SECRET
        )
        findViewById<Button>(R.id.btn_kakao_share).setOnClickListener { kakaoShare() }
        findViewById<Button>(R.id.btn_naver_share).setOnClickListener { naverShare() }
        findViewById<Button>(R.id.btn_VK_share).setOnClickListener { vkShare() }
        authLauncher = VK.login(this) { result : VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    VkShare.requestPhoto(this)
                }
                is VKAuthenticationResult.Failed -> {
                    // User didn't pass authorization
                }
            }
        }
    }

    private fun kakaoShare(){

        val defaultFeed = FeedTemplate(
            content = Content(
                title = "标题：xxx",
                description = "内容：XXXX",
                imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            )
        )
        val imgTemp = KakaoShare.buildShareTemplate(
            imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png"
        )

        val textTemp = KakaoShare.buildShareTemplate(
            description = "内容：XXXX",
            imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png"
        )
        val testTemp = KakaoShare.buildShareTemplate(
            description = "内容：XXXX",
            imageUrl = "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png"
        )
        KakaoShare.kakaoUploadImage(this, object : Callback{
            override fun onSuccess(url: String) {
                val temp = KakaoShare.buildShareTemplate(imageUrl = url)
                KakaoShare.kakaoShare(this@MainActivity, temp)
            }

            override fun onFail() {
            }

        })

    }

    private fun naverShare(){
         NaverShare.share(this)
    }


    private fun vkShare(){
//        VkShare.requestPhoto(this)
        if (VK.isLoggedIn()){
            VkShare.requestPhoto(this)
            return
        }

        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        VkShare.onActivityResult(this, requestCode, resultCode, data)
    }



    interface Callback{
        fun onSuccess(url: String)
        fun onFail()
    }
}