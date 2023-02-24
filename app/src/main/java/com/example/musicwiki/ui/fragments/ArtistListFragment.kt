package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.adapters.TagAlbumAdapter
import com.example.musicwiki.adapters.TagArtistAdapter
import com.example.musicwiki.models.Album
import com.example.musicwiki.models.Artist
import com.example.musicwiki.models.ArtistX
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistListFragment: Fragment(R.layout.fragment_artist_list) {

    lateinit var artistAdapter: TagArtistAdapter
    private val viewModel: GenreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tagTopArtistList: List<ArtistX>
        setupRecyclerView()
        artistAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("albumName","")
                putString("artistName",it.name)
            }
            findNavController().navigate(
                R.id.action_genreDetailFragment_to_artistDetailFragment,
                bundle
            )
        }

        viewModel.tagTopArtists.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagTopArtistListResponse ->
                        tagTopArtistList = tagTopArtistListResponse.topartists.artist
                        artistAdapter.differ.submitList(tagTopArtistList)
                        Log.d("ArtistsListsssss",tagTopArtistListResponse.topartists.artist.toString())
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


    }
    private fun setupRecyclerView() {
        artistAdapter = TagArtistAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvTagArtistsList).apply {
            adapter = artistAdapter
            layoutManager = GridLayoutManager(activity,2)
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