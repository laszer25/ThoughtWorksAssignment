package com.example.thoughtworksassignment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sisir on 06-Jun-15.
 */
public class DataHandler {
    List<String> titleList;
    HashMap<String, List<Item>> itemList;
    //HashMap<String,List<Item>> listHashMap;
    Context context;

    public Item getItem(int titlePosition,int itemPosition){
        Item item = itemList.get(titleList.get(titlePosition)).get(itemPosition);
        return  item;
    }
    public DataHandler(){
            itemList = new HashMap<String,List<Item>>();
    }

    public DataHandler(Context context){
        this.context =  context;
        itemList = new HashMap<String,List<Item>>();
    }

    public List<String> getTitleList(){
        titleList = new ArrayList<String>();
        titleList.add("Electronics");
        titleList.add("Furniture");
        return  titleList;
    }

    public HashMap<String, List<Item>> getItemList(){

        for(int i = 0; i < titleList.size();i++){
            List<Item>  items = new ArrayList<Item>();



            Item item;
            if(titleList.get(i)=="Electronics"){

                item = new Item(1,"Microwave Oven",4000.00,titleList.get(i), BitmapFactory.decodeResource(context.getResources(),R.drawable.microwave_oven));
                items.add(item);

                item = new Item(2,"Television",7000.00,titleList.get(i),BitmapFactory.decodeResource(context.getResources(),R.drawable.television));
                items.add(item);


                item = new Item(3,"Vacuum Cleaner",2000.00,titleList.get(i),BitmapFactory.decodeResource(context.getResources(),R.drawable.vacuum_cleaner));
                items.add(item);

            }
            else if(titleList.get(i)=="Furniture"){
                item = new Item(4,"Table",4000.00,titleList.get(i), BitmapFactory.decodeResource(context.getResources(),R.drawable.table));
                items.add(item);


                item = new Item(5,"Chair",1000.00,titleList.get(i),BitmapFactory.decodeResource(context.getResources(),R.drawable.chair));
                items.add(item);


                item = new Item(6,"Almirah",10000.00,titleList.get(i),BitmapFactory.decodeResource(context.getResources(),R.drawable.almirah));
                items.add(item);

            }
            itemList.put(titleList.get(i),items);
        }
        return  itemList;
    }




}
