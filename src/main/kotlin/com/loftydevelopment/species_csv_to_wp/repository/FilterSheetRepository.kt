package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.csv.CsvReader
import com.loftydevelopment.species_csv_to_wp.csv.FilterSheet
import com.loftydevelopment.species_csv_to_wp.model.Filter

class FilterSheetRepository : FilterRepository {
    private lateinit var filterSheet: FilterSheet
    
    init {
        val filterCsvPath = "C:/Users/conno/Documents/Web/Aquarium API/filters.csv"
        val filterRows = CsvReader.readCsv(filterCsvPath)
        filterSheet = FilterSheet(filterRows)
    }

    override fun getFilters(): List<Filter> = filterSheet.filters
}