package com.pl.discord;

import net.dv8tion.jda.api.entities.Member;

public class DonutUser {
    private String name;
    private int level;
    private int donuts;
    private long coins;
    private String startedVoice;
    private boolean mining;

    public DonutUser(Member user){
        this.name = user.getEffectiveName();
        this.level = 1;
        this.donuts = 0;
        this.coins = 0;
        this.startedVoice = "00:00:00:00:00:0000";
        this.mining = false;

    }

    public DonutUser() {
    }

    public void joinedVoice(String s){
        this.startedVoice = s;
    }

    public void setMining(boolean b){
        this.mining = b;
    }

    public void addCoins(long coins) {
        this.coins += coins;
    }

    public void addDonut(){
        this.donuts++;
    }

    public void removeCoins(long coins) {
        this.coins -= coins;
        if (this.coins < 0)
            this.coins = 0;
    }

    public void addCoins(int coins){
        this.coins += coins;
        if (this.coins < 0)
            this.coins = 0;
    }

    public void removeDonuts(int donuts){
        this.donuts -= donuts;
        if (this.donuts < 0)
            this.donuts = 0;
    }

    public void addDonuts(int donuts){
        this.donuts += donuts;
        if (this.donuts < 0)
            this.donuts = 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getDonuts() {
        return donuts;
    }

    public long getCoins() {
        return coins;
    }

    public String getStartedVoice() {
        return startedVoice;
    }

    public boolean isMining() {
        return mining;
    }

    @Override
    public String toString() {
        return "DonutUser{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", donuts=" + donuts +
                ", coins=" + coins +
                '}';
    }
}
