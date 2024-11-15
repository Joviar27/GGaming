package com.example.core.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

object TextUtils {
    fun htmlToText(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("Deprecation")
            Html.fromHtml(html)
        }
    }
}