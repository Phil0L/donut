package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.Main;
import com.pl.discord.objects.items.Item;
import com.pl.discord.objects.items.Clothing;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.time.OffsetDateTime;

public class UserInfo extends Command {

    public UserInfo() {
        super.name = "userinfo";
        super.aliases = new String[]{"ui", "uinfo", "useri", "useringo"};
        super.category = new Category("Donut");
        super.arguments = "[user]";
        super.help = "% userinfo : displays your current stats\n" +
                "%userinfo @user : displays the users current stats";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Userinfo");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        if (Main.getDonutUser(event.getGuild(), event.getMember()) == null) {
            eb.setTitle("You are not registered. Use %enter to register in Donut Kingdom");
            event.reply(eb.build());
        } else {
            if (event.getArgs().isEmpty()) {
                showInfo(Main.getDonutUser(event.getGuild(), event.getMember()), event);
            } else {
                Member member = event.getMessage().getMentionedMembers().get(0);
                if (Main.getDonutUser(event.getGuild(), member) != null) {
                    showInfo(Main.getDonutUser(event.getGuild(), member), event);
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

        eb.addField("**Look:**", user.getHead().emoji + "\n" + user.getUpper().emoji + (user.getUpper() instanceof Clothing && ((Clothing) user.getUpper()).isTwoInOne() ? "" : "\n" + user.getTrousers().emoji) + "\n" + user.getShoes().emoji, false);

        eb.addField("**Coins:**", getCoinsString(user), false);
        eb.addField("**Donuts**", getDonutsString(user), false);


        eb.addField("Level", user.getLevel() + "", true);
        eb.addField("Rank", "", true);
        eb.setTimestamp(OffsetDateTime.now());
        event.reply(eb.build());



    }

    private String getCoinsString(DonutUser user){
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
        return coins;
    }

    private String getDonutsString(DonutUser user){
        int donutsCount = 0;
        for (Item item : user.getInventory()){
            if (item.name.toLowerCase().contains("donut"))
                donutsCount += item.getStack();
        }

        String donuts = donutsCount + " | ";
        int temp = donutsCount;

        int step = 600;
        int current = (temp / step);
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
        return donuts;
    }
}
