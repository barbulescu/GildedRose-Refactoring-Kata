package com.gildedrose

import org.junit.jupiter.api.Test

class ItemTest {

    @Test
    fun `update simple item`() {
        val item = Item(name = "foo", sellIn = 4, quality = 5)

        item.updateQuality()


    }
}
