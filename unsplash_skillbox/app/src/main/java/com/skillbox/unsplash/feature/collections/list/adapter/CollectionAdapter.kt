package com.skillbox.unsplash.feature.collections.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skillbox.unsplash.R
import com.skillbox.unsplash.databinding.ItemCollectionBinding

import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import com.skillbox.unsplash.util.inflate

class CollectionAdapter(
    private val onCollectionClick: (CollectionUiModel) -> Unit,
    private val isNetworkAvailable: () -> Boolean,
) : PagingDataAdapter<CollectionUiModel, CollectionAdapter.Holder>(CollectionDiffUtilCallback()) {

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

    class CollectionDiffUtilCallback() : DiffUtil.ItemCallback<CollectionUiModel>() {
        override fun areItemsTheSame(oldItem: CollectionUiModel, newItem: CollectionUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CollectionUiModel, newItem: CollectionUiModel): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        private val binding: ItemCollectionBinding,
        private val onCollectionClick: (CollectionUiModel) -> Unit,
        private val isNetworkAvailable: () -> Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {
        private var collectionUiModel: CollectionUiModel? = null
        private var position: Int? = null

        init {
            binding.collectionItemView.setOnClickListener {
                if (isNetworkAvailable()) {
                    onCollectionClick(collectionUiModel!!)
                }
            }
        }

        fun bind(collectionItem: CollectionUiModel, position: Int) {
            this.collectionUiModel = collectionItem
            this.position = position

            with(binding) {
                imageCountInCollection.text = collectionItem.count.toString()
                collectionTitle.text = collectionItem.title
                collectionAuthorName.text = collectionItem.user.name
                authorNickName.text = collectionItem.user.nickname
                loadImagesFromCache(collectionItem, avatarCollectionView, collectionItemView)
            }
        }

        private fun loadImagesFromCache(
            collectionItem: CollectionUiModel,
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