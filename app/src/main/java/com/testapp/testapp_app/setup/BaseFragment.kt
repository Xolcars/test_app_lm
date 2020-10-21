package com.testapp.testapp_app.setup

import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.testapp.testapp_app.R
import com.testapp.testapp_app.setup.extensions.hideProgressDialog
import com.testapp.testapp_app.setup.extensions.showProgressDialog
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {
    //region Vars
    protected val prefs: Prefs by inject()
    //endregion

    //region Override Methods
    //endregion

    //region Methods
    fun checkAndRequestPermission(permission: String, codeRequest: Int): Boolean {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                    return true
                } else {
                    ActivityCompat.requestPermissions(it, arrayOf(permission), codeRequest)
                }
            } else {
                return true
            }
            return false
        }
        return false
    }

    fun showMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun showMessageActionAccentColor(
        message: String,
        view: View,
        actionTitle: String,
        colorStateList: ColorStateList,
        onClickAction: View.OnClickListener
    ) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            //.setActionTextColor(ContextCompat.getColor(view.context, R.color.white_gray))
            .setAction(actionTitle, onClickAction)
            .setActionTextColor(colorStateList)
            .show()
    }

    fun showMessageAccentColor(message: String, v: View) {
        with(Snackbar.make(v, message, Snackbar.LENGTH_SHORT)) {
            view.setBackgroundColor(ContextCompat.getColor(v.context, R.color.colorAccent))
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(Color.WHITE)
            show()
        }
    }

    fun showError(error: String, v: View) {
        with(Snackbar.make(v, error, Snackbar.LENGTH_LONG)) {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    v.context,
                    android.R.color.holo_red_dark
                )
            )
            val tv = view.findViewById(R.id.snackbar_text) as TextView
            tv.setTextColor(Color.WHITE)
            show()
        }
    }

    fun showProgressDialog() {
        if (activity != null) {
            (activity as BaseActivity).showProgressDialog()
        }
    }

    fun hideProgressDialog() {
        if (activity != null) {
            (activity as BaseActivity).hideProgressDialog()
        }
    }

    fun hideKeyboard() {
        if (activity != null) {
            (activity as BaseActivity).hideKeyboard()
        }
    }
    //endregion
}