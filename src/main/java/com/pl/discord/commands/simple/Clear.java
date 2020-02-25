package com.pl.discord.commands.simple;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Clear extends Command {

    public Clear() {
        super.name = "clear";
        super.aliases = new String[]{"c","delete","incognito","burn","destroy"};
        super.category = new Category("SimpleCommand");
        super.arguments = "";
        super.help = "clears this Textchannel";
    }

    @Override
    protected void execute(CommandEvent event) {
            event.getTextChannel().deleteMessageById(event.getTextChannel().getLatestMessageId()).queue(
                    success -> {execute(event);},
                    error -> {});

    }

}
