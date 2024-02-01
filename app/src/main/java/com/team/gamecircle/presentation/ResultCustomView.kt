package com.team.gamecircle.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.team.gamecircle.data.network.model.ImageInfo

class ResultCustomView(
    context: Context, attributeSet: AttributeSet
) : View(context, attributeSet) {

    var isText = false
    var imageInfo: ImageInfo? = null
    private var bitmap: Bitmap? = null
    private val paintText = Paint()
    private val paintImage = Paint()


    init {
        paintText.color = Color.BLACK
        paintText.style = Paint.Style.FILL
        paintText.textSize = 50f
        paintText.textAlign = Paint.Align.CENTER
        paintImage.color = Color.BLACK
        paintImage.style = Paint.Style.FILL
        paintImage.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isText) {
            renderingText(canvas)
        } else {
            renderingImage(canvas)
            bitmap?.let { canvas.drawBitmap(it, (width / 2f), (height / 2f), paintImage) }
        }
    }

    private fun renderingText(canvas: Canvas) {
        canvas.drawText(TEXT, (width / 2f), (height / 2f), paintText)
    }

    private fun renderingImage(canvas: Canvas) {
        Glide.with(context)
            .asBitmap()
            .load(imageInfo?.file)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    Log.d("ResultCustomView", "onLoadFailed")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.d("ResultCustomView", "onLoadCleared")
                }
            })
    }

    companion object {
        private const val TEXT = "Text is fish"
    }
}
