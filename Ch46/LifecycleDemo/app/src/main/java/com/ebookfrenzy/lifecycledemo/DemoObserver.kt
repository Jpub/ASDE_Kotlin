package com.ebookfrenzy.lifecycledemo

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.LifecycleOwner

class DemoObserver : LifecycleObserver {

    private val LOG_TAG = "DemoObserver"

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.i(LOG_TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i(LOG_TAG, "onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i(LOG_TAG, "onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i(LOG_TAG, "onStart")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i(LOG_TAG, "onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i(LOG_TAG, "onDestroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(LOG_TAG, owner.lifecycle.currentState.name)
    }
}