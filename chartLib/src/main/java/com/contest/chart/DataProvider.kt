package com.contest.chart

import com.contest.chart.model.InterceptionInfo

interface DataProvider {
    fun getInterceptions(x: Float): List<InterceptionInfo>
    fun setChartDetailsProvider(chartDetailsProvider: ChartDetailsProvider)
}