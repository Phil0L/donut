package com.pl.discord.objects.locations;

import com.pl.discord.objects.items.Item;
import com.pl.discord.objects.items.Clothing;

import java.util.ArrayList;
import java.util.Collections;

public class ClothingStore extends Location {
    private static int POS_HEAD = 0;
    private static int POS_TOP = 1;
    private static int POS_LEG = 2;
    private static int POS_SHOE = 3;

    public ClothingStore(){
        super.name = "Clothing store";
        super.command = "%location clothing";
        super.emoji = ":person_standing:";
        super.opens = 8;
        super.closes = 18;
        super.restockAt = 8;
        super.possible = new ArrayList<>();

        super.possible.add(new Clothing("Cylinder", ":tophat:", 30, 75, POS_HEAD));
        super.possible.add(new Clothing("Cap", ":billed_cap:", 40, 30, POS_HEAD));
        super.possible.add(new Clothing("Sun hat", ":womans_hat:", 30, 45, POS_HEAD));
        super.possible.add(new Clothing("Crown", ":crown:", 15, 200, POS_HEAD));
        super.possible.add(new Clothing("Sunglasses", ":dark_sunglasses:", 20, 50, POS_HEAD));

        super.possible.add(new Clothing("Coat", ":coat:", 40, 100, POS_TOP));
        super.possible.add(new Clothing("Lab Coat", ":lab_coat:", 20, 70, POS_TOP));
        super.possible.add(new Clothing("Safety Vest", ":safety_vest:", 30, 40, POS_TOP));
        super.possible.add(new Clothing("T-Shirt", ":shirt:", 60, 20, POS_TOP));
        super.possible.add(new Clothing("Dress", ":dress:", 35, 130, POS_TOP, true));
        super.possible.add(new Clothing("Bikini", ":bikini:", 30, 70, POS_TOP, true));
        super.possible.add(new Clothing("Swim suit", "::swim_suit", 25, 80, POS_TOP, true));

        super.possible.add(new Clothing("Jeans", ":jeans:", 50, 40, POS_LEG));
        super.possible.add(new Clothing("Shorts", ":shorts:", 40, 35, POS_LEG));
        super.possible.add(new Clothing("Trunks", ":shorts:", 25, 30, POS_LEG));
        super.possible.add(new Clothing("Underpants", ":briefs:", 45, 15, POS_LEG));

        super.possible.add(new Clothing("High heels", ":high_heel:", 25, 150, POS_SHOE));
        super.possible.add(new Clothing("Sneaker", ":athletic_shoe:", 50, 100, POS_SHOE));
        super.possible.add(new Clothing("Shoe", ":mans_shoe:", 40, 80, POS_SHOE));
        super.possible.add(new Clothing("Hiking Boots", ":hiking_boot:", 20, 90, POS_SHOE));
        super.possible.add(new Clothing("Socks", ":socks:", 50, 20, POS_SHOE));

        super.stock = new ArrayList<>();

    }

    @Override
    public void restock() {
        Collections.shuffle(super.possible);
        int itemsToStock = ((int)(Math.random() * 3 + 3) + restockAt) - stock.size();
        for (Item item : super.possible){
            if ((int)(Math.random() * 100) <= item.rarity){
                super.stock.add(item);
                itemsToStock--;
            }
            if (itemsToStock <= 0)
                break;
        }
        if (super.stock.size() < super.restockAt)
            restock();
    }
}
