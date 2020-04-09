package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;

public class Ping extends Command {
    static int activeThreads;
    static ArrayList<Integer> time;
    static String one;
    static String two;
    static String three;
    static long id1;
    static long id2;
    static long id3;

    public Ping() {
        super.name = "ping";
        super.aliases = new String[]{};
        super.category = new Category("Utilities");
        super.arguments = "";
        super.help = "%ping : tries to calculate the current latency of the bot";
        activeThreads = 0;
        time = new ArrayList<>();
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Ping");

        activeThreads += Main.manager.getGuilds().size();
        time = new ArrayList<>();
        one = "";
        two = "";
        three = "";
        id1 = 0;
        id2 = 0;
        id3 = 3;
        sendTime(1, event.getTextChannel());


    }

    public static void sendTime(int tri, TextChannel tc) {
        Calendar cal = Calendar.getInstance();
        String sb = "" +
                cal.get(Calendar.MILLISECOND) +
                ":" +
                cal.get(Calendar.SECOND) +
                ":" +
                cal.get(Calendar.MINUTE) +
                ":" +
                cal.get(Calendar.HOUR_OF_DAY) +
                ":" +
                cal.get(Calendar.DAY_OF_MONTH) +
                ":" +
                (cal.get(Calendar.MONTH) + 1) +
                ":" +
                cal.get(Calendar.YEAR);
        switch (tri) {
            case 1:
                Ping.one = sb;
                id1 = tc.sendMessage("pingmessage1one").complete().getIdLong();
                break;
            case 2:
                Ping.two = sb;
                id2 = tc.sendMessage("pingmessage2two").complete().getIdLong();
                break;
            case 3:
                Ping.three = sb;
                id3 = tc.sendMessage("pingmessage3three").complete().getIdLong();
                break;
            default:
                break;
        }
    }

    public static void getTime(int tri) {

        Calendar cal = Calendar.getInstance();
        String[] now = new String[]{};
        switch (tri) {
            case 1:
                now = Ping.one.split(":");
                break;
            case 2:
                now = Ping.two.split(":");
                break;
            case 3:
                now = Ping.three.split(":");
                break;
            default:
                break;
        }


        int msec = cal.get(Calendar.MILLISECOND) - Integer.parseInt(now[0]);
        if (msec < 0) {
            msec += 1000;
            now[1] = String.valueOf(Integer.parseInt(now[1]) - 1);
        }

        int sec = cal.get(Calendar.SECOND) - Integer.parseInt(now[1]);
        if (sec < 0) {
            sec += 60;
            now[2] = String.valueOf(Integer.parseInt(now[2]) - 1);
        }

        int min = cal.get(Calendar.MINUTE) - Integer.parseInt(now[2]);
        if (min < 0) {
            min += 60;
            now[3] = String.valueOf(Integer.parseInt(now[3]) - 1);
        }

        int hour = cal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(now[3]);
        if (hour < 0) {
            hour += 24;
            now[4] = String.valueOf(Integer.parseInt(now[4]) - 1);
        }

        int day = cal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(now[4]);
        if (day < 0) {
            YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(now[5]), Integer.parseInt(now[4]));
            int daysInMonth = yearMonthObject.lengthOfMonth();
            day += daysInMonth;
            now[5] = String.valueOf(Integer.parseInt(now[5]) - 1);
        }

        int month = (cal.get(Calendar.MONTH) + 1) - Integer.parseInt(now[5]);
        if (month < 0) {
            month += 12;
            now[6] = String.valueOf(Integer.parseInt(now[6]) - 1);
        }

        int years = cal.get(Calendar.YEAR) - Integer.parseInt(now[6]);

        Ping.time.add(msec + sec * 100 + min * 6000 + hour * 360000 + day * 8640000);

    }

    public static void sendMessage(TextChannel channel) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setAuthor("Pingtest complete");
        eb.addField("Time to calculate", ((time.get(0) + time.get(1) + time.get(2)) / 3) + " ms", false);
        eb.addField("Time to notify", String.valueOf(channel.getJDA().getGatewayPing()), false);
        eb.addField("Threads", String.valueOf(activeThreads), false);
        channel.sendMessage(eb.build()).queue();
        channel.deleteMessageById(id1).queue();
        channel.deleteMessageById(id2).queue();
        channel.deleteMessageById(id3).queue();
        activeThreads -= Main.manager.getGuilds().size();

    }

    public static void addThread() {
        activeThreads++;
    }

    public static void removeThread() {
        activeThreads--;
    }

}
