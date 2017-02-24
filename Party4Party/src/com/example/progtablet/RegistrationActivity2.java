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

public class RegistrationActivity2 extends Activity implements OnClickListener{
	Spinner ruolo;
	Button registrati;
	EditText descrizione;
String nick,ruolonuovo;
	String controllo="false";
	int myid;
	IP ip = new IP();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration2);
		Bundle bundle = getIntent().getExtras();
		nick = bundle.getString("nickname");
		
		ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Registrazione");
		 
		// Setta il sottotitolo
		actionBar.setSubtitle("comincia una nuova avventura");
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		ruolo = (Spinner) findViewById(R.id.Ruolo);
		addItemsOnSpinner();
		
		descrizione = (EditText) findViewById(R.id.Descrizione);
		
		registrati = (Button) findViewById(R.id.Registrati);
		registrati.setOnClickListener(this);
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"ViewIdRegistrazione?nickname="+ nick);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(risposta);
				myid = json.optInt("id");
			}}catch (Exception e) {
				Log.e("TEST", "Errore nella connessione http " + e.toString());
			}
		
		
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


	@Override
	public void onClick(View arg0) {
modifica();
		Intent i = new Intent(RegistrationActivity2.this,LoginActivity.class);
		startActivity(i);
		
		
	}
public void modifica(){
		
		
		ruolonuovo=ruolo.getSelectedItem().toString();
		String descrizione1 =descrizione.getText().toString();
Log.d("OGGETTO",ruolonuovo);
Log.d("ID",Integer.toString(myid));
Log.d("ID",descrizione.getText().toString());

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
StrictMode.setThreadPolicy(policy);
try {
	HttpClient httpclient = new DefaultHttpClient();
	HttpPost httppost = new HttpPost(
			"http://192.168.1.131:8084/party4party/ModificaRuolo?idutente="
					+ myid + "&ruolo=" + ruolonuovo+"&descrizione="+descrizione1);
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
