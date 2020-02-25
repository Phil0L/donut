package com.pl.discord.commands.voice;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.OffsetDateTime;

public class Disconnect extends Command {

    public Disconnect(){
        super.name = "disconnect";
        super.aliases = new String[]{"dc","fuckoff","leave"};
        super.category = new Category("Sound");
        super.arguments = "";
        super.help = "disconnects the Bot";
    }

    @Override
    protected void execute(CommandEvent event) {
        stopRec(event);
        event.getGuild().getAudioManager().closeAudioConnection();
        event.reply("**Disconnected!**");
    }

    private void stopRec(CommandEvent event){
        if (event.getGuild().getAudioManager().getReceivingHandler() != null) {
            event.getGuild().getAudioManager().setReceivingHandler(null);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Donut");
            embed.setColor(Color.RED);
            embed.setTitle("Stopped recording your audio");
            embed.setTimestamp(OffsetDateTime.now());

            event.reply(embed.build());
        }
    }
}