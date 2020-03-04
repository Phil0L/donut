package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class UserInfo extends Command {

    public UserInfo() {
        super.name = "userinfo";
        super.aliases = new String[]{"info", "ui", "uinfo", "useri", "useringo"};
        super.category = new Category("Donut");
        super.arguments = "[user]";
        super.help = "displays the current user stats";
    }

    @Override
    protected void execute(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        if (Main.getServer(event.getGuild()) == -1) {
            eb.setTitle("No one registered yet. Use %enter to register in Donut Kingdom");
            event.reply(eb.build());
        } else {
            int j = Main.getServer(event.getGuild());
            if (event.getArgs().isEmpty()) {

                int i = Main.server.get(j).getMember(event.getMember());
                if (i != -1) {
                    showInfo(Main.server.get(j).getUser().get(i), event);
                } else {
                    eb.setTitle("You are not registered. Use %enter to register in Donut Kingdom");
                    event.reply(eb.build());
                }


            } else {
                Member member = event.getMessage().getMentionedMembers().get(0);
                int i = Main.server.get(j).getMember(member);
                if (i != -1) {
                    showInfo(Main.server.get(j).getUser().get(i), event);
                } else {
                    eb.setTitle("This user is not registered. Use %enter [user] to register the user in Donut Kingdom");
                    event.reply(eb.build());
                }
            }
        }
    }

    private void showInfo(DonutUser user, CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setTitle(user.getName());


        String coins = user.getCoins() + " | ";
        int temp = (int) user.getCoins();

        int step = 1000;
        int current = (temp / step);
        for (int i = 0; i < current; i++) {
            coins += ":credit_card: ";
        }
        temp -= ((temp / step) * step);

        step = 100;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            coins += ":gem: ";
        }
        temp -= ((temp / step) * step);

        step = 10;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            coins += ":moneybag: ";
        }
        temp -= ((temp / step) * step);
        step = 1;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            coins += ":dollar: ";
        }
        eb.addField("Coins", coins + "", false);


        String donuts = "";
        //String donuts = user.getDonuts() + " | ";
        //temp = user.getDonuts();
        step = 600;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            donuts += ":articulated_lorry: ";
        }
        temp -= ((temp / step) * step);

        step = 60;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            donuts += ":shopping_cart: ";
        }
        temp -= ((temp / step) * step);
        step = 6;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            donuts += ":package: ";
        }
        temp -= ((temp / step) * step);
        step = 1;
        current = (temp / step);
        for (int i = 0; i < current; i++) {
            donuts += ":doughnut: ";
        }
        eb.addField("Donuts", donuts + "", false);



        eb.addField("Level", user.getLevel() + "", false);
        eb.addField("Rank", "", false);
        event.reply(eb.build());

    }
}
