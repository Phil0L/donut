package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;

import java.awt.*;

public class Volume extends Command {

    public Volume(){
        super.name = "volume";
        super.aliases = new String[]{};
        super.category = new Category("Sound");
        super.arguments = "[percentage]";
        super.help = "%volume [percentage] : sets the volume";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Volume");

        try {
            int i = Integer.parseInt(event.getArgs());
            PlayerManager manager = PlayerManager.getInstance();
            EmbedBuilder eb = new EmbedBuilder();
            if (i > 50){
                if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || Main.getDonutServer(event.getGuild()).settings().isUseraccess()) {
                    manager.getGuildMusicManager(event.getGuild()).player.setVolume(i);
                    event.reply(eb.setColor(Color.GREEN).setTitle("Changed the volume to " + i).build());
                }else {
                    event.reply(eb.setColor(Color.RED).setTitle("You have to be an Administrator to change the volume to " + i).build());
                }
            }else{
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(i);
                event.reply(eb.setColor(Color.GREEN).setTitle("Changed the volume to " + i).build());
            }


        } catch (Exception e){
            EmbedBuilder eb = new EmbedBuilder();
            event.reply(eb.setColor(Color.RED).setTitle("Provide a number as the percentage of the volume").build());
        }

    }
}
