package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.model.Filter

interface FilterRepository {
    abstract fun getFilters(): List<Filter>
}