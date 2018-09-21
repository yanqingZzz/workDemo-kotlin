package com.yanqing.kotlindemo.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.constant.DialogMessage
import com.yanqing.kotlindemo.fragment.dialogfragment.BaseDialogFragment
import com.yanqing.kotlindemo.fragment.dialogfragment.CommonMessageDialogFragment

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) = beginTransaction().func().commit()

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        val ft = supportFragmentManager.beginTransaction()
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