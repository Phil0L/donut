package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.net.MalformedURLException;
import java.net.URL;

public class Play extends Command {

    public Play(){
        super.name = "play";
        super.aliases = new String[]{"p"};
        super.category = new Category("Sound");
        super.arguments = "[url]";
        super.help = "play some music (link either from youtube, soundcloud or bandcamp)";
    }

    @Override
    protected void execute(CommandEvent event) {
        TextChannel channel = event.getTextChannel();
        String input;

        if (event.getArgs().isEmpty()) {
            channel.sendMessage("Please provide some arguments").queue();
            return;
        }else {
             if (!isUrl(event.getArgs()))
                 input = "ytsearch:" + event.getArgs();
             else
                 input = event.getArgs().replaceAll(" ", "");
        }



        PlayerManager manager = PlayerManager.getInstance();
        manager.loadAndPlay(event.getTextChannel(), input);

    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

}
