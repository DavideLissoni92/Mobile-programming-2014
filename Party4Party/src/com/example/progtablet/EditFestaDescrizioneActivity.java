package com.example.progtablet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditFestaDescrizioneActivity extends Activity implements OnClickListener{
	
	String descrizionestr;
	int myid,idfesta;
	EditText descrizione;
	Button conferma;
	IP ip = new IP();

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdescrizionefesta);
		ActionBar actionBar= getActionBar();
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		idfesta=bundle.getInt("idfesta");
		descrizionestr=bundle.getString("descrizione");
		 // Setta il titolo
		actionBar.setTitle("Modifica descrizione");
		 
		
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	
		descrizione = (EditText) findViewById(R.id.editdescrizionefesta);
		conferma = (Button) findViewById(R.id.editsalvafesta);
		descrizione.setText(descrizionestr);
		
		
		
		
		
		
		
		
		
		
		
		
		conferma.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		String descrnew=descrizione.getText().toString();
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"ModificaDescrizioneFesta?idfesta="
							+ idfesta + "&descrizione=" + descrnew);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject jarr = new JSONObject(risposta);}}catch(Exception e){}
		
		
		Intent i = new Intent(this,ViewFestaActivity.class);
		i.putExtra("id", myid);
		i.putExtra("idfesta", idfesta);
		startActivity(i);
		
		
	}

}