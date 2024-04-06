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
import androidx.annotation.NavigationRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputEditText
import com.skillbox.unsplash.R
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

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity()
        .supportFragmentManager
        .findFragmentById(R.id.startContainerView) as NavHostFragment?

    return topLevelHost?.navController ?: findNavController()
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

fun haveQ(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}

fun haveTiramisu(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
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

fun NavController.resetNavGraph(@NavigationRes navGraph: Int) {
    val newGraph = navInflater.inflate(navGraph)
    graph = newGraph
}
