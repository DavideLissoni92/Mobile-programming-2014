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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUtenteActivity extends Activity{
	EditText nick, nome, cognome, email, pass, confermapass, vecchiapass;
	Button registrati;
	String controllo = "false";
	String nomestr, cognomestr, nicknamestr, emailstr;
	IP ip = new IP();

	int myid,idutente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editutente);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		idutente=bundle.getInt("idutente");
		nicknamestr = bundle.getString("nickname");
		nomestr = bundle.getString("nome");
		cognomestr = bundle.getString("cognome");
		emailstr = bundle.getString("email");
		ActionBar actionBar = getActionBar();
		// Setta il titolo
		actionBar.setTitle("Modifica le tue impostazioni");

		actionBar.setDisplayHomeAsUpEnabled(true);

		nick = (EditText) findViewById(R.id.Nicknameedit);
		nome = (EditText) findViewById(R.id.Nomeedit);
		cognome = (EditText) findViewById(R.id.Cognomeedit);
		email = (EditText) findViewById(R.id.Emailedit);
		pass = (EditText) findViewById(R.id.Passwordedit);
		confermapass = (EditText) findViewById(R.id.ConfermaPasswordedit);
		vecchiapass = (EditText) findViewById(R.id.VecchiaPasswordedit);
		registrati = (Button) findViewById(R.id.Modifica);
		nick.setText(nicknamestr);
		nome.setText(nomestr);
		cognome.setText(cognomestr);
		email.setText(emailstr);

		registrati.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				confrontapass();
				// startActivity(intent);
			}
		});
	}

	

	

	public void confrontapass() {
		String nick1 = nick.getText().toString();
		String nome1 = nome.getText().toString();
		String cognome1 = cognome.getText().toString();
		String email1 = email.getText().toString();
		String pass1 = pass.getText().toString();
		String confermapass1 = confermapass.getText().toString();
		String vecchiapass1 = vecchiapass.getText().toString();
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
		Toast toast6 = Toast.makeText(this,
				"Il campo conferma Password non puo essere vuoto",
				Toast.LENGTH_LONG);
		Toast toast7 = Toast.makeText(this,
				"Il campo vecchia Password non puo essere vuoto",
				Toast.LENGTH_LONG);

		if (nick1.equals("")) {
			toast2.show();
		} else if (nome1.equals("")) {
			toast3.show();
		} else if (cognome1.equals("")) {
			toast4.show();
		} else if (pass1.equals("")) {
			toast5.show();
		} else if (confermapass1.equals("")) {
			toast6.show();
		} else if (vecchiapass1.equals("")) {
			toast7.show();
		} else if (pass1.equals(confermapass1)) {
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
		String pass1 = pass.getText().toString();
		String vecchiapass1 = vecchiapass.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"ModificaUtente?nickname="
							+ nick1 + "&nome=" + nome1 + "&cognome=" + cognome1
							+ "&email=" + email1 + "&password=" + pass1
							+ "&vecchiapass=" + vecchiapass1 + "&idutente="
							+ myid);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(risposta);
				controllo = json.optString("modifica");
			}

			if (controllo.equals("true")) {
				Toast toast = Toast.makeText(this,
						"Modifica avvenuta con successo", Toast.LENGTH_LONG);
				toast.show();
				final Intent intent = new Intent(EditUtenteActivity.this,
						ViewUtenteActivity.class);
				intent.putExtra("id", myid);
				intent.putExtra("idutente", myid);
				startActivity(intent);
			} else if (controllo.equals("falsepassword")) {
				Toast toast6 = Toast.makeText(this, "Vecchia password errata",
						Toast.LENGTH_LONG);
				toast6.show();
			} else {
				Toast toast1 = Toast.makeText(this, "Altro errore",
						Toast.LENGTH_LONG);
				toast1.show();
			}

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}
	}

}
