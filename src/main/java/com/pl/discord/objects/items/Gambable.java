package com.pl.discord.objects.items;

public class Gambable extends Item{
    public static int COMMON = 0;
    public static int RARE = 1;
    public static int EPIC = 2;
    public static int LEGENDARY = 3;

    private static int TYPE_ITEM = 0;
    private static int TYPE_CLOTHING = 1;

    private int rarity;
    private int type;
    private Item item;
    private Clothing clothing;


    //default Konstruktor
    public Gambable(){
    }

    //item Konstruktor
    public Gambable(Item item, int rarity){
        this.item = item;
        this.rarity = rarity;
        this.type = TYPE_ITEM;
    }

    //clothing Konstruktor
    public Gambable(Clothing clothing, int rarity){
        this.clothing = clothing;
        this. rarity = rarity;
        this.type = TYPE_CLOTHING;
    }

    public Item getItem(){
        if (type == TYPE_ITEM)
            return item;
        if (type == TYPE_CLOTHING)
            return clothing;
        return item;
    }

    public int getRarity(){
        return rarity;
    }

}
