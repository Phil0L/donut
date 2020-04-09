package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Queue extends Command {

    public Queue(){
        super.name = "queue";
        super.aliases = new String[]{"q", "que"};
        super.category = new Category("Sound");
        super.arguments = "[action]";
        super.help = "" +
                "%queue : shows the current queue\n" +
                "%queue delete : skips the hole queue\n" +
                "%queue shuffle : shuffles the queue";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Queue");

        PlayerManager manager = PlayerManager.getInstance();
        if (event.getArgs().isEmpty()) {

            if (!manager.getGuildMusicManager(event.getGuild()).scheduler.getList().isEmpty()) {
                String desc = "";
                int i = 0;
                for (AudioTrack track : manager.getGuildMusicManager(event.getGuild()).scheduler.getList()) {
                    i++;
                    if (desc.length() > 1900) {
                        desc += "...\n";
                        desc += "#" + manager.getGuildMusicManager(event.getGuild()).scheduler.queue.size() + ": " +
                                manager.getGuildMusicManager(event.getGuild()).scheduler.queue.get(manager.getGuildMusicManager(event.getGuild()).scheduler.queue.size() - 1).getInfo().title;
                        break;
                    }
                    desc += "#" + i + ": " + track.getInfo().title + "\n";
                }

                event.reply(new EmbedBuilder().setTitle("Current Queue: ").setColor(Color.CYAN).setDescription(desc).build());
            } else {
                event.reply(new EmbedBuilder().setTitle("No queue").setColor(Color.RED).build());
            }
        }else if (event.getArgs().equals("delete")){
            manager.getGuildMusicManager(event.getGuild()).player.destroy();
        }else if (event.getArgs().equals("shuffle")){
            manager.getGuildMusicManager(event.getGuild()).scheduler.shuffle();
        }
    }
}
