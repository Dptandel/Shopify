package com.app.shopify.dialogs

import androidx.fragment.app.Fragment
import com.app.shopify.R
import com.app.shopify.databinding.DialogResetPasswordBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setUpBottomSheetDialog(
    onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    val dialogBinding = DialogResetPasswordBinding.inflate(layoutInflater)
    dialog.setContentView(dialogBinding.root)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    dialogBinding.apply {
        btnSendResetPassword.setOnClickListener {
            val email = edtResetPassword.text.toString().trim()
            onSendClick(email)
            dialog.dismiss()
        }
        btnCancelResetPassword.setOnClickListener {
            dialog.dismiss()
        }
    }
}