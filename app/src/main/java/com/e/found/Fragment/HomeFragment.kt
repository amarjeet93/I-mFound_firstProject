package com.e.found.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.e.found.Activities.BaseActivity
import com.e.found.Activities.LoginActivity
import com.e.found.R
import com.e.found.Utils.FoundAppSharedPreference
import com.e.found.databinding.HomeFragmentBinding


class HomeFragment : Fragment() {
lateinit var layoutHomeFragmentBinding:HomeFragmentBinding

    lateinit var foundAppSharedPreference: FoundAppSharedPreference
    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutHomeFragmentBinding = HomeFragmentBinding.inflate(inflater, container, false)
        foundAppSharedPreference = FoundAppSharedPreference.getInstance(requireActivity())

        layoutHomeFragmentBinding.tvLogout.setOnClickListener {
            foundAppSharedPreference.clearPreference()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            requireActivity().finish()
        }

       return layoutHomeFragmentBinding.root
    }
}
