package com.contest.chart.base

import android.graphics.Canvas
import android.graphics.Path
import com.contest.chart.model.BrokenLine

open class LinePrinter(
    line: BrokenLine,
    provider: DetalsProvider,
    thickness: Float
) : BaseLinePrinter(line, thickness, provider) {

    protected val path = Path()

    override fun draw(canvas: Canvas, xStep: Float, yStep: Float) {
        if (!line.isEnabled) return

        path.reset()
        val size = line.points.size - 1

        for (positionX in 0 until size) {

            val x1 = positionX * xStep
            val x2 = (positionX + 1) * xStep

            val originY1 = line.points[positionX]
            val originY2 = line.points[positionX + 1]
            val y1 = getStartY() - originY1 * yStep
            val y2 = getStartY() - originY2 * yStep

            path.moveTo(x1, y1)
            path.lineTo(x2, y2)
        }

        canvas.drawPath(path, paint)
    }
}