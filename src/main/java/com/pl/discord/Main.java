package com.pl.discord;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.donut.*;
import com.pl.discord.commands.fun.FlipACoin;
import com.pl.discord.commands.fun.Fuck;
import com.pl.discord.commands.fun.Hund;
import com.pl.discord.commands.fun.Snickers;
import com.pl.discord.commands.util.*;
import com.pl.discord.commands.voice.JoinMe;
import com.pl.discord.commands.voice.music.*;
import com.pl.discord.listener.Listener;
import com.pl.discord.commands.voice.Disconnect;
import com.pl.discord.commands.voice.Join;
import com.pl.discord.commands.voice.sound.Clip;
import com.pl.discord.commands.voice.sound.Record;
import com.pl.discord.commands.util.Stop;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.DonutUser;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.discordbots.api.client.DiscordBotListAPI;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static ShardManager manager;
    public static DiscordBotListAPI dblapi;
    public static ArrayList<DonutServer> server;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) throws LoginException {
        new Main();

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
        cmd.setHelpWord("generichelp");
        cmd.setActivity(Activity.of(Activity.ActivityType.DEFAULT, "%status | in dev rn"));

        dblapi = new DiscordBotListAPI.Builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MzY3NTMzMTMyODQwOTYxOCIsImJvdCI6dHJ1ZSwiaWF0IjoxNTg0OTk0Nzc4fQ.ObUnKxVd-uPs5ANIK0TkREHRu9HUUDvepZi9R1AAbpw")
                .botId("653675331328409618")
                .build();

        cmd.addCommand(new Help());
        cmd.addCommand(new Clear());
        cmd.addCommand(new Ping());
        cmd.addCommand(new Stop());
        cmd.addCommand(new Patreon());
        cmd.addCommand(new Status());
        cmd.addCommand(new Settings());

        cmd.addCommand(new Join());
        cmd.addCommand(new Disconnect());
        cmd.addCommand(new Record());
        cmd.addCommand(new Clip());
        cmd.addCommand(new Soundboard());
        cmd.addCommand(new Play());
        cmd.addCommand(new Volume());
        cmd.addCommand(new Pause());
        cmd.addCommand(new Skip());
        cmd.addCommand(new Song());
        cmd.addCommand(new Shuffle());
        cmd.addCommand(new Queue());
        cmd.addCommand(new JoinMe());

        cmd.addCommand(new Enter());
        cmd.addCommand(new Mine());
        cmd.addCommand(new Load());
        cmd.addCommand(new Welcome());
        cmd.addCommand(new Serverinfo());
        cmd.addCommand(new UserInfo());
        cmd.addCommand(new LocationCommand());
        cmd.addCommand(new Gamble());
        cmd.addCommand(new Buy());
        cmd.addCommand(new Item());

        cmd.addCommand(new Hund());
        cmd.addCommand(new FlipACoin());
        cmd.addCommand(new Fuck());
        cmd.addCommand(new Snickers());

        CommandClient client = cmd.build();
        builder.addEventListeners(client);
        builder.addEventListeners(new Listener());
        Main.manager = builder.build();


    }

    public static void loadData() {

        File file = new File("./data");
        if (file.mkdir())
            System.out.println("[data]: made dir 'data'");
        else if (file.exists()){
        }
        else
            System.out.println("[data]: cannot make dir 'data'!");

        Main.server = new ArrayList<>();
        for (Guild guild : Main.manager.getGuilds()) {
            Load.loadByGuild(guild);
        }

    }


    public static DonutServer getDonutServer(Guild guild) {
        for (int i = 0; i < server.size(); i++) {
            if (Main.server.get(i).getId().equalsIgnoreCase(guild.getId())) {
                return Main.server.get(i);
            }
        }
        return null;
    }

    public static DonutUser getDonutUser(Guild guild, Member member) {
        for (int i = 0; i < server.size(); i++) {
            if (Main.server.get(i).getId().equals(guild.getId())) {
                for (int j = 0; j < Main.server.get(i).getUser().size(); j++) {
                    if (Main.server.get(i).getUser().get(j).getName().equals(member.getEffectiveName())) {
                        return Main.server.get(i).getUser().get(j);
                    }
                }
                return null;
            }
        }
        return null;
    }

    public static void printData() {
        for (DonutServer server : Main.server) {
            System.out.println(server.toString());
        }
    }

    public static void log(CommandEvent event, String command){
        System.out.println("[" + event.getGuild().getName() + "]:[" + event.getMember().getEffectiveName() + "]: " + Main.ANSI_YELLOW + "Triggered " + command + Main.ANSI_RESET + ": " + event.getMessage().getContentRaw());
    }


}
