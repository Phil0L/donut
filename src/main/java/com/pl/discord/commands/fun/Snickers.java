package com.pl.discord.commands.fun;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;

public class Snickers extends Command {

    public Snickers(){
        super.name = "snickers";
        super.aliases = new String[]{"snikers","isseinsnickers","iss-ein-snickers"};
        super.category = new Category("Fun");
        super.arguments = "";
        super.help = "%snickers : sends a random snickers gif\n" +
                "This is an insider pls dont be confused";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Snickers");

        ArrayList<String> sample = new ArrayList<>();
        sample.add("https://media.giphy.com/media/fsoHbBoAF38hI2qcpc/giphy.gif");
        sample.add("https://media.giphy.com/media/xXjIVvfvkvqZG/giphy.gif");
        sample.add("https://media.giphy.com/media/10OPcijAw07udq/giphy-downsized.gif");
        sample.add("https://media.giphy.com/media/106EGwK7KpwbCg/giphy.gif");
        sample.add("https://media.giphy.com/media/3o72F4QT3KEBtMtGaQ/giphy-downsized.gif");
        sample.add("https://media.giphy.com/media/Z9o1CZDg5bIrquZDCS/giphy.gif");
        sample.add("https://media.giphy.com/media/LOcq77tRkdI3Zr3Xil/giphy-downsized.gif");

        EmbedBuilder eb = new EmbedBuilder();
        eb.setImage(sample.get((int) (Math.random() * sample.size())));
        event.reply(eb.build());
    }
}
