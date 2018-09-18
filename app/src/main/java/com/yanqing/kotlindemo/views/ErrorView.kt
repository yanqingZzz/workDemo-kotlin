package com.yanqing.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import com.yanqing.kotlindemo.R


class ErrorView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mErrorImage = ImageView(context)
    private val mErrorMessage = TextView(context)

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        mErrorImage.setImageResource(R.drawable.ic_empty)
        mErrorMessage.text = "获取内容失败\n点击重试"
        mErrorMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        mErrorMessage.setTextColor(resources.getColor(R.color.colorPrimary))
        mErrorMessage.gravity = Gravity.CENTER

        val loadingImageSize = resources.getDimension(R.dimen.load_image_size).toInt()
        val loadingImageMargin = resources.getDimension(R.dimen.load_image_margin).toInt()
        val params = LayoutParams(loadingImageSize, loadingImageSize)
        params.setMargins(loadingImageMargin, loadingImageMargin, loadingImageMargin, loadingImageMargin)
        val messageParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(mErrorImage, params)
        addView(mErrorMessage, messageParams)
    }

    fun setErrorMessage(message: String) {
        mErrorMessage.text = message
    }
}