package com.example.musicwiki.adapters

import com.example.musicwiki.models.AlbumXX

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
import com.example.musicwiki.models.ArtistX
import org.w3c.dom.Text

class ArtistTopAlbumsAdapter() :
    RecyclerView.Adapter<ArtistTopAlbumsAdapter.ArtistTopAlbumsViewHolder>() {

    inner class ArtistTopAlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<AlbumXX>(){
        override fun areItemsTheSame(oldItem: AlbumXX, newItem: AlbumXX): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: AlbumXX, newItem: AlbumXX): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistTopAlbumsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return ArtistTopAlbumsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtistTopAlbumsViewHolder, position: Int) {
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

    private var onItemClickListener: ((AlbumXX) -> Unit)? = null

    fun setOnItemClickListener(listener: (AlbumXX) -> Unit) {
        onItemClickListener = listener
    }
}
