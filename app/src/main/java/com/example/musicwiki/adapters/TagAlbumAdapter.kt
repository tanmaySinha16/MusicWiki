package com.example.musicwiki.adapters

import android.content.Context
import android.util.Log
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
import org.w3c.dom.Text

class TagAlbumAdapter() :
    RecyclerView.Adapter<TagAlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Album>(){
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(album.image[3].text)
                .into(findViewById(R.id.ivItemAlbum))

        findViewById<TextView>(R.id.tvTitle).text = "Title: ${album.name.toString()}"
            findViewById<TextView>(R.id.tvArtist).text = "Artist: ${album.artist.name.toString()}"

            setOnClickListener {
                onItemClickListener?.let { it(album) }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Album) -> Unit)? = null

    fun setOnItemClickListener(listener: (Album) -> Unit) {
        onItemClickListener = listener
    }

}
