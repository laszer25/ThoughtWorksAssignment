package com.example.thoughtworksassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sisir on 07-Jun-15.
 */
public class Cart extends ActionBarActivity {

    CartItemAdapter adapter;
    TextView totalPriceTV;
    double totalPrice = 0.0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);



    }

    public List<CartItem> getCartItemList(Collection<CartItem> cartItemCollection){
        List<CartItem> cartItems = new ArrayList<CartItem>();
        for(CartItem cartItem : cartItemCollection){
            cartItems.add(cartItem);
            totalPrice += cartItem.getPrice();
        }
        return cartItems;
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalPrice = 0.0;
        setContentView(R.layout.cart_layout);

        TextView cartEmptyView = (TextView) findViewById(R.id.cart_empty_textView);
        totalPriceTV = (TextView) findViewById(R.id.cart_total_price);
        Globals.populateCart(this);
        HashMap<Integer,CartItem> cartItemHashMap = Globals.cartItems;
        if(cartItemHashMap.isEmpty()) {
            cartEmptyView.setText("There are no items in the cart");
        }
        else
        {
            cartEmptyView.setText("");
            ListView cartList = (ListView) findViewById(R.id.cart_list);
            final List<CartItem> cartItems = getCartItemList(cartItemHashMap.values());

            totalPriceTV.setText(Double.toString(totalPrice));
            adapter = new CartItemAdapter(this,R.id.cart_list,cartItems);
            cartList.refreshDrawableState();
            cartList.setAdapter(adapter);


            cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item selectedItem = cartItems.get(position).getItem();
                    Intent intent = new Intent(Cart.this, ItemDetail.class);
                    intent.putExtra("Category", selectedItem.getCategory());
                    intent.putExtra("Name", selectedItem.getName());
                    intent.putExtra("Price", selectedItem.getPrice());
                    intent.putExtra("Id",selectedItem.getId());
                    intent.putExtra("Caller","Cart");
                    Cart.this.startActivityForResult(intent,1);

                }
            });
        }
    }

    public void removeItem(CartItem cartItem) {
        Item item = cartItem.getItem();
        double priceRemoved = cartItem.getPrice();
        Globals.cartItems.remove(item.getId());
        adapter.remove(cartItem);
        totalPrice = totalPrice - priceRemoved;
        totalPriceTV.setText(Double.toString(Globals.totalPrice(getCartItemList(Globals.cartItems.values()))));
        adapter.notifyDataSetChanged();
        Globals.saveCart(this);
    }


    public void addItems(CartItem cartItem) {
        int index = adapter.getPosition(cartItem);
        adapter.remove(cartItem);
        cartItem.updateQuantity(1);
        Globals.cartItems.put(cartItem.getItem().getId(), cartItem);

        adapter.insert(cartItem,index);

        totalPrice = totalPrice + cartItem.getItem().getPrice();
        totalPriceTV.setText(Double.toString(Globals.totalPrice(getCartItemList(Globals.cartItems.values()))));
        adapter.notifyDataSetChanged();
        Globals.saveCart(this);
    }

    public void subItems(CartItem cartItem) {
        int index = adapter.getPosition(cartItem);
        adapter.remove(cartItem);
        if(cartItem.getQuantity()>1)
        cartItem.updateQuantity(-1);
        Globals.cartItems.put(cartItem.getItem().getId(), cartItem);

        adapter.insert(cartItem,index);

        totalPrice = totalPrice + cartItem.getItem().getPrice();
        totalPriceTV.setText(Double.toString(Globals.totalPrice(getCartItemList(Globals.cartItems.values()))));
        adapter.notifyDataSetChanged();
        Globals.saveCart(this);

    }
}
