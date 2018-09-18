package com.yanqing.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.*
import com.yanqing.kotlindemo.R


class EmptyView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mEmptyImage = ImageView(context)
    private val mEmptyMessage = TextView(context)

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        mEmptyImage.setImageResource(R.drawable.ic_empty)
        mEmptyMessage.text = "内容为空"
        mEmptyMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        mEmptyMessage.setTextColor(resources.getColor(R.color.colorPrimary))
        mEmptyMessage.gravity = Gravity.CENTER

        val loadingImageSize = resources.getDimension(R.dimen.load_image_size).toInt()
        val loadingImageMargin = resources.getDimension(R.dimen.load_image_margin).toInt()
        val params = LayoutParams(loadingImageSize, loadingImageSize)
        params.setMargins(loadingImageMargin, loadingImageMargin, loadingImageMargin, loadingImageMargin)
        val messageParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(mEmptyImage, params)
        addView(mEmptyMessage, messageParams)
    }

    fun setEmptyMessage(message: String) {
        mEmptyMessage.text = message
    }
}