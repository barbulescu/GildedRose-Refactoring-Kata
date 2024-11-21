package com.gildedrose

class GildedRose(var items: List<Item>) {
    fun updateQuality() {
        for (item in items) {
            item.updateQuality()
        }
    }
}

const val CONCERT = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS = "Sulfuras, Hand of Ragnaros"
const val CHEESE = "Aged Brie"

fun Item.updateQuality() {
    validate()
    if (name != CHEESE && name != CONCERT) {
        if (quality > 0) {
            if (name != SULFURAS) {
                quality--
            }
        }
    } else {
        if (quality < 50) {
            quality++

            if (name == CONCERT) {
                if (sellIn < 11) {
                    if (quality < 50) {
                        quality++
                    }
                }

                if (sellIn < 6) {
                    if (quality < 50) {
                        quality++
                    }
                }
            }
        }
    }

    if (name != SULFURAS) {
        sellIn--
    }

    if (sellIn < 0) {
        if (name != CHEESE) {
            if (name != CONCERT) {
                if (quality > 0) {
                    if (name != SULFURAS) {
                        quality--
                    }
                }
            } else {
                quality = 0
            }
        } else {
            if (quality < 50) {
                quality++
            }
        }
    }
    validate()
}

private fun Item.validate() {
    if (name == SULFURAS) {
        require(quality == 80) { "sulfuras quality must be always 80, but it was $quality" }
    } else {
        require(quality in 0..50) { "quality must be in [0..50], but it was $quality" }
    }
}

