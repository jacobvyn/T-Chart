package com.telegram.chart.model

import com.contest.chart.model.BrokenLine
import com.contest.chart.model.LineChartData
import java.lang.IllegalArgumentException

class ListLineChartDataMapper : Mapper<List<Data>, List<LineChartData>> {
    override fun apply(inputData: List<Data>): List<LineChartData> {
        val outputDataList = ArrayList<LineChartData>()

        inputData.forEachIndexed { index, data ->

            val mutableColumns = data.columns.toMutableList()

            val lineChart = LineChartData()

            mutableColumns.forEach { list ->
                val mutableColumn = list.toMutableList()
                val id = mutableColumn[0].toString()
                mutableColumn.removeAt(0)

                if (id == "x") {
                    lineChart.timeLine = mutableColumn.toLongArr()
                } else {
                    val points = mutableColumn.toFloatArr()
                    val colorCode = data.colors[id]
                    val line = BrokenLine(points, data.names.getValue(id), colorCode!!)
                    lineChart.brokenLines.add(line)
                }
            }

            outputDataList.add(lineChart)
        }

        if (outputDataList.isEmpty()) throw IllegalArgumentException("Data is Empty")

        return outputDataList
    }
}


class LineChartDataMapper : Mapper<Data, LineChartData> {
    override fun apply(inputData: Data): LineChartData {

        val mutableColumns = inputData.columns.toMutableList()

        val lineChart = LineChartData().apply {
            percentage = inputData.percentage
            stacked = inputData.stacked
            yScaled = inputData.yScaled
        }

        mutableColumns.forEach { list ->
            val mutableColumn = list.toMutableList()
            val id = mutableColumn[0].toString()
            mutableColumn.removeAt(0)

            if (id == "x") {
                lineChart.timeLine = mutableColumn.toLongArr()
            } else {
                val points = mutableColumn.toFloatArr()
                val colorCode = inputData.colors[id]
                val line = BrokenLine(points, inputData.names.getValue(id), colorCode!!)
                lineChart.brokenLines.add(line)
            }
        }

        return lineChart
    }
}

private fun MutableList<Any>.toLongArr(): LongArray {
    val result = LongArray(size)
    var index = 0
    for (element in this) result[index++] = element as Long
    return result
}


private fun MutableList<Any>.toFloatArr(): FloatArray {
    val result = FloatArray(size)
    var index = 0
    for (element in this) result[index++] = (element as Int).toFloat()
    return result
}


