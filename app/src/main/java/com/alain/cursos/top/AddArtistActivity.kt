package com.alain.cursos.top

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
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

class AddArtistActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.imgFoto)
    var imgFoto: AppCompatImageView? = null

    @JvmField
    @BindView(R.id.etNombre)
    var etNombre: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etApellidos)
    var etApellidos: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etFechaNacimiento)
    var etFechaNacimiento: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etEstatura)
    var etEstatura: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etLugarNacimiento)
    var etLugarNacimiento: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etNotas)
    var etNotas: TextInputEditText? = null

    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: MaterialToolbar? = null

    @JvmField
    @BindView(R.id.tilNombre)
    var tilNombre: TextInputLayout? = null

    @JvmField
    @BindView(R.id.tilApellido)
    var tilApellido: TextInputLayout? = null

    @JvmField
    @BindView(R.id.tilEstatura)
    var tilEstura: TextInputLayout? = null

    private var mArtista: Artista? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_artist)
        ButterKnife.bind(this)

        configActionBar()
        configArtista(intent)
        configCalendar()
    }

    private fun configActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun configArtista(intent: Intent) {
        mArtista = Artista()
        mArtista!!.fechaNacimiento = System.currentTimeMillis()
        mArtista!!.orden = intent.getIntExtra(Artista.ORDEN, 0)
    }

    private fun configCalendar() {
        etFechaNacimiento!!.setText(
            SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(
                System.currentTimeMillis()
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finishAfterTransition()//finish()
            R.id.action_save -> saveArtist()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveArtist() {
        if (validateFields() && etNombre!!.text != null && etApellidos!!.text != null &&
            etEstatura!!.text != null && etLugarNacimiento!!.text != null && etNotas!!.text != null
        ) {
            mArtista!!.nombre = etNombre!!.text.toString().trim { it <= ' ' }
            mArtista!!.apellidos = etApellidos!!.text.toString().trim { it <= ' ' }
            mArtista!!.estatura = etEstatura!!.text.toString().trim { it <= ' ' }.toShort()
            mArtista!!.lugarNacimiento = etLugarNacimiento!!.text.toString().trim { it <= ' ' }
            mArtista!!.notas = etNotas!!.text.toString().trim { it <= ' ' }

            try {
                mArtista!!.save()
                Log.i("DBFlow", "Inserción correcta de datos.")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("DBFlow", "Error al insertar datos.")
            }
            finishAfterTransition()
            //finish()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (etEstatura!!.text != null && (etEstatura!!.text.toString().trim { it <= ' ' }
                .isEmpty() ||
                    Integer.valueOf(
                        etEstatura!!.text.toString()
                            .trim { it <= ' ' }) < resources.getInteger(R.integer.estatura_min))
        ) {
            //etEstatura!!.error = getString(R.string.addArtist_error_estaturaMin)
            tilEstura!!.error = getString(R.string.addArtist_error_estaturaMin)
            tilEstura!!.requestFocus()
            isValid = false
        } else {
            tilEstura!!.error = null
        }

        if (etApellidos!!.text != null && etApellidos!!.text.toString().trim { it <= ' ' }
                .isEmpty()) {
            //etApellidos!!.error = getString(R.string.addArtist_error_required)
            tilApellido!!.error = getString(R.string.addArtist_error_required)
            tilApellido!!.requestFocus()
            isValid = false
        } else {
            tilApellido!!.error = null
        }

        if (etNombre!!.text != null && etNombre!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            //etNombre!!.error = getString(R.string.addArtist_error_required)
            tilNombre!!.error = getString(R.string.addArtist_error_required)
            tilNombre!!.requestFocus()
            isValid = false
        } else {
            tilNombre!!.error = null

        }

        return isValid
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_PHOTO_PICKER) {
                configImageView(data!!.dataString)
            }
        }
    }

    @OnClick(R.id.etFechaNacimiento)
    fun onSetFecha() {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTheme(R.style.PickerDialogCut)
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
            format.timeZone = TimeZone.getTimeZone("UTC")
            etFechaNacimiento!!.setText(format.format(it))
            mArtista!!.fechaNacimiento = it
        }
        picker.show(supportFragmentManager, picker.toString())
    }

    @OnClick(R.id.imgDeleteFoto, R.id.imgFromGallery, R.id.imgFromUrl)
    fun imageEvents(view: View) {
        when (view.id) {
            R.id.imgDeleteFoto -> {
                val builder = MaterialAlertDialogBuilder(this)//AlertDialog.Builder(this)
                    .setTitle(R.string.detalle_dialogDelete_title)
                    .setMessage(
                        String.format(
                            Locale.ROOT,
                            getString(R.string.detalle_dialogDelete_message),
                            mArtista!!.nombreCompleto
                        )
                    )
                    .setPositiveButton(R.string.label_dialog_delete) { dialogInterface: DialogInterface?, i: Int ->
                        configImageView(
                            null
                        )
                    }
                    .setNegativeButton(R.string.label_dialog_cancel, null)
                builder.show()
            }
            R.id.imgFromGallery -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/jpeg"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startActivityForResult(
                    Intent.createChooser(
                        intent,
                        getString(R.string.detalle_chooser_title)
                    ), RC_PHOTO_PICKER
                )
            }
            R.id.imgFromUrl -> showAddPhotoDialog()
        }
    }

    private fun showAddPhotoDialog() {
        val etFotoUrl = EditText(this)
        val builder = MaterialAlertDialogBuilder(this)//AlertDialog.Builder(this)
            .setTitle(R.string.addArtist_dialogUrl_title)
            .setPositiveButton(R.string.label_dialog_add) { dialogInterface: DialogInterface?, i: Int ->
                configImageView(
                    etFotoUrl.text.toString().trim { it <= ' ' })
            }
            .setNegativeButton(R.string.label_dialog_cancel, null)
        builder.setView(etFotoUrl)
        builder.show()
    }

    private fun configImageView(fotoUrl: String?) {
        if (fotoUrl != null) {
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
            Glide.with(this)
                .load(fotoUrl)
                .apply(options)
                .into(imgFoto!!)
        } else {
            imgFoto!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_photo_size_select_actual
                )
            )
        }
        mArtista!!.fotoUrl = fotoUrl
    }

    companion object {
        private const val RC_PHOTO_PICKER = 21
    }
}