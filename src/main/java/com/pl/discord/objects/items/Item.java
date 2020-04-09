package com.pl.discord.objects.items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Clothing.class, name = "clothing")
})
public class Item {
    public int prize;
    public int stack;
    public String name;
    public String command;
    public String emoji;
    public boolean stackable;
    public int rarity;

    //default Konstruktor
    public Item(){
    }

    //stack Konstruktor
    public Item(int stack, Item item){
        this.prize = item.prize;
        this.name = item.name;
        this.command = item.command;
        this.emoji = item.emoji;
        this.stackable = item.stackable;
        this.stack = stack;
    }

    //nostack Konstruktor
    public Item(String name, String emoji, int rarity, int prize){
        this.prize = prize;
        this.name = name;
        this.command = "%buy " + name.toLowerCase().replace(' ', '-');
        this.emoji = emoji;
        this.stack = 0;
        this.rarity = rarity;
        this.stackable = true;
    }

    //Donut Konstruktor
    public Item(String name, int rarity, int prize){
        this.prize = prize;
        this.name = name;
        this.command = "%buy " + name.toLowerCase().replace(' ', '-');
        this.emoji = ":doughnut:";
        this.stack = 0;
        this.rarity = rarity;
        this.stackable = true;
    }

    //Default Clothing Konstruktor
    public Item(String name, String emoji){
        this.prize = 0;
        this.name = name;
        this.command = "%buy " + name.toLowerCase().replace(' ', '-');
        this.emoji = emoji;
        this.stack = 0;
        this.rarity = 0;
        this.stackable = false;
    }

    public void removeItem(int stack){
        this.stack = this.stack - stack;
    }

    public int getPrize() {
        return prize;
    }

    public int getStack() {
        return stack;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public String getEmoji() {
        return emoji;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getRarity() {
        return rarity;
    }

    @Override
    public String toString() {
        return "item:[name=" + name +
                ",emoji=" + emoji +
                ",command=" + command +
                ",stack=" + stack +
                ",stackable=" + stackable +
                "]";
    }
}
