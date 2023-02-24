package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumDetailGenreAdapter
import com.example.musicwiki.models.Tag
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.AlbumDetailViewModel
import com.example.musicwiki.viewmodels.TopTagsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment:Fragment(R.layout.fragment_genre){
    private val tagsViewModel: TopTagsViewModel by viewModels()
    lateinit var tagAdapter: AlbumDetailGenreAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        var tagList:MutableList<Tag> = mutableListOf()
        var initialList:MutableList<Tag>


        tagsViewModel.tagList.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagListResponse ->
                        tagList = tagListResponse.topTags.tag
                        initialList = tagList.subList(0,9)
                        tagAdapter.differ.submitList(initialList)
                        requireView().findViewById<TextView>(R.id.tvMore).visibility=View.VISIBLE
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
        tagAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("tag",it.name)
            }
            findNavController().navigate(R.id.action_genreFragment_to_genreDetailFragment,
            bundle)
        }

        requireView().findViewById<TextView>(R.id.tvMore).setOnClickListener {
            tagAdapter.differ.submitList(tagList)
            it.visibility =  View.GONE
        }

    }

    private fun setupRecycleView() {
        tagAdapter = AlbumDetailGenreAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvGenres).apply {
            adapter = tagAdapter
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