package com.testapp.testapp_app.setup.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.testapp.testapp_app.R
import com.testapp.testapp_app.setup.BaseActivity

fun BaseActivity.showProgressDialog() {
    hideProgressDialog()
    Dialog(this).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setContentView(R.layout.dialog_progress)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
        this@showProgressDialog.progressDialog = this
    }
}

fun BaseActivity.hideProgressDialog() {
    this.progressDialog?.dismiss()
}
