package com.pl.discord.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Calendar;

public class ServerSettings {
    //statics:
    public static int PLAYER_SPOTIFY = 1;
    public static int PLAYER_YOUTUBE = 0;

    public static int SAVE_SETTINGS = 1;

    boolean autoJoin;
    boolean autoLeave;
    boolean autoRec;
    int defaultPlayer;
    String botChannel;
    boolean assignRole;
    String backup;
    boolean useraccess;


    public ServerSettings(Guild guild){
        this.autoJoin = false;
        this.autoLeave = true;
        this.autoRec = false;
        this.defaultPlayer = PLAYER_YOUTUBE;
        this.botChannel = guild.getTextChannels().get(0).getId();
        this.assignRole = true;
        this.backup = "None";
        this.useraccess = false;

    }

    //default Konstruktor for JSON
    public ServerSettings() {
    }

    public boolean isAutoJoin() {
        return autoJoin;
    }

    public boolean isAutoLeave() {
        return autoLeave;
    }

    public boolean isAutoRec() {
        return autoRec;
    }

    public int getDefaultPlayer() {
        return defaultPlayer;
    }

    public String getBotChannel(){
        return botChannel;
    }

    public boolean isAssignRole() {
        return assignRole;
    }

    public String getBackup() {
        return backup;
    }

    @JsonIgnore
    public String getBotChannelName(Guild guild){
        return guild.getTextChannelById(this.botChannel).getName();
    }

    @JsonIgnore
    public TextChannel getBotchannel(Guild guild){
        return guild.getTextChannelById(this.botChannel);
    }

    public void setAutoJoin(boolean autoJoin) {
        this.autoJoin = autoJoin;
    }

    public void setAutoLeave(boolean autoLeave) {
        this.autoLeave = autoLeave;
    }

    public void setAutoRec(boolean autoRec) {
        this.autoRec = autoRec;
    }

    public void setDefaultPlayer(int defaultPlayer) {
        this.defaultPlayer = defaultPlayer;
    }

    public void setBotChannel(String botChannel) {
        this.botChannel = botChannel;
    }

    public void setBotchannel(TextChannel botchannel) {
        this.botChannel = botchannel.getId();
    }

    public void setAssignRole(boolean assignRole) {
        this.assignRole = assignRole;
    }

    public void setBackup(){
        Calendar calendar = Calendar.getInstance();
        this.backup = "" + calendar.get(Calendar.DAY_OF_MONTH) + ". " + (calendar.get(Calendar.MONTH) + 1) + ". " + calendar.get(Calendar.YEAR);
    }

    public boolean isUseraccess() {
        return useraccess;
    }

    public void setUseraccess(boolean useraccess) {
        this.useraccess = useraccess;
    }

    @Override
    public String toString() {
        return "\nsettings:[" +
                "autojoin=" + autoJoin +
                ",autorec=" + autoRec +
                ",autoleavve=" + autoLeave +
                ",player=" + defaultPlayer +
                ",channel=" + botChannel + "]";

    }
}
