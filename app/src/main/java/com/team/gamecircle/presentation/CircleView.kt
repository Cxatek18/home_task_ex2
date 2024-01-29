package com.team.gamecircle.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.team.gamecircle.R

class CircleView(
    context: Context, attributeSet: AttributeSet
) : View(context, attributeSet) {

    private var startAngle = -180f
    private val paint = Paint()
    private val colors = listOf(
        ContextCompat.getColor(getContext(), R.color.red),
        ContextCompat.getColor(getContext(), R.color.orange),
        ContextCompat.getColor(getContext(), R.color.yellow),
        ContextCompat.getColor(getContext(), R.color.green),
        ContextCompat.getColor(getContext(), R.color.blue_light),
        ContextCompat.getColor(getContext(), R.color.blue),
        ContextCompat.getColor(getContext(), R.color.violet),
    )
    private val sweepAngle = 360f / colors.size


    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCircleButton(canvas)
    }

    private fun drawCircleButton(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radiusCircle = width.coerceAtMost(height) / 2f
        for (index in colors.indices) {
            paint.color = colors[index]
            canvas.drawArc(
                centerX - radiusCircle,
                centerY - radiusCircle,
                centerX + radiusCircle,
                centerY + radiusCircle,
                startAngle + index * sweepAngle,
                sweepAngle,
                true,
                paint
            )
        }
    }
}