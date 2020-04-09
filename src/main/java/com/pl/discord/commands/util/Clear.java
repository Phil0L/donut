package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;

public class Clear extends Command {

    public Clear() {
        super.name = "clear";
        super.aliases = new String[]{"c","delete","incognito","burn","destroy"};
        super.category = new Category("Utilities");
        super.arguments = "";
        super.help = "[not working rn]";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Clear");

    }

}
