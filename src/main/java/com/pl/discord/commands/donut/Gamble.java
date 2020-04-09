package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.objects.items.Gambable;
import com.pl.discord.objects.locations.Casino;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class Gamble extends Command {

    public Gamble() {
        super.name = "gamble";
        super.aliases = new String[]{"itemspin", "coingamble"};
        super.category = new Category("Donut");
        super.arguments = "";
        super.help = "" +
                "%coingamble : gambles with 100 coins\n" +
                "%itemspin: win a random item";
    }

    @Override
    protected void execute(CommandEvent event) {
        DonutUser user = Main.getDonutUser(event.getGuild(), event.getMember());
        if (user != null && user.currentLocation().name.equalsIgnoreCase("casino"))
            if (event.getMessage().getContentRaw().startsWith("%itemspin")) {
                if (user.getCoins() >= 50)
                    new Thread(() -> {
                        user.removeCoins(50);
                        Gambable[] items = new Gambable[9];
                        for (int i = 0; i < items.length; i++) {
                            items[i] = Casino.getRandom();
                        }
                        int skips = 10;
                        int delay = 10;
                        EmbedBuilder eb = new EmbedBuilder().setAuthor("Casino").setTitle("Item spin").setColor(Color.ORANGE).setDescription(getString(items));
                        Message message = event.getTextChannel().sendMessage(eb.build()).complete();

                        for (int i = 0; i < skips; i++) {
                            move(items);
                            message.editMessage(eb.setDescription(getString(items)).build()).queue();
                            try {
                                Thread.sleep(delay);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            delay += skips * 20;
                        }
                        message.editMessage(eb.setDescription("**Reward:**\n" + items[4].getItem().emoji + " " + items[4].getItem().name).build()).queue();
                        user.addItem(items[4].getItem());
                    }).start();
                else
                    event.reply(new EmbedBuilder().setTitle("Not enougth coins").setColor(Color.RED).build());
            } else if (event.getMessage().getContentRaw().startsWith("%coingamble")) {
                if (user.getCoins() >= 100)
                    new Thread(() -> {
                        user.removeCoins(100);
                        int[][] coins = new int[3][3];
                        for (int i = 0; i < coins.length; i++) {
                            for (int j = 0; j < coins[i].length; j++){
                                coins[i][j] = (int) (Math.random() * 11);
                            }
                        }
                        EmbedBuilder eb = new EmbedBuilder().setAuthor("Casino").setTitle("Item spin").setColor(Color.ORANGE).setDescription(getString(coins));
                        Message message = event.getTextChannel().sendMessage(eb.build()).complete();

                        for (int i = 0; i < 5; i++) {
                            move(coins, true, true, true);
                            message.editMessage(eb.setDescription(getString(coins)).build()).queue();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < 3; i++) {
                            move(coins, false, true, true);
                            message.editMessage(eb.setDescription(getString(coins)).build()).queue();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < 2; i++) {
                            move(coins, false, false, true);
                            message.editMessage(eb.setDescription(getString(coins)).build()).queue();
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                else
                    event.reply(new EmbedBuilder().setTitle("Not enougth coins").setColor(Color.RED).build());

            } else {

            }


    }

    private String getString(Gambable[] array) {
        String s = ":blue_square::blue_square::blue_square::blue_square::arrow_down::blue_square::blue_square::blue_square::blue_square:\n";
        for (Gambable gambable : array) {
            switch (gambable.getRarity()) {
                case 0:
                    s += ":white_circle:";
                    break;
                case 1:
                    s += ":blue_circle:";
                    break;
                case 2:
                    s += ":purple_circle:";
                    break;
                case 3:
                    s += ":orange_circle:";
                    break;

            }
        }
        return s;
    }

    private String getString(int[][] coins){
        String s = "";
        for (int[] coin : coins) {
            for (int i : coin) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        s += ":white_circle:";
                        break;
                    case 5:
                    case 6:
                    case 7:
                        s += ":blue_circle:";
                        break;
                    case 8:
                    case 9:
                        s += ":purple_circle:";
                        break;
                    case 10:
                        s += ":orange_circle:";
                        break;
                }
            }
        }
        return s;
    }

    private Gambable[] move(Gambable[] array) {
        if (array.length - 1 >= 0) System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = Casino.getRandom();
        return array;
    }

    private int[][] move(int[][] array, boolean one, boolean two, boolean three){
        if (one) {
            array[2][0] = array[1][0];
            array[1][0] = array[0][0];
            array[0][0] = (int) (Math.random() * 11);
        }
        if (one) {
            array[2][1] = array[1][1];
            array[1][1] = array[0][1];
            array[0][1] = (int) (Math.random() * 11);
        }
        if (one) {
            array[2][2] = array[1][2];
            array[1][2] = array[0][2];
            array[0][2] = (int) (Math.random() * 11);
        }
        return array;
    }

}
