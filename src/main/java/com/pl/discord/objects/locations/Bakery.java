package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;

import java.util.ArrayList;

public class Bakery extends Location {

    public Bakery(){
        super.name = "Bakery";
        super.command = "%location bakery";
        super.emoji = ":doughnut:";
        super.opens = 4;
        super.closes = 22;
        super.stock = new ArrayList<>();
        super.possible = new ArrayList<>();
        super.possible.add(new Donut(10, "Casual Donut"));
        super.possible.add(new Donut(10, "Springfield Donut", ":pink:doughnut:"));
        super.possible.add(new Donut());
    }
}
