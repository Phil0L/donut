package com.pl.discord.commands.dev;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

public class Coins extends Command {

    public Coins(){
        super.name = "coins";
        super.aliases = new String[]{};
        super.category = new Category("DevCommands");
        super.arguments = "[user] [action] [amount]";
        super.help = "modifies coins";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
            String[] args = event.getArgs().split(" ");
            Member user = event.getMessage().getMentionedMembers().get(0);
            int amount = Integer.parseInt(args[2]);
            switch (args[1]) {
                case "add":
                    Main.server.get(Main.getServer(event.getGuild())).getUser().get(Main.server.get(Main.getServer(event.getGuild())).getMember(user)).addCoins(amount);
                    break;
                case "remove":
                    Main.server.get(Main.getServer(event.getGuild())).getUser().get(Main.server.get(Main.getServer(event.getGuild())).getMember(user)).removeCoins(amount);
                    break;
            }
        } else {
            event.reply("You are not allowed to use this command");
        }
    }
}
