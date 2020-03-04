package com.pl.discord;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.pl.discord.commands.dev.Coins;
import com.pl.discord.commands.dev.Donuts;
import com.pl.discord.commands.donut.*;
import com.pl.discord.commands.fun.FlipACoin;
import com.pl.discord.commands.fun.Fuck;
import com.pl.discord.commands.fun.Hund;
import com.pl.discord.commands.fun.Snickers;
import com.pl.discord.commands.simple.*;
import com.pl.discord.commands.voice.music.*;
import com.pl.discord.listener.Listener;
import com.pl.discord.commands.voice.Disconnect;
import com.pl.discord.commands.voice.Join;
import com.pl.discord.commands.voice.sound.Clip;
import com.pl.discord.commands.voice.sound.Record;
import com.pl.discord.commands.simple.Stop;
import com.pl.discord.objects.DonutServer;
import com.wrapper.spotify.SpotifyApi;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static ShardManager manager;
    public static ArrayList<DonutServer> server;


    public static void main(String[] args) throws LoginException {
        new Main();
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
// Tokens:
// Access Token: BQBoex12w6u3H5WNcupbD3KIFkKtyiRLwRj5Gb8iY7rZMr1sYKsH6ll-xZyzx6D9fXHr6w3-34whWxnHr0sLqMgXjihpWlplGEMPhc55VGvadxFlxPKj4o5eQ2l_VOBrSo9XO_G7_GuVxsDDg1ARR0r4jYFeK0Y
// Refresh Token: AQBacLlvwGXFEnOGIrmYwEHxvFU-UdKExkkZsYsu5bcQvLfwB2hbrf3SHDxIsXhrJ3USQ-o0CPhQRZBkHVdYUF8mKdda3MWCWTk-tOEYpdfwYI09vtumTLhlSPyhlZkyZFE
                .setAccessToken("BQBoex12w6u3H5WNcupbD3KIFkKtyiRLwRj5Gb8iY7rZMr1sYKsH6ll-xZyzx6D9fXHr6w3-34whWxnHr0sLqMgXjihpWlplGEMPhc55VGvadxFlxPKj4o5eQ2l_VOBrSo9XO_G7_GuVxsDDg1ARR0r4jYFeK0Y")
                .setRefreshToken("AQBacLlvwGXFEnOGIrmYwEHxvFU-UdKExkkZsYsu5bcQvLfwB2hbrf3SHDxIsXhrJ3USQ-o0CPhQRZBkHVdYUF8mKdda3MWCWTk-tOEYpdfwYI09vtumTLhlSPyhlZkyZFE")
                .build();

    }

    private Main() throws LoginException {
        setup();
        server = new ArrayList<>();

    }

    private void setup() throws LoginException {
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("NjUzNjc1MzMxMzI4NDA5NjE4.Xj_7Ww.8CWeVl0-SOhgEC9B1ankBPbDmr0");
        builder.setActivity(Activity.of(Activity.ActivityType.DEFAULT, "reloading"));
        CommandClientBuilder cmd = new CommandClientBuilder();
        cmd.setPrefix("%");
        cmd.setOwnerId("653675331328409618");
        cmd.setHelpWord("help");
        cmd.setActivity(Activity.of(Activity.ActivityType.DEFAULT, "%help | online"));
        cmd.addCommand(new Hund());
        cmd.addCommand(new Clear());
        cmd.addCommand(new FlipACoin());
        cmd.addCommand(new Fuck());
        cmd.addCommand(new Ping());
        cmd.addCommand(new Snickers());

        cmd.addCommand(new Join());
        cmd.addCommand(new Disconnect());
        cmd.addCommand(new Record());
        cmd.addCommand(new Clip());
        cmd.addCommand(new Play());
        cmd.addCommand(new Volume());
        cmd.addCommand(new Pause());
        cmd.addCommand(new Skip());
        cmd.addCommand(new Song());

        cmd.addCommand(new Stop());

        cmd.addCommand(new Enter());
        cmd.addCommand(new Mine());
        cmd.addCommand(new Load());
        cmd.addCommand(new Welcome());
        cmd.addCommand(new Serverinfo());
        cmd.addCommand(new UserInfo());
        cmd.addCommand(new LocationCommand());

        cmd.addCommand(new Coins());
        cmd.addCommand(new Donuts());

        CommandClient client = cmd.build();
        builder.addEventListeners(client);
        builder.addEventListeners(new Listener());
        Main.manager = builder.build();
    }


    public static int getServer(Guild guild) {
        for (int i = 0; i < server.size(); i++) {
            if (server.get(i).getId().equals(guild.getId())) {
                return i;
            }
        }
        return -1;
    }

    public static void printData(){
        System.out.println(Arrays.toString(Main.server.toArray()));
    }


}
