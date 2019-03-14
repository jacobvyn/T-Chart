package com.telegram.chart.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable

class DataProvider {

    val fileName = "chart_data.json"

    fun getData(context: Context): Observable<List<Data>> {
        return Observable.fromCallable {
            val content = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Data>>() {}.type
            Gson().fromJson<List<Data>>(content, listType)
        }
    }
}