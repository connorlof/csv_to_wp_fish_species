package com.loftydevelopment.species_csv_to_wp.csv

import com.loftydevelopment.species_csv_to_wp.model.Heater
import com.loftydevelopment.species_csv_to_wp.util.Range

class HeaterSheet(private val rowList: List<List<String>>) {
    val numRows: Int = rowList.size
    val numColumns: Int = rowList[0].size
    val heaters: List<Heater> = parseRows()

    private fun parseRows(): List<Heater> {
        val heaterList: MutableList<Heater> = mutableListOf()

        // Skip header (1st row)
        for (i in 1 until rowList.size) {
            heaterList.add(parseRow(rowList[i]))
        }

        return heaterList
    }

    private fun parseRow(row: List<String>): Heater {
        val gallonRange: Range = Range.fromString(row[0])
        val name: String = row[1]
        val imageLink: String = row[2]
        val link: String = row[3]

        return Heater(gallonRange, name, imageLink, link)
    }

    override fun toString(): String {
        return rowList.toString()
    }
}