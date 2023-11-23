package com.cyn.sharedemo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity

class TestActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.r1_sdk_share_dialog)
    }
}