package com.pl.discord.commands.voice.music;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.music.handler.PlayerManager;
import com.pl.discord.objects.DonutServer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.TrackMarker;
import com.sedmelluq.discord.lavaplayer.track.TrackMarkerHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.gradle.internal.impldep.org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class Soundboard extends Command {
    public static long position = 0;
    public static boolean wasActive = true;

    public static int SAVE_SOUNDBOARD = 2;

    public Soundboard() {
        super.name = "soundboard";
        super.aliases = new String[]{"sound", "booard", "sb"};
        super.category = new Category("Sound");
        super.arguments = "[action] [name] {source}";
        super.help = "" +
                "%soundboard save [name] [link] : saves the link of the clip. Can be played by %soundboard play [name]\n" +
                "%soundboard save [name] +{File} : saves the file as a clip. Can be played by %soundboard play [name]\n" +
                "%soundboard play [name] : \n";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Soundboard");

        if (Main.getDonutUser(event.getGuild(), event.getMember()) == null) {
            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You have to be entered to access the servers data").build());
        } else {
            DonutServer server = Main.getDonutServer(event.getGuild());
            if (event.getArgs().isEmpty() || event.getArgs().split(" ").length < 2) {
                // show clips
                showBoard(event, server);
            } else {
                String mode = event.getArgs().split(" ")[0];
                String name = event.getArgs().split(" ")[1];
                if (mode.equalsIgnoreCase("save")) {
                    //save
                    if (!(event.getArgs().split(" ").length > 2 && isUrl(event.getArgs().split(" ")[2])))
                        try {
                            File file =
                                    event.getMessage().getAttachments().get(0).downloadToFile().get();
                            String ending = FilenameUtils.getExtension(file.getPath());
                            if (ending.equals("mp3") || ending.equals("flac") || ending.equals("wav"))
                                addFile(file, event.getGuild(), name);
                            else
                                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Filetype " + ending + " is not supported.").build());

                        } catch (InterruptedException | ExecutionException | IndexOutOfBoundsException ignored) {
                            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Failed getting attached file. \nYou have to attach a file of the sound or add a link after the name").build());
                        }
                    else {
                        String url = event.getArgs().split(" ")[2];
                        addUrlFile(url, event.getGuild(), name);
                    }
                } else if (mode.equalsIgnoreCase("play")) {
                    //play
                    File file = new File("./data/" + server.getId() + "/soundboard");
                    for (File subfile : file.listFiles()) {
                        if (subfile.getName().startsWith(name)) {
                            playFile(subfile, event.getGuild());
                            break;
                        }
                    }

                } else
                    event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Can not recognize " + mode).build());
            }
        }


    }

    private void showBoard(CommandEvent event, DonutServer server) {
        File file = new File("./data/" + server.getId() + "/soundboard");
        if (file.exists()) {
            EmbedBuilder eb = new EmbedBuilder().setColor(Color.ORANGE).setTitle("Soundboard of " + server.getName());
            for (File subfile : file.listFiles()) {
                String name = subfile.getName().replaceAll(".txt", "").replaceAll(".mp3","").replaceAll(".flac","").replaceAll(".wav","");
                eb.addField("**" + StringUtils.capitalize(name) + "**", "%sb play " + name, true);
            }
            event.reply(eb.build());
        }
    }

    private void addFile(File filesrc, Guild guild, String name) {
        DonutServer server = Main.getDonutServer(guild);
        File filefolder = new File("./data/" + server.getId() + "/soundboard");
        if (filefolder.mkdir()) {
            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_PURPLE + "Created soundboard folder" + Main.ANSI_RESET);
        }
        File filedest = new File("./data/" + server.getId() + "/soundboard/" + name + "." + FilenameUtils.getExtension(filesrc.getPath()));
        try {
            FileUtils.copyFile(filesrc, filedest);
            server.save();
        } catch (IOException ignored) {
        }
    }

    private void addUrlFile(String url, Guild guild, String name) {
        DonutServer server = Main.getDonutServer(guild);
        File filefolder = new File("./data/" + server.getId() + "/soundboard");
        if (filefolder.mkdir()) {
            System.out.println("[" + guild.getName() + "]: " + Main.ANSI_PURPLE + "Created soundboard folder" + Main.ANSI_RESET);
        }
        File filedest = new File("./data/" + server.getId() + "/soundboard/" + name + ".txt");
        try {
            FileWriter fileWriter = new FileWriter(filedest);
            fileWriter.write(url);
            fileWriter.flush();
            server.save();
        } catch (IOException ignored) {

        }
    }

    private void playFile(File file, Guild guild) {
        if (FilenameUtils.getExtension(file.getPath()).equals("txt"))
            playLinkFile(file, guild);
        else {
            String identifier = file.getPath();
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(guild, identifier);
        }

    }

    private void playLinkFile(File file, Guild guild) {
        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.US_ASCII);
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(guild, content);
        } catch (IOException ignored) {
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

    private void interruptTrack(String identifier, Guild guild) {
        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack track = null;
        boolean wasPaused = manager.getGuildMusicManager(guild).player.isPaused();
        if (wasPaused)
            manager.getGuildMusicManager(guild).player.setPaused(false);
        if (manager.getGuildMusicManager(guild).player.getPlayingTrack() != null) {
            wasActive = true;
            track = manager.getGuildMusicManager(guild).player.getPlayingTrack();
            position = track.getPosition();
            manager.getGuildMusicManager(guild).scheduler.queueFront(track);
        }
        manager.loadAndPlay(guild, identifier);

    }
}
