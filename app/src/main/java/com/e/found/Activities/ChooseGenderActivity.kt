package com.e.found.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.e.found.R
import com.e.found.Utils.FoundAppSharedPreference

import com.e.found.databinding.ActivityChooseGenderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId


class ChooseGenderActivity : AppCompatActivity(), View.OnClickListener {
    var tag = ""
    lateinit var activityChooseGenderBinding: ActivityChooseGenderBinding
    private var firebaseAuth: FirebaseAuth? = null
    var user_id = ""
    var email = ""
    var token=""
    var password = ""
    var name = ""
    var dob = ""

var gender=""
    lateinit var foundAppSharedPreference: FoundAppSharedPreference
    private var mDatabase: DatabaseReference? = null
    //vars
    private var mDb: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChooseGenderBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_choose_gender
        )
        //set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------
        mDb = FirebaseFirestore.getInstance()
        try {
            intent = getIntent()
            email = intent?.getStringExtra("email").toString()
            password = intent?.getStringExtra("password").toString()
            name = intent?.getStringExtra("name").toString()
            dob = intent?.getStringExtra("dob").toString()
        } catch (e: Exception) {

        }
//firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("users")
        token= FirebaseInstanceId.getInstance().getToken()!!

        foundAppSharedPreference = FoundAppSharedPreference.getInstance(this)

        ClickListener()
    }

    fun ClickListener() {
        activityChooseGenderBinding.rlTrans.setOnClickListener(this)
        activityChooseGenderBinding.rlFemale.setOnClickListener(this)
        activityChooseGenderBinding.rlMale.setOnClickListener(this)
        activityChooseGenderBinding.tvNext.setOnClickListener(this)
        activityChooseGenderBinding.rlBack.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.rl_male -> {
                gender = "Male"
                activityChooseGenderBinding.rlFemale.visibility = View.VISIBLE
                activityChooseGenderBinding.rlTrans.visibility = View.VISIBLE
                activityChooseGenderBinding.rlMale.visibility = View.GONE
                activityChooseGenderBinding.rlFemaleDark.visibility = View.GONE
                activityChooseGenderBinding.rlTransDark.visibility = View.GONE
                activityChooseGenderBinding.rlMaleDark.visibility = View.VISIBLE
            }
            R.id.rl_female -> {
                gender = "Female"
                activityChooseGenderBinding.rlFemale.visibility = View.GONE
                activityChooseGenderBinding.rlTrans.visibility = View.VISIBLE
                activityChooseGenderBinding.rlMale.visibility = View.VISIBLE
                activityChooseGenderBinding.rlFemaleDark.visibility = View.VISIBLE
                activityChooseGenderBinding.rlTransDark.visibility = View.GONE
                activityChooseGenderBinding.rlMaleDark.visibility = View.GONE
            }
            R.id.rl_trans -> {
                gender = "Transgender"
                activityChooseGenderBinding.rlFemale.visibility = View.VISIBLE
                activityChooseGenderBinding.rlTrans.visibility = View.GONE
                activityChooseGenderBinding.rlMale.visibility = View.VISIBLE
                activityChooseGenderBinding.rlFemaleDark.visibility = View.GONE
                activityChooseGenderBinding.rlTransDark.visibility = View.VISIBLE
                activityChooseGenderBinding.rlMaleDark.visibility = View.GONE

            }
            R.id.rl_back -> {
                finish()
                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.fadeout
                )
            }
            R.id.tv_next -> {
                if (gender.equals("")) {
                    Toast.makeText(
                        this,
                        "Select gender",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent = Intent(this, SignUpStatusActivity::class.java)
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("password", password);
                    intent.putExtra("dob", dob);
                    intent.putExtra("gender", gender);
                    startActivity(intent)

                    overridePendingTransition(
                        R.anim.fadein,
                        R.anim.fadeout
                    )
                }
            }

        }
    }



}