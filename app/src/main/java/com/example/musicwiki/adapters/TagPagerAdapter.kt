package com.example.musicwiki.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.musicwiki.ui.fragments.AlbumListFragment
import com.example.musicwiki.ui.fragments.ArtistListFragment
import com.example.musicwiki.ui.fragments.TrackListFragment


class TabPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AlbumListFragment()
            1 -> ArtistListFragment()
            2 -> TrackListFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Albums"
            1 -> "Artists"
            2 -> "Tracks"
            else -> null
        }
    }
}
