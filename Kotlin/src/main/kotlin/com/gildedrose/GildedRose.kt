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
const val SULFURAS = "Sulfuras, Hand of Ragnaros"

fun Item.updateQuality() {
    validate()
    when (name) {
        CHEESE -> updateCheeseQuality()
        CONCERT -> updateConcertQuality()
        SULFURAS -> require(quality == 80) { "sulfuras quality must be always 80, but it was $quality" }
        else -> updateGenericQuality()
    }
    validate()
}

private fun Item.updateGenericQuality() {
    quality -= if (sellIn > 0) 1 else 2

    sellIn--

    if (quality < 0) {
        quality = 0
    }
}

fun Item.updateCheeseQuality() {
    sellIn--

    quality += if (sellIn < 0) 2 else 1

    if (quality > 50) {
        quality = 50
    }
}

private fun Item.updateConcertQuality() {
    quality += when {
        sellIn < 6 -> 3
        sellIn < 11 -> 2
        else -> 1
    }

    if (quality > 50) {
        quality = 50
    }

    sellIn--

    if (sellIn < 0) {
        quality = 0
    }
}

private fun Item.validate() {
    if (name != SULFURAS) {
        require(quality in 0..50) { "quality must be in [0..50], but it was $quality" }
    }
}

