package com.skillbox.unsplash.util

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText
import com.skillbox.unsplash.common.util.AutoClearedValue
import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.data.images.retrofit.model.image.detail.RemoteImageDetail
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.images.commondata.Author
import com.skillbox.unsplash.feature.images.commondata.Image
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.detail.data.Exif
import com.skillbox.unsplash.feature.images.detail.data.Location
import com.skillbox.unsplash.feature.images.detail.data.Statistic
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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

fun haveQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

fun haveTiramisu(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun RemoteImage.toImageItem(cachedImagePath: String, cachedAvatarPath: String): ImageItem {
    return ImageItem(
        Image(
            this.id,
            this.description ?: "",
            this.likes,
            this.likedByUser,
            this.urls.small,
            cachedImagePath
        ),
        Author(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAvatarPath
        )
    )
}

fun RemoteImage.toRoomImageEntity(cachedImagePath: String, cachedAvatarPath: String): ImageWithAuthorEntity {
    val authorEntity = AuthorEntity(
        this.user.id,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.medium,
        cachedAvatarPath,
        this.user.biography ?: ""
    )
    val imageEntity = ImageEntity(
        this.id,
        this.user.id,
        this.description ?: "",
        this.likes,
        this.likedByUser,
        this.urls.thumb,
        cachedImagePath
    )
    return ImageWithAuthorEntity(imageEntity, authorEntity)
}

fun ImageItem.toImageWithAuthorEntity(): ImageWithAuthorEntity {
    val authorEntity = AuthorEntity(
        this.author.id,
        this.author.name,
        this.author.nickname,
        this.author.avatarUrl,
        this.author.cachedAvatarLocation,
        this.author.biography
    )
    val imageEntity = ImageEntity(
        this.image.id,
        this.author.id,
        this.image.description,
        this.image.likes,
        this.image.likedByUser,
        this.image.url,
        this.image.cachedLocation
    )
    return ImageWithAuthorEntity(imageEntity, authorEntity)
}

fun ImageWithAuthorEntity.toImageItem(): ImageItem {
    return ImageItem(
        Image(
            this.image.id,
            this.image.description,
            this.image.likes,
            this.image.likedByUser,
            this.image.preview,
            this.image.cachedPreview
        ),
        Author(
            this.author.id,
            this.author.nickName,
            this.author.name,
            this.author.biography,
            this.author.profileImage,
            this.author.cachedProfileImage
        )
    )
}

fun RemoteImageDetail.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): DetailImageItem {
    return DetailImageItem(
        Image(this.id, this.description ?: "", this.likes, this.likedByUser, this.urls.small, cachedImagePath),
        this.width,
        this.height,
        Author(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAuthorAvatarPath
        ),
        Exif(
            this.exif.make ?: "Unknown",
            this.exif.model ?: "Unknown",
            this.exif.name ?: "Unknown",
            this.exif.focalLength ?: "Unknown",
            this.exif.aperture ?: "Unknown",
            this.exif.exposureTime ?: "Unknown",
            this.exif.iso ?: 0
        ),
        this.tags.map { it.title },
        Location(
            this.location.city,
            this.location.country,
            this.location.name,
            this.location.position.latitude,
            this.location.position.longitude
        ),
        Statistic(
            this.downloads,
            0,
            this.likes
        ),
        this.links.download
    )
}

fun TextInputEditText.textChangedFlow(): Flow<String?> {
    return callbackFlow {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(text?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        this@textChangedFlow.addTextChangedListener(textChangeListener)

        awaitClose {
            this@textChangedFlow.removeTextChangedListener(textChangeListener)
        }

    }
}
