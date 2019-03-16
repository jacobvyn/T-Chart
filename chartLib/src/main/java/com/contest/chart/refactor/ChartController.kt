package com.contest.chart.refactor

import android.graphics.Canvas
import com.contest.chart.bottom.LinePainter
import com.contest.chart.base.Focus
import com.contest.chart.base.Refresher
import com.contest.chart.model.LineChartData
import com.contest.chart.utils.Constants

open class ChartController(
        private val chartData: LineChartData,
        private val width: Int,
        private val height: Int,
        private val refresher: Refresher) : Focus {

    private val lineControllers = ArrayList<LinePainter>()
    private var xScale = 0f
    private var yScale = 0f
    private var focusRange = 0..100

    init {
        chartData.brokenLines.forEach {
            lineControllers.add(LinePainter(it, height, this))
        }
        calculateScale()
    }

    fun draw(canvas: Canvas) {
        lineControllers.forEach {
            it.draw(canvas, xScale, yScale)
        }
    }

    private fun calculateScale() {
        val sizes = mutableListOf<Int>()
        chartData.brokenLines.forEach {
            if (it.isEnabled) sizes.add(it.points.size)
        }

        if (sizes.isNotEmpty()) {
            xScale = (width - Constants.SPARE_SPACE_X) / sizes.max()!!.toFloat()
        }

        val maxValues = mutableListOf<Float>()
        chartData.brokenLines.forEach {
            if (it.isEnabled) maxValues.add(it.points.max()!!)
        }

        if (maxValues.isNotEmpty()) {
            yScale = (height - Constants.SPARE_SPACE_Y) / maxValues.max()!!
        }
    }


    fun onFocusedRangeChanged(left: Int, right: Int) {
        lineControllers.get(0)?.let {
            val size = it.line.points.size
            val focusLeft = size * left / 100
            val focusRight = size * right / 100
            focusRange = focusLeft..focusRight
        }
        refresher.refresh()
    }

    fun onLineStateChanged(name: String, isShow: Boolean) {
        chartData.brokenLines.forEach { line ->
            if (line.name == name) line.isEnabled = isShow
        }
        calculateScale()
        refresher.refresh() // todo need hide/ show line with animation
    }

    override fun isFocused(pos: Int): Boolean {
        return pos in focusRange
    }
}


