package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.entities.Message;

public class Mine extends Command {

    public Mine() {
        super.name = "mine";
        super.aliases = new String[]{};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "mines coins";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());
            if (Main.server.get(i).getMember(event.getMember()) != -1) {
                new Thread(() -> {
                    try {
                        if (Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).isMining()) {
                            event.reply("You are currently mining");
                        } else {
                            Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).setMining(true);
                            Message message = event.getTextChannel().sendMessage("%start").complete();

                            message.editMessage(":clock1: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock2: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock3: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock4: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock5: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock6: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock7: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock8: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock9: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock10: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock11: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            message.editMessage(":clock11: " + event.getMember().getEffectiveName() + " is mining...").queue();
                            Thread.sleep((long) ((Math.random() * 500) + 1000));

                            int value = (int) ((Math.random() * 10) + 10);
                            Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).addCoins(value);
                            Main.server.get(i).save(event.getGuild());
                            message.editMessage(event.getMember().getEffectiveName() + " **mined " + value + " coins**").queue();
                            Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).setMining(false);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }).start();

            } else {
                event.reply("You are not registered. Use %enter to register in Donut Kingdom");
            }
        }else {
            event.reply("You are not registered. Use %enter to register in Donut Kingdom");
        }
    }
}
