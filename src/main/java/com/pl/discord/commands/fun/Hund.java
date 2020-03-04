package com.pl.discord.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class Hund extends Command {

    public Hund() {
        super.name = "duhund";
        super.aliases = new String[]{"hund","dh"};
        super.category = new Category("Fun");
        super.arguments = "[name]";
        super.help = "H.U.N.D";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getTextChannel().deleteMessageById(event.getTextChannel().getLatestMessageId()).queue();

        if (event.getArgs().isEmpty()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.ORANGE);
            eb.setTitle("Du Hund");
            event.reply(eb.build());
        }else {
            Member member = event.getMessage().getMentionedMembers().get(0);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.ORANGE);
            eb.setTitle("@" + member.getUser().getName() + "  Du Hund");
            event.reply(eb.build());
        }
    }
}
