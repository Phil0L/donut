package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.objects.DonutServer;
import com.pl.discord.objects.DonutUser;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Enter extends Command {

    public Enter() {
        super.name = "sign";
        super.aliases = new String[]{"enter", "signup"};
        super.category = new Category("Donut");
        super.arguments = "[user]";
        super.help = "Enter the world of Donut";
    }

    @Override
    protected void execute(CommandEvent event) {

        if (Main.getServer(event.getGuild()) == -1) {
            Main.server.add(new DonutServer(event.getGuild()));
            if (event.getGuild().getRolesByName("DonutMember", true).isEmpty())
                event.getGuild().createRole().setName("DonutMember").queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN).setTitle("Server entered");
            event.reply(eb.build());
            if (event.getGuild().getEmotes().size() == 50) {
                event.reply(new EmbedBuilder().setTitle("Not enogth space to create emojis. If you dont want to add emojis ignore this messgage").setColor(Color.RED).build());
            } else {
                try {
                    int i = 0;
                    int k = 0;
                    if (event.getGuild().getEmotesByName("mini_doughnuts", true).isEmpty()) {
                        event.getGuild().createEmote("mini_doughnuts", Icon.from(new File("./data/media/mini_doughnuts.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("red_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("red_doughnut", Icon.from(new File("./data/media/red_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("blue_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("blue_doughnut", Icon.from(new File("./data/media/blue_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("rainbow_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("rainbow_doughnut", Icon.from(new File("./data/media/rainbow_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("zebra_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("zebra_doughnut", Icon.from(new File("./data/media/zebra_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("white_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("white_doughnut", Icon.from(new File("./data/media/white_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    if (event.getGuild().getEmotesByName("pink_doughnut", true).isEmpty()) {
                        event.getGuild().createEmote("pink_doughnut", Icon.from(new File("./data/media/pink_doughnut.png")), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                        i++;
                    } else k++;
                    event.reply(new EmbedBuilder().setColor(Color.GREEN).setTitle(i + " Emojis created," + (7 - (k + i)) + " missing").build());
                    int j = Main.getServer(event.getGuild());
                    if (k + i == 7)
                        Main.server.get(j).setEmojis(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        int i = Main.getServer(event.getGuild());
        if (event.getArgs().isEmpty()) {
            if (Main.server.get(i).getMember(event.getMember()) == -1) {
                Main.server.get(i).add(new DonutUser(event.getMember()));
                Main.server.get(i).save(event.getGuild());
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                event.getTextChannel().sendMessage("Welcome" + event.getMember().getEffectiveName()).addFile(new File("./data/media/Banner.png")).queue();

            } else {
                eb.setColor(Color.RED);
                eb.setTitle("You have already entered");
                event.reply(eb.build());
            }

        } else if (event.getArgs().split(" ").length > 1 && event.getArgs().split(" ")[1].equals("force")) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            Main.server.get(i).add(new DonutUser(member));
            Main.server.get(i).save(event.getGuild());
            event.getGuild().addRoleToMember(member, event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
            event.getTextChannel().sendMessage("Welcome" + member.getEffectiveName()).addFile(new File("./data/media/Banner.png")).queue();

        } else if (event.getArgs().equals("force")) {
            Main.server.get(i).add(new DonutUser(event.getMember()));
            Main.server.get(i).save(event.getGuild());
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
            event.getTextChannel().sendMessage("Welcome" + event.getMember().getEffectiveName()).addFile(new File("./data/media/Banner.png")).queue();

        } else {
            Member member = event.getMessage().getMentionedMembers().get(0);
            if (Main.server.get(i).getMember(member) == -1) {
                Main.server.get(i).add(new DonutUser(member));
                Main.server.get(i).save(event.getGuild());
                event.getGuild().addRoleToMember(member, event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                event.getTextChannel().sendMessage("Welcome" + member.getEffectiveName()).addFile(new File("./data/media/Banner.png")).queue();

            } else {
                eb.setColor(Color.RED);
                eb.setTitle("This user already entered");
                event.reply(eb.build());
            }
        }
    }
}
