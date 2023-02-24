package com.example.musicwiki.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicwiki.R
import com.example.musicwiki.models.Album
import com.example.musicwiki.models.Tag
import org.w3c.dom.Text

class AlbumDetailGenreAdapter :
    RecyclerView.Adapter<AlbumDetailGenreAdapter.AlbumDetailGenreViewHolder>() {

    inner class AlbumDetailGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Tag>(){
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.name==newItem.name
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDetailGenreViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return AlbumDetailGenreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumDetailGenreViewHolder, position: Int) {
        val tag = differ.currentList[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.btnTag).text = tag.name

            setOnClickListener {
                onItemClickListener?.let { it(tag) }

            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Tag) -> Unit)? = null

    fun setOnItemClickListener(listener: (Tag) -> Unit) {
        onItemClickListener = listener
    }
}
