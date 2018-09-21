package com.yanqing.kotlindemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.yanqing.kotlindemo.activity.BaseActivity
import com.yanqing.kotlindemo.fragment.dialogfragment.CommonMessageDialogFragment

open class BaseFragment : Fragment() {


    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    protected fun showToast(msg: String) {
        val activity = activity
        if (activity != null && activity is BaseActivity) {
            activity.showToast(msg)
        }
    }


    protected fun showCommonMessageDialogFragment(tag: String, message: String, mInterface: CommonMessageDialogFragment.OnCommonMessageDialogInterface) {
        val activity = activity
        if (activity != null && activity is BaseActivity) {
            activity.showCommonMessageDialogFragment(tag, message, mInterface)
        }
    }

    protected fun showDialogFragment(tag: String, arg: Bundle?) {
        val activity = activity
        if (activity != null && activity is BaseActivity) {
            activity.showDialogFragment(tag, arg)
        }
    }
}