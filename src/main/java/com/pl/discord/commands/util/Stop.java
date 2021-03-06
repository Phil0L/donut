package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.OffsetDateTime;

public class Stop extends Command {

    public Stop(){
        super.name = "stop";
        super.aliases = new String[]{};
        super.category = new Category("Utilities");
        super.arguments = "[action]";
        super.help = "" +
                "%stop rec : stops the bots recording | aliases: %stop r\n" +
                "%stop play : stops whatever the bot is playing | aliases : %stop p, %stop song, %stop sound, %stop music\n" +
                "%stop voice : simply stops everything to do with a voice channel | aliases: %stop v, %stop channel, %stop vc, %stop voicechannel";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Stop");

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
            if (event.getArgs().equals("play") || event.getArgs().equals("p") || event.getArgs().equals("song") || event.getArgs().equals("sound") || event.getArgs().equals("music")){
                PlayerManager manager = PlayerManager.getInstance();
                manager.getGuildMusicManager(event.getGuild()).player.destroy();
            }
            if (event.getArgs().equals("voice") || event.getArgs().equals("v") || event.getArgs().equals("channel") || event.getArgs().equals("vc") || event.getArgs().equals("voicechannel")){
                if (event.getGuild().getAudioManager().getReceivingHandler() != null) {
                    event.getGuild().getAudioManager().setReceivingHandler(null);

                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setAuthor("Donut");
                    embed.setColor(Color.RED);
                    embed.setTitle("Stopped recording your audio");
                    embed.setTimestamp(OffsetDateTime.now());

                    event.reply(embed.build());
                }
                event.getGuild().getAudioManager().closeAudioConnection();
                event.reply(new EmbedBuilder().setColor(Color.ORANGE).setTitle("Disconnected").build());
            }
        }
    }
}
