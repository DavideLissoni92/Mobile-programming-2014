package com.example.progtablet;

import java.util.ArrayList;
import java.util.HashMap;

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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ViewFestaActivity extends Activity {
	TextView indirizzo, generi;
	IP ip = new IP();

	ListView lineup;
	ExpandableListView descrizione;
	NumberPicker risposta;
	int myid, idfesta;
	String datai, dataf, orai, oraf, nomefesta, genere, luogo;
	String[] nomedj;
	String[] orainizio;
	String[] orafine;
	String amministratore = "false", descrizionestr;

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();

	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map = new HashMap<String, String>();

	SimpleAdapter mSchedule;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewfesta);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		idfesta = bundle.getInt("idfesta");

		descrizione = (ExpandableListView) findViewById(R.id.apridescrizione);
		indirizzo = (TextView) findViewById(R.id.indirizzofesta);
		generi = (TextView) findViewById(R.id.generifesta);
		lineup = (ListView) findViewById(R.id.listalineup);
		risposta = (NumberPicker) findViewById(R.id.risposta);
		final String[] valori = new String[3];
		valori[0] = "partecipo";
		valori[1] = "Non So";
		valori[2] = "Non partecipo";

		risposta.setMinValue(0);
		risposta.setMaxValue(2);
		risposta.setWrapSelectorWheel(true);
		risposta.setDisplayedValues(valori);

		risposta.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {

				String vecchiarisposta = String.valueOf(valori[oldVal]);
				String nuovarisposta = String.valueOf(valori[newVal]);

				if (nuovarisposta.equals("partecipo")) {

					try {
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://192.168.1.131:8084/party4party/Accettainvito?idutente="
										+ myid + "&idfesta=" + idfesta);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();

					} catch (Exception e) {
					}

				} else if (nuovarisposta.equals("Non So")) {

					try {
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://192.168.1.131:8084/party4party/InvitoNonSo?idutente="
										+ myid + "&idfesta=" + idfesta);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();

					} catch (Exception e) {
					}

				} else if (nuovarisposta.equals("Non partecipo")) {

					try {
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://192.168.1.131:8084/party4party/RifiutaInvito?idutente="
										+ myid + "&idfesta=" + idfesta);
						HttpResponse response = httpclient.execute(httppost);
						HttpEntity entity = response.getEntity();

					} catch (Exception e) {
					}

				}

			}
		});

		lineup.setClickable(false);
		descrizione.setGroupIndicator(null);
		descrizione.setClickable(false);

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ip.getindirizzo()
					+ "VisualizzaFesta?idutente=" + myid + "&idfesta="
					+ idfesta);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				JSONObject json = jarr.getJSONObject(0);
				orai = json.optString("orainizio");
				datai = json.optString("datainizio");
				oraf = json.optString("orafine");
				dataf = json.optString("datafine");
				descrizionestr = json.optString("descrizione");
				nomefesta = json.optString("nome");
				genere = json.optString("generi");
				luogo = json.optString("luogo");
				amministratore = json.optString("amministratore");

				nomedj = new String[jarr.length() - 1];
				orainizio = new String[jarr.length() - 1];
				orafine = new String[jarr.length() - 1];
				JSONObject json2;
				for (int i = 1; i < jarr.length(); i++) {
					json2 = jarr.getJSONObject(i);
					nomedj[i] = json2.optString("name");
					orainizio[i] = json2.optString("orainizio");
					orafine[i] = json2.optString("orafine");

				}
				map.put("orari", "NomeDJ");
				map.put("generi", "OraInizio");
				map.put("dj", "OraFine");
				mylist.add(map);

				for (int i = 0; i < jarr.length(); i++) {
					map = new HashMap<String, String>();
					map.put("orari", nomedj[i]);
					map.put("generi", orainizio[i]);
					map.put("dj", orafine[i]);
					mylist.add(map);

				}
			}
		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}

		generi.setText(genere);
		indirizzo.setText(luogo);
		ActionBar actionBar = getActionBar();
		// Setta il titolo
		actionBar.setTitle(nomefesta);

		// Setta il sottotitolo
		actionBar.setSubtitle(datai.substring(0, 10) + " " + orai);

		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set the Items of Parent
		setGroupParents();
		// Set The Child Data
		setChildData();

		ListaLineupAdapter adapter = new ListaLineupAdapter(parentItems,
				childItems);

		adapter.setInflater(
				(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
				this);

		descrizione.setAdapter(adapter);

		mSchedule = new SimpleAdapter(this, mylist, R.layout.listlineupitem,
				new String[] { "orari", "generi", "dj" }, new int[] {
						R.id.orariolineup, R.id.generilineup, R.id.djlineup });
		lineup.setAdapter(mSchedule);

	}

	public void onToggleClicked(View view) {

		indirizzo.setText("schiaccaito");

	}

	private void setChildData() {

		ArrayList<String> child = new ArrayList<String>();
		child.add("vadjlanksjnfaksdj");
		childItems.add(child);
	}

	private void setGroupParents() {
		parentItems.add("Descrizione");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		boolean admin = true;

		if (admin) {
			getMenuInflater().inflate(R.menu.viewfestamenuadmin, menu);
		} else {
			getMenuInflater().inflate(R.menu.viewfestamenu, menu);
		}

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

		case R.id.action_invita:
			i = new Intent(this, InvitaActivity.class);
			int controlo = 0;
			i.putExtra("id", myid);
			i.putExtra("idfesta", idfesta);
			i.putExtra("controllo", controlo);
			i.putExtra("nome", "");
			i.putExtra("cognome", "");
			i.putExtra("nickname", "");
			i.putExtra("ruolo", "Tutti");
			i.putExtra("oggetto", "Qualsiasi");
			startActivity(i);
			return true;

		case R.id.action_partecipanti:
			i = new Intent(this, PartecipantiActivity.class);
			startActivity(i);
			return true;

		case R.id.action_edit_info:
			i = new Intent(this, EditFestaActivity.class);
			i.putExtra("id", myid);
			i.putExtra("idfesta", idfesta);
			i.putExtra("nomefesta", nomefesta);
			i.putExtra("orai", orai);
			i.putExtra("oraf", oraf);
			i.putExtra("datai", datai);
			i.putExtra("dataf", dataf);
			i.putExtra("genere", genere);
			i.putExtra("luogo", luogo);

			startActivity(i);
			return true;

		case R.id.action_edit_lineup:
			i = new Intent(this, EditLineupActivity.class);
			startActivity(i);
			return true;

		case R.id.action_edit_descrizione:
			i = new Intent(this, EditFestaDescrizioneActivity.class);
			i.putExtra("id", myid);
			i.putExtra("idfesta", idfesta);
			i.putExtra("descrizione", descrizionestr);
			startActivity(i);
			return true;

		case R.id.action_home:
			i = new Intent(this, HomeActivity.class);
			startActivity(i);
			return true;

		}
		return true;
	}

}