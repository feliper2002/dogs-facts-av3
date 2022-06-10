package com.atividade.devmobile.randomdogs.utils

import android.content.Context
import android.widget.Toast

class AppToasts {

    companion object {
        fun show(context: Context, text: String) {
            Toast.makeText(context, "$text", Toast.LENGTH_SHORT).show()
        }
    }

}