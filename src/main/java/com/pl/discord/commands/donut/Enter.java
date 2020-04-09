package com.pl.discord.commands.donut;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.commands.util.Welcome;
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
        super.help = "" +
                "%enter : the user can now use all commands from Donut Kingdom\n" +
                "%enter @[username] : the mentioned user can now use all commands from Donut Kingdom\n" +
                "%enter force : enter no matter what (only for administrator)\n" +
                "%enter @[username] force : enters the user no matter what (only for administrator)";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Enter");

        if (Main.getDonutServer(event.getGuild()) == null) {
            Main.server.add(new DonutServer(event.getGuild()));
            if (event.getGuild().getRolesByName("DonutMember", true).isEmpty())
                event.getGuild().createRole().setName("DonutMember").queue();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.GREEN).setTitle("Server entered");
            event.reply(eb.build());
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        if (event.getArgs().isEmpty()) {
            if (Main.getDonutUser(event.getGuild(), event.getMember()) == null) {
                Main.getDonutServer(event.getGuild()).add(new DonutUser(event.getMember()));
                Main.getDonutServer(event.getGuild()).save(DonutServer.SAVE_USER);
                if (!event.getGuild().getRolesByName("DonutMember", true).isEmpty() && Main.getDonutServer(event.getGuild()).settings().isAssignRole())
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                Welcome.enter(event, event.getMember().getEffectiveName(), event.getMember());

            } else {
                eb.setColor(Color.RED);
                eb.setTitle("You have already entered");
                event.reply(eb.build());
            }

        } else if (event.getArgs().split(" ").length > 1 && event.getArgs().split(" ")[1].equals("force")) {
            Member member = event.getMessage().getMentionedMembers().get(0);
            Main.getDonutServer(event.getGuild()).add(new DonutUser(member));
            Main.getDonutServer(event.getGuild()).save(DonutServer.SAVE_USER);
            if (!event.getGuild().getRolesByName("DonutMember", true).isEmpty() && Main.getDonutServer(event.getGuild()).settings().isAssignRole())
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
            Welcome.enter(event, member.getEffectiveName(), member);

        } else if (event.getArgs().equals("force")) {
            Main.getDonutServer(event.getGuild()).add(new DonutUser(event.getMember()));
            Main.getDonutServer(event.getGuild()).save(DonutServer.SAVE_USER);
            if (!event.getGuild().getRolesByName("DonutMember", true).isEmpty() && Main.getDonutServer(event.getGuild()).settings().isAssignRole())
                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
            Welcome.enter(event, event.getMember().getEffectiveName(), event.getMember());

        } else {
            Member member = event.getMessage().getMentionedMembers().get(0);
            if (Main.getDonutUser(event.getGuild(), event.getMember()) == null) {
                Main.getDonutServer(event.getGuild()).add(new DonutUser(member));
                Main.getDonutServer(event.getGuild()).save(DonutServer.SAVE_USER);
                if (!event.getGuild().getRolesByName("DonutMember", true).isEmpty() && Main.getDonutServer(event.getGuild()).settings().isAssignRole())
                    event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                event.getGuild().addRoleToMember(member, event.getGuild().getRolesByName("DonutMember", true).get(0)).queue();
                Welcome.enter(event, member.getEffectiveName(), member);

            } else {
                eb.setColor(Color.RED);
                eb.setTitle("This user already entered");
                event.reply(eb.build());
            }
        }
    }
}
