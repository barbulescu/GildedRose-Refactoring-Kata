package com.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
    @ValueSource(strings = [CHEESE, CONCERT, SULFURAS, "foo"])
    fun `quality can never be negative`(name: String) {
        val quality = -nextInt(1, 1000)
        val item = Item(name, nextInt(), quality)
        assertThatThrownBy { item.updateQuality() }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("quality must be positive, but it was $quality")
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
        TestData(name = "foo", beforeSellIn = 0, beforeQuality = 0, afterSellIn = -1, afterQuality = 0),
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


