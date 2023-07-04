package com.skillbox.unsplash.feature.imagelist.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.inflate

class ImageItemAdapter(private val imageItemList: List<ImageItem>) : RecyclerView.Adapter<ImageItemAdapter.Holder>() {

    override fun getItemCount() = imageItemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_fragment_image))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = imageItemList[position]
        holder.bind(imageItem)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView = view.findViewById<TextView>(R.id.authorName)
        private val nickname = view.findViewById<TextView>(R.id.authorNickName)
        private val avatarImageView = view.findViewById<ImageView>(R.id.avatarImageView)
        private val likesCount = view.findViewById<TextView>(R.id.likesCountView)
        private val activeLikesIcon = view.findViewById<ImageView>(R.id.activeLikesIconView)
        private val inactiveLikesIcon = view.findViewById<ImageView>(R.id.inactiveLikesIconView)

        fun bind(imageItem: ImageItem) {
            nameTextView.text = imageItem.authorName
            nickname.text = imageItem.authorNickname
            likesCount.text = imageItem.likes.toString()
            activeLikesIcon.visibility = if (imageItem.likedByUser) View.VISIBLE else View.GONE
            inactiveLikesIcon.visibility = if (imageItem.likedByUser) View.VISIBLE else View.GONE

            Glide.with(itemView)
                .load(imageItem.authorAvatarUrl)
                .placeholder(R.drawable.user_icon_place_holder)
                .into(avatarImageView)
        }
    }
}