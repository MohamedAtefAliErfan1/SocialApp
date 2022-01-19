package com.mohamedabdelaziz.socialapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()
{   override fun onCreate() {
    super.onCreate()
    BaseApplication.appContext = applicationContext
}
    companion object {
        lateinit  var appContext: Context
    }
}