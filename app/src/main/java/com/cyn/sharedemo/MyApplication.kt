package com.cyn.sharedemo

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

/**
 * @author: chenyunan
 * @date 2023/11/10
 * email: chenyunan@37.com
 * description:
 */
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "78a266d3ab4d48e842a6203ccc71cc54")
//        VK.addTokenExpiredHandler(tokenTracker)
    }
    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
        }
    }
}