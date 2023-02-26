package com.example.musicwiki.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.adapters.AlbumDetailGenreAdapter
import com.example.musicwiki.adapters.ArtistTopAlbumsAdapter
import com.example.musicwiki.adapters.ArtistTopTracksAdapter
import com.example.musicwiki.models.AlbumXX
import com.example.musicwiki.models.Tag
import com.example.musicwiki.models.TrackXX
import com.example.musicwiki.util.Resource
import com.example.musicwiki.viewmodels.ArtistDetailViewModel
import com.example.musicwiki.viewmodels.ArtistDetailViewModel.Companion.ARTIST_DETAIL_NAME_ARGS
import com.example.musicwiki.viewmodels.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailFragment:Fragment(R.layout.fragment_artist_detail) {
    private val viewModel: ArtistDetailViewModel by viewModels()
    lateinit var genreAdapter: AlbumDetailGenreAdapter
    lateinit var trackAdapter: ArtistTopTracksAdapter
    lateinit var albumAdapter: ArtistTopAlbumsAdapter
    val args:AlbumListFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTagsRecycleView()
        setupTracksRecyclerView()
        setupAlbumsRecyclerView()

        var tagList: MutableList<Tag>
        var artistTopAlbumsList: List<AlbumXX>
        var artistTopTracksList: List<TrackXX>

        var artistCoverUrl:String
        val artistName = view.findViewById<TextView>(R.id.tvArtistDetailName)
        val playCounter = view.findViewById<TextView>(R.id.tvPlayCountNumber)
        val followers = view.findViewById<TextView>(R.id.tvFollowersNumber)
        val artistDetail = view.findViewById<TextView>(R.id.tvArtistDetail)

        val artistDetailName = args.artistName
        ARTIST_DETAIL_NAME_ARGS = artistDetailName

        albumAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("albumName",it.name)
                putString("artistName",it.artist.name)
            }
            findNavController().navigate(
                R.id.action_artistDetailFragment_to_albumDetailFragment,
                bundle
            )
        }


        viewModel.tagList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {

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
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
        viewModel.artistInfo.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { artistInfoResponse ->
                        artistCoverUrl = artistInfoResponse.artist.image[3].text
                        Glide.with(requireContext())
                            .load(artistCoverUrl)
                            .centerCrop()
                            .into(requireView().findViewById(R.id.ivArtistDetail))
                        artistDetail.text = artistInfoResponse.artist.bio.summary
                        artistName.text = artistInfoResponse.artist.name
                        requireView().findViewById<TextView>(R.id.tvFollowers).text="Followers"
                        requireView().findViewById<TextView>(R.id.tvPlaycount).text ="Playcount"
                        requireView().findViewById<TextView>(R.id.tvArtistTopAlbums).text="Top Albums"
                        requireView().findViewById<TextView>(R.id.tvArtistTopTracks).text ="Top Tracks"

                        val followersText = artistInfoResponse.artist.stats.listeners.toInt()
                        if(followersText>=1000000)
                            followers.text = followersText.toMillions()
                        else
                            followers.text=followersText.toThousands()



                        val playCounterText = artistInfoResponse.artist.stats.playcount.toInt()
                        if(playCounterText>=1000000)
                            playCounter.text = playCounterText.toMillions()
                        else
                            playCounter.text = playCounterText.toThousands()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
        viewModel.artistTopAlbums.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { artistTopAlbumsListResponse ->
                        artistTopAlbumsList = artistTopAlbumsListResponse.topalbums.album
                        albumAdapter.differ.submitList(artistTopAlbumsList)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
        viewModel.artistTopTracks.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { artistTopTracksListResponse ->
                        artistTopTracksList = artistTopTracksListResponse.toptracks.track
                        trackAdapter.differ.submitList(artistTopTracksList)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        })
        genreAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString("tag",it.name)
            }
            findNavController().navigate(R.id.action_artistDetailFragment_to_genreDetailFragment,
                bundle)
        }
    }

    private fun setupAlbumsRecyclerView() {
        albumAdapter = ArtistTopAlbumsAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvArtistTopAlbums).apply {
            adapter = albumAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupTracksRecyclerView() {
        trackAdapter = ArtistTopTracksAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvArtistTopTracks).apply {
            adapter = trackAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupTagsRecycleView() {
        genreAdapter = AlbumDetailGenreAdapter()
        requireView().findViewById<RecyclerView>(R.id.rvArtistDetailTags).apply {
            adapter = genreAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun Int.toMillions(): String {
        val millions = this / 1000000
        val remainder = this % 1000000 / 100000
        return if (remainder == 0) "$millions M" else "%.1f M".format(millions + remainder / 10.0)
    }

    private fun Int.toThousands(): String {
        val thousands = this / 1000
        val remainder = this % 1000 / 100
        return if (remainder == 0) "$thousands K" else "%.1f K".format(thousands + remainder / 10.0)
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

