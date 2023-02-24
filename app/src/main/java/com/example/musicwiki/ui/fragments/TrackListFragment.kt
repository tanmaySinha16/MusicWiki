package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.adapters.TagAlbumAdapter
import com.example.musicwiki.adapters.TagArtistAdapter
import com.example.musicwiki.adapters.TagTrackAdapter
import com.example.musicwiki.models.ArtistX
import com.example.musicwiki.models.Track
import com.example.musicwiki.models.Tracks
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackListFragment: Fragment(R.layout.fragment_track_list) {

    lateinit var trackAdapter: TagTrackAdapter
    private val viewModel: GenreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var tagTopTracksList : List<Track>
        viewModel.tagTopTracks.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagTopTrackListResponse ->
                        tagTopTracksList = tagTopTrackListResponse.tracks.track
                        trackAdapter.differ.submitList(tagTopTracksList)
                        Log.d("TrackListssssss",tagTopTrackListResponse.tracks.track.toString())
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
        setupRecyclerView()

    }
    private fun setupRecyclerView() {
        trackAdapter = TagTrackAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvTagTrackList).apply {
            adapter = trackAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
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