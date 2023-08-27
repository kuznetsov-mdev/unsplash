package com.skillbox.unsplash.feature.images.list.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemImageBinding
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.util.inflate

class ImageAdapter(
    private val context: Context,
    private val onLikeClicked: (String, Int, Boolean) -> Unit,
    private val isNetworkAvailable: () -> Boolean,
    private val onImageClicked: (String) -> Unit,
) : PagingDataAdapter<ImageItem, ImageAdapter.Holder>(ImageDiffUtilCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = getItem(position) ?: return
        holder.bind(imageItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            context = context,
            binding = parent.inflate(ItemImageBinding::inflate),
            isNetworkAvailable = isNetworkAvailable,
            onLikeClicked = onLikeClicked,
            onImageClicked = onImageClicked
        )
    }

    class ImageDiffUtilCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.image.id == newItem.image.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    class Holder(
        private val context: Context,
        private val binding: ItemImageBinding,
        private val isNetworkAvailable: () -> Boolean,
        onLikeClicked: (String, Int, Boolean) -> Unit,
        private val onImageClicked: (String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentImage: ImageItem? = null
        private var imagePosition: Int? = null

        init {
            binding.activeLikesIconView.setOnClickListener {
                if (isNetworkAvailable()) {
                    currentImage?.let { currentImg ->
                        imagePosition?.let { imgPosition ->
                            currentImg.image.likedByUser = false
                            currentImg.image.likes = currentImg.image.likes - 1
                            onLikeClicked(currentImg.image.id, imgPosition, false)
                        }
                    }
                }
            }

            binding.inactiveLikesIconView.setOnClickListener {
                if (isNetworkAvailable()) {
                    currentImage?.let { currentImg ->
                        imagePosition?.let { imgPosition ->
                            currentImg.image.likedByUser = true
                            currentImg.image.likes = currentImg.image.likes + 1
                            onLikeClicked(currentImg.image.id, imgPosition, true)
                        }
                    }
                }
            }

            binding.imageItemView.setOnClickListener { onImageClicked(currentImage?.image?.id ?: "") }
        }

        fun bind(imageItem: ImageItem, position: Int) {
            this.currentImage = imageItem
            this.imagePosition = position
            with(binding) {
                authorName.text = imageItem.author.name
                authorNickName.text = imageItem.author.nickname
                likesCountView.text = imageItem.image.likes.toString()
                activeLikesIconView.visibility = if (imageItem.image.likedByUser) View.VISIBLE else View.GONE
                inactiveLikesIconView.visibility = if (!imageItem.image.likedByUser) View.VISIBLE else View.GONE

                if (isNetworkAvailable()) {
                    loadImagesFromNetwork(imageItem, avatarImageView, imageItemView)
                } else {
                    loadImagesFromCache(context, imageItem, avatarImageView, imageItemView)
                }
            }
        }

        private fun loadImagesFromNetwork(
            imageItem: ImageItem,
            avatarImageView: ImageView,
            imageItemView: ImageView
        ) {
            Glide.with(itemView)
                .load(imageItem.author.avatarUrl)
                .placeholder(R.drawable.user_icon_place_holder)
                .into(avatarImageView)

            Glide.with(itemView)
                .load(imageItem.image.url)
                .placeholder(R.drawable.ic_img_placeholder_foreground)
                .into(imageItemView)
        }

        private fun loadImagesFromCache(
            context: Context,
            imageItem: ImageItem,
            avatarImageView: ImageView,
            imageItemView: ImageView
        ) {
            Glide.with(itemView)
                .load(imageItem.author.cachedAvatarLocation)
                .placeholder(R.drawable.user_icon_place_holder)
                .into(avatarImageView)

            Glide.with(itemView)
                .load(imageItem.image.cachedLocation)
                .placeholder(R.drawable.ic_img_placeholder_foreground)
                .into(imageItemView)
        }
    }
}