package com.pl.discord.objects.locations;

import com.pl.discord.objects.items.Item;

import java.util.ArrayList;
import java.util.Collections;

public class GasStation extends Location {

    public GasStation(){
        super.name = "Gas station";
        super.command = "%location gas";
        super.emoji = ":fuelpump:";
        super.opens = 0;
        super.closes = 24;
        super.restockAt = 6;
        super.possible = new ArrayList<>();

        super.possible.add(new Item("Lolli", ":lollipop:", 70, 5));
        super.possible.add(new Item("Bonbon", ":candy:", 60, 5));
        super.possible.add(new Item("Chocolate", ":chocolate_bar:", 20, 10));
        super.possible.add(new Item("Cookie", ":cookie:", 20, 15));
        super.possible.add(new Item("Muffin", ":cupcake:", 20, 25));
        super.possible.add(new Item("Pretzel", ":pretzel:", 20, 20));
        super.possible.add(new Item("Coffee" , ":coffee:", 15, 10));
        super.possible.add(new Item("Pizza", ":pizza:", 25, 40));
        super.possible.add(new Item("Sandwich", ":sandwich:", 40, 25));
        super.possible.add(new Item("Casual Donut", 80, 20));
        super.possible.add(new Item("Ice Cubes", ":ice_cube:", 30, 10));
        super.possible.add(new Item("Beer", ":beer:", 75, 5));
        super.possible.add(new Item("Champagne", ":champagne:", 40, 40));

        super.stock = new ArrayList<>();
    }

    @Override
    public void restock() {
        Collections.shuffle(super.possible);
        int itemsToStock = ((int)(Math.random() * 3 + 3) + restockAt) - stock.size();
        for (Item item : super.possible){
            if ((int)(Math.random() * 100) <= item.rarity){
                super.stock.add(new Item((int)(Math.random() * (item.rarity / 4))+1, item));
                itemsToStock--;
            }
            if (itemsToStock <= 0)
                break;
        }
        super.combineItems(super.stock);
        if (super.stock.size() < super.restockAt)
            restock();
    }
}
