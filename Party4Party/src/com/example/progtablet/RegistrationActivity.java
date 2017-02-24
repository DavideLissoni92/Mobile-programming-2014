package com.example.progtablet;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnClickListener{
	EditText nick, nome, cognome, email, pass, confermapass;
	Button continua;
	String controllo="false";
	IP ip = new IP();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Registrazione");
		 
		// Setta il sottotitolo
		actionBar.setSubtitle("comincia una nuova avventura");
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		
		nick= (EditText) findViewById(R.id.Nickname);
		nome =(EditText) findViewById(R.id.Nome);
		cognome = (EditText) findViewById(R.id.Cognome);
		email =(EditText) findViewById(R.id.Email);
		pass = (EditText) findViewById(R.id.Password);
		confermapass = (EditText) findViewById(R.id.ConfermaPassword);
		continua = (Button) findViewById(R.id.Continua);
		continua.setOnClickListener(this);
		
		
		
		
	}


	


	@Override
	public void onClick(View arg0) {
confrontapass();
		/*Intent i = new Intent(this,RegistrationActivity2.class);
		startActivity(i);*/
		
		
	}

	public void confrontapass() {
		String nick1 = nick.getText().toString();
		String nome1 = nome.getText().toString();
		String cognome1 = cognome.getText().toString();
		String password1 = pass.getText().toString();
		String confermapass1 = confermapass.getText().toString();

		Toast toast1 = Toast.makeText(this,
				"Le due password devono coincidere", Toast.LENGTH_LONG);
		Toast toast2 = Toast.makeText(this,
				"Il campo Nickname non puo essere vuoto", Toast.LENGTH_LONG);
		Toast toast3 = Toast.makeText(this,
				"Il campo Nome non puo essere vuoto", Toast.LENGTH_LONG);
		Toast toast4 = Toast.makeText(this,
				"Il campo Cognome non puo essere vuoto", Toast.LENGTH_LONG);
		Toast toast5 = Toast.makeText(this,
				"Il campo Password non puo essere vuoto", Toast.LENGTH_LONG);

		if (nick1.equals("")) {
			toast2.show();
		} else if (nome1.equals("")) {
			toast3.show();
		} else if (cognome1.equals("")) {
			toast4.show();
		} else if (password1.equals("")) {
			toast5.show();
		} else if (password1.equals(confermapass1)) {
			registrazione();
		} else {
			toast1.show();
			return;
		}

	}

	public void registrazione() {

		String nick1 = nick.getText().toString();
		String nome1 = nome.getText().toString();
		String cognome1 = cognome.getText().toString();
		String email1 = email.getText().toString();
		String password1 = pass.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"Registrazione?nickname="
							+ nick1 + "&nome=" + nome1 + "&cognome=" + cognome1
							+ "&email=" + email1 + "&password=" + password1);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(risposta);
				controllo = json.optString("registration");
			}
			Log.d("json", controllo);

			if (controllo.equals("true")) {
				Toast toast = Toast.makeText(this,
						"Registrazione avvenuta con successo",
						Toast.LENGTH_LONG);
				toast.show();
				final Intent intent = new Intent(RegistrationActivity.this,
						RegistrationActivity2.class);
				intent.putExtra("nickname", nick1);
				startActivity(intent);
			} else {
				Toast toast6 = Toast.makeText(this, "Nickname gia esistente",
						Toast.LENGTH_LONG);
				toast6.show();
			}

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}
}
}
