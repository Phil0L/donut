package com.pl.discord.commands.voice.sound;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.voice.sound.Listeners.AudioReceiveListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Record extends Command {

    public Record() {
        super.name = "record";
        super.aliases = new String[]{"rec", "r"};
        super.category = new Category("Sound");
        super.arguments = "";
        super.help = "%record : starts recording the channel the bot is in";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Record");

        if (event.getGuild().getAudioManager().isConnected())
            joinVoiceChannel(Objects.requireNonNull(event.getMember().getVoiceState().getChannel()), true, event);

    }

    public static void joinVoiceChannel(VoiceChannel vc, boolean warning, CommandEvent event) {
        //send alert to correct users in the voice channel
        alert(vc, event);

        //initalize the audio reciever listener
        vc.getGuild().getAudioManager().setReceivingHandler(new AudioReceiveListener(1, vc));

    }

    public static void alert(VoiceChannel vc, CommandEvent event) {


            //make an embeded alert message to warn the user
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            embed.setTitle("Your audio is now being recorded in '" + vc.getName() + "'");
            embed.setTimestamp(OffsetDateTime.now());

            event.reply(embed.build());

    }


}
