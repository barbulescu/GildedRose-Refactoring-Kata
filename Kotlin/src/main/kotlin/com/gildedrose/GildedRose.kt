package com.gildedrose

data class Item(var name: String, var sellIn: Int, var quality: Int)

fun updateQuality(items: List<Item>) : List<Item>{
    items.forEach { item -> item.updateQuality() }
    return items
}

fun Item.updateQuality() {
    if (name != "Aged Brie" && name != "Backstage passes to a TAFKAL80ETC concert") {
        if (quality > 0) {
            if (name != "Sulfuras, Hand of Ragnaros") {
                quality -= 1
            }
        }
    } else {
        if (quality < 50) {
            quality += 1

            if (name == "Backstage passes to a TAFKAL80ETC concert") {
                if (sellIn < 11) {
                    if (quality < 50) {
                        quality += 1
                    }
                }

                if (sellIn < 6) {
                    if (quality < 50) {
                        quality += 1
                    }
                }
            }
        }
    }

    if (name != "Sulfuras, Hand of Ragnaros") {
        sellIn -= 1
    }

    if (sellIn < 0) {
        if (name != "Aged Brie") {
            if (name != "Backstage passes to a TAFKAL80ETC concert") {
                if (quality > 0) {
                    if (name != "Sulfuras, Hand of Ragnaros") {
                        quality -= 1
                    }
                }
            } else {
                quality -= quality
            }
        } else {
            if (quality < 50) {
                quality += 1
            }
        }
    }
}
