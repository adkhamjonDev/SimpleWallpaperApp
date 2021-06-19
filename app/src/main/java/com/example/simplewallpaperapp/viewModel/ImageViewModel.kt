package com.example.simplewallpaperapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.simplewallpaperapp.network.ApiService
import com.example.simplewallpaperapp.paging.ImagePagination

class ImageViewModel(val apiService: ApiService, val str:String) : ViewModel() {

    val image = Pager(PagingConfig(100)) {
        ImagePagination(apiService,str)
    }.flow.cachedIn(viewModelScope)
}