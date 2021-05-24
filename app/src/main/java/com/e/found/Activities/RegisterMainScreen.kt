package com.e.found.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.e.found.R

import com.e.found.databinding.ActivityRegisterMainScreenBinding

import java.util.*


class RegisterMainScreen : AppCompatActivity(), View.OnClickListener{
    lateinit var activityRegisterMainScreenBinding: ActivityRegisterMainScreenBinding

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterMainScreenBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_register_main_screen
        )
        //set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------
        activityRegisterMainScreenBinding.tvLogin.setOnClickListener(this)
        activityRegisterMainScreenBinding.tvRegister.setOnClickListener(this)



    }


    override fun onClick(p0: View?) {
        when(p0!!.id)
        {

            R.id.tv_login -> {
                val intent = Intent(this@RegisterMainScreen, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)


            }
            R.id.tv_register -> {
                val intent = Intent(this@RegisterMainScreen, SignupActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }

        }
    }



}