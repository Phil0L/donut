package com.pl.discord.commands.voice;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Join extends Command {

    public Join() {
        super.name = "join";
        super.aliases = new String[]{"j"};
        super.category = new Category("Sound");
        super.arguments = "";
        super.help = "connects the Bot to your Voicecannel";
    }

    @Override
    protected void execute(CommandEvent event) {
        AudioManager audio = event.getGuild().getAudioManager();
        try {
            audio.openAudioConnection(event.getMember().getVoiceState().getChannel());
        } catch (Exception e) {
            event.getTextChannel().sendMessage("You have to be in a voice channel").queue();
        }

    }


}
