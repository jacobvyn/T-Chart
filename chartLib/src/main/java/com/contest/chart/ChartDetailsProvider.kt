package com.contest.chart

interface ChartDetailsProvider {
    fun getStepMap(): Map<Int, Step>
    fun getTotalHeight(): Int
    fun getPositionOffset(): Int
}

class Step(val xStep: Float, val yStep: Float)