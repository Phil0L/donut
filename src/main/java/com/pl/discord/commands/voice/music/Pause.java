package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Pause extends Command {

    public Pause() {
        super.name = "pause";
        super.aliases = new String[]{"resume"};
        super.category = new Category("Sound");
        super.arguments = "/ %resume";
        super.help = "pauses/resumes the song";
    }

    @Override
    protected void execute(CommandEvent event) {
        PlayerManager manager = PlayerManager.getInstance();
        if (event.getMessage().getContentRaw().equals("%pause")) {
            if (manager.getGuildMusicManager(event.getGuild()).player.isPaused()) {
                event.reply(new EmbedBuilder().setTitle("Currently paused. Use %resume to resume or %stop play to stop").setColor(Color.RED).build());
            } else {
                manager.getGuildMusicManager(event.getGuild()).player.setPaused(true);
            }
        } else if (event.getMessage().getContentRaw().equals("%resume")) {
            if (manager.getGuildMusicManager(event.getGuild()).player.isPaused()) {
                manager.getGuildMusicManager(event.getGuild()).player.setPaused(false);
            } else {
                event.reply(new EmbedBuilder().setTitle("Currently playing. Use %pause to pause or %stop play to stop").setColor(Color.RED).build());
            }
        }
    }
}
