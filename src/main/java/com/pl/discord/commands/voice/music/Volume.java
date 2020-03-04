package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Volume extends Command {

    public Volume(){
        super.name = "volume";
        super.aliases = new String[]{};
        super.category = new Category("Sound");
        super.arguments = "[percentage]";
        super.help = "sets the volume";
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            int i = Integer.parseInt(event.getArgs());
            PlayerManager manager = PlayerManager.getInstance();
            manager.getGuildMusicManager(event.getGuild()).player.setVolume(i);
            EmbedBuilder eb = new EmbedBuilder();
            event.reply(eb.setColor(Color.GREEN).setTitle("Changed the volume to " + i).build());
        } catch (Exception e){
            EmbedBuilder eb = new EmbedBuilder();
            event.reply(eb.setColor(Color.RED).setTitle("Provide a number as the percentage of the volume").build());
        }

    }
}
