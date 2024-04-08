package com.skillbox.unsplash.common

sealed class SearchCondition {
    class SearchQueryImages(val searchQuery: String) : SearchCondition()
    class UserImages(val userName: String) : SearchCondition()
    class LikedByUserImages(val userName: String) : SearchCondition()
    class CollectionImages(val collectionId: String) : SearchCondition()
    data object AllImages : SearchCondition()
}
