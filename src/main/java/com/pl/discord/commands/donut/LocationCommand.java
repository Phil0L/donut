package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.Location;
import com.pl.discord.objects.locations.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;

public class LocationCommand extends Command {
    ArrayList<Location> locations;

    public LocationCommand() {
        super.name = "location";
        super.aliases = new String[]{"locations","loc","store","stores"};
        super.category = new Category("Donut");
        super.arguments = "[type]";
        super.help = "enteres a location";
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
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());
            if (Main.server.get(i).getMember(event.getMember()) != -1) {
                int j = Main.server.get(i).getMember(event.getMember());
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
        } else {
            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You are not registered. Use %enter to register in Donut Kingdom").build());
        }
    }

    private void enter(Location loc, CommandEvent event) {
        if (loc.isOpen()){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(loc.emoji + loc.name + ":").setColor(Color.ORANGE);
            for (int k = 0; k < loc.stock.size(); k++) {
                eb.addField(loc.stock.get(k).name, loc.stock.get(k).command, true);
                if (k % 3 == 2)
                    eb.addBlankField(false);
            }
            event.reply(eb.build());
        }else {
            event.reply(new EmbedBuilder().setColor(Color.RED).setTitle(loc.name + "is not open").build());
        }

    }

    private void printStores(CommandEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Locations:").setColor(Color.ORANGE);
        for (int k = 0; k < locations.size(); k++) {
            eb.addField(locations.get(k).emoji + " " + locations.get(k).name, locations.get(k).getCommand(), true);
            if (k % 3 == 2)
                eb.addBlankField(false);
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

    /*private void buy(CommandEvent event, int amount) {
        new Thread(() -> {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.ORANGE);
            eb.setTitle("start");
            Message message = event.getTextChannel().sendMessage(eb.build()).complete();
            try {
                Ping.addThread();
                eb.setTitle(".....:person_walking: **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                eb.setTitle("....:person_walking:. **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                eb.setTitle("...:person_walking:.. **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                eb.setTitle("..:person_walking:... **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                eb.setTitle(".:person_walking:.... **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                eb.setTitle(":person_walking:..... **Going to the store**");
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) ((Math.random() * 500) + 1000));

                String message2 = ":moneybag: **Buying donuts** ";
                for (int i = 0; i < amount; i++) {
                    eb.setTitle(":person_walking:..... **Going to the donut store**\n" + message2);
                    message.editMessage(eb.build()).queue();
                    Thread.sleep((long) (((Math.random() * 500) + 1000)));
                    message2 += ":doughnut:";
                    message2 = message2.replaceAll(":doughnut::doughnut::doughnut::doughnut::doughnut::doughnut:", ":package:");
                    message2 = message2.replaceAll(":package::package::package::package::package::package::package::package::package::package:", ":shopping_cart:");

                    int j = Main.getServer(event.getGuild());
                    int k = Main.server.get(j).getMember(event.getMember());
                    Main.server.get(j).getUser().get(k).removeCoins(30);
                    Main.server.get(j).getUser().get(k).addDonuts(1);

                }

                eb.setTitle(":person_walking:..... **Going to the donut store**\n" + message2);
                message.editMessage(eb.build()).queue();
                Thread.sleep((long) (((Math.random() * 500) + 1000)));


                eb.setColor(Color.ORANGE);
                eb.setTitle(":person_walking:..... **Going to the donut store**\n" + message2 + "\n" + event.getMember().getEffectiveName() + " has bought **" + amount + "** donuts");
                message.editMessage(eb.build()).queue();

                Ping.removeThread();
            }catch (InterruptedException ignored){

            }

        }).start();
    }*/
}
