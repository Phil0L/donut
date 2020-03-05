package com.pl.discord.objects;

import net.dv8tion.jda.api.entities.Guild;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class Location {
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

    public String getDonutEmote(String emote, Guild guild){
        System.out.println(guild.getEmotesByName("doughnut", true));
        if (guild.getEmotesByName("doughnut", true).size() >= 7){
            return emote;
        }
        return ":doughnut:";
    }

    public abstract void restock();


}
