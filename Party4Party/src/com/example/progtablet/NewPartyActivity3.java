package com.example.progtablet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewPartyActivity3 extends Activity implements OnClickListener{
	
	EditText descrizione;
	Button conferma;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newparty3);
		ActionBar actionBar= getActionBar();
		
		
		 // Setta il titolo
		actionBar.setTitle("Crea festa");
		 
		
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	
		descrizione = (EditText) findViewById(R.id.descrizionefesta);
		conferma = (Button) findViewById(R.id.salvafesta);
		
		conferma.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		Intent i = new Intent(this,ViewFestaActivity.class);
		startActivity(i);
		
		
	}

}
