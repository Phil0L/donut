package com.pl.discord.objects.items;

import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.Item;
import net.dv8tion.jda.api.entities.Guild;

public class Donut extends Item {

    //default Konstruktor
    public Donut(){
        super.prize = 30;
        super.name = "Casual Donut";
        super.command = "%buy casual-donut";
        super.emoji = ":doughnut:";
        super.stack = 1;
        super.stackable = true;
    }

    //stack Konstruktor
    public Donut(int stack, Item item){
        super.prize = item.prize;
        super.name = item.name;
        super.command = item.command;
        super.emoji = item.emoji;
        super.stackable = item.stackable;
        super.stack = stack;
    }

    //nostack Konstruktor
    public Donut(String name, int rarity){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = ":doughnut:";
        super.stack = 0;
        super.rarity = rarity;
    }

    public Donut(String name, String emoji, int rarity){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = emoji;
        super.stack = 0;
        super.rarity = rarity;
    }

}
