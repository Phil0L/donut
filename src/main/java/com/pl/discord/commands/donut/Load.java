package com.pl.discord.commands.donut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.objects.ServerSettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Load extends Command {

    public Load() {
        super.name = "load";
        super.aliases = new String[]{};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "%load : loads the data of the guild\n" +
                "%load backup : loads the last backup created (not working yet)";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Load");

        load(event);
    }

    private void load(CommandEvent event) {
        //deleting current data
        Main.server.remove(getDonutServerByID(event.getGuild()));
        Main.server.add(new DonutServer(event.getGuild()));
        Main.getDonutServer(event.getGuild()).resetUser();

        loadUser(event.getGuild(), event);
        loadSettings(event.getGuild(), event);

        Main.getDonutServer(event.getGuild()).save();
    }

    public static boolean loadByGuild(Guild guild) {

        //deleting current data
        Main.server.add(new DonutServer(guild));
        Main.getDonutServer(guild).resetUser();

        loadUser(guild);
        loadSettings(guild);

        Main.getDonutServer(guild).save();
        return true;
    }


    private static void loadUser(Guild guild, CommandEvent event) {

        File file = new File("./data/" + guild.getId() + "/user");
        if (file.exists())
            for (File subFile : file.listFiles()) {
                try {
                    FileReader reader = new FileReader(subFile);
                    Object obj = new JSONParser().parse(reader);
                    JSONObject jo = (JSONObject) obj;
                    ObjectMapper m = new ObjectMapper();

                    DonutUser user = m.readValue(jo.toString(), DonutUser.class);
                    Main.getDonutServer(guild).add(user);

                    System.out.println("[" + guild.getName() + "]: " + Main.ANSI_BLUE + "Loaded user " + user.getName() + Main.ANSI_RESET);

                } catch (IOException | ParseException e) {
                    Main.getDonutServer(event.getGuild()).save();
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Failed loading data of " + event.getGuild().getName());
                    eb.setColor(Color.RED);
                    event.reply(eb.build());

                    System.out.println("[" + guild.getName() + "]: " + Main.ANSI_RED + "Failed loading data" + Main.ANSI_RESET);
                }
            }
        Main.getDonutServer(event.getGuild()).save();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Loaded data of " + event.getGuild().getName());
        eb.setColor(Color.BLUE);
        event.reply(eb.build());
    }

    private static void loadSettings(Guild guild, CommandEvent event) {
        try {
            FileReader reader = new FileReader(
                    "./data/" + guild.getId() + "/settings.json");

            Object obj = new JSONParser().parse(reader);
            JSONObject jo = (JSONObject) obj;

            ObjectMapper m = new ObjectMapper();
            ServerSettings settings = m.readValue(jo.toString(), ServerSettings.class);

            Main.getDonutServer(guild).setSettings(settings);

            Main.getDonutServer(event.getGuild()).save();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Loaded settings of " + event.getGuild().getName());
            eb.setColor(Color.BLUE);
            event.reply(eb.build());

            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_BLUE + "Loaded settings" + Main.ANSI_RESET);

        } catch (IOException | ParseException e) {
            Main.getDonutServer(event.getGuild()).save();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Failed loading settings of " + event.getGuild().getName());
            eb.setColor(Color.RED);
            event.reply(eb.build());

            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_RED + "Failed loading settings" + Main.ANSI_RESET);
        }
    }

    private static void loadUser(Guild guild) {

        File file = new File("./data/" + guild.getId() + "/user");
        if (file.exists())
            for (File subFile : file.listFiles()) {
                try {
                    FileReader reader = new FileReader(subFile);
                    Object obj = new JSONParser().parse(reader);
                    JSONObject jo = (JSONObject) obj;
                    ObjectMapper m = new ObjectMapper();

                    DonutUser user = m.readValue(jo.toString(), DonutUser.class);
                    Main.getDonutServer(guild).add(user);

                    System.out.println("[" + guild.getName() + "]: " + Main.ANSI_BLUE + "Loaded user " + user.getName() + Main.ANSI_RESET);

                } catch (IOException | ParseException e) {
                    System.out.println("[" + guild.getName() + "]: " + Main.ANSI_RED + "Failed loading data" + Main.ANSI_RESET);
                }
            }

    }

    private static void loadSettings(Guild guild) {
        try {
            FileReader reader = new FileReader(
                    "./data/" + guild.getId() + "/settings.json");

            Object obj = new JSONParser().parse(reader);
            JSONObject jo = (JSONObject) obj;

            ObjectMapper m = new ObjectMapper();
            ServerSettings settings = m.readValue(jo.toString(), ServerSettings.class);

            Main.getDonutServer(guild).setSettings(settings);

            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_BLUE + "Loaded settings" + Main.ANSI_RESET);

        } catch (IOException | ParseException e) {
            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_RED + "Failed loading settings" + Main.ANSI_RESET);
        }
    }

    public static int getDonutServerByID(Guild guild) {
        for (int i = 0; i < Main.server.size(); i++) {
            if (Main.server.get(i).getId().equalsIgnoreCase(guild.getId())) {
                return i;
            }
        }
        return -1;
    }

}
