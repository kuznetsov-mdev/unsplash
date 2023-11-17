package com.skillbox.unsplash.data.common

sealed class SearchCondition {
    class SearchString(val searchQuery: String) : SearchCondition()
    class CollectionImages(val collectionId: String) : SearchCondition()
    data object Empty : SearchCondition()
}
