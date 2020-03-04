package com.pl.discord.objects;

import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class DonutUser {
    private String name;
    private int level;
    //private int donuts;
    private long coins;
    private String startedVoice;
    private boolean mining;

    private Location currentLocation;
    private ArrayList<Item> inventory;


    public DonutUser(Member user) {
        this.name = user.getEffectiveName();
        this.level = 1;
        this.coins = 0;
        this.startedVoice = "00:00:00:00:00:0000";
        this.mining = false;
        this.inventory = new ArrayList<>();
        this.currentLocation = null;

    }

    // default Konstuktor for JSON
    public DonutUser() {
    }

    public void enterLocation(Location location){
        this.currentLocation = location;
    }

    public void addItem(Item item){
        this.inventory.add(item);
    }

    public void joinedVoice(String s) {
        this.startedVoice = s;
    }

    public void setMining(boolean b) {
        this.mining = b;
    }

    public void removeCoins(long coins) {
        this.coins -= coins;
        if (this.coins < 0)
            this.coins = 0;
    }

    public void addCoins(int coins) {
        this.coins += coins;
        if (this.coins < 0)
            this.coins = 0;
    }

    // Getter Methods
    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
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
        return "\nuser:[" +
                "name=" + name +
                ",level=" + level +
                ",items=" + "" +
                ",coins=" + coins +
                "]";
    }
}
