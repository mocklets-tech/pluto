package com.mocklets.pluto.utilities.extensions

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

const val KEY_EXTRA = "extra"

fun Fragment.setParcelExtra(parcelable: Parcelable?, key: String = KEY_EXTRA) {
    arguments = (arguments ?: Bundle()).apply {
        putParcelable(key, parcelable)
    }
}

fun Fragment.setStringExtra(value: String?, key: String = KEY_EXTRA) {
    arguments = (arguments ?: Bundle()).apply {
        putString(key, value)
    }
}

fun <T : Parcelable> Fragment.getParcelExtra(key: String = KEY_EXTRA): T? = arguments?.getParcelable(key)

inline fun <reified T : Parcelable> Fragment.lazyParcelExtra(key: String = KEY_EXTRA): Lazy<T?> =
    lazy { this.getParcelExtra(key) }
