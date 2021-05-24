package com.e.found.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.e.found.R
import com.e.found.Utils.FoundAppSharedPreference

import com.e.found.databinding.ActivitySignUpStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import java.util.HashMap


class SignUpStatusActivity : AppCompatActivity() {
    lateinit var activitySignUpStatusBinding: ActivitySignUpStatusBinding
    var dialog: ACProgressFlower? = null

    lateinit var foundAppSharedPreference: FoundAppSharedPreference
    var email = ""
    var password = ""
    var token = ""
    var user_id = ""
    var TAG = "found"
    var image = ""
    var status = ""
    var name = ""
    var gender = ""
    var dob = ""
    var from_screen = ""
    private var mDatabase: DatabaseReference? = null

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpStatusBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_up_status)
        //set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------

        try {
            intent = getIntent()
            email = intent?.getStringExtra("email").toString()
            password = intent?.getStringExtra("password").toString()
            name = intent?.getStringExtra("name").toString()
            dob = intent?.getStringExtra("dob").toString()
            gender = intent?.getStringExtra("gender").toString()

        } catch (e: Exception) {

        }
//firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("users")

        token = FirebaseInstanceId.getInstance().getToken()!!

        //preferences
        foundAppSharedPreference = FoundAppSharedPreference.getInstance(this)


        activitySignUpStatusBinding.editStatus.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                var count = 80 - s.toString().length
                Log.e("voopipo", count.toString())
                activitySignUpStatusBinding.tvAbout12.setText(count.toString())
            }
        })

        activitySignUpStatusBinding.rlBack.setOnClickListener {
            finish()
            overridePendingTransition(
                R.anim.fadein,
                R.anim.fadeout
            )
        }
        activitySignUpStatusBinding.tvNext.setOnClickListener {
            status = activitySignUpStatusBinding.editStatus.text.toString()
            if (activitySignUpStatusBinding.editStatus.text.toString().equals("")) {
                Toast.makeText(
                    this,
                    "Enter your status",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                dialog = ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .isTextExpandWidth(true)
                    .textSize(20)
                    .bgAlpha(50f)
                    .fadeColor(Color.DKGRAY).build()
                dialog?.show()
                performLoginOrAccountCreation(email, password)

            }
        }
    }


    private fun performLoginOrAccountCreation(email: String, password: String) {
        firebaseAuth!!.fetchSignInMethodsForEmail(email).addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "checking to see if user exists in firebase or not")
                val result = task.result
                if (result != null && result.signInMethods != null && result.signInMethods!!.size > 0
                ) {
                    Log.d(TAG, "User exists, trying to login using entered credentials")


                } else {
                    Log.d(TAG, "User doesn't exist, creating account")
                    //insert some default data
                    registerAccount(email, password)

                }
            } else {
                Log.w(TAG, "User check failed", task.exception)
                dialog?.dismiss()
            }
            //hide progress dialog

        }
    }


    private fun registerAccount(email: String, password: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val current_user = FirebaseAuth.getInstance().currentUser
                    user_id = current_user!!.uid
                    val token_id = FirebaseInstanceId.getInstance().token

                    val userMap: MutableMap<String, Any?> = HashMap()
                    userMap["device_token"] = token_id
                    userMap["name"] = name
                    userMap["email"] = email
                    userMap["user_id"] = user_id
                    userMap["status"] = status
                    userMap["online"] = "true"
                    mDatabase!!.child(user_id).setValue(userMap)
                        .addOnSuccessListener { // Write was successful!
                            // ...
                            dialog?.dismiss()
                            foundAppSharedPreference.user_id = user_id
                            val intent = Intent(this, BaseActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                        }
                        .addOnFailureListener { e -> // Write failed
                            // ...
                            dialog?.dismiss()

                        }
                } else {
                    Log.e("register account failed", task.exception.toString() + "")
                    dialog?.dismiss()
                }
            }
    }
}