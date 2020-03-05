package com.pl.discord.objects;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.json.JSONArray;
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


    public DonutServer(Guild guild){
        this.name = guild.getName();
        this.id = guild.getId();
        this.user = new ArrayList<>();
    }

    public void addUser(Member member){
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

    public void resetUser(){
        this.user = new ArrayList<>();
    }

    public void add(DonutUser user){
        this.user.add(user);
    }

    public void save(Guild guild) {
        JSONArray ja = new JSONArray();
        for (DonutUser donutUser : this.user) {
            JSONObject jo = new JSONObject(donutUser);
            ja.put(jo);
        }
        try {
            File file2 = new File("./data/" + guild.getName().replace(' ', '_'));
            File file3 = new File("./data/" + guild.getName().replace(' ', '_') + "/" + guild.getId() + ".json");
            FileWriter file;
            if (file3.exists()) {
                file = new FileWriter("./data/" + guild.getName().replace(' ', '_') + "/" + guild.getId() + ".json");
            } else if (file2.exists()){
                file = new FileWriter("./data/" + guild.getName().replace(' ', '_') + "/" + guild.getId() + ".json");
            }else{
                file2.mkdir();
                file = new FileWriter("./data/" + guild.getName().replace(' ', '_') + "/" + guild.getId() + ".json");
            }

            file.write(ja.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public String toString() {
        return "server:[name=" + this.name + ",id=" + this.id + ",user=" + Arrays.toString(this.user.toArray()) + "]\n";
    }
}
