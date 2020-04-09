package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.objects.items.Item;
import com.pl.discord.objects.items.Clothing;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Buy extends Command {

    public Buy(){
        super.name = "buy";
        super.aliases = new String[]{"get", "purchase"};
        super.category = new Category("Donut");
        super.arguments = "[item] [quantity]";
        super.help = "" +
                "%buy [item] : buys one of the item\n" +
                "%buy [item] [quantity] : buys [quantity] of the item";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Buy");

        if (Main.getDonutUser(event.getGuild(), event.getMember()) == null){
            event.reply(new EmbedBuilder().setTitle("You are not registered. Use %enter to register in Donut Kingdom").setColor(Color.RED).build());
        }else{
            DonutUser donutUser = Main.getDonutUser(event.getGuild(), event.getMember());
            if (donutUser.currentLocation() == null) {
                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You are currently in no location use \"%help location\" to find out more").build());
            }else {
                for (Item item : donutUser.currentLocation().stock) {
                    if (item.command.equals(event.getMessage().getContentRaw().split(" ")[0] + " " + event.getMessage().getContentRaw().split(" ")[1])) {
                        buy(item, donutUser, event.getMessage().getContentRaw().split(" ").length > 2 ? Integer.parseInt(event.getMessage().getContentRaw().split(" ")[2]) : 1, event);
                        return;
                    }
                }
                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Cannot find that item in your current location: " + donutUser.currentLocation().name).build());
            }
        }
    }

    private void buy(Item item, DonutUser user, int quantity, CommandEvent event){
        if (quantity > item.stack)
            buy(item, user, item.stack, event);
        else {
            if (user.getCoins() < item.prize * quantity) {
                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("You do not have enougth coins to buy " + quantity + " " + item.name + " (" + (item.prize * quantity - user.getCoins()) + " coins missing)").build());
            } else {
                item.removeItem(quantity);
                user.currentLocation().checkForEmpty();
                user.addItem(new Item(quantity, item));
                user.combineItems();
                user.removeCoins(item.prize * quantity);
                event.reply(new EmbedBuilder().setColor(Color.RED).setTitle("Bought " + quantity + " " + item.name).build());
                if (item instanceof Clothing){
                    switch (((Clothing) item).getPosition()){
                        case 0:
                            if (user.getHead().getName().equalsIgnoreCase("none"))
                                user.setLook((Clothing) item, 0);
                            break;
                        case 1:
                            if (user.getUpper().getName().equalsIgnoreCase("none")) {
                                user.setLook((Clothing) item, 1);
                                if (((Clothing) item).isTwoInOne())
                                    user.moveToInv(2);
                            }
                            break;
                        case 2:
                            if (user.getTrousers().getName().equalsIgnoreCase("none"))
                                user.setLook((Clothing) item, 2);
                            break;
                        case 3:
                            if (user.getShoes().getName().equalsIgnoreCase("none"))
                                user.setLook((Clothing) item, 3);
                            break;
                    }
                }
                DonutServer server = Main.getDonutServer(event.getGuild());
                server.save(user);
            }
        }
    }
}
