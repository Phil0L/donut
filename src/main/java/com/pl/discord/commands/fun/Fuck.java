package com.pl.discord.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.util.Random;

public class Fuck extends Command {

    public Fuck(){
        super.name = "fuck";
        super.aliases = new String[]{};
        super.category = new Category("Fun");
        super.arguments = "[length]";
        super.help = "%fuck [length] : sends a tts message of random chars (will definitely get updated)";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Fuck");

        int i = Integer.parseInt(event.getArgs());
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < i) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setTTS(true);
        messageBuilder.setContent(saltStr);
        event.reply(messageBuilder.build());
    }
}
