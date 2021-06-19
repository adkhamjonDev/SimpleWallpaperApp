package com.example.simplewallpaperapp.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.simplewallpaperapp.network.ApiService
import java.lang.IllegalArgumentException

class ViewModelFactory(val apiService: ApiService,val str:String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(apiService,str) as T
        }
        throw IllegalArgumentException("Error")
    }
}