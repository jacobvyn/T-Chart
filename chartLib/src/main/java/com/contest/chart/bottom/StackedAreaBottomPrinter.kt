package com.contest.chart.bottom

import android.graphics.Canvas
import android.graphics.Paint
import com.contest.chart.base.ChartView
import com.contest.chart.base.DetalsProvider
import com.contest.chart.base.LinePrinter
import com.contest.chart.model.BrokenLine

open class StackedAreaBottomPrinter(
    line: BrokenLine,
    provider: DetalsProvider,
    thickness: Float,
    val view: ChartView
) : LinePrinter(line, provider, thickness) {

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun draw(canvas: Canvas, xStep: Float, yStep: Float) {
        if (!line.isEnabled) return

        path.reset()
        path.moveTo(0f, view.getChartHeight().toFloat())

        val size = line.points.size - 1
        for (positionX in 0 until size) {

            val x1 = positionX * xStep
            val originY1 = line.points[positionX]
            val y1 = getStartY() - originY1 * yStep
            path.lineTo(x1,y1)
        }
        path.lineTo(view.getChartWidth().toFloat(), view.getChartHeight().toFloat())
        canvas.drawPath(path, paint)
    }
}