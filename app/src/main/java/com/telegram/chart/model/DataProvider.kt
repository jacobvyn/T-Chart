package com.telegram.chart.model

import android.content.Context
import com.contest.chart.model.LineChartData
import org.json.JSONArray
import org.json.JSONObject

class DataProvider {

    companion object {
        const val stage1Data = "chart_data.json"
        const val stage2Chart2 = "contest/2/overview.json"
        const val stage2Chart3 = "contest/3/overview.json"
        const val stage2Chart5 = "contest/5/overview.json"
    }

    fun getData(context: Context, callBack: CallBack) {
        try {
            val chartsDataList = ArrayList<Data>()
            val content = context.assets.open(stage1Data).bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(content)

            for (index in 0 until jsonArray.length()) {
                val rawChart = jsonArray[index] as JSONObject
                val rawColumns = rawChart.get("columns") as JSONArray
                val rawTypes = rawChart.get("types") as JSONObject
                val rawNames = rawChart.get("names") as JSONObject
                val rawColors = rawChart.get("colors") as JSONObject
                chartsDataList.add(createData(rawColumns, rawTypes, rawNames, rawColors))
            }
            val mapper = ListLineChartDataMapper()
            val chartData = mapper.apply(chartsDataList)

            callBack.onSuccess(chartData)
        } catch (ex: java.lang.Exception) {
            callBack.onError(ex)
        }
    }

    fun getDataStage2(context: Context, callBack: CallBack) {
        try {

            val chart2 = getChart(context, stage2Chart2)
            val chart3 = getChart(context, stage2Chart3)
            val chart5 = getChart(context, stage2Chart5)

            callBack.onSuccess(listOf(chart2, chart3, chart5))
        } catch (ex: java.lang.Exception) {
            callBack.onError(ex)
        }
    }

    private fun getChart(context: Context, fileName: String): LineChartData {
        val content = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val rawChart = JSONObject(content)

        val rawColumns = rawChart.get("columns") as JSONArray
        val rawTypes = rawChart.get("types") as JSONObject
        val rawNames = rawChart.get("names") as JSONObject
        val rawColors = rawChart.get("colors") as JSONObject
        val percentage = getBoolean(rawChart, "percentage")
        val stacked = getBoolean(rawChart, "stacked")
        val yScaled = getBoolean(rawChart, "y_scaled")

        val data =
            createData(rawColumns, rawTypes, rawNames, rawColors, percentage, stacked, yScaled)

        return LineChartDataMapper().apply(data)
    }

    private fun getBoolean(obj: JSONObject, name: String): Boolean {
        return try {
            obj.getBoolean(name)
        } catch (ex: Exception) {
            false
        }
    }

    private fun createData(
        rawColumns: JSONArray,
        rawTypes: JSONObject,
        rawNames: JSONObject,
        rawColors: JSONObject,
        percentage: Boolean = false,
        stacked: Boolean = false,
        yScaled: Boolean = false
    ): Data {

        val names = toStringStringMap(rawNames)
        val types = toStringStringMap(rawTypes)
        val colors = toStringStringMap(rawColors)
        val columns = toListOfListOfAny(rawColumns)

        return Data(columns, types, names, colors, percentage, stacked, yScaled)
    }

    private fun toListOfListOfAny(jsonArray: JSONArray): List<List<Any>> {
        val mainList = ArrayList<List<Any>>()

        for (index in 0 until jsonArray.length()) {
            val list = ArrayList<Any>()
            val childArray = jsonArray[index] as JSONArray
            for (i in 0 until childArray.length()) {
                list.add(childArray.get(i))
            }
            mainList.add(list)
        }
        return mainList
    }

    private fun toStringStringMap(jsonObject: JSONObject): Map<String, String> {
        val map = HashMap<String, String>()
        jsonObject.keys().forEach { map[it] = jsonObject.getString(it) }
        return map
    }


    interface CallBack {
        fun onSuccess(chartsDataList: List<LineChartData>)
        fun onError(exception: Exception)
    }
}