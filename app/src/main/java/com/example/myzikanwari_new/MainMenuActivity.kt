package com.example.myzikanwari_new

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import io.realm.Realm
import io.realm.RealmConfiguration

class MainMenuActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var backgroundimage: ImageView
    private lateinit var all_delete_area: TextView
    private lateinit var sat_switch: Switch
    private lateinit var sun_switch: Switch
    private lateinit var current_max_num: String
    private lateinit var mRealm: Realm
    private var max_num: String = "5"
    private var sat_off_flag: Boolean = true
    private var sun_off_flag: Boolean = true
    private var current_background_image: Int = 0
    private val REQUESTCODE_ONE = 1
    private val REQUESTCODE_TWE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()

        mRealm = Realm.getInstance(realmConfig)
        setResult(Activity.RESULT_CANCELED)

        current_max_num = intent.getStringExtra("current_max_num")
        sat_off_flag = intent.getBooleanExtra("stu_flag", true)
        sun_off_flag = intent.getBooleanExtra("sun_flag", true)
        current_background_image =
            intent.getIntExtra("current_background_image", R.drawable.background5)

        spinner = findViewById<Spinner>(R.id.spinner)
        sat_switch = findViewById<Switch>(R.id.StaSwitch)
        sun_switch = findViewById<Switch>(R.id.SunSwitch)
        backgroundimage = findViewById<ImageView>(R.id.BackgroundImage)
        all_delete_area = findViewById<TextView>(R.id.all_delete)


        sat_switch.isChecked = if (sat_off_flag) false else true
        sun_switch.isChecked = if (sun_off_flag) false else true

        backgroundimage.setImageResource(current_background_image)
        spinner.setSelection(current_max_num.toInt() - 4)
        backgroundimage.setOnClickListener {
            val intent = Intent(applicationContext, SelectBackgroundActivity::class.java)
            startActivityForResult(intent, REQUESTCODE_ONE);
        }


        sat_switch.setOnCheckedChangeListener { compoundButton, isChecked ->
            sat_off_flag = if (isChecked) false else true
        }
        
        sun_switch.setOnCheckedChangeListener { compoundButton, isChecked ->
            sun_off_flag = if (isChecked) false else true
        }

        all_delete_area.setOnClickListener {
            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                .setTitle("削除してもよろしいですか?")
                .setMessage("元に戻すことはできません。\n本当によろしいですか?")
                .setPositiveButton("削除", { dialog, which ->

                    var data = mRealm.where(SubjectModel::class.java).findAll()
                    mRealm.executeTransaction {
                        data.deleteAllFromRealm()
                    }
                    PutIntentItem()
                    setResult(Activity.RESULT_OK, intent)

                })
                .setNegativeButton("キャンセル", { dialog, which ->

                })
                .show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun PutIntentItem() {
        max_num = spinner.selectedItem as String
        intent.putExtra("stu_flag", sat_off_flag)
        intent.putExtra("sun_flag", sun_off_flag)
        intent.putExtra("max_num", max_num)
        intent.putExtra("current_background_image", current_background_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUESTCODE_ONE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    current_background_image =
                        data?.getIntExtra("current_background_image", R.drawable.background5)
                    backgroundimage.setImageResource(current_background_image)
                } else if (resultCode == Activity.RESULT_CANCELED) {

                }
            }
            REQUESTCODE_TWE -> {
                if (resultCode == Activity.RESULT_OK) {

                } else if (resultCode == Activity.RESULT_CANCELED) {

                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            PutIntentItem()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
