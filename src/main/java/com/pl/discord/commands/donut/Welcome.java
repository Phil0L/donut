package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.util.ArrayList;

public class Welcome extends Command {
    public ArrayList<String> messages;


    public Welcome() {
        super.name = "welcome";
        super.aliases = new String[]{"hello", "hi"};
        super.category = new Category("Donut");
        super.arguments = "[user]";
        super.help = "sends a random welcome message to the mentioned user";
    }


    @Override
    protected void execute(CommandEvent event) {
        sendMessage(event);
    }

    private String setupMessages(String name) {
        messages.add("Moin...Servus...Moin " + name + "!");
        messages.add("Hoffentlich hast du Donuts mitgebracht " + name);


        return messages.get((int) (Math.random() * messages.size()));
    }

    private String setupMessages() {
        messages.add("Moin...Servus...Moin");
        messages.add("Hi ich hab Donuts mitgebracht");


        return messages.get((int) (Math.random() * messages.size()));
    }

    void sendMessage(CommandEvent event){
        event.getTextChannel().deleteMessageById(event.getTextChannel().getLatestMessageId()).queue();
        messages = new ArrayList<>();

        if (event.getArgs().isEmpty()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN);
            eb.setTitle(setupMessages());
            event.reply(eb.build());
        } else {
            Member member = event.getMessage().getMentionedMembers().get(0);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN);
            eb.setTitle(setupMessages(member.getUser().getName()));
            event.reply(eb.build());
        }
    }


}
