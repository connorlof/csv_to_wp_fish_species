package com.loftydevelopment.species_csv_to_wp

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun main() {
    println("Hello Kotlin/Native!")

    val csvData: String = "a,b,c\nd,e,f"
    val rows: List<List<String>> = csvReader().readAll(csvData)

    println(rows)
}