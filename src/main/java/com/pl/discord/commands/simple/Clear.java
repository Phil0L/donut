package com.pl.discord.commands.simple;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Clear extends Command {

    public Clear() {
        super.name = "clear";
        super.aliases = new String[]{"c","delete","incognito","burn","destroy"};
        super.category = new Category("Utilities");
        super.arguments = "";
        super.help = ":x: clears this Textchannel";
    }

    @Override
    protected void execute(CommandEvent event) {

    }

}
