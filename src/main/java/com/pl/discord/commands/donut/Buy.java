package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class Buy extends Command {

    public Buy() {
        super.name = "buy";
        super.aliases = new String[]{"buydonut", "donut"};
        super.category = new Category("Donut");
        super.arguments = "[amount]";
        super.help = "buys a donut for 30 coins";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());
            if (Main.server.get(i).getMember(event.getMember()) != -1) {
                int j = Main.server.get(i).getMember(event.getMember());
                if (event.getArgs().isEmpty()) {
                    int amount = 1;
                    if (Main.server.get(i).getUser().get(j).getCoins() >= amount * 30) {
                        buy(event, amount);
                    } else {
                        event.reply("You do have not enough coins to buy that");
                    }

                } else {
                    int amount = Integer.parseInt(event.getArgs());
                    if (Main.server.get(i).getUser().get(j).getCoins() >= amount * 30) {
                        buy(event, amount);
                    } else {
                        event.reply("You do have not enough coins to buy that");
                    }
                }
            } else {
                event.reply("You are not registered. Use %enter to register in Donut Kingdom");
            }
        } else {
            event.reply("You are not registered. Use %enter to register in Donut Kingdom");
        }
    }

    private void buy(CommandEvent event, int amount) {
        new Thread(() -> {
            Message message = event.getTextChannel().sendMessage("%start").complete();
            try {
                message.editMessage(".....:person_walking: **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message.editMessage("....:person_walking:. **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message.editMessage("...:person_walking:.. **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message.editMessage("..:person_walking:... **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message.editMessage(".:person_walking:.... **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message.editMessage(":person_walking:..... **Going to the donut store**").queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                message = event.getTextChannel().sendMessage("%start").complete();

                String message2 = ":moneybag: **Buying donuts** ";
                for (int i = 0; i < amount; i++) {
                    message.editMessage(message2).queue();
                    Thread.sleep((long) (((Math.random() * 500) + 1000)));
                    message2 += ":doughnut:";

                    int j = Main.getServer(event.getGuild());
                    int k = Main.server.get(j).getMember(event.getMember());
                    Main.server.get(j).getUser().get(k).removeCoins(30);
                    Main.server.get(j).getUser().get(k).addDonut();

                }

                message.editMessage(message2).queue();
                Thread.sleep((long) (((Math.random() * 500) + 1000)));

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.ORANGE);
                eb.setTitle(event.getMember().getEffectiveName() + " has bought " + amount + " donuts");


                message.editMessage(eb.build()).queue();

            }catch (InterruptedException ignored){

            }

        }).start();
    }
}
