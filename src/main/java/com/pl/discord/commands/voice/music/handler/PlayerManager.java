package com.pl.discord.commands.voice.music.handler;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild) {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndQueueSpotify(TextChannel channel, String trackUrl){
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        musicManager.player.setVolume(10);

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                AudioTrack track = playlist.getTracks().get(0);
                play(musicManager, track);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    public void loadAndPlaySpotify(TextChannel channel, String trackUrl){
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        musicManager.player.setVolume(10);

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.playNow(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                AudioTrack track = playlist.getTracks().get(0);
                musicManager.scheduler.playNow(track);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    public void loadAndQueueFirstSpotify(TextChannel channel, String trackUrl){
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        musicManager.player.setVolume(10);

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queueFront(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                AudioTrack track = playlist.getTracks().get(0);
                musicManager.scheduler.queueFront(track);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

    public void loadAndPlay(TextChannel channel, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
        musicManager.player.setVolume(10);
        

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage(new EmbedBuilder().setTitle(track.getInfo().title + "\nTrack added to queue").setColor(Color.GREEN).setAuthor(track.getInfo().author).build()).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                play(musicManager, playlist.getTracks().get(0));
                channel.sendMessage(new EmbedBuilder().setTitle(playlist.getName() + "\nTrack added to queue").setColor(Color.GREEN).build()).queue();

            }

            @Override
            public void noMatches() {
                channel.sendMessage(new EmbedBuilder().setTitle("Nothing found by " + trackUrl).setColor(Color.RED).build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(new EmbedBuilder().setTitle("Could not play " + trackUrl).setColor(Color.RED).build()).queue();
            }
        });

    }

    public void loadAndPlay(Guild guild, String trackUrl) {
        GuildMusicManager musicManager = getGuildMusicManager(guild);
        musicManager.player.setVolume(10);


        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                play(musicManager, playlist.getTracks().get(0));
            }

            @Override
            public void noMatches() {
            }

            @Override
            public void loadFailed(FriendlyException exception) {
            }
        });

    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}