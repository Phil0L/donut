package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nonnull;
import javax.swing.plaf.IconUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Spotify extends Command {

//     Tokens:
//     Access Token: BQBoex12w6u3H5WNcupbD3KIFkKtyiRLwRj5Gb8iY7rZMr1sYKsH6ll-xZyzx6D9fXHr6w3-34whWxnHr0sLqMgXjihpWlplGEMPhc55VGvadxFlxPKj4o5eQ2l_VOBrSo9XO_G7_GuVxsDDg1ARR0r4jYFeK0Y
//     Refresh Token: AQBacLlvwGXFEnOGIrmYwEHxvFU-UdKExkkZsYsu5bcQvLfwB2hbrf3SHDxIsXhrJ3USQ-o0CPhQRZBkHVdYUF8mKdda3MWCWTk-tOEYpdfwYI09vtumTLhlSPyhlZkyZFE
//     Client Id: fe905aafc8544b3a99e185621deb5c08
//     Client Secret: a15fff057b6545c78e5eb3064e2628ba

    public Spotify() {
        super.name = "spotify";
        super.aliases = new String[]{};
        super.category = new Category("Sound");
        super.arguments = "[playlist]";
        super.help = "looks for a spotify playlist and plays these songs via Youtube";


    }

    @Override
    protected void execute(CommandEvent event) {
        authorizationCodeRefresh_Sync();

        playlists = getPlaylists(event.getArgs());

        if (playlists != null) {
            current = 0;
            message = event.getTextChannel().sendMessage(new EmbedBuilder().setTitle("Waiting for spotify").build()).complete();
            messageID = message.getIdLong();


            showPlaylist(message, playlists, current);
            new Thread(() -> {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }


    }

    public static Message message;
    public static PlaylistSimplified[] playlists;
    public static long messageID;
    public static int current;


    public static void showPlaylist(Message message, PlaylistSimplified[] playlists, int which) {

        message.clearReactions().queue();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(playlists[which].getOwner().getDisplayName());
        eb.setTitle(playlists[which].getName());
        eb.setColor(new Color(30, 215, 96));
        eb.setThumbnail(playlists[which].getImages()[0].getUrl());

        eb.setDescription("**Tracks:**\n" +
                ((getPlaylistsTracks(playlists[which].getId()).length > 0) ? getPlaylistsTracks(playlists[which].getId())[0].getTrack().getArtists()[0].getName() + " - " + getPlaylistsTracks(playlists[which].getId())[0].getTrack().getName() + "\n" : "") +
                ((getPlaylistsTracks(playlists[which].getId()).length > 1) ? getPlaylistsTracks(playlists[which].getId())[1].getTrack().getArtists()[0].getName() + " - " + getPlaylistsTracks(playlists[which].getId())[1].getTrack().getName() + "\n" : "") +
                ((getPlaylistsTracks(playlists[which].getId()).length > 2) ? getPlaylistsTracks(playlists[which].getId())[2].getTrack().getArtists()[0].getName() + " - " + getPlaylistsTracks(playlists[which].getId())[2].getTrack().getName() + "\n" : "") +
                ((getPlaylistsTracks(playlists[which].getId()).length > 3) ? "..." : "")

        );

        message.editMessage(eb.build()).queue();
        if (which != 0)
            message.addReaction("U+2B05").queue(); // left
        message.addReaction("U+1F3B5").queue(); // play
        if (which != playlists.length)
            message.addReaction("U+27A1").queue(); // right
    }

    private PlaylistSimplified[] getPlaylists(String name) {
        try {
            return spotifyApi.searchItem(name, ModelObjectType.PLAYLIST.getType())
                    .build().execute()
                    .getPlaylists().getItems();

        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PlaylistTrack[] getPlaylistsTracks(String id) {
        try {
            return spotifyApi.getPlaylistsTracks(id)
                    .build().execute()
                    .getItems();
        } catch (IOException | SpotifyWebApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final String clientId = "fe905aafc8544b3a99e185621deb5c08";
    private static final String clientSecret = "a15fff057b6545c78e5eb3064e2628ba";
    private static final String refreshToken = "AQBacLlvwGXFEnOGIrmYwEHxvFU-UdKExkkZsYsu5bcQvLfwB2hbrf3SHDxIsXhrJ3USQ-o0CPhQRZBkHVdYUF8mKdda3MWCWTk-tOEYpdfwYI09vtumTLhlSPyhlZkyZFE";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRefreshToken(refreshToken)
            .build();
    private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
            .build();

    public static void authorizationCodeRefresh_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void authorizationCodeRefresh_Async() {
        try {
            final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRefreshRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
}


