package com.skillbox.unsplash.presentation.collections.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemCollectionBinding

import com.skillbox.unsplash.domain.model.local.CollectionModel
import com.skillbox.unsplash.util.inflate

class CollectionAdapter(
    private val onCollectionClick: (CollectionModel) -> Unit,
    private val isNetworkAvailable: () -> Boolean,
) : PagingDataAdapter<CollectionModel, CollectionAdapter.Holder>(CollectionDiffUtilCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val collectionItem = getItem(position) ?: return
        holder.bind(collectionItem, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            binding = parent.inflate(ItemCollectionBinding::inflate),
            onCollectionClick = onCollectionClick,
            isNetworkAvailable = isNetworkAvailable
        )
    }

    class CollectionDiffUtilCallback : DiffUtil.ItemCallback<CollectionModel>() {
        override fun areItemsTheSame(oldItem: CollectionModel, newItem: CollectionModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CollectionModel, newItem: CollectionModel): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        private val binding: ItemCollectionBinding,
        private val onCollectionClick: (CollectionModel) -> Unit,
        private val isNetworkAvailable: () -> Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var collectionModel: CollectionModel? = null
        private var position: Int? = null

        init {
            binding.collectionItemView.setOnClickListener {
                if (isNetworkAvailable()) {
                    onCollectionClick(collectionModel!!)
                }
            }
        }

        fun bind(collectionItem: CollectionModel, position: Int) {
            this.collectionModel = collectionItem
            this.position = position

            with(binding) {
                imageCountInCollection.text = collectionItem.count.toString()
                collectionTitle.text = collectionItem.title
                collectionAuthorName.text = collectionItem.user.name
                userNicknameTextView.text = collectionItem.user.nickname
                loadImagesFromCache(collectionItem, avatarCollectionView, collectionItemView)
            }
        }

        private fun loadImagesFromCache(
            collectionItem: CollectionModel,
            avatarImageView: ImageView,
            imageItemView: ImageView
        ) {
            Glide.with(itemView)
                .load(collectionItem.user.cachedAvatarLocation)
                .placeholder(R.drawable.user_icon_place_holder)
                .into(avatarImageView)

            Glide.with(itemView)
                .load(collectionItem.cachedCoverPhoto)
                .placeholder(R.drawable.ic_img_placeholder_foreground)
                .into(imageItemView)
        }
    }
}