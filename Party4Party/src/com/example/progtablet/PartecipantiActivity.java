package com.example.progtablet;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class PartecipantiActivity extends ExpandableListActivity implements
OnChildClickListener{
	
ExpandableListView lista;
TextView numero;
	
	ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_partecipanti);
        
        ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Partecipanti");
		 
		
        
        
        numero = (TextView) findViewById(R.id.npartecipanti);
        
        lista=(ExpandableListView) findViewById(android.R.id.list);
        lista.setClickable(true);
        lista.setDividerHeight(2);
        lista.setGroupIndicator(null);
        setGroupData();
        setChildGroupData();
        
        numero.setText("inserire numero partecipanti da db");
        
        ExpandableListPartecipantiAdapter adapter = new ExpandableListPartecipantiAdapter(groupItem, childItem);
   	 adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
   	      this);
   	  getExpandableListView().setAdapter(adapter);
   	  lista.setOnChildClickListener(this);
        
    }


	private void setChildGroupData() {
		ArrayList<String> child = new ArrayList<String>();
		  child.add("1");
		  child.add("2");
		  child.add("3");
		  childItem.add(child);
		  
		  child = new ArrayList<String>();
		  child.add("Android");
		  child.add("Window Mobile");
		  child.add("iPHone");
		  child.add("Blackberry");
		  childItem.add(child);
		  
		  child = new ArrayList<String>();
		  child.add("Android");
		  child.add("Window Mobile");
		  child.add("iPHone");
		  child.add("Blackberry");
		  childItem.add(child);
		
	}


	private void setGroupData() {
		
		groupItem.add("Preferiti");
		  groupItem.add("Altri");
		
	}
	
	
	@Override
	 public boolean onChildClick(ExpandableListView parent, View v,
	   int groupPosition, int childPosition, long id) {
	  Toast.makeText(PartecipantiActivity.this, "Clicked On Child",
	    Toast.LENGTH_SHORT).show();
	  return true;
	 }
    






}

