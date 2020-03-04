package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;

import java.util.ArrayList;

public class GasStation extends Location {

    public GasStation(){
        super.name = "Gas station";
        super.command = "%location gas";
        super.emoji = ":fuelpump:";
        super.opens = 0;
        super.closes = 24;
        super.stock = new ArrayList<>();
    }
}
