package com.contest.chart.model

class LineChartData {
    val brokenLines = ArrayList<BrokenLine>()
    lateinit var timeLine: LongArray
    var percentage: Boolean = false
    var stacked: Boolean = false
}