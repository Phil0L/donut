package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;

import java.util.ArrayList;

public class ClothingStore extends Location {

    public ClothingStore(){
        super.name = "Clothing Store";
        super.command = "%location clothing";
        super.emoji = ":person_standing:";
        super.opens = 8;
        super.closes = 18;
        super.stock = new ArrayList<>();
    }
}
