package com.example.simplewallpaperapp.paging
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagination.models.Hit
import com.example.simplewallpaperapp.network.ApiService
import java.lang.Exception
class ImagePagination(val apiService: ApiService,val type:String): PagingSource<Int, Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        try {
            val nextPageNumber = params.key ?: 1
            val image = apiService.getPhoto(nextPageNumber,type,30)
            if (nextPageNumber > 1) {
                return LoadResult.Page(image.hits, nextPageNumber - 1, nextPageNumber + 1)
            } else {
                return LoadResult.Page(image.hits, null, nextPageNumber + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}