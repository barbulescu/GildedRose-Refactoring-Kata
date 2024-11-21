package com.gildedrose

class GildedRose(var items: List<Item>) {
    fun updateQuality() {
        for (item in items) {
            item.updateQuality()
        }
    }
}

const val CHEESE = "Aged Brie"
const val CONCERT = "Backstage passes to a TAFKAL80ETC concert"
const val CONJURED = "Conjured"
const val SULFURAS = "Sulfuras, Hand of Ragnaros"

fun Item.updateQuality() {
    if (name == SULFURAS) {
        require(quality == 80) { "sulfuras quality must be always 80, but it was $quality" }
        return
    }

    require(quality in 0..50) { "quality must be in [0..50], but it was $quality" }
    when (name) {
        CHEESE -> updateCheeseQuality()
        CONCERT -> updateConcertQuality()
        CONJURED -> updateGenericQuality(multiplier = 2)
        else -> updateGenericQuality(multiplier = 1)
    }

    enforceBounds()
    sellIn--
}

private fun Item.enforceBounds() {
    if (quality < 0) {
        quality = 0
    }
    if (quality > 50) {
        quality = 50
    }
}

private fun Item.updateGenericQuality(multiplier: Int) {
    val degradation = if (sellIn > 0) 1 else 2
    quality -= degradation * multiplier
}

fun Item.updateCheeseQuality() {
    quality += if (sellIn <= 0) 2 else 1
}

private fun Item.updateConcertQuality() {
    quality += when {
        sellIn < 6 -> 3
        sellIn < 11 -> 2
        else -> 1
    }

    if (sellIn <= 0) {
        quality = 0
    }
}

