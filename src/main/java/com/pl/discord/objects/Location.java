package com.pl.discord.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Location {
    public String name;
    public String command;
    public String emoji;
    public long opens;
    public long closes;
    public ArrayList<Item> stock;
    public ArrayList<Item> possible;

    public String getCommand(){
        Calendar cal = Calendar.getInstance();
        int now = cal.get(Calendar.HOUR_OF_DAY);
        if (now > opens && now < closes)
            return this.command;
        if (opens > closes && (now < closes || now > opens))
            return this.command;
        return "[closed] opens: " + opens;
    }

    public boolean isOpen(){
        Calendar cal = Calendar.getInstance();
        int now = cal.get(Calendar.HOUR_OF_DAY);
        if (now > opens && now < closes)
            return true;
        return opens > closes && (now < closes || now > opens);
    }

}
