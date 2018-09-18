package com.yanqing.kotlindemo.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.yanqing.kotlindemo.R


class LoadingView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mLoadingImage: ImageView = ImageView(context)
    private val mLoadingMessage = TextView(context)

    init {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        mLoadingImage.setImageResource(R.drawable.ic_loading)
        mLoadingMessage.text = "正在加载"
        mLoadingMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        mLoadingMessage.setTextColor(resources.getColor(R.color.colorPrimary))
        mLoadingMessage.gravity = Gravity.CENTER

        val loadingImageSize = resources.getDimension(R.dimen.load_image_size).toInt()
        val loadingImageMargin = resources.getDimension(R.dimen.load_image_margin).toInt()
        val params = LayoutParams(loadingImageSize, loadingImageSize)
        params.setMargins(loadingImageMargin, loadingImageMargin, loadingImageMargin, loadingImageMargin)
        val messageParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(mLoadingImage, params)
        addView(mLoadingMessage, messageParams)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        animation.duration = 1500
        animation.repeatMode = Animation.RESTART
        animation.repeatCount = Animation.INFINITE
        animation.interpolator = LinearInterpolator()
        mLoadingImage.startAnimation(animation)
    }

    override fun onDetachedFromWindow() {
        mLoadingImage.clearAnimation()
        super.onDetachedFromWindow()
    }
}