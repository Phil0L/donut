package com.pl.discord.objects.locations;

import com.pl.discord.objects.items.Item;
import net.dv8tion.jda.api.entities.Guild;

import java.util.ArrayList;
import java.util.Calendar;


public abstract class Location {
    public String name;
    public String command;
    public String emoji;
    public int opens;
    public int closes;
    public int restockAt;
    public ArrayList<Item> stock;
    public ArrayList<Item> possible;

    //default Konstruktor
    public Location(){

    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public long getOpens() {
        return opens;
    }

    public long getCloses() {
        return closes;
    }

    public int getRestockAt() {
        return restockAt;
    }

    public ArrayList<Item> getStock() {
        return stock;
    }

    public ArrayList<Item> getPossible() {
        return possible;
    }

    public String getCommand(){
        if (open())
            return this.command;
        return "[closed] opens : " + getTime(opens);
    }

    public boolean open(){
        Calendar cal = Calendar.getInstance();
        int now = (int) (cal.get(Calendar.MINUTE) / 2.5);
        if (now > opens && now < closes)
            return true;
        return opens > closes && (now < closes || now > opens);
    }

    protected void combineItems(ArrayList<Item> items){
        if (!items.isEmpty()){
            for (int i = 0; i < items.size() -1; i++){
                for (int j = i + 1; j < items.size(); j++){
                    if (items.get(i).name.equals(items.get(j).name)){
                        items.get(i).stack = items.get(i).stack + items.get(j).stack;
                        items.remove(j);
                    }
                }
            }
        }
    }

    public void checkForEmpty(){
        for (int i = 0; i < this.stock.size(); i++){
            if (this.stock.get(i).stack == 0)
                this.stock.remove(i);
        }
    }

    private String getTime(){
        Calendar cal = Calendar.getInstance();
        int now = (int) (cal.get(Calendar.MINUTE) / 2.5);
        return (now > 12 ? String.valueOf(now-12) : String.valueOf(now));

    }

    private String getTime(int time){
        return (time > 12 ? String.valueOf(time-12) : String.valueOf(time)) + " o'clock "  + (time > 12 ? "pm" : "am");
    }

    private String getPeriod(){
        Calendar cal = Calendar.getInstance();
        int now = (int) (cal.get(Calendar.MINUTE) / 2.5);
        return (now > 12 ? "pm" : "am");
    }

    public abstract void restock();


}
