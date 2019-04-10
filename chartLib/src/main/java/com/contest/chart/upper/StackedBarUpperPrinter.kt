package com.contest.chart.upper

import android.graphics.Canvas
import android.graphics.Paint
import com.contest.chart.base.ChartView
import com.contest.chart.base.DetalsProvider
import com.contest.chart.model.BrokenLine
import com.contest.chart.utils.Constants

class StackedBarUpperPrinter(
    line: BrokenLine,
    provider: DetalsProvider,
    thickness: Float,
    val view: ChartView
) : UpperChatLinePrinter(line, provider, thickness) {

    init {
        paint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun draw(canvas: Canvas, xStep: Float, yStep: Float) {
        if (!line.isEnabled) return

        path.reset()
        path.moveTo(0f, view.getChartHeight().toFloat())

        val range = provider.getFocusedRange()

        for (positionX in range) {
            val x1 = (positionX - positionOffset) * xStep
            val x2 = (positionX + 1 - positionOffset) * xStep

            val originY1 = line.points[positionX]
            val y1 = getStartY() - originY1 * yStep - Constants.BOTTOM_VERTICAL_OFFSET

            path.lineTo(x1, y1)
            path.lineTo(x2, y1)
        }

        val lastX = (range.last + 1 - positionOffset) * xStep
        val lastOriginY1 = line.points[range.last + 1]
        val lastY = getStartY() - lastOriginY1 * yStep - Constants.BOTTOM_VERTICAL_OFFSET

        path.lineTo(lastX, lastY)
        path.lineTo(view.getChartWidth().toFloat(), view.getChartHeight().toFloat())
        canvas.drawPath(path, paint)
    }
}