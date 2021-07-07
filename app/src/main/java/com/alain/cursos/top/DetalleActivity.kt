package com.alain.cursos.top

import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.raizlabs.android.dbflow.sql.language.SQLite
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

class DetalleActivity : AppCompatActivity(), OnDateSetListener {
    @JvmField
    @BindView(R.id.imgFoto)
    var imgFoto: AppCompatImageView? = null

    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.toolbar_layout)
    var toolbarLayout: CollapsingToolbarLayout? = null

    @JvmField
    @BindView(R.id.app_bar)
    var appBar: AppBarLayout? = null

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
    @BindView(R.id.etEdad)
    var etEdad: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etEstatura)
    var etEstatura: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etOrden)
    var etOrden: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etLugarNacimiento)
    var etLugarNacimiento: TextInputEditText? = null

    @JvmField
    @BindView(R.id.etNotas)
    var etNotas: TextInputEditText? = null

    @JvmField
    @BindView(R.id.containerMain)
    var containerMain: NestedScrollView? = null

    @JvmField
    @BindView(R.id.fab)
    var fab: FloatingActionButton? = null

    private var mArtista: Artista? = null
    private var mCalendar: Calendar? = null
    private var mMenuItem: MenuItem? = null
    private var mIsEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        ButterKnife.bind(this)

        configArtista(intent)
        configActionBar()
        configImageView(mArtista!!.fotoUrl)
        configCalendar()
    }

    private fun configArtista(intent: Intent) {
        getArtist(intent.getLongExtra(Artista.ID, 0))
        etNombre!!.setText(mArtista!!.nombre)
        etApellidos!!.setText(mArtista!!.apellidos)
        etFechaNacimiento!!.setText(SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                .format(mArtista!!.fechaNacimiento))
        etEdad!!.setText(getEdad(mArtista!!.fechaNacimiento))
        etEstatura!!.setText(mArtista!!.estatura.toString())
        etOrden!!.setText(mArtista!!.orden.toString())
        etLugarNacimiento!!.setText(mArtista!!.lugarNacimiento)
        etNotas!!.setText(mArtista!!.notas)
    }

    private fun getArtist(id: Long) {
        mArtista = SQLite
                .select()
                .from(Artista::class.java)
                .where(Artista_Table.id.`is`(id))
                .querySingle()
    }

    private fun getEdad(fechaNacimiento: Long): String {
        val time = Calendar.getInstance().timeInMillis / 1000 - fechaNacimiento / 1000
        val years = Math.round(time.toFloat()) / 31536000
        return years.toString()
    }

    private fun configActionBar() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        configTitle()
    }

    private fun configTitle() {
        toolbarLayout!!.title = mArtista!!.nombreCompleto
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
            imgFoto!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual))
        }
        mArtista!!.fotoUrl = fotoUrl
    }

    private fun configCalendar() {
        mCalendar = Calendar.getInstance(Locale.ROOT)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        mMenuItem = menu.findItem(R.id.action_save)
        mMenuItem?.setVisible(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            saveOrEdit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_PHOTO_PICKER) {
                savePhotoUrlArtist(data!!.dataString)
            }
        }
    }

    private fun savePhotoUrlArtist(fotoUrl: String?) {
        try {
            mArtista!!.fotoUrl = fotoUrl
            mArtista!!.update()
            configImageView(fotoUrl)
            showMessage(R.string.detalle_message_update_success)
        } catch (e: Exception) {
            e.printStackTrace()
            showMessage(R.string.detalle_message_update_fail)
        }
    }

    @OnClick(R.id.fab)
    fun saveOrEdit() {
        if (mIsEdit) {
            if (validateFields() && etNombre!!.text != null && etApellidos!!.text != null &&
                    etEstatura!!.text != null && etLugarNacimiento!!.text != null && etNotas!!.text != null) {
                mArtista!!.nombre = etNombre!!.text.toString().trim { it <= ' ' }
                mArtista!!.apellidos = etApellidos!!.text.toString().trim { it <= ' ' }
                mArtista!!.estatura = etEstatura!!.text.toString().trim { it <= ' ' }.toShort()
                mArtista!!.lugarNacimiento = etLugarNacimiento!!.text.toString().trim { it <= ' ' }
                mArtista!!.notas = etNotas!!.text.toString().trim { it <= ' ' }
                try {
                    mArtista!!.update()
                    configTitle()
                    showMessage(R.string.detalle_message_update_success)
                    Log.i("DBFlow", "Inserción correcta de datos.")
                } catch (e: Exception) {
                    e.printStackTrace()
                    showMessage(R.string.detalle_message_update_fail)
                    Log.i("DBFlow", "Error al insertar datos.")
                }
            }

            fab!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_edit))
            enableUIElements(false)
            mIsEdit = false
        } else {
            mIsEdit = true
            fab!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_check))
            enableUIElements(true)
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        if (etEstatura!!.text != null && (etEstatura!!.text.toString().trim { it <= ' ' }.isEmpty() ||
                        Integer.valueOf(etEstatura!!.text.toString().trim { it <= ' ' }) < resources.getInteger(R.integer.estatura_min))) {
            etEstatura!!.error = getString(R.string.addArtist_error_estaturaMin)
            etEstatura!!.requestFocus()
            isValid = false
        }

        if (etApellidos!!.text != null && etApellidos!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            etApellidos!!.error = getString(R.string.addArtist_error_required)
            etApellidos!!.requestFocus()
            isValid = false
        }

        if (etNombre!!.text != null && etNombre!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            etNombre!!.error = getString(R.string.addArtist_error_required)
            etNombre!!.requestFocus()
            isValid = false
        }

        return isValid
    }

    private fun enableUIElements(enable: Boolean) {
        etNombre!!.isEnabled = enable
        etApellidos!!.isEnabled = enable
        etFechaNacimiento!!.isEnabled = enable
        etEstatura!!.isEnabled = enable
        etLugarNacimiento!!.isEnabled = enable
        etNotas!!.isEnabled = enable
        mMenuItem!!.isVisible = enable
        appBar!!.setExpanded(!enable)
        containerMain!!.isNestedScrollingEnabled = !enable
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        mCalendar!!.timeInMillis = System.currentTimeMillis()
        mCalendar!![Calendar.YEAR] = year
        mCalendar!![Calendar.MONTH] = month
        mCalendar!![Calendar.DAY_OF_MONTH] = day
        etFechaNacimiento!!.setText(SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(
                mCalendar!!.timeInMillis))
        mArtista!!.fechaNacimiento = mCalendar!!.timeInMillis
        etEdad!!.setText(getEdad(mCalendar!!.timeInMillis))
    }

    @OnClick(R.id.etFechaNacimiento)
    fun onSetFecha() {
        val selectorFecha = DialogSelectorFecha()
        selectorFecha.setListener(this@DetalleActivity)
        val args = Bundle()
        args.putLong(DialogSelectorFecha.FECHA, mArtista!!.fechaNacimiento)
        selectorFecha.arguments = args
        selectorFecha.show(supportFragmentManager, DialogSelectorFecha.SELECTED_DATE)
    }

    private fun showMessage(resource: Int) {
        Snackbar.make(containerMain!!, resource, Snackbar.LENGTH_SHORT).show()
    }

    @OnClick(R.id.imgDeleteFoto, R.id.imgFromGallery, R.id.imgFromUrl)
    fun photoHandler(view: View) {
        when (view.id) {
            R.id.imgDeleteFoto -> {
                val builder = AlertDialog.Builder(this)
                        .setTitle(R.string.detalle_dialogDelete_title)
                        .setMessage(String.format(Locale.ROOT,
                                getString(R.string.detalle_dialogDelete_message),
                                mArtista!!.nombreCompleto))
                        .setPositiveButton(R.string.label_dialog_delete) { dialogInterface: DialogInterface?, i: Int ->
                            savePhotoUrlArtist(null)
                        }
                        .setNegativeButton(R.string.label_dialog_cancel, null)
                builder.show()
            }
            R.id.imgFromGallery -> {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/jpeg"
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.detalle_chooser_title)), RC_PHOTO_PICKER)
            }
            R.id.imgFromUrl -> showAddPhotoDialog()
        }
    }

    private fun showAddPhotoDialog() {
        val etFotoUrl = EditText(this)
        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.addArtist_dialogUrl_title)
                .setPositiveButton(R.string.label_dialog_add) { dialogInterface: DialogInterface?, i: Int ->
                    savePhotoUrlArtist(etFotoUrl.text.toString().trim { it <= ' ' })
                }
                .setNegativeButton(R.string.label_dialog_cancel, null)
        builder.setView(etFotoUrl)
        builder.show()
    }

    companion object {
        private const val RC_PHOTO_PICKER = 21
    }
}