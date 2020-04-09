package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.ServerSettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.time.OffsetDateTime;

public class Settings extends Command {

    public Settings(){
        super.name = "settings";
        super.aliases = new String[]{};
        super.arguments ="[setting] [status]";
        super.category = new Category("Utilities");
        super.help = "%settings : shows the current settings for your server\n" +
                "%settings [setting] [status] : changes the status of a setting";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Settings");

        if (Main.getDonutServer(event.getGuild()) == null || Main.getDonutServer(event.getGuild()).settings() == null)
            Main.server.add(new DonutServer(event.getGuild()));
        DonutServer server = Main.getDonutServer(event.getGuild());

        if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || server.settings().isUseraccess()) {

            if (event.getArgs().length() == 0)
                event.reply(printSettingsMessage(event.getGuild()));
            else {
                ServerSettings serverSettings = Main.getDonutServer(event.getGuild()).settings();
                switch (event.getArgs().split(" ")[0]) {
                    case "autojoin":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("on")) {
                                serverSettings.setAutoJoin(true);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("off")) {
                                serverSettings.setAutoJoin(false);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        }
                        break;
                    case "autoleave":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("on")) {
                                serverSettings.setAutoLeave(true);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("off")) {
                                serverSettings.setAutoLeave(false);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        }
                        break;
                    case "autorec":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("on")) {
                                serverSettings.setAutoRec(true);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("off")) {
                                serverSettings.setAutoRec(false);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        }
                        break;
                    case "player":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("YOUTUBE")) {
                                serverSettings.setDefaultPlayer(ServerSettings.PLAYER_YOUTUBE);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("SPOTIFY")) {
                                serverSettings.setDefaultPlayer(ServerSettings.PLAYER_SPOTIFY);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"youtube\" or \"spotify\".").setAuthor("Settings").build());
                        }
                        break;
                    case "channel":
                        if (event.getArgs().split(" ")[1] == null || event.getMessage().getMentionedChannels().isEmpty()) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide a channel.").setAuthor("Settings").build());
                        } else {
                            serverSettings.setBotchannel(event.getMessage().getMentionedChannels().get(0));
                            event.reply(printSettingsMessage(event.getGuild()));
                        }
                        break;
                    case "role":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("on")) {
                                serverSettings.setAssignRole(true);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("off")) {
                                serverSettings.setAssignRole(false);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        }
                        break;
                    case "backup":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"create\" or \"load\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("create")) {
                                Backup.save(Main.getDonutServer(event.getGuild()));
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("load")) {
                                Backup.load(Main.getDonutServer(event.getGuild()));
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"create\" or \"load\".").setAuthor("Settings").build());
                        }
                        break;
                    case "data":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide \"delete\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("delete")) {
                                Backup.delete(Main.getDonutServer(event.getGuild()));
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Data deleted.").setAuthor("Settings").build());
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide \"delete\".").setAuthor("Settings").build());
                        }
                        break;
                    case "useraccess":
                        if (event.getArgs().split(" ")[1] == null) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        } else {
                            if (event.getArgs().split(" ")[1].equalsIgnoreCase("on")) {
                                serverSettings.setUseraccess(true);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else if (event.getArgs().split(" ")[1].equalsIgnoreCase("off")) {
                                serverSettings.setUseraccess(false);
                                event.reply(printSettingsMessage(event.getGuild()));
                            } else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(event.getArgs().split(" ")[1] + "is no valid state. You have to provide either \"on\" or \"off\".").setAuthor("Settings").build());
                        }
                        break;
                    default:
                        event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Cannot find that setting").setAuthor("Settings").build());
                        break;

                }
                Main.getDonutServer(event.getGuild()).save(ServerSettings.SAVE_SETTINGS);
            }
        }
    }

    private MessageEmbed printSettingsMessage(Guild guild){
        ServerSettings serverSettings = Main.getDonutServer(guild).settings();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setTitle("Settings");
        eb.addField("**Automatic join**", (serverSettings.isAutoJoin() ? "**On**" : "**Off**") + "\n%settings autojoin " + (serverSettings.isAutoJoin() ? "off" : "on"), true);
        eb.addField("**Automatic leave**", (serverSettings.isAutoLeave() ? "**On**" : "**Off**") + "\n%settings autoleave " + (serverSettings.isAutoJoin() ? "off" : "on"), true);
        eb.addField("**Automatic record**", (serverSettings.isAutoRec() ? "**On**" : "**Off**") + "\n%settings autorec " + (serverSettings.isAutoJoin() ? "off" : "on"), true);
        eb.addField("**Default music selector for %play**", (serverSettings.getDefaultPlayer() == ServerSettings.PLAYER_YOUTUBE ? "**Youtube**" : "**Spotify**") + "\n%settings player " + (serverSettings.getDefaultPlayer() == ServerSettings.PLAYER_YOUTUBE ? "spotify" : "youtube"), false);
        eb.addField("**Bot channel**", "**" + serverSettings.getBotChannelName(guild) + "** \n%settings channel #YourChannel", false);
        eb.addField("**Assign role 'Donut Member'**", (serverSettings.isAssignRole() ? "**On**" : "**Off**") + "\n%settings role " + (serverSettings.isAssignRole() ? "off" : "on"), false);
        eb.addField("**Create Backup**", "**Last backup: " + serverSettings.getBackup() + "**\n%settings backup create", true);
        if (!serverSettings.getBackup().equalsIgnoreCase("none"))
            eb.addField("**Load Backup**", "**Last backup: " + serverSettings.getBackup() + "**\n%settings backup load", true);
        eb.addField("**Delete data**", "%settings data delete", true);
        eb.addField("**Access for everyone to everything**", (serverSettings.isUseraccess() ? "**On**" : "**Off**") + "\n%settings useraccess " + (serverSettings.isUseraccess() ? "off" : "on"), false);
        eb.setTimestamp(OffsetDateTime.now());

        return eb.build();

    }

}
