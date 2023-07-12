package com.skillbox.unsplash.feature.imagelist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemImageBinding
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.inflate

class ImageItemAdapter(
    private val onLikeClicked: (String, Boolean) -> Unit
) : RecyclerView.Adapter<ImageItemAdapter.Holder>() {
    private var differ = AsyncListDiffer(this, ImageDiffUtilCallback())

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(ItemImageBinding::inflate), onLikeClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = differ.currentList[position]
        holder.bind(imageItem)
    }

    fun setImages(newImages: List<ImageItem>) {
        differ.submitList(newImages)
    }

    class ImageDiffUtilCallback : DiffUtil.ItemCallback<ImageItem>() {
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        private val binding: ItemImageBinding,
        onLikeClicked: (String, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentImageId: String? = null

        init {
            binding.activeLikesIconView.setOnClickListener {
                currentImageId?.let { onLikeClicked(it, false) }
            }

            binding.inactiveLikesIconView.setOnClickListener {
                currentImageId?.let { onLikeClicked(it, true) }
            }
        }

        fun bind(imageItem: ImageItem) {
            this.currentImageId = imageItem.id
            with(binding) {
                authorName.text = imageItem.authorName
                authorNickName.text = imageItem.authorNickname
                likesCountView.text = imageItem.likes.toString()
                activeLikesIconView.visibility = if (imageItem.likedByUser) View.VISIBLE else View.GONE
                inactiveLikesIconView.visibility = if (!imageItem.likedByUser) View.VISIBLE else View.GONE


                Glide.with(itemView)
                    .load(imageItem.authorAvatarUrl)
                    .placeholder(R.drawable.user_icon_place_holder)
                    .error(R.drawable.user_icon_place_holder)
                    .into(avatarImageView)

                Glide.with(itemView)
                    .load(imageItem.imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(imageItemView)
            }
        }
    }
}