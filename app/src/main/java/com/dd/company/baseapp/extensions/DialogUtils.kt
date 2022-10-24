package com.dd.company.baseapp.extensions

import android.content.Context
import android.view.*
import com.dd.company.baseapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


//fun Context.showDialogConfirm(
//    message: String,
//    positiveBtnStr: String,
//    negativeBtnStr: String,
//    callback: () -> Unit = {},
//) {
//    val style = R.style.AlertDialogThemeForDelete
//    val builder = MaterialAlertDialogBuilder(this, style)
//    builder.apply {
//        setMessage(message)
//        setPositiveButton(positiveBtnStr) { dialog, _ ->
//            callback()
//            dialog.dismiss()
//        }
//        setNegativeButton(negativeBtnStr) { dialog, _ ->
//            dialog.dismiss()
//        }
//        setCancelable(false)
//
//    }
//    val alert = builder.show()
//    val positive = alert.getButton(AlertDialog.BUTTON_POSITIVE)
//    val negative = alert.getButton(AlertDialog.BUTTON_NEGATIVE)
//    negative.isSingleLine = true
//    positive.isSingleLine = true
//}

fun Context.showAlertDialog(title: String?, message: String?, callback: () -> Unit = {}) {
    val builder = MaterialAlertDialogBuilder(this)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton("OK") { _, _ ->
            callback()
        }
        setNegativeButton("Hủy bỏ") { _, _ ->

        }
        show()
    }
}

fun Context.showAlertDialogConfirm(title: String?, message: String?) {
    val builder = MaterialAlertDialogBuilder(this)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(getString(R.string.ok)) { _, _ ->
        }
        show()
    }
}