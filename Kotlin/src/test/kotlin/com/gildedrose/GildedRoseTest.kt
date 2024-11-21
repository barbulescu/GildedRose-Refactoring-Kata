package com.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream
import kotlin.random.Random.Default.nextInt
import kotlin.streams.asStream

internal class GildedRoseTest {

    @ParameterizedTest
    @ValueSource(strings = [CHEESE, CONCERT, "foo"])
    fun `quality can never be negative`(name: String) {
        val quality = -nextInt(1, 1000)
        val item = Item(name, nextInt(), quality)
        assertThatThrownBy { item.updateQuality() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("quality must be in [0..50], but it was $quality")
    }

    @ParameterizedTest
    @ValueSource(strings = [CHEESE, CONCERT, "foo"])
    fun `quality can never be more than 50`(name: String) {
        val quality = nextInt(51, 1000)
        val item = Item(name, nextInt(), quality)
        assertThatThrownBy { item.updateQuality() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("quality must be in [0..50], but it was $quality")
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 0, 1])
    fun `sulfuras quality is always 80`(sellIn: Int) {
        val quality = nextIntExcept(80)
        val item = Item(SULFURAS, sellIn, quality)
        assertThatThrownBy { item.updateQuality() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("sulfuras quality must be always 80, but it was $quality")

    }

    @ParameterizedTest
    @ArgumentsSource(TestDataProvider::class)
    fun foo(testData: TestData) {
        val item = testData.item()
        item.updateQuality()
        assertThat(item.toTestItem()).isEqualTo(testData.expected())
    }

}

class TestDataProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = sequenceOf(
        TestData(name = "foo", beforeSellIn = 5, beforeQuality = 0, afterSellIn = 4, afterQuality = 0),
        TestData(name = "foo", beforeSellIn = 5, beforeQuality = 10, afterSellIn = 4, afterQuality = 9),
        TestData(name = "foo", beforeSellIn = 1, beforeQuality = 10, afterSellIn = 0, afterQuality = 9),
        TestData(name = "foo", beforeSellIn = 0, beforeQuality = 10, afterSellIn = -1, afterQuality = 8),
        TestData(name = "foo", beforeSellIn = -1, beforeQuality = 10, afterSellIn = -2, afterQuality = 8),
        TestData(name = "foo", beforeSellIn = 0, beforeQuality = 0, afterSellIn = -1, afterQuality = 0),
        TestData(name = "foo", beforeSellIn = 0, beforeQuality = 2, afterSellIn = -1, afterQuality = 0),
        TestData(name = "foo", beforeSellIn = 1, beforeQuality = 1, afterSellIn = 0, afterQuality = 0),

        TestData(name = SULFURAS, beforeSellIn = 5, beforeQuality = 80, afterSellIn = 5, afterQuality = 80),
        TestData(name = SULFURAS, beforeSellIn = 1, beforeQuality = 80, afterSellIn = 1, afterQuality = 80),
        TestData(name = SULFURAS, beforeSellIn = 0, beforeQuality = 80, afterSellIn = 0, afterQuality = 80),
        TestData(name = SULFURAS, beforeSellIn = -1, beforeQuality = 80, afterSellIn = -1, afterQuality = 80),

        TestData(name = CHEESE, beforeSellIn = 5, beforeQuality = 0, afterSellIn = 4, afterQuality = 1),
        TestData(name = CHEESE, beforeSellIn = 5, beforeQuality = 50, afterSellIn = 4, afterQuality = 50),
        TestData(name = CHEESE, beforeSellIn = 5, beforeQuality = 10, afterSellIn = 4, afterQuality = 11),
        TestData(name = CHEESE, beforeSellIn = 1, beforeQuality = 10, afterSellIn = 0, afterQuality = 11),
        TestData(name = CHEESE, beforeSellIn = 0, beforeQuality = 10, afterSellIn = -1, afterQuality = 12),
        TestData(name = CHEESE, beforeSellIn = -1, beforeQuality = 10, afterSellIn = -2, afterQuality = 12),

        TestData(name = CONCERT, beforeSellIn = 5, beforeQuality = 0, afterSellIn = 4, afterQuality = 3),
        TestData(name = CONCERT, beforeSellIn = 5, beforeQuality = 50, afterSellIn = 4, afterQuality = 50),
        TestData(name = CONCERT, beforeSellIn = 11, beforeQuality = 23, afterSellIn = 10, afterQuality = 24),
        TestData(name = CONCERT, beforeSellIn = 10, beforeQuality = 23, afterSellIn = 9, afterQuality = 25),
        TestData(name = CONCERT, beforeSellIn = 9, beforeQuality = 23, afterSellIn = 8, afterQuality = 25),
        TestData(name = CONCERT, beforeSellIn = 6, beforeQuality = 23, afterSellIn = 5, afterQuality = 25),
        TestData(name = CONCERT, beforeSellIn = 5, beforeQuality = 23, afterSellIn = 4, afterQuality = 26),
        TestData(name = CONCERT, beforeSellIn = 4, beforeQuality = 23, afterSellIn = 3, afterQuality = 26),

        TestData(name = CONCERT, beforeSellIn = 1, beforeQuality = 23, afterSellIn = 0, afterQuality = 26),
        TestData(name = CONCERT, beforeSellIn = 0, beforeQuality = 23, afterSellIn = -1, afterQuality = 0),

    )
        .map { Arguments.of(it) }
        .asStream()


}

data class TestData(
    val name: String,
    val beforeSellIn: Int,
    val beforeQuality: Int,
    val afterSellIn: Int,
    val afterQuality: Int,
) {
    fun item() = Item(name, beforeSellIn, beforeQuality)
    fun expected() = TestItem(name, afterSellIn, afterQuality)
}

data class TestItem(val name: String, val sellIn: Int, val quality: Int)

fun Item.toTestItem() = TestItem(name, sellIn, quality)

fun nextIntExcept(except: Int): Int {
    while (true) {
        val value = nextInt(-1000, 1000)
        if (value != except) {
            return value
        }
    }
}
