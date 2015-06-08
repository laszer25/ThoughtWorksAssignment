package com.example.thoughtworksassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DataHandler dataHandler = new DataHandler(this);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
        List<String> titleList = dataHandler.getTitleList();
        Globals.titleList = titleList;
        HashMap<String,List<Item>> itemList = dataHandler.getItemList();
        Globals.itemList = itemList;
        final ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this,Globals.titleList,Globals.itemList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Send the selected item to the View Items page
                Item selectedItem = (Item) expandableListAdapter.getChild(groupPosition,childPosition);
                Intent intent = new Intent(MainActivity.this, ItemDetail.class);
                intent.putExtra("Category", selectedItem.getCategory());
                intent.putExtra("Name", selectedItem.getName());
                intent.putExtra("Price", selectedItem.getPrice());
                intent.putExtra("Id",selectedItem.getId());
                intent.putExtra("Caller","MainActivity");

                MainActivity.this.startActivity(intent);

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent intent = new Intent(MainActivity.this,Cart.class);
            intent.putExtra("Caller","MainActivity");
            MainActivity.this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}