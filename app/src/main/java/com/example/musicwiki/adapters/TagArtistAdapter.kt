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

class TagArtistAdapter() :
    RecyclerView.Adapter<TagArtistAdapter.ArtistsViewHolder>() {

    inner class ArtistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object :DiffUtil.ItemCallback<ArtistX>(){
        override fun areItemsTheSame(oldItem: ArtistX, newItem: ArtistX): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArtistX, newItem: ArtistX): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_artists, parent, false)
        return ArtistsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistsViewHolder, position: Int) {
        val artist = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(artist.image[3].text)
                .into(findViewById(R.id.ivItemArtist))
            findViewById<TextView>(R.id.tvArtistName).text = artist.name

            setOnClickListener {
                onItemClickListener?.let { it(artist) }
            }
        }
    }
    override fun getItemCount() = differ.currentList.size
    private var onItemClickListener: ((ArtistX) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArtistX) -> Unit) {
        onItemClickListener = listener
    }
}
