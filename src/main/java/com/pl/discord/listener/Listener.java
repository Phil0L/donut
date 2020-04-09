package com.pl.discord.listener;

import com.pl.discord.Main;
import com.pl.discord.commands.util.Ping;
import com.pl.discord.commands.voice.music.Spotify;
import com.pl.discord.objects.SpotifyMessage;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import com.pl.discord.commands.voice.sound.Listeners.AudioReceiveListener;
import com.pl.discord.objects.DonutServer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import java.awt.*;
import java.time.YearMonth;
import java.util.Calendar;

public class Listener extends ListenerAdapter {


    @Override
    public void onGenericGuildMessageReaction(@Nonnull GenericGuildMessageReactionEvent event) {
        SpotifyMessage spotifyMessage = null;
        boolean foundSpotify = false;
        for (SpotifyMessage message : Spotify.spotifyMessages) {
            if (message.messageID == event.getMessageIdLong()) {
                spotifyMessage = message;
                foundSpotify = true;
                break;
            }
        }
        if (foundSpotify && !event.getUser().isBot()) {
            spotifyMessage.restartThread();

            if (event.getReaction().getReactionEmote().getAsCodepoints().equals("U+1f3b5")) {
                // play
                AudioManager audio = event.getGuild().getAudioManager();
                if (!audio.isConnected())
                    try {
                        audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
                    } catch (Exception e) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.RED);
                        eb.setTitle("You have to be in a Voicechannel");
                        event.getChannel().sendMessage(eb.build()).queue();
                    }

                spotifyMessage.getMessage().clearReactions().queue();
                PlayerManager manager = PlayerManager.getInstance();
                if (spotifyMessage.tracks == null) {
                    String search = "ytsearch:" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[0].getTrack().getArtists()[0].getName() + "-" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[0].getTrack().getName();
                    manager.loadAndPlaySpotify(event.getChannel(), search);
                    for (int i = 0; i < Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId()).length || i < 25; i++) {
                        search = "ytsearch:" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[i].getTrack().getArtists()[0].getName() + "-" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[i].getTrack().getName();
                        manager.loadAndQueueFirstSpotify(event.getChannel(), search);
                    }
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(30, 215, 96)).setTitle("Queued playlist").build()).queue();
                } else {
                    manager.loadAndPlaySpotify(event.getChannel(), "ytsearch:" + spotifyMessage.tracks[spotifyMessage.getCurrent()].getArtists()[0].getName() + "-" + spotifyMessage.tracks[spotifyMessage.current].getName());
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(30, 215, 96)).setTitle("Queued track").build()).queue();
                }
                Spotify.spotifyMessages.remove(spotifyMessage);

            }
            if (event.getReaction().getReactionEmote().getAsCodepoints().equals("U+27a1")) {
                // next
                if (spotifyMessage.tracks == null)
                    Spotify.showPlaylist(spotifyMessage.getMessage(), spotifyMessage.getPlaylists(), spotifyMessage.getCurrent() + 1);
                else
                    Spotify.showTracks(spotifyMessage.message, spotifyMessage.tracks, spotifyMessage.current + 1);
                spotifyMessage.incCurrent();

            }
            if (event.getReaction().getReactionEmote().getAsCodepoints().equals("U+2b05")) {
                // prev
                if (spotifyMessage.tracks == null)
                    Spotify.showPlaylist(spotifyMessage.getMessage(), spotifyMessage.getPlaylists(), spotifyMessage.getCurrent() - 1);
                else
                    Spotify.showTracks(spotifyMessage.message, spotifyMessage.tracks, spotifyMessage.current - 1);
                spotifyMessage.decCurrent();
            }
            if (event.getReaction().getReactionEmote().getAsCodepoints().equals("U+2795")) {
                // queue
                AudioManager audio = event.getGuild().getAudioManager();
                if (!audio.isConnected())
                    try {
                        audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
                    } catch (Exception e) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.RED);
                        eb.setTitle("You have to be in a Voicechannel");
                        event.getChannel().sendMessage(eb.build()).queue();
                    }

                spotifyMessage.getMessage().clearReactions().queue();
                PlayerManager manager = PlayerManager.getInstance();
                if (spotifyMessage.tracks == null) {
                    for (int i = 0; i < Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId()).length && i < 25; i++) {
                        String search = "ytsearch:" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[i].getTrack().getArtists()[0].getName() + "-" + Spotify.getPlaylistsTracks(spotifyMessage.getPlaylists()[spotifyMessage.getCurrent()].getId())[i].getTrack().getName();
                        manager.loadAndQueueSpotify(event.getChannel(), search);
                    }
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(30, 215, 96)).setTitle("Queued playlist").build()).queue();
                } else {
                    manager.loadAndQueueSpotify(event.getChannel(), "ytsearch:" + spotifyMessage.tracks[spotifyMessage.current].getArtists()[0].getName() + "-" + spotifyMessage.tracks[spotifyMessage.current].getName());
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(30, 215, 96)).setTitle("Queued track").build()).queue();
                }
                Spotify.spotifyMessages.remove(spotifyMessage);

            }
        }
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        // check for ping message
        switch (event.getMessage().getContentRaw()) {
            case "pingmessage1one":
                Ping.getTime(1);
                Ping.sendTime(2, event.getChannel());
                break;
            case "pingmessage2two":
                Ping.getTime(2);
                Ping.sendTime(3, event.getChannel());
                break;
            case "pingmessage3three":
                Ping.getTime(3);
                Ping.sendMessage(event.getChannel());
                break;
            default:
                break;
        }

        //add coins for sending messages
        if (Main.getDonutServer(event.getGuild()) != null) {

            if (event.getMessage().getContentRaw().startsWith("%")) {
                if (event.getMessage().getContentRaw().startsWith("%mine")) {

                } else {
                    //command
                    int value = (int) (Math.random() * 3);
                    if (Main.getDonutUser(event.getGuild(), event.getMember()) != null)
                        Main.getDonutUser(event.getGuild(), event.getMember()).addCoins(value);

                }
            } else {
                //normal
                int value = (int) (Math.random() * 6);
                if (Main.getDonutUser(event.getGuild(), event.getMember()) != null)
                    Main.getDonutUser(event.getGuild(), event.getMember()).addCoins(value);

            }
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            System.out.println(event.getMessage().getContentRaw());
        }
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        if (Main.getDonutServer(event.getGuild()) != null && Main.getDonutServer(event.getGuild()).settings().isAutoJoin() && !event.getGuild().getAudioManager().isConnected()){
            AudioManager audio = event.getGuild().getAudioManager();
            try {
                audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
                if (event.getGuild().getAudioManager().isConnected() && Main.getDonutServer(event.getGuild()).settings().isAutoRec())
                    event.getGuild().getAudioManager().setReceivingHandler(new AudioReceiveListener(1, event.getMember().getVoiceState().getChannel()));
            } catch (Exception e) {

            }
        }

        if (Main.getDonutUser(event.getGuild(), event.getMember()) != null) {
            Calendar cal = Calendar.getInstance();

            String sb = "" + cal.get(Calendar.SECOND) +
                    ":" +
                    cal.get(Calendar.MINUTE) +
                    ":" +
                    cal.get(Calendar.HOUR_OF_DAY) +
                    ":" +
                    cal.get(Calendar.DAY_OF_MONTH) +
                    ":" +
                    (cal.get(Calendar.MONTH) + 1) +
                    ":" +
                    cal.get(Calendar.YEAR);
            Main.getDonutUser(event.getGuild(), event.getMember()).joinedVoice(sb);


        }
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        if (
                (Main.getDonutServer(event.getGuild()) == null
                || (Main.getDonutServer(event.getGuild()) != null && Main.getDonutServer(event.getGuild()).settings().isAutoLeave()))
                && event.getGuild().getAudioManager().isConnected()
                && event.getChannelLeft().getName().equalsIgnoreCase(event.getGuild().getAudioManager().getConnectedChannel().getName())
                && event.getOldValue().getMembers().size() == 0){
            System.out.println(event.getOldValue().getMembers().size());
            AudioManager audio = event.getGuild().getAudioManager();
            try {
                audio.closeAudioConnection();
            } catch (Exception e) {

            }
        }

        if (Main.getDonutUser(event.getGuild(), event.getMember()) != null && !Main.getDonutUser(event.getGuild(), event.getMember()).getStartedVoice().equals("00:00:00:00:00:0000")) {
            Calendar cal = Calendar.getInstance();
            String load = Main.getDonutUser(event.getGuild(), event.getMember()).getStartedVoice();
            String now = cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.DAY_OF_MONTH) + ":" + (cal.get(Calendar.MONTH) + 1) + ":" + cal.get(Calendar.YEAR);

            StringBuilder sb = new StringBuilder("");
            sb.append(event.getMember().getEffectiveName());
            sb.append(" has been **");
            sb.append(calculateDuration(load, now));
            sb.append("** in ").append(event.getChannelLeft().getName()).append(".");

            int coins = (int) (calcCoins(load, now));
            if (coins < 2)
                sb.append(" He mined ").append(coins).append(" coin in this time");
            else
                sb.append(" He mined ").append(coins).append(" coins in this time");

            Main.getDonutUser(event.getGuild(), event.getMember()).joinedVoice("00:00:00:00:00:0000");
            Main.getDonutUser(event.getGuild(), event.getMember()).addCoins(coins);


            Main.getDonutServer(event.getGuild()).settings().getBotchannel(event.getGuild()).sendMessage(sb.toString()).queue();
        }

        if (event.getOldValue().getMembers().size() == 2) {
            PlayerManager manager = PlayerManager.getInstance();
            event.getGuild().getAudioManager().closeAudioConnection();
            manager.getGuildMusicManager(event.getGuild()).player.destroy();
        }

    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        if (Main.getDonutServer(event.getGuild()).settings().isAutoLeave()
                && event.getGuild().getAudioManager().isConnected()
                && event.getChannelLeft().getName().equalsIgnoreCase(event.getGuild().getAudioManager().getConnectedChannel().getName())
                && event.getOldValue().getMembers().size() == 1){
            AudioManager audio = event.getGuild().getAudioManager();
            try {
                audio.closeAudioConnection();
            } catch (Exception e) {

            }
        }

        if (Main.getDonutServer(event.getGuild()).settings().isAutoJoin() && !event.getGuild().getAudioManager().isConnected()){
            AudioManager audio = event.getGuild().getAudioManager();
            try {
                audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
                if (Main.getDonutServer(event.getGuild()).settings().isAutoRec())
                    event.getGuild().getAudioManager().setReceivingHandler(new AudioReceiveListener(1, event.getMember().getVoiceState().getChannel()));
            } catch (Exception e) {

            }
        }

        if (Main.getDonutUser(event.getGuild(), event.getMember()) != null && !Main.getDonutUser(event.getGuild(), event.getMember()).getStartedVoice().equals("00:00:00:00:00:0000")) {
            Calendar cal = Calendar.getInstance();
            String load = Main.getDonutUser(event.getGuild(), event.getMember()).getStartedVoice();
            String now = cal.get(Calendar.SECOND) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.DAY_OF_MONTH) + ":" + (cal.get(Calendar.MONTH) + 1) + ":" + cal.get(Calendar.YEAR);

            StringBuilder sb = new StringBuilder("");
            sb.append(event.getMember().getEffectiveName());
            sb.append(" has been **");
            sb.append(calculateDuration(load, now));
            sb.append("** in ").append(event.getChannelLeft().getName()).append(".");

            int coins = (int) (calcCoins(load, now));
            if (coins < 2)
                sb.append(" He mined ").append(coins).append(" coin in this time");
            else
                sb.append(" He mined ").append(coins).append(" coins in this time");
            Main.getDonutUser(event.getGuild(), event.getMember()).addCoins(coins);

            event.getGuild().getTextChannels().get(1).sendMessage(sb.toString()).queue();

            //restart

            String sb2 = "" + cal.get(Calendar.SECOND) +
                    ":" +
                    cal.get(Calendar.MINUTE) +
                    ":" +
                    cal.get(Calendar.HOUR_OF_DAY) +
                    ":" +
                    cal.get(Calendar.DAY_OF_MONTH) +
                    ":" +
                    (cal.get(Calendar.MONTH) + 1) +
                    ":" +
                    cal.get(Calendar.YEAR);
            Main.getDonutUser(event.getGuild(), event.getMember()).joinedVoice(sb2);


        }

        if (event.getNewValue().getMembers().size() == 1) {
            PlayerManager manager = PlayerManager.getInstance();
            event.getGuild().getAudioManager().closeAudioConnection();
            manager.getGuildMusicManager(event.getGuild()).player.destroy();
        }

    }

    private String calculateDuration(String date1, String date2) {
        long time1 = getDateInMs(date1.split(":"));
        long time2 = getDateInMs(date2.split(":"));

        int delay = (int) (time2 - time1);
        return getTimeAsString(delay);
    }

    private long getDateInMs(String[] date) {
        YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(date[5]), Integer.parseInt(date[4]));
        int daysInMonth = yearMonthObject.lengthOfMonth();

        date[4] = String.valueOf(Long.parseLong(date[4]) + Integer.parseInt(date[5]) * 12);
        date[3] = String.valueOf(Long.parseLong(date[3]) + Integer.parseInt(date[4]) * daysInMonth);
        date[2] = String.valueOf(Long.parseLong(date[2]) + Integer.parseInt(date[3]) * 24);
        date[1] = String.valueOf(Long.parseLong(date[1]) + Integer.parseInt(date[2]) * 60);
        date[0] = String.valueOf(Long.parseLong(date[0]) + Integer.parseInt(date[1]) * 60);
        return Long.parseLong(date[0]);

    }

    private String getTimeAsString(int ms) {

        StringBuilder sb = new StringBuilder();
        if (ms >= 27051840){
            sb.append(ms / 27051840);
            sb.append(" months, ");
            ms = ms%27051840;
        }
        if (ms >= 86400){
            sb.append(ms / 86400);
            sb.append(" days, ");
            ms = ms%86400;
        }
        if (ms >= 3600){
            sb.append(ms / 3600);
            sb.append(" hours, ");
            ms = ms%3600;
        }
        if (ms >= 60){
            sb.append(ms / 60);
            sb.append(" minutes and ");
            ms = ms%60;
        }

        sb.append(ms);
        sb.append(ms);
        sb.append(" seconds");
        return sb.toString();
    }

    private int calcCoins(String date1, String date2){
        long time1 = getDateInMs(date1.split(":"));
        long time2 = getDateInMs(date2.split(":"));

        int delay = (int) (time2 - time1);
        return delay > 3600 ? (delay / 100) : (delay > 60 ? delay / 30 : delay / 15);
    }

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        int serverCount = Main.manager.getGuilds().size();
        Main.dblapi.setStats(serverCount);

        if (Main.getDonutServer(event.getGuild()) == null)
            Main.server.add(new DonutServer(event.getGuild()));

    }

    @Override
    public void onGenericEvent(@Nonnull GenericEvent event) {
        if (event instanceof ReadyEvent)
            Main.loadData();
    }
}
