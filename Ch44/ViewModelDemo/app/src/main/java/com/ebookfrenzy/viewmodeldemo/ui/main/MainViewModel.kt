package com.ebookfrenzy.viewmodeldemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.SavedStateHandle

const val RESULT_KEY = "Euro Value"

class MainViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val usd_to_eu_rate = 0.74f
    private var dollarText = ""
    private var result: MutableLiveData<Float> = savedStateHandle.getLiveData(RESULT_KEY)

    fun setAmount(value: String) {
        this.dollarText = value
        val convertedValue = value.toFloat() * usd_to_eu_rate
        result.value = convertedValue
        savedStateHandle.set(RESULT_KEY, convertedValue)
    }

    fun getResult(): MutableLiveData<Float> {
        return result
    }
}