package com.pl.discord.objects.locations;

import com.pl.discord.objects.items.Item;

import java.util.ArrayList;
import java.util.Collections;

public class Supermarket extends Location {

    public Supermarket(){
        super.name = "Supermarket";
        super.command = "%location supermarket";
        super.emoji = ":apple:";
        super.opens = 6;
        super.closes = 20;
        super.restockAt = 12;
        super.possible = new ArrayList<>();

//        fruits and vegetables
        super.possible.add(new Item("Apple", ":apple:", 30, 10));
        super.possible.add(new Item("Pear", ":pear:", 30, 10));
        super.possible.add(new Item("Orange", ":tangerine:", 20, 15));
        super.possible.add(new Item("Lemon", ":lemon:", 20, 15));
        super.possible.add(new Item("Banana", ":banana:", 30, 30));
        super.possible.add(new Item("Strawberry", ":strawberry:", 20, 5));
        super.possible.add(new Item("Cherries", ":cherries:", 20, 10));
        super.possible.add(new Item("Pineapple", ":pineapple:", 15, 20));
        super.possible.add(new Item("Chili" ,":hot_pepper:", 10, 15));
        super.possible.add(new Item("Onion", ":onion:", 10, 15));
        super.possible.add(new Item("Garlic", ":garlic:", 10, 15));
        super.possible.add(new Item("Potato", ":potato:", 30, 10));
        super.possible.add(new Item("Peanut", ":peanuts:", 20, 5));
        super.possible.add(new Item("Lettuce", ":leafy_green:", 25, 10));

//        drinks
        super.possible.add(new Item("Ice Cubes", ":ice_cube:", 20, 10));
        super.possible.add(new Item("Beer", ":beer:", 35, 5));
        super.possible.add(new Item("Champagne", ":champagne:", 10, 40));


//        general
        super.possible.add(new Item("Cheese", ":cheese:", 25, 20));
        super.possible.add(new Item("Egg", ":egg:", 25, 10));
        super.possible.add(new Item("Bacon", ":bacon:", 20, 15));
        super.possible.add(new Item("Rice", ":rice:", 30, 10));
        super.possible.add(new Item("Steak", ":cut_of_meat:", 25, 50));
        super.possible.add(new Item("Shrimps", ":fried_shrimp:", 20, 20));
        super.possible.add(new Item("Butter", ":butter:", 25, 15));

//        fast food
        super.possible.add(new Item("Canned Food", ":canned_food:", 20, 30));
        super.possible.add(new Item("Spareribs", ":meat_on_bone:", 25, 40));
        super.possible.add(new Item("Fries", ":fries:", 20, 35));
        super.possible.add(new Item("Pizza", ":pizza:", 25, 40));
        super.possible.add(new Item("Sandwich", ":sandwich:", 25, 25));


//        bakery stuff
        super.possible.add(new Item("Bread", ":bread:", 25, 15));
        super.possible.add(new Item("Croissant", ":croissant:", 40, 25));
        super.possible.add(new Item("Baguette", ":baguette_bread:", 30, 45));
        super.possible.add(new Item("Pretzel", ":pretzel:", 20, 20));
        super.possible.add(new Item("Coffee" , ":coffee:", 15, 10));
        super.possible.add(new Item("Casual Donut", 40, 20));

//        sweets
        super.possible.add(new Item("Lolli", ":lollipop:", 30, 5));
        super.possible.add(new Item("Bonbon", ":candy:", 25, 5));
        super.possible.add(new Item("Chocolate", ":chocolate_bar:", 20, 10));
        super.possible.add(new Item("Cookie", ":cookie:", 20, 15));
        super.possible.add(new Item("Muffin", ":cupcake:", 20, 25));

        super.stock = new ArrayList<>();
    }

    @Override
    public void restock() {
        Collections.shuffle(super.possible);
        int itemsToStock = ((int)(Math.random() * 3 + 3) + restockAt) - stock.size();
        for (Item item : super.possible){
            if ((int)(Math.random() * 100) <= item.rarity){
                super.stock.add(new Item((int)(Math.random() * (item.rarity / 4))+1, item));
                itemsToStock--;
            }
            if (itemsToStock <= 0)
                break;
        }
        super.combineItems(super.stock);
        if (super.stock.size() < super.restockAt)
            restock();
    }
}
