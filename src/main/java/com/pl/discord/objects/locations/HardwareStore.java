package com.pl.discord.objects.locations;

import com.pl.discord.objects.Location;
import com.pl.discord.objects.items.Donut;

import java.util.ArrayList;

public class HardwareStore extends Location {

    public HardwareStore(){
        super.name = "Hardware Store";
        super.command = "%location hardware";
        super.emoji = ":tools:";
        super.opens = 6;
        super.closes = 24;
        super.stock = new ArrayList<>();
    }
}
