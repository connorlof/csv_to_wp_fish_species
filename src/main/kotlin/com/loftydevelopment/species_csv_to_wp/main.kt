package com.loftydevelopment.species_csv_to_wp

import com.loftydevelopment.species_csv_to_wp.csv.TankCsvParser

fun main() {
    val csvParser = TankCsvParser()
    csvParser.readCsv()
}