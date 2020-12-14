package kz.kolesateam.confapp.detail

import android.content.Context
import android.content.Intent

class DetailActivityRouter {

    fun createIntent(
        context: Context
    ): Intent = Intent(context, DetailActivity::class.java)
}