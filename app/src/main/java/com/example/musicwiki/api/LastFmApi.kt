package com.example.musicwiki.api

import com.example.musicwiki.models.*
import com.example.musicwiki.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {



    @GET("?method=tag.getTopTags&format=json")
    suspend fun getTopTags(
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<TopTagsResponse>

    @GET("?method=tag.getTopTags&format=json")
    suspend fun getTopTagsWithLimit(
        @Query("limit")
        limit:Int =10,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<TopTagsResponse>

    @GET("?method=tag.getTopTags&format=json")
    suspend fun getTopTagsWithOffset(
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("offset")
        offset: Int = 10
    ): Response<TopTagsResponse>

    @GET("?method=tag.getinfo&format=json")
    suspend fun getTagInfo(
        @Query("tag")
        tag:String = "rock",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<TagInfoResponse>

    @GET("?method=tag.gettopalbums&format=json")
    suspend fun getTagTopAlbums(
        @Query("tag")
        tag:String = "rock",
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<TagAlbumListResponse>

    @GET("?method=tag.gettopartists&format=json")
    suspend fun getTagTopArtists(
        @Query("tag")
        tag:String = "rock",
        @Query("api_key")
        apiKey:String = API_KEY
    ): Response<TagArtistListResponse>

    @GET("?method=tag.gettoptracks&format=json")
    suspend fun getTagTopTracks(
        @Query("tag")
        tag:String = "rock",
        @Query("api_key")
        apiKey:String = API_KEY
    ): Response<TagTracksListResponse>

    @GET("?method=album.getinfo&format=json")
    suspend fun getAlbumInfo(
        @Query("artist")
        artist:String = "cher",
        @Query("album")
        album:String = "Believe",
        @Query("api_key")
        apiKey:String = API_KEY
    ):Response<AlbumInfoResponse>

    @GET("?method=artist.getinfo&format=json")
    suspend fun getArtistInfo(
        @Query("artist")
        artist:String = "cher",
        @Query("api_key")
        apiKey:String = API_KEY
    ):Response<ArtistInfoResponse>

    @GET("?method=artist.gettoptracks&format=json")
    suspend fun getArtistTopTracks(
        @Query("artist")
        artist: String = "cher",
        @Query("api_key")
        apiKey:String = API_KEY
    ):Response<ArtistTopTracksResponse>

    @GET("?method=artist.gettopalbums&format=json")
    suspend fun getArtistTopAlbums(
        @Query("artist")
        artist: String = "cher",
        @Query("api_key")
        apiKey: String= API_KEY
    ):Response<ArtistTopAlbumsResponse>



}