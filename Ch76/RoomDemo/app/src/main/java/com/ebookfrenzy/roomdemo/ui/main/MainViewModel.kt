package com.ebookfrenzy.roomdemo.ui.main

import androidx.lifecycle.AndroidViewModel
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebookfrenzy.roomdemo.Product
import com.ebookfrenzy.roomdemo.ProductRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository = ProductRepository(application)
    private val allProducts: LiveData<List<Product>>?
    private val searchResults: MutableLiveData<List<Product>>

    init {
        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }

    fun getSearchResults(): MutableLiveData<List<Product>> {
        return searchResults
    }

    fun getAllProducts(): LiveData<List<Product>>? {
        return allProducts
    }
}