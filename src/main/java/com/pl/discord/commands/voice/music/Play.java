package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import com.pl.discord.commands.voice.sound.Listeners.AudioReceiveListener;
import com.pl.discord.objects.ServerSettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Play extends Command {

    public Play() {
        super.name = "play";
        super.aliases = new String[]{"p", "playnow"};
        super.category = new Category("Sound");
        super.arguments = "[url]";
        super.help = "" +
                "%play [link] : plays the song/playlist of the link. possible links from youtube, soundcloud or bandcamp\n" +
                "Default player on Spotify: (change by %settings player)\n" +
                "%play playlist [search] : searches for a playlist with spotify. react with eiter the \"arrow left\" or the \"arrow right\" to navigate threw the results, react with \"musical_note\" to play the playlist or react with \"+\" to queue the playlist. aliases: pl, list\n" +
                "%play song [search] : searches for a song with spotify. react with eiter the \"arrow left\" or the \"arrow right\" to navigate threw the results, react with \"musical_note\" to play the song or react with \"+\" to queue the song. aliases: track\n" +
                "%play user [userID] : lists the users playlists on spotify. react with eiter the \"arrow left\" or the \"arrow right\" to navigate threw the playlists, react with \"musical_note\" to play the playlist or react with \"+\" to queue the playlist. aliases: account, member\n" +
                "Default player on Youtube: (change by %settings player)\n" +
                "%play [search] : searches for a song on youtube and plays the first result";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Play");

        TextChannel channel = event.getTextChannel();
        String input = "";

        if (event.getArgs().isEmpty()) {
            channel.sendMessage("Please provide some arguments, type %help play for further information").queue();
        } else {
            AudioManager audio = event.getGuild().getAudioManager();
            if (!audio.isConnected())
                try {
                    audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
                    if (event.getGuild().getAudioManager().isConnected() && Main.getDonutServer(event.getGuild()).settings().isAutoRec())
                        event.getGuild().getAudioManager().setReceivingHandler(new AudioReceiveListener(1, event.getMember().getVoiceState().getChannel()));
                } catch (Exception e) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.RED);
                    eb.setTitle("You have to be in a Voicechannel");
                    event.getTextChannel().sendMessage(eb.build()).queue();
                }

            if (!isUrl(event.getArgs())) {
                if (Main.getDonutServer(event.getGuild()).settings().getDefaultPlayer() == ServerSettings.PLAYER_YOUTUBE) {
                    input = "ytsearch:" + event.getArgs().replaceAll(" ", "");
                    PlayerManager manager = PlayerManager.getInstance();
                    manager.loadAndPlay(event.getTextChannel(), input);
                }else
                    Spotify.searchSpotify(event, event.getArgs());

            } else {
                input = event.getArgs().replaceAll(" ", "");
                PlayerManager manager = PlayerManager.getInstance();
                manager.loadAndPlay(event.getTextChannel(), input);

            }
        }


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
