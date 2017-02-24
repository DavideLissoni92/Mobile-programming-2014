package com.example.progtablet;

import java.util.ArrayList;
import java.util.List;

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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditDescrizioneUtenteActivity extends Activity implements OnClickListener{

	Button conferma;
	EditText descrizione;
	Spinner ruolo;
	String controllo="false";
	String ruolotuo,descrizionetuo,ruolonuovo;
	int myid;
	IP ip = new IP();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdescrizione);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Modifica descrizione");
		 
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		ruolo = (Spinner) findViewById(R.id.EditRuolo);
		addItemsOnSpinner();
		
		descrizione = (EditText) findViewById(R.id.txtEditDescrizione);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);
try {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(
			ip.getindirizzo()+"ViewRuolo?idutente="+ myid);
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();

	if (entity != null) {
		String risposta = EntityUtils.toString(entity);
		JSONObject json = new JSONObject(risposta);
		ruolotuo = json.optString("ruolo");
		descrizionetuo = json.optString("descrizione");

	}}
	catch (Exception e) {
		Log.e("TEST", "Errore nella connessione http " + e.toString());
	}

if(ruolotuo.equals("DJ")){
	ruolo.setSelection(0);
}else if(ruolotuo.equals("Vocalist")){
	ruolo.setSelection(1);
}else if(ruolotuo.equals("Preparatore")){
	ruolo.setSelection(2);
}else if(ruolotuo.equals("Ristoratore")){
	ruolo.setSelection(3);
}else if(ruolotuo.equals("Partecipante")){
	ruolo.setSelection(4);
}
	
descrizione.setText(descrizionetuo);
		conferma = (Button) findViewById(R.id.btnEditDescrizione);

conferma.setOnClickListener(this);
		
		
		
	}


	
	private void addItemsOnSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("DJ");
		list.add("Vocalist");
		list.add("Preparatore");
		list.add("Ristoratore");
		list.add("Partecipante");
		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ruolo.setAdapter(dataAdapter);
		
	}



	public void onClick(View arg0) {

modifica();
		Intent i = new Intent(this,ViewUtenteActivity.class);
		i.putExtra("id",myid);
		i.putExtra("idutente", myid);
		startActivity(i);
		
		
	}

	public void modifica(){
		
		
		ruolonuovo=ruolo.getSelectedItem().toString();
		descrizione = (EditText) findViewById(R.id.txtEditDescrizione);
Log.d("OGGETTO",ruolonuovo);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);
try {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(
			ip.getindirizzo()+"ModificaRuolo?idutente="
					+ myid + "&ruolo=" + ruolonuovo+"&descrizione="+descrizione.getText().toString());
	HttpResponse response = httpclient.execute(httppost);
	HttpEntity entity = response.getEntity();

	if (entity != null) {
		String risposta = EntityUtils.toString(entity);
		JSONObject json = new JSONObject(risposta);
		controllo = json.optString("modificaruolo");
	}
	Log.d("json", controllo);

	
} catch (Exception e) {
	Log.e("TEST", "Errore nella connessione http " + e.toString());
}
	}
}
