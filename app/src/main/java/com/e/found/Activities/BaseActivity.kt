package com.e.found.Activities

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.e.found.Presenter.basePackage.FoundAppPresenter
import com.e.found.Presenter.basePackage.FoundAppPresenterImpl
import com.e.found.Presenter.basePackage.FoundAppView
import com.e.found.Fragment.HomeFragment
import com.e.found.R
import com.e.found.databinding.ActivityContainerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


open class BaseActivity : AppCompatActivity(), FoundAppView{
    var foundAppPresenter: FoundAppPresenter? = null
    var activityContainerBinding: ActivityContainerBinding? = null
    lateinit var previos_frag: Fragment
    var mDatabaseReference: DatabaseReference? = null
    private var mauth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setStatusBarTransparent(this)
        activityContainerBinding = DataBindingUtil.setContentView(this, R.layout.activity_container)
        //set top status bar gradient color
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
        val background: Drawable = this.getResources().getDrawable(R.drawable.gradient_norm)
        window.setBackgroundDrawable(background)
        //-------------------------------------------
        mauth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
        foundAppPresenter = FoundAppPresenterImpl(this, this)
        foundAppPresenter!!.navigateTo(HomeFragment.newInstance())

    }




    override fun onBackPressed() {
        foundAppPresenter!!.oneStepBack()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun changeTitle(title: String?) {

    }

    override fun hideNavigationButton() {

    }

    override fun showNavigationButton() {

    }
    override fun onStart() {
        super.onStart()
        val user: FirebaseUser = mauth?.getCurrentUser()!!
        if (user == null) {

        } else {
            //---IF LOGIN , ADD ONLINE VALUE TO TRUE---
            mDatabaseReference!!.child(user.uid).child("online").setValue("true")
        }
    }


}