package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;

import java.util.ArrayList;

public class Casino extends Location {

    public Casino(){
        super.name = "Casino";
        super.command = "%location casino";
        super.emoji = ":diamonds:";
        super.opens = 18;
        super.closes = 6;
        super.stock = new ArrayList<>();

    }

    @Override
    public void restock() {

    }
}
