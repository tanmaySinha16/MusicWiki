package com.example.musicwiki.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.Album
import com.example.musicwiki.models.ArtistX
import com.example.musicwiki.models.Track

class TagTrackAdapter:
    RecyclerView.Adapter<TagTrackAdapter.TrackViewHolder>() {

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Track>(){
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
           return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tracks, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(track.image[3].text)
                .into(findViewById(R.id.ivItemTrack))
            findViewById<TextView>(R.id.tvTrackTitle).text = track.name
            findViewById<TextView>(R.id.tvTrackArtistName).text = "Artist: ${track.artist.name}"

        }

    }

    override fun getItemCount() = differ.currentList.size
}
