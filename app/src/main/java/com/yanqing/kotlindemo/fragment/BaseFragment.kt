package com.yanqing.kotlindemo.fragment

import android.support.v4.app.Fragment
import android.widget.Toast

open class BaseFragment : Fragment() {
    protected fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}