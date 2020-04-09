package com.pl.discord.objects.locations;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.items.Gambable;
import com.pl.discord.objects.items.Item;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class Casino extends Location {

    public Casino(){
        super.name = "Casino";
        super.command = "%location casino";
        super.emoji = ":diamonds:";
        super.opens = 18;
        super.closes = 6;
        super.stock = new ArrayList<>();

        super.stock.add(new Gambable(new Item("Casual Donut", ":doughnut:"), Gambable.COMMON));
        super.stock.add(new Gambable(new Item("Crown", ":crown:"), Gambable.EPIC));

    }

    @Override
    public void restock() {

    }


    public static void enter(CommandEvent event){
        EmbedBuilder eb = new EmbedBuilder().setAuthor("Casino").setTitle("Your coins: **" + Main.getDonutUser(event.getGuild(), event.getMember()).getCoins() + "**");

        event.reply(eb.build());

    }

    public static Gambable getRandom(){
        Casino casino = new Casino();
        int pos = (int) (Math.random() * casino.stock.size());
        return (Gambable) casino.stock.get(pos);

    }


}
