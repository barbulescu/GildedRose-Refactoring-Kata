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
    require(quality in 0..50) { "quality must be in [0..50], but it was $quality" }
    if (name != CHEESE && name != CONCERT) {
        if (quality > 0) {
            if (name != SULFURAS) {
                quality = quality - 1
            }
        }
    } else {
        if (quality < 50) {
            quality = quality + 1

            if (name == CONCERT) {
                if (sellIn < 11) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }

                if (sellIn < 6) {
                    if (quality < 50) {
                        quality = quality + 1
                    }
                }
            }
        }
    }

    if (name != SULFURAS) {
        sellIn = sellIn - 1
    }

    if (sellIn < 0) {
        if (name != CHEESE) {
            if (name != CONCERT) {
                if (quality > 0) {
                    if (name != SULFURAS) {
                        quality = quality - 1
                    }
                }
            } else {
                quality = quality - quality
            }
        } else {
            if (quality < 50) {
                quality = quality + 1
            }
        }
    }
    require(quality in 0..50) { "quality must be in [0..50], but it was $quality" }
}

