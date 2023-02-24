package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.musicwiki.R
import com.example.musicwiki.adapters.TabPagerAdapter
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.GenreViewModel
import com.example.musicwiki.viewmodels.GenreViewModel.Companion.TAG
import com.google.android.material.tabs.TabLayout

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GenreDetailFragment:Fragment(R.layout.fragment_genre_detail) {
    private val viewModel:GenreViewModel by viewModels()
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    val args:GenreFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvGenreText = view.findViewById<TextView>(R.id.tvGenreText)
        val tvGenreName = view.findViewById<TextView>(R.id.tvGenreName)
        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val tag = args.tag

        TAG  = tag


        val tabPagerAdapter = TabPagerAdapter(childFragmentManager)
        viewPager.adapter = tabPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewModel.tagInfo.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { tagInfoResponse ->
                        tvGenreText.text = tagInfoResponse.tag.wiki.summary
                        tvGenreName.text = tagInfoResponse.tag.name
                        Log.d("tagInfoResponse.tag.wiki.summary",tagInfoResponse.tag.wiki.summary)
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
     private fun hideProgressBar()
    {
        requireView().findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE

    }
    private fun showProgressBar()
    {
        requireView().findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE

    }

}