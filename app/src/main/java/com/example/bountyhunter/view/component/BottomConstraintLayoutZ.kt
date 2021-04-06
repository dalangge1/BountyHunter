package com.example.bountyhunter.view.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

//自定义view
//加了两个函数 show  hide  同时动态改变大小

class BottomConstraintLayoutZ : ConstraintLayout {
    private lateinit var animY : ValueAnimator
    var mPaint: Paint = Paint()
    var mHeight = 0
    var trY = 0f
    var isHide = false
    var isNotInit = true

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if(isNotInit){
            mHeight = measuredHeight
            isNotInit = false
        }
    }

    fun show(){
        isHide = false
        animY = ValueAnimator.ofFloat(trY, 0f)
        animY.repeatCount = 0
        animY.repeatMode = ValueAnimator.REVERSE
        animY.duration = 500
        animY.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            trY = value
            val params = layoutParams
            params.height = (mHeight - trY).toInt()
            layoutParams = params
        }
        animY.start()
    }
    fun hide(){
        isHide = true
        animY = ValueAnimator.ofFloat(trY, mHeight.toFloat())
        animY.repeatCount = 0
        animY.repeatMode = ValueAnimator.REVERSE
        animY.duration = 500
        animY.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            trY = value
            val params = layoutParams
            params.height = (mHeight - trY).toInt()
            layoutParams = params
        }
        animY.start()
    }



}