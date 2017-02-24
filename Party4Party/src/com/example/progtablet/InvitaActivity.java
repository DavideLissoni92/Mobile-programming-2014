package com.example.progtablet;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;

public class InvitaActivity extends ExpandableListActivity implements OnClickListener,
OnChildClickListener{
	String nomepar = "", cognomepar = "", nicknamepar = "", ruolopar = "Tutti",
			oggettopar = "Qualsiasi";
	String nomestr, cognomestr, nicknamestr, ruolostr, oggettostr;
	EditText nome, cognome, nickname;
	Button cerca;
	  ListaInvitiAdapter adapter;
	ExpandableListView lista;
	List<Utente> preferitilist = new ArrayList<Utente>();
	List<Utente> utentilist = new ArrayList<Utente>();;
	int myid, controllo, idutente,idfesta;
	
	
	
	ArrayList<String> groupItem = new ArrayList<String>();
    ArrayList<Object> childItem = new ArrayList<Object>();
	
   
    
 
    
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_invita);
	    
	    ActionBar actionBar= getActionBar();
		 // Setta il titolo
		actionBar.setTitle("Invita utente");
		Bundle bundle = getIntent().getExtras();
		controllo = bundle.getInt("controllo");
		myid = bundle.getInt("id"); 
		idfesta = bundle.getInt("idfesta"); 
		
		cerca= (Button) findViewById(R.id.btncerca);
	    
	    lista=(ExpandableListView) findViewById(android.R.id.list);
        lista.setClickable(true);
        lista.setDividerHeight(2);
        lista.setGroupIndicator(null);
        
        setGroupData();
         cerca.setOnClickListener(this);
        
        nome = (EditText) findViewById(R.id.cercanomeinvito);
        cognome = (EditText) findViewById(R.id.cercacognomeinvito);
        nickname = (EditText) findViewById(R.id.cercanicknameinvito);
        
        
        nomepar = bundle.getString("nome");
		cognomepar = bundle.getString("cognome");
		nicknamepar = bundle.getString("nickname");
		
		nome.setText(nomepar);
		cognome.setText(cognomepar);
		nickname.setText(nicknamepar);
        
		
		nomestr = nome.getText().toString();
		cognomestr = cognome.getText().toString();
		nicknamestr = nickname.getText().toString();
		ruolostr =ruolopar;
		oggettostr = oggettopar;
		
		if (controllo == 0) {
			Log.d("dovechiamo", "if");
			visualizzatutti();
		} else {
			visualizzaricerca(nomepar, cognomepar, nicknamepar, oggettopar,
					ruolopar);
		}
        
		setChildGroupData();
        ListaInvitiAdapter adapter = new ListaInvitiAdapter(groupItem, childItem,utentilist,preferitilist,idfesta,getApplicationContext());
   	 adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
   	      this);
   	  lista.setAdapter(adapter);
   	  lista.setOnChildClickListener(this);
	        
	        


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

	
	@Override
	public void onClick(View arg0) {
		nomestr = nome.getText().toString();
		cognomestr = cognome.getText().toString();
		nicknamestr = nickname.getText().toString();
		ruolostr = "Tutti";
		oggettostr = "Qualsiasi";
		
		Intent i;
		i = new Intent(InvitaActivity.this, InvitaActivity.class);
		i.putExtra("controllo", 1);
		i.putExtra("id", myid);
		i.putExtra("nome", nomestr);
i.putExtra("idfesta", idfesta);
		i.putExtra("cognome", cognomestr);
		i.putExtra("nickname", nicknamestr);
		i.putExtra("ruolo", ruolostr);
		i.putExtra("oggetto", oggettostr);
		startActivity(i);
	
	}
	

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			 int groupPosition,int childPosition, long id) {
	

		
		return false;
	}
	
	
	
	public void visualizzatutti() {
		Log.d("nomechiamata", "ciao");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Log.d("nomechiamata", "ciao1");
		try {	Log.d("nomechiamata", "ciao2");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://192.168.1.131:8084/party4party/ViewUtenti?idutente="
							+ myid);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			Log.d("nomechiamata", "ciao3");
			if (entity != null) {
				String risposta = EntityUtils.toString(entity);
				JSONArray jarr = new JSONArray(risposta);
				
				JSONObject json;
				
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
			HttpPost httppost = new HttpPost(
					"http://192.168.1.131:8084/party4party//Ricerca?idutente="
							+ myid + "&nome=" + nome1 + "&cognome=" + cognome1
							+ "&nickname=" + nickname1 + "&ruolo=" + ruolo1
							+ "&prestito=" + oggetto1);
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
