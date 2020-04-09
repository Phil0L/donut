package com.pl.discord.objects;

import com.pl.discord.objects.items.Clothing;
import com.pl.discord.objects.items.Item;
import com.pl.discord.objects.locations.Location;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;
import java.util.Arrays;

public class DonutUser {
    private String name;
    private String id;
    private int level;
    private long coins;
    private String startedVoice;
    private boolean mining;
    private Location currentLocation;
    private ArrayList<Item> inventory;
    private Item head;
    private Item upper;
    private Item trousers;
    private Item shoes;


    public DonutUser(Member user) {
        this.name = user.getEffectiveName();
        this.id = user.getId();
        this.level = 1;
        this.coins = 0;
        this.startedVoice = "00:00:00:00:00:0000";
        this.mining = false;
        this.inventory = new ArrayList<>();
        this.currentLocation = null;
        this.head = new Item("None", ":x:");
        this.upper = new Item("None", ":x:");
        this.trousers = new Item("None", ":x:");
        this.shoes = new Item("None", ":x:");
    }

    // default Konstuktor for JSON
    public DonutUser() {
    }

    public void enterLocation(Location location) {
        this.currentLocation = location;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void joinedVoice(String s) {
        this.startedVoice = s;
    }

    public void setMining(boolean b) {
        this.mining = b;
    }

    public void setLook(Clothing item, int pos) {
        switch (pos) {
            case 0:
                head = item;
                break;
            case 1:
                upper = item;
                break;
            case 2:
                trousers = item;
                break;
            case 3:
                shoes = item;
                break;
        }
    }

    public void moveToInv(int pos) {
        switch (pos) {
            case 0:
                inventory.add(head);
                head = new Item("None", ":x:");
                break;
            case 1:
                inventory.add(upper);
                upper = new Item("None", ":x:");
                break;
            case 2:
                inventory.add(trousers);
                trousers = new Item("None", ":x:");
                break;
            case 3:
                inventory.add(shoes);
                shoes = new Item("None", ":x:");
                break;
        }
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

    public void combineItems() {
        ArrayList<Item> items = this.inventory;
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size() - 1; i++) {
                for (int j = i + 1; j < items.size(); j++) {
                    if (items.get(i).name.equals(items.get(j).name) && items.get(i).isStackable() && items.get(j).isStackable()) {
                        items.get(i).stack = items.get(i).stack + items.get(j).stack;
                        items.remove(j);
                    }
                }
            }
        }
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

    public Location currentLocation() {
        return currentLocation;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public Item getHead() {
        return head;
    }

    public Item getUpper() {
        return upper;
    }

    public Item getTrousers() {
        return trousers;
    }

    public Item getShoes() {
        return shoes;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString() {
        return "\nuser:[" +
                "name=" + name +
                ",level=" + level +
                ",coins=" + coins +
                ",look:[head=" + head + ",upper=" + upper + ",trousers=" + trousers + ",shoes=" + shoes +
                "],inventory=" + Arrays.toString(this.inventory.toArray()) +
                "]";
    }
}
