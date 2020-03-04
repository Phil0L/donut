package com.pl.discord.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class FlipACoin extends Command {

    public FlipACoin() {
        super.name = "flipacoin";
        super.aliases = new String[]{"fac", "flip", "coin"};
        super.category = new Category("Fun");
        super.arguments = "";
        super.help = "flips a coin";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (Math.random() > 0.5) {
            //heads
            ArrayList<String> sample = new ArrayList<>();
            sample.add("https://media.giphy.com/media/STQ6QKpChMKKk/giphy-downsized.gif");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setImage(sample.get((int) (Math.random() * sample.size())));
            event.reply(eb.build());
        } else {
            //tails
            ArrayList<String> sample = new ArrayList<>();
            sample.add("https://media.giphy.com/media/XyDehxKiXnRkEjrcMC/giphy-downsized.gif");

            EmbedBuilder eb = new EmbedBuilder();
            eb.setImage(sample.get((int) (Math.random() * sample.size())));
            event.reply(eb.build());
        }
    }
}
