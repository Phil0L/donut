package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Pause extends Command {

    public Pause() {
        super.name = "pause";
        super.aliases = new String[]{"resume"};
        super.category = new Category("Sound");
        super.arguments = "";
        super.help = "%pause : pauses the player\n" +
                "%resume : resumes the bot";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Pause");

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
