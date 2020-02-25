package com.pl.discord.commands.voice.sound;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.OffsetDateTime;

public class Stop extends Command {

    public Stop(){
        super.name = "stop";
        super.aliases = new String[]{"pause"};
        super.category = new Category("Utilities");
        super.arguments = "[type]";
        super.help = "stops an action (actions: rec )";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty())
            event.reply("You have to provide the type of action you want to stop");
        else {
            if (event.getArgs().equals("rec") || event.getArgs().equals("r")){
                if (event.getGuild().getAudioManager().getReceivingHandler() != null) {

                    event.getGuild().getAudioManager().setReceivingHandler(null);

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setColor(Color.RED);
                    embed.setTitle("Stopped recording your audio");
                    embed.setTimestamp(OffsetDateTime.now());

                    event.reply(embed.build());
                }else {
                    event.reply("Nothing is recording");
                }
            }
        }
    }
}
