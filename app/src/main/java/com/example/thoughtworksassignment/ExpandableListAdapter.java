package com.example.thoughtworksassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Sisir on 06-Jun-15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> titleList;
    HashMap<String, List<Item>> itemList;

    public ExpandableListAdapter(Context context,List<String> titleList,HashMap<String,List<Item>> itemList){
        this.context = context;
        this.titleList = titleList;
        this.itemList = itemList;
    }

    @Override
    public int getGroupCount() {
        return titleList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(titleList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titleList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(this.titleList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String titleString = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.title_list,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.titleText);
        textView.setText(titleString);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Item item = (Item) getChild(groupPosition,childPosition);
        String itemString = item.getName();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.itemText);
        textView.setText(itemString);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
