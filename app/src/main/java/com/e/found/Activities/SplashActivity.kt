package com.e.found.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.e.found.R
import com.e.found.Utils.FoundAppSharedPreference
import java.util.*


class SplashActivity : AppCompatActivity() {
    private var timer: Timer? = null
    var user_id = ""
    lateinit var foundAppSharedPreference: FoundAppSharedPreference

    var is_question = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable =
            this@SplashActivity.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------

        foundAppSharedPreference = FoundAppSharedPreference.getInstance(this)
        user_id = foundAppSharedPreference.user_id
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {

                timer!!.cancel()
                if (user_id.equals("")) {
                    val intent = Intent(this@SplashActivity, RegisterMainScreen::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, BaseActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    finish()
                }
            }
        }, 2000)
    }
}