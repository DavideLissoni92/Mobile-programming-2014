package com.example.progtablet;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.progtablet.R;
public class AddPrestitiActivity extends ListActivity implements OnClickListener{
	Button salva;
	Spinner oggetto;
	EditText descrizione;
	ListView listasalvati;
	String oggettonuovo;
	IP ip = new IP();
	int myid;
	String controllo = "false";
	List<String> listoggetto = new ArrayList<String>();
	/** Items entered by the user is stored in this ArrayList variable */
	ArrayList<String> list = new ArrayList<String>();

	/** Declaring an ArrayAdapter to set items to ListView */
	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addprestiti);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		descrizione = (EditText) findViewById(R.id.txtInserisciPrestito);
		listasalvati = (ListView) findViewById(android.R.id.list);

		oggetto = (Spinner) findViewById(R.id.InserisciOggetto);
		addItemsOnSpinner();

		salva = (Button) findViewById(R.id.salvaprestiti);

		/** Defining the ArrayAdapter to set items to ListView */
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		/** Setting the adapter to the ListView */
		setListAdapter(adapter);

		salva.setOnClickListener(this);

	}

	private void addItemsOnSpinner() {

		listoggetto.add("Casse");
		listoggetto.add("Console");
		listoggetto.add("Microfono");
		listoggetto.add("Altro");

		ArrayAdapter<String> dataAdapteroggetto = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listoggetto);
		dataAdapteroggetto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		oggetto.setAdapter(dataAdapteroggetto);

	}

	@Override
	public void onClick(View arg0) {
		modifica();
		adapter.notifyDataSetChanged();

	}

	public void modifica() {

		oggettonuovo = oggetto.getSelectedItem().toString();
		String descrizione1 = descrizione.getText().toString();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"InserisciOggetto?idutente="
							+ myid + "&nome=" + oggettonuovo + "&descrizione="
							+ descrizione1);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(risposta);
				controllo = json.optString("inseriscioggetto");
			}
			Log.d("json", controllo);

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}
		if (controllo.equals("true")) {
			int obj = oggetto.getSelectedItemPosition();

			list.add(listoggetto.get(obj).toString());
		} else {
			Toast toast6 = Toast.makeText(this,
					"Errore nell inserimento dell oggetto", Toast.LENGTH_LONG);
			toast6.show();
		}
	}

}