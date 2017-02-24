package com.example.progtablet;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;


public class ListaLineupAdapter extends BaseExpandableListAdapter
{

    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    // constructor
    public ListaLineupAdapter(ArrayList<String> parents, ArrayList<Object> childern)
    {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity)
    {
        this.inflater = inflater;
        this.activity = activity;
    }
   
    // method getChildView is called automatically for each child view.
    //  Implement this method as per your requirement
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.viewfestalineupchild, null);
        }
   
         // get the textView reference and set the value
        textView = (TextView) convertView.findViewById(R.id.viewchild);
        textView.setText(child.get(childPosition));

        
        return convertView;
    }

    // method getGroupView is called automatically for each parent item
    // Implement this method as per your requirement
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.viewfestalineupheader, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }


    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

   
    public int getChildrenCount(int groupPosition)
    {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    
    public int getGroupCount()
    {
        return parentItems.size();
    }

    
   
    
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

   
    public boolean hasStableIds()
    {
        return false;
    }

   
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}	