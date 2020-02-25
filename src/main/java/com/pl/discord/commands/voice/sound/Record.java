package com.pl.discord.commands.voice.sound;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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
        super.help = "records voice";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getGuild().getAudioManager().isConnected())
            joinVoiceChannel(Objects.requireNonNull(event.getMember().getVoiceState().getChannel()), true, event);

    }

    public static void joinVoiceChannel(VoiceChannel vc, boolean warning, CommandEvent event) {
        System.out.format("Joining '%s' voice channel in %s\n", vc.getName(), vc.getGuild().getName());

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
