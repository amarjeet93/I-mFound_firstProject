package com.e.found.Presenter.basePackage

import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment

interface FoundAppPresenter {
    fun navigateTo(fragment: Fragment?)
    fun navigateToWithoutAnimation(fragment: Fragment?)
    fun navigateToClearAllPreviousFragment(fragment: Fragment?)
    fun navigateToAnimation(
        fragment: Fragment?,
        sharedImageView: ImageView?
    )

    fun navigateWithBundle(
        fragment: Fragment?,
        bundle: Bundle?
    )

    fun oneStepBack()
    fun navigateReplacingCurrent(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?
    )

    fun navigateReplacingCurrentWithChild(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?
    )

    fun navigateReplacingCurrentWithBundle(
        currentFragment: Fragment?,
        fragmentToNavigate: Fragment?,
        bundle: Bundle?
    )

    fun hideBackNavigation()
    fun showBackNavigation()
    fun backToParent()
    fun changeTitle(title: String?)
}