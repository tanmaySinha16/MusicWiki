package com.example.musicwiki.repository

import com.example.musicwiki.api.LastFmApi
import javax.inject.Inject

class GenreRepository@Inject constructor(
    val lastFmApi: LastFmApi
) {
    suspend fun getTopTagsWithLimits(limit:Int) = lastFmApi.getTopTagsWithLimit(limit)
    suspend fun getTopTags() = lastFmApi.getTopTags()
    suspend fun getTagInfo(tag:String) = lastFmApi.getTagInfo(tag)
    suspend fun getTagTopAlbums(tag:String) = lastFmApi.getTagTopAlbums(tag)
    suspend fun getTagTopArtists(tag:String) = lastFmApi.getTagTopArtists(tag)
    suspend fun getTagTopTracks(tag:String) = lastFmApi.getTagTopTracks(tag)
    suspend fun getAlbumInfo(artist:String,album:String)=lastFmApi.getAlbumInfo(artist,album)
    suspend fun getArtistInfo(artist:String) = lastFmApi.getArtistInfo(artist)
    suspend fun getArtistTopTracks(artist: String)=lastFmApi.getArtistTopTracks(artist)
    suspend fun getArtistTopAlbums(artist:String)=lastFmApi.getArtistTopAlbums(artist)
}