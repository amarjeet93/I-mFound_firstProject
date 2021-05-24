package com.e.found.Activities


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.e.found.R
import com.e.found.Utils.FoundAppSharedPreference
import com.e.found.Utils.ViewUtils
import com.e.found.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var activityLoginBinding: ActivityLoginBinding
    var email = ""
    var password = ""
    var user_id = ""
    var token = ""
    var name = ""
    var dialog: ACProgressFlower? = null
    private var firebaseAuth: FirebaseAuth? = null
    var TAG = "found"

    lateinit var foundAppSharedPreference: FoundAppSharedPreference

    private var mDatabase: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //layout bind
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------
        //firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference.child("users")
        token = FirebaseInstanceId.getInstance().getToken()!!
        Log.e("tokennn", token)

        //preferences
        foundAppSharedPreference = FoundAppSharedPreference.getInstance(this)

        //click listener
        activityLoginBinding.tvLogin.setOnClickListener(this)
        activityLoginBinding.rlSignup.setOnClickListener(this)
        activityLoginBinding.rlBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_login -> {
                email = activityLoginBinding.editEmail.text.toString()
                password = activityLoginBinding.editPass.text.toString()
                if (email.equals("") && password.equals("")) {
                    Toast.makeText(
                        this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (email.equals("")) {
                    Toast.makeText(
                        this,
                        "Enter your email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.equals("")) {
                    Toast.makeText(
                        this,
                        "Enter your password",
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
            R.id.rl_back -> {
                finish()
                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.fadeout
                )
            }

            R.id.rl_signup -> {
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.fadeout
                )
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
                    dialog?.dismiss()
                    Log.d(TAG, "User exists, trying to login using entered credentials")
                    performLogin(email, password)
                } else {
                    Log.d(TAG, "User doesn't exist, creating account")
                    dialog?.dismiss()
                    ViewUtils.showSnackBar(
                        activityLoginBinding.getRoot(),
                        this,
                        "User doesn't exist, create account"
                    )

                }
            } else {
                Log.w(TAG, "User check failed", task.exception)
                dialog?.dismiss()
            }
            //hide progress dialog

        }
    }

    private fun performLogin(email: String, password: String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {

                    user_id = firebaseAuth?.getCurrentUser()?.getUid()!!
                    val token_id = FirebaseInstanceId.getInstance().token
                    val addValue: MutableMap<String, Any> = HashMap<String, Any>()
                    addValue["device_token"] = token_id!!
                    addValue["online"] = "true"
                    mDatabase!!.child(user_id).updateChildren(addValue,
                        DatabaseReference.CompletionListener { databaseError, databaseReference ->
                            if (databaseError == null) {
                                dialog?.dismiss()
                                foundAppSharedPreference.user_id = user_id
                                val intent = Intent(this, BaseActivity::class.java)
                                startActivity(intent)
                                finishAffinity()
                                overridePendingTransition(
                                    R.anim.fadein,
                                    R.anim.fadeout
                                )

                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    databaseError.toString(),
                                    Toast.LENGTH_SHORT

                                ).show()
                                Log.e("Error is : ", databaseError.toString())
                            }
                        })


                } else {
                    Log.e(TAG, "Login fail", task.exception)
                    dialog?.dismiss()
                }
                //hide progress dialog


            }
    }


}