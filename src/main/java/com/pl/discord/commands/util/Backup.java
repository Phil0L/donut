package com.pl.discord.commands.util;

import com.pl.discord.Main;
import com.pl.discord.objects.DonutServer;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Backup {

    public static void save(DonutServer server){
        File file2 = new File("./data/" + server.getId() + "/backup");
        if (file2.mkdir()) {
            System.out.println("[" + server.getName() + "]: " + Main.ANSI_PURPLE + "Created backup folder" + Main.ANSI_RESET);
        }
        File file1 = new File("./data/" + server.getId());

        try {
            FileUtils.copyDirectory(file1, file2);
            new File("./data/" + server.getId() + "/backup/backup").delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.settings().setBackup();
    }

    public static boolean load(DonutServer server){
        File file1 = new File("./data/" + server.getId());
        File file2 = new File("./data/" + server.getId() + "/backup");
        if (file2.exists()) {
            new File("./data/" + server.getId() + "/user").delete();
            new File("./data/" + server.getId() + "/settings.json").delete();
            new File("./data/" + server.getId() + "/soundboard").delete();

            try {
                FileUtils.copyDirectory(file2, file1);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void delete(DonutServer server){
        new File("./data/" + server.getId() + "/user").delete();
        new File("./data/" + server.getId() + "/settings.json").delete();
        new File("./data/" + server.getId() + "/soundboard").delete();
    }
}
