package com.example.thoughtworksassignment;

/**
 * Created by Sisir on 06-Jun-15.
 */
public class CartItem {
    private Item item;
    private int quantity = 0;
    private double price = 0.00;

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public CartItem(Item item,int quantity){
        this.item = item;
        this.quantity = quantity;
        price = calculatePrice(this.item,this.quantity);
    }

    public double updateQuantity(int quantity){
        this.quantity += quantity;
        price = calculatePrice(item,this.quantity);
        return price;
    }

    private double calculatePrice(Item item, int quantity) {
        return item.getPrice()*quantity;
    }
}
