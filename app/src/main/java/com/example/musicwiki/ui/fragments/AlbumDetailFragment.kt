package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumDetailGenreAdapter
import com.example.musicwiki.adapters.TagTrackAdapter
import com.example.musicwiki.models.Tag
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.AlbumDetailViewModel
import com.example.musicwiki.viewmodels.AlbumDetailViewModel.Companion.ALBUM_NAME_ARGS
import com.example.musicwiki.viewmodels.AlbumDetailViewModel.Companion.ARTIST_NAME_ARGS
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class AlbumDetailFragment:Fragment(R.layout.fragment_album_detail){

    private val viewModelAlbumDetail:AlbumDetailViewModel by viewModels()
    lateinit var genreAdapter:AlbumDetailGenreAdapter
    val args:AlbumListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        var tagList:MutableList<Tag>

        val albumTitle = view.findViewById<TextView>(R.id.tvAlbumDetailTitle)
        val albumDetail = view.findViewById<TextView>(R.id.tvAlbumDetail)
        var albumCoverUrl:String
        val artistName = view.findViewById<TextView>(R.id.tvAlbumDetailArtist)
        val albumCover = view.findViewById<ImageView>(R.id.ivAlbumDetail)


        val albumNameArgs = args.albumName
        val artistNameArgs = args.artistName

        ALBUM_NAME_ARGS = albumNameArgs
        ARTIST_NAME_ARGS = artistNameArgs



        viewModelAlbumDetail.tagList.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagListResponse ->
                        tagList = tagListResponse.topTags.tag
                        genreAdapter.differ.submitList(tagList)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }

        })
        viewModelAlbumDetail.albumInfo.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { albumInfoResponse ->
                        albumCoverUrl=albumInfoResponse.album.image[3].text
                        Glide.with(requireContext())
                            .load(albumCoverUrl)
                            .into(albumCover)
                        albumTitle.text="Title: ${albumInfoResponse.album.name}"
                        albumDetail.text=albumInfoResponse.album.wiki.summary
                        artistName.text="Artist : ${albumInfoResponse.album.artist}"
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }

        })
        genreAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("tag",it.name)
            }
            findNavController().navigate(R.id.action_albumDetailFragment_to_genreDetailFragment,
                bundle)
        }


    }


    private fun setupRecycleView() {
        genreAdapter = AlbumDetailGenreAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvAlbumDetailGenres).apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun hideProgressBar()
    {
        requireView().findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE

    }
    private fun showProgressBar()
    {
        requireView().findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE

    }

}