package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.DonutUser;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class Serverinfo extends Command {

    public Serverinfo() {
        super.name = "serverinfo";
        super.aliases = new String[]{"si", "server", "sinfo", "serveri"};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "%serverinfo : displays the current server stats regarding their users";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Serverinfo");
        DonutServer server = Main.getDonutServer(event.getGuild());
        ArrayList<DonutUser> user = server.getUser();
        Comparator<DonutUser> compareByCategory = Comparator.comparing((DonutUser o) -> o.getName());
        user.sort(compareByCategory);

        EmbedBuilder eb = new EmbedBuilder().setColor(Color.ORANGE).setTitle(server.getName());
        int coincount = 0;
        int itemcount = 0;
        eb.setDescription("**User:**\n\n");

        for (DonutUser duser : user){
            eb.appendDescription("**" + duser.getName() + ":** %userinfo @" + duser.getName() + "\n");
            coincount += duser.getCoins();
            itemcount += duser.getInventory().size();
        }

        eb.addField("**Total coins:**", String.valueOf(coincount), true);
        eb.addField("**Total items:**", String.valueOf(itemcount), true);
        eb.setTimestamp(OffsetDateTime.now());

        event.reply(eb.build());
    }
}
