package com.skillbox.unsplash.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.skillbox.unsplash.common.util.AutoClearedValue
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImagePreviewUrls
import com.skillbox.unsplash.data.images.retrofit.model.RemoteProfileImage
import com.skillbox.unsplash.data.images.retrofit.model.RemoteUser
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.imagelist.data.ImageItem

fun <T : Fragment> T.withArguments(action: Bundle.() -> Unit): T {
    return apply {
        val args = Bundle().apply(action)
        arguments = args
    }
}

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}

fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)


fun RemoteImage.toImageItem(): ImageItem {
    return ImageItem(
        this.id,
        this.user.id,
        this.likes,
        this.likedByUser,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.small,
        this.urls.small
    )
}

fun RemoteImage.toImageEntity(): ImageEntity {
    return ImageEntity(
        this.id,
        this.user.id,
        this.description ?: "",
        this.likes,
        this.likedByUser,
        this.urls.thumb
    )
}

fun RemoteImage.toAuthorEntity(): AuthorEntity {
    return AuthorEntity(
        this.user.id,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.small
    )
}

fun ImageWithAuthorEntity.toRemoteImage(): RemoteImage {
    val remoteUser = RemoteUser(
        this.author.id,
        this.author.name,
        this.author.nickName,
        RemoteProfileImage(
            this.author.profileImage,
            this.author.profileImage,
            this.author.profileImage
        )
    )
    return RemoteImage(
        this.image.id,
        this.image.description,
        this.image.likes,
        this.image.likedByUser,
        remoteUser,
        RemoteImagePreviewUrls(
            this.image.preview,
            this.image.preview,
            this.image.preview,
            this.image.preview,
            this.image.preview
        )
    )
}