package com.cyn.sharedemo

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

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
    }
}