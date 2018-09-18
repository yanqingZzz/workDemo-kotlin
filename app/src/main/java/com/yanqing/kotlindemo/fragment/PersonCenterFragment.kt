package com.yanqing.kotlindemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanqing.kotlindemo.R

class PersonCenterFragment : BaseFragment() {
    companion object {
        const val TAG = "PersonCenterFragment"

        fun newInstance(): PersonCenterFragment {
            val homeFragment = PersonCenterFragment();
            return homeFragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_person_center, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}