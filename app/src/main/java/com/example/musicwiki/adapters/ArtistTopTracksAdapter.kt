package com.example.musicwiki.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.AlbumXX
import com.example.musicwiki.models.TrackXX

class ArtistTopTracksAdapter() :
    RecyclerView.Adapter<ArtistTopTracksAdapter.ArtistTopTracksViewHolder>() {

    inner class ArtistTopTracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<TrackXX>(){
        override fun areItemsTheSame(oldItem: TrackXX, newItem: TrackXX): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: TrackXX, newItem: TrackXX): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistTopTracksViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tracks, parent, false)
        return ArtistTopTracksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistTopTracksViewHolder, position: Int) {
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
