package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class Serverinfo extends Command {

    public Serverinfo() {
        super.name = "serverinfo";
        super.aliases = new String[]{"si", "server", "sinfo", "serveri"};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "displays the current server stats";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setTitle(event.getGuild().getName());
        String[] members = new String[event.getGuild().getMembers().size()];
        for (int i = 0; i < event.getGuild().getMembers().size(); i++) {
            members[i] = "\n" + event.getGuild().getMembers().get(i).getEffectiveName();
        }
        Arrays.sort(members);
        eb.addField("Server Owner: ", Objects.requireNonNull(event.getGuild().getOwner()).getEffectiveName(), false);
        eb.addField("**Members**", Arrays.toString(members).replace('[',' ').replace(']', ' '), true);
        eb.addField("**Roles**", "", true);
        eb.addBlankField(false);
        eb.addField("Members: ", String.valueOf(event.getGuild().getMembers().size()), true);
        eb.addField("Roles", "", true);
        eb.addField("Boost: ", event.getGuild().getBoostTier().toString(), false);

        event.reply(eb.build());
    }
}
