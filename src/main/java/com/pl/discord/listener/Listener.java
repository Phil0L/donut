package com.pl.discord.listener;

import com.pl.discord.Main;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.time.YearMonth;
import java.util.Calendar;

public class Listener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        //add coins for sending messages
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());
            if (event.getMessage().getContentRaw().startsWith("%")) {
                if (event.getMessage().getContentRaw().startsWith("%mine")) {

                } else {
                    //command
                    int value = (int) (Math.random() * 3);
                    if (Main.server.get(i).getMember(event.getMember()) != -1)
                        Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).addCoins(value);
                    Main.server.get(i).save(event.getGuild());
                }
            } else {
                //normal
                int value = (int) (Math.random() * 6);
                if (Main.server.get(i).getMember(event.getMember()) != -1)
                    Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).addCoins(value);
                Main.server.get(i).save(event.getGuild());
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        System.out.println(event.getMessage().getContentRaw());
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());
            Calendar cal = Calendar.getInstance();
            if (Main.server.get(i).getMember(event.getMember()) != -1) {
                String sb = "" + cal.get(Calendar.SECOND) +
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
                Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).joinedVoice(sb);
                Main.server.get(i).save(event.getGuild());
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());

            Calendar cal = Calendar.getInstance();
            if (Main.server.get(i).getMember(event.getMember()) != -1 && !Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).getStartedVoice().equals("00:00:00:00:00:0000")) {
                String[] now = Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).getStartedVoice().split(":");

                int sec = cal.get(Calendar.SECOND) - Integer.parseInt(now[0]);
                if (sec < 0) {
                    sec += 60;
                    now[1] = String.valueOf(Integer.parseInt(now[1]) - 1);
                }

                int min = cal.get(Calendar.MINUTE) - Integer.parseInt(now[1]);
                if (min < 0) {
                    min += 60;
                    now[2] = String.valueOf(Integer.parseInt(now[2]) - 1);
                }

                int hour = cal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(now[2]);
                if (hour < 0) {
                    hour += 24;
                    now[3] = String.valueOf(Integer.parseInt(now[3]) - 1);
                }

                int day = cal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(now[3]);
                if (day < 0) {
                    YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(now[5]), Integer.parseInt(now[4]));
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    day += daysInMonth;
                    now[4] = String.valueOf(Integer.parseInt(now[4]) - 1);
                }

                int month = (cal.get(Calendar.MONTH) + 1) - Integer.parseInt(now[4]);
                if (month < 0) {
                    month += 12;
                    now[5] = String.valueOf(Integer.parseInt(now[5]) - 1);
                }

                int years = cal.get(Calendar.YEAR) - Integer.parseInt(now[5]);

                StringBuilder sb = new StringBuilder("");
                sb.append(event.getMember().getEffectiveName());
                sb.append(" has been **");
                if (years != 0) {
                    sb.append(years).append(" years, ");
                    sb.append(month).append(" months, ");
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (month != 0) {
                    sb.append(month).append(" months, ");
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (day != 0) {
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (hour != 0) {
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (min != 0) {
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else {
                    sb.append(sec).append(" seconds");
                }
                sb.append("** in ").append(event.getChannelLeft().getName()).append(".");

                int coins = (int) (hour * 12 + min * 0.8 + sec * 0.1);
                if (coins < 2)
                    sb.append(" He mined ").append(coins).append(" coin in this time");
                else
                    sb.append(" He mined ").append(coins).append(" coins in this time");

                Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).joinedVoice("00:00:00:00:00:0000");

                event.getGuild().getTextChannels().get(1).sendMessage(sb.toString()).queue();

                //TODO save added
            }
        } else {

        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        if (Main.getServer(event.getGuild()) != -1) {
            int i = Main.getServer(event.getGuild());

            Calendar cal = Calendar.getInstance();
            if (Main.server.get(i).getMember(event.getMember()) != -1 && !Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).getStartedVoice().equals("00:00:00:00:00:0000")) {
                String[] now = Main.server.get(i).getUser().get(Main.server.get(i).getMember(event.getMember())).getStartedVoice().split(":");

                int sec = cal.get(Calendar.SECOND) - Integer.parseInt(now[0]);
                if (sec < 0) {
                    sec += 60;
                    now[1] = String.valueOf(Integer.parseInt(now[1]) - 1);
                }

                int min = cal.get(Calendar.MINUTE) - Integer.parseInt(now[1]);
                if (min < 0) {
                    min += 60;
                    now[2] = String.valueOf(Integer.parseInt(now[2]) - 1);
                }

                int hour = cal.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(now[2]);
                if (hour < 0) {
                    hour += 24;
                    now[3] = String.valueOf(Integer.parseInt(now[3]) - 1);
                }

                int day = cal.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(now[3]);
                if (day < 0) {
                    YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(now[5]), Integer.parseInt(now[4]));
                    int daysInMonth = yearMonthObject.lengthOfMonth();
                    day += daysInMonth;
                    now[4] = String.valueOf(Integer.parseInt(now[4]) - 1);
                }

                int month = (cal.get(Calendar.MONTH) + 1) - Integer.parseInt(now[4]);
                if (month < 0) {
                    month += 12;
                    now[5] = String.valueOf(Integer.parseInt(now[5]) - 1);
                }

                int years = cal.get(Calendar.YEAR) - Integer.parseInt(now[5]);

                StringBuilder sb = new StringBuilder("");
                sb.append(event.getMember().getEffectiveName());
                sb.append(" has been **");
                if (years != 0) {
                    sb.append(years).append(" years, ");
                    sb.append(month).append(" months, ");
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (month != 0) {
                    sb.append(month).append(" months, ");
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (day != 0) {
                    sb.append(day).append(" days, ");
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (hour != 0) {
                    sb.append(hour).append(" hours, ");
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else if (min != 0) {
                    sb.append(min).append(" minutes, ");
                    sb.append(sec).append(" seconds");
                } else {
                    sb.append(sec).append(" seconds");
                }
                sb.append("** in ").append(event.getChannelLeft().getName()).append(".");

                int coins = (int) (hour * 12 + min * 0.8 + sec * 0.1);
                if (coins < 2)
                    sb.append(" He mined ").append(coins).append(" coin in this time");
                else
                    sb.append(" He mined ").append(coins).append(" coins in this time");

                event.getGuild().getTextChannels().get(1).sendMessage(sb.toString()).queue();


                if (Main.getServer(event.getGuild()) != -1) {
                    int j = Main.getServer(event.getGuild());

                    String sb2 = "" + cal.get(Calendar.SECOND) +
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
                    Main.server.get(j).getUser().get(Main.server.get(j).getMember(event.getMember())).joinedVoice(sb2);
                    Main.server.get(j).save(event.getGuild());
                }
            }
        }
    }
}
