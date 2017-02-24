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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity{
	EditText editnick, editpass;
	Button btnlogin, btnregistrazione;
	CheckBox ricorda;
	int verifica;
	IP ip = new IP();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		editnick = (EditText) findViewById(R.id.Nickname);
		editpass = (EditText) findViewById(R.id.Password);
		btnlogin = (Button) findViewById(R.id.Login);
		btnregistrazione = (Button) findViewById(R.id.Registrazione);

		btnlogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				autenticazione();

			}
		});

		btnregistrazione.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent registrazione = new Intent(LoginActivity.this,
						RegistrationActivity.class);
				startActivity(registrazione);
			}
		});

	}

	public void autenticazione() {
		String nick = editnick.getText().toString();
		String password = editpass.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"Autenticazione?nickname="
							+ nick + "&password=" + password);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(risposta);
				verifica = json.optInt("id");

			}
			Intent login = new Intent(LoginActivity.this, HomeActivity.class);
			login.putExtra("id", verifica);

			if (verifica == -1) {
				Toast toast6 = Toast.makeText(this,
						"Nome utente o password errati", Toast.LENGTH_LONG);
				toast6.show();
				editnick.setText("");
				editpass.setText("");
				editnick.requestFocus();
			} else {
				startActivity(login);

			}

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}
	}
}