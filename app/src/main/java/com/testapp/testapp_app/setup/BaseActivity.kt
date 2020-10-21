package com.testapp.testapp_app.setup

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity: AppCompatActivity() {
    //region Vars
    var progressDialog: Dialog? = null
    //endregion

    //region Override Methods
    //endregion

    //region Methods
    fun showMessage(message: String, view: View = this.findViewById(android.R.id.content)) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showError(error: String, v: View = this.findViewById(android.R.id.content)) {
        with(Snackbar.make(v, error, Snackbar.LENGTH_LONG)) {
            view.setBackgroundColor(ContextCompat.getColor(v.context, android.R.color.holo_red_dark))
            show()
        }
    }

    fun hideKeyboard() {
        with(getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
            this.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.SHOW_FORCED
            )
        }
    }

    fun checkAndRequestPermission(permission: String, codeRequest: Int): Boolean {
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), codeRequest)
            }
        } else {
            return true
        }
        return false
    }
    //endregion

    companion object {
        private val LOGTAG: String = BaseActivity::class.java.simpleName
    }
}