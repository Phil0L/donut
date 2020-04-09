package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.locations.Location;
import com.pl.discord.objects.locations.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class LocationCommand extends Command {
    ArrayList<Location> locations;

    public LocationCommand() {
        super.name = "location";
        super.aliases = new String[]{"locations", "loc", "store", "stores"};
        super.category = new Category("Donut");
        super.arguments = "[type]";
        super.help = "" +
                "%location : shows the list of locations\n" +
                "%location [location] : enters the provided location";

        this.locations = new ArrayList<>();
        this.locations.add(new Bakery());
        this.locations.add(new Supermarket());
        this.locations.add(new GasStation());

        this.locations.add(new HardwareStore());
        this.locations.add(new ClothingStore());
        this.locations.add(new Casino());
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Location");

        if (Main.getDonutUser(event.getGuild(), event.getMember()) != null) {
            if (event.getArgs().isEmpty()) {
                // show locations
                printStores(event);
            } else {
                //enter location
                if (getLocationByName(event.getArgs()) != null) {
                    enter(getLocationByName(event.getArgs()), event);
                } else
                    event.reply(new EmbedBuilder().setTitle("No such location").setColor(Color.RED).build());
            }
        } else {
            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You are not registered. Use %enter to register in Donut Kingdom").build());
        }

    }

    private void enter(Location loc, CommandEvent event) {
        if (loc.open()) {
            loc.checkForEmpty();
            if (loc.stock.size() < loc.restockAt)
                loc.restock();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(loc.emoji + " " + loc.name + ":").setColor(Color.ORANGE);
            eb.addBlankField(false);
            for (int k = 0; k < loc.stock.size(); k++) {
                switch (loc.getName()) {
                    case "Bakery":
                    case "Supermarket":
                    case "Gas station":
                        eb.addField(loc.getEmoji() + " **" + loc.stock.get(k).name + "** \nstocked: **" + loc.stock.get(k).stack + "** \nprize: **" + loc.stock.get(k).prize + "** coins", loc.stock.get(k).command, true);
                        if (k % 3 == 2)
                            eb.addBlankField(false);
                        break;
                    case "Hardware store":
                        break;
                    case "Clothing store":
                        eb.addField(loc.getEmoji() + " **" + loc.stock.get(k).name + "** \nprize: **" + loc.stock.get(k).prize + "** coins", loc.stock.get(k).command, true);
                        if (k % 3 == 2)
                            eb.addBlankField(false);
                        break;
                    default:
                        break;
                }

            }
            switch (loc.getName()) {
                case "Casino":
                    eb.addField("**Item spin**", "50 coins:\n%itemspin", true);
                    eb.addField("**Coin gamble**", "100 coins:\n%coingamble", true);
                    event.reply(eb.build());
                    break;
                default:
                    event.reply(eb.build());
                    break;

            }

            Main.getDonutUser(event.getGuild(), event.getMember()).enterLocation(loc);
            Main.getDonutServer(event.getGuild()).save(Main.getDonutUser(event.getGuild(), event.getMember()));
        } else {
            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(loc.name + " is closed").build());
        }

    }

    private void printStores(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Locations:").setColor(Color.ORANGE);
        eb.addField("**Time: " + getTime() + " o'clock " + getPeriod() + "**", " ", false);
        for (int k = 0; k < locations.size(); k++) {
            eb.addField(locations.get(k).emoji + " " + locations.get(k).name, locations.get(k).getCommand(), true);
        }
        event.reply(eb.build());
    }

    private Location getLocationByName(String command) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).command.toLowerCase().split(" ")[1].equals(command.toLowerCase())) {
                return locations.get(i);
            }
        }
        return null;
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        int now = (int) (cal.get(Calendar.MINUTE) / 2.5);
        return (now > 12 ? String.valueOf(now - 12) : String.valueOf(now));

    }

    private String getPeriod() {
        Calendar cal = Calendar.getInstance();
        int now = (int) (cal.get(Calendar.MINUTE) / 2.5);
        return (now > 12 ? "pm" : "am");
    }
}
