package com.example.progtablet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ExpandableListActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Note that here we are inheriting ListActivity class instead of Activity class
 **/
public class RicercaActivity extends ExpandableListActivity implements
		OnChildClickListener {
	IP ip = new IP();

	EditText nome, cognome, nickname;
	Spinner ruolo, oggetto;
	String nomestr, cognomestr, nicknamestr, ruolostr, oggettostr;
	String nomepar = "", cognomepar = "", nicknamepar = "", ruolopar = "",
			oggettopar = "";
	static int selected;
	ExpandableListView lista;
	int myid, controllo, idutente,iduscio;
	List<Utente> preferitilist = new ArrayList<Utente>();
	List<Utente> utentilist = new ArrayList<Utente>();;
	ArrayList<String> groupItem = new ArrayList<String>();
	ArrayList<Object> childItem = new ArrayList<Object>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Setting a custom layout for the list activity */
		setContentView(R.layout.activity_ricerca);

		Bundle bundle = getIntent().getExtras();
		controllo = bundle.getInt("controllo");
		myid = bundle.getInt("id");
		Log.d("MYID", Integer.toString(myid));
		idutente = bundle.getInt("idutente");
		lista = (ExpandableListView) findViewById(android.R.id.list);
		lista.setClickable(true);
		lista.setDividerHeight(2);
		lista.setGroupIndicator(null);
		setGroupData();

		ruolo = (Spinner) findViewById(R.id.RicercaRuolo);

		oggetto = (Spinner) findViewById(R.id.RicercaOggetto);
		addItemsOnSpinner();

		/** Reference to the button of the layout main.xml */
		Button btn = (Button) findViewById(R.id.btncerca);

		nome = (EditText) findViewById(R.id.txtnome);
		cognome = (EditText) findViewById(R.id.txtcognome);
		nickname = (EditText) findViewById(R.id.txtnickname);

		nomepar = bundle.getString("nome");
		cognomepar = bundle.getString("cognome");
		nicknamepar = bundle.getString("nickname");
		ruolopar = bundle.getString("ruolo");
		oggettopar = bundle.getString("oggetto");
		nome.setText(nomepar);
		cognome.setText(cognomepar);
		nickname.setText(nicknamepar);

		if (ruolopar.equals("Tutti")) {
			ruolo.setSelection(0);
		} else if (ruolopar.equals("DJ")) {
			ruolo.setSelection(1);
		} else if (ruolopar.equals("Vocalist")) {
			ruolo.setSelection(2);
		} else if (ruolopar.equals("Preparatore")) {
			ruolo.setSelection(3);
		} else if (ruolopar.equals("Ristoratore")) {
			ruolo.setSelection(4);
		} else if (ruolopar.equals("Partecipante")) {
			ruolo.setSelection(5);
		}

		if (oggettopar.equals("Qualsiasi")) {
			ruolo.setSelection(0);
		} else if (oggettopar.equals("Casse")) {
			ruolo.setSelection(1);
		} else if (oggettopar.equals("Console")) {
			ruolo.setSelection(2);
		} else if (oggettopar.equals("Microfono")) {
			ruolo.setSelection(3);
		}

		nomestr = nome.getText().toString();
		cognomestr = cognome.getText().toString();
		nicknamestr = nickname.getText().toString();
		ruolostr = ruolo.getSelectedItem().toString();
		oggettostr = oggetto.getSelectedItem().toString();

		if (controllo == 0) {
			Log.d("dovechiamo", "if");
			visualizzatutti();
		} else {
			visualizzaricerca(nomepar, cognomepar, nicknamepar, oggettopar,
					ruolopar);
		}
		setChildGroupData();

		
		/** Defining a click event listener for the button "Add" */
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				nomestr = nome.getText().toString();
				cognomestr = cognome.getText().toString();
				nicknamestr = nickname.getText().toString();
				ruolostr = ruolo.getSelectedItem().toString();
				oggettostr = oggetto.getSelectedItem().toString();

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(nome.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(cognome.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(nickname.getWindowToken(), 0);
				Intent i;
				i = new Intent(RicercaActivity.this, RicercaActivity.class);
				i.putExtra("controllo", 1);
				i.putExtra("id", myid);
				i.putExtra("nome", nomestr);

				i.putExtra("cognome", cognomestr);
				i.putExtra("nickname", nicknamestr);
				i.putExtra("ruolo", ruolostr);
				i.putExtra("oggetto", oggettostr);
				startActivity(i);
			}
		};

		/** Setting the event listener for the add button */
		btn.setOnClickListener(listener);
		lista.setOnChildClickListener(this);
		
		ExpandableListRicercaAdapter adapter = new ExpandableListRicercaAdapter(
				groupItem, childItem, getApplicationContext(),myid,iduscio,utentilist,preferitilist);
		adapter.setInflater(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				this);
		getExpandableListView().setAdapter(adapter);
		

	}

	private void setChildGroupData() {
		ArrayList<String> child = new ArrayList<String>();
		for (int i = 0; i < preferitilist.size(); i++) {
			child.add("Nickname: "+preferitilist.get(i).getnickname() + " Nome: "
					+ preferitilist.get(i).getnome()+" Cognome: "+preferitilist.get(i).getcognome());

		}
		childItem.add(child);
		child = new ArrayList<String>();
		for (int i = 0; i < utentilist.size(); i++) {
			child.add("Nickname: "+utentilist.get(i).getnickname() + " Nome: "
					+ utentilist.get(i).getnome()+" Cognome: "+utentilist.get(i).getcognome());

		}
		childItem.add(child);

	}

	private void setGroupData() {
		groupItem.add("Preferiti");
		groupItem.add("Altri");
	}

	private void addItemsOnSpinner() {
		List<String> listruolo = new ArrayList<String>();
		listruolo.add("Tutti");
		listruolo.add("DJ");
		listruolo.add("Vocalist");
		listruolo.add("Preparatore");
		listruolo.add("Ristoratore");
		listruolo.add("Partecipante");

		ArrayAdapter<String> dataAdapterruolo = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listruolo);
		dataAdapterruolo
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ruolo.setAdapter(dataAdapterruolo);

		List<String> listoggetto = new ArrayList<String>();
		listoggetto.add("Qualsiasi");
		listoggetto.add("Casse");
		listoggetto.add("Console");
		listoggetto.add("Microfono");

		ArrayAdapter<String> dataAdapteroggetto = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listoggetto);
		dataAdapteroggetto
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		oggetto.setAdapter(dataAdapteroggetto);

	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		
		try {
			if (groupPosition==0){
				iduscio=preferitilist.get(childPosition).getId();
				Log.d("iduscio",String.valueOf(iduscio));
			}else{
				iduscio=utentilist.get(childPosition).getId();
				Log.d("iduscio",String.valueOf(iduscio));
			}
			
	    	}catch (Exception e) {
	    		Log.e("TEST", "Errore nella connessione http " + e.toString());
	    	}
			
		
		
		
		Intent i = new Intent(RicercaActivity.this, ViewUtenteActivity.class);
		i.putExtra("id", myid);
		i.putExtra("idutente", iduscio);
		startActivity(i);
		return true;
	}

	public void visualizzatutti() {
		Log.d("nomechiamata", "ciao");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Log.d("nomechiamata", "ciao1");
		try {
			Log.d("nomechiamata", "ciao2");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ip.getindirizzo()
					+ "ViewUtenti?idutente=" + myid);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			Log.d("nomechiamata", "ciao3");
			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				JSONObject json;
				Log.d("nomechiamata", String.valueOf(jarr.length()));
				for (int i = 0; i < jarr.length(); i++) {
					json = jarr.getJSONObject(i);
					Utente pre = new Utente();
					Utente ut = new Utente();

					if (json.optString("nome").equals("")) {

						pre.setnickname(json.optString("nicknamepref"));
						pre.setnome(json.optString("nomepref"));
						pre.setcognome(json.optString("cognomepref"));

						pre.setid(json.optInt("idpref"));

						pre.setemail(json.optString("emailpref"));
						pre.setruolo(json.optString("ruolopref"));
						preferitilist.add(pre);
					} else {
						ut.setnickname(json.optString("nickname"));
						ut.setnome(json.optString("nome"));
						ut.setcognome(json.optString("cognome"));
						ut.setid(json.optInt("id"));
						ut.setemail(json.optString("email"));
						ut.setruolo(json.optString("ruolo"));
						utentilist.add(ut);
					}

				}
			}

		} catch (Exception e) {
		}

	}

	public void visualizzaricerca(String nome1, String cognome1,
			String nickname1, String oggetto1, String ruolo1) {
		Log.d("nomechiamata", oggetto1);
		/*
		 * nome1=nome.getText().toString();
		 * cognome1=cognome.getText().toString();
		 * nickname1=nickname.getText().toString();
		 * ruolo1=ruolo.getSelectedItem().toString();
		 * oggetto1=oggetto.getSelectedItem().toString();
		 */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Log.d("myid", String.valueOf(myid));

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ip.getindirizzo()
					+ "Ricerca?idutente=" + myid + "&nome=" + nome1
					+ "&cognome=" + cognome1 + "&nickname=" + nickname1
					+ "&ruolo=" + ruolo1 + "&prestito=" + oggetto1);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				Log.d("nomechiamata", "sono nell if ");
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				JSONObject json;
				Log.d("nomechiamata", oggetto1);
				for (int i = 0; i < jarr.length(); i++) {
					Log.d("nomechiamata", String.valueOf(jarr.length()));
					json = jarr.getJSONObject(i);
					Utente pre = new Utente();
					Utente ut = new Utente();

					if (json.optString("nome").equals("")) {

						pre.setnickname(json.optString("nicknamepref"));
						pre.setnome(json.optString("nomepref"));
						pre.setcognome(json.optString("cognomepref"));

						pre.setid(json.optInt("idpref"));

						pre.setemail(json.optString("emailpref"));
						pre.setruolo(json.optString("ruolopref"));
						preferitilist.add(pre);
					} else {
						ut.setnickname(json.optString("nickname"));
						ut.setnome(json.optString("nome"));
						ut.setcognome(json.optString("cognome"));
						ut.setid(json.optInt("id"));
						ut.setemail(json.optString("email"));
						ut.setruolo(json.optString("ruolo"));
						utentilist.add(ut);
					}

				}
			}

		} catch (Exception e) {
		}

	}

}