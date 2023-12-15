package com.skillbox.unsplash.feature.images.list.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemImageBinding
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import com.skillbox.unsplash.util.inflate

class ImageAdapter(
    private val onLikeClicked: (String, Int, Boolean) -> Unit,
    private val isNetworkAvailable: () -> Boolean,
    private val onImageClicked: (String) -> Unit,
) : PagingDataAdapter<ImageWithUserUiModel, ImageAdapter.Holder>(ImageDiffUtilCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = getItem(position) ?: return
        holder.bind(imageItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            binding = parent.inflate(ItemImageBinding::inflate),
            isNetworkAvailable = isNetworkAvailable,
            onLikeClicked = onLikeClicked,
            onImageClicked = onImageClicked
        )
    }

    class ImageDiffUtilCallback : DiffUtil.ItemCallback<ImageWithUserUiModel>() {
        override fun areItemsTheSame(oldItem: ImageWithUserUiModel, newItem: ImageWithUserUiModel): Boolean {
            return oldItem.image.id == newItem.image.id
        }

        override fun areContentsTheSame(oldItem: ImageWithUserUiModel, newItem: ImageWithUserUiModel): Boolean {
            return oldItem == newItem
        }
    }

    @SuppressLint("SetTextI18n")
    class Holder(
        private val binding: ItemImageBinding,
        private val isNetworkAvailable: () -> Boolean,
        onLikeClicked: (String, Int, Boolean) -> Unit,
        private val onImageClicked: (String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentImage: ImageWithUserUiModel? = null
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

            binding.imageItemView.setOnClickListener {
                if (isNetworkAvailable()) {
                    onImageClicked(currentImage?.image?.id ?: "")
                }
            }
        }

        fun bind(imageItem: ImageWithUserUiModel, position: Int) {
            this.currentImage = imageItem
            this.imagePosition = position

            with(binding) {
                userNameTextView.text = imageItem.author.name
                userNicknameTextView.text = imageItem.author.nickname
                likesCountView.text = imageItem.image.likes.toString()
                activeLikesIconView.visibility = if (imageItem.image.likedByUser) View.VISIBLE else View.GONE
                inactiveLikesIconView.visibility = if (!imageItem.image.likedByUser) View.VISIBLE else View.GONE

                //Todo: need replace to check if is there image in cache, use it if not - use stub
                if (isNetworkAvailable()) {
                    loadImagesFromNetwork(imageItem, avatarImageView, imageItemView)
                } else {
                    loadImagesFromCache(imageItem, avatarImageView, imageItemView)
                }
            }
        }

        private fun loadImagesFromNetwork(
            imageItem: ImageWithUserUiModel,
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
            imageItem: ImageWithUserUiModel,
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