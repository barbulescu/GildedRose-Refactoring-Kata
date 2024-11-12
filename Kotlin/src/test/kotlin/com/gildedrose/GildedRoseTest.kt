package com.gildedrose

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun `no items`() {
        val app = GildedRose(listOf())

        app.updateQuality()

        assertThat(app.items).isEmpty()

    }


    @Test
    fun `one item empty`() {
        val items = listOf(Item(name = "foo", sellIn = 0, quality = 0))
        val app = GildedRose(items)

        app.updateQuality()

        assertThat(app.items).containsExactlyInAnyOrder(Item(name = "foo", sellIn = -1, quality = 0))

    }

}


