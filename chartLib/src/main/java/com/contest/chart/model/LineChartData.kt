package com.contest.chart.model

class LineChartData {
    val brokenLines = ArrayList<BrokenLine>()
    val types = HashMap<String, String>()
    lateinit var timeLine: LongArray
    var percentage: Boolean = false
    var stacked: Boolean = false
    var yScaled: Boolean = false


    fun isOfType(type: Type): Boolean {
        return stacked && types.any { entry ->
            entry.value.toLowerCase() == type.name.toLowerCase()
        }
    }
}

enum class Type {
    LINE, BAR, AREA
}