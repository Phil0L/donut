package com.pl.discord.commands.donut;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.DonutServer;
import com.pl.discord.DonutUser;
import com.pl.discord.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Load extends Command {

    public Load() {
        super.name = "load";
        super.aliases = new String[]{};
        super.category = new Category("Donut");
        super.arguments = "[]";
        super.help = "reloads the data";
    }

    @Override
    protected void execute(CommandEvent event) {
        load(event);
    }

    private void load(CommandEvent event) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try {
            File file = new File("./data/" + event.getGuild().getName().replace(' ', '_') + "/" + event.getGuild().getId() + ".json");
            if (file.exists()) {

                FileReader reader = new FileReader(
                        "./data/" + event.getGuild().getName().replace(' ', '_') + "/" + event.getGuild().getId() + ".json");

                //Read JSON file
                Object obj = jsonParser.parse(reader);

                org.json.simple.JSONArray list = (org.json.simple.JSONArray) obj;

                //Iterate over array
                list.forEach(emp -> {
                    try {
                        getUser((org.json.simple.JSONObject) emp, event.getGuild());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                event.reply("Loaded data of " + event.getGuild().getName());
            } else {
                event.reply("Couldn't find that file");
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }

    private static void getUser(org.json.simple.JSONObject user, Guild guild) throws IOException {
        ObjectMapper m = new ObjectMapper();
        DonutUser myClass = m.readValue(user.toString(), DonutUser.class);
        if (Main.getServer(guild) == -1){
            Main.server.add(new DonutServer(guild));
        }
        Main.server.get(Main.getServer(guild)).add(myClass);
    }

}
