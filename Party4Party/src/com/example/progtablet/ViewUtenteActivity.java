package com.example.progtablet;


import java.util.ArrayList;
import java.util.LinkedHashMap;
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
 
import com.example.progtablet.ExpandableListViewUtenteAdapter;
import com.example.progtablet.R.drawable;
 
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class ViewUtenteActivity extends Activity {
	IP ip = new IP();

	List<String> groupList;
	List<String> childList;
	Map<String, List<String>> infoCollection;
	ExpandableListView expListView;
	int myid,idutente;
	String nome, cognome, nickname, ruolo, descrizione, email, nomeogg,
			descrizioneogg,preferito;
	String[] prestiti;
	ImageButton imgpreferito;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewutente);
		Bundle bundle = getIntent().getExtras();
		myid = bundle.getInt("id");
		idutente=bundle.getInt("idutente");
		imgpreferito = (ImageButton) findViewById(R.id.preferito);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ip.getindirizzo()+"VisualizzaUtente?id="
							+ myid + "&idutente=" + idutente);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);

				JSONObject json = jarr.getJSONObject(0);
				prestiti = new String[jarr.length() - 1];
				nome = json.optString("nome");
				cognome = json.optString("cognome");
				ruolo = json.optString("ruolo");
				descrizione = json.optString("descrizioneruolo");
				nickname = json.optString("nickname");
				email = json.optString("email");
				preferito = json.optString("preferito");
				JSONObject json2;
				Log.d("ERRORE", Integer.toString(jarr.length()));

				for (int i = 1; i < jarr.length(); i++) {
					json2 = jarr.getJSONObject(i);
					nomeogg = json2.optString("nameogg");
					descrizioneogg = json2.optString("descrizione");
					prestiti[i - 1] = nomeogg + ": " + descrizioneogg;
				}
			} else {
				prestiti = new String[0];
			}

		} catch (Exception e) {
			Log.e("TEST", "Errore nella connessione http " + e.toString());
		}

		if(myid==idutente){}else{
		if(preferito.equals("true")){
        	imgpreferito.setImageResource(drawable.ic_action_important);
        }else{
        imgpreferito.setImageResource(drawable.ic_action_not_important);
        }}
        
        
        imgpreferito.setOnClickListener(new View.OnClickListener() {
			
			        		            public void onClick(View view) {
			        		            	
			        		            	if(preferito.equals("true")){
			        		                	imgpreferito.setImageResource(drawable.ic_action_important);
			        		                
			        		                	
			        		                	try {
			        		            			HttpClient httpclient = new DefaultHttpClient();
			        		            			HttpPost httppost = new HttpPost(
			        		            					ip.getindirizzo()+"EliminaPreferito?idutente="
			        		            							+ myid + "&idpreferito=" + idutente);
			        		            			HttpResponse response = httpclient.execute(httppost);
			        		            			HttpEntity entity = response.getEntity();
			        		                	}catch(Exception e){}
			        		                }else{
			        		                imgpreferito.setImageResource(drawable.ic_action_not_important);
			        		               try{
			        		                HttpClient httpclient = new DefaultHttpClient();
	        		            			HttpPost httppost = new HttpPost(
	        		            					ip.getindirizzo()+"AggiungiPreferito?idutente="
	        		            							+ myid + "&idpreferito=" + idutente);
	        		            			HttpResponse response = httpclient.execute(httppost);
	        		            			HttpEntity entity = response.getEntity();
			        		               }catch(Exception e){} 
			        		               }
			        		            } });
		
		
		createGroupList();

		createCollection();

		expListView = (ExpandableListView) findViewById(R.id.infolistutente);
		final ExpandableListViewUtenteAdapter expListAdapter = new ExpandableListViewUtenteAdapter(
				this, groupList, infoCollection);
		expListView.setAdapter(expListAdapter);

		expListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final String selected = (String) expListAdapter.getChild(
						groupPosition, childPosition);
				Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
						.show();

				return true;
			}
		});

		expListView.expandGroup(0);

	}

	private void createGroupList() {
		groupList = new ArrayList<String>();
		groupList.add("Informazioni");
		groupList.add("Prestiti");
	}

	private void createCollection() {

		String[] informazioni = { "Nickname: " + nickname, "Nome: " + nome,
				"Cognome: " + cognome, "Email: " + email, "Ruolo: " + ruolo,
				"Descrizione: " + descrizione };

		// String[] prestiti = { "Casse: " , "Microfono:", "Console:" };

		infoCollection = new LinkedHashMap<String, List<String>>();

		for (String controllo : groupList) {
			if (controllo.equals("Informazioni")) {
				loadChild(informazioni);
				infoCollection.put(controllo, childList);
			} else if (controllo.equals("Prestiti")) {
				loadChild(prestiti);
				infoCollection.put(controllo, childList);
			}

		}
	}

	private void loadChild(String[] child) {
		childList = new ArrayList<String>();
		for (String model : child)
			childList.add(model);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(myid==idutente){
		getMenuInflater().inflate(R.menu.viewutentemenu, menu);}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		Intent i;
		
		switch (item.getItemId()) {
		case R.id.action_editprofilo:
			i = new Intent(this, EditUtenteActivity.class);
			i.putExtra("id", myid);
			i.putExtra("nickname", nickname);
			i.putExtra("nome", nome);
			i.putExtra("cognome", cognome);
			i.putExtra("email", email);
			startActivity(i);
			return true;

		case R.id.action_editdescrizione:
			i = new Intent(this, EditDescrizioneUtenteActivity.class);
			i.putExtra("id", myid);
			i.putExtra("idutente",idutente);
			startActivity(i);
			return true;

		case R.id.action_new:
			i = new Intent(this, AddPrestitiActivity.class);
			i.putExtra("id", myid);
			startActivity(i);
			return true;

		}
		return true;
	}
}
