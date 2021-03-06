package com.alain.cursos.top

import android.app.ActivityOptions
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.raizlabs.android.dbflow.sql.language.SQLite
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

class MainActivity : AppCompatActivity(), OnItemClickListener {
    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.recyclerview)
    var recyclerview: RecyclerView? = null

    @JvmField
    @BindView(R.id.containerMain)
    var containerMain: CoordinatorLayout? = null

    private var adapter: ArtistaAdapter? = null

    private val SP_DARK_THEME = "spDarkTheme"
    private lateinit var mSharedPreferends: SharedPreferences
    private var mIsModeNigth: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configTheme()
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        configToolbar()
        configAdapter()
        configRecyclerView()

        if (artistasFromDB.size == 0) {
            generateArtist()
        }
    }

    private fun configTheme() {
        setTheme(R.style.MyTheme_DayNigth)
        mSharedPreferends = getPreferences(MODE_PRIVATE)
        mIsModeNigth = mSharedPreferends.getBoolean(SP_DARK_THEME, false)
        if (mIsModeNigth as Boolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun generateArtist() {
        val nombres = arrayOf(
            "Rachel",
            "Mary Elizabeth",
            "Jessica",
            "Gal",
            "Anne",
            "Emma",
            "Sophie",
            "Rooney",
            "Michelle",
            "Margot"
        )
        val apellidos = arrayOf(
            "McAdams",
            "Winstead",
            "Chastain",
            "Gadot",
            "Hathaway",
            "Stone",
            "Turner",
            "Mara",
            "Williams",
            "Robbie"
        )
        val nacimientos = longArrayOf(
            280108800000L,
            470469600000L,
            228031200000L,
            483667200000L,
            405907200000L,
            594777600000L,
            824860800000L,
            482544000000L,
            337305600000L,
            646876800000L
        )
        val lugares =
            arrayOf("Canada", "USA", "USA", "Israel", "USA", "USA", "UK", "USA", "USA", "AUS")
        val estaturas = shortArrayOf(163, 173, 163, 178, 173, 168, 175, 160, 163, 168)
        val notas = arrayOf(
            "Rachel Anne McAdams was born on November 17, 1978 in London, Ontario, Canada, to Sandra Kay (Gale), a nurse, and Lance Frederick McAdams, a truck driver and furniture mover. She is of English, Welsh, Irish, and Scottish descent. Rachel became involved with acting as a teenager and by the age of 13 was performing in Shakespearean productions in summer theater camp; she went on to graduate with honors with a BFA degree in Theater from York University. After her debut in an episode of Disney's The Famous Jett Jackson (1998), she co-starred in the Canadian TV series Slings and Arrows (2003), a comedy-drama about the trials and travails of a Shakespearean theater group, and won a Gemini award for her performance in 2003.\nHer breakout role as Regina George in the hit comedy Chicas pesadas (2004) instantly catapulted her onto the short list of Hollywood's hottest young actresses. She followed that film with a star turn opposite Ryan Gosling in the adaptation of the Nicholas Sparks bestseller Diario de una pasión (2004), which was a surprise box office success and became the predominant romantic drama for a new, young generation of moviegoers. After filming, McAdams and Gosling became romantically involved and dated through mid-2007. McAdams next showcased her versatility onscreen with the manic comedy Los cazanovias (2005), the thriller Vuelo nocturno (2005), and the holiday drama The Family Stone (2005).\nMcAdams then explored the independent film world with Infieles (2007), which premiered at the Toronto Film Festival and also starred Pierce Brosnan, Chris Cooper and Patricia Clarkson. Starring roles in the military drama The Lucky Ones (2008), the newspaper thriller Los secretos del poder (2009), and the romance Te amaré por siempre (2009) followed before she starred opposite Robert Downey Jr. and Jude Law in Guy Ritchie's international blockbuster Sherlock Holmes (2009). McAdams played the plucky producer of a failing morning TV show in Morning Glory (2010), the materialistic fiancée of Owen Wilson in Woody Allen's Medianoche en París (2011), and returned to romantic drama territory with the hit film Votos de amor (2012) opposite Channing Tatum. The actress also stars with Ben Affleck in Terrence Malick's Deberás amar (2012) and alongside Noomi Rapace in Brian De Palma's thriller Pasión, un asesinato perfecto (2012).\nIn 2005, McAdams received ShoWest's \"Supporting Actress of the Year\" Award as well as the \"Breakthrough Actress of the Year\" at the Hollywood Film Awards. In 2009, she was awarded with ShoWest's \"Female Star of the Year.\" As of 2011, she has been romantically linked with her Medianoche en París (2011) co-star Michael Sheen.",
            "Mary Elizabeth Winstead is a gifted actress, known for her versatile work in a variety of film and television projects. Possibly most known for her role as Ramona Flowers in Scott Pilgrim contra el mundo (2010), she has also starred in critically acclaimed independent films such as Tocando fondo (2012), for which she received an Independent Spirit Award nomination, as well as genre fare like Destino final 3 (2006) and Quentin Tarantino's Death Proof (2007).",
            "Jessica Michelle Chastain was born in Sacramento, California, and was raised in a middle-class household in a northern California suburb. Her mother, Jerri Chastain, is a vegan chef whose family is originally from Kansas, and her stepfather is a fireman. She discovered dance at the age of nine and was in a dance troupe by age thirteen. She began performing in Shakespearean productions all over the Bay area.",
            "Gal Gadot is an Israeli actress, singer, martial artist, and model. She was born in Rosh Ha'ayin, Israel, to an Ashkenazi Jewish family. Her parents are Irit, a teacher, and Michael, an engineer, who is a sixth-generation Israeli. She served in the IDF for two years, and won the Miss Israel title in 2004.\nGal began modeling in the late 2000s, and made her film debut in the fourth film of the Fast and Furious franchise, Rápidos y furiosos (2009), as Gisele. Her role was expanded in the sequels Rápidos y Furiosos: 5in control (2011) and Rápidos y furiosos 6 (2013), in which her character was romantically linked to Han Seoul-Oh (Sung Kang). In the films, Gal performed her own stunts. She also appeared in the 2010 films Una noche fuera de serie (2010) and Knight and Day (2010).\nIn early December 2013, Gal was cast as Wonder Woman in the superhero team-up film Batman vs. Superman: El origen de la justicia (2016), and filming began in 2014 for a March 2016 release. Gadot received swordsmanship, Kung Fu kickboxing, Capoeira and Brazilian Jiu-Jitsu training in preparation for the role. As a result, her performance as the superhero, which is the first time for the character on film, was hailed as one of the best parts of the otherwise poorly-received film. The film is part of the DC Extended Universe, and Gadot plays the role again in the solo film Mujer Maravilla (2017), which was received very positively, and superhero team-up Justice League (2017).\nGal is a motorcycle enthusiast, and owns a black 2006 Ducati Monster-S2R. She has been married to Israeli businessman Yaron Versano since September 28, 2008. They have two daughters.",
            "Anne Jacqueline Hathaway was born in Brooklyn, New York, to Kate McCauley Hathaway, an actress, and Gerald T. Hathaway, a lawyer, both originally from Philadelphia. She is of mostly Irish descent, along with English, German, and French. Her first major role came in the short-lived television series Asuntos de familia (1999). She gained widespread recognition for her roles in Princesa por sorpresa (2001) and its 2004 sequel as a young girl who discovers she is a member of royalty, opposite Julie Andrews and Heather Matarazzo.",
            "Emily Jean \"Emma\" Stone was born in Scottsdale, Arizona, to Krista (Yeager), a homemaker, and Jeffrey Charles Stone, a contracting company founder and CEO. She is of Swedish, German, and British Isles descent. Stone began acting as a child as a member of the Valley Youth Theatre in Phoenix, Arizona, where she made her stage debut in a production of Kenneth Grahame's \"The Wind in the Willows\". She appeared in many more productions through her early teens until, at the age of fifteen, she decided that she wanted to make acting her career.",
            "Sophie Belinda Jonas (née Turner; born February 21, 1996) is an English actress. Turner made her professional acting debut as Sansa Stark on the HBO fantasy television series Juego de tronos (2011) (2011-2019), which brought her international recognition and critical praise. For her performance, she has received four nominations for Screen Actors Guild Award for Outstanding Performance by an Ensemble in a Drama Series, as well as a Young Artist Award nomination for Best Supporting Young Actress in a TV Series.",
            "Actress and philanthropist Rooney Mara was born on April 17, 1985 in Bedford, New York. She made her screen debut in the slasher film Leyenda urbana 3 (2005), went on to have a supporting role in the independent coming-of-age drama Tanner Hall (2009), and has since starred in the horror remake Pesadilla en Elm Street. El origen (2010), the biographical drama La red social (2010), the thriller remake Millennium: Los hombres que no amaban a las mujeres (2011), and the romantic drama Carol (2015).",
            "A small-town girl born and raised in rural Kalispell, Montana, Michelle Ingrid Williams is the daughter of Carla Ingrid (Swenson), a homemaker, and Larry Richard Williams, a commodity trader and author. Her ancestry is Norwegian, as well as German, British Isles, and other Scandinavian.",
            "Margot Elise Robbie was born on July 2, 1990 in Dalby, Queensland, Australia to Scottish parents. Her mother, Sarie Kessler, is a physiotherapist, and her father, is Doug Robbie. She comes from a family of four children, having two brothers and one sister. She graduated from Somerset College in Mudgeeraba, Queensland, Australia, a suburb in the Gold Coast hinterland of South East Queensland, where she and her siblings were raised by their mother and spent much of her time at the farm belonging to her grandparents."
        )
        val fotos = arrayOf(
            "https://live.staticflickr.com/8167/28530213082_926d57d5c9_b.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/7/7b/Mary_Elizabeth_Winstead.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/SDCC_2015_-_Jessica_Chastain_%2819544181630%29.jpg/1024px-SDCC_2015_-_Jessica_Chastain_%2819544181630%29.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Gal_Gadot_%2835402074433%29.jpg/1024px-Gal_Gadot_%2835402074433%29.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/b/b3/Anne_Hathaway_for_AHC.png",
            "https://live.staticflickr.com/7235/6855549290_5f6aefd89e_b.jpg",
            "https://live.staticflickr.com/257/19764740481_cd0c41a1d8_b.jpg",
            "https://live.staticflickr.com/7015/6633253419_4ce2ddd4cb_b.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/0/06/Michelle_Williams_by_Gage_Skidmore.jpg",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Margot_Robbie_%2828601016915%29_%28cropped%29.jpg/576px-Margot_Robbie_%2828601016915%29_%28cropped%29.jpg"
        )
        for (i in nombres.indices) {
            val artista = Artista(
                nombres[i], apellidos[i], nacimientos[i], lugares[i],
                estaturas[i], notas[i], i + 1, fotos[i]
            )
            try {
                artista.save()
                Log.i("DBFlow", "Inserción correcta de datos.")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("DBFlow", "Error al insertar datos.")
            }
        }
    }

    private fun configToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun configAdapter() {
        adapter = ArtistaAdapter(ArrayList(), this)
    }

    private fun configRecyclerView() {
        recyclerview!!.layoutManager = LinearLayoutManager(this)
        recyclerview!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter!!.setList(artistasFromDB)
    }

    private val artistasFromDB: MutableList<Artista>
        get() = SQLite
            .select()
            .from(Artista::class.java)
            .orderBy(Artista_Table.orden, true)
            .queryList()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_ligthTheme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mIsModeNigth = false

            }
            R.id.action_darkTheme -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mIsModeNigth = true
            }
        }
        val editor = mSharedPreferends.edit()
        mIsModeNigth?.let { editor.putBoolean(SP_DARK_THEME, it) }
        editor.apply()
        return super.onOptionsItemSelected(item)
    }

    /******
     * Métodos implementados por la interface OnItemClickListener
     */
    override fun onItemClick(
        artista: Artista?,
        imgPhoto: View,
        tvName: View
    ) {
        val intent = Intent(this@MainActivity, DetalleActivity::class.java)
        intent.putExtra(Artista.ID, artista!!.id)
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        //startActivity(intent)
        //val imgPair: Pair<View, String> = Pair.create(imgPhoto, imgPhoto.transitionName)
        //val notePair:Pair<View, String> = Pair.create(tvNote,tvNote.transitionName)

        val imgPair: Pair<View, String> =
            Pair.create(imgPhoto, getString(R.string.transition_name_photo))
        //val notePair: Pair<View, String> = Pair.create(tvNote, getString(R.string.tn_note))
        // val orderPair: Pair<View, String> = Pair.create(tvNote, getString(R.string.tn_Order))
        val namePair: Pair<View, String> = Pair.create(tvName, getString(R.string.tn_Name))


        /*startActivity(
            intent,
            ActivityOptions.makeSceneTransitionAnimation(
                this,
                imgPhoto,
                getString(R.string.transition_name_photo)
            ).toBundle()
        )*/

        val options: ActivityOptions =
            ActivityOptions.makeSceneTransitionAnimation(
                this,
                imgPair,
                namePair
            )
        startActivity(intent, options.toBundle())
    }

    override fun onLongItemClick(artista: Artista?) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(60)
        }

        val builder = MaterialAlertDialogBuilder(this)//AlertDialog.Builder(this)
            .setTitle(R.string.main_dialogDelete_title)
            .setMessage(
                String.format(
                    Locale.ROOT, getString(R.string.main_dialogDelete_message),
                    artista!!.nombreCompleto
                )
            )
            .setPositiveButton(R.string.label_dialog_delete) { dialogInterface: DialogInterface?, i: Int ->
                try {
                    artista.delete()
                    adapter!!.remove(artista)
                    showMessage(R.string.main_message_delete_success)
                } catch (e: Exception) {
                    e.printStackTrace()
                    showMessage(R.string.main_message_delete_fail)
                }
            }
            .setNegativeButton(R.string.label_dialog_cancel, null)
        builder.show()
    }

    @OnClick(R.id.fab)
    fun addArtist() {
        val intent = Intent(this@MainActivity, AddArtistActivity::class.java)
        intent.putExtra(Artista.ORDEN, adapter!!.itemCount + 1)
        //startActivity(intent);
        //startActivityForResult(intent, 1)
        startActivityForResult(
            intent,
            1,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }

    private fun showMessage(resource: Int) {
        Snackbar.make(containerMain!!, resource, Snackbar.LENGTH_SHORT).show()
    }
}