package com.pl.discord.commands.simple;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class FlipACoin extends Command {

    public FlipACoin(){
        super.name = "flipacoin";
        super.aliases = new String[]{"fac","flip","coin"};
        super.category = new Category("SimpleCommand");
        super.arguments = "";
        super.help = "flips a coin";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (Math.random() > 0.5){
            event.reply("**Heads!**");
        }else {
            event.reply("**Tails!**");
        }
    }
}
