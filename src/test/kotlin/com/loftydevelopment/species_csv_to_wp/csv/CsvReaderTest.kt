package com.loftydevelopment.species_csv_to_wp.csv

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File

class CsvReaderTest {
    // Test valid case, returns correct list size and expected contents
    @Test
    fun readCsv() {
        val rows = CsvReader.readCsv("${File("").absolutePath}/assets/csv/test.csv")

        // Expected rows is 3
        assertThat(rows.size).isEqualTo(3)

        // Contents matches file
        // Header
        assertThat(rows[0][0]).isEqualTo("Column 1")
        assertThat(rows[0][1]).isEqualTo("Column 2")
        assertThat(rows[0][2]).isEqualTo("Column 3")

        // Row 1
        assertThat(rows[1][0]).isEqualTo("1a")
        assertThat(rows[1][1]).isEqualTo("1b")
        assertThat(rows[1][2]).isEqualTo("1c")

        // Row 2
        assertThat(rows[2][0]).isEqualTo("2a")
        assertThat(rows[2][1]).isEqualTo("2b")
        assertThat(rows[2][2]).isEqualTo("2c")
    }

    // Test invalid path throws FileNotFoundException
    //val rows = CsvReader.readCsv("${File("").absolutePath}/assets/test.csv")
}