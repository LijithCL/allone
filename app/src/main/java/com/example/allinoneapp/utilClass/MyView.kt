package com.example.allinoneapp.utilClass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View


class MyView(context: Context?,rad:Int,col:String,wid:Int,hei:Int) : View(context) {
    var paint: Paint? = null
    var paint1: Paint? = null
    var paint2: Paint? = null
    var red=rad
    var color=col
    var widt=wid
    var heig=hei
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var startX = 20
        var startY = 100
        var stopX = 140
        var stopY = 30

        val x = widt
        val y = heig
        val radius= red
        paint?.style = Paint.Style.FILL
        paint?.color = Color.WHITE
//        paint?.let { canvas.drawPaint(it) }
        // Use Color.parseColor to define HTML colors
        paint?.color = Color.parseColor(color)
        paint?.let {
            canvas.drawCircle(x.toFloat(),y.toFloat(), radius.toFloat(),
                it
            )
        }

//        val rectF = RectF(50F, 20F, 100F, 80F)
//        paint1!!.color = Color.BLACK
//        canvas.drawArc(rectF, 180F, 0F, true, paint1!!)
//        val rect = RectF()


//        paint1!!.color = Color.BLACK
//        paint2!!.color = Color.BLUE
//        canvas.drawArc(rect, 0f, 60f, false, paint1!!)
//        canvas.drawArc(rect, 60f, 60f, false, paint1!!)
//        canvas.drawArc(rect, 120f, 60f, false, paint2!!)
//        canvas.drawArc(rect, 180f, 60f, false, paint1!!)
//        canvas.drawArc(rect, 240f, 60f, false, paint1!!)
//        canvas.drawArc(rect, 300f, 60f, false, paint2!!)
////        paint1?.let { canvas.drawLine((x/2).toFloat(), (y/2).toFloat(), 20F, (y/2).toFloat(), it) }



    }

    init {
        paint = Paint()
        paint1 = Paint()
        paint2 = Paint()
    }

}