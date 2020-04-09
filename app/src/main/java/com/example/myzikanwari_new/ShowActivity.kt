package com.example.myzikanwari_new

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.activity_main.view.*
import org.w3c.dom.Text

class ShowActivity : AppCompatActivity() {
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        mRealm = Realm.getInstance(realmConfig)
        val text         = findViewById<EditText>(R.id.text)
        val room_name    = findViewById<EditText>(R.id.text1)
        val teacher_name = findViewById<EditText>(R.id.text2)
        val memo_content = findViewById<EditText>(R.id.memo_content)
        val save_button   = findViewById<Button>(R.id.save)
        val delete_button = findViewById<Button>(R.id.delete)


        setResult(Activity.RESULT_CANCELED)

        val viewid = intent.getIntExtra("viewid", 0)
        setResult(Activity.RESULT_OK, intent)


        var ids: Array<Int> = arrayOf(
            R.id.text0,
            R.id.text1,
            R.id.text2,
            R.id.text3,
            R.id.text4,
            R.id.text5,
            R.id.text6,
            R.id.text7,
            R.id.text8,
            R.id.text9
            ,
            R.id.text10,
            R.id.text11,
            R.id.text12,
            R.id.text13,
            R.id.text14,
            R.id.text15,
            R.id.text16,
            R.id.text17,
            R.id.text18
            ,
            R.id.text19,
            R.id.text20,
            R.id.text21,
            R.id.text22,
            R.id.text23,
            R.id.text24,
            R.id.text25,
            R.id.text26,
            R.id.text27
            ,
            R.id.text28,
            R.id.text29,
            R.id.text30,
            R.id.text31,
            R.id.text32,
            R.id.text33,
            R.id.text34,
            R.id.text35,
            R.id.text36
            ,
            R.id.text37,
            R.id.text38,
            R.id.text39,
            R.id.text40,
            R.id.text41,
            R.id.text42,
            R.id.text43,
            R.id.text44,
            R.id.text45
            ,
            R.id.text46,
            R.id.text47,
            R.id.text48
        )
        for (i in 0..48) {
            when (viewid) {
                ids[i] -> {
                    var data = mRealm.where(SubjectModel::class.java)
                        .equalTo("id", i.toInt())
                        .findFirst()
                    var subject_name_isEmpty=data?.getName().toString() == "null" || data?.getName().toString() == ""
                    var room_name_isnull=data?.getRoom_name().toString() == "null"
                    var teacher_name_isnull=data?.getTeacher_name().toString() == "null"
                    var memo_isnull=data?.getMemo().toString() == "null"

                    if (subject_name_isEmpty) {
                        delete_button.setVisibility(View.GONE);
                        text.setText("", TextView.BufferType.NORMAL)
                    } else {
                        text.setText(data?.getName().toString(), TextView.BufferType.NORMAL)
                    }

                    if (room_name_isnull)
                        room_name.setText("", TextView.BufferType.NORMAL)
                    else
                        room_name.setText(data?.getRoom_name().toString(), TextView.BufferType.NORMAL)


                    if (teacher_name_isnull)
                        teacher_name.setText("", TextView.BufferType.NORMAL)
                    else
                        teacher_name.setText(data?.getTeacher_name().toString(), TextView.BufferType.NORMAL)


                    if (memo_isnull)
                        memo_content.setText("", TextView.BufferType.NORMAL)
                    else
                        memo_content.setText(data?.getMemo().toString(), TextView.BufferType.NORMAL)
                }
            }
        }

        save_button.setOnClickListener {


            for (i in 0..48) {
                when (viewid) {
                    ids[i] -> {
                        var subject_name_isEmpty=text.text.toString() == "null" || text.text.toString() == ""


                        if (subject_name_isEmpty) {
                            AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                                .setTitle("無効な授業")
                                .setMessage("科目名を入力してください")
                                .setPositiveButton("OK", { dialog, which ->

                                })
                                .show()
                        } else {

                            update(
                                i.toInt(), text.text.toString(), room_name.text.toString(),
                                teacher_name.text.toString(), memo_content.text.toString()
                            )

                            create(
                                i.toInt(), text.text.toString(), room_name.text.toString(),
                                teacher_name.text.toString(), memo_content.text.toString()
                            )

                            finish()
                        }
                    }
                }
            }

        }

        delete_button.setOnClickListener {
            for (i in 0..48) {
                when (viewid) {
                    ids[i] -> {
                        delete(i.toInt())
                    }
                }
            }
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    fun update(id: Int, name: String, room_name: String, teacher_name: String, memo: String) {
        mRealm.executeTransaction {
            var data = mRealm.where(SubjectModel::class.java)
                .equalTo("id", id)
                .findFirst()
            data?.setName(name)
            data?.setRoom_name(room_name)
            data?.setTeacher_name(teacher_name)
            data?.setMemo(memo)
        }
    }

    fun create(id: Int, name: String, room_name: String, teacher_name: String, memo: String) {
        mRealm.executeTransaction {
            var data = mRealm.createObject(SubjectModel::class.java)
            data.setId(id.toLong())
            data.setName(name)
            data.setRoom_name(room_name)
            data.setTeacher_name(teacher_name)
            data.setMemo(memo)
            mRealm.copyToRealm(data)
        }
    }

    fun delete(id: Int) {
        mRealm.executeTransaction {
            var data = mRealm.where(SubjectModel::class.java)
                .equalTo("id", id)
                .findAll()
                .deleteAllFromRealm()

        }
    }


}


