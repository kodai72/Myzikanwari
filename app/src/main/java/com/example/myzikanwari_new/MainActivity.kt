package com.example.myzikanwari_new

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration


class MainActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm
    private lateinit var textview: Array<TextView>
    private lateinit var rows: Array<TableRow>
    private val REQUESTCODE_ONE = 1
    private val REQUESTCODE_TWE = 2
    private var tableLayout: TableLayout? = null
    private var max_num: String = "5"
    private var current_background_image: Int = 0
    private var sat_flag: Boolean = true
    private var sun_flag: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)
        var textview_ids: Array<Int> = arrayOf(
            R.id.text0, R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5, R.id.text6, R.id.text7, R.id.text8, R.id.text9
            , R.id.text10, R.id.text11, R.id.text12, R.id.text13, R.id.text14, R.id.text15, R.id.text16, R.id.text17, R.id.text18
            , R.id.text19, R.id.text20, R.id.text21, R.id.text22, R.id.text23, R.id.text24, R.id.text25, R.id.text26, R.id.text27
            , R.id.text28, R.id.text29, R.id.text30, R.id.text31, R.id.text32, R.id.text33, R.id.text34, R.id.text35, R.id.text36
            , R.id.text37, R.id.text38, R.id.text39, R.id.text40, R.id.text41, R.id.text42, R.id.text43, R.id.text44, R.id.text45
            , R.id.text46, R.id.text47, R.id.text48)

        var row_ids: Array<Int> = arrayOf(
            R.id.row1, R.id.row1, R.id.row2, R.id.row3, R.id.row4, R.id.row5, R.id.row6, R.id.row7, R.id.row8
        )

        textview = Array<TextView>(49, { i -> findViewById(textview_ids[i]) })
        rows = Array<TableRow>(9, { i -> findViewById(row_ids[i]) })
        tableLayout = findViewById<TableLayout>(R.id.tablelayout);


        rows[7].setVisibility(View.GONE)
        rows[8].setVisibility(View.GONE)
        current_background_image = R.drawable.background5


        tableLayout!!.setBackgroundResource(current_background_image)
        tableLayout!!.setColumnCollapsed(6, sat_flag)
        tableLayout!!.setColumnCollapsed(7, sun_flag)


        val listener = intentListener()
        for (e in textview) {
            e.setOnClickListener(listener)
        }

        for (i in 0..48) {
            var data = mRealm.where(SubjectModel::class.java)
                .equalTo("id", i.toInt())
                .findFirst()
            var subject_name_isEmpty = data?.getName().toString() == "null" || data?.getName().toString() == ""
            if (!subject_name_isEmpty) {
                textview[i].setBackgroundColor(Color.argb(200, 245, 124, 103))
                textview[i].text = data?.getName().toString()
            } else {
                textview[i].text = ""
            }
        }


    }


    private inner class intentListener : View.OnClickListener {
        override fun onClick(view: View?) {
            val intent = Intent(applicationContext, ShowActivity::class.java)
            intent.putExtra("viewid", view?.id)
            startActivityForResult(intent, REQUESTCODE_ONE);
        }
    }

    private fun CheckHasSubjectName() {
        for (i in 0..48) {
            var data = mRealm.where(SubjectModel::class.java)
                .equalTo("id", i.toInt())
                .findFirst()

            textview[i].text = data?.getName().toString()
            var subject_name_isEmpty = textview[i].text == "null" || textview[i].text == ""

            if (subject_name_isEmpty) {
                textview[i].text = ""
                textview[i].setBackgroundColor(Color.argb(0, 237, 62, 50))
            } else {
                textview[i].setBackgroundColor(Color.argb(200, 245, 124, 103))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUESTCODE_ONE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    CheckHasSubjectName()
                } else if (resultCode == Activity.RESULT_CANCELED) {

                }
            }
            REQUESTCODE_TWE -> {
                if (resultCode == Activity.RESULT_OK) {
                    max_num = data!!.getStringExtra("max_num")
                    current_background_image =
                        data?.getIntExtra("current_background_image", R.drawable.background5)
                    tableLayout?.setBackgroundResource(current_background_image)

                    sat_flag = data.getBooleanExtra("stu_flag", true)
                    tableLayout?.setColumnCollapsed(6, sat_flag)

                    sun_flag = data.getBooleanExtra("sun_flag", true)
                    tableLayout?.setColumnCollapsed(7, sun_flag)
                    when (max_num) {
                        "4" -> {
                            rows[6].setVisibility(View.GONE)
                            rows[7].setVisibility(View.GONE)
                            rows[8].setVisibility(View.GONE)
                        }
                        "5" -> {
                            rows[6].setVisibility(View.VISIBLE)
                            rows[7].setVisibility(View.GONE)
                            rows[8].setVisibility(View.GONE)
                        }
                        "6" -> {
                            rows[6].setVisibility(View.VISIBLE)
                            rows[7].setVisibility(View.VISIBLE)
                            rows[8].setVisibility(View.GONE)
                        }
                        "7" -> {
                            rows[6].setVisibility(View.VISIBLE)
                            rows[7].setVisibility(View.VISIBLE)
                            rows[8].setVisibility(View.VISIBLE)
                        }
                    }
                    CheckHasSubjectName()
                } else if (resultCode == Activity.RESULT_CANCELED) {


                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu -> {
                val intent = Intent(applicationContext, MainMenuActivity::class.java)
                intent.putExtra("current_max_num", max_num)
                intent.putExtra("current_background_image", current_background_image)
                intent.putExtra("stu_flag", sat_flag)
                intent.putExtra("sun_flag", sun_flag)
                startActivityForResult(intent, REQUESTCODE_TWE)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
