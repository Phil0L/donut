package com.pl.discord.objects.locations;

import com.pl.discord.Main;
import com.pl.discord.objects.Item;
import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;
import net.dv8tion.jda.api.entities.Activity.Emoji;
import net.dv8tion.jda.api.entities.Emote;

import java.util.ArrayList;
import java.util.Collections;

public class Bakery extends Location {

    public Bakery(){
        super.name = "Bakery";
        super.command = "%location bakery";
        super.emoji = ":doughnut:";
        super.opens = 4;
        super.closes = 22;
        super.stock = new ArrayList<>();
        super.possible = new ArrayList<>();
        super.possible.add(new Donut("Casual Donut", 100));
        super.possible.add(new Donut("Filled Donut", 70));
        super.possible.add(new Donut("Springfield Donut", "" + "pink_doughnut:684783508207829150", 60));
        super.possible.add(new Donut("White Choclate Donut", "white_doughnut:684793612793675810", 40));
        super.possible.add(new Donut("Pudding Donut", "white_doughnut:684793612793675810", 40));
        super.possible.add(new Donut("Blueberry Donut", "blue_doughnut:684792623168618514", 50));
        super.possible.add(new Donut("Strawberry Donut", "red_doughnut:684792614960365589", 50));
        super.possible.add(new Donut("Raspberry Donut", "red_doughnut:684792614960365589", 50));
        super.possible.add(new Donut("Cherry Donut", "red_doughnut:684792614960365589", 50));
        super.possible.add(new Donut("Zebra Donut", "zebra_doughnut:684836654581809226", 30));
        super.possible.add(new Donut("Mini Donuts", "mini_doughnuts:684792606596530311", 20));
        super.possible.add(new Donut("Rainbow Donut", "rainbow_doughnut:684792625546526803", 10));
    }


    @Override
    public void restock(){
        Collections.shuffle(super.possible);
        int itemsToStock = (int)(Math.random() * 3 + 4) - stock.size();
        for (Item item : super.possible){
            if ((int)(Math.random() * 100) <= item.rarity){
                super.stock.add(new Donut((int)(Math.random() * (item.rarity / 10))+1, item));
                itemsToStock--;
            }
            if (itemsToStock <= 0)
                break;
        }
        if (super.stock.size() <= 6)
            restock();
    }
}
