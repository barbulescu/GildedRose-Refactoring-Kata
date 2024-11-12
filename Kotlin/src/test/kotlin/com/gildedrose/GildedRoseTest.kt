package com.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun `no items`() {
        val items = updateQuality()

        assertThat(items).isEmpty()
    }

    @Test
    fun `one item empty`() {
        val items = updateQuality(Item(name = "foo", sellIn = 0, quality = 0))

        assertThat(items).containsExactlyInAnyOrder(Item(name = "foo", sellIn = -1, quality = 0))

    }

    @Test
    fun `process two items`() {
        val items = updateQuality(
            Item(name = "foo1", sellIn = 0, quality = 0),
            Item(name = "foo2", sellIn = 5, quality = 2)
        )

        assertThat(items).containsExactlyInAnyOrder(
            Item(name = "foo1", sellIn = -1, quality = 0),
            Item(name = "foo2", sellIn = 4, quality = 1)
        )

    }
}

fun updateQuality(vararg items: Item) = updateQuality(items.toList())
