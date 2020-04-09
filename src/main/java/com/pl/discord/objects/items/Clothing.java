package com.pl.discord.objects.items;

public class Clothing extends Item {
    public int position;
    public boolean twoInOne;

    //default Konstruktor
    public Clothing(){
    }

    //nostack Konstruktor
    public Clothing(String name, String emoji, int rarity, int prize, int position){
        super.prize = prize;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = emoji;
        super.stack = 1;
        super.rarity = rarity;
        super.stackable = false;

        this.position = position;
        this.twoInOne = false;
    }

    //TwoInOne Konstruktor
    public Clothing(String name, String emoji, int rarity, int prize, int position, boolean twoInOne){
        super.prize = prize;
        super.name = name;
        super.command = "%buy " + name.toLowerCase().replace(' ', '-');
        super.emoji = emoji;
        super.stack = 1;
        super.rarity = rarity;
        super.stackable = false;

        this.position = position;
        this.twoInOne = twoInOne;
    }

    public int getPosition() {
        return position;
    }

    public boolean isTwoInOne() {
        return twoInOne;
    }

    @Override
    public String toString() {
        return "item:[name=" + name +
                ",emoji=" + emoji +
                ",command=" + command +
                ",stack=" + stack +
                ",stackable=" + stackable +
                ",twoInOne=" + twoInOne +
                ",position" + position +
                "]";
    }
}
