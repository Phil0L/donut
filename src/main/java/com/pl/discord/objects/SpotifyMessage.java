package com.pl.discord.objects;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class SpotifyMessage {
    public Message message;
    public PlaylistSimplified[] playlists;
    public Track[] tracks;
    public long messageID;
    public int current;
    public Guild guild;
    public Thread waiter;

    public SpotifyMessage(Message message, PlaylistSimplified[] playlists, int current, Guild guild) {
        this.message = message;
        this.playlists = playlists;
        this.messageID = message.getIdLong();
        this.current = current;
        this.guild = guild;
        this.startThread();
    }

    public SpotifyMessage(Message message, Track[] tracks, int current, Guild guild) {
        this.message = message;
        this.tracks = tracks;
        this.messageID = message.getIdLong();
        this.current = current;
        this.guild = guild;
        this.startThread();
    }

    public SpotifyMessage() {

    }

    public void incCurrent(){
        this.current++;
    }

    public void decCurrent(){
        this.current--;
    }

    public Message getMessage() {
        return message;
    }

    public PlaylistSimplified[] getPlaylists() {
        return playlists;
    }

    public long getMessageID() {
        return messageID;
    }

    public int getCurrent() {
        return current;
    }

    public Guild getGuild() {
        return guild;
    }

    public void startThread(){
        this.waiter = new Thread(() -> {
            try {
                Thread.sleep(20000);
                this.message.clearReactions().queue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void restartThread(){
        this.waiter.interrupt();
        this.startThread();
    }
}
