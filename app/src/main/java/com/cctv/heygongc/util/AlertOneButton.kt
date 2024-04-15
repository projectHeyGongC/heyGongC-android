package com.cctv.heygongc.util

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//필드 context 힐트 사용 방법
//1. @AndroidEntryPoint
//2. @Inject constructor
//3.  필드   @Inject @ActivityContext
class AlertOneButton constructor(context: Context, title: String, msg: String, buttonMsg: String, listener: DialogInterface.OnClickListener?){

    private val alertDialog: AlertDialog

    init {
        alertDialog = AlertDialog.Builder(context)
            .setTitle(title) // Dialog Title
            .setMessage(msg) // Dialog Message
            .setPositiveButton(buttonMsg, listener)
            .create()
    }

    fun show() {
        alertDialog.show()
    }
}