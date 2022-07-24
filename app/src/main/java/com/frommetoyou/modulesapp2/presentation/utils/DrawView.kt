package com.frommetoyou.modulesapp2.presentation.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class DrawView(context: Context?, var rect: Rect, var text: String) : View(context) {

    private lateinit var boundaryPaint: Paint
    private lateinit var textPaint: Paint

    init {
        init()
    }

    private fun init() {
        boundaryPaint = Paint()
        boundaryPaint.color = Color.BLACK
        boundaryPaint.strokeWidth = 10f
        boundaryPaint.style = Paint.Style.STROKE

        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 48f
        textPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText(text, rect.centerX().toFloat(), rect.centerY().toFloat(), textPaint)
        canvas?.drawRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            boundaryPaint
        )
    }
}
