package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.DonutServer;
import com.pl.discord.DonutUser;
import com.pl.discord.Main;
import net.dv8tion.jda.api.entities.Member;

public class Enter extends Command {

    public Enter(){
        super.name = "sign";
        super.aliases = new String[]{"enter","signup"};
        super.category = new Category("Donut");
        super.arguments = "[user]";
        super.help = "Enter the world of Donut";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (Main.getServer(event.getGuild()) == -1){
           Main.server.add(new DonutServer(event.getGuild()));
        }
        int i = Main.getServer(event.getGuild());
        if (event.getArgs().isEmpty()){
            if (Main.server.get(i).getMember(event.getMember()) == -1) {
                Main.server.get(i).add(new DonutUser(event.getMember()));
                Main.server.get(i).save(event.getGuild());
                event.reply("Entered");
            } else
                event.reply("You have already entered");


        } else if (event.getArgs().split(" ").length > 1 && event.getArgs().split(" ")[1].equals("force")) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            Main.server.get(i).add(new DonutUser(member));
            Main.server.get(i).save(event.getGuild());
            event.reply("Entered");

        } else if (event.getArgs().equals("force")){
            Main.server.get(i).add(new DonutUser(event.getMember()));
            Main.server.get(i).save(event.getGuild());
            event.reply("Entered");

        } else {
            Member member = event.getMessage().getMentionedMembers().get(0);
            if (Main.server.get(i).getMember(member) == -1) {
                Main.server.get(i).add(new DonutUser(member));
                Main.server.get(i).save(event.getGuild());
                event.reply("Entered");
            } else
                event.reply("This member already entered");

        }
    }
}
