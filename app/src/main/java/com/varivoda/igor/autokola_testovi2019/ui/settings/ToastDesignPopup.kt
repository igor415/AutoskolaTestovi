package com.varivoda.igor.autokola_testovi2019.ui.settings

import android.app.AlertDialog
import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.varivoda.igor.autokola_testovi2019.R
import com.varivoda.igor.autokola_testovi2019.databinding.CancelExamPopupBinding
import com.varivoda.igor.autokola_testovi2019.databinding.ToastDesignPopupBinding
import com.varivoda.igor.autokola_testovi2019.ui.shared.layoutInflater
import com.varivoda.igor.autokola_testovi2019.ui.shared.setFullSizeDialog
import com.varivoda.igor.autokola_testovi2019.ui.shared.setWindowAttributes

class ToastDesignPopup {


    fun open(context: Context,
             fragmentActivity: FragmentActivity,
             currentFilter: String,
             onFilterSelected: (String) -> Unit
            ): AlertDialog {

        val builder = AlertDialog.Builder(context, R.style.fullScreenDialog)
        val binding = DataBindingUtil.inflate<ToastDesignPopupBinding>(context.layoutInflater,R.layout.toast_design_popup, null, false)
        builder.setView(binding.root)



        when(currentFilter){
            "Default" -> binding.defaultFilter.isChecked = true
            "Dnevna" -> binding.lightFilter.isChecked = true
            "Noćna" -> binding.darkFilter.isChecked = true
        }

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)

        fragmentActivity.setFullSizeDialog(binding.root)
        dialog.setWindowAttributes()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.defaultFilter -> {
                    onFilterSelected.invoke("Default")
                    dialog.dismiss()
                }
                R.id.lightFilter -> {
                    onFilterSelected.invoke("Dnevna")
                    dialog.dismiss()
                }
                R.id.darkFilter -> {
                    onFilterSelected.invoke("Noćna")
                    dialog.dismiss()
                }
            }
        }

        binding.dismissPopupBackground.setOnClickListener {
            dialog.dismiss()
        }


        return dialog
    }
}