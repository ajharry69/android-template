package co.ke.xently.template.features.utils

import android.content.Context

fun interface ErrorState {
    fun getMessage(context: Context): String
}