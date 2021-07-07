package com.alain.cursos.top

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

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

@Table(database = TopDB::class)
class Artista : BaseModel {
    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var nombre: String? = null

    @Column
    var apellidos: String? = null

    @Column
    var fechaNacimiento: Long = 0

    @Column
    var lugarNacimiento: String? = null

    @Column
    var estatura: Short = 0

    @Column
    var notas: String? = null

    @Column
    var orden = 0

    @Column
    var fotoUrl: String? = null

    internal constructor() {}
    internal constructor(nombre: String?, apellidos: String?, fechaNacimiento: Long, lugarNacimiento: String?,
                         estatura: Short, notas: String?, orden: Int, fotoUrl: String?) {
        this.nombre = nombre
        this.apellidos = apellidos
        this.fechaNacimiento = fechaNacimiento
        this.lugarNacimiento = lugarNacimiento
        this.estatura = estatura
        this.notas = notas
        this.orden = orden
        this.fotoUrl = fotoUrl
    }

    val nombreCompleto: String
        get() = "$nombre $apellidos"

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val artista = o as Artista
        return id == artista.id
    }

    override fun hashCode(): Int {
        return (id xor (id ushr 32)).toInt()
    }

    companion object {
        const val ORDEN = "orden"
        const val ID = "id"
    }
}