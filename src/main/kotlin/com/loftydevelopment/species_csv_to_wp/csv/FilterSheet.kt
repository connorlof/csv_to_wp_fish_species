package com.loftydevelopment.species_csv_to_wp.csv

import com.loftydevelopment.species_csv_to_wp.model.Filter
import com.loftydevelopment.species_csv_to_wp.model.enum.FilterType

class FilterSheet(private val rowList: List<List<String>>) {
    val numRows: Int = rowList.size
    val numColumns: Int = rowList[0].size
    val filters: List<Filter> = parseRows()

    private fun parseRows(): List<Filter> {
        val filterList: MutableList<Filter> = mutableListOf()

        // Skip header (1st row)
        for (i in 1 until rowList.size) {
            filterList.add(parseRow(rowList[i]))
        }

        return filterList
    }

    private fun parseRow(row: List<String>): Filter {
        val size: Int = row[0].toInt()
        val type: FilterType = FilterType.valueOf(row[1].toUpperCase())
        val name: String = row[2]
        val imageLink: String = row[3]
        val link: String = row[4]

        return Filter(size, type, name, imageLink, link)
    }

    override fun toString(): String {
        return rowList.toString()
    }
}