package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Skip extends Command {

    public Skip(){
        super.name = "skip";
        super.aliases = new String[]{"next"};
        super.category = new Category("Sound");
        super.arguments = "[amount]";
        super.help = "%skip : skips the current song\n" +
                "%skip [x] : skips x songs";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Skip");

        PlayerManager manager = PlayerManager.getInstance();
        if (event.getArgs().isEmpty()) {
            manager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
        }else {
            try {
                if (event.getArgs().equals("all")){
                    manager.getGuildMusicManager(event.getGuild()).player.destroy();
                }else {
                    int i = Integer.parseInt(event.getArgs());
                    for (int j = 0; j < i; j++){
                        manager.getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
                    }
                }
            }catch (Exception e){
                EmbedBuilder eb = new EmbedBuilder();
                event.reply(eb.setColor(Color.RED).setTitle("provide a number of how many songfs you want to skip or say \"all\"").build());
            }
        }
    }
}
