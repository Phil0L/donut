package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.commands.util.Ping;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class Mine extends Command {

    public Mine() {
        super.name = "mine";
        super.aliases = new String[]{};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "%mine : mines extra coins";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Mine");

        if (Main.getDonutUser(event.getGuild(), event.getMember()) != null) {

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                if (Main.getDonutUser(event.getGuild(), event.getMember()).isMining()) {
                    eb.setTitle("You are currently mining");
                    event.reply(eb.build());
                } else {
                    new Thread(() -> {
                        try {

                            Ping.addThread();
                            Main.getDonutUser(event.getGuild(), event.getMember()).setMining(true);
                            eb.setColor(Color.ORANGE);
                            eb.setTitle("start");
                            Message message = event.getTextChannel().sendMessage(eb.build()).complete();

                            eb.setTitle(":clock1: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock2: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock3: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock4: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock5: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock6: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock7: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock8: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock9: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock10: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock11: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            eb.setTitle(":clock12: " + event.getMember().getEffectiveName() + " is mining...");
                            message.editMessage(eb.build()).queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            int value = (int) ((Math.random() * 10) + 10);
                            Main.getDonutUser(event.getGuild(), event.getMember()).addCoins(value);
                            Main.getDonutServer(event.getGuild()).save(Main.getDonutUser(event.getGuild(), event.getMember()));
                            eb.setTitle(":clock12: " + event.getMember().getEffectiveName() + " is mining...\n" + event.getMember().getEffectiveName() + " **mined " + value + " coins**");
                            message.editMessage(eb.build()).queue();
                            Main.getDonutUser(event.getGuild(), event.getMember()).setMining(false);
                            Ping.removeThread();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }).start();
                }


        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("You are not registered. Use %enter to register in Donut Kingdom");
            event.reply(eb.build());
        }
    }
}
