package com.varivoda.igor.autokola_testovi2019.ui.test.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.databinding.CancelExamPopupBinding
import com.varivoda.igor.autokola_testovi2019.ui.shared.layoutInflater
import com.varivoda.igor.autokola_testovi2019.ui.shared.setFullSizeDialog
import com.varivoda.igor.autokola_testovi2019.ui.shared.setWindowAttributes

class CancelExamPopup {

    companion object{
        fun getPopup(fragmentActivity: FragmentActivity, context: Context , isTimeExpired: Boolean, onConfirm: () -> Unit): AlertDialog {
            val builder = AlertDialog.Builder(context, R.style.fullScreenDialog)
            val binding = DataBindingUtil.inflate<CancelExamPopupBinding>(context.layoutInflater,R.layout.cancel_exam_popup, null, false)
            builder.setView(binding.root)

            val dialog = builder.create()

            fragmentActivity.setFullSizeDialog(binding.root)
            dialog.setWindowAttributes()

            if(isTimeExpired){
                binding.title.text = "Isteklo vam je vrijeme! Niste položili ispit!"
                binding.cancel.visibility = View.GONE
                binding.confirm.text = "Završi ispit"
                binding.confirm.setOnClickListener {
                    onConfirm.invoke()
                    dialog.dismiss()
                }
            }else{
                binding.cancel.setOnClickListener {
                    onConfirm.invoke()
                    dialog.dismiss()
                }

                binding.confirm.setOnClickListener {
                    dialog.dismiss()
                }
            }

            return dialog
        }
    }
}