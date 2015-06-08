package com.example.thoughtworksassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Sisir on 07-Jun-15.
 */
public class CartItemAdapter extends ArrayAdapter<CartItem> {
    private Context context;
    private List<CartItem> cartItems;


    public CartItemAdapter(Context context, int resource, List<CartItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.cartItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cart_list_item,parent,false);

        final CartItem cartItem = cartItems.get(position);
        final Item item = cartItem.getItem();
        Double totalPrice = cartItem.getPrice();
        int quantity = cartItem.getQuantity();

        TextView nameTV = (TextView) view.findViewById(R.id.cart_item_itemname);
        TextView priceQuantityTV = (TextView) view.findViewById(R.id.cart_item_itemprice);
        TextView totalPriceTV = (TextView) view.findViewById(R.id.cart_item_totalprice);

        Button addBtn = (Button) view.findViewById(R.id.cart_item_add);
        Button subBtn = (Button) view.findViewById(R.id.cart_item_sub);
        Button remBtn = (Button) view.findViewById(R.id.cart_item_remove);

        if(cartItem.getQuantity()>1)
            subBtn.setVisibility(View.VISIBLE);
        else
            subBtn.setVisibility(View.INVISIBLE);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Cart)context).addItems(cartItem);

            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItem.getQuantity() > 1)
                ((Cart)context).subItems(cartItem);
                else
                    Toast.makeText(context,"Minimum order is 1.",Toast.LENGTH_SHORT).show();
            }
        });

        remBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Cart)context).removeItem(cartItem);

            }
        });

        nameTV.setText(item.getName());
        priceQuantityTV.setText(Double.toString(item.getPrice())+" x "+Integer.toString(cartItem.getQuantity()));
        totalPriceTV.setText(Double.toString(totalPrice));
        return view;
    }
}
