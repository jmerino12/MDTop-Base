package com.alain.cursos.top

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

/****
 * Project: MD Postres
 * From: com.cursosant.android.mdpostres
 * Created by Alain Nicolás Tello on 01/01/21 at 14:06
 * Course: Professional Material Desing/Theming for Android, UX/UI.
 * More info: https://www.udemy.com/course/material-design-theming-diseno-profesional-para-android/
 * All rights reserved 2021.
 *
 * Others:
 * Android con Kotlin intensivo y práctico desde cero.
 * https://www.udemy.com/course/kotlin-intensivo/?referralCode=93D5D2FA6EF503FD0A6B
 */

class DialogSelectorFecha : DialogFragment() {
    private var listener: OnDateSetListener? = null
    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance(Locale.ROOT)
        val args = this.arguments
        if (args != null) {
            val fecha = args.getLong(FECHA)
            calendar.timeInMillis = fecha
        }
        val anyo = calendar[Calendar.YEAR]
        val mes = calendar[Calendar.MONTH]
        val dia = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(activity!!, listener, anyo, mes, dia)
    }

    companion object {
        const val FECHA = "fecha"
        const val SELECTED_DATE = "selectedDate"
    }
}