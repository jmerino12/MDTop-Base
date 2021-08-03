package com.alain.cursos.top

import android.view.View

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

internal interface OnItemClickListener {
    //fun onItemClick(artista: Artista?)
    //fun onItemClick(artista: Artista?, view: View)
    //fun onItemClick(artista: Artista?, imgPhoto: View, tvNote: View)
    //fun onItemClick(artista: Artista?, imgPhoto: View, tvNote: View, tvOrder: View, tvName: View)
    fun onItemClick(artista: Artista?, imgPhoto: View, tvNote: View)


    fun onLongItemClick(artista: Artista?)
}