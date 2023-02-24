package com.example.musicwiki.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicwiki.BaseApplication
import com.example.musicwiki.models.TagAlbumListResponse
import com.example.musicwiki.models.TagArtistListResponse
import com.example.musicwiki.models.TagInfoResponse
import com.example.musicwiki.models.TagTracksListResponse
import com.example.musicwiki.repository.GenreRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GenreViewModel@ViewModelInject constructor(
    val repository: GenreRepository,
    application: Application
):AndroidViewModel(application) {

    val tagInfo:MutableLiveData<Resource<TagInfoResponse>> = MutableLiveData()
    var tagInfoResponse:TagInfoResponse?=null

    val tagTopAlbums:MutableLiveData<Resource<TagAlbumListResponse>> = MutableLiveData()
    var tagTopAlbumsResponse : TagAlbumListResponse?=null

    val tagTopTracks:MutableLiveData<Resource<TagTracksListResponse>> = MutableLiveData()
    var tagTopTracksResponse : TagTracksListResponse?=null

    val tagTopArtists:MutableLiveData<Resource<TagArtistListResponse>> = MutableLiveData()
    var tagTopArtistsResponse : TagArtistListResponse?=null

    companion object {
        var TAG:String?=null
    }

    init {
        TAG?.let { getTagInfo(it) }
        TAG?.let { getTagTopAlbums(it) }
        TAG?.let { getTagTopTracks(it) }
        TAG?.let { getTagTopArtists(it) }
    }

    fun getTagInfo(tag:String) =
        viewModelScope.launch {
            safeTagInfoCall(tag)
        }
    private suspend fun safeTagInfoCall(tag: String){
        tagInfo.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getTagInfo(tag)
                tagInfo.postValue(handleTagInfoResponse(response))
            }
            else{
                tagInfo.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> tagInfo.postValue(Resource.Error("Network Failure"))
                else -> tagInfo.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleTagInfoResponse(response: Response<TagInfoResponse>) : Resource<TagInfoResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(tagInfoResponse == null){
                    tagInfoResponse=resultResponse
                }

                return Resource.Success(tagInfoResponse!!)
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

    fun getTagTopAlbums(tag:String){
        viewModelScope.launch {
            safeTagTopAlbums(tag)
        }
    }

    private suspend fun safeTagTopAlbums(tag: String) {
        tagTopAlbums.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getTagTopAlbums(tag)
                tagTopAlbums.postValue(handleTagTopAlbumsResponse(response))
            }
            else{
                tagTopAlbums.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> tagTopAlbums.postValue(Resource.Error("Network Failure"))
                else -> tagTopAlbums.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleTagTopAlbumsResponse(response: Response<TagAlbumListResponse>) : Resource<TagAlbumListResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(tagTopAlbumsResponse == null){
                    tagTopAlbumsResponse=resultResponse
                }

                return Resource.Success(tagTopAlbumsResponse!!)
            }
        }
        return Resource.Error(response.message())
    }
    fun getTagTopTracks(tag:String){
        viewModelScope.launch {
            safeTagTopTracks(tag)
        }
    }

    private suspend fun safeTagTopTracks(tag: String) {
        tagTopTracks.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getTagTopTracks(tag)
                Log.d("TrackList",response.body().toString())
                tagTopTracks.postValue(handleTagTopTracksResponse(response))
            }
            else{
                tagTopTracks.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> tagTopTracks.postValue(Resource.Error("Network Failure"))
                else -> tagTopTracks.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleTagTopTracksResponse(response: Response<TagTracksListResponse>) : Resource<TagTracksListResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(tagTopTracksResponse == null){
                    tagTopTracksResponse=resultResponse
                }

                return Resource.Success(tagTopTracksResponse!!)
            }
        }
        return Resource.Error(response.message())
    }
    fun getTagTopArtists(tag:String){
        viewModelScope.launch {
            safeTagTopArtists(tag)
        }
    }

    private suspend fun safeTagTopArtists(tag: String) {
        tagTopArtists.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getTagTopArtists(tag)
                tagTopArtists.postValue(handleTagTopArtistsResponse(response))
            }
            else{
                tagTopArtists.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> tagTopArtists.postValue(Resource.Error("Network Failure"))
                else -> tagTopArtists.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleTagTopArtistsResponse(response: Response<TagArtistListResponse>) : Resource<TagArtistListResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(tagTopArtistsResponse == null){
                    tagTopArtistsResponse=resultResponse
                }

                return Resource.Success(tagTopArtistsResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

}
