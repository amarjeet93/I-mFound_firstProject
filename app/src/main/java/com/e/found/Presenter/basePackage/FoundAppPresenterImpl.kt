package com.e.found.Presenter.basePackage

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.e.found.Activities.BaseActivity
import com.e.found.R
import com.e.found.Utils.AppUtils.hideKeyboardBackFragment


class FoundAppPresenterImpl(
    var ctx: Context,
    careAppView: FoundAppView?
) : FoundAppPresenter {
    var barberAppView: FoundAppView? = null
    override fun navigateTo(fragment: Fragment?) {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        /*fts.setCustomAnimations(
            R.anim.enter,
            R.anim.exit,
            R.anim.left_to_right,
            R.anim.right_to_left
        );*/

        fts.setCustomAnimations(
            R.anim.fadein,
            R.anim.fadeout
        )

        fts.add(R.id.fragmentHolder, fragment!!)
        fts.addToBackStack(fragment.javaClass.simpleName)
        fts.commit()
    }

    override fun navigateToWithoutAnimation(fragment: Fragment?) {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        fts.setCustomAnimations(
            R.anim.fadein,
            R.anim.fadeout
        )
        fts.add(R.id.fragmentHolder, fragment!!)
        fts.addToBackStack(fragment.javaClass.simpleName)
        fts.commit()
    }

    override fun navigateToClearAllPreviousFragment(fragment: Fragment?) {


        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        fts.remove((ctx as BaseActivity).supportFragmentManager.findFragmentById(R.id.fragmentHolder)!!)
            ;
        fts.setCustomAnimations(
            R.anim.fadein,
            R.anim.fadeout
        )
        fts.add(R.id.fragmentHolder, fragment!!)
//        fts.addToBackStack(fragment.javaClass.simpleName)
        fts.commit()
    }

    override fun navigateToAnimation(
        fragment: Fragment?,
        sharedImageView: ImageView?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (ctx as BaseActivity).supportFragmentManager
                .beginTransaction()
                .addSharedElement(
                    sharedImageView!!,
                    ViewCompat.getTransitionName(sharedImageView)!!
                )
                .addToBackStack(fragment!!.javaClass.simpleName)
                .add(R.id.fragmentHolder, fragment)
                .commit()
        } else {
            val fts =
                (ctx as BaseActivity).supportFragmentManager.beginTransaction()
            // fts.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fts.add(R.id.fragmentHolder, fragment!!)
            fts.addToBackStack(fragment.javaClass.simpleName)
            fts.commit()
        }
    }

    override fun navigateWithBundle(
        fragment: Fragment?,
        bundle: Bundle?
    ) {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        //  fts.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fts.add(R.id.fragmentHolder, fragment!!)
        fragment.arguments = bundle
        fts.addToBackStack(fragment.javaClass.simpleName)
        fts.commit()
    }

    override fun navigateReplacingCurrentWithBundle(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?,
        bundle: Bundle?
    ) {
        fragmentToNavigate!!.arguments = bundle
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        //   fts.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        (ctx as BaseActivity).supportFragmentManager.popBackStack()
        fts.add(R.id.fragmentHolder, fragmentToNavigate)
        fts.addToBackStack(fragmentToNavigate.javaClass.simpleName)
        fts.remove(currentFragment!!).commit()
    }

    override fun oneStepBack() {
        hideKeyboardBackFragment(ctx)
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        val fragmentManager =
            (ctx as BaseActivity).supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStackImmediate()
            fts.commit()
        } else {
            (ctx as BaseActivity).finish()
        }
    }

    override fun navigateReplacingCurrent(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?
    ) {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        fts.setCustomAnimations(R.anim.fadein, R.anim.fadeout)
        (ctx as BaseActivity).supportFragmentManager.popBackStack()
        fts.add(R.id.fragmentHolder, fragmentToNavigate!!)
        fts.addToBackStack(fragmentToNavigate.javaClass.simpleName)
        fts.remove(currentFragment!!).commit()
    }

    override fun navigateReplacingCurrentWithChild(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?
    ) {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        //  fts.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        (ctx as BaseActivity).supportFragmentManager.popBackStack()
        fts.add(R.id.fragmentHolder, fragmentToNavigate!!)
        fts.addToBackStack(fragmentToNavigate.javaClass.simpleName)
        fts.remove(currentFragment!!).commit()
    }

    override fun hideBackNavigation() {
        barberAppView!!.hideNavigationButton()
    }

    override fun showBackNavigation() {
        barberAppView!!.showNavigationButton()
    }

    override fun backToParent() {
        val fts =
            (ctx as BaseActivity).supportFragmentManager.beginTransaction()
        val fragmentManager =
            (ctx as BaseActivity).supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStackImmediate()
        }
        fts.commit()
    }

    override fun changeTitle(title: String?) {
        barberAppView!!.changeTitle(title)
    }

    init {
        barberAppView = barberAppView
    }
}