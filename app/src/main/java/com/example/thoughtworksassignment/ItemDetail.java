package com.example.thoughtworksassignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sisir on 06-Jun-15.
 */
public class ItemDetail extends ActionBarActivity {

    TextView priceView;
    Intent intent;
    String caller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);


        intent = getIntent();
        final Item item = new Item();
        caller  = intent.getStringExtra("Caller");
        item.setPrice(intent.getDoubleExtra("Price",0.00));
        item.setCategory(intent.getStringExtra("Category"));
        item.setName(intent.getStringExtra("Name"));
        item.setId(intent.getIntExtra("Id",0));

        List<Item> items;
        items = Globals.itemList.get(intent.getStringExtra("Category"));


        for(Item iteml : items){
            if(intent.getIntExtra("Id",0) == iteml.getId())
                item.setImage(iteml.getImage());
        }

        TextView categoryView = (TextView) findViewById(R.id.item_category);
        TextView nameView = (TextView) findViewById(R.id.item_name);
        priceView = (TextView) findViewById(R.id.item_price);
        ImageView imageView = (ImageView) findViewById(R.id.item_image);
        Button addToCartButton = (Button) findViewById(R.id.item_addcart);


        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem cartItem;
                if(Globals.cartItems.containsKey(item.getId())){
                    cartItem = Globals.cartItems.get(item.getId());
                    cartItem.updateQuantity(1);
                    Globals.cartItems.put(item.getId(),cartItem);
                }
                else {
                    cartItem = new CartItem(item,1);
                    Globals.cartItems.put(item.getId(),cartItem);
                }
                Globals.saveCart(ItemDetail.this);
                Toast.makeText(ItemDetail.this,"Added to cart",Toast.LENGTH_SHORT).show();
            }


        });
        categoryView.setText("Category : " + item.getCategory());
        nameView.setText("Name : "+ item.getName());
        imageView.setImageBitmap(item.getImage());
        priceView.setText("Price : "+Double.toString(item.getPrice()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {

            if(caller.equals("Cart")){
                Intent returnIntent = new Intent();
                setResult(1,returnIntent);
                finish();
            }
            else {
                Intent intent = new Intent(ItemDetail.this, Cart.class);
                intent.putExtra("Caller", "ItemDetail");
                ItemDetail.this.startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
