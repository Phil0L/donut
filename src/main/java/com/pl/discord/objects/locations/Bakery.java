package com.pl.discord.objects.locations;

import com.pl.discord.objects.items.Item;

import java.util.ArrayList;
import java.util.Collections;

public class Bakery extends Location {

    public Bakery(){
        super.name = "Bakery";
        super.command = "%location bakery";
        super.emoji = ":doughnut:";
        super.opens = 4;
        super.closes = 22;
        super.restockAt = 5;
        super.stock = new ArrayList<>();
        super.possible = new ArrayList<>();

        super.possible.add(new Item("Croissant", ":croissant:", 40, 25));
        super.possible.add(new Item("Baguette", ":baguette_bread:", 30, 45));
        super.possible.add(new Item("Pretzel", ":pretzel:", 20, 20));
        super.possible.add(new Item("Coffee" , ":coffee:", 15, 10));
        super.possible.add(new Item("Bread", ":bread:", 25, 15));

        super.possible.add(new Item("Casual Donut", 100, 20));
        super.possible.add(new Item("Filled Donut", 70, 30));
        super.possible.add(new Item("Springfield Donut", "<:pink_doughnut:684783508207829150>", 60, 70));
        super.possible.add(new Item("White Choclate Donut", "<:white_doughnut:684793612793675810>", 40, 35));
        super.possible.add(new Item("Pudding Donut", "<:white_doughnut:684793612793675810>", 40, 35));
        super.possible.add(new Item("Blueberry Donut", "<:blue_doughnut:684792623168618514>", 50, 40));
        super.possible.add(new Item("Strawberry Donut", "<:red_doughnut:684792614960365589>", 50, 40));
        super.possible.add(new Item("Raspberry Donut", "<:red_doughnut:684792614960365589>", 50, 40));
        super.possible.add(new Item("Cherry Donut", "<:red_doughnut:684792614960365589>", 50, 40));
        super.possible.add(new Item("Zebra Donut", "<:zebra_doughnut:684836654581809226>", 30, 50));
        super.possible.add(new Item("Mini Donuts", "<:mini_doughnuts:684792606596530311>", 20, 60));
        super.possible.add(new Item("Rainbow Donut", "<:rainbow_doughnut:684792625546526803>", 10, 150));
    }


    @Override
    public void restock(){
        Collections.shuffle(super.possible);
        int itemsToStock = ((int)(Math.random() * 3 + 3) + restockAt) - stock.size();
        for (Item item : super.possible){
            if ((int)(Math.random() * 100) <= item.rarity){
                super.stock.add(new Item((int)(Math.random() * (item.rarity / 10))+1, item));
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
