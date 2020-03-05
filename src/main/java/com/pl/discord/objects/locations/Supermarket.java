package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;

import java.util.ArrayList;

public class Supermarket extends Location {

    public Supermarket(){
        super.name = "Supermarket";
        super.command = "%location supermarket";
        super.emoji = ":apple:";
        super.opens = 6;
        super.closes = 20;
        super.stock = new ArrayList<>();
    }

    @Override
    public void restock() {

    }
}
