package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.voice.music.handler.PlayerManager;

public class Shuffle extends Command {

    public Shuffle(){
        super.name = "shuffle";
        super.aliases = new String[]{};
        super.category = new Category("Sound");
        super.arguments = "";
        super.help = "shuffles the queue";
    }

    @Override
    protected void execute(CommandEvent event) {
        PlayerManager manager = PlayerManager.getInstance();
        manager.getGuildMusicManager(event.getGuild()).scheduler.shuffle();
    }
}
