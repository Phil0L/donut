package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;

public class Status extends Command {

    public Status(){
        super.name = "status";
        super.aliases = new String[]{"botinfo","bi","bot","info","about"};
        super.arguments = "";
        super.category = new Category("Utilities");
        super.help = "%status : displays the current Bot status";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Status");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Donut");
        eb.setTitle("alpha 0.3.16");
        eb.setDescription("This Bot is currently still in development.\n" +
                "it is very likely, that your data regarding this bot may be deleted in case of an update\n" +
                "feel free to use the bots functions, but be aware, i am working highspeed on the bot so not everything is working perfectly rn.\n" +
                "If you want to support me, you can check out my patreon: %patreon.\n" +
                "If you have any suggestions for the bot just add me on Discord: Philo#1880.\n" +
                "Enjoy!");
        eb.addField("Servercount:", String.valueOf(Main.manager.getGuilds().size()), true);
        event.reply(eb.build());

    }
}
