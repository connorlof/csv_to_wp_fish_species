package com.loftydevelopment.species_csv_to_wp.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class RangeTest {

    @Test
    fun requireStartGreaterThanEndThrows() {
        assertThatThrownBy{ createRange(start = 100.0, end = 5.0) }
            .withFailMessage("start must be less than or equal to end")
    }

    @Test
    fun isWithinRange() {
        val range = createRange()

        assertThat(range.isWithinRange(5.0)).isTrue
        assertThat(range.isWithinRange(7.0)).isTrue
        assertThat(range.isWithinRange(10.0)).isTrue

        assertThat(range.isWithinRange(-10.0)).isFalse
        assertThat(range.isWithinRange(1.0)).isFalse
        assertThat(range.isWithinRange(20.0)).isFalse
    }

    private fun createRange(
        start: Double = 5.0,
        end: Double = 10.0,
    ) = Range(start = start, end = end)
}