package com.dam.ciclismoApp.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dam.ciclismoApp.R

object DialogManager {
    private var loadingDialog: AppCompatDialog? = null
    fun showConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        onConfirm: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { _, _ -> onConfirm?.invoke() }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    fun showErrorDialog(
        context: Context,
        message: String
    ) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
    fun showSuccessDialog(
        context: Context,
        message: String,
        onDismiss: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle("Éxito")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> onDismiss?.invoke() }
            .show()
    }

    fun showLoadingDialog(context: Context) {
        if (loadingDialog != null && loadingDialog!!.isShowing) return // Evitar múltiples instancias

        val dialog = AppCompatDialog(context)
        dialog.setCancelable(false) // Evita que el usuario lo cierre manualmente
        dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_loading, null))
        loadingDialog = dialog
        dialog.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    fun showCustomDialog(
        context: Context,
        binding: ViewBinding,
        isCancelableOnTouchOutside: Boolean,
        onDialogReady: (Dialog) -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout((context.resources.displayMetrics.widthPixels * 0.9).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(isCancelableOnTouchOutside)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        onDialogReady(dialog)
        dialog.show()
    }




}


class FullScreenDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_FRAGMENT_CLASS = "fragment_class"
        private const val ARG_FRAGMENT_ARGS = "fragment_args"

        fun newInstance(fragmentClass: Class<out Fragment>, args: Bundle? = null): FullScreenDialogFragment {
            return FullScreenDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FRAGMENT_CLASS, fragmentClass.name)
                    putBundle(ARG_FRAGMENT_ARGS, args)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fullscreen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentClassName = arguments?.getString(ARG_FRAGMENT_CLASS)
        val fragmentArgs = arguments?.getBundle(ARG_FRAGMENT_ARGS)

        if (fragmentClassName != null) {
            val fragmentClass = Class.forName(fragmentClassName).asSubclass(Fragment::class.java)
            val fragment = fragmentClass.newInstance().apply {
                arguments = fragmentArgs // Pasar los datos al Fragment
            }

            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}