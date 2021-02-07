package com.example.bountyhunter.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import com.amap.api.maps2d.MapView


class AMapView: MapView {
    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, int: Int): super(context, attributeSet, int)


    override fun onDraw(canvas: Canvas?) {
        Log.e("sandyzhang", width.toString())

        super.onDraw(canvas)
        canvas?.drawRect(Rect(0,0,100,100), Paint().apply { color = Color.RED })
    }


}