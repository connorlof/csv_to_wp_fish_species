package com.loftydevelopment.species_csv_to_wp.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner.StrictStubs

class QuantityTest {

    @Test
    fun requireSingleValueInit() {
        assertThatThrownBy { createQuantity(isSingleValueOrGreater = true, values = listOf(5, 6)) }
            .withFailMessage("Only a single value may be included if isSingleValueOrGreater")
    }

    @Test
    fun requireInfiniteWhenNoValuesInit() {
        assertThatThrownBy { createQuantity(isInfinite = false, values = listOf()) }
            .withFailMessage("isInfinite must be set if no values are set")
    }

    @Test
    fun toStringReturnsExpectedValues() {
        // isInfinite set as true
        assertThat(createQuantity(isInfinite = true).toString()).isEqualTo("any")

        // isSingleValueOrGreater set as true
        assertThat(createQuantity(isSingleValueOrGreater = true).toString()).isEqualTo("1+")

        // Single value
        assertThat(createQuantity().toString()).isEqualTo("1")

        // Multiple values
        assertThat(createQuantity(values = listOf(1, 2)).toString()).isEqualTo("1, 2")
        assertThat(createQuantity(values = listOf(1, 2, 4, 5)).toString()).isEqualTo("1, 2, 4, 5")
    }

    @Test
    fun isWithinRange() {
        // Infinite accepts any value
        val infiniteQuantity = createQuantity(isInfinite = true)
        assertThat(infiniteQuantity.isWithinRange(0.0)).isTrue
        assertThat(infiniteQuantity.isWithinRange(-1.0)).isTrue
        assertThat(infiniteQuantity.isWithinRange(1.0)).isTrue
        assertThat(infiniteQuantity.isWithinRange(Double.MAX_VALUE)).isTrue
        assertThat(infiniteQuantity.isWithinRange(Double.MIN_VALUE)).isTrue

        // Single value or greater accepts above, equal, but not below
        val singleValueGreaterThan = createQuantity(isSingleValueOrGreater = true, values = listOf(5))
        assertThat(singleValueGreaterThan.isWithinRange(0.0)).isFalse
        assertThat(singleValueGreaterThan.isWithinRange(4.0)).isFalse
        assertThat(singleValueGreaterThan.isWithinRange(5.0)).isTrue
        assertThat(singleValueGreaterThan.isWithinRange(6.0)).isTrue

        // Multiple values accepts either but not another value
        val multipleValues = createQuantity(values = listOf(2, 4, 6))
        assertThat(multipleValues.isWithinRange(1.0)).isFalse
        assertThat(multipleValues.isWithinRange(7.0)).isFalse
        assertThat(multipleValues.isWithinRange(2.0)).isTrue
        assertThat(multipleValues.isWithinRange(4.0)).isTrue
        assertThat(multipleValues.isWithinRange(6.0)).isTrue
    }

    private fun createQuantity(
            isInfinite: Boolean = false,
            isSingleValueOrGreater: Boolean = false,
            values: List<Int> = listOf(1),
    ) = Quantity(isInfinite = isInfinite, isSingleValueOrGreater = isSingleValueOrGreater, values = values)

}