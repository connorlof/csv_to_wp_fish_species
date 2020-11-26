package com.loftydevelopment.species_csv_to_wp.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File

class CsvReader {
    companion object {
        @JvmStatic
        fun readCsv(path: String): List<List<String>> = csvReader().readAll(File(path))
    }
}