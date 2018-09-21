package com.yanqing.kotlindemo.fragment

import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.constant.DialogMessage
import com.yanqing.kotlindemo.fragment.dialogfragment.BaseDialogFragment
import com.yanqing.kotlindemo.fragment.dialogfragment.CommonMessageDialogFragment

open class BaseFragment : Fragment() {
    protected fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    fun showCommonMessageDialogFragment(tag: String, message: String, mInterface: CommonMessageDialogFragment.OnCommonMessageDialogInterface) {
        val arg = Bundle()
        arg.putString(Constant.MESSAGE, message)
        val dialogFragment = onCreateDialogFragment(tag, arg, null)
        if (dialogFragment != null && dialogFragment is CommonMessageDialogFragment) {
            dialogFragment.setInterface(mInterface)
        }
        showDialogFragment(tag, dialogFragment)
    }

    fun showDialogFragment(tag: String, arg: Bundle?) {
        showDialogFragment(tag, onCreateDialogFragment(tag, arg, onCreateDialogFragmentView(tag, arg)))
    }

    private fun showDialogFragment(tag: String, dialogFragment: BaseDialogFragment?) {
        val fragment = fragmentManager.findFragmentByTag(tag)
        val ft = fragmentManager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment)
        }

        if (dialogFragment != null) {
            ft.add(dialogFragment, tag)
            ft.commitAllowingStateLoss()
        }
    }

    fun dismissDialogFragment(tag: String) {
        val fragment = fragmentManager.findFragmentByTag(tag)
        val ft = fragmentManager.beginTransaction()
        if (fragment != null) {
            ft.remove(fragment)
            ft.commitAllowingStateLoss()
        }

    }

    open fun onCreateDialogFragmentView(tag: String, arg: Bundle?): View? {
        when (tag) {
            else -> return null
        }
    }

    open fun onCreateDialogFragment(tag: String, arg: Bundle?, view: View?): BaseDialogFragment? {
        when (tag) {
            DialogMessage.COMMON_MESSAGE_TAG -> {
                val message = arg?.getString(Constant.MESSAGE)
                val dialogFragment = CommonMessageDialogFragment()
                if (!TextUtils.isEmpty(message)) {
                    dialogFragment.setMessage(message!!)
                }
                return dialogFragment
            }
            else -> return null
        }
    }


}