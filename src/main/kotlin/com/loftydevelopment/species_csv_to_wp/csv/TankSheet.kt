package com.loftydevelopment.species_csv_to_wp.csv

import com.loftydevelopment.species_csv_to_wp.model.Tank

class TankSheet(private val rowList: List<List<String>>) {
    val numRows: Int = rowList.size
    val numColumns: Int = rowList[0].size
    val tanks: List<Tank> = parseRows()

    private fun parseRows(): List<Tank> {
        val tankList: MutableList<Tank> = mutableListOf()

        // Skip header (1st row)
        for (i in 1 until rowList.size) {
            tankList.add(parseRow(rowList[i]))
        }

        return tankList
    }

    private fun parseRow(row: List<String>): Tank {
        val size: Int = row[0].toInt()
        val name: String = row[1]
        val imageLink: String = row[2]
        val link: String = row[3]

        return Tank(size, name, imageLink, link)
    }

    override fun toString(): String {
        return rowList.toString()
    }
}