package com.loftydevelopment.species_csv_to_wp.repository

import com.loftydevelopment.species_csv_to_wp.model.Tank

interface TankRepository {
    abstract fun getTanks(): List<Tank>
}