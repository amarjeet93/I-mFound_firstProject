package com.e.found.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import cc.cloudist.acplibrary.ACProgressFlower
import com.e.found.R
import com.e.found.databinding.ActivitySignupBinding
import java.util.*
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity(), View.OnClickListener {
    var name = ""
    var email = ""
    var dob = ""
    var password = ""
    private var mYear = 0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    var dialog :ACProgressFlower? = null
    lateinit var activitySignupBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------
        clickListener()

    }
    public fun clickListener()
    {
        activitySignupBinding.editBirth.setOnClickListener(this)
        activitySignupBinding.tvNext.setOnClickListener(this)
        activitySignupBinding.rlBack.setOnClickListener(this)
    }

    public fun getValue() {
        name = activitySignupBinding.editName.text.toString()
        email = activitySignupBinding.editEmail.text.toString()
        dob = activitySignupBinding.editBirth.text.toString()
        password = activitySignupBinding.editPass.text.toString()
        if(name.equals("")||name.equals("")||name.equals("")||name.equals(""))
        {
            Toast.makeText(
                this,
                "Fill all Fields",
                Toast.LENGTH_SHORT
            ).show()
        }else if(name.equals("")){
            Toast.makeText(
                this,
                "Enter your name",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(email.equals("")){
            Toast.makeText(
                this,
                "Enter your email",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(dob.equals("")||dob.equals("Enter Date of Birth")){
            Toast.makeText(
                this,
                "Enter your date of birth",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if(password.equals("")){
            Toast.makeText(
                this,
                "Enter your password",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches())
            {
                Toast.makeText(
                    this,
                    "Enter valid email",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                val intent = Intent(this, ChooseGenderActivity::class.java)
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("password", password);
                intent.putExtra("dob", dob);
                startActivity(intent)

                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.fadeout
                )
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.tv_next -> {
                getValue()
            }
            R.id.rl_back -> {
                finish()
                overridePendingTransition(
                    R.anim.fadein,
                    R.anim.fadeout
                )
            }
            R.id.edit_birth -> {
                val c: Calendar = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)


                val datePickerDialog = DatePickerDialog(
                    this,
                    { view, year, monthOfYear, dayOfMonth ->
                        activitySignupBinding.editBirth.setText((monthOfYear + 1).toString() + "/" + dayOfMonth.toString() + "/" + year)
                        activitySignupBinding.editBirth.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                    },
                    mYear,
                    mMonth,
                    mDay
                )
                datePickerDialog.show()
            }
        }
    }

}