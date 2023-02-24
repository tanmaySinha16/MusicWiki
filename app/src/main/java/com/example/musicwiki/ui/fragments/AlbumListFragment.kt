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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.adapters.TagAlbumAdapter
import com.example.musicwiki.models.Album
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumListFragment: Fragment(R.layout.fragment_album_list) {

    lateinit var albumAdapter: TagAlbumAdapter
    private val viewModel: GenreViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tagTopAlbumList: List<Album>
        setupRecyclerView()
        albumAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("albumName",it.name)
                putString("artistName",it.artist.name)
            }
            findNavController().navigate(
                R.id.action_genreDetailFragment_to_albumDetailFragment,
                bundle
            )
        }

        viewModel.tagTopAlbums.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagTopAlbumListResponse ->
                        tagTopAlbumList = tagTopAlbumListResponse.albums.album
                        albumAdapter.differ.submitList(tagTopAlbumList)
                        Log.d("AlbumListssssssss",tagTopAlbumListResponse.albums.album.toString())
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
         albumAdapter = TagAlbumAdapter()
         requireView().findViewById<RecyclerView>(R.id.rvTagAlbumsList).apply {
            adapter = albumAdapter
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