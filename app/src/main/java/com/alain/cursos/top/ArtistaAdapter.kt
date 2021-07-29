package com.alain.cursos.top

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView

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
class ArtistaAdapter internal constructor(
    private var artistas: MutableList<Artista>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ArtistaAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_artist, parent,
            false
        )
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artista = artistas[position]

        holder.setListener(artista, holder.imgFoto!!.rootView)
        holder.tvNombre!!.text = artista.nombreCompleto
        holder.tvNota!!.text = artista.notas
        holder.tvOrden!!.text = (position + 1).toString()

        if (artista.fotoUrl != null) {
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.ic_sentiment_satisfied)
            Glide.with(context!!)
                .load(artista.fotoUrl)
                .apply(options)
                .into(holder.imgFoto!!)
        } else {
            holder.imgFoto!!.setImageDrawable(
                ContextCompat.getDrawable(
                    context!!,
                    R.drawable.ic_account_box
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return artistas.size
    }

    fun setList(list: MutableList<Artista>) {
        artistas = list
        notifyDataSetChanged()
    }

    fun remove(artista: Artista) {
        if (artistas.contains(artista)) {
            artistas.remove(artista)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        @JvmField
        @BindView(R.id.imgFoto)
        var imgFoto: CircleImageView? = null

        @JvmField
        @BindView(R.id.tvNombre)
        var tvNombre: AppCompatTextView? = null

        @JvmField
        @BindView(R.id.tvNote)
        var tvNota: AppCompatTextView? = null

        @JvmField
        @BindView(R.id.tvOrden)
        var tvOrden: AppCompatTextView? = null

        @JvmField
        @BindView(R.id.containerMain)
        var containerMain: ConstraintLayout? = null

        fun setListener(artista: Artista, imgPhoto: View) {
            containerMain!!.setOnClickListener { view: View? ->
                listener.onItemClick(artista, imgPhoto)//listener.onItemClick(artista) }
                containerMain!!.setOnLongClickListener { view: View? ->
                    listener.onLongItemClick(artista)
                    true
                }
            }


        }

        init {
            ButterKnife.bind(this, itemView!!)
        }
    }
}