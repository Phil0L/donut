package com.pl.discord.objects;

public class Item {
    public int prize;
    public int stack;
    public String name;
    public String command;
    public String emoji;
    boolean stackable;

    public Item(){
        this.prize = 30;
        this.name = "Casual Item";
        this.command = "%buy casual-Item";
        this.emoji = ":doughnut:";
        this.stack = 1;
    }

    public Item(int stack){
        this.prize = 30;
        this.name = "Casual Item";
        this.command = "%buy casual-Item";
        this.emoji = ":doughnut:";
        this.stack = stack;
    }

    public Item(int stack, String name){
        this.prize = 30;
        this.name = name;
        this.command = "%buy " + name.toLowerCase().replace(' ', '-');
        this.emoji = ":doughnut:";
        this.stack = stack;
    }

    public Item(int stack, String name, String emoji){
        this.prize = 30;
        this.name = name;
        this.command = "%buy " + name.toLowerCase().replace(' ', '-');
        this.emoji = emoji;
        this.stack = stack;
    }

}
