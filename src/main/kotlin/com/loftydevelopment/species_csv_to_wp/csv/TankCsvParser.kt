package com.loftydevelopment.species_csv_to_wp.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

class TankCsvParser {
    fun readCsv() {
        val directory = File("").absolutePath
        println("Path: $directory")

        val file: File = File("$directory/assets/csv/tanks.csv")
        val rows: List<List<String>> = csvReader().readAll(file)
        println("Rows: $rows")
    }
}