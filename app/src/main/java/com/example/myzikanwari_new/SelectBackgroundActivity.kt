package com.example.myzikanwari_new

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_select_background.view.*

class SelectBackgroundActivity : AppCompatActivity() {
    private var now_image: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_background)

        setResult(Activity.RESULT_CANCELED)
        var ids: Array<Int> = arrayOf(
            R.id.BackgroundImage1, R.id.BackgroundImage2, R.id.BackgroundImage3
            , R.id.BackgroundImage4, R.id.BackgroundImage5, R.id.BackgroundImage6
            , R.id.BackgroundImage7
        )
        var background_images: Array<ImageView>
        background_images = Array<ImageView>(7, { i -> findViewById(ids[i]) })

        for (i in background_images) {
            i.setOnClickListener(BackgroundImageListener())
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private inner class BackgroundImageListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.BackgroundImage1 ->
                    now_image = R.drawable.background3

                R.id.BackgroundImage2 ->
                    now_image = R.drawable.background5

                R.id.BackgroundImage3 ->
                    now_image = R.drawable.background6

                R.id.BackgroundImage4 ->
                    now_image = R.drawable.background7

                R.id.BackgroundImage5 ->
                    now_image = R.drawable.background8

                R.id.BackgroundImage6 ->
                    now_image = R.drawable.background9

                R.id.BackgroundImage7 ->
                    now_image = R.drawable.background10
            }
            intent.putExtra("current_background_image", now_image)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
