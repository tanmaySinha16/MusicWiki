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
import com.example.musicwiki.models.ArtistInfoResponse
import com.example.musicwiki.models.ArtistTopAlbumsResponse
import com.example.musicwiki.models.ArtistTopTracksResponse
import com.example.musicwiki.models.TopTagsResponse
import com.example.musicwiki.repository.GenreRepository
import com.example.musicwiki.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Appendable

class ArtistDetailViewModel @ViewModelInject constructor(
    val repository: GenreRepository,
    application: Application
):AndroidViewModel(application) {

    val tagList: MutableLiveData<Resource<TopTagsResponse>> = MutableLiveData()
    var tagsResponse: TopTagsResponse?=null

    val artistTopAlbums : MutableLiveData<Resource<ArtistTopAlbumsResponse>> = MutableLiveData()
    var artistTopAlbumsResponse : ArtistTopAlbumsResponse?=null

    val artistTopTracks :  MutableLiveData<Resource<ArtistTopTracksResponse>> = MutableLiveData()
    var artistTopTracksResponse : ArtistTopTracksResponse?=null

    val artistInfo : MutableLiveData<Resource<ArtistInfoResponse>> = MutableLiveData()
    var artistInfoResponse : ArtistInfoResponse?=null

    companion object {
        var ARTIST_DETAIL_NAME_ARGS : String?=null
    }

    init {
        getTagList()
        ARTIST_DETAIL_NAME_ARGS?.let { getArtistTopAlbums(it) }
        ARTIST_DETAIL_NAME_ARGS?.let { getArtistTopTracks(it) }
        ARTIST_DETAIL_NAME_ARGS?.let { getArtistInfo(it) }
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


    fun getArtistTopAlbums(artist:String){
        viewModelScope.launch {
            safeArtistTopAlbumsCall(artist)
        }
    }

    private suspend fun safeArtistTopAlbumsCall(artist:String){
        artistTopAlbums.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getArtistTopAlbums(artist)
                artistTopAlbums.postValue(handleArtistTopAlbumsResponse(response))
            }
            else{
                artistTopAlbums.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> artistTopAlbums.postValue(Resource.Error("Network Failure"))
                else -> artistTopAlbums.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleArtistTopAlbumsResponse(response: Response<ArtistTopAlbumsResponse>) : Resource<ArtistTopAlbumsResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(artistTopAlbumsResponse == null){
                    artistTopAlbumsResponse=resultResponse
                }

                return Resource.Success(artistTopAlbumsResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

    fun getArtistTopTracks(artist:String){
        viewModelScope.launch {
            safeArtistTopTracksCall(artist)
        }
    }

    private suspend fun safeArtistTopTracksCall(artist:String){
        artistTopTracks.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getArtistTopTracks(artist)
                artistTopTracks.postValue(handleArtistTopTracksResponse(response))
            }
            else{
                artistTopTracks.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> artistTopTracks.postValue(Resource.Error("Network Failure"))
                else -> artistTopTracks.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleArtistTopTracksResponse(response: Response<ArtistTopTracksResponse>) : Resource<ArtistTopTracksResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(artistTopTracksResponse == null){
                    artistTopTracksResponse=resultResponse
                }

                return Resource.Success(artistTopTracksResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

    fun getArtistInfo(artist:String){
        viewModelScope.launch {
            safeArtistInfoCall(artist)
        }
    }

    private suspend fun safeArtistInfoCall(artist:String){
        artistInfo.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = repository.getArtistInfo(artist)
                artistInfo.postValue(handleArtistInfoResponse(response))
            }
            else{
                artistInfo.postValue(Resource.Error("No internet connection"))
            }
        }catch (t:Throwable)
        {
            when(t){
                is IOException -> artistInfo.postValue(Resource.Error("Network Failure"))
                else -> artistInfo.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleArtistInfoResponse(response: Response<ArtistInfoResponse>) : Resource<ArtistInfoResponse>
    {
        if(response.isSuccessful)
        {
            response.body()?.let{ resultResponse ->

                if(artistInfoResponse == null){
                    artistInfoResponse=resultResponse
                }

                return Resource.Success(artistInfoResponse!!)
            }
        }
        return Resource.Error(response.message())
    }

}