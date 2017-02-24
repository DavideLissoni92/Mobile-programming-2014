package com.example.progtablet;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

public class ListaPreferitiActivity extends Activity {
	
	ListView lista;

    
    
    
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  setContentView(R.layout.activity_listapreferiti);
	  
	  ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Utenti preferiti");
		 
		
	    
	    lista=(ListView) findViewById(R.id.listapreferiti);
	    
	    String nomi[]={ "Perro", "Gato", "Oveja", "Elefante", "Pez",
		        "Nicuro", "Bocachico", "Chucha", "Curie", "Raton", "Aguila",
		        "Leon", "Jirafa" };
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              R.layout.rigapreferiti, nomi);
	    
	    lista.setAdapter(adapter); 
	    
	 
      
	 
	 }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.preferitimenu, menu);
	        return true;
	    }
	    
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
	    	Intent i;
		    switch (item.getItemId()) { 
		    
		    case android.R.id.home:
	            NavUtils.navigateUpFromSameTask(this);
	            return true;
		    
	    	
		    case R.id.action_addpreferiti: 
		    	i = new Intent(this,RicercaActivity.class);
				startActivity(i);
		    	return true;
		    
		    }
			return true;
		}

		
}
