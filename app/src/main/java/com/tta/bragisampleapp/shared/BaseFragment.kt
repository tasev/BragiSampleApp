package com.tta.bragisampleapp.shared

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.tta.bragisampleapp.R

open class BaseFragment : Fragment() {

    private lateinit var connectionSnackBar: Snackbar

    fun showToast(message: String) {
        Toast
            .makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }

    fun showToast(resourceId: Int) {
        showToast(getString(resourceId))
    }

    fun showDialogWithOneOption(
        message: String,
        buttonName: String,
        onButtonClick: () -> Unit,
        onCancelListener: () -> Unit
    ) {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
            builder.setCancelable(true)
            builder.setOnCancelListener {
                onCancelListener.invoke()
            }
            builder.setPositiveButton(buttonName) { dialog, _ ->
                try {
                    onButtonClick.invoke()
                    dialog.dismiss()
                } catch (e: Exception) {
                    // activity is in background
                    e.printStackTrace()
                }
            }
            builder.show()
        }
    }

    private fun initializeSnackBar(view: View, titleResource: Int, titleColorResource: Int) {
        connectionSnackBar = Snackbar
            .make(
                view,
                    getString(titleResource),
                Snackbar.LENGTH_LONG
            )
            .setDuration(LENGTH_INDEFINITE)
        try {
            connectionSnackBar.view.backgroundTintList = (ContextCompat.getColorStateList(connectionSnackBar.view.context, titleColorResource))
            (connectionSnackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).apply {
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun hideConnectionSnackBar() {
        if (!::connectionSnackBar.isInitialized) {
            return
        }
            connectionSnackBar.dismiss()
    }

    fun showConnectionSnackBar(view: View, titleResource: Int, titleColorResource: Int = R.color.color_light) {
        initializeSnackBar(view, titleResource, titleColorResource)
            connectionSnackBar.show()
    }

}