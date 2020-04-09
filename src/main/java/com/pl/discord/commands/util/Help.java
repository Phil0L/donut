package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Help extends Command {
    ArrayList<Command> commands;


    public Help() {
        super.name = "help";
        super.aliases = new String[]{"comm", "command"};
        super.category = new Category("Utilities");
        super.arguments = "[command]";
        super.help = "" +
                "%help : shows a listof all the commands\n" +
                "%generichelp : shows the built-in help function (not recommended)\n" +
                "%help [command] : shows the info about a command (like this one here)";
        this.commands = new ArrayList<>();

    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Help");

        if (event.getArgs().isEmpty()) {
            Comparator<Command> compareByCategory = Comparator.comparing((Command o) -> o.getCategory().getName());
            this.commands = new ArrayList<>();
            commands.addAll(event.getClient().getCommands());
            commands.sort(compareByCategory);

            EmbedBuilder eb = new EmbedBuilder().setTitle("**Commands:**").setAuthor("Donut by Philo#1880");
            Command prevCommand = null;
            String current = "```%";
            for (Command command : commands) {
                if (prevCommand != null && !prevCommand.getCategory().getName().equals(command.getCategory().getName())) {
                    eb.addField(prevCommand.getCategory().getName(), current.substring(0, current.length() - 3) + "```", true);
                    current = "```%";
                }
                current += command.getName() + ",\n%";

                prevCommand = command;
            }
            eb.addField(prevCommand.getCategory().getName(), current.substring(0, current.length() - 3) + "```", true);
            eb.addField("\"**%help [command]**\" for more information about a command", "", false);
            eb.setTimestamp(OffsetDateTime.now());
            event.reply(eb.build());
        }else{
            for (Command command : this.commands){
                if (command.getName().equals(event.getArgs())){
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("%" + command.getName() + " " + command.getArguments());
                    eb.setDescription("**aliases:\n" + Arrays.toString(command.getAliases()) + "**\n\nusages:\n" + command.getHelp());
                    eb.setTimestamp(OffsetDateTime.now());
                    event.reply(eb.build());

                    break;
                }
                for (String alias : command.getAliases()){
                    if (alias.equals(event.getArgs())){
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("%" + command.getName() + " " + command.getArguments());
                        eb.setDescription("**aliases:\n" + Arrays.toString(command.getAliases()) + "**\n\nusages:\n" + command.getHelp());
                        eb.setTimestamp(OffsetDateTime.now());
                        event.reply(eb.build());
                    }
                }
            }
        }
    }
}


