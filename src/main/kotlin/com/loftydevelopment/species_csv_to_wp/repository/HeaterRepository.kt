package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.model.Heater

interface HeaterRepository {
    abstract fun getHeaters(): List<Heater>
}