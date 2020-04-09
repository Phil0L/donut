package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Patreon extends Command {

    public Patreon(){
        super.name = "patreon";
        super.aliases = new String[]{"pat","patron"};
        super.category = new Category("Utilities");
        super.arguments = "";
        super.help = "%patreon : sends the link to my Patreon page. Feel free to support me";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Patreon");

        event.reply(new EmbedBuilder().setTitle("https://www.patreon.com/philo_software").setColor(Color.ORANGE).build());
    }
}
