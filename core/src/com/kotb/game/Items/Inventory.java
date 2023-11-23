package com.kotb.game.Items;


import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int maxItems;

    public Inventory(int maxItems) {
        this.items = new ArrayList<>();
        this.maxItems = maxItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        if (items.size() < maxItems) {
            items.add(item);
            System.out.println("Item added to inventory: " + item.getName());
        } else {
            System.out.println("Inventory is full. Cannot add item: " + item.getName());
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
        System.out.println("Item removed from inventory: " + item.getName());
    }

    // Ajoute d'autres méthodes selon les besoins, par exemple, pour récupérer des armes spécifiques, des artefacts, etc.
}
