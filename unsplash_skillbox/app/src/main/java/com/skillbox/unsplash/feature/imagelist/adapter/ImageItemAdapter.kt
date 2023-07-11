package com.skillbox.unsplash.feature.imagelist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemImageBinding
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.inflate

class ImageItemAdapter(
    private val onLikeClicked: (Boolean) -> Unit
) : RecyclerView.Adapter<ImageItemAdapter.Holder>() {

    private var images: List<ImageItem> = emptyList()

    override fun getItemCount() = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(ItemImageBinding::inflate), onLikeClicked)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val imageItem = images[position]
        holder.bind(imageItem)
    }

    fun setImages(newImages: List<ImageItem>) {
        this.images = newImages
        notifyDataSetChanged()
    }

    class Holder(
        private val binding: ItemImageBinding,
        onLikeClicked: (Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.activeLikesIconView.setOnClickListener {
                onLikeClicked(false)
                binding.activeLikesIconView.visibility = View.GONE
                binding.inactiveLikesIconView.visibility = View.VISIBLE

            }

            binding.inactiveLikesIconView.setOnClickListener {
                onLikeClicked(true)
                binding.inactiveLikesIconView.visibility = View.GONE
                binding.activeLikesIconView.visibility = View.VISIBLE
            }
        }

        fun bind(imageItem: ImageItem) {
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