package com.sdk.wallpaperapp.util

import android.content.Context
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

fun Context.success(text: String) {
    Toasty.success(this, text, Toasty.LENGTH_LONG).show()
}
fun Context.error(text: String) {
    Toasty.error(this, text, Toasty.LENGTH_LONG).show()
}
fun Fragment.success(text: String) {
    Toasty.success(this.requireContext(), text, Toasty.LENGTH_LONG).show()
}
fun Fragment.error(text: String) {
    Toasty.error(this.requireContext(), text, Toasty.LENGTH_LONG).show()
}
