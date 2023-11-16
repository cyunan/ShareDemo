package com.cyn.sharedemo.share

import android.app.Activity
import android.content.IntentFilter
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.internal.TwitterApi
import com.twitter.sdk.android.tweetcomposer.TweetUploadService


object TwitterShare {
    private val twitterApi: TwitterApi by lazy { TwitterApi() }

    fun init(activity: Activity, consumerKey: String, consumerSecret: String){
        val config = TwitterConfig.Builder(activity.applicationContext)
            .logger(DefaultLogger(Log.VERBOSE))
            .twitterAuthConfig(TwitterAuthConfig(consumerKey, consumerSecret))
            .debug(true)
            .build()
        Twitter.initialize(config)
    }

    fun share(activity: Activity){
        init(activity, "Atm0nw12whLHIf8hW9JAxCDYn", "Dv2s5rlHnP6fqPONVOTllCuhqtQ5PLGkowJDcgExPhWXpW1iQR")

    }
}