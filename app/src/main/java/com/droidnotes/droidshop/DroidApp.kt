package com.droidnotes.droidshop

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}