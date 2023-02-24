package com.example.musicwiki.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.BaseApplication
import com.example.musicwiki.models.AlbumInfoResponse
import com.example.musicwiki.models.TagInfoResponse
import com.example.musicwiki.models.TopTagsResponse
import com.example.musicwiki.repository.GenreRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class AlbumDetailViewModel@ViewModelInject constructor(
    val repository: GenreRepository,
    application: Application
) :AndroidViewModel(application){

    val tagList:MutableLiveData<Resource<TopTagsResponse>> = MutableLiveData()
    var tagsResponse:TopTagsResponse?=null

    val albumInfo:MutableLiveData<Resource<AlbumInfoResponse>> = MutableLiveData()
    var albumInfoResponse:AlbumInfoResponse?=null

    companion object{
        var ALBUM_NAME_ARGS:String?=null
        var ARTIST_NAME_ARGS:String?=null
    }

    init {
        getTagList()
        ALBUM_NAME_ARGS?.let { ARTIST_NAME_ARGS?.let { it1 -> getAlbumInfo(it1, it) } }
    }
    fun getTagList(){
        viewModelScope.launch {
            safeTagListCall()
        }
    }

    private suspend fun safeTagListCall(){
        tagList.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getTopTags()
                tagList.postValue(handleTagListResponse(response))
            }
            else{
                tagList.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> tagList.postValue(Resource.Error("Network Failure"))
                else -> tagList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleTagListResponse(response: Response<TopTagsResponse>) : Resource<TopTagsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(tagsResponse == null){
                    tagsResponse=resultResponse
                }

                return Resource.Success(tagsResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<BaseApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val activeNetwork = connectivityManager.activeNetwork?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?:return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type)
                {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    else -> false
                }
            }
        }
        return false
    }
    fun getAlbumInfo(artist:String,album:String){
        viewModelScope.launch {
            safeAlbumInfoCall(artist,album)
        }
    }

    private suspend fun safeAlbumInfoCall(artist:String,album:String){
        albumInfo.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getAlbumInfo(artist,album)
                albumInfo.postValue(handleAlbumInfoResponse(response))
            }
            else{
                albumInfo.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> albumInfo.postValue(Resource.Error("Network Failure"))
                else -> albumInfo.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleAlbumInfoResponse(response: Response<AlbumInfoResponse>) : Resource<AlbumInfoResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(albumInfoResponse == null){
                    albumInfoResponse=resultResponse
                }

                return Resource.Success(albumInfoResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

}