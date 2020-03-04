package com.pl.discord.objects.items;

import com.pl.discord.objects.Item;

public class Donut extends Item {

    //default Konstruktor
    public Donut(){
        super.prize = 30;
        super.name = "Casual Donut";
        super.command = "%buy casual-donut";
        super.emoji = ":doughnut:";
        super.stack = 1;
    }

    //stack Konstruktor
    public Donut(int stack){
        super.prize = 30;
        super.name = "Casual Donut";
        super.command = "%buy casual-donut";
        super.emoji = ":doughnut:";
        super.stack = stack;
    }

    public Donut(int stack, String name){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = ":doughnut:";
        super.stack = stack;
    }

    public Donut(int stack, String name, String emoji){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = emoji;
        super.stack = stack;
    }


    //nostack Konstruktor
    public Donut(String name){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = ":doughnut:";
        super.stack = 0;
    }

    public Donut(String name, String emoji){
        super.prize = 30;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = emoji;
        super.stack = 0;
    }
}
