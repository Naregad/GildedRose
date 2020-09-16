package com.gildedrose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    ArrayList<Integer> itemQualities;
    ArrayList<Integer> itemSellin;
    final static int QUALITY_MIN = 0;
    final static int QUALITY_MAX = 50;
    final static int DAYS = 10;
    final static int QUALITY = 10;
    final static String SULFURAS_ITEM = "Sulfuras, Hand of Ragnaros";
    final static String AGEDBRIE_ITEM = "Aged Brie";
    final static String CONJURED_ITEM = "Conjured Mana Cake";
    final static String BACKSTAGEPASS_ITEM = "Backstage passes to a TAFKAL80ETC concert";

    @BeforeEach
    void beforeEach() {
        itemQualities = new ArrayList<Integer>();
        itemSellin = new ArrayList<Integer>();
    }

    @Test
    void qualityCannotBeNegative() {
        Item[] items = new Item[]{
                new Item("foo", DAYS, QUALITY - QUALITY * 2)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        assertThrows(IllegalStateException.class, () -> app.updateQuality());
    }

    @Test
    void qualityCannotBeGreaterThanFifty() {
        Item[] items = new Item[]{
                new Item("foo", DAYS, QUALITY*6)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        assertThrows(IllegalStateException.class, () -> app.updateQuality());
    }

    @Test
    void shouldDecreaseQualityForNormalItems() {
        Item[] items = new Item[]{
                new Item("foo", DAYS, QUALITY),
                new Item("Cheese Balls", DAYS + 5, QUALITY + 5),
                new Item("Fireworks", DAYS + 5, QUALITY - QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void shouldDecreaseQualityForNormalItemsDegradesFaster() {
        Item[] items = new Item[]{
                new Item("foo", DAYS - 10, QUALITY - 10),
                new Item("Cheese Balls", DAYS - 5, QUALITY - 5),
                new Item("Fireworks", DAYS - 5, QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void sulfurasDoesNotChangeQuality() {

        Item[] items = new Item[]{
                new Item(SULFURAS_ITEM, DAYS - 10, QUALITY - 10),
                new Item(SULFURAS_ITEM, DAYS - 5, QUALITY - 5),
                new Item(SULFURAS_ITEM, DAYS - 5, QUALITY + 70)
        };
        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void agedBrieIncreaseQualityNormal() {
        Item[] items = new Item[]{
                new Item(AGEDBRIE_ITEM, DAYS, QUALITY),
                new Item(AGEDBRIE_ITEM, DAYS + 5, QUALITY),
                new Item(AGEDBRIE_ITEM, DAYS + 10, QUALITY + 30)
        };
        buildHelperLists(items);
        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void agedBrieIncreaseQualityDoubled() {
        Item[] items = new Item[]{
                new Item(AGEDBRIE_ITEM, DAYS - 10, QUALITY),
                new Item(AGEDBRIE_ITEM, DAYS - 5, QUALITY),
                new Item(AGEDBRIE_ITEM, DAYS - 5, QUALITY + 30)
        };
        buildHelperLists(items);
        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void agedBrieMaxOfFifityQuality() {

        Item[] items = new Item[]{
                new Item(AGEDBRIE_ITEM, DAYS - 10, QUALITY + 35),
                new Item(AGEDBRIE_ITEM, DAYS - 5, QUALITY + 40),
                new Item(AGEDBRIE_ITEM, DAYS - 5, QUALITY)
        };
        buildHelperLists(items);
        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);
    }

    @Test
    void shouldDecreaseForConjuredItems() {
        Item[] items = new Item[]{
                new Item(CONJURED_ITEM, DAYS, QUALITY),
                new Item(CONJURED_ITEM, DAYS + 5, QUALITY + 5)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);

    }

    @Test
    void shouldDecreaseForConjuredItemsDegradesFaster() {
        Item[] items = new Item[]{
                new Item(CONJURED_ITEM, DAYS - 10, QUALITY - 10),
                new Item(CONJURED_ITEM, DAYS - 5, QUALITY - 5),
                new Item(CONJURED_ITEM, DAYS - 5, QUALITY),
                new Item(CONJURED_ITEM, DAYS - 5, QUALITY + QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);

    }

    @Test
    void shouldIncreaseQualityForBackStagePasses() {
        Item[] items = new Item[]{
                new Item(BACKSTAGEPASS_ITEM, DAYS + 10, QUALITY * 2 - 1),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 10, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 20, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 10, QUALITY - QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);

    }

    @Test
    void shouldIncreaseQualityDoubleForBackStagePassesLessThanElevenDays() {
        Item[] items = new Item[]{
                new Item(BACKSTAGEPASS_ITEM, DAYS + 7, QUALITY * 2 - 1),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 11, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 10, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 7, QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);

    }

    @Test
    void shouldIncreaseQualityDoubleForBackStagePassesLessThanElevenDaysIterateFiveDays() {
        Item[] items = new Item[]{
                new Item(BACKSTAGEPASS_ITEM, DAYS + 1, QUALITY * 2 - 1),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 1, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - DAYS, QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS - 5);

    }

    @Test
    void shouldIncreaseQualityDoubleForBackStagePassesLessThanFiveDays() {
        Item[] items = new Item[]{
                new Item(BACKSTAGEPASS_ITEM, DAYS + 11, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 10, QUALITY + 10),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 3, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS + 1, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 1, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 4, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 5, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 3, QUALITY)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS);

    }

    @Test
    void shouldIncreaseQualityDoubleForBackStagePassesLessThanFiveDaysIterateFiveDays() {
        Item[] items = new Item[]{
                new Item(BACKSTAGEPASS_ITEM, DAYS - 4, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 5, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 3, QUALITY),
                new Item(BACKSTAGEPASS_ITEM, DAYS - 3, QUALITY * 2 - 1)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS - 5);

    }

    @Test
    void shouldNotBreakProvidedTestBase() {
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Conjured Mana Cake", 3, 6)
        };

        buildHelperLists(items);

        GildedRose app = new GildedRose(items);

        updateValidateQualityAndSellin(items, app, DAYS + 21);

    }

    private void buildHelperLists(Item[] items) {
        for (Item i : items) {
            itemQualities.add(i.quality);
            itemSellin.add(i.sellIn);
        }
    }

    private void updateValidateQualityAndSellin(Item[] items, GildedRose app, int size) {
        for (int x = 0; x < size; x++) {
            int sellin = 0;
            int quality = 0;
            String itemName = "";

            app.updateQuality();

            for (int i = 0; i < items.length; i++) {

                Item it = items[i];

                sellin = itemSellin.get(i);
                quality = itemQualities.get(i);

                itemName = it.name;

                if (itemName != null && itemName.equalsIgnoreCase(AGEDBRIE_ITEM)) {
                    itemQualities.set(i, Math.min(QUALITY_MAX, sellin - 1 < QUALITY_MIN ? quality + 2 : quality + 1));
                    itemSellin.set(i, sellin - 1);
                } else if (itemName != null && itemName.equalsIgnoreCase(CONJURED_ITEM)) {
                    itemQualities.set(i, Math.max(QUALITY_MIN, sellin - 1 < QUALITY_MIN ? quality - 4 : quality - 2));
                    itemSellin.set(i, sellin - 1);
                } else if (itemName != null && itemName.equalsIgnoreCase(BACKSTAGEPASS_ITEM)) {
                    itemSellin.set(i, sellin - 1);
                    if (sellin > 0) {
                        itemQualities.set(i, Math.min(QUALITY_MAX, ++quality));
                        if (sellin < 11)
                            itemQualities.set(i, Math.min(QUALITY_MAX, ++quality));
                        if (sellin < 6)
                            itemQualities.set(i, Math.min(QUALITY_MAX, ++quality));
                    } else if (sellin == QUALITY_MIN) {
                        itemQualities.set(i, QUALITY_MIN);
                    }

                } else if (itemName != null && !itemName.equalsIgnoreCase(SULFURAS_ITEM)) {
                    itemQualities.set(i, Math.max(QUALITY_MIN, sellin - 1 < QUALITY_MIN ? quality - 2 : quality - 1));
                    itemSellin.set(i, sellin - 1);
                }


                assertEquals(itemQualities.get(i), it.quality, "Quality Check: " + it.toString());
                assertEquals(itemSellin.get(i), it.sellIn, "SellIn Check: " + it.toString());
                assertTrue(it.quality >= QUALITY_MIN, "Quality Should be positive: " + it.toString());
                //System.out.println(it.toString());
                if (itemName != null && !itemName.equalsIgnoreCase(SULFURAS_ITEM)) {
                    assertTrue(it.quality <= QUALITY_MAX, "Quality Max is "+QUALITY_MAX+": " + it.toString());
                }
            }
        }
    }


}
