package com.example.thoughtworksassignment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sisir on 06-Jun-15.
 */
public class Globals extends Application{
    public static HashMap<Integer,CartItem> cartItems  = new HashMap<Integer,CartItem>();
    public static HashMap<String,List<Item>> itemList;
    public static List<String> titleList;
    public static SharedPreferences sharedPreferences;
    public static DataHandler dataHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        dataHandler = new DataHandler(this);
        titleList = dataHandler.getTitleList();
        itemList = dataHandler.getItemList();


    }


    public static void populateCart(Context context){
        try {
            sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
            if (!sharedPreferences.getAll().isEmpty()) {


                List<Item> items = new ArrayList<Item>();

                Collection<List<Item>> lists = itemList.values();

                for (List<Item> list : lists) {
                    items.addAll(list);
                }


                Map<String, ?> integerMap = sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : integerMap.entrySet()) {
                    int id = Integer.valueOf(entry.getKey());
                    int quantity = Integer.valueOf(entry.getValue().toString());
                    for (Item item : items) {
                        if (item.getId() == id) {
                            CartItem cartItem = new CartItem(item, quantity);
                            Globals.cartItems.put(id, cartItem);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveCart(Context context){
        try {
                sharedPreferences = context.getSharedPreferences("cart", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Set<Map.Entry<Integer, CartItem>> entries = Globals.cartItems.entrySet();

                for (Map.Entry<Integer, CartItem> entry : entries) {

                    sharedPreferences.edit().putInt(entry.getKey().toString(), entry.getValue().getQuantity()).commit();

                }

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }



    public static double totalPrice(List<CartItem> items){

        double totalPrice = 0.0;
        for(CartItem item : items){
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
