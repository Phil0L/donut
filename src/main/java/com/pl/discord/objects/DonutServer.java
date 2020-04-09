package com.pl.discord.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pl.discord.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DonutServer {
    private String name;
    private String id;
    private ArrayList<DonutUser> user;
    private ServerSettings settings;

    public static int SAVE_USER = 0;
    public static int SAVE_SETTINGS = 1;
    public static int SAVE_SOUNDBOARD = 2;


    public DonutServer(Guild guild) {
        this.name = guild.getName();
        this.id = guild.getId();
        this.user = new ArrayList<>();
        this.settings = new ServerSettings(guild);
    }

    public void addUser(Member member) {
        user.add(new DonutUser(member));
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<DonutUser> getUser() {
        return user;
    }

    public void resetUser() {
        this.user = new ArrayList<>();
    }

    public void add(DonutUser user) {
        this.user.add(user);
    }

    public void save() {
        File file = new File("./data/" + getId());
        if (file.mkdir())
            System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created data folder" + Main.ANSI_RESET);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String data = "";
        FileWriter fileWriter;

        if (!getUser().isEmpty()) {
            file = new File("./data/" + getId() + "/user");
            if (file.mkdir())
                System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created user folder" + Main.ANSI_RESET);

            for (DonutUser user : getUser()) {
                try {

                    file = new File("./data/" + getId() + "/user/" + user.getId() + ".json");
                    data = mapper.writeValueAsString(user);
                    fileWriter = new FileWriter(file);
                    fileWriter.write(data);
                    fileWriter.flush();
                    System.out.println("[" + getName() + "]: " + Main.ANSI_GREEN + "Saved " + user.getName() + Main.ANSI_RESET);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            file = new File("./data/" + getId() + "/settings.json");
            fileWriter = new FileWriter(file);
            data = mapper.writeValueAsString(settings);
            fileWriter.write(data);
            fileWriter.flush();
            System.out.println("[" + getName() + "]: " + Main.ANSI_GREEN + "Saved settings" + Main.ANSI_RESET);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(DonutUser user) {
        File file = new File("./data/" + getId());
        if (file.mkdir())
            System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created data folder" + Main.ANSI_RESET);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        String data = "";
        FileWriter fileWriter;

        if (!getUser().isEmpty()) {
            file = new File("./data/" + getId() + "/user");
            if (file.mkdir())
                System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created user folder" + Main.ANSI_RESET);

            try {

                file = new File("./data/" + getId() + "/user/" + user.getId() + ".json");
                data = mapper.writeValueAsString(user);
                fileWriter = new FileWriter(file);
                fileWriter.write(data);
                fileWriter.flush();
                System.out.println("[" + getName() + "]: " + Main.ANSI_GREEN + "Saved " + user.getName() + Main.ANSI_RESET);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void save(int what) {
        switch (what){
            case 0: //USER
                File file = new File("./data/" + getId());
                if (file.mkdir())
                    System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created data folder" + Main.ANSI_RESET);

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                String data = "";
                FileWriter fileWriter;

                if (!getUser().isEmpty()) {
                    file = new File("./data/" + getId() + "/user");
                    if (file.mkdir())
                        System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created user folder" + Main.ANSI_RESET);

                    for (DonutUser user : getUser()) {
                        try {

                            file = new File("./data/" + getId() + "/user/" + user.getId() + ".json");
                            data = mapper.writeValueAsString(user);
                            fileWriter = new FileWriter(file);
                            fileWriter.write(data);
                            fileWriter.flush();
                            System.out.println("[" + getName() + "]: " + Main.ANSI_GREEN + "Saved " + user.getName() + Main.ANSI_RESET);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case 1: //SETTINGS
                file = new File("./data/" + getId());
                if (file.mkdir())
                    System.out.println("[" + getName() + "]: " + Main.ANSI_PURPLE + "Created data folder" + Main.ANSI_RESET);

                mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                data = "";

                try {
                    file = new File("./data/" + getId() + "/settings.json");
                    fileWriter = new FileWriter(file);
                    data = mapper.writeValueAsString(settings);
                    fileWriter.write(data);
                    fileWriter.flush();
                    System.out.println("[" + getName() + "]: " + Main.ANSI_GREEN + "Saved settings" + Main.ANSI_RESET);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }

    public int getMember(Member member) {
        for (int i = 0; i < this.user.size(); i++) {
            if (this.user.get(i).getName().equals(member.getEffectiveName())) {
                return i;
            }
        }
        return -1;
    }

    public static void printUser(ArrayList<DonutUser> list) {
        for (DonutUser user : list) {
            System.out.print(user.toString());
        }
    }

    public ServerSettings settings() {
        return this.settings;
    }

    public void setSettings(ServerSettings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "server:[name=" + this.name + ",id=" + this.id + ",settings:" + this.settings + ",user=" + Arrays.toString(this.user.toArray()) + "]\n";
    }
}
