package com.yanqing.kotlindemo.fragment.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.yanqing.kotlindemo.R

class CommonMessageDialogFragment : BaseDialogFragment() {
    private var mTitle: String = ""
    private var mMessage: String = ""

    private var mInterface: OnCommonMessageDialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTitle = context.resources.getString(R.string.app_name)
    }

    fun setTitle(title: String) {
        mTitle = title
    }

    fun setMessage(message: String) {
        mMessage = message
    }

    fun setInterface(mInterface: OnCommonMessageDialogInterface) {
        this.mInterface = mInterface
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(mTitle)
        builder.setMessage(mMessage)
        builder.setPositiveButton("确定", mConfirmClickLister)
        builder.setNegativeButton("取消", mCancelClickLister)
        return builder.create()
    }

    private val mConfirmClickLister = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            mInterface?.onConfirm(dialog, which)
        }
    }

    private val mCancelClickLister = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            mInterface?.onCancel(dialog, which)
        }
    }

    interface OnCommonMessageDialogInterface {
        fun onConfirm(dialog: DialogInterface?, which: Int)
        fun onCancel(dialog: DialogInterface?, which: Int)
    }
}