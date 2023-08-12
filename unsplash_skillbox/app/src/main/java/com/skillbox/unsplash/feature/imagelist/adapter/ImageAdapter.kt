package com.skillbox.unsplash.feature.imagelist.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemImageBinding
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.inflate

class ImageAdapter(
    private val onLikeClicked: (String, Int, Boolean) -> Unit,
    private val isNetworkAvailable: () -> Boolean
) : PagingDataAdapter<ImageItem, ImageAdapter.Holder>(ImageDiffUtilCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = getItem(position) ?: return
        holder.bind(imageItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(ItemImageBinding::inflate), isNetworkAvailable, onLikeClicked)
    }

    class ImageDiffUtilCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    class Holder(
        private val binding: ItemImageBinding,
        isNetworkAvailable: () -> Boolean,
        onLikeClicked: (String, Int, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentImage: ImageItem? = null
        private var imagePosition: Int? = null

        init {
            binding.activeLikesIconView.setOnClickListener {
                if (isNetworkAvailable()) {
                    currentImage?.let { image ->
                        imagePosition?.let { imgPosition ->
                            image.likedByUser = false
                            image.likes = image.likes - 1
                            onLikeClicked(image.id, imgPosition, false)
                        }
                    }
                }
            }

            binding.inactiveLikesIconView.setOnClickListener {
                if (isNetworkAvailable()) {
                    currentImage?.let { image ->
                        imagePosition?.let { imgPosition ->
                            image.likedByUser = true
                            image.likes = image.likes + 1
                            onLikeClicked(image.id, imgPosition, true)
                        }
                    }
                }
            }
        }

        fun bind(imageItem: ImageItem, position: Int) {
            this.currentImage = imageItem
            this.imagePosition = position
            with(binding) {
                authorName.text = imageItem.authorName
                authorNickName.text = imageItem.authorNickname
                likesCountView.text = imageItem.likes.toString()
                activeLikesIconView.visibility = if (imageItem.likedByUser) View.VISIBLE else View.GONE
                inactiveLikesIconView.visibility = if (!imageItem.likedByUser) View.VISIBLE else View.GONE

                Glide.with(itemView)
                    .load(imageItem.authorAvatarUrl)
                    .placeholder(R.drawable.user_icon_place_holder)
                    .into(avatarImageView)

                Glide.with(itemView)
                    .load(imageItem.imageUrl)
                    .placeholder(R.drawable.ic_img_placeholder_foreground)
                    .into(imageItemView)
            }
        }
    }
}